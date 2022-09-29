package edu.kh.jdbc.game.player.vo;

public class Game {
	
	private String playerNm;
	private int playerNo;
	private String playtime;	// 게임 초 
	private	String playDate;
	
	
	public Game() {}
	
	
	
	public Game(String playerNm, String playtime, String playDate) {
		super();
		this.playerNm = playerNm;
		this.playtime = playtime;
		this.playDate = playDate;
	}



	public Game(int playerNo, String playerNm, String playtime) {
		super();
		this.playerNm = playerNm;
		this.playerNo = playerNo;
		this.playtime = playtime;
		
	}
	public String getPlayerNm() {
		return playerNm;
	}
	public void setPlayerNm(String playerNm) {
		this.playerNm = playerNm;
	}
	public int getPlayerNo() {
		return playerNo;
	}
	public void setPlayerNo(int playerNo) {
		this.playerNo = playerNo;
	}
	public String getPlaytime() {
		return playtime;
	}
	public void setPlaytime(String playtime) {
		this.playtime = playtime;
	}
	public String getPlayDate() {
		return playDate;
	}
	public void setPlayDate(String playDate) {
		this.playDate = playDate;
	}
}
