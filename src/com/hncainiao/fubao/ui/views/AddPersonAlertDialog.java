package com.hncainiao.fubao.ui.views;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.hncainiao.fubao.R;

/**
 * @author zhaojing
 * @version 2015年1月14日 下午10:40:22
 * 
 *          自定义的AlertDialog
 */
public class AddPersonAlertDialog {

	private Context context;
	private AlertDialog dialog;

	private EditText etName, etID, etCard, etPhone, etRelation;

	private Button btnCancel, btnConfirm;

	public AddPersonAlertDialog(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		dialog = new AlertDialog.Builder(context).create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		// 关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
		Window window = dialog.getWindow();
		window.setContentView(R.layout.add_person_alertdialog);

		etName = (EditText) window.findViewById(R.id.et_add_dialog_name);
		etID = (EditText) window.findViewById(R.id.et_add_dialog_id);
		etCard = (EditText) window.findViewById(R.id.et_add_dialog_card);
		etPhone = (EditText) window.findViewById(R.id.et_add_dialog_phone);

		//etRelation = (EditText) window
				//.findViewById(R.id.et_add_dialog_relation);

		

		btnCancel = (Button) window.findViewById(R.id.btn_add_dialog_cancel);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
		btnConfirm = (Button) window.findViewById(R.id.btn_add_dialog_confirm);
	}

	/** * 关闭对话框 */
	public void dismiss() {
		if (dialog != null)
			dialog.dismiss();
	}
}
