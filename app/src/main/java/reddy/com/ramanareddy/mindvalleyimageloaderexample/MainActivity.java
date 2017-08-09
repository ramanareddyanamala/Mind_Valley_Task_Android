package reddy.com.ramanareddy.mindvalleyimageloaderexample;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;

//import np.com.ramanareddy.mindvalleyimageloaderexample.R;

import np.com.ramanareddy.mindvalleyimageloaderexample.R;
import reddy.com.ramanareddy.httplibrarymindvalley.MindValleyHTTP;
import reddy.com.ramanareddy.httplibrarymindvalley.listeners.HttpListener;
import reddy.com.ramanareddy.httplibrarymindvalley.utils.CacheManager;
import reddy.com.ramanareddy.httplibrarymindvalley.datatypes.*;
import reddy.com.ramanareddy.mindvalleyimageloaderexample.common.Const;


public class MainActivity extends AppCompatActivity {
    //final String TAG=getClass().getSimpleName();
    Type<Bitmap> bitmap;
    CacheManager<JSONArray> jsonCacheManager;
    CacheManager<Bitmap> bitmapCacheManager;
    ImageView imgData;
    ProgressBar imgLoading;
    NetworkInfo activeNetworkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        imgData= (ImageView) findViewById(R.id.imgView);
        imgLoading =(ProgressBar) findViewById(R.id.imgLoading);
        imgLoading.setVisibility(View.GONE);


        jsonCacheManager=new CacheManager<JSONArray>(40*1024*1024);
        bitmapCacheManager= new CacheManager<>(40*1024*1024);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }



    public void btnCancelRequestClicked(View v){
        if(bitmap!=null)
            bitmap.cancel();
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
         activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void btnImageRequestClicked(View v){

        bitmap=MindValleyHTTP
                .from(MainActivity.this)
                .load(MindValleyHTTP.Method.GET, "https://images.unsplash.com/profile-1452199439559-1274b6758431?ixlib=rb-0.3.5\\u0026q=80\\u0026fm=jpg\\u0026crop=faces\\u0026fit=crop\\u0026h=64\\u0026w=64\\u0026s=1a6918cdb001510ddd5a96fb70854685")
                .asBitmap()
                .setCacheManager(bitmapCacheManager)
                .setCallback(new HttpListener<Bitmap>() {
                    @Override
                    public void onRequest() {
                        isNetworkAvailable();
                        if(activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                            imgLoading.setVisibility(View.VISIBLE);
                        }
                        else{
                            Toast.makeText(MainActivity.this,"OOPS!!! Please check Interent connection!!!",Toast.LENGTH_SHORT).show();

                        }
                    }


                    @Override
                    public void onResponse(Bitmap data) {
                        if(data!=null){
                            imgData.setImageBitmap(data);
                            imgLoading.setVisibility(View.GONE);
                        }
                        else {
                            Toast.makeText(MainActivity.this,"OOPS!!! Please check Interent connection!!!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError() {
                        imgLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancel() {
                        imgLoading.setVisibility(View.GONE);
                    }
                });
    }

    public void btnClearImage(View v) {
        imgData.setImageResource(R.drawable.image);
    }

    public void btnClearCache(View v) {
        bitmapCacheManager.removeDataFromCache(Const.SINGLE_IMAGE_URL);
    }

    public void btnLoadApiClicked(View v) {
        Intent intent= new Intent(this, UserListActivity.class);
        startActivity(intent);
    }


}
