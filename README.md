
참고자료 
https://github.com/kimcore/riot.kt


실행하기 
settings.gradle -> dependencyResolutionManagement -> repositories -> maven { url 'https://jitpack.io' } 추가
build.gradle -> dependencies -> implementation("com.github.kimcore:riot.kt:1.0") 추가
AndroidManifest -> <uses-permission android:name="android.permission.INTERNET" /> 추가
