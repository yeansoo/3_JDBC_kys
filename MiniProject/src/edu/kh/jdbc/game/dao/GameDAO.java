package edu.kh.jdbc.game.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.game.player.vo.Game;
import edu.kh.jdbc.game.player.vo.Player;

public class GameDAO {
	
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	public GameDAO() {
		try{
			prop=new Properties();
			prop.loadFromXML(new FileInputStream("player-query.xml"));
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public int playGame(Connection conn, Game game) throws Exception {
		
		int result=0;
		try {
			String sql=prop.getProperty("insertGame");
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, game.getPlayerNm());
			pstmt.setString(2, game.getPlaytime());
			
			result=pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public List<Game> ranking(Connection conn) throws Exception{
		List<Game> listGame=new ArrayList<>();
		
		try {
			String sql=prop.getProperty("ranking");
			
			stmt=conn.createStatement();
			
			rs=stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				int playerNo=rs.getInt(1);
				String playerNm=rs.getString(2);
				String playTime=rs.getString(3);
				
				
				listGame.add(new Game(playerNo,playerNm, playTime));
			}
		}finally {
			close(rs);
			close(stmt);
		}
		
		return listGame;
	}
	
	
	public int inDupCheck(Connection conn, String playerId) throws Exception{
		int result=0;
		
		try {
			String sql=prop.getProperty("idDupCheck");
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, playerId);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				result=rs.getInt(1);
			}
			
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return result;
	}

	public int signIn(Connection conn, Player player) throws Exception {
int result=0;
		
		try {
			String sql=prop.getProperty("signIn");
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(2, player.getPlayerId());
			pstmt.setString(3, player.getPlayerPw());
			pstmt.setString(1, player.getPlayerName());
			
			result=pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
			
		}
		
		
		return result;
	}

	public Player logIn(Connection conn, String playerId, String playerPw) throws Exception {
		
		Player loginPlayer=null;
		
		try {
			
			String sql=prop.getProperty("login");
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, playerId);
			pstmt.setString(2, playerPw);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				loginPlayer=new Player();
				
				loginPlayer.setPlayerNo(rs.getInt("PLAYER_NO"));
				loginPlayer.setPlayerId(playerId);
				loginPlayer.setPlayerName(rs.getString("PLAYER_NAME"));
				loginPlayer.setEnrollDate("ENROLL_DATE");
			}
			
		}finally {
			close(rs);
			close(pstmt);
		}
		return loginPlayer;
	}

	public int nameDupCheck(Connection conn, String oncePlayer) throws Exception {
		int result=0;
		
		try {
			String sql=prop.getProperty("nameDupCheck");
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, oncePlayer);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				result=rs.getInt(1);
			}
			
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return result;
	}

	public List<Game> myRecord(Connection conn,String playerName) throws Exception {
		List<Game> listGame=new ArrayList<>();
		
		try {
			String sql=prop.getProperty("myRecord");
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, playerName);
			
			rs=pstmt.executeQuery();
			
			
			while(rs.next()) {
				
				
				String playerNm=rs.getString(1);
				String playTime=rs.getString(2);
				String playDate=rs.getString(3);
				
				
				listGame.add(new Game(playerNm, playTime, playDate));
			}
		}finally {
			close(rs);
			close(stmt);
		}
		
		return listGame;
	}

	public String avgRecord(Connection conn, String playerName) throws Exception {
		String avgRecord=null;
		
		try {
			String sql=prop.getProperty("avgRecord");
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, playerName);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				avgRecord=rs.getString("AVG_TIME");
			}
			
		}finally {
			
		}
		
		return avgRecord;
	}

	
}
