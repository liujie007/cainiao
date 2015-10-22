package com.hncainiao.fubao.ui.views;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.hncainiao.fubao.R;

/**
 * @author zhaojing
 * @version 2015年04月09日 下午10:40:22
 * 
 *          自定义的AlertDialog
 */
public class CustomAlertDialog {

	private Context mContext;
	private AlertDialog dialog;
	private TextView tvTitle, tvMsg;
	private Button btnCancel, btnCofirm;

	public CustomAlertDialog(Context context, OnClickListener onClickListener) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		dialog = new AlertDialog.Builder(context).create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		// 使用window.setContentView,替换整个对话框窗口的布局
		Window window = dialog.getWindow();
		window.setContentView(R.layout.custom_alertdialog);
		tvTitle = (TextView) window
				.findViewById(R.id.tv_custom_alertdialog_title);
		tvMsg = (TextView) window.findViewById(R.id.tv_custom_alertdialog_msg);
		btnCancel = (Button) window
				.findViewById(R.id.btn_custom_alertdialog_cancel);
		btnCofirm = (Button) window
				.findViewById(R.id.btn_custom_alertdialog_confirm);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
		btnCofirm.setOnClickListener(onClickListener);
	}

	public void setTitle(int resId) {
		tvTitle.setText(resId);
	}

	public void setTitle(String title) {
		tvTitle.setText(title);
	}

	public void setMessage(int resId) {
		tvMsg.setText(resId);
	}

	public void setMessage(String message) {
		tvMsg.setText(message);
	}

	/** * 关闭对话框 */
	public void dismiss() {
		if (dialog != null)
			dialog.dismiss();
	}
}
