# Android Roulette Wheel View Project

<br/>

![roulette_sample_image](https://user-images.githubusercontent.com/52662641/110210350-97360b00-7ed4-11eb-8496-91cf588e5041.gif)

<br/>

## 소개
* Android Roulette Wheel View
* library development post - 개발과정 (Korean)
  * https://jhdroid.tistory.com/23

<br/><br/>

## Gradle

### Project Gradle
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

<br/><br/>

### App Gradle
[![](https://jitpack.io/v/JhDroid/android-roulette-wheel-view.svg)](https://jitpack.io/#JhDroid/android-roulette-wheel-view)
```groovy
dependencies {
    implementation 'com.github.JhDroid:android-roulette-wheel-view:{version}'
}
```

<br/><br/>

## How to use ?
### layout (xml)
```xml
<androidx.constraintlayout.widget.ConstraintLayout
    <com.jhdroid.view.Roulette
        android:id="@+id/roulette"
        android:layout_width="wrap_content"
        android:layout_height="0dp"/>
</androidx.constraintlayout.widget.ConstraintLayout>
```
* setup `android:layout_height` is `match_parent` (or ConstraintLayout : `0dp`)
* (필수) `height`속성은 `match_parent`(ConstraintLayout은 `0dp`)설정해야 합니다.

<br/><br/>

### Use in Activity or Fragment
```kotlin
val rouletteData = listOf("JhDroid", "Android", "Blog", "IT", "Developer", "Kotlin", "Java", "Happy")
roulette.apply {
    setRouletteSize(8) // 2 ~ 8
    setRouletteDataList(rouletteData)
}
```

<br/><br/>

### rotate and get result
```kotlin
fun rotateRoulette() {
    val rouletteListener = object : RotateListener {
        override fun onRotateStart() {
            // rotate animation start
        }
        override fun onRotateEnd(result: String) {
            // rotate animation end, get result here
        }
    }
    // random degrees (options)
    val toDegrees = (2000..10000).random().toFloat()
    roulette.rotateRoulette(toDegrees, 4000, rouletteListener)
}
```
```kotlin
 /**
   * Rotate roulette function
   * @param toDegrees : end angle (start angle is '0')
   * @param duration : rotate duration
   * @param rotateListener : rotate anim start, end listener (선택)
  * */
fun rotateRoulette(toDegrees: Float, duration: Long, rotateListener: RotateListener?)
```


<br/><br/>

## example
![roulette_sample_image](https://user-images.githubusercontent.com/52662641/110210350-97360b00-7ed4-11eb-8496-91cf588e5041.gif)

<br/><br/>
