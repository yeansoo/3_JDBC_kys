package edu.kh.jdbc.game.service;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.game.dao.GameDAO;
import edu.kh.jdbc.game.player.vo.Game;
import edu.kh.jdbc.game.player.vo.Player;

public class GameService {
	
	GameDAO dao=new GameDAO();

	public int idDupCheck(String playerId) throws Exception {
		Connection conn=getConnection();
		
		int result=dao.inDupCheck(conn, playerId);
		
		close(conn);
		
		return result;
	}

	/** 비회원 게임 
	 * @param game
	 * @throws Exception
	 */
	public void playGame(Game game) throws Exception{
		Connection conn=getConnection();
		
		int result=dao.playGame(conn, game);
		
		if(result>0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
	
	}

	public List<Game> ranking() throws Exception{
		Connection conn=getConnection();
		
		List<Game> listGame=dao.ranking(conn);
		
		close(conn);
		
		return listGame;
	}

	public int signIn(Player player) throws Exception{
		
		Connection conn=getConnection();
		
		int result=dao.signIn(conn, player);
		
		if(result>0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}

	public Player login(String playerId, String playerPw) throws Exception {
		
		Connection conn=getConnection(); 
		
		Player player=dao.logIn(conn, playerId, playerPw);
		
		close(conn);
		
		return player;
	}

	public int nameDupCheck(String oncePlayer) throws Exception {
		Connection conn=getConnection();
		
		int result=dao.nameDupCheck(conn, oncePlayer);
		
		close(conn);
		
		return result;
	}

	public List<Game> myRecord(String playerName) throws Exception {
		Connection conn=getConnection();
		
		List<Game> listGame=dao.myRecord(conn, playerName);
		
		close(conn);
		
		return listGame;
	}

	public String avgRecord(String playerName) throws Exception {
		Connection conn=getConnection();
		
		String avgRecord=dao.avgRecord(conn, playerName);
		
		close(conn);
		
		return avgRecord;
	}

}
