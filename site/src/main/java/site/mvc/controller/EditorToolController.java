package site.mvc.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.mvc.dto.AttachedFileDTO;
import site.mvc.service.AttachedFileService;
import site.mvc.vo.EditorImgUploaderResVO;

/**
 * @author benchel
 * https://ckeditor.com/docs/ckeditor4/latest/examples/image.html
 * https://ckeditor.com/docs/ckeditor4/latest/guide/dev_errors.html#clipboard-image-handling-disabled
 */
@Controller
@RequestMapping("/editor/*")
@RequiredArgsConstructor
@Slf4j
public class EditorToolController {

	private final Environment env;
	private final AttachedFileService attFileService;
	
	@PostMapping("/image/upload")
	@ResponseBody
	public ResponseEntity<?> ImgUploader(HttpServletRequest request, HttpServletResponse response) {
		
		log.info("---- uploading ----");

		EditorImgUploaderResVO resVO = new EditorImgUploaderResVO();
		
		String resourcePath = env.getProperty("editor.resource.full.url");
		String uploadPath = env.getProperty("editor.upload.path");
		String category = request.getParameter("category");
		
		AttachedFileDTO fileDTO = new AttachedFileDTO();
		fileDTO.setCategory(category);
		
		log.info("---- category : " + category);
		log.info("---- uploading path : " + uploadPath);
		log.info("---- resource path : " + resourcePath);
		
		try {
			
			Collection<Part> parts = request.getParts();
			
			for(Part part : parts) {
				log.info("======= PART ======");
				log.info("name={}", part.getName());
				
				if(part.getName().equals("upload")) {
					uploader(part, uploadPath, fileDTO);
					attFileService.insert(fileDTO);
				}
			}

			resVO.setFileName(fileDTO.getKey());
			resVO.setUploaded(1);
			resVO.setUrl(resourcePath+fileDTO.getKey());

			return new ResponseEntity<>(resVO, HttpStatus.OK);
			
		} catch (ServletException e) {
			resVO.setUploaded(0);
			return new ResponseEntity<>(resVO, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			resVO.setUploaded(0);
			return new ResponseEntity<>(resVO, HttpStatus.BAD_GATEWAY);
		} catch (Exception e) {
			resVO.setUploaded(0);
			return new ResponseEntity<>(resVO, HttpStatus.BAD_GATEWAY);
		}
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
        
        if(1048576 < size & size < 10485760) {
        	size_measure = Long.toString((size / 1024) / 1024) + "MB";
        } else if(1024 < size & size < 1048576) {
        	size_measure = Long.toString((size / 1024)) + "KB";
        } else if(size < 1024) {
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
	@ResponseBody
	public Map<String, Object> delete(@RequestBody AttachedFileDTO fileDTO) {
		
		log.info("---- deleting ----");
		
		Map<String, Object> result = new HashMap<>();
		
		String path = env.getProperty("file.path");
		
		log.info("======= FILE ======");
		log.info("file_key : " + fileDTO.getKey());
		log.info("path : " + path);
		log.info("category : " + fileDTO.getCategory());
		
		try {
			
			File file = this.getFile(fileDTO.getKey(), path);
			file.delete();
			
			attFileService.delete(fileDTO);
			
			result.put("result", true);
			result.put("msg", "삭제 성공");
		} catch (Exception e) {
			result.put("result", false);
		}
		
		return result;
	}
    
	/**
	 * 파일이름 인코딩
	 * @param data
	 * @return
	 */
    public String encodingByBrowser(String data) {
        String rtn = null;
        
    	if(data.isEmpty()) 
    		throw new NullPointerException();
    	
    	try {
    		rtn = URLEncoder.encode(data, "UTF-8").replaceAll("\\+", "%20");

    	} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return rtn;
    }
}
