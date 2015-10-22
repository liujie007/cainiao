package com.hncainiao.fubao.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class NobarsorScorview extends ScrollView {
	float lastX;
	float lastY;

	public NobarsorScorview(Context context) {
		super(context);
		
	}
	
    public NobarsorScorview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NobarsorScorview(Context context, AttributeSet attrs,
        int defStyle) {
        super(context, attrs, defStyle);
   }
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = false;
        
        
		
		switch (ev.getAction()) {
        case MotionEvent.ACTION_DOWN:

                lastX = ev.getX();
                lastY = ev.getY();
                break;
        case MotionEvent.ACTION_MOVE:
                int distanceX =(int) Math.abs( ev.getX() - lastX);
                int distanceY = (int) Math.abs(ev.getY()-lastY);
                
                if(distanceX>distanceY && distanceX>10){
                        result = true;
                }else{
                        result = false;
                }
                break;

        default:
                break;
        }
        
        return result;
}
	


}
