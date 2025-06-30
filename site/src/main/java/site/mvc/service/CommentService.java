package site.mvc.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import site.mvc.dto.CommentDTO;
import site.mvc.mapper.CommentMapper;

@Service
@AllArgsConstructor
public class CommentService {

	private final CommentMapper commentMapper;
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Map<String, Object> registry(CommentDTO commentDTO) throws Exception {
		
		Map<String, Object> result = new HashMap<>();
		int rs = commentMapper.insert(commentDTO);
		
		if(rs > 0) {
			result.put("msg", "댓글을 성공적으로 등록하였습니다.");
		} else {
			result.put("msg", "댓글 등록 실패\n 오류가 반복되는 경우 관리자에게 문의하여 주십시오.");
		}
		return result;
	}
}
