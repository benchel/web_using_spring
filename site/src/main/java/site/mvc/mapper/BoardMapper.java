package site.mvc.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;
import site.common.config.DBMapper;
import site.mvc.dto.BoardDTO;
import site.mvc.vo.BoardVO;

@DBMapper
@Repository
public interface BoardMapper {
	public List<BoardVO> list() throws Exception;
	public int insert(BoardDTO boardDTO) throws Exception;
}
