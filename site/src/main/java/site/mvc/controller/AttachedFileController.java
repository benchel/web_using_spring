package site.mvc.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.mvc.controller.service.AttachedFileService;
import site.mvc.dto.AttachedFileDTO;

/**
 * @author benchel
 *
 */
@Controller
@RequestMapping("/file/*")
@RequiredArgsConstructor
@Slf4j
public class AttachedFileController {

	private final Environment env;
	private final AttachedFileService attFileService;
	
	@PostMapping("/upload")
	@ResponseBody
	public Map<String, Object> upload_contr(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<>();

		String uploadPath = env.getProperty("file.path");
		String category = request.getParameter("category");
		
		AttachedFileDTO fileDTO = new AttachedFileDTO();
		fileDTO.setCategory(category);
		
		log.info("---- uploading ----");
		log.info("---- category : " + category);
		log.info("---- uploading path : " + uploadPath);
		
		try {
			
			Collection<Part> parts = request.getParts();
			
			for(Part part : parts) {
				log.info("======= PART ======");
				log.info("name={}", part.getName());
				
				if(part.getName().equals("file")) {
					uploader(part, uploadPath, fileDTO);
					attFileService.insert(fileDTO);
				}
			}
			
			result.put("file", fileDTO);
			result.put("result", true);
			result.put("msg", "업로드 성공");
			
		} catch (ServletException e) {
			e.printStackTrace();
			result.put("result", false);
		} catch (IOException e) {
			e.printStackTrace();
			result.put("result", false);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", false);
		}
		
		return result;
	}
	
	/**
	 * 파일 객체를 생성하여 리턴한다.
	 * @param fileKey
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
    private File getFile(String fileKey, String filePath) throws Exception {
    	String fileName ="";
    	
        File temp = new File(filePath);
        if(!temp.isDirectory()) temp.mkdirs();
        
    	if (!filePath.endsWith("/")) {
    		fileName = filePath + "/" + fileKey;
    	} else {
    		fileName = filePath + fileKey;
    	}
    	File file = new File(fileName);
    	return file;
    }
    
    private void uploader(Part part, String uploadPath, AttachedFileDTO fileDTO) throws Exception {
    	
		String fileName = part.getHeader("content-disposition");
		fileName = fileName.substring(fileName.lastIndexOf("=")+1);
		fileName = fileName.replaceAll("\"", "");
		
		String contentType = part.getHeader("content-type");
        String extension = fileName.substring(fileName.lastIndexOf('.'));
        String fileKey = UUID.randomUUID().toString().toUpperCase() + extension;
        
        long size = part.getSize();	
        String size_measure = "";
        if(size < 1000000000) {
        	size_measure = Long.toString((size / 1024) / 1024) + "MB";
        } else if(size < 1000000) {
        	size_measure = Long.toString((size / 1024)) + "KB";
        } else if(size <= 1024) {
        	size_measure = Long.toString(size) + "B";
        }
        
		fileDTO.setMediaType(contentType);
		fileDTO.setName(fileName);
		fileDTO.setKey(fileKey);
		fileDTO.setSize(size_measure);
		
		log.info("======= FILE ======");
		log.info("file_name : " + fileName);
		log.info("contentType : " + contentType);
		log.info("size={}", size_measure);        
        
        //데이터 읽기
        InputStream inStream = part.getInputStream();
        File file = this.getFile(fileKey, uploadPath);
        
        OutputStream outStream = new FileOutputStream(file);

        int readBytes = 0;
        byte[] buffer = new byte[8192];
        while ((readBytes = inStream.read(buffer, 0, 8192)) != -1) {
        	outStream.write(buffer, 0, readBytes);
	   }

        outStream.close();
        inStream.close();
        
    }

	@PostMapping("/delete")
	public void delete() {
		
	}
}
