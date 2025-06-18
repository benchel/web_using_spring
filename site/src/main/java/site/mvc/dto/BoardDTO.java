package site.mvc.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BoardDTO extends BaseDTO {
	private int idx;
	private String title;
	private String writer;
	private String content;
	private String regDate;
	private String modifDate;
	private int view;
	
	private List<AttachedFileDTO> list; 
}
