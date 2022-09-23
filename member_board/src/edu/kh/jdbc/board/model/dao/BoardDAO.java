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
}
