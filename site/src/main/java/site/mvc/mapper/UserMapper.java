package site.mvc.mapper;


import org.springframework.stereotype.Repository;
import site.common.config.DBMapper;
import site.mvc.dto.UserDTO;
import site.mvc.vo.UserVO;

@DBMapper
@Repository
public interface UserMapper {
	public int checkOverlap(UserDTO dto) throws Exception;
	public int insert(UserDTO dto) throws Exception;
	public UserVO searchUserById(String id) throws Exception;
}
