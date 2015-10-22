package com.hncainiao.fubao.application;

import com.hncainiao.fubao.properties.SharedPreferencesConfig;

public class IsLogin {
	
	public static String member_id;
	
	/**
	 * @return
	 * 登录判断
	 */
	public static boolean isLogin(){
		member_id=SharedPreferencesConfig.getStringConfig(FuBaoApplication.getInstance(), "member_id");
		
		if(member_id!=""&&member_id!=null){
			return true;
		}else{
			return false;

		}
		
	}
	

}
