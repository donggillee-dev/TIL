# Jackson

* [jackson이란?](#jackson이란)
* [의존성 추가](#의존성-추가)
* [어노테이션](#어노테이션)



## jackson이란?

- JSON 뿐만 아니라 XML, YAML, CSV 등 다양한 형식의 데이터를 지원하는 data-processing 툴
- 스트림 방식, 속도가 빠르며 유연, 다양한 third party 데이터 타입을 지원
- annotation 방식으로 메타 데이터를 기술 가능 => JSON의 약점중 하나인 문서화, 데이터 validation 문제 해결 가능



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