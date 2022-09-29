package edu.kh.jdbc.board.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.board.model.vo.Board;

public class BoardDAO {

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	public BoardDAO() {
		try {
			
			prop=new Properties();
			prop.loadFromXML(new FileInputStream("board-query.xml"));
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/** 게시글 목록 조회 DAO 
	 * @param conn
	 * @return boardList
	 * @throws Exception
	 */
	public List<Board> selectAllBoard(Connection conn) throws Exception {
		// 결과 저장용 변수 
		List<Board> boardList=null;
		
		Board board;
		
		try {
			String sql=prop.getProperty("selectAllBoard");
			
			stmt=conn.createStatement();
			
			rs=stmt.executeQuery(sql);
			boardList=new ArrayList<>();
			
			while(rs.next()) {
							
				int boardNo=rs.getInt("BOARD_NO");
				String boardTitle=rs.getString("BOARD_TITLE");
				String memberName=rs.getString("MEMBER_NM");
				int readCount=rs.getInt("READ_COUNT");
				String createDt=rs.getString("CREATE_DT");
				int commentCount=rs.getInt("COMMENT_COUNT");
				
				board=new Board();
				
				board.setBoardNo(boardNo);
				board.setBoardTitle(boardTitle);
				board.setMemberName(memberName);
				board.setReadCount(readCount);
				board.setCreateDate(createDt);
				board.setCommentCount(commentCount);
				
				
				boardList.add(board);
			}
			
			
		}finally {
			close(rs);
			close(stmt);
		}
		
		return boardList;
	}

	
	
	
	/**
	 * @param conn
	 * @param boardNo
	 * @return
	 * @throws Exception
	 */
	public Board selectBoard(Connection conn, int boardNo) throws Exception{
		
		Board board=null;
		
		try {
			String sql=prop.getProperty("selectBoard");
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1,  boardNo);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				board= new Board();
				
				board.setBoardNo(rs.getInt("BOARD_NO"));
				board.setBoardTitle(rs.getString("BOARD_TITLE"));
				board.setBoardContent(rs.getString("BOARD_CONTENT"));
				board.setMemberNo(rs.getInt("MEMBER_NO"));
				board.setMemberName(rs.getString("MEMBER_NM"));
				board.setReadCount(rs.getInt("READ_COUNT"));
				board.setCreateDate(rs.getString("CREATE_DT"));
				
			}
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return board;
	}
	
	
	

	public int increaseReadCount(Connection conn, int boardNo) throws Exception {
		int result=0;
		
		try {
			
			String sql=prop.getProperty("increaseReadCount");
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, boardNo);
			
			result=pstmt.executeUpdate();
			System.out.println("나와");
			
		}finally {
			
			close(pstmt);
		}
		
		return result;
	}

	public int updateBoard(Connection conn, Board board) throws Exception {
		int result=0;
		
		try {
			
			String sql=prop.getProperty("updateBoard");
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, board.getBoardTitle());
			pstmt.setString(2, board.getBoardContent());
			pstmt.setInt(3, board.getBoardNo());
			
			result=pstmt.executeUpdate();
			
		}finally {
			
			close(pstmt);
		}
		
		return result;
	}

	public int deleteBoard(Connection conn, int boardNo) throws Exception{
		int result=0;
		
		try {
			
			String sql=prop.getProperty("deleteBoard");
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, boardNo);
			
			result=pstmt.executeUpdate();
			
		}finally {
			
			close(pstmt);
		}
		
		return result;
	}

	/** 게시글 등록 DAO
	 * @param conn
	 * @param board
	 * @return
	 * @throws Exception
	 */
	public int insertBoard(Connection conn, Board board) throws Exception {
		
		int result=0;
		
		
		try {
			String sql=prop.getProperty("insertBoard");
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, board.getBoardNo());
			pstmt.setString(2, board.getBoardTitle());
			pstmt.setString(3, board.getBoardContent());
			pstmt.setInt(4, board.getMemberNo());
			
			result=pstmt.executeUpdate();
			
			
			
		}finally {
			close(pstmt);
		}
		
		
		return result;
	}

	/** 다음 게시글 번호 생성 DAO
	 * @param conn
	 * @return
	 */
	public int nextBoardNo(Connection conn) throws Exception{
		
		int boardNo=0;
		
		try {
			String sql=prop.getProperty("nextBoardNo");
			
			pstmt=conn.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				boardNo=rs.getInt(1);// 첫번째 컬럼 값을 얻어와 boardNo에 저장 
			}
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return boardNo;
	}

	/** 게시글 검색 DAO
	 * @param conn
	 * @param condition
	 * @param query
	 * @return boardList
	 * @throws Exception
	 */
	public List<Board> searchBoard(Connection conn, int condition, String query) throws Exception {
		
		List<Board> boardList=new ArrayList<>();
		
		try {
			String sql=prop.getProperty("searchBoard1")
					+prop.getProperty("searchBoard2_"+condition)
					+prop.getProperty("searchBoard3");
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, query);
			// 3번(제목+내용)은 ?가 2개 존재하기 때문에 추가 세팅 구문 작성
			if(condition==3)pstmt.setString(2, query);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				
				int boardNo=rs.getInt("BOARD_NO");
				String boardTitle=rs.getString("BOARD_TITLE");
				String memberName=rs.getString("MEMBER_NM");
				int readCount=rs.getInt("READ_COUNT");
				String createDt=rs.getString("CREATE_DT");
				int commentCount=rs.getInt("COMMENT_COUNT");
				
				Board board=new Board();
				
				board.setBoardNo(boardNo);
				board.setBoardTitle(boardTitle);
				board.setMemberName(memberName);
				board.setReadCount(readCount);
				board.setCreateDate(createDt);
				board.setCommentCount(commentCount);
				
				
				boardList.add(board);
			}
		}finally {
			close(rs);
			close(pstmt);
		}
		
		
		return boardList;
	}
}
