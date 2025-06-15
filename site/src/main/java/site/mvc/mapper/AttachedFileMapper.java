package site.mvc.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;
import site.common.config.DBMapper;
import site.mvc.dto.AttachedFileDTO;

@DBMapper
@Repository
public interface AttachedFileMapper {
	public List<String> showdb() throws Exception;
	public int insert(AttachedFileDTO fileDTO) throws Exception;
	public int delete(AttachedFileDTO fileDTO) throws Exception;
	public int update(AttachedFileDTO fileDTO) throws Exception;
}
