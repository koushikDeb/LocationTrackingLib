
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

var trackerBuilder = DroidTracking.Builder(this)
      .setDbEnabled(true)
      .setUserId("anyUserid") 
      .setLocationDistanceInterval(minimumDistance)  // Default = 0.1f
      .setLocationFastTimeInterval(intervelInMilis)  // Default = 10
      .setLocationTimeInterval(intervelInMilis)      // Default = 100
      .setAccuracy(accuracy)//Provide custom accuracy to filter out less accurate locations. Default = 50

var tracker = trackerBuilder.build()

```    


## How to use it 

### **Start Tracking** 
```
private fun startTrackingModule() {  
  if (locationPermissionAvailable()) {  
    tracker?.startTracking()  
  } else {  
    requestWritePermission()  
  }  
}
```

### Stop Tracking 
```
  tracker.stopTracking()  
```

### Get service running status 
#### Provides the running status of Foreground Service we are using to capture location
```
tracker.getServiceRunningStatus()
```


### Get All Locatios
#### DB operation. Run in background 
```
tracker?.getAllLocation())  
```

### Clear Location
#### DB operation. Run in background 
```
  tracker?.clearLocations()  
```
### Get locations for specific date
#### DB operation. Run in background 
```
tracker?.getPositionByDate(offsetDateTime)
```

### Get locations for between date and time 
#### DB operation. Run in background 
```
tracker?.getPositionByDate(startDateTime:OffsetDateTime,endDateTime:OffsetDateTime)
```


Their are other methods to access DB go ahead and explore







## License

This project is licensed under the Apache License 2.0 - see the [LICENSE.md](/LICENSE) file for details




Koushik Deb [Linkedin](https://www.linkedin.com/in/koushik-deb-19562385)












