package site.mvc.dto;

public class BaseDTO {
	//페이징
	private int pageNo = 1;//페이지 번호
	private int pageSize = 10;//페이지 사이즈
	private int pageBlock = 10;//페이지 블록
	private int pageOffset; // 페이지 시작 번호
	
	public int getPageNo() {
		return pageNo;
	}
	
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getPageBlock() {
		return pageBlock;
	}
	
	public void setPageBlock(int pageBlock) {
		this.pageBlock = pageBlock;
	}

	public int getPageOffset() {
		return pageOffset;
	}

	public void setPageOffset(int pageOffset) {
		this.pageOffset = pageOffset;
	}
	
}
