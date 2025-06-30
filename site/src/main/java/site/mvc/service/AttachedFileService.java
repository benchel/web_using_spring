package site.mvc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.mvc.dto.AttachedFileDTO;
import site.mvc.mapper.AttachedFileMapper;
import site.mvc.vo.AttachedFileVO;

@Service
@RequiredArgsConstructor
public class AttachedFileService {
	
	private final AttachedFileMapper attFileMapper;
	
	@Transactional(readOnly = false)
	public int insert(AttachedFileDTO fileDTO) throws Exception {
		return attFileMapper.insert(fileDTO);
	}
	
	@Transactional(readOnly = false)
	public int delete(AttachedFileDTO fileDTO) throws Exception {
		return attFileMapper.delete(fileDTO);
	}

	@Transactional(readOnly = false)
	public int update(AttachedFileDTO fileDTO) throws Exception {
		return attFileMapper.update(fileDTO);
	}
	
	public List<AttachedFileVO> getList(int parent, String category) throws Exception {
		AttachedFileDTO file = new AttachedFileDTO();
		file.setParent(parent);
		file.setCategory(category);
		List<AttachedFileVO> list = attFileMapper.list(file);
		
		return list;
	}
}
