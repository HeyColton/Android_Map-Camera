package basicproject1.android.mapandcamera;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;

import basicproject1.android.mapandcamera.maps.MapsFragment;
import basicproject1.android.mapandcamera.utils.GestureInterface;
import basicproject1.android.mapandcamera.utils.TouchManager;

public class FullScreenActivity extends AppCompatActivity implements GestureInterface {
    private GestureDetector gd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        gd = new GestureDetector(this, new TouchManager(this));
        FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.fullScreenActivity, MapsFragment.newInstance())
                .commit();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        return gd.onTouchEvent(event);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        this.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onGestureRecognized(int gesture) {

        // react to callbacks
        switch (gesture) {
            case TouchManager.DOUBLE_TAP:
                // do something
                Intent intent=new Intent(FullScreenActivity.this,MainActivity.class);
                startActivity(intent);
        }
    }
}
