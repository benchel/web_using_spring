package site.mvc.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NoticeDTO extends BaseDTO {
	private int idx;
	private String title;
	private String writer;
	private String content;
	
	private ArrayList<String> imgs;
	private List<AttachedFileDTO> files; 
}
