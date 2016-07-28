package cn.myapp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fish.model.Score;
import com.jfinal.core.Controller;
import com.weixin.oauth.model.SNSUserInfo;

import cn.myapp.model.ResultObj;

public class StatController extends Controller {
	
	/**
	 * @param gold		count of golden
	 * @param normal	count of normal 
	 * @param score
	 * @param userID
	 * 
	 * @return display string .
	 */
	public void result() {
		
		int gold = getParaToInt("gold") ; 				
		int normal = getParaToInt("normal") ;
		int score = getParaToInt("score") ;
		String openid = getPara("openid") ;
		
		SNSUserInfo userInfo = SNSUserInfo.selectByOpenId(openid) ;
		int userID = userInfo.getUserID() ;
		
		// insert .
		Date createtime = new Date() ;
		Score scoreObj = new Score(score, gold, normal, userID, createtime) ;
		long scoreID = scoreObj.daoInsert("score", "scoreID") ; // get scoreID .
		
		// success .
		if (scoreID > 0) {
			// game info .
			long over = Score.selectOverCount(score).longValue() ;
			long sumNum = Score.sumOfUsers().longValue() - 1 ;
			float percentage = (float)over / (float)sumNum ;			
			
			int maxScore = Score.selectMaxScore(userID).intValue() ;
			long maxRank = Score.selectMaxRank(userID).longValue() ;
			
			System.out.println("over : " + over ) ;
			System.out.println("all : " + sumNum) ;
			System.out.println("percent : " + percentage ) ;			
			System.out.println("maxscore : " + maxScore +"\nmaxrank : " + maxRank) ; 
			
			// ranking list .
			ArrayList<Map<String, Object>> rankingList = Score.selectListDesclineWithNumber(5) ;			
			
			// return data 
			HashMap<String, Object> map = new HashMap<>() ;
			map.put("over", over) ;
			
			// ADD BY TEASON 20160723 BEGIIN
			sumNum += 15000 ;
			// ADD BY TEASON 20160723 END
			
			map.put("all", sumNum) ;
			map.put("percent", percentage) ;
			map.put("list", rankingList) ;			
//			map.put("ranking", ranking) ;
			map.put("maxScore", maxScore) ;
			map.put("maxRank", maxRank) ;
			
			map.put("nickname", userInfo.getNickname()) ;
			map.put("headimgurl", userInfo.getHeadimgurl()) ;
			
			ResultObj resultObj = new ResultObj(map) ;
			renderJson(resultObj) ;
		}
		// failure .
		else {
			ResultObj resultObj = new ResultObj("0", "error : 插入失败", null) ;
			renderJson(resultObj);
		}				
	}
	
}
