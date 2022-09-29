package edu.kh.jdbc.game.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

import edu.kh.jdbc.game.player.vo.Game;
import edu.kh.jdbc.game.player.vo.Player;
import edu.kh.jdbc.game.service.GameService;


public class GameView {
	
	private GameService service=new GameService();

	private Scanner sc=new Scanner(System.in);
	
	Player loginPlayer=null;
	
	public void showMemu() throws Exception {
		int input=-1;
		
		do {
			
			if(loginPlayer==null) {
				System.out.println("<<타이핑 게임>>\n");
				
				System.out.println("1. 랭킹 조회");
				System.out.println("2. 비회원");
				System.out.println("3. 로그인");
				System.out.println("4. 회원가입");
				System.out.println("0. 게임 종료");
				
				
				System.out.print("입력 : ");
				input=sc.nextInt();
				
				
				switch(input) {
				case 1: ranking(); break;
				case 2: oncePlayer(); break;
				case 3: logIn(); break;
				case 4: signIn(); break;
				case 0: System.out.println("게임을 종료합니다."); break;
				default:
					System.out.println("번호를 다시 입력하세요.");
				}
			}else {
				
				System.out.println("<<타이핑 게임-로그인>>\n");
				
				System.out.println("1. 내 기록 보기 ");
				System.out.println("2. 게임하기 ");
				System.out.println("3. 연습 모드 ");
				System.out.println("4. 커스텀 모드 ");
				System.out.println("5. 로그아웃");
				System.out.println("99. 프로그램 종료");
				
				
				System.out.print("입력 : ");
				input=sc.nextInt();
				
				
				switch(input) {
				case 1: myRecord(); break;
				case 2: oncePlayer(); break;
				case 3: practiceGame(randomSpell()); break;
				case 4: costomGame(); break;
				case 5: loginPlayer=null; System.out.println("[로그아웃 되었습니다.]"); input=-1; break;
				case 0: System.out.println("프로그램 종료"); break;
				default:
					System.out.println("번호를 다시 입력하세요.");
				}
				
			}
			
			
			
			
		}while(input!=0);
		
		
	}
	
	

	/**
	 * 3. 로그인 
	 */
	private void logIn() {
		System.out.println("[로그인]");
		
		System.out.print("아이디 : ");
		String memberId=sc.next();
		
		System.out.print("비밀번호 : ");
		String memberPw=sc.next();
		
		// 로그인 서비스 호출 후 조회 결과를 loginMember에 저장 
		try {
			loginPlayer=service.login(memberId, memberPw);
			
			System.out.println();
			
			if(loginPlayer!=null) {
				System.out.println(loginPlayer.getPlayerName()+"님 환영합니다.");
			}else {
				System.out.println("[아이디 또는 비밀번호가 일치하지 않습니다.]");
			}
		}catch(Exception e) {
			System.out.println("\n<<로그인 중 예외 발생>>");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 4. 회원가입 
	 */
	private void signIn() {
		
		System.out.println("[회원 가입]");

		String playerId = null;
		String playerPw1 = null;
		String playerPw2 = null;

		String playerName = null;

		// 아이디를 입력 받아 중복이 아닐 때까지 반복

		try {
			while (true) {

				System.out.print("아이디 입력 : ");
				playerId = sc.next();

				// 입력받은 아이디를 매개변수로 전달하여
				// 중복여부를 검사하는 서비스 호출 후 결과 반환 받기

				int result = service.idDupCheck(playerId);

				// 중복이 아닌 경우
				if (result == 0) {
					System.out.println("[사용 가능한 아이디 입니다.]");
					break;
				} else {
					System.out.println("[이미 사용중인 아이디 입니다.]");
				}

			}

			// 비밀번호 입력
			// 비밀번호/비밀번호 확인이 일치할 때 까지 무한 반복

			while (true) {

				System.out.print("비밀번호 : ");
				playerPw1 = sc.next();

				System.out.print("비밀번호 재입력 : ");
				playerPw2 = sc.next();

				System.out.println();
				if (playerPw1.equals(playerPw2)) {// 일치할 경우
					System.out.println("[일치합니다]");
					break;
				} else {// 일치하지 않을 경우
					System.out.println("[비밀번호가 일치하지 않습니다. 다시 입력해주세요.]");
				}
				System.out.println();
			}

			// 이름 입력
			System.out.print("이름 입력 : ");
			playerName = sc.next();

			

			// -- 아이디, 비밀번호, 이름 입력 완료 --
			// -> 하나의 VO에 담아서 서비스 호출 후 반환 받기
			Player player=new Player(playerId, playerPw2, playerName);

			int result = service.signIn(player);

			System.out.println();
			// 서비스 처리 결과에 따른 출력 화면 제어
			if (result > 0) {
				System.out.println("***** 회원가입 성공 *****");
			} else {
				System.out.println("<<회원가입 실패>>");
			}
			System.out.println();

		} catch (Exception e) {
			System.out.println("\n<<회원 가입 중 예외 발생>>");
			e.printStackTrace();
		}
		
	}


	private void oncePlayer() throws Exception {
		Game game=new Game();
		String spendTime;
		if(loginPlayer==null) {
			int result=0;
			System.out.println("\n[비회원 게임]\n");
			
			spendTime=startGame();
			String oncePlayer;
			do {
				System.out.print("\n순위를 위한 이름을 입력하세요 : ");
				oncePlayer=sc.next();
				
				result=service.nameDupCheck(oncePlayer);
				
				if(result!=0)
					System.out.println("이미 같은 이름이 있습니다.");
			}while(result!=0);
			
			
			game.setPlayerNm(oncePlayer);
			
			
		}else {
			System.out.println("\n[게임 시작]\n");
			
			spendTime=startGame();
			
			game.setPlayerNm(loginPlayer.getPlayerName());
		}
		
		game.setPlaytime(spendTime);
		
		
		// 랭킹 들어갈 때 로그인 되어있는 경우 더 잘나온 값으로 변경
		// 비회원 이름 입력시 같은 이름 있는지 확인해야함 
		try {
			service.playGame(game);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		ranking();
		
	}
	
	private void ranking() {
		List<Game> listGame;
		
		System.out.println("\n[랭킹]\n");
		
		try {
			listGame=service.ranking();
			
			
			for(Game g:listGame) {
				System.out.printf("| %d | %15s | %10s |\n",g.getPlayerNo() ,g.getPlayerNm(),g.getPlaytime());
			}
			
			
		}catch(Exception e) {
			System.out.println("\n<<랭킹 조회 중 오류 발생>>\n");
			e.printStackTrace();
		}
		
	}


	
	private void myRecord() {
		System.out.println("\n[내 기록]\n");
		
		List<Game> listGame;
		String avgRecord=null;
		
		try {
			
			avgRecord=service.avgRecord(loginPlayer.getPlayerName());
			
			listGame=service.myRecord(loginPlayer.getPlayerName());
			
			System.out.print("평균 시간 : ");
			System.out.printf("%10s\n", avgRecord);
			
			for(Game g:listGame) {
				System.out.printf("| %s | %15s | %10s |\n",g.getPlayDate() ,g.getPlayerNm(),g.getPlaytime());
			}
			
			
		}catch(Exception e) {
			System.out.println("\n<<랭킹 조회 중 오류 발생>>\n");
			e.printStackTrace();
		}
	}
	
	private void practiceGame(String correctSpell) throws InterruptedException {
		int result=0;
		
		System.out.println("\n[연습모드]\n");
		
		do {
			delayThreeSec();
			System.out.println("*** 시작 ***");
			System.out.println("(끝내려면 $exit를 입력하세요)");
			
			
			System.out.println(correctSpell);
			
			long startTime=System.currentTimeMillis();
			
			result=inputSpell(correctSpell);
			
			long endTime=System.currentTimeMillis();
			double spendTime=(double)(endTime-startTime)/1000;
		
			
			System.out.printf("\n %.3f 초 소요!! \n",spendTime);
		}while(result!=-1);
	}
	
	
	private void costomGame() throws Exception{
		System.out.println("[커스텀 게임 모드]");
		
		System.out.print("연습하고 싶은 키를 입력하세요 (아무 알파벳 입력) : ");
		String input=sc.next();
		
		System.out.println("몇자리로 연습할까요? ");
		int inputNum=sc.nextInt();
		
		practiceGame(costomRandomSpell(input, inputNum));
	
	}



	
	public String startGame() throws InterruptedException{
		
		delayThreeSec();
		System.out.println("*** 시작 ***");
		
		String correctSpell=randomSpell();
		
		System.out.println(correctSpell);
		
		long startTime=System.currentTimeMillis();
		
		inputSpell(correctSpell);
		
		long endTime=System.currentTimeMillis();
		double spendTime=(double)(endTime-startTime)/1000;
		
		String spendTimestr=Double.toString(spendTime);
		
		System.out.printf("\n %.3f 초 소요!! \n",spendTime);
		return spendTimestr;
		
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
	
	/** 커스텀 랜덤 문자열 생성 및 초 재주기 
	 * @throws InterruptedException
	 */
	public String costomRandomSpell(String spell, int spellNum) throws InterruptedException {
		
		
		char []spellList= spell.toCharArray();
		String correctSpell="";
		
		for(int i=0;i<spellNum;i++) {
			int ranNum=(int)(Math.random()*spellList.length);
			correctSpell+=spellList[ranNum];
		}
		
		return correctSpell;
	}
	
	/** 사용자에게서 문자 입력받는 메서드 
	 * @param correctSpell 랜덤 문자열 
	 */
	public int inputSpell(String correctSpell) {
		
		while(true) {
			System.out.println("입력 : ");
			
			String input=sc.next();
			
			if(input.equals("$exit")) {
				System.out.println("연습모드 종료");
				return -1;
			}
			else if(correctSpell.equals(input))
				break;
			else
				System.out.print("***********\n\n"+correctSpell+"\n다시 ");
		}
		return 0;
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
