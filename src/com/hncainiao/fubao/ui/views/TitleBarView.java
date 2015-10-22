package com.hncainiao.fubao.ui.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.utils.SystemMethod;

/**
 * @author zhaojing
 * @version 2015年4月1日 上午11:30:17
 * 
 *          标题栏
 */
public class TitleBarView extends RelativeLayout {

	private Context mContext;

	private Button btnLeft; // 左边的按钮

	private Button btnRight; // 右边的按钮

	private TextView tvTitle; // 标题

	public TitleBarView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		setupView();
	}

	public TitleBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		setupView();
	}

	private void setupView() {
		LayoutInflater.from(mContext).inflate(R.layout.layout_common_title, null);
		btnLeft = (Button) findViewById(R.id.title_btn_left);
//		btnRight = (Button) findViewById(R.id.title_btn_right);
		tvTitle = (TextView) findViewById(R.id.title_txt);
	}

	public void setCommonTitle(int LeftVisibility, int centerVisibility,
			int rightVisibility) {
		btnLeft.setVisibility(LeftVisibility);
		btnRight.setVisibility(rightVisibility);
		tvTitle.setVisibility(centerVisibility);
	}

	public void setBtnLeft(int icon) {
		Drawable img = mContext.getResources().getDrawable(icon);
		int height = SystemMethod.dip2px(mContext, 30);
		int width = img.getIntrinsicWidth() * height / img.getIntrinsicHeight();
		img.setBounds(0, 0, width, height);
		btnRight.setCompoundDrawables(img, null, null, null);
	}

	public void setBtnLeft(int icon, int txtRes) {
		Drawable img = mContext.getResources().getDrawable(icon);
		int height = SystemMethod.dip2px(mContext, 20);
		int width = img.getIntrinsicWidth() * height / img.getIntrinsicHeight();
		img.setBounds(0, 0, width, height);
		btnLeft.setText(txtRes);
		btnLeft.setCompoundDrawables(img, null, null, null);
	}

	public void setBtnRight(int icon) {
		Drawable img = mContext.getResources().getDrawable(icon);
		int height = SystemMethod.dip2px(mContext, 30);
		int width = img.getIntrinsicWidth() * height / img.getIntrinsicHeight();
		img.setBounds(0, 0, width, height);
		btnRight.setCompoundDrawables(img, null, null, null);
	}

	public void setTitleText(int txtRes) {
		tvTitle.setText(txtRes);
	}

	public void setBtnLeftOnclickListener(OnClickListener listener) {
		btnLeft.setOnClickListener(listener);
	}

	public void setBtnRightOnclickListener(OnClickListener listener) {
		btnRight.setOnClickListener(listener);
	}

	public void destoryView() {
		btnLeft.setText(null);
		btnRight.setText(null);
		tvTitle.setText(null);
	}
}
