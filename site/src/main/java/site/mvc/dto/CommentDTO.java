package site.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
	private int boardIdx;
	private String cmmtIdx;
	private String writer;
	private String parentCmmtIdx;
	private String parentCmmtWriter;
	private String content;
}
