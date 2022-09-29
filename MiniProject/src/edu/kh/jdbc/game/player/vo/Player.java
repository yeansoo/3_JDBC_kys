package edu.kh.jdbc.game.player.vo;

public class Player {
	private int playerNo;
	private String playerId;
	private String playerPw;
	private String playerName; 		// 플레이어 이름 
	private String enrollDate;
	private String secessionFlag;
	
	public Player() {}
	
	public Player(String playerId, String playerPw, String playerName) {
		this.playerId=playerId;
		this.playerPw=playerPw;
		this.playerName=playerName;
	}
	
	public int getPlayerNo() {
		return playerNo;
	}
	
	public void setPlayerNo(int playerNo) {
		this.playerNo=playerNo;
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
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getEnrollDate() {
		return enrollDate;
	}
	public void setEnrollDate(String enrollDate) {
		this.enrollDate = enrollDate;
	}
	public String getSecessionFlag() {
		return secessionFlag;
	}
	public void setSecessionFlag(String secessionFlag) {
		this.secessionFlag = secessionFlag;
	}
	
	
	

	

	

}
