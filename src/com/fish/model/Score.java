package com.fish.model;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.myapp.model.DaoObject;


@SuppressWarnings("serial")
public class Score extends DaoObject {
	
//	private static final String sql_selectScoreLeftjoinUser = "SELECT activity.score.score , activity.user.nickname , activity.user.headimgurl , activity.user.sex FROM activity.score LEFT JOIN activity.user ON activity.user.userID=activity.score.userID ORDER BY activity.score.score DESC , activity.score.createtime DESC LIMIT ? ;" ;
	private static final String sql_selectScoreLeftjoinUser = "SELECT MAX(score) AS sc,user.nickname,user.headimgurl,user.sex FROM activity.score LEFT JOIN activity.user ON score.userID=user.userID GROUP BY activity.score.userID ORDER BY sc DESC , createtime asc LIMIT ? ;" ; 
			
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
	
	public static Long sumOfUsers() {
		Long sum = Db.queryLong("SELECT count(*) FROM activity.score ;") ;
		return sum ;
	}
	
	public static Long selectOverCount(int currentScore) {
		Long num = Db.queryLong("SELECT count(*) FROM activity.score where score < ? ;",currentScore) ;
		return num ;
	}
	
	public static Long selectRank(int currentScore) {
		Long num = Db.queryLong("SELECT count(*) FROM activity.score where score > ? ;",currentScore) ; 
		return num ;
	}
	
	public static Integer selectMaxScore(int userID) {
		Integer maxScore = Db.queryInt("select max(score) as sc from score where userID = ? ;",userID) ;
		return maxScore ;
	}
	
	public static Long selectMaxRank(int userID) {
		Long maxRank = Db.queryDouble("select rownum from (SELECT @rownum:=@rownum+1 rownum, sc, createtime , userID From (SELECT max(score) as sc, userID, createtime , @rownum:=0 FROM score group by userID order by sc desc, createtime asc ) aa ) bb where userID = ?"
				,userID).longValue() ;
		return maxRank ;
	}
	
	
	/**
	 * select List Desc line With Number
	 * @param num
	 * @return [ {score, nickname, headimgurl , sex} , ... ]
	 */
	public static ArrayList<Map<String, Object>> selectListDesclineWithNumber(int num) {
		
		ArrayList<Map<String, Object>> statList = new ArrayList<>() ;
		
		List<Record> list = Db.find(sql_selectScoreLeftjoinUser , num) ;		
		
		for (Record record : list) {			
			int score = record.getInt("sc").intValue() ;
			String nickname = record.getStr("nickname") ;
			String headimgurl = record.getStr("headimgurl") ;
			int sex = record.getInt("sex").intValue() ;
			
			HashMap<String, Object> map = new HashMap<>() ;
			map.put("score", score) ;
			map.put("nickname", nickname) ;
			map.put("headimgurl", headimgurl) ;
			map.put("sex", sex) ;
			
			statList.add(map) ;
		}	
		return statList ;
	}
	
}
