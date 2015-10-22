package com.hncainiao.fubao.ui.activity.healthshop;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.net.ShopApi;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.healthshop.bean.GoodsCarBean;
import com.hncainiao.fubao.ui.adapter.base.CommonAdapter;
import com.hncainiao.fubao.ui.adapter.base.ViewHolder;
import com.hncainiao.fubao.ui.fragment.MallOrderActivity;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.jmheart.base.BaseApplication;
import com.jmheart.net.JsonUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-7-13下午6:12:51
 *  购物车
 */
public class GoodsCarActivity extends BaseActivity {

	ListView listGoodsCar;
	List<GoodsCarBean> listGoodsCarBeans=new ArrayList<GoodsCarBean>();
	TextView tvGoodsnum,tvGoodsPrice;
	CommonAdapter<GoodsCarBean> adapter;
	CheckBox checkGoods;
	TextView btnEdit;
	Button btnSubmit;
	List<HashMap<String, Object>> mList=new ArrayList<HashMap<String,Object>>();
     @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_goodscar);
    	setTitle("购物车");
    	inintView();
    	inintDate();

    }
     
     private void inintView()
      {
     	 listGoodsCar=getView(R.id.list_goodscar);
     	 tvGoodsnum=getView(R.id.tv_goodsnum);
     	 tvGoodsPrice=getView(R.id.tv_goodsprice);
     	 btnEdit=getView(R.id.myzixun);
     	 btnEdit.setOnClickListener(this);
     	 btnEdit.setText("编辑");
     	btnEdit.setVisibility(View.VISIBLE);
     	btnSubmit= getView(R.id.btn_settlement);
     	btnSubmit.setOnClickListener(this);
       	checkGoods= getView(R.id.ch_chose);
     	listGoodsCar.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
			
				ViewHolder viewHolder = (ViewHolder)arg1.getTag();
	                //在每次获取点击的item时将对应的checkBox状态改变，同时修改map的值
				viewHolder.box.setChecked(!adapter.chList.get(arg2));
	             adapter.chList.set(arg2, viewHolder.box.isChecked());
	             adapter.notifyDataSetChanged();
				
			}
		});
     	checkGoods.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (adapter!=null) {
					  adapter.chList.clear();
					  adapter.isclear=true;
					 if (arg1) {
						 for (int i = 0; i < listGoodsCarBeans.size(); i++) {
							adapter.chList.add(true);
						}
					}
					 else {
						 for (int i = 0; i < listGoodsCarBeans.size(); i++) {
							 adapter.chList.add(false);
							}
					}
					 adapter.notifyDataSetChanged();
				}
				else {
					showToast("暂无商品");
				}
			}
		});
     
      }
     
     @Override
    public void onClick(View arg0) {
    	// TODO Auto-generated method stub
    	switch (arg0.getId()) {
		case R.id.btn_settlement:
			 if (btnSubmit.getText().toString().equals("删除")) {
				 if (NetworkUtil.isOnline(this)) {
					 
					 if (adapter!=null&&listGoodsCarBeans.size()>0) {
					
					 List<String > mlistList=new ArrayList<String>();
						for (int i = 0; i < adapter.chList.size(); i++) {
							if (adapter.chList.get(i)) {
								mlistList.add(listGoodsCarBeans.get(i).getCart_id());
							}
						}
						if (mlistList.size()>0) {
							Showloading();
							ShopApi.delCart(mlistList, delcartheadl);
						}
						else {
							showToast("请选择删除的商品");
						}
						
					 }
					 
					 else {
						showToast("暂时无商品可以删除");
					}
					}
					else {
						showToastNotNet();
					}
			}
		 else {
			List<GoodsCarBean> mlist=new ArrayList<GoodsCarBean>();
			Intent iuIntent=new Intent(GoodsCarActivity.this,PayOrderActivity.class);
			for (int i = 0; i < adapter.chList.size(); i++) {
				if (adapter.chList.get(i)) {
					mlist.add(listGoodsCarBeans.get(i));
				}
			}
			 if (mlist.size()>0&&Float.parseFloat(tvGoodsPrice.getText().toString().substring(0, tvGoodsPrice.getText().toString().length()-1))>0) {
					iuIntent.putExtra("order_goods", (Serializable)mlist);
					startActivity(iuIntent);
			}
			 else {
				showToast("请选择购买的商品和商品数量");
			}
			 }
		/*	//提交信息立即结算
			if (NetworkUtil.isOnline(this)) {
				for (int i = 0; i < adapter.chList.size(); i++) {
					if (adapter.chList.get(i)) {
						HashMap< String, Object> map=new HashMap<String, Object>();
						map.put(listGoodsCarBeans.get(i).getId(), listGoodsCarBeans.get(i).getCount());
						mList.add(map);
					}
				}
				Showloading();
				ShopApi.settle(mList, cartheadl);
			}
			else {
				showToastNotNet();
			}*/
			
		
			break;
		case R.id.myzixun:
			//编辑
			if (btnEdit.getText().toString().equals("编辑")) {
				btnEdit.setText("取消");
				btnSubmit.setText("删除");
			}
			else {
				btnEdit.setText("编辑");
				btnSubmit.setText("立即结算");
			}
			break;
		default:
			break;
		}
    	
    }
     AsyncHttpResponseHandler delcartheadl=new AsyncHttpResponseHandler()
     {
    	 public void onSuccess(String content) {
    		 Dissloading();
    		 try {
				JSONObject jsonObject =new JSONObject(content);
				if (jsonObject.getInt("err")==0) {
					//删除
					showToast(jsonObject.getString("msg"));
				
			    		Showloading();
						ShopApi.getCart(getcarheader);
					
				}
				else {
					showToast(jsonObject.getString("msg"));
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	 };
    	 public void onFailure(Throwable error) {
    		 showToastNetTime();
    		 Dissloading();
    	 };
     };
     AsyncHttpResponseHandler cartheadl=new AsyncHttpResponseHandler()
     {
    	 public void onSuccess(String content) {
    		 Dissloading();
    		try {
				JSONObject jsonObject =new JSONObject(content);
				if (jsonObject.getInt("err")==0) {
					
					startActivity(new Intent(GoodsCarActivity.this,MallOrderActivity.class));
					 
				}
				else {
					showToast("提交订单失败");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	 };
    	 public void onFailure(Throwable error) {
    		 Dissloading();
    		 showToast("请求超时");
    	 };
     };
     /**
     * 数据
     */
    private void inintDate()
     {
    	
    	if (NetworkUtil.isOnline(this)) {
    		Showloading();
			ShopApi.getCart(getcarheader);
		}
    	else {
			showToastNotNet();
		}
    
     }
    AsyncHttpResponseHandler getcarheader=new AsyncHttpResponseHandler()
    {
    	public void onSuccess(String content) {
    		Dissloading();
    		try {
				JSONObject jsonObject =new JSONObject(content);
				if (jsonObject.getInt("err")==0) {
  				
					try {
						listGoodsCarBeans=JsonUtil.parserJsonToList(GoodsCarBean.class, jsonObject.getJSONArray("cart"));
				     //   tvGoodsnum.setText(listGoodsCarBeans.size()+"");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					listGoodsCar.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
					listGoodsCar.setAdapter(adapter=new CommonAdapter<GoodsCarBean>(getApplicationContext(),listGoodsCarBeans,R.layout.goodscar_itml,true) {
						@Override
						public void convert(final ViewHolder helper, final GoodsCarBean item,int post) {
							// TODO Auto-generated method stub
							 helper.setText(R.id.tv_tile, item.getProduct_name());
							 helper.setText(R.id.tv_mtile, item.getRemark());
							 helper.setText(R.id.tv_price, item.getPrice());
							 helper.setText(R.id.ed_goodsnum, item.getCount());
							 BaseApplication.imageLoader.displayImage(item.getImg(), (ImageView)helper.getView(R.id.im_head));
							 helper.getView(R.id.tv_addnum).setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub
								//	showToast("d");
								EditText editText=helper.getView(R.id.ed_goodsnum);
								int goodsname=Integer.parseInt(""+editText.getText().toString());
								goodsname++;
								item.setCount(goodsname+"");
								editText.setText(""+goodsname);
								}
							});
							 helper.getView(R.id.tv_outnum).setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View arg0) {
										// TODO Auto-generated method stub
										//showToast("d");
										EditText editText=helper.getView(R.id.ed_goodsnum);
										int goodsname=Integer.parseInt(""+editText.getText().toString());
										goodsname--;
										if (goodsname>=0) {
											editText.setText(""+goodsname);
											item.setCount(goodsname+"");
										}
										
									}
								});
							 /*CheckBox cBox=helper.getView(R.id.ch_shop);
							 cBox.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									
									isclear=false;
									notifyDataSetChanged();
								}
							});*/
									int goodsnum=0;
									float price=0;
									for (int i = 0; i < chList.size(); i++) {
										if (chList.get(i)) {
											goodsnum++;
											price=price+Float.parseFloat(listGoodsCarBeans.get(i).getPrice())*Float.parseFloat( listGoodsCarBeans.get(i).getCount());
										}
									}
									tvGoodsnum.setText(goodsnum+"");
									DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
									String p=decimalFormat.format(price);//format 返回的是字符串
									tvGoodsPrice.setText(p+"￥");
								}
						
						

				    	});
				}
				else {
					listGoodsCarBeans.clear();
					if (adapter!=null) {
						adapter.notifyDataSetChanged();
					}
					showToast(jsonObject.getString("msg"));
					tvGoodsnum.setText("0");
					tvGoodsPrice.setText("0.00"+"￥");
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
     
   
}
