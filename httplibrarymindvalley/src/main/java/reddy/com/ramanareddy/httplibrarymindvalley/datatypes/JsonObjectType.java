package reddy.com.ramanareddy.httplibrarymindvalley.datatypes;

import org.json.JSONObject;

import java.util.ArrayList;

import reddy.com.ramanareddy.httplibrarymindvalley.MindValleyHTTP;
import reddy.com.ramanareddy.httplibrarymindvalley.listeners.HttpListener;
import reddy.com.ramanareddy.httplibrarymindvalley.models.HeaderParameter;
import reddy.com.ramanareddy.httplibrarymindvalley.models.RequestParameter;
import reddy.com.ramanareddy.httplibrarymindvalley.tasks.JsonObjectRequestTask;
import reddy.com.ramanareddy.httplibrarymindvalley.utils.CacheManagerInterface;
import reddy.com.ramanareddy.httplibrarymindvalley.MindValleyHTTP;

/**
 * Created by Dell on 7/11/2016.
 */
public class JsonObjectType extends Type<JSONObject> {
    private String mUrl;
    private HttpListener<JSONObject> mListener;
    private MindValleyHTTP.Method mMethod;
    private ArrayList<RequestParameter> params;
    private ArrayList<HeaderParameter> headers;
    private JsonObjectRequestTask mTask;
    private CacheManagerInterface<JSONObject> mCacheManager;

    /**
     * Constructor to load json datatyes
     * @param url
     * @param params
     * @param headers
     */
    public JsonObjectType(MindValleyHTTP.Method m, String url, ArrayList<RequestParameter> params, ArrayList<HeaderParameter> headers){
        this.mUrl=url;
        this.mMethod=m;
        this.headers=headers;
        this.params=params;
    }



    /**
     *
     * Sets future callback after Http response is received
     * @param listener
     */
    public JsonObjectType setCallback(HttpListener<JSONObject> listener){
        this.mListener=listener;
        mListener.onRequest();
        JSONObject data;
        if(mCacheManager!=null) {
            data = mCacheManager.getDataFromCache(mUrl);
            if (data != null) {
                mListener.onResponse(data);
                return this;
            }
        }

        mTask=new JsonObjectRequestTask(mMethod,mUrl,params,headers,mListener);
        mTask.execute();
        return this;
    }

    /**
     * Cancels the current request
     * @return True if cancelled
     */
    public boolean cancel(){
        if(mTask!=null){
            mTask.cancel(true);
            if(mTask.isCancelled()) {
                mListener.onCancel();
                return true;
            }
            else
            {
                return false;
            }
        }

        return false;
    }


    /**
     * Lets depend on abstraction
     * Sets CacheManager for this
     * @param cache
     * @return JsonObjectType
     */
    public JsonObjectType setCacheManager(CacheManagerInterface<JSONObject> cache){
        this.mCacheManager=cache;
        return this;
    }


}
