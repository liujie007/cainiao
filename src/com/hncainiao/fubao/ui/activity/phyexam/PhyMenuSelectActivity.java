package com.hncainiao.fubao.ui.activity.phyexam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.application.FuBaoApplication;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.adapter.PhyMenuAdapter;
import com.hncainiao.fubao.ui.listener.OnClickAvoidForceListener;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author zhaojing
 * @version 2015年4月13日 下午2:17:59
 * 
 *          套餐选择
 */
public class PhyMenuSelectActivity extends BaseActivity {

	// private static final String TAG = "PhyMenuSelectActivity";

	private Context mContext;

	private ListView listView;

	private PhyMenuAdapter adapter;

	/**
	 * 默认 价格 人气
	 */
	private RadioButton rbDefault, rbPrice, rbHuman;

	/**
	 * 选择分类
	 */
	private RelativeLayout rlAll;
	private TextView tvALl;

	/**
	 * 分类向上 向下箭头
	 */
	private ImageView ivFilter;

	/**
	 * 体检项目分类
	 */
	private List<Button> buttons = null;

	/**
	 * 体检项目分类字段
	 */
	private List<String> strings = new ArrayList<String>();//名称
	private List<String> listidList=new ArrayList<String>();//id

	private View line;

	private  String hospital_id ;
	/**
	 * 弹出PopupWindow 后 后面背景变暗
	 */
	private RelativeLayout mCanversLayout;
	private String [] intentdate;;
	
	List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
	
	private void setupView() {
		setTitle("套餐选择");
		rlAll = (RelativeLayout) findViewById(R.id.rl_all);
		tvALl = (TextView) findViewById(R.id.tv_all);
		ivFilter = (ImageView) findViewById(R.id.iv_prolist_filter);
		ivFilter.setImageResource(R.drawable.prolist_filter_closed);
		rbDefault = (RadioButton) findViewById(R.id.rb_default);
		rbDefault.setChecked(true);
		rbPrice = (RadioButton) findViewById(R.id.rb_price);
		rbHuman = (RadioButton) findViewById(R.id.rb_human);
		line = (View) findViewById(R.id.line);
		mCanversLayout = (RelativeLayout) findViewById(R.id.rl_canvers);
	}

	private void addListener() {
		rlAll.setOnClickListener(l);
		rbDefault.setOnClickListener(l);
		rbPrice.setOnClickListener(l);
		rbHuman.setOnClickListener(l);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_phymenu);
		Intent intent =getIntent();
		if (intent!=null) {
			hospital_id=intent.getStringExtra("hospital_id");
		}
		mContext = PhyMenuSelectActivity.this;
		adapter = new PhyMenuAdapter(mContext);
		
		setupView();
		addListener();
		getNetListDate();
		getInitNetDate("",2);
		listView = (ListView) findViewById(R.id.lv_phy_menu_list);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext,PhyMenuDetailActivity.class);
				intent.putExtra("flag", 2);
				intent.putExtra("intendate", intentdate[position]);
				startActivity(intent);
			}
		});
		
	}

	private OnClickAvoidForceListener l = new OnClickAvoidForceListener() {

		@Override
		public void onClickAvoidForce(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.rl_all:
				if (mPopupWindow!=null) {
					if (mPopupWindow.isShowing()) {
						mPopupWindow.dismiss();
					} else {
						
						ivFilter.setImageResource(R.drawable.prolist_filter_opened);
						mCanversLayout.setVisibility(View.VISIBLE);
						mPopupWindow.showAsDropDown(line);
					}
				}
				else {
					showToast("没有分类信息！");
				}
				break;
			case R.id.rb_default:
				//默认
				getInitNetDate("",2);
				break;
			case R.id.rb_price:
				//星级
				getInitNetDate("star",1);
				break;
			case R.id.rb_human:
				//价格低
				getInitNetDate("current_price",2);
				break;
			}
		}
	};

/*	private List<Map<String, Object>> setData() {
		List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (int i = 0; i < 20; i++) {
			map = new HashMap<String, Object>();
			map.put("name", "爱心体检套餐" + (i + 1));
			map.put("detail", "老百姓一直在勤劳奋斗，当干部的决不能为官不为!");
			map.put("price", "￥" + (i + 100));
			mList.add(map);
		}
		return mList;
	}*/

	/**
	 * 得到套餐数据
	 */
	private void getInitNetDate(String price,int flag)
	{
		if (NetworkUtil.isOnline(mContext)) {
			AsyncHttpClient httpClient =new AsyncHttpClient();
			RequestParams params =new RequestParams();
			params.put("hospital_id", hospital_id);
			params.put("price_order", ""+price);
			params.put("orderkey", ""+price);
			if (flag==1) {
				params.put("orderby", "DESC");
			}
			else
			{
				params.put("orderby", "ASC");
			}
			
			showLog("得到套餐列表提交的参数:"+params.toString());
			httpClient.setTimeout(5000);
			httpClient.post(Constant.URL_LEI_STRING, params,new AsyncHttpResponseHandler()
			{
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					super.onStart();
					Showloading();
				}
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					 	Dissloading();
					 	
					 	if (!CheckJson(responseBody).equals("")) {
							//开始解析数据
					 	
					 		/*
					 		 * {"package":[{"id":"3","hospital_id":"6","cid":"3","name":"十一女性体检","remark":"十一女性体检","info":"十一女性体检十一女性体检十一女性体检十一女性体检十一女性体检十一女性体检十一女性体检","current_price":"700.00","market_price":"2800.00","star":"5","createtime":"1430733128","status":"1","sort":"255"},{"id":"2","hospital_id":"6","cid":"2","name":"五一男性体检","remark":"五一男性体检五一男性体检","info":"五一男性体检五一男性体检五一男性体检五一男性体检五一男性体检五一男性体检五一男性体检五一男性体检五一男性体检五一男性体检五一男性体检五一男性体检","current_price":"800.00","market_price":"2500.00","star":"4","createtime":"1430733099","status":"1","sort":"255"},{"id":"1","hospital_id":"6","cid":"1","name":"关爱体检套餐","remark":"关爱体检套餐关爱体检套餐","info":"关爱体检套餐关爱体检套餐关爱体检套餐关爱体检套餐关爱体检套餐关爱体检套餐关爱体检套餐关爱体检套餐关爱体检套餐关爱体检套餐关爱体检套餐","current_price":"500.00","market_price":"1000.00",
					 		 *   "star":"5","createtime":"1430732669","status":"1","sort":"255"}],"err":0}
					 		 */
					 		 
							try {
								JSONObject jsonObject = new JSONObject(new String(responseBody));
								 if (jsonObject.getInt("err")==0) {
							 			mList.clear();
							 			JSONArray jArray =jsonObject.getJSONArray("package");
							 			Map<String, Object> map = null;
							 			intentdate=new String[jArray.length()];
							 			for (int i = 0; i < jArray.length(); i++) {
							 				JSONObject jObject=jArray.getJSONObject(i);
							 				intentdate[i]=jObject.toString();
							 				map = new HashMap<String, Object>();
							 				map.put("name", jObject.getString("name"));
							 				map.put("detail", jObject.getString("info"));
							 				map.put("price", "￥"+jObject.getString("current_price"));
							 				mList.add(map);
										}
							 			adapter.setList(mList);
										listView.setAdapter(adapter);
									}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					 		
					 		
							
						}
					 	else {
							showToast("没有数据");
						}
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
						Dissloading();
						showToastNetTime();
				}
			});
		}
		else
		{
			showToastNotNet();
		}
	}
	/**
	 * 得到套餐分类数据
	 */
	private void getNetListDate()
	{
		if (NetworkUtil.isOnline(mContext)) {
			AsyncHttpClient httpClient =new AsyncHttpClient();
			RequestParams params =new RequestParams();
			params.put("hospital_id", hospital_id);
			showLog("得到套餐列表提交的参数:"+params.toString());
			httpClient.setTimeout(5000);
			httpClient.post(Constant.url_taocantype, params,new AsyncHttpResponseHandler()
			{
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					super.onStart();
					//Showloading();
				}
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					 	//Dissloading();
					 	/*
					 	 * {"category":[{"id":"1","name":"常规体检"},{"id":"2","name":"男性体检"},{"id":"3","name":"女性体检"},{"id":"4","name":"儿童体检"},{"id":"5","name":"中老年人体检"},{"id":"6","name":"教师体检"},{"id":"7","name":"入职体检"},{"id":"8","name":"全身体检"}],"err":0}
					 	 */
					    FuBaoApplication.getInstance().setInt(100);
					 	String date="{'taocan':[{'id':1,'str':'爱心套餐'},{'id':1,'str':'爱心套餐'},{'id':1,'str':'爱心套餐'}]}";
					 	if (!CheckJson(responseBody).equals("")) {
							//开始解析数据
					 		try {
								JSONObject jsonObject =new JSONObject(new String(responseBody));
								if (jsonObject.getInt("err")==0) {
									strings.clear();
							      JSONArray jsonArray =jsonObject.getJSONArray("category");
								for (int i = 0; i < jsonArray.length(); i++) {
									
									JSONObject jsonObject2=jsonArray.getJSONObject(i);
									listidList.add(jsonObject2.getInt("id")+"");
									strings.add(""+jsonObject2.getString("name"));
								}
								 strings.add("全部");
						 	   	initPopupWindow();
								}
								else
								{
									showToast("没有数据");
								}
					 		} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					 		
						}
					 	else {
							showToast("没有数据");
						}
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
						//Dissloading();
						showToastNetTime();
				}
			});
		}
		else
		{
			showToastNotNet();
		}
	}


	/**
	 * 搜索套餐
	 */
	public void getSoso(String keyword)
	{
		if (NetworkUtil.isOnline(mContext)) {
			
			AsyncHttpClient httpClient =new AsyncHttpClient();
			RequestParams params =new RequestParams();
			//params.put("keyword", keyword);
	        params.put("cid", keyword);
			showLog("搜索套餐提交的参数:"+params.toString());
			httpClient.setTimeout(5000);
			httpClient.post(Constant.URL_TAOSANSOSO_STRING, params,new AsyncHttpResponseHandler()
			{
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					super.onStart();
					Showloading();
				}
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					 	Dissloading();
					 	mList.clear();
					 	String date="{'taocan':[{'id':1,'str':'爱心套餐'},{'id':1,'str':'爱心套餐'},{'id':1,'str':'爱心套餐'}]}";
					 	if (!CheckJson(responseBody).equals("")) {
					 		 
							try {
								JSONObject jsonObject = new JSONObject(new String(responseBody));
								 if (jsonObject.getInt("err")==0) {
							 			
							 			JSONArray jArray =jsonObject.getJSONArray("package");
							 			Map<String, Object> map = null;
							 			intentdate=new String[jArray.length()];
							 			for (int i = 0; i < jArray.length(); i++) {
							 				JSONObject jObject=jArray.getJSONObject(i);
							 				intentdate[i]=jObject.toString();
							 				map = new HashMap<String, Object>();
							 				map.put("name", jObject.getString("name"));
							 				map.put("detail", jObject.getString("info"));
							 				map.put("price", "￥"+jObject.getString("current_price"));
							 				mList.add(map);
										}
							 			
									}
								 else
								 {
										showToast("没有数据");
								 }
								    adapter.setList(mList);
									listView.setAdapter(adapter);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					 		
					 		
						}
					 	else {
							showToast("没有数据");
						}
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
						Dissloading();
						showToastNetTime();
				}
			});
		}
		else
		{
			showToastNotNet();
		}
	}
	private View view;

	private PopupWindow mPopupWindow;

	private LinearLayout llKinds;
	
	/**
	 * 初始化PopupWindow
	 */
	@SuppressLint("ResourceAsColor") private void initPopupWindow() {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		view = inflater.inflate(R.layout.layout_phyexam_menu_kind, null);

		llKinds = (LinearLayout) view.findViewById(R.id.ll_phyexam_kinds);
	
		buttons = createListButtonLayout(strings, llKinds);
		//SharedPreferencesConfig.saveIntConfig(mContext, "btn_id", buttons.size()-1);
		for (int i = 0; i < buttons.size(); i++) {
			final Button button = buttons.get(i);
			button.setTag(i);//为按钮设置一个标记，来确认是按下了哪一个按钮
			if (FuBaoApplication.getInstance().getInt()==100) {
				FuBaoApplication.getInstance().setInt(buttons.size()-1);
			}
			buttons.get(FuBaoApplication.getInstance().getInt()).setBackgroundResource(R.color.blue);
			buttons.get(FuBaoApplication.getInstance().getInt()).setTextColor(getResources().getColor(R.color.white));
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int id = Integer.parseInt(button.getTag().toString());
					tvALl.setText(buttons.get(id).getText().toString());
					//SharedPreferencesConfig.saveIntConfig(mContext, "btn_id", id);
					FuBaoApplication.getInstance().setInt(id);
					for (int j = 0; j < buttons.size(); j++) {
						buttons.get(j).setBackgroundResource(R.drawable.bg_button_phymenu_selectd);
						buttons.get(j).setTextColor(getResources().getColor(R.color.black));
					}
					buttons.get(FuBaoApplication.getInstance().getInt()).setTextColor(getResources().getColor(R.color.white));
					buttons.get(FuBaoApplication.getInstance().getInt()).setBackgroundResource(R.color.blue);
					if (mPopupWindow.isShowing()) {
						mPopupWindow.dismiss();
						//加载网络数据
						if (buttons.get(id).getText().toString().equals("全部")) {
							getInitNetDate("",2);
						}
						else
						{
							//getSoso(""+buttons.get(id).getText().toString());
							getSoso(""+listidList.get(id));
						}
						
					}
				}
			});
		}

		mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#E9E9E9")));
		mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.update();
		mPopupWindow.setOnDismissListener(new OnDismissListener() { // 监听对话框消失

					@Override
					public void onDismiss() {
						// TODO Auto-generated method stub
						ivFilter.setImageResource(R.drawable.prolist_filter_closed);
						mCanversLayout.setVisibility(View.GONE);
					}
				});
	}

	/**
	 * @param list
	 *            文件名称集合 content:创建列表文件夹
	 */
	List<Button> createListButtonLayout(List<String> list,
			LinearLayout linear_userSearch) {
		List<Button> buts = new ArrayList<Button>();
		List<Integer> values = getFileLayoutValues(list, ButtonWidth);// 获取文件列表布局的属性
		int rows = values.get(0);// 文件需要占几行
		int column_fileNum = values.get(1);// 每列文件数目
		int surplus = values.get(2);// 不满一列个数的文件数
		// 生成文件列表布局
		for (int i = 0; i < rows; i++) {
			if (surplus == 0) {
				linear_userSearch.addView(createRowFileLayout(i, column_fileNum,column_fileNum, buts, ButtonWidth, list));
			} else {
				if (i == rows - 1) {// 是否是最后一行
					linear_userSearch.addView(createRowFileLayout(rows - 1,column_fileNum, surplus, buts, ButtonWidth, list));
				} else {
					linear_userSearch.addView(createRowFileLayout(i,column_fileNum, column_fileNum, buts, ButtonWidth,list));
				}
			}
		}
		return buts;
	}

	/**
	 * 创建Button
	 * 
	 * @param style
	 *            Button样式
	 * @return Button
	 */
	public Button createButton(int width, int height, int style, int textcolor) {
		Button button = new Button(mContext);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,
				height);
		params.leftMargin = 20;
		params.rightMargin = 20;
		
		button.setLayoutParams(params);
		button.setBackgroundResource(style);
		button.setTextColor(textcolor);
		button.setTextSize(14);
		return button;
	}

	/**
	 * @param orientation
	 * @param width
	 * @param height
	 * @return LinearLayout content:创建线性布局（每个文件夹所在的区域）
	 */
	LinearLayout createLinearLayout(int orientation, int width, int height) {
		LinearLayout linear = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,
				height);
		linear.setLayoutParams(params);
		linear.setGravity(Gravity.CENTER_HORIZONTAL);
		linear.setOrientation(orientation);
		return linear;
	}

	/**
	 * @param orientation
	 * @param width
	 * @param height
	 * @return LinearLayout content:创建线性布局（每行）
	 */
	LinearLayout createLinearLayout_row(int orientation, int width, int height) {
		LinearLayout linear = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,
				height);
		params.topMargin = 15;
		params.rightMargin = 20;
		params.leftMargin = 20;
		params.bottomMargin = 15;
		
		linear.setLayoutParams(params);
		linear.setOrientation(orientation);
		//linear.setGravity(Gravity.CENTER);
		return linear;
	}

	/**
	 * @param fileNameList
	 * @param textViewWidth
	 *            : 文件名称所在控件的宽度
	 * @return 属性值集合 content
	 *         :获取创建布局的属性（文件总共需要占据屏幕的行数，每行文件的个数，不满一列个数的文件数(最后一列文件的个数)）
	 */
	List<Integer> getFileLayoutValues(List<String> fileNameList,
			int textViewWidth) {
		List<Integer> values = new ArrayList<Integer>();
		int size = fileNameList.size();// 文件的个数
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels - 60;// 屏幕宽
		int rows = 0;// 文件需要占几行数(屏幕)
		int column_fileNum = width / (ButtonWidth + 10);// 每行文件数目
		int surplus = size % column_fileNum;// 不满一列个数的文件数(最后一列文件的个数)
		if (size < column_fileNum) {// 如果文件数少于每行陈列的文件数
			rows = 1;
		} else {
			if (size % column_fileNum == 0) {
				rows = size / column_fileNum;
			} else if (size % column_fileNum != 0) {
				rows = size / column_fileNum + 1;
			}
		}
		values.add(rows);
		values.add(column_fileNum);
		values.add(surplus);
		return values;
	}

	int ButtonWidth = 180;// 文件名称所在控件的宽度

	/**
	 * 创建每行文件的布局
	 * 
	 * @param i
	 * @param fileNum
	 * @param buts
	 *            :将创建的button放入集合 buts
	 * @param textViewWidth
	 *            作用：根据textView的宽度计算每行应放置多少个文件夹
	 * @return linearLayout(包含文件夹) content :线性布局，显示一行文件夹（Button 和 TextView的组合）
	 */
	LinearLayout createRowFileLayout(int i, int row_fileNum,
			int lastrow_fileNum, List<Button> buts, int textViewWidth,
			List<String> classs) {
		// 创建布局每行
		LinearLayout linear = createLinearLayout_row(LinearLayout.HORIZONTAL,
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		
			//linear.setGravity(Gravity.CENTER);
		// 向每行添加子linearLayout
		for (int j = 0; j < lastrow_fileNum; j++) {
			Button button = createButton(ButtonWidth, 70,
					R.drawable.bg_button_phymenu_selectd,
					R.drawable.button_phymenu_selectd_textcolor);// 设置默认背景
			button.setText(classs.get(i * row_fileNum + j));
			linear.addView(button);
			buts.add(button);
		}
		return linear;
	}
}
