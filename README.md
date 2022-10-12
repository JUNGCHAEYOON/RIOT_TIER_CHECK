
## 참고자료 
* https://github.com/kimcore/riot.kt


## 실행하기 

* MainActivity.kt 에서 해당값 24시간마다 갱신 
```c
RiotAPI.setApiKey("RGAPI-...")
```

* settings.gradle 
```c
dependencyResolutionManagement{
  repositories {
    ...
    maven { url 'https://jitpack.io' } 
  }
}
```
* build.gradle 
```c
dependencies {
  implementation("com.github.kimcore:riot.kt:1.0") 
}
```
* AndroidManifest  
```c
<uses-permission android:name="android.permission.INTERNET" />
```
