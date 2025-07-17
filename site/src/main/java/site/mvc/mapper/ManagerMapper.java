package site.mvc.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import site.common.config.DBMapper;
import site.mvc.vo.ManagerVO;

@DBMapper
@Repository
public interface ManagerMapper {
	public ManagerVO searchManagerById(@RequestParam("id") String id) throws Exception;
	public List<String> showdb() throws Exception;
}
