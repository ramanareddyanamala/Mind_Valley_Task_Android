package reddy.com.ramanareddy.httplibrarymindvalley;

import org.json.JSONObject;
import org.junit.Test;

import reddy.com.ramanareddy.httplibrarymindvalley.models.HeaderParameter;
import reddy.com.ramanareddy.httplibrarymindvalley.models.RequestParameter;
import reddy.com.ramanareddy.httplibrarymindvalley.utils.CacheManager;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.Assert.assertEquals;

/**
 * Created by Dell on 7/13/2016.
 */
public class MindValleyLibraryTest {
    String responseId;
    @Test
    public void headerset_isCorrect() throws Exception {
        String key="test";
        HeaderParameter headerParameter=new HeaderParameter().setKey(key);
        assertEquals(key,headerParameter.getKey());
    }

    @Test
    public void requestparamset_isCorrect() throws Exception {
        String key="foo";
        RequestParameter requestParameter=new RequestParameter().setKey(key);
        assertEquals(key,requestParameter.getKey());
    }


    @Test
    public void cache_isCorrect() throws Exception {
        String key="foo";
        JSONObject d=new JSONObject();
        d.put("name","Sagar");
        CacheManager<JSONObject> cacheManager=mock(CacheManager.class);
        when(cacheManager.get(key)).thenReturn(d);
        cacheManager.put(key,d);
        assertEquals(d,cacheManager.get(key));
    }
}
