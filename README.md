
# Location Tracking Library  
 [![](https://jitpack.io/v/koushikDeb/LocationTrackingLib.svg)](https://jitpack.io/#koushikDeb/LocationTrackingLib)
 
This library offers realtime location tracking with foreground service and integrated Room implementation. Tracking user location with a persistent notification using a foreground service and saves the location data to to local DB and provides multiple API to access location data later.  

## USE

![enter image description here](https://github.com/koushikDeb/LocationTrackingLib/blob/master/demo.gif?raw=true)


## Getting Started

**Current Version** 
 [![](https://jitpack.io/v/koushikDeb/LocationTrackingLib.svg)](https://jitpack.io/#koushikDeb/LocationTrackingLib)

### Installing

Step 1. Add it in your root build.gradle at the end of repositories:

```
buildscript {  
  repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```


```
dependencies {

     implementation 'com.github.koushikDeb.LocationTrackingLib:final:CurrentVersion'

}
```


### Prerequisites

You Will need Location permission
Add it in menifest
```
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />  
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

In your Project

```
**//Create the tracking builder** 
var tracker = DroidTrackingBuilder.Builder(applicationContext)  
  .setDbEnabled(true)  
  .setUserId(userId) // or you can set it later also  
  .setLocationDistanceInterval(minimumDistance)  //in float
  .setLocationFastTimeInterval(intervelInMilis)  
  .setLocationTimeInterval(intervelInMilis)

var trackerBuilder = tracker.build()
```    


## How to use it 

### **Start Tracking** 
```
private fun startTrackingModule() {  
  if (locationPermissionAvailable()) {  
    trackerBuilder?.startTracking()  
  } else {  
    requestWritePermission()  
  }  
}
```

### Stop Tracking 
```
  trackerBuilder.stopTracking()  
```

### Get All Data
```
GlobalScope.launch {  
  mutableLivedata.postValue(trackerBuilder?.getAllLocation())  
}
```

### Clear DB
```
GlobalScope.launch {  
  trackerBuilder?.clearLocations()  
}
```

### Get service running status 
```
trackerBuilder.getServiceRunningStatus()
```

Their are other methods to access DB go ahead and explore







## License

This project is licensed under the Apache License 2.0 - see the [LICENSE.md](/LICENSE) file for details


## Developed by

Koushik Deb [Linkedin](https://www.linkedin.com/in/koushik-deb-19562385)












