package site.mvc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BoardVO {
	private int idx;
	private String title;
	private String writer;
	private String content;
	private String regDate;
	private String modifDate;
	private int view;
}
