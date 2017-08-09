package reddy.com.ramanareddy.httplibrarymindvalley.datatypes;

import reddy.com.ramanareddy.httplibrarymindvalley.listeners.HttpListener;
import reddy.com.ramanareddy.httplibrarymindvalley.utils.CacheManagerInterface;

/**
 * Created by HP on 7/12/2016.
 */
public abstract class Type<T> {
    public abstract Type setCacheManager(CacheManagerInterface<T> cacheManager);
    public abstract Type setCallback(HttpListener<T> callback);
    public abstract boolean cancel();
}
