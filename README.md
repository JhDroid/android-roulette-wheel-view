# Android Roulette Wheel View Project

<br/>

![roulette_sample_image](https://user-images.githubusercontent.com/52662641/110210350-97360b00-7ed4-11eb-8496-91cf588e5041.gif)

<br/>

## 소개
* Android Roulette Wheel View
* 안드로이드에서 사용할 수 있는 룰렛 뷰 입니다.
* 회전 기능을 제공하고 회전 후 결과를 리턴받을 수 있습니다.
* 개발 과정
    * https://jhdroid.tistory.com/category/Android%20Proejct/Roulette%20Wheel%20View

<br/><br/>

## setup gradle

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
```groovy
dependencies {
    implementation 'com.github.JhDroid:android-roulette-wheel-view:1.0.0'
}
```

<br/><br/>

### layout (xml)
```xml
<androidx.constraintlayout.widget.ConstraintLayout
    <com.jhdroid.view.Roulette
        android:id="@+id/roulette"
        android:layout_width="wrap_content"
        android:layout_height="0dp"/>
</androidx.constraintlayout.widget.ConstraintLayout>
```
* (필수) `height`속성은 `match_parent`(ConstraintLayout은 `0dp`)설정해야 합니다.

<br/><br/>

### Activity
```kotlin
val rouletteData = listOf("JhDroid", "Android", "Blog", "IT", "Developer", "Kotlin", "Java", "Happy")

roulette.apply {
    setRouletteSize(8)
    setRouletteDataList(rouletteData)
}
```

<br/><br/>

### Rotate result return
```kotlin
fun rotateRoulette() {
    val rouletteListener = object : RotateListener {
        override fun onRotateStart() {
            // rotate animation start
        }

        override fun onRotateEnd(result: String) {
            // rotate animation end
        }
    }

    // random degrees (options)
    val toDegrees = (2000..10000).random().toFloat()
    roulette.rotateRoulette(toDegrees, 4000, rouletteListener)
}
```

<br/><br/>

## example
![roulette_sample_image](https://user-images.githubusercontent.com/52662641/110210350-97360b00-7ed4-11eb-8496-91cf588e5041.gif)
  
<br/><br/>
