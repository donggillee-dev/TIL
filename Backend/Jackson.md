# Jackson

* [jackson이란?](#jackson이란)
* [의존성 추가](#의존성-추가)
* [어노테이션](#어노테이션)



## Jackson이란?

- Spring 개발을 하다 보면, 컨트롤러에서 XML, JSON 형태로 많이 보냄
- JSON은 JSON 데이터 구조를 처리해주는 라이브러리



## Jackson 라이브러리가 없다면?

```java
//그냥 러프하게 문자열로 작성하는 방법
String JSON = "\"{"+
    "\"name\": \"" + person.getName() + "\","+
    "\"job\": \"" + person.getJob() + "\""+ 
"}\"";

//GSON 방법도 있지만
//SimpleJSON으로 하는 방법
JSONObject jsonObject = new JSONObject();
jsonObject.put("name", person.getName());
jsonObject.put("job", person.getJob());
String JSON = jsonObject.toString();

출처: https://mommoo.tistory.com/83 [개발자로 홀로 서기]
```

- 이런 형식으로 만들어서 보내야함
- 그럼 Jackson은 무엇을 더 제공하길래 GSON이나 SimpleJSON말고 사용하는 걸까?



## Jackson과 기존 GSON or SimpleJSON의 차이점

- 딱히 그럴만한 차이는 없다
- Jackson도 ObjectMapper API를 사용하여 GSON, SimpleJSON처럼 객체에 데이터 세팅해줘야 하는 것은 동일
- Spring과의 관계로부터 장점이 있다
  - Spring에서 Jackson API를 제공하므로 자동화 처리가 가능
  - 한 단계 더 발전된 기능



## Jackson 동작 방식

- Spring 3.0 이후로 컨트롤러의 리턴 방식이 `@ResponseBody` 형식이라면, `Spring은` `MessageConverter` API를 통해서 컨트롤러가 리턴하는 객체 후킹 가능
- `Jackson` 은 JSON 데이터를 출력하기 위한 `MappingJacksonHttpMessageConverter` 를 제공한다
- 스프링 `MessageConverter` 를 위의 `MappingJacksonHttpConverter` 로 등록한다면, 컨트롤러가 리턴하는 객체를 뜯어서(자바 리플렉션 사용) `Jackson` 의 ObjectMapper API로 JSON 객체로 만들고 난 후 `JSON 데이터` 를 완성

- 뭐 어렵지만 아래와 같이 사용해도 JSON 객체가 리턴 된다는 것이다.

  ```java
  @RequestMapping("/json")
  @ResponseBody()
  public Object printJSON() {
      Person person = new Person("Mommoo", "Developer");
      return person;
  }
  ```



## Jackson은 기본적으로 프로퍼티로 동작

- 프로퍼티?
  - 멤버변수와 다름
  - `Getter` , `Setter` 의 이름 명명 규칙으로 프로퍼티가 정해진다

- 즉 Jackson사용시에 `Getter` , `Setter` 가 중요하다

  ```java
  public class Person {
      public String getName() {
          return "Mommoo";
      }
      
      public String getJob() {
          return "Developer";
      }
  }
  
  @RequestMapping("/json")
  @ResponseBody()
  public Object printJSON() {
      return new Person();
  }
  
  ```

  - getter만 있기에 getter 기준으로 Jackson 동작해서 JSON 리턴



## Jackson의 데이터 매핑을 Getter가 아닌 멤버변수로

```java
//일일히 멤버변수에 @JsonProperty붙이는 방법보다는 아래로 하면 모든 변수를 JSON화 가능
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Person {
    private String myName = "Mommoo";
}

//@JsonIgnore를 이용한 특정 Getter 제외
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Person {
    private String myName = "Mommoo";
    
    @JsonIgnore
    public String getJob() {
        return "Developer";
    }
}

```



## 의존성 추가

```groovy
implementation("com.squareup.retrofit2:converter-jackson:2.7.1")
```



## 어노테이션

종류는 아래와 같다.. 내용이 많으므로 링크 참고

[모든 jackson 어노테이션 종류 파악](https://recordsoflife.tistory.com/28)

- Jackson 직렬화 어노테이션
- Jackson 역 직렬화 어노테이션
- Jackson Property Inclusion Annotations
- Jackson 다형성 유형 처리 어노테이션
- Jackson 기본적인 어노테이션