package site.mvc.mapper;

import org.springframework.stereotype.Repository;

import site.common.config.DBMapper;
import site.mvc.dto.CommentDTO;

@DBMapper
@Repository
public interface CommentMapper {
	public int insert(CommentDTO commentDTO) throws Exception;
}
