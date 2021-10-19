# Spring Framework

## 스프링 프레임워크란

- 자바 엔터프라이즈 개발을 편하게 해주는 경량급 오픈소스 어플리케이션 프레임워크

- Lightweight Java Application Framework

  - 목표: POJO 기반의 Enterprise Application 개발을 쉽고 편하게 할 수 있도록 한다
  - Spring이 하부구조를 처리하기에 개발자는 Application 개발에 집중할 수 있음

  > POJO?
  >
  > - extends나 implements를 사용하는 클래스들은 클래스간에 의존도가 생기기에 추후에 코드의 수정이 불가피하게 일어난다
  > - 단순히 getter / setter만을 갖춘 단순한 자바 오브젝트



## DTO, DAO, VO, Bean, POJO

1. DTO(Data Transfer Object)
   - 계층 간 데이터 교환을 위해서 사용하는 객체
   - 계층는 View - Controller - Service - DAO를 얘기한다
   - 데이터를 담을 private 변수와 조작할 수 있는 Getter, Setter 메소드로 구성
2. DAO(Data Access Object)
   - DB의 데이터를 조회하거나 조작하는 기능을 담당하도록 만든 객체
   - DB에 접근하기 위한 로직과 비즈니스 로직을 구별하기 위해서 사용
3. VO(Value Object)
   - DTO와 비슷하지만 내부 속성 값을 변경할 수 없는(Immutable) 의미적 속성을 가진 객체
4. Bean
   - Spring의 IoC Container(=DI Controller)를 통해 관리되는 객체를 의미
5. POJO
   - 특정 인터페이스나 클래스를 상속하지 않고 순수하게 Getter, Setter로만 구성된 순수 자바 객체



## Spring Boot vs Spring MVC vs Spring

> Spring, Spring MVC, Spring Boot는 중복되는 부분을 다루지 않으며
>
> 각각의 모듈들은 서로 다른 문제를 해결하면서도 그 문제를 잘 해결한다

### Spring

> - Spring 프레임워크의 가장 중요한 특징은 의존성 주입(DI)
> - 모든 스프링 모듈들의 핵심에는 DI나 IoC가 있다

#### 간단히 @Component, @Autowired를 이용해서 의존성 주입이 가능

```java
@Component 
public class Service {
  
}

@RestController
public class Controller {
  @Autowired
  private Service service;
}
```

- @Component를 통해 스프링 프레임워크에게 관리해야하는 Bean임을 알려줌
- @Autowired를 통해 연결(의존성 주입)



#### 그밖에 Spring Framework가 해결하는 것들은 어떤게 있을까?

1. 많은 스프링 모듈들을 제공함으로써 반복, 중복코드를 줄여준다
2. 다른 프레임워크들과 좋은 통합을 보여준다



### Spring MVC?

> - Dispatcher Servlet, ModelAndView, View Resolver과 같은 단순개념을 이용해 웹 Application을 쉽게 개발할 수 있게 도와준다



### Spring Boot?

> - 스프링 기반 웹 어플리케이션은 많은 환경설정을 해야함
> - 이런 불편함을 줄이고 빠른 시간에 어플리케이션이 제품이 될 수 있는 것을 목표로 하게끔 도와준다



## Spring MVC Framework

![image-20211019220618224](https://user-images.githubusercontent.com/41468004/137917926-df128dfd-56ba-4edb-9d80-012e5f01795b.png)

### MVC 진행 과정

- 클라이언트가 url 요청하면 웹 브라우저에서 스프링으로 request 보내짐
- `Dispatcher Servlet`이 request를 받아서 `Handler Mapping`을 통해 적정한 Controller를 찾아낸다
- 찾아낸 `Controller`로 request를 보내주고 Model을 구성
- `Model`에서는 요청 처리에 필요한 정보들을 Database에 접근하여 가져온다
- 가져온 `Model` 정보를 `Controller`에게 주면 `Controller`는 이를 가지고 `Dispatcher Servlet`에게 전달
- Dispatcher Servlet은 `View Resolver`를 통해 적절한 view파일을 받아낸다
- Dispatcher Servlet은 받아낸 View 파일에 Model을 보낸 후 클라이언트에게 보여줄 페이지를 완성시켜 받아낸다
- 완성된 페이지를 클라이언트에게 response한다



### Dispatcher Servlet

- 서블릿 컨테이너에서 HTTP 프로토콜을 통해 들어오는 요청을 제일 앞에서 처리해주는 프론트 컨트롤러

- 서버가 요청을 받기 이전에 공통처리 작업을 먼저 처리하고 세부 컨트롤러로 작업을 위임해준다
- 얘로 인해서 web.xml이 상당히 축소됨
  - 기존에는 web.xml에다가 모든 서블릿을 매핑해주어서 등록해야했기에 방대했음
- 또한, 서블릿을 통해 MVC를 사용할 수 있음



## Servlet & Servlet Container

### Servlet

- WebProgramming에서 클라이언트의 요청을 처리하고 그 결과를 다시 클라이언트에게 response하는 기술
- 라이프 사이클을 위해 3가지 필수 메소드 정의
- init()
  - 서블릿 생명 주기 중 초기화 단계
- service()
  - 초기화 이후 각각의 요청들이 들어오면 호출됨
- destroy()
  - 서블릿 객체가 파괴되어야 할 때 호출됨

#### Servlet 동작 방식

- Client의 HTTP Request가 Servlet Container로 전송됨
- Servlet Container는 `HttpServletRequest, HttpServletResponse` 두 객체를 생성
- 서블릿을 분석하여 Method(Post, Get,,,)에 따라 doGet(), doPost()를 호출
  - 이런 url에 따라서 일일해 web.xml에 매핑해주어야 하는 것을 `DispatcherServlet`을 통해 처리 가능하다
  - Servlet Container는 HTTP Request를 받아 Dispatcher Servlet으로 보내고
  - Dispatcher Servlet으로부터 받은 HTTP Response를 클라이언트에게 전달

### Servlet Container

- 서블릿을 위한 상자
- 서블릿들의 생명 주기를 관리
- 대표적으로 `Tomcat`이 있다

![](https://user-images.githubusercontent.com/41468004/137918682-e5e91907-6038-49ef-b1b8-8dde2f3d7aac.png)





## Spring Boot

### SpringApplication

- Spring Boot로 프로젝트 실행시에 Application 클래스를 만든다

  ```java
  @SpringBootApplication
  public class Application {
    public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
    }
  }
  ```

  - `@SpringBootApplication` 어노테이션을 통해 스프링 Bean을 읽어와 자동으로 생성

  - `SpringApplication.run()`으로 해당 클래스를 run하면

    - 내장 WAS를 실행
    - 개발자가 따로 톰캣과 같은 외부 WAS를 설정해주지 않아도 되므로 간단하게 어플리케이션을 실행할 수 있다

    

## Spring Container

> - 인스턴스의 생명주기를 관리 및 인스턴스들에게 기능 제공
> - 객체의 생명주기를 직접 관리

- 자바 객체를 담고 있으며 필요한 객체를 여기서 언제든지 꺼내와 사용할 수 있다

- Bean들의 생명주기를 관리

  - DI를 통해 필요한 곳에 주입
  - Ioc를 이용하여 Bean들을 관리
    - 개발자가 인스턴스의 생성 및 관리를 할 필요없다
    - 스프링 컨테이너가 대신해서 인스턴스의 생명주기를 관리(제어권이 개발자에서 Container로 역전)

- 스프링 컨테이너의 종류로는 `BeanFactory`와 `ApplicationContext`가 존재

  > **BeanFactory**
  >
  > - 스프링 설정파일에 등록된 Bean 객체를 생성하고 관리하는 기본적인 기능 제공
  > - Bean 생성은 해당 객체가 사용되는 시점에 (Lazy Loading, JPA에서 사용하는 기법)
  >
  > 
  >
  > **ApplicationContext**
  >
  > - BeanFactory를 상속받아 Bean 객체를 생성하고 관리
  > - 뿐만 아니라 트랜잭션 관리, DI, Ioc 등 많은 지원을 한다
  > - BeanFactory와 달리 컨테이너가 구동되는 시점에 객체들은 싱글톤 패턴으로 생성
  >
  >
  > **IoC Container**
  >
  > - ApplicationContext 인터페이스를 구현한 클래스의 Object임
  > - 즉 ApplicationContext == IoC Container

  

## Bean

> 컨테이너에 들어있는 객체
>
> @Bean을 사용하거나 xml 설정을 통해 일반 객체를 Bean으로 등록 가능하다
>
> Spring 2.0버전 이후부터는 StereoType이 생겨나서 @Controller, @Configuration, @Service 등으로 Bean 생성 가능



### Bean 생명주기

- 객체 생성 -> 의존 설정 -> 초기화 -> 사용 -> 소멸
- Spring Container에 의해 생명주기 관리
- Spring Container 초기화 시 빈 객체 생성, 의존 객체 주입 및 초기화
- Spring Container 종료 시 빈 객체 소멸

### Bean 초기화 방법 3가지

1. Bean 초기화 메소드에 `@PostConstruct` 사용
2. `InitializingBean`인터페이스의 `afterPropertiesSet() ` 메소드 오버라이드
3. 커스텀 `init()` 메소드 정의

### Bean 소멸 방법 3가지

1. Bean 소멸 메소드에 `@PreDestroy` 사용
2. `DisposableBean`인터페이스의 `destroy()`메소드 오버라이드
3. 커스텀 `destroy()` 메소드 정의

##### 권장하는 방법은 1번 방법 => 사용방법 간결, 코드에서 직관적으로 파악 가능

### Bean Scope

- Singleton (default)

  - Application에서 Bean 등록시 Singleton Scope로 등록
  - Spring Ioc Container(= ApplicationContext)하나 당 한 개의 인스턴스만 생성
  - 메모리 관리 효율이 좋다

  

- Prototype

  - 컨테이너에서 Bean 가져다 쓸 때 항상 다른 인스턴스를 사용
  - 모든 요청에서 새로은 인스턴스 생성
  - GC에 의해 Bean제거

  

- Request

  - Bean 등록 시 하나의 HTTP Request 생명주기 안에 단 하나의 Bean만 존재
  - 각각의 HTTP 요청은 고유의 Bean 객체를 보유
  - Spring MVC Web Application에서 사용

  

- Session

  - 하나의 HTTP Session 생명주기 안에 단 하나의 Bean만 존재
  - Spring MVC Web Application에서 사용

  

- Global Session

  - 하나의 global HTTP Session 생명주기 안에 한 개의 Bean 생성
  - Spring MVC Web Application에서 사용

  

- application

  - ServletContext 생명주기 안에 한 개의 Bean 지정
  - URL 설정이 있는 Bean들을 생성(@Controller, Interceptor)

  

## Application Context, Servlet Context

### Application Context

- `Web Application` 최상단에 위치하고 있는 `Context`
- `Spring Container`중 하나이며 `BeanFactory`를 상속받은 Context
- `IoC Container`라고도 한다(Ioc Container가 ApplicationContext를 구현한 객체)
- 서로 다른 Servlet에서 공통적으로 사용할 수 있는 Bean들을 생성한다
- **ApplicationContext에 정의된 Bean들은 ServletContext에 정의된 Bean을 사용할 수 없다**

### Servlet Context

- Servlet(request -> 처리 -> response 일련의 과정) 단위로 생성되는 Context
- URL 설정이 있는 Bean들을 생성한다(@Controller, Interceptor)
- ApplicationContext를 자신의 부모로 사용한다
- ApplicationContext와 ServletContext에 동일한 Id로 선언된 Bean이 있다면 ServletContext에 있는걸 사용
- Bean 찾는 순서
  - Servlet Context에서 우선 찾고
  - 없으면 Application Context에서 찾는다
- **Servlet Context에 등록된 Bean은 Application Context의 Bean을 사용할 수 있다**



## IoC(Inversion of Control)

> 객체의 제어권이 바뀐 것, 즉 제어권이 본인이 아니라 다른 녀석에게 넘어간 것
>
> 프레임워크, DI에서 사용되는 개념
>
> 작성된 코드의 호출이 프레임워크에 의해서 -> 제어권이 프레임워크에게

### Spring에서의 Ioc

- Ioc Container(ApplicationContext)는 우리가 흔히 알고 있고 개발해온 POJO(Bean)의 생명주기를 관리 및 추가 기능(DI, Ioc 등) 제공

### 라이브러리 vs 프레임워크

- IoC 개념이 적용되었냐 아니냐의 차이
- 라이브러리는 작성한 코드가 제어권을 가지고 있되 필요할때 라이브러리 코드를 사용하는 것
- 프레임워크는 개발자가 작성한 코드의 제어권이 프레임워크에게 있으며 프레임워크에 의해 구동된다.



## DI(Dependency Injection)

> - 스프링 컨테이너가 지원하는 핵심 개념 중 하나
> - 설정 파일을 통해 객체간에 의존관계를 설정하는 역할을 한다
> - 각 클래스들 사이에 필요로 하는 의존관계를 파악하고 알아서 연결해준다

### DI의 특징

- 다른 곳에서 객체 생성하고 생성된 객체를 참조하게 한다
- DI는 IoC를 기반으로 한다, 클래스가 외부로부터 의존성을 가져야 함

### DI가 필요한 이유?(장점)

- 클래스를 재사용할 가능성을 높이고 다른 클래스와 독립적으로 클래스를 테스트할 수 있음

### DI의 3가지 방법

- Constructor Injection: 생성자에서 주입
- Method Setter: 메소드 매개변수 주입
- Field Injection: 멤버 변수 삽입



## MVC 패턴

> Model-View-Controller의 합성어로 소프트웨어 디자인 패턴이다

### Model

- 백그라운드에서 동작하는 로직을 처리
- 주로 DB 테이블과 대응되는 경우가 많다

### View

- 사용자가 보게 될 결과 화면을 출력

### Controller

- 사용자의 입력을 처리한다
- 요청사항을 파악하여 적절한 데이터를 Model에게 의뢰하고 데이터를 View에 반영하여 사용자에게 알려준다



## AOP

> - Aspect Oriented Programming(관점 지향 프로그래밍)
> - 어떤 로직을 기준으로 `핵심 관점, 부가 관점으로 나누고 관점을 기준으로 모듈화 하는것`
> - 핵심 관점은 주로 비즈니스 로직
> - 부가 관점은 주로 핵심 로직을 수행하기 위한 사전/사후 작업
>   - DB 연결, 로킹, 파일 입출력 등

### AOP 목적

- 소스 코드에서 반복해서 사용하는 코드(= 흩어진 관심사, Concern)을 Aspect로 모듈화하여 핵심 로직이서 분리 및 재사용
- 개발자가 핵심 로직에 집중할 수 있게

### AOP 주요 용어

- Aspect
  - 흩어진 관심사를 모듈화 한 것
- Target
  - Aspect를 적용하는 곳
  - 클래스, 메소드 등에 적용
- Advice
  - 실질적으로 수행해야 하는 기능을 담은 구현체
- JoinPoint
  - Advice가 적용될 위치
  - 끼어드는 지점
  - 메소드 진입시, 생성자 호출 시 등등
- PointCut
  - JoinPoint를 더욱 상세히 정의하는 것
  - 더욱 구체적으로 Advice가 실행될 지점
- Weaving
  - PointCut에 의해 결정된 Target의 JoinPoint에 Advice를 삽입하는 과정

<img src="https://user-images.githubusercontent.com/41468004/137937501-ab0fec0b-19b2-4672-afab-5c77628b27fa.png" style="zoom:67%;" />

### Spring AOP의 특징

- **프록시 패턴** 기반의 AOP 구현체
  - 프록시: 마치 자신이 클라이언트가 사용하려는 대상인 것처럼 위장해서 사용자의 요청을 처리해주는 것
  - 즉, 타깃에 대한 접근 방법을 제어하려는 목적, 당장 타겟에 대한 레퍼런스는 필요한데 타겟을 실제로 사용하는 것이 아닐때 사용(JPA의 LAZY FETCH)
- 프록시가 Target 객체의 호출을 가로채 Advice 수행 전/후 핵심 로직 호출
- 스프링 Bean에만 AOP적용 가능
  - 메소드 조인 포인트만 지원하여 메소드가 호출되는 런타임 시점에만 AOP 적용 가능



## Filter, Interceptor

> 애플리케이션에서 자주 사용되는 기능을 분리하여 관리할 수 있도록 Spring이 제공하는 기능

### Filter, Interceptor 흐름

[#참고](#https://github.com/WeareSoft/tech-interview/blob/master/contents/images/filterInterceptor.jpg)

![](https://github.com/WeareSoft/tech-interview/blob/master/contents/images/filterInterceptor.jpg?raw=true)

1. 서버 실행 시 `Servlet`이 올라오는 동안 init 후 `doFiler` 수행
2. `DispatcherServlet`을 지나쳐 `Interceptor`의 `preHandler` 수행
3. `Controller`를 거쳐 내부 로직 수행 후 `Interceptor`의 `postHandler` 수행
4. `doFiler` 수행
5. Servlet 종료 시 Distroy



### Filter의 특징

- Dispatcher-Servlet 이전에 수행, Request, Response에 대해 조작 가능
- Spring Context 외부에 존재하며 Spring과 무관한 자원들에 대해 동작
- 일반적으로 web.xml에 설정

### Interceptor의 특징

- Dispatcher-Servlet 이후, Controller 이전에 수행
- Spring Context 내부에 존재하며 Controller의 Response와 Reqeust에 관여하며 모든 Bean에 접근 가능
- 일반적으로 servlet-context.xml에 설정

### Filter, Interceptor 차이점

- Filter는 WAS단에 설정, Spring과 무관한 자원에 대해 동작, Interceptor는 Spring Context 내부에 설정되어 컨트롤러 접근 전 후에 흐름 가로채서 처리
- Filter는 doFiler()만 존재, Interceptor는 pre, postHandler로 명확하게 구분

### Interceptor AOP 차이

- Interceptor와 달리 AOP는 메소드 전 후 지점에 자유롭게 호출 가능
- Interceptor는 주소로 대상을 구분해서 걸러내야하는데, AOP는 주소/파라미터/어노테이션 등 다양한 방법으로 대상 지정할 수 있다