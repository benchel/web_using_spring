package site.mvc.controller.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.mvc.dto.AttachedFileDTO;
import site.mvc.mapper.AttachedFileMapper;

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
	
}
