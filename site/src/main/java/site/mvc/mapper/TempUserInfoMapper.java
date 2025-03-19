package site.mvc.mapper;

import org.springframework.stereotype.Repository;
import site.common.config.DBMapper;
import site.mvc.dto.TempUserInfoDTO;

@DBMapper
@Repository
public interface TempUserInfoMapper {
	public int insert(TempUserInfoDTO tempUserInfoDTO) throws Exception;
}
