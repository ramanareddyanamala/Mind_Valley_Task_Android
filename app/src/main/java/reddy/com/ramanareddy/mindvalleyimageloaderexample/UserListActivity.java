package reddy.com.ramanareddy.mindvalleyimageloaderexample;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import np.com.ramanareddy.mindvalleyimageloaderexample.R;
import reddy.com.ramanareddy.httplibrarymindvalley.MindValleyHTTP;
import reddy.com.ramanareddy.httplibrarymindvalley.listeners.HttpListener;
import reddy.com.ramanareddy.httplibrarymindvalley.utils.CacheManager;
import reddy.com.ramanareddy.mindvalleyimageloaderexample.adapters.UserListAdapter;
import reddy.com.ramanareddy.mindvalleyimageloaderexample.common.Const;
import reddy.com.ramanareddy.mindvalleyimageloaderexample.models.User;

public class UserListActivity extends AppCompatActivity {

    RecyclerView list;
    UserListAdapter adapter;
    CacheManager<JSONArray> jsonArrayCacheManager;
    NetworkInfo activeNetworkInfo;

    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler = new Handler();
    boolean refresh=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        list=(RecyclerView) findViewById(R.id.recyclerViewUser);
        jsonArrayCacheManager=new CacheManager<>(40*1024*1024); // 40mb

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(llm);

        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        adapter=new UserListAdapter(UserListActivity.this);



        populateItems();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateItems();
            }
        });

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void btnLoadListClicked(View v){
        populateItems();
    }

    void populateItems(){
        MindValleyHTTP
                .from(this)
                .load(MindValleyHTTP.Method.GET, Const.USERS_URL)
                .asJsonArray()
                .setCacheManager(jsonArrayCacheManager)
                .setCallback(new HttpListener<JSONArray>() {
                    @Override
                    public void onRequest() {
                        isNetworkAvailable();
                        if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
                        startSync();
                        }
                        else{
                            Toast.makeText(UserListActivity.this,"OOPS!!! Please check Interent connection!!!",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onResponse(JSONArray data) {
                        if(data!=null){
                            List<User> users=new ArrayList<User>();

                            for(int i=0; i<data.length(); i++){
                                try {
                                    JSONObject jUser=data.getJSONObject(i);
                                    users.add(
                                            new User()
                                                    .name(jUser.getJSONObject("user").get("name").toString())
                                                    .imageUrl(jUser.getJSONObject("user").getJSONObject("profile_image").get("small").toString())
                                    );

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            adapter.setItems(users);
                            list.setAdapter(adapter);
                            refresh=false;
                        }
                    }

                    @Override
                    public void onError() {
                       refresh=false;
                    }

                    @Override
                    public void onCancel() {
                        refresh=false;
                    }
                });

    }

    public void btnClearListClicked(View v){
        if(isRefreshing() == false) {
            adapter.clear();
        }
        else{
            Toast.makeText(UserListActivity.this,"Please Load the list!!!",Toast.LENGTH_SHORT).show();

        }
    }

    public void startSync(){
        refresh=true;
        if(!swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(true);
        }
        handler.post(refreshing);

    }

    private final Runnable refreshing = new Runnable(){
        public void run(){
            try {
                if(isRefreshing()){

                    handler.postDelayed(this, 1000);
                }else{

                    swipeRefreshLayout.setRefreshing(false);

                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    boolean isRefreshing(){
        return  refresh;
    }


}
