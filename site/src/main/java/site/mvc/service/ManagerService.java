package site.mvc.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.mvc.mapper.ManagerMapper;
import site.mvc.vo.ManagerVO;

@Service
@RequiredArgsConstructor
public class ManagerService {

	private final ManagerMapper managerMapper;
	
	public ManagerVO findManager(String id) throws Exception {
		return managerMapper.searchManagerById(id);
	}

}
