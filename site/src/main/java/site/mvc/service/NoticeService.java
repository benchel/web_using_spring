package site.mvc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.common.utill.Paging;
import site.mvc.dto.AttachedFileDTO;
import site.mvc.dto.NoticeDTO;
import site.mvc.mapper.NoticeMapper;
import site.mvc.vo.NoticeVO;

@Service
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeMapper noticeMapper;
	private final AttachedFileService attFileService;
	
	@Transactional(readOnly = true)
	public Map<String, Object> list(NoticeDTO noticeDTO) throws Exception {
		Map<String, Object> rs = new HashMap<>();
		
		int pageNo = noticeDTO.getPageNo() == 0 ? 1 : noticeDTO.getPageNo();
		int pageSize = noticeDTO.getPageSize() == 0 ? 10 : noticeDTO.getPageSize();
		int pageBlock = noticeDTO.getPageBlock() == 0 ? 10 : noticeDTO.getPageBlock();
		noticeDTO.setPageNo(pageNo);
		noticeDTO.setPageSize(pageSize);
		noticeDTO.setPageBlock(pageBlock);
		noticeDTO.setPageOffset(Paging.getPageOffset(pageNo, pageSize));
		
		List<NoticeVO> list = noticeMapper.list(noticeDTO);
		
		if(pageNo != 1 && list.size() == 0) {
			pageNo = 1;
			noticeDTO.setPageNo(pageNo);
			noticeDTO.setPageOffset(Paging.getPageOffset(pageNo, pageSize));
			rs.put("list", noticeMapper.list(noticeDTO));
		}
		
		int totalCount = noticeMapper.count(noticeDTO);
		int totalPageNo = Paging.getTotalPageNo(totalCount, pageSize);
		String pagingHTML = Paging.getPagingHTML(totalCount, pageNo, pageSize, pageBlock);
		
		rs.put("list", list);
		rs.put("totalCount", totalCount);
		rs.put("totalPageNo", totalPageNo);
		rs.put("pagingHTML", pagingHTML);
		
		return rs;
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public Map<String, Object> registry(NoticeDTO noticeDTO) throws Exception {
		Map<String, Object> rs = new HashMap<>();
		
		noticeMapper.insert(noticeDTO);
		
		// 첨부파일이 있는 경우
		if(!noticeDTO.getList().isEmpty()) {
			// 첨부파일의 정보(file.parent = board.idx)를 업데이트
			for(String fileKey : noticeDTO.getList()) {
				AttachedFileDTO file = new AttachedFileDTO();
				file.setKey(fileKey);
				file.setCategory("editor");
				file.setParent(noticeDTO.getIdx());
				attFileService.update(file);
			}
		}
		
		rs.put("result", true);
		rs.put("msg", "성공적으로 저장하였습니다.");		
		return rs;
	}
	
	@Transactional(readOnly = true)
	public NoticeVO view(NoticeDTO noticeDTO) throws Exception {
		return noticeMapper.view(noticeDTO);
	}	
	
}
