package site.mvc.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;
import site.common.config.DBMapper;
import site.mvc.dto.NoticeDTO;
import site.mvc.vo.NoticeVO;

@DBMapper
@Repository
public interface NoticeMapper {
	public int count(NoticeDTO noticeDTO) throws Exception;
	public int insert(NoticeDTO noticeDTO) throws Exception;
	public List<NoticeVO> list(NoticeDTO noticeDTO) throws Exception;
}
