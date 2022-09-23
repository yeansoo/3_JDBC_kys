package edu.kh.jdbc.game.view;

import java.util.Scanner;

import edu.kh.jdbc.game.player.vo.Player;

public class GameView {

	private Scanner sc=new Scanner(System.in);
	
	Player player=null;
	
	public void showMemu() throws Exception {
		int input=-1;
		
		do {
			System.out.println("<<타이핑 게임>>\n");
			
			System.out.println("1. 랭킹 조회");
			System.out.println("2. 비회원(일회성)");
			System.out.println("3. 로그인");
			System.out.println("4. 로그아웃");
			System.out.println("5. 회원가입");
			System.out.println("0. 게임 종료");
			
			
			System.out.print("입력 : ");
			input=sc.nextInt();
			
			
			switch(input) {
			case 1: break;
			case 2: oncePlayer(); break;
			case 3: break;
			case 4: break;
			case 0: System.out.println("게임을 종료합니다."); break;
			default:
				System.out.println("번호를 다시 입력하세요.");
			}
			
			
			
		}while(input!=0);
		
		
	}
	
	
	private void oncePlayer() throws InterruptedException {
		
		System.out.println("\n[비회원 게임]\n");
		
		double spendTime=startGame();
		
		System.out.print("\n순위를 위한 이름을 입력하세요 : ");
		String oncePlayer=sc.next();
		
		player.setPlayerId(oncePlayer);
		player.setPlaytime(spendTime);
		// 회원이랑 비회원이랑 테이블을 나눠야하나 
		
	}


	private void login() {
		// TODO Auto-generated method stub
		
	}

	
	public double startGame() throws InterruptedException{
		
		delayThreeSec();
		System.out.println("*** 시작 ***");
		
		String correctSpell=randomSpell();
		
		System.out.println(correctSpell);
		
		long startTime=System.currentTimeMillis();
		
		inputSpell(correctSpell);
		
		long endTime=System.currentTimeMillis();
		double spendTime=(double)(endTime-startTime)/1000;
		
		System.out.printf("\n%.3f 초 소요!! ",spendTime);
		return spendTime;
		
	}

	/** 랜덤 문자열 생성 및 초 재주기 
	 * @throws InterruptedException
	 */
	public String randomSpell() throws InterruptedException {
		
		
		char[] spell= {'q','w','e','a','s','d'};
		String correctSpell="";
		
		for(int i=0;i<6;i++) {
			int ranNum=(int)(Math.random()*6);
			correctSpell+=spell[ranNum];
		}
		
		return correctSpell;
	}
	
	/** 사용자에게서 문자 입력받는 메서드 
	 * @param correctSpell 랜덤 문자열 
	 */
	public void inputSpell(String correctSpell) {
		
		while(true) {
			System.out.println("입력 : ");
			
			if(correctSpell.equals(sc.next()))
				break;
			else
				System.out.print("***********\n\n"+correctSpell+"\n다시 ");

		}
	}
	
	/** 3초 지연 출력 321 메서드 
	 * @throws InterruptedException
	 */
	public void delayThreeSec() throws InterruptedException { 
		for(int i=3;i>0;i--) {
			Thread.sleep(1000);
			System.out.println(i+"..");
		}
		Thread.sleep(1000);
		
	}
	
}
