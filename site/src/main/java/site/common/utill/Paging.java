package site.common.utill;

public class Paging {

	/**
	 * 전체 페이지 번호를 구한다.
	 * @param totalCount 게시글의 총 개수
	 * @param pageSize 총 게시글을 몇 개의 페이지로 나누어 보여줄 것인지 지정한 값
	 * @return
	 */
	public static int getTotalPageNo(int totalCount, int pageSize) {
		int totalPageNo = 1;
		if(totalCount > 0 && pageSize > 0) {
			totalPageNo = (int) Math.ceil((double) totalCount / pageSize);
		}
		return totalPageNo;
	}
	
	/**
	 * 페이지네이션의 시작 번호를 구한다.
	 * @param pageNo 현재 조회하고 있는 페이지의 번호
	 * @param pageSize 총 게시글을 몇 개의 페이지로 나누어 보여줄 것인지 지정한 값
	 * @return
	 */
	public static int getPageOffset(int pageNo, int pageSize) {
		return pageSize * (pageNo - 1);
	}
	
	/**
	 * 페이지네이션의 끝 번호를 구한다.
	 * @param pageNo 현재 조회하고 있는 페이지의 번호
	 * @param pageSize 총 게시글을 몇 개의 페이지로 나누어 보여줄 것인지 지정한 값
	 * @return
	 */
	public static int getPageEnd(int pageNo, int pageSize) {
		return getPageOffset(pageNo, pageSize) + pageSize;
	}
	
	/**
	 * 
	 * @param totalCount 게시글의 총 개수
	 * @param pageNo 현재 조회하고 있는 페이지의 번호
	 * @param pageSize 총 게시글을 몇 개의 페이지로 나누어 보여줄 것인지 지정한 값
	 * @param pageBlock 현재 남아있는 페이지 수
	 * @return
	 */
	public static String getPagingHTML(int totalCount, int pageNo, int pageSize, int pageBlock) {
		
		// String 대신 StringBuffer를 쓰는 이유
		// new 연산자 반복 호출을 방지
		// 처음부터 StringBuffer를 사용
		StringBuffer rs = new StringBuffer();
		int totalPage = getTotalPageNo(totalCount, pageSize);
		
		if(totalCount > 0) {
			// 시작 페이지
			int outsetPage = 1;
			
			// 현재 페이지 번호 계산
			if(pageNo > 0 && pageBlock > 0) {
				// 홀수인 경우
				if(pageNo % pageBlock > 0) {
					outsetPage = pageNo - (pageNo % pageBlock) + 1; 
				} else {
					outsetPage = pageNo - pageBlock + 1;
				}
			}
			
			// 마지막 페이지
			int endPage = outsetPage + pageBlock - 1;
			if(endPage > totalPage) {
				endPage = totalPage;
			}
			
			// 이전 페이지
			int prevPage = outsetPage - pageBlock;
			if(prevPage < 1) {
				prevPage = 1;
			}
			
			// 다음 페이지
			int nextPage = outsetPage + pageBlock;
			if(nextPage > totalPage) {
				nextPage = totalPage;
			}
			
			rs.setLength(0);
			rs.append("<div class=\"pages\">");
			// 맨처음 페이지로
			rs.append("<span class=\"page\" page="+ outsetPage +">&lt;&lt;</span>");
			// 이전 페이지로
			rs.append("<span class=\"page\" page="+ prevPage +">&lt;</span>");
			
			for(int i = outsetPage; i <= endPage; i++) {
				if(pageNo == i) {
					rs.append("<span class=\"page pick\" page="+ i +">"+ i +"</span>");
				} else {
					rs.append("<span class=\"page\" page="+ i +">"+ i +"</span>");
				}
			}
			
			// 다음 페이지로
			rs.append("<span class=\"page\" page="+ nextPage +">&gt;</span>");
			// 맨마지막 페이지로
			rs.append("<span class=\"page\" page="+ endPage +">&gt;&gt;</span>");
			rs.append("</div>");
		}
		
		return rs.toString();
	}
	
}
