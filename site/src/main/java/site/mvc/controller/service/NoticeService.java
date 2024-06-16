package site.mvc.controller.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.mvc.mapper.NoticeMapper;

@Service
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeMapper noticeMapper;
	
	@Transactional(readOnly = true)
	public Map<String, Object> list() throws Exception {
		Map<String, Object> rs = new HashMap<>();
		rs.put("list", noticeMapper.list());
		return rs;
	}
	
}
