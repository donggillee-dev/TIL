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

1. Constructor Injection
   `Autowired 사용 방식`

   ```java
   @Component
   public class JackCoding {
     private final Jack jack;
     private final Soy soy;
     
     @Autowired //Spring 4.3부터 단일 생성자는 @Autowired 표기 불필요
     public JackCoding(Jack jack, Soy soy) {
       this.jack = jack;
       this.soy = soy;
     }
   }
   ```

   `Lombok을 이용한 방식`

   ```java
   @Component
   @RequiredArgsConstructor
   public class JackCoding {
     private final Jack jack;
     private final Soy soy;
   }
   ```

   - @RequiredArgsConstructor 어노테이션은 `final`이나 `@NotNull`을 사용한 필드에 대한 생성자를 자동으로 생성

   

2. Field Injection
   `가장 널리 사용되는 방식이며 @Autowired를 이용`

   ```java
   @Component
   public class JackCoding {
     @Autowired
     private Jack jack;
     
   }
   ```

   - 생성자 주입과 다르게 필드에 final 정의 불가

   

3. Setter Injection

   ```java
   @Component
   public class JackCoding {
     private Jack jack;
     
     @Autowired
     public void JackCoding(Jack jack) [
       this.jack = jack;
     ]
   }
   ```

   - 이 방식도 final 선언 불가

### Constructor Injection을 권장하는 이유

#### 1. 순환 참조 사전 방지

- 객체의 의존성 추가하다보면 A -> B, B -> A와 같은 참조가 생성될 수 있다

  ```java
  @Component
  public class Jack {
    @Autowired
    private Coding coding;
    
    public void jack() {
      coding.coding();
    }
  }
  
  @Component
  public class Coding {
    @Autowired
    private Jack jack;
    
    public void coding() {
      jack.jack();
    }
  }
  
  @Component
  public class JackCoding {
    @Autowired
    private Jack jack;
    
    @Autowired
    private Coding coding;
    
    public void jackCoding() {
      jack.jack();
      coding.coding();
    }
  }
  ```

  - 이 상태에서 서버 구동하면 잘됨
  - but jackCoding()메서드가 실행되는 순간 순환참조로 인해 서버가 죽는다
  - `즉, 메소드 실행 이전까지 순환 참조를 알아차릴 수 없음`

  똑같이 생성자 주입 방식 채택해보자

  - 아마 서버가 실행되기도 이전에 에러가 발생할 것
  - `즉, 서버 자체가 구동되지 않으므로 바로 순환 참조를 알아차릴 수 있다`

- 이러한 차이가 생기는 핵심?

  - 빈을 주입하는 순서의 차이
  - 필드 주입, setter 주입은 우선 빈을 생성한 후 해당 빈에 주입해야 하는 빈을 찾는다
  - 생성자 주입은 주입해야하는 빈을 먼저 찾은 후 빈을 생성한다
  - 그렇기에 사전에 순환 참조 오류를 방지할 수 있다



#### 2. final 선언 가능

- final로 주입해야 하는 빈에 대한 불변성을 보장해준다
- 즉, 런타임에 객체의 불변성을 보장해준다



#### 3. 테스트 코드 작성 용이

- Mockito 같은것을 이용할 필요 없이 단순히 원하는 객체 생성 후 생성자에 넣어주면 됨



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



<img src="https://user-images.githubusercontent.com/41468004/140912981-20a6b974-339f-4ad6-a957-733f6a0f0487.png" style="zoom:50%;" />

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



## @Controller(Spring MVC Controller)

> Spring에서의 Controller는 @Controller, @RestController 두가지 존재
>
> 가장 큰 차이점은 HTTP Response Body가 생성되는 방식

### Controller - View

`View를 반환하기 위해 사용`

<img src="https://user-images.githubusercontent.com/41468004/140636627-998714ce-b73a-4c15-8d83-8baecbfb8fe7.png" style="zoom:50%;" />

> 1. Client측의 URI를 통한 서비스 요청
> 2. DispatcherServlet에 의해 요청 가로채진다
> 3. Handler Mapping을 통해 적절한 Controller 매치
> 4. Controller의 처리 완료 후 적절한 View를 찾기 위해 View Resolver로 View 요청
> 5. 전달받은 View에 데이터를 적절히 바인딩 하여 결과 View를 Client로 전달

### Controller - Data

`@Controller에서 Data를 반환해야하는 경우에 사용`

<img src="https://user-images.githubusercontent.com/41468004/140636720-cc253f29-e9c9-47a3-ad1b-3fbec27c7d9b.png" style="zoom:50%;" />

> 1. Client의 요청
> 2. DispatcherServlet의 요청 수신
> 3. Handler Mapping을 통한 적절한 Controller
> 4. Controller에서 @ResponseBody를 사용
> 5. Json형태로 Client에게 데이터 전달

- 여기서 ViewResolver 대신에 `HttpMessageConverter`가 동작
- HttpMessageConverter에는 여러 `Converter`가 등록되어 있으며 반환해야하는 데이터에 따라 사용되는 Converter가 다르다
- `HTTP Accept 헤더`와 `서버의 컨트롤러 반환 타입` 이 둘을 조합해 적합한 HttpMessageConverter를 선택한다

### Controller Code

```java
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  
  //Data 반환
  @PostMapping("value = /info")
  public @ResponseBody User info(@RequestBody User user) {
    return userService.retrieveUserInfo(user);
  }
  
  //View 반환
  @GetMapping("value = /infoView")
  public String infoView(
    Model model, 
    @RequestParam(value = "userName", required = true) String userName
  ) {
    User user = userService.retrieveUserInfo(userName);
    model.addAttiribute("user", user);
    
    return "/user/userInfoView";
  }
}
```



### @RestController(Spring Rest Controller)

`Json 형태로 객체 데이터를 반환하기 위한 용도`

<img src="https://user-images.githubusercontent.com/41468004/140637088-e9d1f8da-ca36-4a0a-9e62-69055929f7a9.png" style="zoom:50%;" />

> 1. Client의 서비스 요청
> 2. DispatcherServlet -> Handler Mappging에 의해 적절한 Controller반환
> 3. RestController는 그 요청을 처리하고 적절한 데이터를 반환(JSON)

```java
@RestController
@RequsetMapping("/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  
  @PostMapping(value = "/info")
  public ResponseEntity<User> info(
    @RequestParam(value = "userName", required = true) String userName
  ) {
    return Optional.ofNullable(userService.retrieveUserInfo(userName))
      .map(user -> ResponseEntity.ok(user))
      .orElseGet(() -> ResponseEntity.noContent().build());
  }
}
```



## Transaction

> 단일 쿼리로 해결할 수 없는 로직을 처리할 때 필요한 개념
>
> 더 이상 쪼갤 수 없는 최소 작업 단위를 의미한다

### Transaction의 필요성

- 여러 작업을 진행하다가 문제가 생겼을 시 이전 상태로 롤백하기 위해 사용되는 것이 Transaction
- 트랜잭션 커밋 : 작업이 마무리 되었다
- 트랜잭션 롤백 : 작업을 취소하고 이전 상태로 되돌린다

- 하나의 트랜잭션 안에 여러 작업 처리중
  - 잘 처리되었다면 커밋으로 변경내용 반영
  - 제대로 처리 안되었다면 작업들의 변경내용 이전으로 되돌림

### Spring이 제공하는 Transaction 핵심 기술

#### 1. Transaction 동기화

- 여러 작업을 하나의 트랜잭션으로 쉽게 관리하기 위한 기술

- 트랜잭션을 시작하기 위한 Connection 객체를 어딘가에 저장해놓고 필요할때마다 꺼내서 사용하는 기술이다

- 작업 쓰레드마다 독립적인 Connection을 꺼내서 사용하기에 충돌 없다

  > JDBC가 아닌 Hibernate에서는 Connection이 아니라 Session
  >
  > Connection
  >
  > - DB와의 물리적인 연결 단위
  >
  > Session
  >
  > - 하나의 Session안에 Connection이 포함되어있다
  > - 1차 캐시를 사용한다

#### 2. Transaction 추상화

<img src="https://user-images.githubusercontent.com/41468004/140641111-7926c5bc-eb04-4660-ad6e-40f0dde4ba2b.png" style="zoom:50%;" />

- 1에서의 기술적인 종속성(Connection, Session)을 해결하기 위해 제공하는 기술
- 트랜잭션 기술의 공통점을 담은 추상화 기술
- 각 기술(JDBC, JPA, Hibernate)마다 종속적인 코드를 작성하지 않고 일관되게 트랜잭션 처리할 수 있게 해줌

#### 3. AOP를 이용한 트랜잭션 분리

- 2에서의 비즈니스 로직 + 트랜잭션 로직 중첩 현상을 분리하기 위해 AOP 이용
- 트랜잭션 관리 코드를 밖으로 빼낸것 같이 동작하게끔 `@Transactional` 제공

### Spring Transaction 세부 설정

> 트랜잭션에 대해서 4가지 속성을 적용함으로써 트랜잭션을 세부적으로 이용할 수 있게
>
> - 트랜잭션 전파
> - 격리수준
> - 제한시간
> - 읽기전용

#### 트랜잭션 전파(PROPAGATION)

`트랜잭션간의 경계에서 이미 진행중인 트랜잭션이 있거나 없을시 어떻게 동작할지 명시해주는 것`

1. A의 트랜잭션에 참여(PROPAGATION REQUIRED)
   - B의 코드는 새로운 트랜잭션 생성 x, A의 트랜잭션에 참여
   - B의 작업이 모두 마무리 되고 난 후 A처리
     - A 처리시에 오류 발생하면 B작업까지 취소 => 둘 다 하나의 트랜잭션으로 묶여있기 때문
2. 독립적인 트랜잭션 생성(PROPAGATION REQUIRES NEW)
   - B의 트랜잭션은 A 트랜잭션과 무관하게 생성 가능
   - B의 트랜잭션 경계를 빠져나오는 순간 B의 트랜잭션은 독자적으로 커밋 or 롤백되며 A에게 영향을 끼치지 않는다
3. 트랜잭션 생성 없이 동작(PROPAGATION NOT SUPPORTED)
   - B의 작업에 대해 트랜잭션 생성 X
   - 단순히 조회인 경우에

#### 격리수준(Isolation)

- 모든 Transaction은 격리수준을 가지고 있어야한다
- 모든 트랜잭션을 독립시키고 순차 진행하면 안정하지만 성능 떨어진다
- 따라서 적절한 격리수준을 줌으로서 적절히 동시에 진행 + 문제 발생 안하게

#### 제한시간

- 트랜잭션 수행하는데 제한시간을 줄 수 있다

#### 읽기전용

- 읽기전용으로 함으로써 데이터의 조작을 막을 수 있다

### Spring 트랜잭션의 세부 설정

#### 전파 속성(Propagation)

1. REQUIRED
   - Default 속성, 보통 이 속성이면 다 된다
   - 미리 시작된 트랜잭션이 존재하면 참여하고 없으면 새로 시작
2. SUPPORTS
   - 이미 시작된 트랜잭션이 있으면 참여, 그렇지 않으면 없이 진행
3. MANDATORY
   - 이미 시작된 트랜잭션이 있으면 참여, 그렇지 않으면 새로 시작 + 예외 발생
4. REQUIRES_NEW
   - 항상 새로운 트랜잭션을 시작해야 하는 경우
   - 이미 시작된 트랜잭션이 있다면 트랜잭션을 잠시 보류
5. NOT_SUPPORTED
   - 이미 진행중인 트랜잭션이 있으면 이를 보류, 트랜잭션 사용 X
6. NEVER
   - 이미 진행중인 트랜잭션이 있으면 예외 발생, 트랜잭션 사용 X 강제
7. NESTED
   - 이미 진행중인 트랜잭션이 있으면 중첩 트랜잭션 시작
   - 트랜잭션 안에 서브 트랜잭션을 만드는 것, 독립적인 트랜잭션을 만드는 REQUIRES_NEW와는 다르다
   - 서브 트랜잭션은 부모 트랜잭션의 롤백, 커밋에 영향 O
   - 서브 트랜잭션의 커밋, 롤백은 부모 트랜잭션에게 영향 X

#### 격리 수준(Isolation)

`동시 여러 트랜잭션 진행시 각 트랜잭션의 작업 결과를 다른 트랜잭션에게 어떻게 노출시킬지 결정`

1. DEFAULT
   - DB 드라이버의 디폴트 설정을 따른다
2. READ_UNCOMMITED
   - 가장 낮은 격리수준
   - 하나의 트랜잭션이 커밋되기 이전에 그 변화가 다른 트랜잭션에 그대로 노출
3. READ_COMMITED
   - 가장 많이 사용되는 격리수준
   - 다른 트랜잭션이 커밋하지 않은 정보는 읽을 수 없음
   - 대신 하나의 트랜잭션이 읽은 로우를 다른 트랜잭션이 수정 가능
4. REPEATABLE_READ
   - 하나의 트랜잭션이 읽은 로우를 다른 트랜잭션이 수정할 수 없도록 막는다
   - 하지만 새로운 로우 추가는 막지 않음
   - 따라서, 트랜잭션이 끝나기 전에 추가된 로우가 발견될 수 있음
5. SERIALIZABLE
   - 가장 강력한 격리수준
   - 모든 트랜잭션을 순차적으로 진행

#### 읽기 전용(readOnly)

- 읽기 전용으로 설정, 성능 최적화
- 쓰기 작업이 일어나는 것을 의도적으로 방지

#### 롤백/커밋 예외

- 런타임 예외 발생하면 롤백
- 예외가 발생하지 않거나 체크 예외가 발생하면 커밋

#### 제한 시간(timeout)

- 트랜잭션에 제한시간 지정
- 별도 값을 지정하지 않으면 트랜잭션 시스템의 default 시간을 따른다

### Spring에서 트랜잭션 사용법

#### @Transactional

- 이를 명시해준 곳은 포인트 컷의 대상으로 자동 등록되며 트랜잭션 관리 대상이 된다 ( AOP 적용 )
- 즉, 포인트 컷에 등록하고 트랜잭션 속성을 부여하는 것이다
- 이 어노테이션 적용 시
  - 타깃 메소드 -> 타깃 클래스 -> 선언 메소드 -> 선언 타입(클래스 or 인터페이스) 순으로 @Transactional이 적용되었는지 차례로 확인

- 보통 비즈니스 로직을 담고 있는 서비스 계층의 메소드와 결합
  - 일반적으로 데이터를 읽어오고 사용하고 변경하고 등의 작업을 하는 곳이 대부분 Service Layer이기 때문
  - 서비스 클래스의 상단에 @Transactional해주면 그 안의 모든 메소드가 트랜잭션 관리 대상이 된다



## 토큰 기반 vs 서버(세션) 기반 인증

### 서버(세션) 기반 인증

- 상태를 유지해야하기에 Stateful Server 라고도 한다
- 서버 측에서 사용자들의 정보를 기억
- 사용자들의 정보를 기억하기 위해서 세션을 유지
  - 메모리, 디스크, 데이터베이스를 통해 관리

<img src="https://user-images.githubusercontent.com/41468004/140872677-8a1b6eac-e79d-4847-bfb9-68ff1d47ffb2.png" style="zoom:50%;" />

- 로그인 중인 사용자가 늘어날 경우 서버 RAM에 부하가 걸린다
  - 이를 회피하기 위해 DB에 저장하기도 하는데 DB 부하 또한 피하기 쉽지 않다
- 분산 서버의 경우 세션 또한 모든 서버에서 공유 및 분산처리 => 매우 어렵고 복잡
- CORS 오류 회피 어려움
  - 하나의 origin에 대해 하나의 쿠키만 사용 가능
  - 분산 서버로 나누게 되면 CORS에 필요한 쿠키도 복잡하게 관리

### 토큰 기반 인증

- 인증받은 사용자들에게 토큰을 발급
- 사용자들은 토큰을 가지고 접근 => 이를 통해 유효성 검사
- 사용자의 상태를 저장할 필요가 없으므로 Stateless Server

<img src="https://user-images.githubusercontent.com/41468004/140873088-d5f16296-8e5c-40eb-ada9-1c515df0f81f.png" style="zoom:50%;" />

- Stateless하기에 서버는 클라이언트와 분리 => 확장성이 좋다
  - 분산 서버 + 토큰의 경우 세션과는 달리 어떤 서버로 요청을 보내도 괜찮다
- 클라이언트 측의 쿠키를 보내지 않기에 보안 취약점이 덜하다
- CORS 에러를 해결할 수 있다
  - 어떤 디바이스, 도메인에서도 토큰에 대한 유효성만 검증하면 됨



## JWT

`Json 포맷을 이용해서 사용자에 대한 속성을 저장하는 Claim 기반 Web Token`

> Claim?
>
> - 사용자의 정보나 데이터 속성 등을 의미
> - 클레임 토큰이란
>   - 토큰 안에 위와 같은 정보들을 담은 토큰이라고 보면 된다

### 구성

- Header, Payload, Signature
- 각 부분은 Base64로 인코딩 되어 표현
- 또한 각 부분을 이어주기 위해 . 구분자를 사용

#### Header

- typ와 alg 두 가지 정보로 구성
  - tpy : 토큰의 타입을 지정
  - alg : 알고리즘 방식을 지정, Signature를 해싱하기 위한 알고리즘 지정

#### PayLoad

- 토큰에서 사용할 정보의 조각들인 클레임이 담겨있다

#### Signature

- 토큰을 인코딩하거나 유효성 검증을 할때 사용하는 고유한 암호화 코드



## 

## 

## Spring Security 처리 과정

<img src="https://user-images.githubusercontent.com/41468004/140858354-29f042df-e7a8-4012-8ac8-05c8aacde4ad.png" style="zoom:50%;" />

1. Client가 ID, PW로 로그인 요청
2. `AuthenticationFilter`에서 `UserNamePasswordAuthenticationToken`을 생성해서 `AuthenticationManager`로 전달
3. `AuthenticationManager`는 등록된 `AuthenticationProvider`들을 조회해서 인증을 요청한다
4. 인증 요청을 받은 `AuthenticationProvider`는 `UserDetailService`를 통해 입력받은 ID에 대한 User의 정보를 DB에서 조회한다
5. 사용자가 입력한 PW를 암호화하여 조회한 User의 PW와 동일한 것인지 확인
   1. 동일하다면 성공한 `UsernameAuthenticationToken`을 생성해서 `AuthenticationManager`에게 반환
6. `AuthenticationManager`는 `UsernameAuthenticationToken`을 `AuthenticationFilter`로 전달
7. `AuthenticationFilter`는 전달받은 `UsernameAuthenticationToken`을 `LoginSuccessHandler`로 전송, 토큰을 response 헤더에 추가하여 반환



## @RequestParam, @RequestBody, @ModelAttribute

### @RequestParam

- 1개의 HTTP 요청 파라미터를 받기 위해 사용
- 기본값이 required = true이기에 기본적으로 반드시 해당 파라미터가 전송되어야함
- false로도 가능
- 해당 파라미터에 대해서 defaultValue 옵션을 통해서도 설정 가능

### @RequestBody

- 클라이언트가 전송하는 Json 형태의 HTTP Body 내용을 Java Object로 변환해주는 역할
- Get 메소드에 사용 불가
- MessageConverter중 하나인 MappingJackson2HttpMessageConverter를 통해 Java 객체로 변환
  - 개본 생성자를 통해 객체 생성
  - Reflection을 이용해서 값을 할당
  - 이 때문에 Setter가 필요 없다

### @ModelAttribute

- Multipart/form-data 형태의 HTTP Body 내용과 HTTP 파라미터들을 Setter를 통해 1 : 1로 객체에 바인딩하기 위해 사용
- 매핑시키는 파라미터의 타입이 객체의 타입과 일치하는지 등등 다양한 검증이 추가 진행
- Setter가 없다면 바인딩되지 않는다
  - 값을 매핑해주는 과정이 있기 때문
  - @RequestBody는 값의 변환이기에 Setter 불필요



## @Bean, @Configuration, @Component

### @Bean, @Configuration

- 개발자가 직접 제어가 불가능한 외부 라이브러리, 설정을 위한 클래스를 Bean으로 등록할 때 @Bean 어노테이션 사용
- 1개 이상의 @Bean을 재공하는 클래스의 경우 반드시 @Configuration을 명시해주어야 한다

### @Component

- 개발자가 직접 개발한 클래스를 Bean으로 등록하고자 하는 경우 @Component 어노테이션 활용



## @Autowired 빈 탐색 전략, @Qualifier, @Primary

### Bean 조회 규칙

@Auowired가 등록된 Bean을 찾을 때 다음과 같음 규칙으로 조회

1. 주입받고자하는 타입으로 매칭 시도
2. 타입이 여러개면 필드, 파라미터 이름으로 매칭 시도

빈의 이름이 충돌되어 빈 이름만으로는 불가능할때 @Qualifier, @Primary 사용

추가로 @Autowired 말고도 @Resource 사용 가능

- @Autowired : 필드의 타입을 기준으로 찾음
- @Resource : 필드의 이름을 기준으로 찾음

### @Qualifier

- Bean의 이름만으로는 부족해서 추가적인 식별자를 부여해주는 방식
- Bean의 이름이 바뀌는 것이 아니라 추가적인 별칭, 구분자를 넣어주는 것

### @Primary

- 여러 개의 Bean들 중에서 우선순위를 부여하는 방식
- 만약 Primary, Qualifier 둘 다 등록되어있다면 Qualifier가 우선순위를 가진다

### Bean 등록 충돌 발생

- 위와 같이 처리해주지 않고 중복하여 작성한다면
  - 자동 빈 vs 자동 빈 : 빈 이름 충돌 에러 발생
  - 수동 빈 vs 자동 빈 : 과거에는 수동빈이 자동 빈 덮었지만 최근에는 에러 발생

## ApplicationContext와 Singleton

### ApplicationContext

- Bean Factory를 상속받아 확장한 것

#### 장점

- 클라이언트는 @Configuration이 붙은 구체적인 팩토리 클래스를 알 필요가 없음
- 종합 Ioc 서비스를 제공
- 이를 통해 다양한 빈 검색 방법 제공 가능

### Singleton

- 여러 빈을 요청하더라도 매번 동일한 인스턴스를 돌려준다
- 대규모 트래픽을 처리하기 위함
- 빈을 싱글톤 스코프로 관리해 1개의 요청이 들어왔을 때 여러 쓰레드가 빈을 공유해 처리할 수 있도록

#### Singleton 구현의 단점

- private 생성자를 가지고 있어 상속 불가능
- 테스트가 어려움
- 멀티쓰레드 환경에서 여기저기 가져다가 쓰게되면 전역 상태가 될 수 있음

#### Spring Singleton의 장점

- static 메소드나 private 생성자를 사용하지 않아 객체지향적 개발 가능
- 테스트가 용이해짐
- 멀티쓰레드 환경에서 위험할수도 있기에 무상태로 만드는데 Spring 자체에서 Bean들을 이렇게 만들어준다

## Entity or Domain Object와 DTO를 분리해야 하는 이유

> 1. 관심사의 분리
> 2. Validation 로직 및 불필요한 코드 등과의 분리
> 3. 순환 참조 방지

### 관심사 분리

- DTO의 핵심 관심사는 이름 그대로 데이터 전달
- 엔티티는 핵심 비즈니스 로직을 담는 비즈니스 도메인 영역의 일부

### Validation 로직 및 불필요한 코드 등과의 분리

- @Valid 사용시 관련 유효성 검증 어노테이션을 붙여야하는데 코드가 더러워진다
- 또한 API에서 응답에 불필요한 변수들은 @JsonIgnore등을 사용해야하는데 추가적으로 코드가 복잡해짐

### 순환 참조 방지

- API에서 Entity 반환시 Json화시킴으로서 내부적으로 toString 호출
- 각 내부 필드내에 양방향 참조 필드가 존재할 시 참조하는 엔티티도 조회
- 해당 엔티티 내에서 toString발생 -> 무한반복

## @Component, @Controller, @Service, @Repository 차이

> @Controller, @Service, @Repository 모두 @Component가 붙어있다
>
> 스프링에서는 컴포넌트 스캔을 통해 @Component가 붙어있는 클래스들을 스캔해서 빈으로 등록
>
> 즉 위 4가지들도 스캔해서 빈으로 등록
>
> 어노테이션은 상속을 지원하지 않기에 이런 처리는 자바가 아니라 스프링이 하는것

### @Controller

- 해당 클래스를 스프링 MVC 모듈의 컨트롤러로 인식하게 처리

### @Service

- 특별한 처리를 해주는 것은 아님
- 하지만 개발자들에게 비즈니스 로직임을 명시적으로 보여줌

### @Repository

- 해당 클래스를 스프링 Data Access Layer로 인식하게끔
- Data Layer의 예외를 스프링 예외로 변환 => DataAccessException
- 이를 통해 db마다 다른 예외가 발생하여도 서비스 레이어에 영향을 끼치지 않음
