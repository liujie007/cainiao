package com.hncainiao.fubao.ui.fragment;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.model.City;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.adapter.Stlect_othercity_apr;
import com.hncainiao.fubao.ui.sqlite.Create_sqlite_datas;
import com.hncainiao.fubao.utils.ToastManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Select_Other_city extends BaseActivity {

	private Context mContext;
	private GridView gridView;
	List<City> cities = null, cityhot = null;
	AutoCompleteTextView auto_city;
	LinearLayout mengceng;
	String arrs[];
	SQLiteDatabase db;
	ImageView delcity;
	TextView reshf, current_city;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_select_city);
		initView();
		// 获取热门城市
		gethotcity();
		initlisten();
		// 添加数据库
		getCitylist();
		// 自动补全控件获取数据
		getautodata();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, arrs);
		auto_city.setAdapter(adapter);
		auto_city.setThreshold(1);
	}

	private void initlisten() {
		auto_city.setOnClickListener(new click());
		mengceng.setOnClickListener(new click());
		reshf.setOnClickListener(new click());
		//auto_city.setOnFocusChangeListener(new listen1());
		delcity.setOnClickListener(new click());
		gridView.setOnItemClickListener(new listen());

	}

	class listen1 implements OnFocusChangeListener {

		@Override
		public void onFocusChange(View arg0, boolean arg1) {

			if (arg0.getId() == R.id.auto_city) {
				if (!arg1) {
					if (auto_city.getText().toString().length() > 0) {
						delcity.setVisibility(ViewGroup.VISIBLE);
					} else if (auto_city.getText().toString().length() == 0) {
						delcity.setVisibility(ViewGroup.GONE);
					}
				}

			}

		}

	}

	class listen implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (arg0.getId() == R.id.SELECT_City) {
				String con = cityhot.get(arg2).getName();
				if (con.length() == 0) {
					ToastManager.getInstance(mContext).showToast("请输入要切换的城市");
				} else {
					Cursor re1 = db
							.rawQuery(
									"select count(*) as total from Citys where citynm=?",
									new String[] { con });
					re1.moveToFirst();
					int total = re1.getInt(re1.getColumnIndex("total"));
					if (total <= 0) {
						Toast.makeText(mContext, "您选择的城市不存在", 1).show();
						return;
					} else {
						SharedPreferencesConfig.saveStringConfig(mContext,
								"city", con);
						// Intent intent=new
						// Intent(mContext,RegistrationActivity.class);
						// startActivity(intent);
						finish();
					}

				}
			}

		}

	}

	class click implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {

			if (arg0.getId() == R.id.resh) {
				mengceng.setVisibility(ViewGroup.GONE);
				String con = auto_city.getText().toString().trim();
				if (con.length() == 0) {
					ToastManager.getInstance(mContext).showToast("请输入要切换的城市");
				} else {
					Cursor re1 = db
							.rawQuery(
									"select count(*) as total from Citys where citynm=?",
									new String[] { con });
					re1.moveToFirst();
					int total = re1.getInt(re1.getColumnIndex("total"));
					if (total <= 0) {
						Toast.makeText(mContext, "您选择的城市不存在", 1).show();
						return;
					} else {
						SharedPreferencesConfig.saveStringConfig(mContext,
								"city", con);

						finish();
					}

				}

			} else if (arg0.getId() == R.id.lin_three) {
				mengceng.setVisibility(ViewGroup.GONE);

			} else if (arg0.getId() == R.id.auto_city) {

				mengceng.setVisibility(ViewGroup.VISIBLE);

			} else if (arg0.getId() == R.id.del_city) {
				auto_city.setText("");
				delcity.setVisibility(ViewGroup.GONE);

			}

		}

	}

	private void gethotcity() {
		cityhot = new ArrayList<City>();
		AsyncHttpClient client = new AsyncHttpClient();
		String url = Constant.MORENCITY;
		RequestParams params = new RequestParams();
		params.put("number", "12");
		client.setTimeout(5000);
		client.post(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				System.out.println("---------------------"
						+ new String(responseBody));
				if (!CheckJson(responseBody).equals("")) {
					try {
						JSONObject object = new JSONObject(new String(
								responseBody));
						if (object.getInt("err") == 0) {
							JSONArray array = object.getJSONArray("region");
							for (int i = 0; i < array.length(); i++) {
								cityhot.add(new City(array.getJSONObject(i)
										.getString("name")));
							}
							Stlect_othercity_apr apr = new Stlect_othercity_apr(cityhot, mContext);
							gridView.setAdapter(apr);

						}

					} catch (Exception e) {
						
					}
				}

				super.onSuccess(statusCode, headers, responseBody);
			}

		
		});

		

	}

	private void getautodata() {
		Cursor re = db.rawQuery(
				"select count(*) as total from Citys where _id>?",
				new String[] { 0 + "" });
		re.moveToFirst();
		int nums = re.getInt(re.getColumnIndex("total"));
		arrs = new String[nums];
		Cursor res = db.rawQuery("select * from Citys where _id>?",
				new String[] { "0" });
		int dex = 0;
		while (res.moveToNext()) {
			arrs[dex] = res.getString(res.getColumnIndex("citynm"));
			dex++;
		}
	}

	private void initView() {
		mContext = this;
		((TextView) findViewById(R.id.title_txt)).setText("选择城市");
		Create_sqlite_datas sqlite_datas = new Create_sqlite_datas(mContext);
		db = sqlite_datas.getWritableDatabase();
		gridView = (GridView) findViewById(R.id.SELECT_City);
		delcity = (ImageView) findViewById(R.id.del_city);
		auto_city = (AutoCompleteTextView) findViewById(R.id.auto_city);
		mengceng = (LinearLayout) findViewById(R.id.lin_three);
		reshf = (TextView) findViewById(R.id.resh);
		current_city = (TextView) findViewById(R.id.current_city);
		current_city.setText(SharedPreferencesConfig.getStringConfig(mContext,
				"city"));
	}

	private void getCitylist() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(5000);
		String url = Constant.MORENCITY;
		client.post(url, new AsyncHttpResponseHandler() {
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				System.out.println("所有城市------"
						+ new String(responseBody).length());
				cities = new ArrayList<City>();
				try {

					JSONObject object = new JSONObject(new String(responseBody));
					if (object.getInt("err") == 0) {
						JSONArray array = object.getJSONArray("region");
						for (int i = 0; i < array.length(); i++) {
							cities.add(new City(array.getJSONObject(i)
									.getString("name")));
						}

					}
					System.out.println("添加數據" + cities.size());

					Cursor tt = db.rawQuery(
							"select count(*) as total from Citys where _id>?",
							new String[] { 0 + "" });
					tt.moveToFirst();
					int nums = tt.getInt(tt.getColumnIndex("total"));

					System.out.println("shuliang" + nums);

					if (nums == 0) {
						for (City city : cities) {
							db.execSQL("insert into Citys(citynm) values('"
									+ city.getName() + "')");
						}
						ToastManager.getInstance(mContext).showToast("添加数据库成功");
					} else if (nums > 370) {
						db.execSQL("delete from Citys where _id>0");
						for (City city : cities) {
							db.execSQL("insert into Citys(citynm) values('"
									+ city.getName() + "')");
						}
						ToastManager.getInstance(mContext).showToast("重新添加了數據");
					}
				} catch (Exception e) {

				}
				super.onSuccess(statusCode, headers, responseBody);
			}

		

		});

	}

}
