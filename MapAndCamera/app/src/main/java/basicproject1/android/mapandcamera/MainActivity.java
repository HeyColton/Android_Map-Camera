package basicproject1.android.mapandcamera;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import basicproject1.android.mapandcamera.maps.MapsFragment;
import basicproject1.android.mapandcamera.utils.GestureInterface;
import basicproject1.android.mapandcamera.utils.TouchManager;

public class MainActivity extends AppCompatActivity implements
       GestureInterface {
    private static final String TAG = "MainActivity";
    private MapsFragment mMapsFragment;
    private GestureDetector gd;
    private boolean fullScreen=false;
    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gd = new GestureDetector(this, new TouchManager(this));
        FragmentManager manager = getSupportFragmentManager();


        mMapsFragment = (MapsFragment)manager.findFragmentById(R.id.Main_Map);
        if(mMapsFragment == null) {
            mMapsFragment = MapsFragment.newInstance();
            manager.beginTransaction()
                    .add(R.id.Main_Map, mMapsFragment)
                    .commit();
        }

//        SeekBar zoomBar = (SeekBar)findViewById(R.id.zoombar);
//        int max = (int)(MapsFragment.MAXIMUM_ZOOM - MapsFragment.MINIMUM_ZOOM);
//        zoomBar.setMax(max);
//        zoomBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                mMapsFragment.updateZoom(progress + MapsFragment.MINIMUM_ZOOM);
//            }

//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.fragment_maps, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        boolean handled;
//        switch(item.getItemId()) {
//            case R.id.mi_current_location:
//                mMapsFragment.updateToCurrentLocation();
//                handled = true;
//                break;
//            default:
//                handled = super.onOptionsItemSelected(item);
//                break;
//        }
//        return handled;
//    }


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
            Log.d(TAG,"onGestureRecognized");
              Intent intent=new Intent(MainActivity.this,FullScreenActivity.class);
                startActivity(intent);
        }
    }
}
