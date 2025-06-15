package site.mvc.controller.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import site.mvc.dto.AttachedFileDTO;
import site.mvc.dto.BoardDTO;
import site.mvc.mapper.BoardMapper;

@Service
@AllArgsConstructor
public class BoardService {
	
	private final BoardMapper boardMapper;
	private final AttachedFileService attFileService;
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Map<String, Object> registry(BoardDTO boardDTO) throws Exception {
		Map<String, Object> result = new HashMap<>();
		
		boardMapper.insert(boardDTO);
		
		// 첨부파일이 있는 경우
		if(!boardDTO.getList().isEmpty()) {
			// 첨부파일의 정보(file.parent = board.idx)를 업데이트
			for(AttachedFileDTO file : boardDTO.getList()) {
				file.setParent(boardDTO.getIdx());
				attFileService.update(file);
			}
		}
		
		result.put("result", true);
		result.put("msg", "게시글 등록 완료");
		return result;
	}
}
