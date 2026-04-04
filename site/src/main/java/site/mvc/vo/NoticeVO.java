package site.mvc.vo;

import lombok.Data;

@Data
public class NoticeVO {
	private int idx;
	private String seq;
	private String title;
	private String writer;
	private String content;
	private String regDate;
	private String modifDate;
	private int view;
	// 이전
	private int prevIdx;
	private String prevTitle;
	// 다음
	private int nextIdx;
	private String nextTitle;
}
