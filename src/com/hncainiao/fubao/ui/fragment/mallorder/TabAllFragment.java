package com.hncainiao.fubao.ui.fragment.mallorder;
	import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import android.widget.ListView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.net.ShopApi;
import com.hncainiao.fubao.ui.adapter.base.CommonAdapter;
import com.hncainiao.fubao.ui.adapter.base.ViewHolder;
import com.hncainiao.fubao.ui.fragment.mallorder.bean.GoodsBean;
import com.hncainiao.fubao.ui.fragment.mallorder.bean.TabAllBean;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.jmheart.net.JsonUtil;
import com.jmheart.tools.StringUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

	public class TabAllFragment extends com.hncainiao.fubao.ui.fragment.BaseFragment {


		Context mContext;
		View FragmetView;
		ListView list;
		List<TabAllBean> listDate=new ArrayList<TabAllBean>();
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			mContext=getActivity();
			FragmetView=inflater.inflate(R.layout.fragment_tab_all, null);
			inintView();
			loadDate();
			return FragmetView;
		}
		/**
		 * 控件初始化
		 */
		private void inintView()
		{
			
			list=(ListView)FragmetView.findViewById(R.id.fragment_all_list);
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(getActivity(),MallOrderInfoActivity.class);
					intent.putExtra("listdate", (Serializable)listDate.get(arg2));
					startActivityForResult(intent, 1);
				}
			});
		}
		/**
		 * 加载数据
		 */
		private void loadDate()
		{
			
			 if (NetworkUtil.isOnline(getActivity())) {
				Showloading();
				ShopApi.getStatusOrder("2,3,4",statuorderheand);
			}
			 else
			 {
				 showToast("无网络连接");
			 }
			
		}
		AsyncHttpResponseHandler statuorderheand=new AsyncHttpResponseHandler()
		{
			public void onSuccess(String content) {
				Dissloading();
				try {
					JSONObject jsonObject =new JSONObject(content);
					if (jsonObject.getInt("err")==0) {
						 try {
							
							 listDate=JsonUtil.parserJsonToList(TabAllBean.class, jsonObject.getJSONArray("order"));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						list.setAdapter(new CommonAdapter<TabAllBean>(getActivity(),listDate,R.layout.fragment_all_list_itme) {
							@Override
							public void convert(ViewHolder helper, TabAllBean item,int pos) {
								// TODO Auto-generated method stub
								List<GoodsBean> goodslistBeans = null;
								try {
									goodslistBeans = JsonUtil.parserJsonToList(GoodsBean.class, item.getOrder_detail());
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								//showLog(goodslistBeans.toString()+""+goodslistBeans.get(0).toString()+""+goodslistBeans.get(0).getProduct_name());
								helper.setText(R.id.title, goodslistBeans.get(0).getProduct_name()+"");
								helper.setText(R.id.tv_time,StringUtil.getaStrTime(item.getOrder_time())+"");
								helper.setText(R.id.tv_price, "总计："+item.getTotal_fee()+"元");
								if (item.getStatus().equals("2")) {
									helper.setText(R.id.tv_state, "已付款待发货");
								}
								else if(item.getStatus().equals("3")){
									helper.setText(R.id.tv_state, "已发货待收货");
								}
								else
								{
									helper.setText(R.id.tv_state, "已收货待评价");
								}
							
								
							}

						
							});
					}
					else {
						//showToast(jsonObject.getString("msg"));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
			public void onFailure(Throwable error) {
				showToast("请求失败");
				Dissloading();
			};
		};
		@Override
		protected void creatFragmentUI() {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			  loadDate();
		}
	}




