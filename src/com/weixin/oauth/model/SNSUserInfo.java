package com.weixin.oauth.model;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import cn.myapp.model.DaoObject;

/**
 * 通过网页授权获取用户信息
 * 
 * @author Administrator
 */
@SuppressWarnings("serial")
public class SNSUserInfo extends DaoObject {
	// id pk 
	private int userID ;
	// 用户标识
	private String openid;
	// 用户昵称
	private String nickname;
	// 性别（1是男性，2是女性，0是未知）
	private int sex;
	// 国家
	private String country;
	// 省份
	private String province;
	// 城市
	private String city;
	// 用户头像链接
	private String headimgurl;
	// 用户特权信息
//	private List<String> privilegeList;
	// 唯一id
	private String unionid ;
	
	public int getUserID() {
		return userID ;
	}
	
	public void setUserID(int userID) {
		this.userID = userID ;
	}
	
	public String getOpenid() {
		return openid;
	}

	public void setOpenId(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	
	public String getUnionid() {
		return unionid ;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid ;
	}
	
//	public List<String> getPrivilegeList() {
//		return privilegeList;
//	}
//	
//	public void setPrivilegeList(List<String> privilegeList) {
//		this.privilegeList = privilegeList;
//	}
	
	////////////////////////////////////////////////////////////////////////////////////////
	
	public static SNSUserInfo selectByOpenId(String openIdStr) {
		Record record = Db.findById("user", "openid", openIdStr) ;
		if (record != null) {
			return (SNSUserInfo)new SNSUserInfo().fetchFromRecord(record) ;
		}
		return null ;
	}
	
	
}
