package com.hncainiao.fubao.ui.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hncainiao.fubao.R;

/**
 * 修改头像
 * 
 * @author liujie
 * 
 */
public class SelectPicPopupWindow extends PopupWindow {

	private TextView tvCamera, tvAlbum, tvCancel; // 拍照 从相册中选择 取消

	private View mView;

	public SelectPicPopupWindow(Context context, OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = inflater.inflate(R.layout.dialog_select_pic, null);

		tvCamera = (TextView) mView.findViewById(R.id.camera);
		tvAlbum = (TextView) mView.findViewById(R.id.album);
		tvCancel = (TextView) mView.findViewById(R.id.cancel);

		// 取消按钮
		tvCancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 销毁弹出框
				dismiss();
			}
		});
		// 设置按钮监听
		tvCamera.setOnClickListener(itemsOnClick);
		tvAlbum.setOnClickListener(itemsOnClick);
		// 设置SelectPicPopupWindow的View
		this.setContentView(mView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.AnimBottom);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#E9E9E9")));
		// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mView.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				int height = mView.findViewById(R.id.dialog_select_pic)
						.getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});
	}
}
