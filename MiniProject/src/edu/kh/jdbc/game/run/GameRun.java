package edu.kh.jdbc.game.run;

import edu.kh.jdbc.game.view.GameView;

public class GameRun {
	// 비회원 게임시 랭크에 입력할 이름-후에 수정 불가 
	// 내기록보기 
	// 회원 게임
	// 랭크 
	// 게시판 
	
	// 아재패턴 게임으로
	// 무한모드
	// 기본모드(시간설정)
	// 패턴 제한모드 
	// 타이핑 시작할 때 시간 흐르게 가능?
	// 게시판에 게임 건의사항이나 넣고 
	// 해결 됐는지 안됐는지도 컬럼으로 넣자
	
	// 시작 누르면 321 나오고 바로 문자열 출력, 바로 따라치기 
	
	// 회원- 최고기록 
	// 회원 - 평균점수 
	

	// 게임테이블-순위 , 점수 , 시간, 사람 fk 플레이어 테이블  
	// 플레이어 테이블
	
	public static void main(String[] args) {
		try {
			new GameView().showMemu();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
