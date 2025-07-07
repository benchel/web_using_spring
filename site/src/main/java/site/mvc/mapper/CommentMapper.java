package site.mvc.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import site.common.config.DBMapper;
import site.mvc.dto.CommentDTO;
import site.mvc.vo.CommentVO;

@DBMapper
@Repository
public interface CommentMapper {
	public int insert(CommentDTO commentDTO) throws Exception;
	public int insertRecomment(CommentDTO commentDTO) throws Exception;
	public int count(CommentDTO commentDTO) throws Exception;
	public List<CommentVO> list(CommentDTO commentDTO) throws Exception;
}
