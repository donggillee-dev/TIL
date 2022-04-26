# Getting Started with Kotlin PL

## Why Kotlin?

- 간결한 문법
  - 코드의 가독성, 유지보수성
- 안전한 언어
  - NullPointerException 방지 기능 => Nullable & Non-Nullable Type
- 자바와의 호환성



## Kotlin Build Process

- *.kt -> Kotlin Compiler -> *.class -> .jar -> Java Runtime Environment에 의해 동작
- 여러 .jar 파일들이 하나의 Java Runtime Environment에서 동작 가능



## Val & Var

#### Val

- 불변값

#### Var 

- 가변값



## Types

### Integer

- Byte, Short, Int, Long

### Floating

- Float, Double

### Character

- Char

#### Boolean

- Boolean



## String Interpolation

- $ 를 이용하여 문자열 출력 내에서 변수 사용 및 코딩 가능
  - println("$value & ${value.length}")
- 문자열 자동 trim 사용 가능
  - """""".trimIndent()



## If-Else when

아래의 두 코드는 같은 기능

모든 코드에서의 if - elseif - elseif - ..... - else 를 여러개 할수록 어샘블리 구간에서의 성능은 더욱 떨어짐

```kotlin
val medal = when (position) {
    1 -> "GOLD"
    2 -> "SILVER"
    3 -> "BRONZE"
    else -> "NO MEDAL"
}

val medal = if(position == 1) {
	"GOLD"
}  else if(position == 2){
	"SILVER"
} else if(position == 3) {
	"BRONZE"
} else {
	"NO MEDAL"
}
```



## For loop

- 오름차순의 경우 1..10 이런경우로 사용
- 내림차순의 경우 10 downTo 1
- 건너뛰기의 경우 for문 안에서 step 사용



