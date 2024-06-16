package site.mvc.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;
import site.common.config.DBMapper;
import site.mvc.vo.NoticeVO;

@DBMapper
@Repository
public interface NoticeMapper {
	public List<NoticeVO> list() throws Exception;
}
