package site.mvc.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;
import site.common.config.DBMapper;
import site.mvc.dto.BoardDTO;
import site.mvc.vo.BoardVO;

@DBMapper
@Repository
public interface BoardMapper {
	public int count(BoardDTO boardDTO) throws Exception;
	public List<BoardVO> list(BoardDTO boardDTO) throws Exception;
	public BoardVO get(BoardDTO boardDTO) throws Exception;
	public int insert(BoardDTO boardDTO) throws Exception;
}
