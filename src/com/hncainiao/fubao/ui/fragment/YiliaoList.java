package com.hncainiao.fubao.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.hncainiao.fubao.R;
import com.hncainiao.fubao.net.ZBapi;
import com.hncainiao.fubao.ui.activity.around.YaoDianMessage;
import com.hncainiao.fubao.ui.adapter.YaodianAdapter;
import com.jmheart.net.NetworkUtil;
import com.jmheart.view.listview.RefreshListView;
import com.jmheart.view.listview.RefreshListView.OnLoadListener;
import com.jmheart.view.listview.RefreshListView.OnRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;


public class YiliaoList extends com.hncainiao.fubao.ui.fragment.BaseFragment 
implements OnRefreshListener,OnLoadListener {
		/**
		 * 药店
		 * 
		 * */
		
		private View view;
		YaodianAdapter adapter;
		Context mContext;
		RefreshListView listView;
		List<Map<String, Object>>mList = new ArrayList<Map<String, Object>>(); ;
		 int page=1;
		
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			view = inflater.inflate(R.layout.yaodian, null);
			mContext=getActivity();
			Initvieew();
			GetData();
			return view;
		}


		private void GetData() {
			
			if (NetworkUtil.isOnline(getActivity())) {
				 Showloading();
				 mList.clear();
				 page=1;
				
				 ZBapi.getNearbyList("2",page+"",AmbServicesFragment.lat, AmbServicesFragment.Long, "", AmbServicesFragment.strdistace, mediheadl);
				
			}else {
				showToast("没有网络连接");
			}
					
		}

		private void getMoreDate() {
			
			if (NetworkUtil.isOnline(getActivity())) {
				 Showloading();
				 mList.clear();
				 page++;
				 ZBapi.getNearbyList("2",page+"",AmbServicesFragment.lat, AmbServicesFragment.Long, "", AmbServicesFragment.strdistace, mediheadl);
				
			}else {
				showToast("没有网络连接");
			}
					
		}
		AsyncHttpResponseHandler mediheadl=new AsyncHttpResponseHandler()
		{
			public void onSuccess(String content) {
				Dissloading();
				try {
					JSONObject jsonObject=new JSONObject(content);
					if (jsonObject.getInt("err")==0) {
						JSONArray jsonArray =jsonObject.getJSONArray("ret");
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObject2=jsonArray.getJSONObject(i);
							Map<String, Object> map=new HashMap<String, Object>();
								map.put("name", jsonObject2.getString("name"));
								map.put("img",  jsonObject2.getString("img"));
								map.put("address",jsonObject2.getString("address"));
								map.put("distance", Float.parseFloat(jsonObject2.getString("distance"))/1000+"km");
								mList.add(map);
								
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				adapter=new YaodianAdapter(mContext) ;
				adapter.setList(mList);
				listView.onRefreshComplete();
				listView.onLoadComplete();
				listView.setResultSize(mList.size());
				listView.setAdapter(adapter);
			};
			public void onFailure(Throwable error) {
				Dissloading();
				showToast("请求失败");
			};
		};

		private void Initvieew() {
			
			listView=(RefreshListView) view.findViewById(R.id.list);
			listView.setOnRefreshListener(this);
			listView.setOnLoadListener(this);
			listView.setSelector(R.drawable.cannel_liseviewback);//去除Listview點擊背景
			listView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					Intent intent=new Intent(mContext,YaoDianMessage.class);
					startActivity(intent);
					
				}
				
			});

			
		}


		@Override
		protected void creatFragmentUI() {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void onLoad() {
			// TODO Auto-generated method stub
			//加载更多
			getMoreDate();
		}


		@Override
		public void onRefresh() {
			// TODO Auto-generated method stub
			//刷新数据
			GetData();
		}
		
		
		
		

	}

