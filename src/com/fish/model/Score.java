package com.fish.model;
import java.util.Date;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.myapp.model.DaoObject;


@SuppressWarnings("serial")
public class Score extends DaoObject {
	
	private int	 	scoreID ;
	private int	 	score ;
	private int 	gold ;
	private int 	normal ;
	private Date	createtime ;
	private int 	userID ;
	
	public int getScoreID() {
		return scoreID;
	}
	public void setScoreID(int scoreID) {
		this.scoreID = scoreID;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public int getNormal() {
		return normal;
	}
	public void setNormal(int normal) {
		this.normal = normal;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Score(int score, int gold, int normal, int userID, Date createtime) {
		// TODO Auto-generated constructor stub
		this.score = score ;
		this.gold = gold ;
		this.normal = normal ;
		this.userID = userID ;
		this.createtime = createtime ;
	}
	
	public Score() {
		// TODO Auto-generated constructor stub			
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Score selectByOpenId(int scoreID) {
		Record record = Db.findById("score", "scoreID", scoreID) ;
		if (record != null) {
			return (Score)new Score().fetchFromRecord(record) ;				
		}
		return null ;
	}
	
	public static int sumOfUsers() {
		int sum = Db.queryInt("select max(scoreID) from score") ;
		return sum ;
	}
	
	public static java.util.List<Record> selectOrderByScoreDesc() {
		java.util.List<Record> listRecord = Db.find("SELECT scoreID FROM score ORDER BY score DESC ,createtime DESC ;") ;
		return listRecord ;
	}
	
}
