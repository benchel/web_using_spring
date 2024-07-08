package site.mvc.controller.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.common.utill.Paging;
import site.mvc.dto.NoticeDTO;
import site.mvc.mapper.NoticeMapper;
import site.mvc.vo.NoticeVO;

@Service
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeMapper noticeMapper;
	
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
	
}
