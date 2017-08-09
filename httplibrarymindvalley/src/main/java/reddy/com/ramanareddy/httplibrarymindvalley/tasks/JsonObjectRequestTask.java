package reddy.com.ramanareddy.httplibrarymindvalley.tasks;

import org.json.JSONObject;

import java.util.ArrayList;

import reddy.com.ramanareddy.httplibrarymindvalley.MindValleyHTTP;
import reddy.com.ramanareddy.httplibrarymindvalley.listeners.HttpListener;
import reddy.com.ramanareddy.httplibrarymindvalley.models.HeaderParameter;
import reddy.com.ramanareddy.httplibrarymindvalley.models.RequestParameter;
import reddy.com.ramanareddy.httplibrarymindvalley.models.Response;

/**
 * Created by Dell on 7/11/2016.
 */
public class JsonObjectRequestTask extends BaseTask<String, Void, JSONObject> {
    private MindValleyHTTP.Method mMethod;
    private String mUrl;
    private HttpListener<JSONObject> mCallback;
    private boolean error=false;
    private ArrayList<RequestParameter> params;
    private ArrayList<HeaderParameter> headers;

    public JsonObjectRequestTask(MindValleyHTTP.Method method, String url, ArrayList<RequestParameter> params, ArrayList<HeaderParameter> headers, HttpListener<JSONObject> callback){
        this.mUrl=url;
        this.mMethod=method;
        this.mCallback=callback;
        this.params=params;
        this.headers=headers;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... urls) {
        try {
            Response response=makeRequest(mUrl,mMethod,params,headers);
            JSONObject json= new JSONObject(response.getDataAsString());
            if(this.mCacheManager!=null){
                if(this.mCacheManager.getDataFromCache(mUrl)==null)
                    this.mCacheManager.addDataToCache(mUrl,json);
            }
            return json;

        } catch (Exception e) {
            e.printStackTrace();
            error=true;
        }

        return null;
    }

    @Override
    protected void onPostExecute(JSONObject data) {
        super.onPostExecute(data);
        if(!error)
            this.mCallback.onResponse(data);
        else
            this.mCallback.onError();
    }

    /**
     * Sometimes users may cancel at almost end, so lets remove if data is in cache
     */
    @Override
    protected void onCancelled() {
        super.onCancelled();
        if(this.mCacheManager!=null){
            this.mCacheManager.removeDataFromCache(mUrl);
        }
    }
}
