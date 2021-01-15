package com.example.viralyapplication.utility;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class VerticalListView extends ListView {

    private boolean isPressed = false, isMoved = false;
    private PointF lastPointPressSwipe = new PointF();

    private boolean disableHeaderTouch = false;

    public VerticalListView(Context context) {
        super(context);
    }

    public VerticalListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setDisableHeaderTouch(boolean enable) {
        disableHeaderTouch = enable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.getChildAt(0) != null && event.getY(0) < this.getChildAt(0).getBottom() * 3 / 4 && disableHeaderTouch)
            return false;
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isPressed = true;
                lastPointPressSwipe.set(event.getX(0), event.getY(0));
                break;

            case MotionEvent.ACTION_UP:
                if (lastPointPressSwipe.x != -1 && isMoved
                        && Math.abs(event.getX(0) - lastPointPressSwipe.x) >
                        2 * Math.abs(event.getY(0) - lastPointPressSwipe.y)) {
                    isPressed = false;
                    isMoved = false;
                    lastPointPressSwipe.set(-1F, -1F);
                    return false;
                }
                isPressed = false;
                isMoved = false;
                lastPointPressSwipe.set(-1F, -1F);
                break;

            case MotionEvent.ACTION_MOVE:
                isMoved = true;
                if (lastPointPressSwipe.x != -1 && isMoved
                        && Math.abs(event.getX(0) - lastPointPressSwipe.x) >
                        2 * Math.abs(event.getY(0) - lastPointPressSwipe.y)) {
                    return false;
                }
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

}
