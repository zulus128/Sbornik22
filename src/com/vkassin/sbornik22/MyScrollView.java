package com.vkassin.sbornik22;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {

	public MyScrollView(Context context) {

		super(context);
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		
		super(context, attrs);
	}

	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent p_event) {

		return false;
	}

//	@Override
//	public boolean onTouchEvent(MotionEvent p_event) {
//	    if (p_event.getAction() == MotionEvent.ACTION_MOVE && getParent() != null) {
//	        getParent().requestDisallowInterceptTouchEvent(true);
//	    }
//	    return super.onTouchEvent(p_event);
//		
//	}

}
