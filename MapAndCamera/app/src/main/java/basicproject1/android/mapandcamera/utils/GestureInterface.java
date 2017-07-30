package basicproject1.android.mapandcamera.utils;

/**
 * Created by zhengxiaoyu on 7/6/17.
 */


    public interface GestureInterface {
        /**
         * returns the recognized gesture from the touchmanager
         * and enables the user of the interface to react to the gesture (or not)
         * @param gesture e.g. TouchManager.SWIPE_LEFT
         */
        public void onGestureRecognized(int gesture);
    }

