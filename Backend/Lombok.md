# Lombok

Java 라이브러리로 반복되는 메소드를 Annotation을 사용해 자동으로 작성해주는 라이브러리. 보통 DTO나 Model, Entity의 경우 여러 속성이 존재하고 이에 대해 Getter, Setter, 생성자 등을 매번 작성 및 수정해줘야하는 불편함을 개선해주는 라이브러리.

Lombok이용 -> Annotation통해서 코드 생성 -> .class에 담김

다만 팀 플젝에서 조심? -> 막연하게 쓰지말고 각 API에 대해 알고 쓰자

ex) @Data, @ToString의 경우 순환 참조, 무한 재귀 발생 가능



## Lombok 사용법

### @Getter, @Setter

필드에 대해 getter, setter를 자동으로 생성해주는 Annotation.

만약 필드의 이름이 name이면 getname(), setName() 자동 생성

자동으로 생성되는 getter, setter의 경우 기본 public, but, AccessLevel 명시하면 PUBLIC, PROTECTED, PACKAGE, PRIVATE 등으로 생성 가능

```java
class Person{
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PROTECTED)
    private String name;
}
```



### @NotNull

메소드, 생성자의 배개변수에 @NotNull 사용하면 lombok이 null check해줌

```java
// code
class Person{
    private String name;
    private int age;

    public Person(@NonNull String name, int age) {
        this.name = name;
        this.age = age;
    }
}
// build
class Person {
    private String name;
    private int age;

    public Person(@NonNull String name, int age) {
        if (name == null) {
            throw new NullPointerException("name is marked non-null but is null");
        } else {
            this.name = name;
            this.age = age;
        }
    }
}
```



### @ToString

이 Annotaion이 붙은 클래스는 자동으로 toString()함수 생성. includeFieldName을 설정하면 각 필드의 이름과 함께 값을 확인할 수 있음. true가 기본 값

```java
// code
@ToString(includeFieldNames = false)
class Person{
    private String name;
    private int age;
}
// build
class Person {
    private String name;
    private int age;
    public String toString() {
        return "Person(" + this.name + ", " + this.age + ")";
    }
}
```



### @EqualsAndHashCode

이 Annotation을 사용하면 자동으로 equlas(Object obj) 와 hashCode() 를 만들어준다. 기본적으로 모든 `non-static`, `non-transient` 필드를 사용하지만 `@EqualsAndHashCode.Include`와 `@EqualsAndHashCode.Exclude` 를 사용해서 명시적으로 선택 가능. `@ToString` 처럼 `@EqualsAndHashCode(onlyExplicitlyIncluded = true)` 를 사용하는 것도 가능



만약 클래스가 아무런 클래스를 상속받지 않는데 `callSuper`를 `true`로 설정하면 Compile Error 발생한다. equals를 사용하는 필드가 슈퍼클래스에 존재한다면 슈퍼 클래스에 존재하는 필드를 비교하지 못하기 때문. `callSuper`를 `false`로 명시해주면 됨



또한 `@ToString`과 마찬가지고 `StackOverFlow`를 조심해야한다. 자기 자신을 포함하는 배열을 가지거나 순환 참조가 존재하면 이를 명시적으로 제외해야만 사용 가능

`doNotUseGetters`를 이용하거나 변수명을 $로 시작하게 하면 제외 대상



### @NoArgsConstructor

- 매개변수가 없는 생성자를 생성
- 불가능하다면 (final 필드 때문에) 컴파일 에러가 난다.
- @NoArgsConstructor(force = true)를 사용하면 컴파일 에러를 발생시키는 대신 모든 final 필드는 default(0, false, null)로 초기화된다.



### @RequiredArgsConstructor

- 초기화되닞 않은 모든 final 필드, @Nonnull 필드에 대한 생성자를 생성
- @NonNull 필드의 경우 null 
  

### @AllArgsConstructor

- 모든 필드에 대한 생성자 생성
- 마찬가지로 @NonNull필드에 대한 null check 구문을 생성



### @Data

모든 필드에 대해 `@ToString`, `@EqualsAndHashCode`, `@Getter`를 모든 `non-final` 필드에 대해 `@Setter`를 설정하고 `@RequiredArgsConstructor`를 설정해주는 단축 `Annotation`



### @Value

`@Data`의 불변 클래스 버전. 모든 필드를 `private / final` 로 만들고 `setter`는 생성되지 않는다. 클래스 또한 `final`로 만든다



`@Data`처럼 `toString(), equals(), hashCode()` 를 자동으로 생성해주고 각 필드에 대한 getter와 생성자 또한 만들어 준다.

즉, `@Value`는 `final @ToString @EqualsAndHashCode @AllArgsConstructor @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE) @Getter` 의 단축형



### @Builder

빌더를 자동으로 작성해준다. 클래스에 작성하면 모든 필드에 대한 빌더를 만들어준다. 원하는 필드에 대해서만 빌더를 작성하고 싶은 경우 생성자를 작성하고 그 위에 `@Builder`를 붙여주면 된다.