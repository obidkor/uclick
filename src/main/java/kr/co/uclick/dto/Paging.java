package kr.co.uclick.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import kr.co.uclick.service.UserService;

public class Paging {

	public int pagenow;// 현재 페이지
	public int totalCount;// 전체게시물 수
	public int countList; // 한 페이지에 출력될 게시물 수
	public int totalPage;// 토탈 페이지 수;
	public int countPage = 5; // 한 페이지에 출력될 페이지 수
	public int startPage = ((pagenow - 1) / countPage) * countPage + 1;// 스타트페이지
	public int endPage = startPage + countPage - 1;// 엔드페이지

	public int getPagenow() {
		return pagenow;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public Paging setPagenow(int pagenow) {
		this.pagenow = pagenow;
		return this;
	}

	public Paging setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		return this;
	}

	public int getCountList() {
		return countList;
	}

	public Paging setCountList(int countList) {
		this.countList = countList;
		return this;
	}
	public Paging setTotalPage() {
		this.totalPage = totalCount/countList;
		return this;
	}
	
	public void calcPage() {

		totalPage = totalCount / countList;// 토탈 페이지 수
		
		if(pagenow<0) {
			pagenow=1;
		}
		
		if (totalCount % countList > 0) { // 전체 게시물을 한페이지의 게시물 수로 나누게 되면 전체 페이지가 나오는데
			// 이때 나머지가 0보다 크게되면 한페이지가 더있어야 마지막페이지를 표시가능 토탈페이지에 +1을 더해줘야함(소수점 버리기때문)
			totalPage++;
		}
		if (totalPage < pagenow) {// pagenow는 totalPage를 넘거가면안되므로
			pagenow = totalPage;// 넘어가면 같게 해준다.
		}
		if (endPage > totalPage) {// endPage도 totalPage를 넘어갈수 없다.
			endPage = totalPage;
		}
	}

	public int checkpagenow(int pagenow, int totalPage) {
		// pagenow를 한번더 사용할 경우 이 메서드 호출
		this.pagenow = pagenow;
		this.totalPage = totalPage;
		if (totalPage < pagenow) {
			this.pagenow = totalPage;
		}
		return this.pagenow;
	}

	public int checktotalpage(int totalPage, int endPage) {
		// 토탈 페이지를 한번더 사용할 경우 이 메서드 호출
		this.totalPage = totalPage;
		this.endPage = endPage;
		if (endPage > totalPage) {
			this.endPage = totalPage;
		}
		return this.totalPage;/////
	}

}
