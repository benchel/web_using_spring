package site.mvc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import site.common.utill.Paging;
import site.mvc.dto.CommentDTO;
import site.mvc.mapper.CommentMapper;
import site.mvc.vo.CommentVO;

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
	
	@Transactional(readOnly = true)
	public Map<String, Object> list(CommentDTO commentDTO) throws Exception {

		Map<String, Object> result = new HashMap<>();
		
		int pageNo = commentDTO.getPageNo() == 0 ? 1 : commentDTO.getPageNo();
		int pageSize = commentDTO.getPageSize() == 0 ? 10 : commentDTO.getPageSize();
		int pageBlock = commentDTO.getPageBlock() == 0 ? 10 : commentDTO.getPageBlock();
		commentDTO.setPageNo(pageNo);
		commentDTO.setPageSize(pageSize);
		commentDTO.setPageBlock(pageBlock);
		commentDTO.setPageOffset(Paging.getPageOffset(pageNo, pageSize));
		
		List<CommentVO> list = commentMapper.list(commentDTO);
		
		if(pageNo != 1 && list.size() == 0) {
			pageNo = 1;
			commentDTO.setPageNo(pageNo);
			commentDTO.setPageOffset(Paging.getPageOffset(pageNo, pageSize));
			result.put("list", commentMapper.list(commentDTO));
		}
		
		int totalCount = commentMapper.count(commentDTO);
		int totalPageNo = Paging.getTotalPageNo(totalCount, pageSize);
		String pagingHTML = Paging.getPagingHTML(totalCount, pageNo, pageSize, pageBlock);
		
		result.put("list", list);
		result.put("totalCount", totalCount);
		result.put("totalPageNo", totalPageNo);
		result.put("pagingHTML", pagingHTML);
		
		return result;
	}
}
