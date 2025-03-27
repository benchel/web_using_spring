package site.mvc.mapper;

import org.springframework.stereotype.Repository;
import site.common.config.DBMapper;
import site.mvc.dto.TempUserInfoDTO;
import site.mvc.vo.TempUserInfoVO;

@DBMapper
@Repository
public interface TempUserInfoMapper {
	public int insert(TempUserInfoDTO tempUserInfoDTO) throws Exception;
	public TempUserInfoVO select(TempUserInfoDTO tempUserInfoDTO) throws Exception; 
	public int updateColThatIsCert(TempUserInfoDTO tempUserInfoDTO) throws Exception;
	public int deleteByIdAndEmail(TempUserInfoDTO tempUserInfoDTO) throws Exception;
}
