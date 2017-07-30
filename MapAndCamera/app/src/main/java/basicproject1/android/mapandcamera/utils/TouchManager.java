package basicproject1.android.mapandcamera.utils;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by zhengxiaoyu on 7/6/17.
 */

public class TouchManager extends GestureDetector.SimpleOnGestureListener {
    public static final int         DOWN                    = 1;
    public static final int         DOUBLE_TAP              = 2;

    /** the class that initialized the gesture-recognizer and will be notified upon gestures made by the user */
    private GestureInterface        caller;

    /**
     * constructor
     * @param  caller that implements the gestureinterface
     */
    public TouchManager(GestureInterface caller) {
        this.caller = caller;
    }

    /**
     * you need this shit to return true, otherwise gestures wont work
     */
    @Override
    public boolean onDown(MotionEvent e) {
        caller.onGestureRecognized(DOWN);
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        caller.onGestureRecognized(DOUBLE_TAP); // callback
        return true;
    }
}
