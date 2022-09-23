package edu.kh.jdbc.board.view;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.board.model.service.BoardService;
import edu.kh.jdbc.board.model.service.CommentService;
import edu.kh.jdbc.board.model.vo.Board;
import edu.kh.jdbc.board.model.vo.Comment;
import edu.kh.jdbc.main.view.MainView;

public class BoardView {

	private Scanner sc=new Scanner(System.in);
	
	private BoardService bService=new BoardService();
	
	private CommentService cService=new CommentService();
	
	
	/**
	 * 게시판 기능 메뉴 화면 
	 */
	public void boardMenu() {
		 int input=-1;
		 
		 do {
			 try {
				 System.out.println("\n*****게시판메뉴*****\n");
				 System.out.println("1. 게시글 목록 조회 ");
				 System.out.println("2. 게시글 상세 조회(+ 댓글 기능) ");
				 System.out.println("3. 게시글 작성");
				 System.out.println("4. 게시글 검색");
				 System.out.println("0. 메인 메뉴 ");
				 
				 System.out.print("\n메뉴 선택 : ");
				 input=sc.nextInt();
				 sc.nextLine(); // 입력버퍼 개행문자 제거 
				 
				 System.out.println();
				 switch(input) {
				 case 1: selectAllBoard(); break; // 게시글 목록 조회 
				 case 2: selectBoard(); break;
				 
				 case 3: break;
				 case 4: break;
				 case 0: System.out.println("[메인메뉴로 이동합니다.]"); break;
				 default:
					 System.out.println("메뉴에 작성된 번호만 입력해주세요.");
				 }
				 System.out.println();
				 
				 
			 }catch(InputMismatchException e) {
				 System.out.println("\n<<입력 형식이 올바르지 않습니다.>>\n");
				 sc.nextLine();
			 }
			 
			 
		 }while(input!=0);
	}


	/**
	 * 게시글 목록 조회 
	 */
	private void selectAllBoard() {
		System.out.println("\n[게시글 목록 조회]\n");
		
		try {
			
			List<Board> boardList= bService.selectAllBoard();
			
			if(boardList.isEmpty()) {
				System.out.println("게시글이 존재하지 않습니다. ");
			}else {
				
				for(Board b:boardList) {
					// 3 | 샘플제목3[4] | 유저삼 | 3시간 전 | 10
					System.out.printf("%d | %s [ %d ] | %s | %s | %d\n", b.getBoardNo(), b.getBoardTitle(), b.getCommentCount(),
							b.getMemberName(), b.getCreateDate(), b.getReadCount());
				}
					
			}
			
			
			
		}catch(Exception e) {
			System.out.println("\n<<게시글 목록 조회 중 예외 발생>>\n");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 게시글 상세 조회 
	 */
	private void selectBoard() {
		System.out.println("\n[게시글 상세 조회]\n");
		
		try {
			System.out.print("게시글 번호 입력 : ");
			int boardNo=sc.nextInt();
			sc.nextLine();
			
			// 게시글 상세 조회 서비스 호출 후 결과 반환받기
			Board board=bService.selectBoard(boardNo, MainView.loginMember.getMemberNo());
								// 게시글 번호,
			
	         if (board != null) {
	             System.out.println("--------------------------------------------------------");
	             System.out.printf("글번호 : %d \n제목 : %s\n", board.getBoardNo(), board.getBoardTitle());
	             System.out.printf("작성자 : %s | 작성일 : %s  \n조회수 : %d\n", 
	                   board.getMemberName(), board.getCreateDate(), board.getReadCount());
	             System.out.println("--------------------------------------------------------\n");
	             System.out.println(board.getBoardContent());
	             System.out.println("\n--------------------------------------------------------");

	          
	             // 댓글 목록
	             if(!board.getCommentList().isEmpty()) {
	                for(Comment c : board.getCommentList()) {
	                   System.out.printf("댓글번호: %d   작성자: %s  작성일: %s\n%s\n",
	                         c.getCommentNo(), c.getMemberName(), c.getCreateDate(), c.getCommentContent());
	                   System.out.println(" --------------------------------------------------------");
	                }
	             }
	             
	             // 댓글 등록, 수정, 삭제
	             // 수정/삭제 메뉴
	             subBoardMenu(board);
	             
	             
	          } else {
	             System.out.println("[\n해당 번호의 게시글이 존재하지 않습니다.]\n");
	          }
	         
			
		}catch(Exception e) {
			System.out.println("\n<<게시글 상세 조회 중 예외 발생>>\n");
		}
	}
	
	
	
	/** 게시글 상세 조회시 출력되는 서브 메뉴 
	 * @param board(상세조회된 게시글 + 작성자 번호 + 댓글 목록)
	 */
	private void subBoardMenu(Board board) {
		
		// 댓글 등록 
		// 댓글 수정 
		// 댓글 삭제 
		
		
		
		try {
			System.out.println("1) 댓글 등록");
			System.out.println("2) 댓글 수정");
			System.out.println("3) 댓글 삭제");
			
			
			// 로그인한 회원과 게시글 작성자가 같은 경우에만 출력되는 메뉴 
			if(MainView.loginMember.getMemberNo()==board.getMemberNo()) {
				System.out.println("4) 게시글 수정");
				System.out.println("5) 게시글 삭제");
			}
			
			
			System.out.println("0) 게시판 메뉴로 돌아가기");
			
			System.out.print("\n서브 메뉴 선택 : ");
			int input=sc.nextInt();
			sc.nextLine();
			
			int memberNo=MainView.loginMember.getMemberNo();
			
			switch(input) {
			case 1: insertComment(board.getBoardNo(),memberNo); break;
			case 2: updateComment(board.getCommentList(), memberNo); break;
			case 3: deleteComment(board.getCommentList(), memberNo); break;
			case 0: System.out.println("\n[게시판 메뉴로 돌아갑니다...]\n");break;
			
			
			case 4: case 5:  // 4 또는 5 입력 시 
				
				// 4 또는 5를 입력한 회원이 게시글 작성자인 경우 
				if(MainView.loginMember.getMemberNo() == board.getMemberNo()) {
					if(input==4) {
						// 게시글 수정 
						
						updateBoard(board.getBoardNo());
					}
					
					if(input==5) {
						// 게시글 삭제 
						
						deleteBoard(board.getBoardNo());
					}
						
					break; // switch문 종료 
				}
				
			default: System.out.println("\n[메뉴에 작성된 번호만 입력해주세요]\n");
			}
			
			// 댓글 등록, 수정, 삭제 선택 시 
			// 각각의 서비스 메서드 종료 후 다시 서브메뉴 메서드 호출 
			if(input>0&&input<5) {
				try {
				
		               board = bService.selectBoard(board.getBoardNo(), MainView.loginMember.getMemberNo());
		   
		               System.out.println(" --------------------------------------------------------");
		               System.out.printf("글번호 : %d | 제목 : %s\n", board.getBoardNo(), board.getBoardTitle());
		               System.out.printf("작성자ID : %s | 작성일 : %s | 조회수 : %d\n", 
		                     board.getMemberName(), board.getCreateDate().toString(), board.getReadCount());
		               System.out.println(" --------------------------------------------------------");
		               System.out.println(board.getBoardContent());
		               System.out.println(" --------------------------------------------------------");
		   
		            
		               // 댓글 목록
		               if(!board.getCommentList().isEmpty()) {
		                  for(Comment c : board.getCommentList()) {
		                     System.out.printf("댓글번호: %d   작성자: %s  작성일: %s\n%s\n",
		                           c.getCommentNo(), c.getMemberName(), c.getCreateDate(), c.getCommentContent());
		                     System.out.println(" --------------------------------------------------------");
		                  }
		               }
					
		            }catch (Exception e) {
		               e.printStackTrace();
		            }
				
				subBoardMenu(board);
			}
			
			
		}catch(InputMismatchException e) {
			 System.out.println("\n<<입력 형식이 올바르지 않습니다.>>\n");
			 sc.nextLine();
		}
	}


	private void insertComment(int bNo, int mNo) {
		
		try {
			System.out.println("\n[댓글 등록]\n");
			
			// 내용 입력 받기 
			String content=inputContent();
			
			// DB INSERT시 필요한 하나의 값을 하나의 Comment 객체에 저장 
			Comment comment=new Comment();
			
			comment.setCommentContent(content);
			comment.setBoardNo(bNo);
			comment.setMemberNo(mNo);
			
			// 댓글 삽입 서비스 호출 후 결과 반환 받기 
			int result=cService.insertComment(comment);
			
			
			if(result>0) {
				System.out.println("\n[댓글 등록 성공]\n");
			}else {
				System.out.println("\n[댓글 등록 실패 ...]\n");
			}
			
		}catch(Exception e) {
			System.out.println("\n<<댓글 등록 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
	}

	/**
	 * 내용 입력 
	 *	@return content
	 */
	private String inputContent() {
		String content=""; // 빈문자열 
		
		String input=null; // 참조하는 객체가 없음 
		System.out.println("입력 종료 시 ($exit) 입력");
		while(true) {
			input=sc.nextLine();
			
			if(input.equals("$exit"))
				break;
			
			// 입력된 내용을 content에 누적 
			content+=input+"\n";
			
		}
		return content;
	}

	/** 댓글 수정 
	 * @param commentList
	 * @param memberNo
	 */
	private void updateComment(List<Comment> commentList, int memberNo) {
		
		// 댓글 번호를 입력 받아
		// 1) 해당 댓글이 commentList에 있는지 검사 
		// 2) 있다면 해당 댓글이 로그인한 회원이 작성한 글인지 검사 
		
		try {
			System.out.println("\n[댓글 수정]\n");
			System.out.print("수정할 댓글 번호 입력 : ");
			int commentNo=sc.nextInt();
			sc.nextLine();
			
			boolean flag=true;
			
			for(Comment c:commentList) {
				if(c.getCommentNo()==commentNo) { // 댓글 번호 일치 
					flag=false;
					if(c.getMemberNo()==memberNo) { // 회원번호 일치 
						
						// 수정할 댓글 내용 입력받기
						String content=inputContent();
						
						
						// 댓글 수정 서비스 호출 
						int result=cService.updateComment(commentNo, content);
						
						if(result>0)
							System.out.println("\n[댓글 수정 성공]\n");
						else 
							System.out.println("\n[댓글 수정 실패...]\n");
						
					}else {
						System.out.println("\n[자신의 댓글만 수정할 수 있습니다.]\n");
					}
					break;
				}
					
			} // for end 
			
			if(flag)
				System.out.println("\n[번호가 일치하는 댓글이 없습니다.]\n");
				
			
		}catch(Exception e) {
			System.out.println("\n<<댓글 수정 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
		
	}
	

	/** 댓글 삭제  
	 * @param commentList
	 * @param memberNo
	 */
	private void deleteComment(List<Comment> commentList, int memberNo) {
		
		// 댓글 번호를 입력 받아
		// 1) 해당 댓글이 commentList에 있는지 검사 
		// 2) 있다면 해당 댓글이 로그인한 회원이 작성한 글인지 검사 
		
		try {
			System.out.println("\n[댓글 삭제]\n");
			System.out.print("삭제할 댓글 번호 입력 : ");
			int commentNo=sc.nextInt();
			sc.nextLine();
			
			boolean flag=true;
			
			for(Comment c:commentList) {
				if(c.getCommentNo()==commentNo) { // 댓글 번호 일치 
					flag=false;
					if(c.getMemberNo()==memberNo) { // 회원번호 일치 
						
						// 정말 삭제? y/n
						// y인 경우 댓글 삭제 서비스 호출 
						
						
						System.out.print("정말 삭제하시겠습니까? (Y/N) : ");
						char ch=sc.next().toUpperCase().charAt(0);
						
						if(ch=='Y') {
							
							// 댓글 삭제 서비스 호출 
							int result=cService.deleteComment(commentNo);
							
							if(result>0)
								System.out.println("\n[댓글 삭제 성공]\n");
							else 
								System.out.println("\n[댓글 삭제 실패...]\n");
							
						}else {
							
							System.out.println("\n[취소되었습니다.]\n");
							
						}
						
						
					}else {
						System.out.println("\n[자신의 댓글만 삭제할 수 있습니다.]\n");
					}
					break;
				}
					
			} // for end 
			
			if(flag)
				System.out.println("\n[번호가 일치하는 댓글이 없습니다.]\n");
				
			
		}catch(Exception e) {
			System.out.println("\n<<댓글 삭제 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
		
	}

	
	/** 게시글 수정 
	 * @param boardNo
	 */
	private void updateBoard(int boardNo) {
		
		try {
			
			System.out.println("\n[게시글 수정]\n");
			
			System.out.print("수정할 제목 : ");
			String boardTitle=sc.next();
			
			System.out.print("수정할 내용 : ");
			String boardContent=inputContent();
			
			
			// 수정된 제목/내용 + 게시글 번호를 한번에 전달하기 위한 Board 객체 생성 
			
			Board board=new Board();
			board.setBoardNo(boardNo);
			board.setBoardTitle(boardTitle);
			board.setBoardContent(boardContent);
			
			int result = bService.updateBoard(board);
			
			if(result>0)
				System.out.println("\n[게시글 수정 성공]\n");
			else 
				System.out.println("\n[게시글 수정 실패...]\n");
			
		}catch(Exception e) {
			System.out.println("\n<<게시글 수정 중 예외 발생>>\n");
			e.printStackTrace();
		}
	}
	
	
	/** 게시글 삭제  
	 * @param boardNo
	 */
	private void deleteBoard(int boardNo) {
		
		try {
			
			System.out.println("\n[게시글 삭제]\n");
			
			
			System.out.print("정말 삭제하시겠습니까? (Y/N) : ");
			char ch=sc.next().toUpperCase().charAt(0);
			
			if(ch=='Y') {
				
				// 댓글 삭제 서비스 호출 
				int result=bService.deleteBoard(boardNo);
				
				if(result>0)
					System.out.println("\n[게시글 삭제 성공]\n");
				else 
					System.out.println("\n[게시글 삭제 실패...]\n");
				
			}else {
				
				System.out.println("\n[삭제 취소]\n");
				
			}
			
		}catch(Exception e) {
			System.out.println("\n<<게시글 삭제 중 예외 발생>>\n");
			e.printStackTrace();
		}
	}
	

	
}