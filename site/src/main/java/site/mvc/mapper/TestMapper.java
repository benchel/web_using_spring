package site.mvc.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;
import site.common.config.DBMapper;

@DBMapper
@Repository
public interface TestMapper {
	public List<String> showdb() throws Exception;
}
