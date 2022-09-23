package edu.kh.jdbc.game.player.vo;

public class Player {
	private String playerName; 		// 플레이어 이름 
	
	private String playerId;
	private String playerPw;
	
	private double playtime;	// 게임 초 
	private int gamecount;		// 게임 플레이 횟수 
	
	
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	public String getPlayerPw() {
		return playerPw;
	}
	public void setPlayerPw(String playerPw) {
		this.playerPw = playerPw;
	}
	public double getPlaytime() {
		return playtime;
	}
	public void setPlaytime(double playtime) {
		this.playtime = playtime;
	}
	public int getGamecount() {
		return gamecount;
	}
	public void setGamecount(int gamecount) {
		this.gamecount = gamecount;
	}
}
