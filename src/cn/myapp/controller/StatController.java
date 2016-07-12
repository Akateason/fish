package cn.myapp.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fish.model.Score;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

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
		int userID = getParaToInt("userID") ;
		
		// insert .
		Date createtime = new Date() ;
		Score scoreObj = new Score(score, gold, normal, userID, createtime) ;
		long scoreID = scoreObj.daoInsert("score", "scoreID") ; // get scoreID .
		//System.out.println("scoreID : " + scoreID) ;
		
		// select . order by score desc .
		int row = - 1 ;
		List<Record> list = Score.selectOrderByScoreDesc() ;
		for (int i = 0; i < list.size(); i++) {
			Record record = list.get(i) ;
			if (record != null) {
				int tmpScoreID = record.getInt("scoreID") ;
				if (tmpScoreID == scoreID) {
					row = i ;
					break ;
				}
			}
		}
		
		// success .
		if (row != -1) {
			int sumNum = Score.sumOfUsers() ;
			float percentage = (float)row / (float)sumNum ;
			System.out.println("over : " + row ) ;
			System.out.println("all : " + sumNum) ;
			System.out.println("percent : " + percentage ) ;
			
			HashMap<String, Object> map = new HashMap<>() ;
			map.put("over", row) ;
			map.put("all", sumNum) ;
			map.put("percent", percentage) ;
			ResultObj resultObj = new ResultObj(map) ;
			renderJson(resultObj);
		}
		// failure .
		else {
			ResultObj resultObj = new ResultObj("0", "error : 未获得数据", null) ;
			renderJson(resultObj);
		}				
	}
	
}
