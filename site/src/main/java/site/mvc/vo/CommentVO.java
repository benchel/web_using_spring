package site.mvc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO {
	private int boardIdx;
	private String cmmtIdx;
	private String writer;
	private String parentCmmtIdx;
	private String parentCmmtWriter;
	private String content;
	private String regDate;
	private String modifDate;
	private String isDelete;
	private String isModif;	
	private int recmmtCnt;
	private String recmmtStart;
	private String recmmtEnd;
}
