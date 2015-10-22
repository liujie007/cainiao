package com.hncainiao.fubao.ui.activity.healthshop;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.hncainiao.fubao.R;
import com.hncainiao.fubao.application.FuBaoApplication;
import com.hncainiao.fubao.net.ShopApi;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.healthshop.bean.GoodsCarBean;
import com.hncainiao.fubao.ui.activity.healthshop.bean.PayAddressBean;
import com.hncainiao.fubao.ui.activity.healthshop.bean.ShippingBean;
import com.hncainiao.fubao.ui.adapter.base.CommonAdapter;
import com.hncainiao.fubao.ui.adapter.base.ViewHolder;
import com.hncainiao.fubao.ui.fragment.MallOrderActivity;
import com.hncainiao.fubao.ui.views.ListViewForScrollView;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.jmheart.base.BaseApplication;
import com.jmheart.net.JsonUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

/**
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-7-13下午4:29:30
 * 	订单支付
 */
public class PayOrderActivity extends BaseActivity {

	ListViewForScrollView listAddress,listShop;
	
	List<PayAddressBean> listAddresss=new ArrayList<PayAddressBean>();
	List<GoodsCarBean> mlist=null;
	TextView tvAddaddress;
	CommonAdapter<PayAddressBean> adapter;
	EditText consignee,address,phone;
	Spinner spKDSpinner;
	List<String> mItems =new ArrayList<String>();
	TextView tvGoodsnum,tvPrice,tvAllPrice,tvGoodsrank;
	float goodsnum=0;
	List<ShippingBean> shipplistBeans=new ArrayList<ShippingBean>();
	String consignee_id="", shipping_id="", product="";
	Button btnConfigButton;
	boolean ischek=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_order_activity);
		setTitle("商品结算");
		mlist=(List<GoodsCarBean>) getIntent().getSerializableExtra("order_goods");
		if (mlist==null) {
			mlist=new ArrayList<GoodsCarBean>();
		}
		inintView();
		inintDate();

	}

	/**
	 * 初始化数据
	 */
	private void inintDate()
	{
		if (NetworkUtil.isOnline(this)) {
			ShopApi.getShipping( kdhandler);
			
		}
		if (NetworkUtil.isOnline(this)) {
			Showloading();
			ShopApi.getConsigneeList(ConsigneeListheadl);
		}
		else {
			showToast("无网络连接");
		}
		if (NetworkUtil.isOnline(this)) {
			//ShopApi.getProductInfo(getIntent().getStringExtra(""),getProductInfoheadl);
		}
		else {
			showToast("无网络连接");
		}
		listShop.setAdapter(new CommonAdapter<GoodsCarBean>(getApplicationContext(),mlist,R.layout.pay_goods_list) {
			@Override
			public void convert(ViewHolder helper, GoodsCarBean item,int pos) {
				// TODO Auto-generated method stub
				 helper.setText(R.id.tv_tile, item.getProduct_name());
				 helper.setText(R.id.tv_mtile, item.getRemark());
				 helper.setText(R.id.tv_price, item.getPrice());
				 helper.setText(R.id.tv_goodsnum, item.getCount());
				 BaseApplication.imageLoader.displayImage(item.getImg(), (ImageView)helper.getView(R.id.im_head));
			
			}

		});
		
	}
	AsyncHttpResponseHandler ConsigneeListheadl =new AsyncHttpResponseHandler(){
		public void onSuccess(String content) {
			Dissloading();
			try {
				JSONObject jsonObject =new JSONObject(content);
				if (jsonObject.getInt("err")==0) {
					try {
						listAddresss.clear();
						listAddresss=JsonUtil.parserJsonToList(PayAddressBean.class, jsonObject.getJSONArray("address"));
						listAddress.setAdapter(adapter=new CommonAdapter<PayAddressBean>(getApplicationContext(),listAddresss,R.layout.pay_address_list,true) {
							@Override
							public void convert(final ViewHolder helper, final PayAddressBean item,final int pos) {
								// TODO Auto-generated method stubch_address
							         
								   if (ischek) {
								    	 chList.set(0, true);
									}
							
								helper.setText(R.id.tv_address, item.getAddress());
								helper.setText(R.id.tv_name, "姓名："+item.getConsignee());
								helper.setText(R.id.tv_phone,"电话："+item.getPhone());
								helper.getView(R.id.im_chage).setOnClickListener(new OnClickListener() {	
									@Override
									public void onClick(View arg0) {
										// TODO Auto-generated method stub
										final NiftyDialogBuilder dialogBuilder=new NiftyDialogBuilder(PayOrderActivity.this,R.style.Dialog_NoTitle);
										View view=LayoutInflater.from(mContext).inflate(R.layout.peopleinfo_dalog, null);
												//in(PayOrderActivity.class, R.layout.peopleinfo_dalog, helper.getConvertView());
										consignee=(EditText)view.findViewById(R.id.ed_name);
										address=(EditText)view.findViewById(R.id.ed_address);
										phone=(EditText)view.findViewById(R.id.ed_phone);
										consignee.setText(item.getConsignee());
										address.setText(item.getAddress());
										phone.setText(item.getPhone());
										dialogBuilder.setContentView(view);
										((Button)view.findViewById(R.id.btn_confirm)).setText("确定修改");
										((Button)view.findViewById(R.id.btn_confirm)).setOnClickListener(new OnClickListener() {
											
											@Override
											public void onClick(View v) {
												// TODO Auto-generated method stub
											
												if (chekc()) {
													dialogBuilder.dismiss();
													Showloading();
													listAddresss.get(pos).setAddress(address.getText().toString());
													listAddresss.get(pos).setConsignee(consignee.getText().toString());
													listAddresss.get(pos).setPhone(phone.getText().toString());
													ShopApi.saveConsignee(item.getId(), consignee.getText().toString(), address.getText().toString(), phone.getText().toString(), saveConsigneehandler);
										
												}
										
											}
										});
										dialogBuilder.setButton1Click(new View.OnClickListener() {
						                    @Override
						                    public void onClick(View v) {
						                    	dialogBuilder.dismiss();
						                    }
						                })
						                .setButton2Click(new View.OnClickListener() {
						                    @Override
						                    public void onClick(View v) {
						                    	dialogBuilder.dismiss();
						                        //Toast.makeText(v.getContext(), "i'm btn2", Toast.LENGTH_SHORT).show();
						                    }
						                })
						                .show();
									
									
									
									}
								});
								helper.getView(R.id.im_del).setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										if (NetworkUtil.isOnline(mContext)) {
											listAddresss.remove(pos);
											Showloading();
											ShopApi.rmConsignee(item.getId(), rmConsigneehandler);
										}
										else {
											showToast("无网络连接");
										}
									}
								});
							}
						});
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		};
		public void onFailure(Throwable error) {
			Dissloading();
			showToastNetTime();
		};
	};
	
	/**
	 * 控件初始化
	 */
	private void inintView()
	{
		spKDSpinner=getView(R.id.sp_kd);
		tvGoodsrank=getView(R.id.tv_goods_rank);
		listAddress=getView(R.id.list_address);
		listShop=getView(R.id.list_shop);
		tvAddaddress=getView(R.id.tv_addaddress);
		tvGoodsnum=getView(R.id.tv_goods_num);
		tvPrice=getView(R.id.tv_goods_price);
		tvAllPrice=getView(R.id.tv_allprice);
		btnConfigButton=getView(R.id.btn_config);
		btnConfigButton.setOnClickListener(this);
		tvGoodsnum.setText(mlist.size()+"");
		for (int i = 0; i < mlist.size(); i++) {
			goodsnum=goodsnum+(Float.parseFloat(mlist.get(i).getPrice()))*(Float.parseFloat(mlist.get(i).getCount()));	
		}
		DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
		String p=decimalFormat.format(goodsnum);//format 返回的是字符串
		
		tvPrice.setText(""+p+"元");
		tvAllPrice.setText(""+p+"元");
		tvAddaddress.setOnClickListener(this);
		spKDSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				shipping_id=shipplistBeans.get(arg2).getId();
				tvGoodsrank.setText(shipplistBeans.get(arg2).getRemark()+"\n价格："+shipplistBeans.get(arg2).getFee());
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		listAddress.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				ischek=false;
				ViewHolder viewHolder = (ViewHolder)arg1.getTag();
                //在每次获取点击的item时将对应的checkBox状态改变，同时修改map的值
				 adapter.chList.clear();
				 for (int i = 0; i < listAddresss.size(); i++) {
					 
					 adapter.chList.add(false);
				}
			  viewHolder.box.setChecked(!adapter.chList.get(arg2));
              adapter.chList.set(arg2, viewHolder.box.isChecked());
              adapter.notifyDataSetChanged();
			}
		});
	}
	AsyncHttpResponseHandler kdhandler =new AsyncHttpResponseHandler()
	{
		@Override
		public void onSuccess(String content) {
			if (content!=null) {
				try {
					JSONObject jsonObject = new JSONObject(content);
					if (jsonObject.getInt("err")==0) {
						//获取成功
						try {
							shipplistBeans=	JsonUtil.parserJsonToList(ShippingBean.class, jsonObject.getJSONArray("shipping"));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						shipping_id=shipplistBeans.get(0).getId();
						for (int i = 0; i < shipplistBeans.size(); i++) {
							mItems.add(shipplistBeans.get(i).getName());
							
						}
						
						ArrayAdapter<String> _Adapter=new ArrayAdapter<String>(PayOrderActivity.this,R.layout.add_bank_itme, mItems);
						//绑定 Adapter到控件
						spKDSpinner.setAdapter(_Adapter);
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
		};
	};
	public boolean chekc()
	{
		if (consignee.getText().toString().equals("")) {
			showToast("请填写名字");
			return false;
		 }
		if (address.getText().toString().equals("")) {
			showToast("请填写地址");
			return false;
		 }if (!CheckPhone(phone)) {
			 showToast("请填写正确的手机号码");
				return false;
			 }
		 return true;
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_config:
			//提交订单
			if (listAddresss.size()>0) {
				
			
			if (NetworkUtil.isOnline(this)) {
				Showloading();
				for (int i = 0; i < mlist.size(); i++) {
					product=product+mlist.get(i).getProduct_id()+"-"+mlist.get(i).getPrice()+"-"+mlist.get(i).getCount()+"|";
				}
				for (int i = 0; i < adapter.chList.size(); i++) {
					if (adapter.chList.get(i)) {
					  consignee_id=listAddresss.get(i).getId();
					}
				}
				
				ShopApi.addOrder(consignee_id, shipping_id, "6", product, addOrderhandler);
			}
			else
			{
				showToastNotNet();
			}
		}
		else
		{
			showToast("请先添加收货地址!");	
		}
			//startActivity(new Intent(PayOrderActivity.this,MallOrderActivity.class));
			break;
		case R.id.tv_addaddress:
				//添加地址
			if (NetworkUtil.isOnline(this)) {
				
				final NiftyDialogBuilder dialogBuilder=new NiftyDialogBuilder(PayOrderActivity.this,R.style.Dialog_NoTitle);
				View view2=LayoutInflater.from(this).inflate(R.layout.peopleinfo_dalog, null);
				consignee=(EditText)view2.findViewById(R.id.ed_name);
				address=(EditText)view2.findViewById(R.id.ed_address);
				phone=(EditText)view2.findViewById(R.id.ed_phone);
				dialogBuilder .withDuration(700) ; 
				((Button)view2.findViewById(R.id.btn_confirm)).setText("确定添加");
				((Button)view2.findViewById(R.id.btn_confirm)).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						 if (chekc()) {
							 dialogBuilder.dismiss();
								Showloading();
								ischek=true;
								ShopApi.addConsignee(consignee.getText().toString(), address.getText().toString(), phone.getText().toString(), addConsigneehandler);
					               
						}
                 
					}
				});
				dialogBuilder.setContentView(view2);
				dialogBuilder.withEffect(Effectstype.Newspager);
				dialogBuilder .setButton1Click(new View.OnClickListener() {
	                    @Override
	                    public void onClick(View v) {
	                    	dialogBuilder.dismiss();
	                    }
	                })
	                .setButton2Click(new View.OnClickListener() {
	                    @Override
	                    public void onClick(View v) {
	                    	dialogBuilder.dismiss();
	                        //Toast.makeText(v.getContext(), "i'm btn2", Toast.LENGTH_SHORT).show();
	                    }
	                })
	                .show();
				
               
			
			}
			else
			{
				showToastNotNet();
			}
			break;
		default:
			break;
		}
	}
	AsyncHttpResponseHandler addOrderhandler=new AsyncHttpResponseHandler(){
		public void onSuccess(String content) {
			Dissloading();
			try {
				JSONObject jsonObject =new JSONObject(content);
				if (jsonObject.getInt("err")==0) {
					//成功
				//	网络返回数据：{"ret":{"order_id":28,"sn":"20150830144819677342","tn":"201508301448196895528"},"err":0}
			
					UPPayAssistEx.startPayByJAR(PayOrderActivity.this, PayActivity.class, null, null,
							jsonObject.getJSONObject("ret").getString("tn").trim(), "00");
					
				}
				else
				{
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
	/**
	 * 删除
	 */
	AsyncHttpResponseHandler rmConsigneehandler=new AsyncHttpResponseHandler(){
		public void onSuccess(String content) {
			Dissloading();
			try {
				JSONObject jsonObject =new JSONObject(content);
				showToast(jsonObject.getString("msg"));
				if (jsonObject.getInt("err")==0) {
					//成功
					adapter.notifyDataSetChanged();
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
	/**
	 * 修改
	 */
	AsyncHttpResponseHandler saveConsigneehandler=new AsyncHttpResponseHandler()
	{
		public void onSuccess(String content) {
			Dissloading();
			try {
				JSONObject jsonObject=new JSONObject(content);
				showToast(jsonObject.getString("msg"));
				if (jsonObject.getInt("err")==0) {
					adapter.notifyDataSetChanged();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		public void onFailure(Throwable error) {
			Dissloading();
			showToastNetTime();
		};
	};
	/**
	 * 添加
	 */
	AsyncHttpResponseHandler addConsigneehandler=new AsyncHttpResponseHandler()
	{
		public void onSuccess(String content) {
			Dissloading();
			try {
				JSONObject jsonObject=new JSONObject(content);
				
				if (jsonObject.getInt("err")==0) {
					if (NetworkUtil.isOnline(PayOrderActivity.this)) {
						listAddresss.add(new PayAddressBean(jsonObject.getString("address_id"),SharedPreferencesConfig.getStringConfig(FuBaoApplication.getInstance(), "member_id")
								,consignee.getText().toString(),address.getText().toString()
								,phone.getText().toString()));
						listAddress.setAdapter(adapter=new CommonAdapter<PayAddressBean>(getApplicationContext(),listAddresss,R.layout.pay_address_list,true) {
							@Override
							public void convert(final ViewHolder helper, final PayAddressBean item,final int pos) {
								// TODO Auto-generated method stubch_address
							     if (ischek) {
							    	 chList.set(0, true);
								}
									
						
								helper.setText(R.id.tv_address, item.getAddress());
								helper.setText(R.id.tv_name, "姓名："+item.getConsignee());
								helper.setText(R.id.tv_phone,"电话："+item.getPhone());
								helper.getView(R.id.im_chage).setOnClickListener(new OnClickListener() {	
									@Override
									public void onClick(View arg0) {
										// TODO Auto-generated method stub
										final NiftyDialogBuilder dialogBuilder=new NiftyDialogBuilder(PayOrderActivity.this,R.style.Dialog_NoTitle);
										View view=LayoutInflater.from(mContext).inflate(R.layout.peopleinfo_dalog, null);
												//in(PayOrderActivity.class, R.layout.peopleinfo_dalog, helper.getConvertView());
										consignee=(EditText)view.findViewById(R.id.ed_name);
										address=(EditText)view.findViewById(R.id.ed_address);
										phone=(EditText)view.findViewById(R.id.ed_phone);
										consignee.setText(item.getConsignee());
										address.setText(item.getAddress());
										phone.setText(item.getPhone());
										dialogBuilder.setContentView(view);
										((Button)view.findViewById(R.id.btn_confirm)).setOnClickListener(new OnClickListener() {
											
											@Override
											public void onClick(View v) {
												// TODO Auto-generated method stub
											
												if (chekc()) {
													dialogBuilder.dismiss();
													Showloading();
													listAddresss.get(pos).setAddress(address.getText().toString());
													listAddresss.get(pos).setConsignee(consignee.getText().toString());
													listAddresss.get(pos).setPhone(phone.getText().toString());
													ShopApi.saveConsignee(item.getId(), consignee.getText().toString(), address.getText().toString(), phone.getText().toString(), saveConsigneehandler);
										
												}
										
											}
										});
										dialogBuilder.setButton1Click(new View.OnClickListener() {
						                    @Override
						                    public void onClick(View v) {
						                    	dialogBuilder.dismiss();
						                    }
						                })
						                .setButton2Click(new View.OnClickListener() {
						                    @Override
						                    public void onClick(View v) {
						                    	dialogBuilder.dismiss();
						                        //Toast.makeText(v.getContext(), "i'm btn2", Toast.LENGTH_SHORT).show();
						                    }
						                })
						                .show();
									
									
									
									}
								});
								helper.getView(R.id.im_del).setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										if (NetworkUtil.isOnline(mContext)) {
											listAddresss.remove(pos);
											Showloading();
											ShopApi.rmConsignee(item.getId(), rmConsigneehandler);
										}
										else {
											showToast("无网络连接");
										}
									}
								});
							}
						});
					
						
						//ShopApi.getConsigneeList(ConsigneeListheadl);
					}
					else {
					
					}
					
				}
				else
				{
					showToast(jsonObject.getString("msg"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
		public void onFailure(Throwable error) {
			Dissloading();
			showToastNetTime();
		};
	};
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/
        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            msg = "支付成功！";
            startActivity(new Intent(PayOrderActivity.this,MallOrderActivity.class));
            //跳转
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
            startActivity(new Intent(PayOrderActivity.this,MallOrderActivity.class));
            
            
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
            
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("支付结果通知");
        builder.setMessage(msg);
        builder.setInverseBackgroundForced(true);
        // builder.setCustomTitle();
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
