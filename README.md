
## 참고자료 
* https://github.com/kimcore/riot.kt


## 실행하기 
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
