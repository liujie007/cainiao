package com.hncainiao.fubao.ui.views;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author zhaojing
 * @version 2010年8月9日 下午1:36:47
 * 
 */
public class CustomerDialog extends Dialog {

	private Context context;

	public CustomerDialog(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.context = context;
		setCanceledOnTouchOutside(true);
	}

	public void setDialogView(int layoutId) {
		setContentView(layoutId);

	}

	public void setDialogView(View view) {
		setContentView(view);

	}

	public void setDialogGravity(int gravity) {
		getWindow().setGravity(gravity); // 此处可以设置dialog显示的位置
	}

	public void setDialogLayout(int w, int h) {
		getWindow().setLayout(w, h);

	}

	public void setDialogAnimiation(int style) {
		getWindow().setWindowAnimations(style);
	}

	/**
	 * lp.x与lp.y表示相对于原始位置的偏移.
	 * 当参数值包含Gravity.LEFT时,对话框出现在左边,所以lp.x就表示相对左边的偏移,负值忽略.
	 * 当参数值包含Gravity.RIGHT时,对话框出现在右边,所以lp.x就表示相对右边的偏移,负值忽略.
	 * 当参数值包含Gravity.TOP时,对话框出现在上边,所以lp.y就表示相对上边的偏移,负值忽略.
	 * 当参数值包含Gravity.BOTTOM时,对话框出现在下边,所以lp.y就表示相对下边的偏移,负值忽略.
	 * 当参数值包含Gravity.CENTER_HORIZONTAL时
	 * ,对话框水平居中,所以lp.x就表示在水平居中的位置移动lp.x像素,正值向右移动,负值向左移动.
	 * 当参数值包含Gravity.CENTER_VERTICAL时
	 * ,对话框垂直居中,所以lp.y就表示在垂直居中的位置移动lp.y像素,正值向右移动,负值向左移动.
	 * gravity的默认值为Gravity.CENTER,即Gravity.CENTER_HORIZONTAL |
	 * Gravity.CENTER_VERTICAL.
	 * 
	 * 本来setGravity的参数值为Gravity.LEFT | Gravity.TOP时对话框应出现在程序的左上角,但在
	 * 我手机上测试时发现距左边与上边都有一小段距离,而且垂直坐标把程序标题栏也计算在内了, Gravity.LEFT, Gravity.TOP,
	 * Gravity.BOTTOM与Gravity.RIGHT都是如此,据边界有一小段距离
	 */
	public void setDialogPostion(int graity, int offx, int offy, float w,
			float h) {

		// Log.d("","FLAG    "+"offx="+offx +"  offy="+offy +" w="+w +"h="+h);
		Window dialogWindow = getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(graity);
		lp.x = offx; // 新位置X坐标
		lp.y = offy; // 新位置Y坐标
		lp.width = (int) w; // 宽度
		lp.height = (int) h; // 高度
		dialogWindow.setAttributes(lp);

	}

	public void setDilaogCancelAble(boolean isCanCancl) {
		setCancelable(isCanCancl);
	}

	public void setDialgCancelOutSide(boolean isCancel) {
		setCanceledOnTouchOutside(isCancel);
	}
}
