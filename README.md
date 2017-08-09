#MindValley Task

 
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

# MindValleyHTTP  Android Library By Ramana Reddy
This library makes easy to perform HTTP request and handle responses easily
### Features
* Supports HTTP
* Memory Cache for JSON, Image
* Supports Various request methods
* Request Cancellation etc
* Auto evict item in cache if not used for recently


# Install

The library can be used as standard android project, which can be imported from build.gradle



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
* I have created few unit tests of library in MindValleyLibraryTest class
* I have also generated javadoc, see javadoc folder in root 
* The coding may have some improvements and missing as this is 2 days work. :) 

### Contact
Ramana Reddy,
ramanareddy0012@gmail.com
