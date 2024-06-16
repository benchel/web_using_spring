package site.mvc.vo;

import lombok.Data;

@Data
public class NoticeVO {
	private int idx;
	private String title;
	private String content;
	private String regDate;
	private String modifDate;
	private int view;
}
