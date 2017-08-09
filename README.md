#Challange
```

Dear candidate,
 
Thank you for your interest in  Mindvalley! We'd like to proceed with your application. The next step of your selection process will be completing the practical challenge. 
Please see the challenge below & send us your solution by Friday, July 15th 2016(3 days).
 
Evaluation Criteria for this Assignment:
 
1. Readability:
Class and method names should clearly show their intent and responsibility.
 
2. Maintainability:
“SOLID” Principles and design pattern.
MVC model
We want to see how you use and integrate 3rd party libraries

 
3. Scalability:
Your software should easily accommodate possible future requirement changes (example: if you are asked to change to xml-based api instead of json)


4. Testability:
Please Unit Test your classes. In our assignment, you can just test all non-UI classes.
 
Please use Android Studio for this project.

<TEST>
 
Imagine you are on the Pinterest Android team and you are working with some colleagues on the pinboard (the scrolling wall of images), you split up the tasks among each other and your task is to create an image loading library that will be used to asynchronously download the images for the pins on the pinboard when they are needed. The library will also be useful for all
other parts of the app where asynchronous remote image loading is required. The images are 
available on a publicly accessible URL (like a CDN). The library should be general purpose and
not assume anything about the use case, the pinboard is an example but other parts of the app that show images will also use it (e.g. a user's profile pic on the profile screen).
 
One of your colleagues will also want to use the library for loading JSON documents, and you
just know that your boss and colleagues will love your library so much that they will ask you 
to support other datatypes in the future as well, so your design should not limit support to a particular datatype.
 
The purpose of the library is to abstract the downloading and caching of remote resources (images, JSON, XML, etc.) so that client code can easily "swap" a URL for a Drawable, Map, etc. without worrying about any of the details. Resources which are reused often should not be continually re-downloaded and should be cached, but the library cannot use infinite memory.
 
Requirements:
-images and JSON should be cached efficiently (in-memory only, no need for caching to disk)
-the cache should have a configurable max capacity and should evict images not recently used
-an image load may be cancelled
-the same image may be requested by multiple sources simultaneously (even before it has loaded), and if one of the sources cancels the load, it should not affect the remaining requests
-multiple distinct resources may be requested in parallel
-you can work under the assumption that the same URL will always return the same resource
-the library should be easy to integrate into the Pinterest app but also any future Android projects of the company
-you are supposed to build a solid structure and use the needed programming design patterns.
-Adding Material design UI elements (Ripple, Fab button, Animations) is an advantage.
-Adding "pull to refresh” is an advantage.



Use the following url for loading data: http://pastebin.com/raw/wgkJgazE
```

# MindValleyHTTP  Android Library By Sagar Devkota
This library makes easy to perform HTTP request and handle responses easily
### Features
* Supports HTTP
* Memory Cache for JSON, Image
* Supports Various request methods
* Request Cancellation etc
* Auto evict item in cache if not used for recently

# Sample App
![Screen 1](https://bytebucket.org/sagarda7/mindvalley_sagardevkota_android_test/raw/ba8a261e94440353f3c16c740a1e87a14ae0218d/screenshots/screen.jpg?token=5718ae78097510fb5f76dfb0fe79f1171c196cd2 "")

# Download Sample App
[mindvalley_library_app.apk](http://www.sagardevkota.com.np/mindvalley_library_app.apk) and install it to your mobile

# Install

The library can be used as standard android project, which can be imported from build.gradle
or download [mindvalleyhttplibrary.1.0.jar](https://bitbucket.org/sagarda7/mindvalley_sagardevkota_android_test/raw/5b094288f64ae1cafad3b2cef5579f03188b52ed/mindvalleyhttplibrary1.0.jar) and put this in libs folder of your project


# Usage

### Make Standard Request 

```java
	...
	MindValleyHTTP
			.from(mContext) //context
			.load(MindValleyHTTP.Method.GET, "http://yoururl.com") //method, url
			.asJsonArray() // asJsonObject() , asBitmap() are supported, asXML() in future
			.setCallback(new HttpListener<JSONArray>() {
				@Override
				public void onRequest() {
					//fired when request begins
				}

				@Override
				public void onResponse(JSONArray data) {
					if(data!=null){
					   
					}
				}

				@Override
				public void onError() {
					//fired when error
				}

				@Override
				public void onCancel() {
					//fired when cancelled
				}
			});
```

### Pass Request Parameters 

```java
	...
	MindValleyHTTP
			.from(mContext) //context
			.load(MindValleyHTTP.Method.POST, "http://yoururl.com") 
			.setRequestParameter("foo","bar")
			.setRequestParameter("anotherkey","value")
			.asJsonObject() 
			.setCallback(new HttpListener<JSONObject>() {
				@Override
				public void onRequest() {
				
				}

				@Override
				public void onResponse(JSONObject data) {
					if(data!=null){
					   
					}
				}

				@Override
				public void onError() {
					//fired when error
				}

				@Override
				public void onCancel() {
					//fired when cancelled
				}
			});
```

### Pass Headers 

```java
	...
	MindValleyHTTP
			.from(mContext) //context
			.load(MindValleyHTTP.Method.POST, "http://yoururl.com") 
			.setHeaderParameter("Content-type","application/json") //we can add more
			.setRequestParameter("foo","bar")
			.asJsonObject() 
			.setCallback(new HttpListener<JSONObject>() {
				@Override
				public void onRequest() {
				
				}

				@Override
				public void onResponse(JSONObject data) {
					if(data!=null){
					   // do your parsing here
					}
				}

				@Override
				public void onError() {
					//fired when error
				}

				@Override
				public void onCancel() {
					//fired when cancelled
				}
			});
```

### Enable Cache

```java
	...
	public class SomeActivity extends AppCompatActivity {
	...
    CacheManager<JSONArray> cacheManager; // we can use JSONObject, Bitmap as generic type
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ...       
        cacheManager=new CacheManager<>(40*1024*1024); // 40mb
    }

    //event to make request
    public void btnLoadListClicked(View v){
        MindValleyHTTP
                .from(this)
                .load(MindValleyHTTP.Method.GET, "http://www.url.com")
                .asJsonArray()
                .setCacheManager(cacheManager)
                .setCallback(new HttpListener<JSONArray>() {
                    @Override
                    public void onRequest() {
						//fired when request begins
                    }

                    @Override
                    public void onResponse(JSONArray data) {
                        if(data!=null){
                            // do some stuff here
                        }
                    }

                    @Override
                    public void onError() {
						
                    }

                    @Override
                    public void onCancel() {

                    }
                });
			}

		}

```


### Load Image into ImageView with cache

```java
	...
	public class SomeActivity extends AppCompatActivity {
	...
    CacheManager<Bitmap> cacheManager; // we can use JSONObject, Bitmap as generic type
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ...       
        cacheManager=new CacheManager<>(40*1024*1024); // 40mb
		imgProfile= (ImageView) findViewById(R.id.image_profile);
    }

    //event to make request
    public void btnLoadImageClicked(View v){
        MindValleyHTTP
                .from(this)
                .load(MindValleyHTTP.Method.GET, "http://www.url.com/image.jpg")
                .asBitmap()
                .setCacheManager(cacheManager)
                .setCallback(new HttpListener<Bitmap>() {
                    @Override
                    public void onRequest() {
						//fired when request begins
                    }

                    @Override
                    public void onResponse(Bitmap data) {
                        if(data!=null){
                            // do some stuff here
							imgProfile.setImageBitmap(data);
                        }
                    }

                    @Override
                    public void onError() {
						
                    }

                    @Override
                    public void onCancel() {

                    }
                });
	}

}

```

### Cancel Request

```java
	...
	public class SomeActivity extends AppCompatActivity {
	...
    CacheManager<Bitmap> cacheManager; // we can use JSONObject, Bitmap as generic type
	Type<Bitmap> bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ...       
        cacheManager=new CacheManager<>(40*1024*1024); // 40mb
		imgProfile= (ImageView) findViewById(R.id.image_profile);
    }

    //event to make request
    public void btnLoadImageClicked(View v){
        bitmap= MindValleyHTTP
                .from(this)
                .load(MindValleyHTTP.Method.GET, "http://www.url.com/image.jpg")
                .asBitmap()
                .setCacheManager(cacheManager)
                .setCallback(new HttpListener<Bitmap>() {
                    @Override
                    public void onRequest() {
						//fired when request begins
                    }

                    @Override
                    public void onResponse(Bitmap data) {
                        if(data!=null){
                            // do some stuff here
							imgProfile.setImageBitmap(data);
                        }
                    }

                    @Override
                    public void onError() {
						
                    }

                    @Override
                    public void onCancel() {
						Log.d(TAG,"Request cancelled");
                    }
                });
	}
	
	public void btnCancelRequestClicked(View v){
		if(bitmap!=null)
			bitmap.cancel();
	}

}

```

### More Info about library
* All calls are made through thread safe singleton instance of MindValleyHTTP, used singleton pattern here
* Most of the functions and classes in library are well commented
* I have tried to make a class be responsible to single purpose (Single Responsibility), eg. See any model class
* I have used Builder design pattern with method chaining for making it easy to use the library calls
* All  classes in datatypes package extend abstract class called Type, so there is no need to modify class, we can extend also create new type class or extend existing to meet Open/Close principle
* As all type classes(JsonArrayType, JsonObjectType, BitmapType) extend abstract class Type, so these can replace any Type , so derived classes can substitute parent class Type.
* Classes are not forced to implement interface that are not required, so I tried to avoid it to meet I in SOLID
* CacheManager class implements CacheManagerInterface and setCacheManager method in Type class depends on abstraction i.e interface, so it is loosely coupled, tried this for D in SOLID
* When we need to add support for XML, just add new class called XMLDataType and make XMLRequestTask Class without touching existing class in datatypes package and request class
* I have created few(Not All) unit tests of library in MindValleyLibraryTest class
* I have also generated javadoc, see javadoc folder in root 
* The coding may have some improvements and missing as this is 2 days work. :) 

### Contact
Sagar Devkota
sagarda7@yahoo.com
+9779856032592
Skype: sagarda7