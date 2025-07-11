package site.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CommentDTO extends BaseDTO {
	private int boardIdx;
	private String cmmtIdx;
	private String writer;
	private String parentCmmtIdx;
	private String parentCmmtWriter;
	private String content;
}
