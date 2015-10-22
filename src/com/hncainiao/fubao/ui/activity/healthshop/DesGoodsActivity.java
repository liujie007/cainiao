package com.hncainiao.fubao.ui.activity.healthshop;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.edmodo.rangebar.RangeBar;
import com.edmodo.rangebar.RangeBar.OnRangeBarChangeListener;
import com.hncainiao.fubao.R;
import com.hncainiao.fubao.net.ShopApi;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.healthshop.bean.TypeBean;
import com.hncainiao.fubao.ui.adapter.ChoseGridViewAdapter;
import com.hncainiao.fubao.ui.adapter.ChosePpGridViewAdapter;
import com.hncainiao.fubao.ui.views.MyGridView;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-7-13上午11:41:44
 *  商城
 */
public class DesGoodsActivity extends BaseActivity {

	MyGridView gridPpView,gridTypeView;
	List<TypeBean> listPp=new ArrayList<TypeBean>();
	List<TypeBean> listType =new ArrayList<TypeBean>();
	List<TypeBean> listType2 =new ArrayList<TypeBean>();
	List<TypeBean> listType3 =new ArrayList<TypeBean>();
	String [] price={"0","50","200","400","800","1500","100000"};
	Button btnType,btnType2,btnFinsh;
	 ChosePpGridViewAdapter adapterppAdapter;
	 ChoseGridViewAdapter adaptertypeAdapter;
	 CheckBox chpP,chlBox;
	 int xprive=0,yprice=6;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_des_goods);
		setTitle("精准筛选");
		inintDate();
		inintView();
		
	}
	
	/**
	 * 初始化数据
	 */
	private void inintDate()
	{ 
	
		if (NetworkUtil.isOnline(this)) {
			Showloading();
			 ShopApi.getFilterInfo(getFilterInfoHander);
		}
		else
		{
			showToastNotNet();
		}
	}
	AsyncHttpResponseHandler getFilterInfoHander=new AsyncHttpResponseHandler()
	{
		@Override
		public void onSuccess(String content) {
			Dissloading();
			try {
				JSONObject jsonObject =new JSONObject(content);
				if (jsonObject.getInt("err")==0) {
					//解析数据
					JSONObject jsonObject2=jsonObject.getJSONObject("return");
					//家用医疗器械
					JSONArray arrayppArray=jsonObject2.getJSONArray("brand");
					for (int i = 0; i < arrayppArray.length(); i++) {
						TypeBean ppBean=new TypeBean();
						ppBean.setId(arrayppArray.getJSONObject(i).getString("id"));
						ppBean.setName(arrayppArray.getJSONObject(i).getString("name"));
						listPp.add(ppBean);
					}
					JSONArray jsonArray=jsonObject2.getJSONArray("category").getJSONObject(0).getJSONArray("child");
					//营养保健品
					 for (int i = 0; i < jsonArray.length(); i++) {
						 
						    TypeBean typeBean =new TypeBean();
						    typeBean.setId(jsonArray.getJSONObject(i).getString("id"));
							
							typeBean.setName(jsonArray.getJSONObject(i).getString("name"));
							listType.add(typeBean);
					}
					JSONArray jsonArray2= jsonObject2.getJSONArray("category").getJSONObject(1).getJSONArray("child");
						for (int j = 0; j < jsonArray2.length(); j++) {
							
							TypeBean typeBean =new TypeBean();
							typeBean.setId(jsonArray2.getJSONObject(j).getString("id"));
								
							typeBean.setName(jsonArray2.getJSONObject(j).getString("name"));
							listType2.add(typeBean);
						}
						gridPpView.setAdapter(adapterppAdapter=new ChosePpGridViewAdapter(listPp, DesGoodsActivity.this));	
						listType3.clear();
						listType3.addAll(listType);
						gridTypeView.setAdapter(adaptertypeAdapter=new ChoseGridViewAdapter(listType3, DesGoodsActivity.this));
				}
				else {
					showToast(jsonObject.getString("msg"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		@Override
		public void onFailure(Throwable error) {
			Dissloading();
			showLog("请求失败");
		};
	};
	/**
	 * 控件初始化
	 */
	private void inintView()
	{
		RangeBar rangebar = (RangeBar) findViewById(R.id.rangebar);
		rangebar.setOnRangeBarChangeListener(new MyRangBarListener());
		chpP=getView(R.id.ch_pp);
		chpP.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				adapterppAdapter.lIntegers.clear();
				if (arg1) {
					for (int i = 0; i < listPp.size(); i++) {
						adapterppAdapter.lIntegers.add(true);
					}
				}else
				{
				
					for (int i = 0; i < listPp.size(); i++) {
						adapterppAdapter.lIntegers.add(false);
					}
				}
			
				
				adapterppAdapter.notifyDataSetChanged();
			}
		});
		chlBox=getView(R.id.ch_lb);
		chlBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				adaptertypeAdapter.lIntegers.clear();
				if (arg1) {
							
						for (int i = 0; i < listType3.size(); i++) {
							adaptertypeAdapter.lIntegers.add(true);
						}
				}else {
				
					for (int i = 0; i < listType3.size(); i++) {
						adaptertypeAdapter.lIntegers.add(false);
					}
				}
				adaptertypeAdapter.notifyDataSetChanged();
			}
		});
		gridPpView=(MyGridView) findViewById(R.id.grid_pp);
		gridTypeView=(MyGridView) findViewById(R.id.grid_type);
		gridPpView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				adapterppAdapter.lIntegers.set(arg2, !adapterppAdapter.lIntegers.get(arg2));
				adapterppAdapter.notifyDataSetChanged();
			}
			
		});
		gridTypeView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-gen4erated method stub
				adaptertypeAdapter.lIntegers.set(arg2, !adaptertypeAdapter.lIntegers.get(arg2));
				adaptertypeAdapter.notifyDataSetChanged();
			}
		});
		btnType=getView(R.id.btn_type);
		btnType2=getView(R.id.btn_type2);
		btnFinsh=getView(R.id.btn_finsh);
		btnFinsh.setOnClickListener(this);
		btnType.setOnClickListener(this);
		btnType2.setOnClickListener(this);
		
	}
	/**
	 * @author:liujie
	 * @tips  :监听滑块选择的监听器
	 * 
	 */
	class MyRangBarListener implements OnRangeBarChangeListener{

	    /**
	     * 三个参数:
	     * 1.rangbar对象
	     * 2.左边的滑块距离左边的距离，这里的距离用每一格来代替
	     * 3.右边滑块距离左边的距离，距离用滑块的格数来代替
	     * 还需要注意的是：设置left = 2，表示左边滑块处于第三个分割线的位置。
	     * 
	     * example：
	     * leftThumbIndex = 2;rightThumbIndex = 5;
	     * 
	     *            thumb          thumb     ← 这是左右滑块
	     * |————|————|————|————|————|————|     ← 这里是分割线
	     */
	    @Override
	    public void onIndexChangeListener(RangeBar rangeBar,
	            int leftThumbIndex, int rightThumbIndex) {
	    	// 0 50 200 800 1600 不限
	        
	        xprive=leftThumbIndex;
	        yprice=rightThumbIndex;
	        				
	    }
	}
	 //设置监听器
	 ArrayList<String>  pprusterList=new ArrayList<String>();
	 ArrayList<String>  typerusterList=new ArrayList<String>();
	 ArrayList<String>  priceList=new ArrayList<String>();
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		switch (arg0.getId()) {
		case R.id.btn_type:
			
			btnType. setBackgroundResource(R.drawable.heath_box_pass);
			btnType2.setBackgroundResource(R.drawable.heath_box);
			listType3.clear();
			listType3.addAll(listType2);
			gridTypeView.setAdapter(adaptertypeAdapter=new ChoseGridViewAdapter(listType3, DesGoodsActivity.this));
			
			break;
		case R.id.btn_type2:
			//类型2
			btnType2.setBackgroundResource(R.drawable.heath_box_pass);
			btnType.setBackgroundResource(R.drawable.heath_box);
			listType3.clear();
			listType3.addAll(listType);
			gridTypeView.setAdapter(adaptertypeAdapter=new ChoseGridViewAdapter(listType3, DesGoodsActivity.this));
			
			break;
		case R.id.btn_finsh:
		    //完成筛选
			showLog(adaptertypeAdapter.lIntegers.toString());
			for (int i = 0; i < adaptertypeAdapter.lIntegers.size(); i++) {
				if (adaptertypeAdapter.lIntegers.get(i)) {
					typerusterList.add(""+listType3.get(i).getId());
				}
			}
			showLog(adapterppAdapter.lIntegers.toString());
			for (int i = 0; i < adapterppAdapter.lIntegers.size(); i++) {
				if (adapterppAdapter.lIntegers.get(i)) {
					pprusterList.add(listPp.get(i).getId());
				}
			}
			priceList.add(price[xprive
			                    ]);
			priceList.add(price[yprice
			                    ]);
			showLog("priceList"+priceList.toString()+"typerusterList:"+typerusterList.toString()+"pprusterList"+pprusterList.toString());
			//priceList  typerusterList  pprusterList
			Intent intent =new Intent();
            intent.putStringArrayListExtra("priceList",  priceList);
            intent.putStringArrayListExtra("typerusterList", typerusterList);
            intent.putStringArrayListExtra("pprusterList", pprusterList);
            
			setResult(200, intent);
			finish();
			break;
		default:
			break;
		}
	}

	
}
