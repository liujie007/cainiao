package com.hncainiao.fubao.ui.activity.healthshop;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.model.Kind;
import com.hncainiao.fubao.net.ShopApi;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.healthshop.bean.HealthBean;
import com.hncainiao.fubao.ui.adapter.Setcity_Adapter;
import com.hncainiao.fubao.ui.adapter.base.CommonAdapter;
import com.hncainiao.fubao.ui.adapter.base.ViewHolder;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.jmheart.net.JsonUtil;
import com.jmheart.view.listview.RefreshListView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;


/**
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-7-15上午9:00:28
 *  商品搜索
 */
public class GoodsSerachActivity extends BaseActivity {

	GridView gridHOt;
	Context mContext; 
	RefreshListView listShop;
	ImageView imserach;
	int pagenum=1;
	String keyword;
	LinearLayout linHoTtop;
	@NotEmpty
	EditText edSearchWorld;
	CommonAdapter<HealthBean> cAdapter=null;
	List<HealthBean> healList=new ArrayList<HealthBean>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_serach);
		mContext=this;
		inintView();
		loadDate();
	}
	private void inintView()
	{
		linHoTtop=getView(R.id.lin_hot_top);
		edSearchWorld=getView(R.id.title_txt);
		gridHOt=getView(R.id.grid_serach);
		listShop=getView(R.id.list_search_list);
		imserach=getView(R.id.serach);
		imserach.setOnClickListener(this);
		listShop.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(GoodsSerachActivity.this,GoodsInfoActivity.class);
				intent.putExtra("product_id", healList.get(arg2-1).getProduct_id());
				startActivity(intent);
			}
		});
		gridHOt.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				keyword=hotWord.get(arg2).getName().substring(0, hotWord.get(arg2).getName().lastIndexOf("("));
				showLog(keyword);
				SerachShop();
			}
		});
	}
	private void loadDate()
	{
		if (NetworkUtil.isOnline(this)) {
			ShopApi.getHotWord(hotHeader);
		}
		else
		{
			showToast("无网络连接");
		}
	}
	List<Kind> hotWord=new ArrayList<Kind>();
	AsyncHttpResponseHandler hotHeader=new AsyncHttpResponseHandler()
	{
		public void onSuccess(String content) {
		   //{"search":[{"id":"29","keyword":"营养","count":"13"},{"id":"28","keyword":"保健","count":"8"},{"id":"30","keyword":"品","count":"4"},{"id":"27","keyword":"商品","count":"2"}],"err":0}
			 JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(content);
				 if (jsonObject.getInt("err")==0) {
					 hotWord.clear();
					 JSONArray array=jsonObject.getJSONArray("search");
					 for (int j = 0; j < array.length(); j++) {
						 JSONObject jsonObject2=array.getJSONObject(j);
						 hotWord.add(new Kind(jsonObject2.getString("id")+"", jsonObject2.getString("keyword")+"("+jsonObject2.getString("count")+")"));
					}
				 }
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			gridHOt.setAdapter(new Setcity_Adapter(mContext, hotWord));
		
		};
		public void onFailure(Throwable error) {
			
		};
	};
	private void SerachShop()
	{
		if (NetworkUtil.isOnline(mContext)) {
			Showloading();
			ShopApi.getProductList(pagenum+"", "", keyword, serachhandler);
		}
		else
		{
			showToastNotNet();
		}
	}
	AsyncHttpResponseHandler serachhandler=new AsyncHttpResponseHandler()
	{
		public void onSuccess(String content) {
			//{"product":[{"product_id":"6","name":"商品名称","img":"/Uploads/Images/55dd4fd441b4a.png","hospital_id":"12","crowd":"适宜人群","price":"100.00","suggested_price":"200.00","stock":"90","hospital_name":"湖南中医药大学第二附属医院"}],"err":0}
			Dissloading();
		try {
			JSONObject jObject =new JSONObject(content);
			if (jObject.getInt("err")==0) {
				linHoTtop.setVisibility(View.GONE);
				try {
					healList.clear();
					healList=JsonUtil.parserJsonToList(HealthBean.class, jObject.getJSONArray("product"));
					showLog("长度"+healList.size()+"数据："+healList.get(0).getName());
					listShop.setAdapter(cAdapter=new CommonAdapter<HealthBean>(getApplicationContext(),healList,R.layout.index_heath_list) {
						@Override
						public void convert(ViewHolder helper, HealthBean item,int pos) {
							// TODO Auto-generated method stub
							showLog("数据"+item.getName());
						   helper.setText(R.id.tv_heath_shop_title, item.getName());
						   helper.setText(R.id.tv_heath_shop_hospital, "所属厂家："+item.getProducer());
						   helper.setText(R.id.tv_heath_shop_people, "适宜人群:"+item.getCrowd());
						   helper.setText(R.id.tv_heath_shop_moeny, item.getPrice()+"￥");
						   helper.setDelText(R.id.tv_heath_shop_start_money, item.getSuggested_price()+"￥");
						   helper.setImageChecUrl(R.id.im_heath_shop_img, item.getImg());
						
						}
					
					});
					listShop.onLoadComplete();
					listShop.onRefreshComplete();
					listShop.setResultSize(healList.size());
					cAdapter.notifyDataSetChanged();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				//无数据
				showToast("未搜索到数据");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		};
		public void onFailure(Throwable error) {
			showToast("请求失败");
		};
	};
	public void onClick(android.view.View arg0) {
		switch (arg0.getId()) {
		case R.id.serach:
			//搜索
			validator.validate();
			break;

		default:
			break;
		}
	};
	
	@Override
	public void onValidationSucceeded() {
		// TODO Auto-generated method stub
		
		keyword=edSearchWorld.getText().toString();
		SerachShop();
	}
	
}
