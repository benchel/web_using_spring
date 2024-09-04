package site.mvc.mapper;


import org.springframework.stereotype.Repository;
import site.common.config.DBMapper;
import site.mvc.dto.UserDTO;

@DBMapper
@Repository
public interface UserMapper {
	public int checkOverlap(UserDTO dto) throws Exception;
}
