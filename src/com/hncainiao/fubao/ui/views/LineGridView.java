package com.hncainiao.fubao.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

/**
 * @author zhaojing
 * @version 2010年8月7日 下午5:14:29
 * 
 *          给GridView添加分割线
 */
public class LineGridView extends GridView {

	public LineGridView(Context context) {
		super(context); // TODO Auto-generated constructor stub
	}

	public LineGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LineGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		View localView1 = getChildAt(0);
		int column = getWidth() / localView1.getWidth();
		int childCount = getChildCount();
		Paint localPaint;
		localPaint = new Paint();
		localPaint.setStyle(Paint.Style.STROKE);
		localPaint.setColor(Color.rgb(161, 161, 161));
		for (int i = 0; i < childCount; i++) {
			View cellView = getChildAt(i);
			if ((i + 1) % column == 0) {
				canvas.drawLine(cellView.getLeft()+20, cellView.getBottom()+20,
						cellView.getRight()+20, cellView.getBottom()+20, localPaint);
			} else if ((i + 1) > (childCount - (childCount % column))) {
				canvas.drawLine(cellView.getRight()+20, cellView.getTop()+20,
						cellView.getRight()+20, cellView.getBottom()+20, localPaint);
			} else {
				canvas.drawLine(cellView.getRight()+20, cellView.getTop()+20,
						cellView.getRight()+20, cellView.getBottom()+20, localPaint);
				canvas.drawLine(cellView.getLeft()+20, cellView.getBottom()+20,
						cellView.getRight()+20, cellView.getBottom()+20, localPaint);
			}
		}
		if (childCount % column != 0) {
			for (int j = 0; j < (column - childCount % column); j++) {
				View lastView = getChildAt(childCount - 1);
				canvas.drawLine(lastView.getRight()+20 + lastView.getWidth() * j,
						lastView.getTop()+20,
						lastView.getRight() + lastView.getWidth() * j,
						lastView.getBottom()+20, localPaint);
			}
		}
	}
}
