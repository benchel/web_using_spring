package site.mvc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import site.common.utill.Paging;
import site.mvc.dto.AttachedFileDTO;
import site.mvc.dto.BoardDTO;
import site.mvc.mapper.BoardMapper;
import site.mvc.vo.BoardVO;

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
	
	@Transactional(readOnly = true)
	public Map<String, Object> list(BoardDTO boardDTO) throws Exception {
		Map<String, Object> result = new HashMap<>();

		int pageNo = boardDTO.getPageNo() == 0 ? 1 : boardDTO.getPageNo();
		int pageSize = boardDTO.getPageSize() == 0 ? 10 : boardDTO.getPageSize();
		int pageBlock = boardDTO.getPageBlock() == 0 ? 10 : boardDTO.getPageBlock();
		boardDTO.setPageNo(pageNo);
		boardDTO.setPageSize(pageSize);
		boardDTO.setPageBlock(pageBlock);
		boardDTO.setPageOffset(Paging.getPageOffset(pageNo, pageSize));
		
		List<BoardVO> list = boardMapper.list(boardDTO);
		
		if(pageNo != 1 && list.size() == 0) {
			pageNo = 1;
			boardDTO.setPageNo(pageNo);
			boardDTO.setPageOffset(Paging.getPageOffset(pageNo, pageSize));
			result.put("list", boardMapper.list(boardDTO));
		}
		
		int totalCount = boardMapper.count(boardDTO);		
		int totalPageNo = Paging.getTotalPageNo(totalCount, pageSize);
		String pagingHTML = Paging.getPagingHTML(totalCount, pageNo, pageSize, pageBlock);
				
		result.put("list", list);
		result.put("totalCount", totalCount);
		result.put("totalPageNo", totalPageNo);
		result.put("pagingHTML", pagingHTML);
		
		return result;
	}
	
	@Transactional(readOnly = true)
	public BoardVO getBoard(BoardDTO boardDTO) throws Exception {
		return boardMapper.get(boardDTO);
	}
	
}
