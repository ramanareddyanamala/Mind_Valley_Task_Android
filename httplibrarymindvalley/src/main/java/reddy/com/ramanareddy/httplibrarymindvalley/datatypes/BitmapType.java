package reddy.com.ramanareddy.httplibrarymindvalley.datatypes;

import android.graphics.Bitmap;

import java.util.ArrayList;

import reddy.com.ramanareddy.httplibrarymindvalley.MindValleyHTTP;
import reddy.com.ramanareddy.httplibrarymindvalley.listeners.HttpListener;
import reddy.com.ramanareddy.httplibrarymindvalley.models.HeaderParameter;
import reddy.com.ramanareddy.httplibrarymindvalley.models.RequestParameter;
import reddy.com.ramanareddy.httplibrarymindvalley.tasks.BitmapRequestTask;
import reddy.com.ramanareddy.httplibrarymindvalley.utils.CacheManagerInterface;

/**
 * Created by Dell on 7/11/2016.
 */
public class BitmapType extends Type<Bitmap> {
    private String mUrl;
    private HttpListener<Bitmap> mListener;
    private MindValleyHTTP.Method mMethod;
    private ArrayList<RequestParameter> params;
    private ArrayList<HeaderParameter> headers;
    private BitmapRequestTask mTask;
    private CacheManagerInterface<Bitmap> mCacheManager;

    /**
     * Constructor to load json datatyes
     * @param url
     * @param params
     * @param headers
     */
    public BitmapType(MindValleyHTTP.Method m, String url, ArrayList<RequestParameter> params, ArrayList<HeaderParameter> headers){
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
    public BitmapType setCallback(HttpListener<Bitmap> listener){
        this.mListener=listener;
        mListener.onRequest();
        Bitmap data;
        if(mCacheManager!=null) {
            data = mCacheManager.getDataFromCache(mUrl);
            if (data != null) {
                mListener.onResponse(data);
                return this;
            }
        }

        mTask = new BitmapRequestTask(mMethod, mUrl, params, headers, mListener);
        mTask.setmCachemanager(mCacheManager);
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
    public BitmapType setCacheManager(CacheManagerInterface<Bitmap> cache){
        this.mCacheManager=cache;
        return this;
    }


}
