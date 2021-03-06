package com.hncainiao.fubao.ui.activity.healthshop;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.net.ShopApi;
import com.hncainiao.fubao.ui.activity.healthshop.bean.HealthBean;
import com.hncainiao.fubao.ui.adapter.base.CommonAdapter;
import com.hncainiao.fubao.ui.adapter.base.ViewHolder;
import com.hncainiao.fubao.ui.fragment.BaseFragment;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.jmheart.net.JsonUtil;
import com.jmheart.view.listview.RefreshListView;
import com.jmheart.view.listview.RefreshListView.OnLoadListener;
import com.jmheart.view.listview.RefreshListView.OnRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class IndexHealthFragment extends BaseFragment implements OnClickListener,OnRefreshListener,OnLoadListener{

	Context mContext;
	RefreshListView listShop;
	CommonAdapter<HealthBean> cAdapter=null;
	List<HealthBean> healList=new ArrayList<HealthBean>();
	TextView tvSales,tvPrice,tvPriceFlag,tvNew,tvDes;
	boolean tab_price=false;
    private String type="sale_desc";
    boolean  saixu=true;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
   	
		 view=inflater.inflate(R.layout.ab_fragment, null);
		 view=inflater.inflate(R.layout.activity_health_shop, null);
	    	mContext=getActivity();
			inintView();
			initLen();
			GetnetDate("1");
	    
	    return view ;
    }
 
	@SuppressWarnings("unchecked")
	public final <E extends View> E getView(int id) {
		try {
			return (E) view.findViewById(id);
		} catch (ClassCastException e) {
			Log.e("Base", "Can't cast the View.", e);
			throw e;
		}

	}
    /**
	 * 控件初始化
	 */
	private void inintView()
	{
		getView(R.id.serach).setVisibility(View.VISIBLE);
		tvSales=getView(R.id.tv_sales);
		tvPrice=getView(R.id.tv_price);
		tvPriceFlag=getView(R.id.tv_price_flag);
		tvNew=getView(R.id.tv_new);
		tvDes=getView(R.id.tv_des);
		listShop=getView(R.id.heath_list_shop);
		((TextView)getView(R.id.title_txt)).setText("健康商城");
		((Button)getView(R.id.comeback)).setVisibility(View.GONE);
		listShop.setOnRefreshListener(this);
		listShop.setOnLoadListener(this);
	}
	/**
	 * 初始化tab
	 */
	
	private void ininTab()
	{
		tvSales.setTextColor(getResources().getColor(R.color.black));
		tvPrice.setTextColor(getResources().getColor(R.color.black));
		tvPriceFlag.setBackgroundResource(R.drawable.heath_des);
		tvNew.setTextColor(getResources().getColor(R.color.black));
		tvDes.setTextColor(getResources().getColor(R.color.black));
	}
	/**
	 * 事件监听
	 */
	private void initLen()
	{
		getView(R.id.serach).setOnClickListener(this);
		tvDes.setOnClickListener(this);
		tvNew.setOnClickListener(this);
		tvPriceFlag.setOnClickListener(this);
		tvPrice.setOnClickListener(this);
		tvSales.setOnClickListener(this);
		listShop.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2<=healList.size()) {
					Intent intent=new Intent(mContext,GoodsInfoActivity.class);
					intent.putExtra("product_id", healList.get(arg2-1).getProduct_id());
					startActivity(intent);
				}
				
			}
		});
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		switch (arg0.getId()) {
			//搜索
		case R.id.serach:
			startActivity(new Intent(mContext,GoodsSerachActivity.class));
			break;
		case R.id.tv_des:
			//筛选
			saixu=false;
			ininTab();
			tvDes.setTextColor(getResources().getColor(R.color.blue));
			startActivityForResult(new Intent(mContext,DesGoodsActivity.class),200);
			break;
		case R.id.tv_sales:
			//销量
			saixu=true;
			ininTab();
			tvSales.setTextColor(getResources().getColor(R.color.blue)); 
			//处理销量逻辑
			type="sale_desc";
			GetnetDate("1");
			
			break;
		case R.id.tv_new:
			//新品
			saixu=true;
			ininTab();
			tvNew.setTextColor(getResources().getColor(R.color.blue));
			type="createtime_desc";
			GetnetDate("1");
			break;
		case R.id.tv_price:
			//价格
			saixu=true;
			ininTab();
			tvPrice.setTextColor(getResources().getColor(R.color.blue));
			if (tab_price) {
				type="price_asc";
				tvPriceFlag.setBackgroundResource(R.drawable.heath_des_dow);
				tab_price=false;
				GetnetDate("1");
			}
			else {
				tvPriceFlag.setBackgroundResource(R.drawable.heath_des_up);
				tab_price=true;
				type="price_desc";
				GetnetDate("1");
			}
			
			break;
		 
		default:
			break;
		}
	}
	 ArrayList<String>  priceList;
	 ArrayList<String>  typerusterList;
	 ArrayList<String>  pprusterList;
	 
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode==200) {
			 priceList=data.getStringArrayListExtra("priceList");
			 typerusterList=data.getStringArrayListExtra("typerusterList");	
			 pprusterList=data.getStringArrayListExtra("pprusterList");
			 GetSainetDate("1",priceList,typerusterList,pprusterList);
		}
		
	}
	/**
	 * 精准筛选
	 */
	private void GetSainetDate(String page, ArrayList<String>  priceList,  ArrayList<String>  typerusterList, ArrayList<String>  pprusterList)
	{
		if (NetworkUtil.isOnline(mContext)) {
			Showloading();
			pagenum=1;
			ShopApi.filter(page,priceList, typerusterList, pprusterList, handler);
		}
		else
		{
			showToast("无网络连接");
		}
	}
	/**
	 * 精准筛选加载更多
	 */
	private void GetMoreSainetDate(String page, ArrayList<String>  priceList,  ArrayList<String>  typerusterList, ArrayList<String>  pprusterList)
	{
		if (NetworkUtil.isOnline(mContext)) {
		//	Showloading();
			ShopApi.filter(page,priceList, typerusterList, pprusterList, morehandler);
		}
		else
		{
			showToast("无网络连接");
		}
	}
	private void GetMoreDate(String page)
	{
		if (NetworkUtil.isOnline(mContext)) {
			
			 ShopApi.getProductList(page,type, "", morehandler);
		}else
		{
			showToast("无网络连接");
		}
	}
	/**
	 * 得到网络数据
	 */
	private void GetnetDate(String st)
	{
		pagenum=1;
		if (NetworkUtil.isOnline(mContext)) {
			if (st.equals("1")) {
				 Showloading();
			}
			 ShopApi.getProductList(pagenum+"",type, "", handler);
		}else
		{
			showToast("无网络连接");
			listShop.onLoadComplete();
			listShop.onRefreshComplete();
			listShop.setAdapter(cAdapter);
		}
	}
	/**
	 * 初次加载
	 */
	AsyncHttpResponseHandler handler=new AsyncHttpResponseHandler(){
		
		@Override
		public void onSuccess(String content) {
		//{"product":[{"product_id":"6","name":"商品名称","img":"/Uploads/Images/55dd4fd441b4a.png","hospital_id":"12","crowd":"适宜人群","price":"100.00","suggested_price":"200.00","stock":"90","hospital_name":"湖南中医药大学第二附属医院"}],"err":0}
				Dissloading();
			try {
				JSONObject jObject =new JSONObject(content);
				if (jObject.getInt("err")==0) {
					pagenum=2;
					try {
						healList.clear();
						healList=JsonUtil.parserJsonToList(HealthBean.class, jObject.getJSONArray("product"));
						showLog("长度"+healList.size()+"数据："+healList.get(0).getName());
						listShop.setAdapter(cAdapter=new CommonAdapter<HealthBean>(mContext.getApplicationContext(),healList,R.layout.index_heath_list) {
						@Override
						public void convert(ViewHolder helper, HealthBean item,int po) {
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
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					//无数据
				}
				listShop.onLoadComplete();
				listShop.onRefreshComplete();
				if (healList!=null) {
					listShop.setResultSize(healList.size());
					
				}
				cAdapter.notifyDataSetChanged();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		};
		@Override
		public void onFailure(Throwable error) {
			showToast("请求失败");
			listShop.onLoadComplete();
			listShop.onRefreshComplete();
			listShop.setAdapter(cAdapter);
			Dissloading();
		};
	};
	
	/**
	 * 加载更多数据
	 */
	AsyncHttpResponseHandler morehandler=new AsyncHttpResponseHandler(){
		
		@Override
		public void onSuccess(String content) {
			//{"product":[{"product_id":"6","name":"商品名称","img":"/Uploads/Images/55dd4fd441b4a.png","hospital_id":"12","crowd":"适宜人群","price":"100.00","suggested_price":"200.00","stock":"90","hospital_name":"湖南中医药大学第二附属医院"}],"err":0}
				
			try {
				JSONObject jObject =new JSONObject(content);
				
				if (jObject.getInt("err")==0) {
					try {
						pagenum++;
						healList.addAll(JsonUtil.parserJsonToList(HealthBean.class, jObject.getJSONArray("product")));
						showLog("长度"+healList.size()+"数据："+healList.get(0).getName());
						listShop.setAdapter(cAdapter=new CommonAdapter<HealthBean>(mContext.getApplicationContext(),healList,R.layout.index_heath_list) {
							@Override
							public void convert(ViewHolder helper, HealthBean item ,int position) {
								// TODO Auto-generated method stub
								showLog("数据"+item.getName());
							   helper.setText(R.id.tv_heath_shop_title, item.getName());
							   helper.setText(R.id.tv_heath_shop_hospital, "所属厂家："+item.getProducer());
							   helper.setText(R.id.tv_heath_shop_people, "适宜人群"+item.getCrowd());
							   helper.setText(R.id.tv_heath_shop_moeny, item.getPrice()+"￥");
							   helper.setDelText(R.id.tv_heath_shop_start_money, item.getSuggested_price()+"￥");
							   helper.setImageChecUrl(R.id.im_heath_shop_img, item.getImg());
							
							}
						
						});
					
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					//无数据
					listShop.isEndDate();
					//listShop.setLoadEnable(false);
				}
				listShop.onLoadComplete();
				if (healList!=null) {
					listShop.setResultSize(healList.size());
					
				}
				cAdapter.notifyDataSetChanged();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		};
		@Override
		public void onFailure(Throwable error) {
			showToast("请求失败");
			
		};
	};
	
	/* (non-Javadoc)
	 * 加载更多
	 * @see com.jmheart.base.BaseActivity#onLoad()
	 */
	int pagenum=1;
	public void onLoad() {
		
		if (saixu) {
			GetMoreDate(pagenum+"");
		}
		else {
			//筛选分页
			GetMoreSainetDate(pagenum+"", priceList, typerusterList, pprusterList);
		}
		
		
	};
	@Override
	public void onRefresh() {
		
		if (saixu) {
			GetnetDate("0");
		}
		else
		{
			GetSainetDate("1",priceList,typerusterList,pprusterList);
		}
		
	}
	
	@Override
	protected void creatFragmentUI() {
		// TODO Auto-generated method stub
		
	}

}
