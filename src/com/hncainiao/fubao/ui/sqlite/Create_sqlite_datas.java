package com.hncainiao.fubao.ui.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Create_sqlite_datas extends SQLiteOpenHelper {

	public Create_sqlite_datas(Context context) {
		super(context, "City.db", null, 1);
	}
	@Override
	public void onCreate(SQLiteDatabase arg0) {
    //所有城市数据库
	arg0.execSQL("create table Citys (_id integer not null primary key, citynm varchar(50));");
	//搜索记录数据库
	arg0.execSQL("create table Jilu (_id integer not null primary key, citynm varchar(50),onclick int(6));");
    //热门城市列表
	arg0.execSQL("create table hotcity (_id integer not null primary key,citynm varchar(50));");
	//上一次浏览的医院记录
	arg0.execSQL("create table hospital (_id integer not null primary key, hospital_nm varchar(50));");
	}
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
