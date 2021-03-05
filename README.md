# Android Roulette Wheel View Project

## 소개
* Android Roulette Wheel View
* 안드로이드에서 사용할 수 있는 룰렛 View 입니다.
* 회전 기능을 제공하고 결과를 리턴받을 수 있습니다.

<br/><br/>

## 사용

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
    implementation 'com.github.User:Repo:Tag'
}
```  
<br/><br/>
### layout
```xml
<androidx.constraintlayout.widget.ConstraintLayout
    <com.jhdroid.view.Roulette
        android:id="@+id/roulette"
        android:layout_width="wrap_content"
        android:layout_height="0dp"/>
</androidx.constraintlayout.widget.ConstraintLayout>
```
* 꼭! `height`속성은 `match_parent`(ConstraintLayout은 `0dp`)설정해야 합니다.  
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
### rotate result return
1. Listener를 사용한 리턴
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

## 뷰 적용 및 회전 예
* 잠시 기다리면 git 이미지를 확인 가능합니다.
![roulette_sample_image](https://user-images.githubusercontent.com/52662641/109980681-8013e380-7d43-11eb-9b4b-6419c2056b47.gif)  
  
<br/><br/>

## 남은 과제
1. 12시 방향에 화살표 추가 (이미지 or 그리기)
2. 룰렛 뷰 최소 길이 설정 후 wrap_content 설정해도 적용가능하도록 수정 (지금은 match_parent 만..)
3. 사용자 설정을 위한 속성 추가 (생각나는 부분 계속 추가 예정)  

<br/><br/>

## 기타
* 개발 과정은 블로그를 확인해주세요!
    * https://jhdroid.tistory.com/category/Android%20Proejct/Roulette%20Wheel%20View
