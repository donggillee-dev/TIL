# Java

- [컴파일 과정](#컴파일-과정)
- [JVM](#jvm)
- [Java Garbage Collection](#java-garbage-collection)
- [Java SE, EE](#java-se,-ee)
- [JDK, JRE](#jdk,-jre)
- [Java의 장단점](#java의-장단점)
- [Java의 데이터](#java의-데이터)
- [Call By Value & Call By Reference](#call-by-value-&-call-by-reference)
- [String / new String](string-/-new-string)
- [String, StringBuilder, StringBuffer](#string,-stringbuilder,-stringbuffer)
- [클래스, 객체, 인스턴스](#클래스,-객체,-인스턴스)
- [오버로딩과 오버라이딩](#오버로딩과-오버라이딩)
- [인터페이스와 추상클래스](#인터페이스와-추상클래스)
- [Reflection](#reflection)
- [Stream](#stream)
- [HashMap vs HashTable](#HashMap-vs-HashTable)
- [HashSet vs TreeSet](#hashset-vs-treeset)
- [Java 버전 특징](#java-버전-특징)



## 컴파일 과정

![](https://user-images.githubusercontent.com/41468004/137432755-fbb71da4-2927-4159-b713-c9a18cce2481.png)

1. 개발자가 Java 코드를 작성
2. 자바 컴파일러(Java Compiler)가 자바 소스파일(.java)을 컴파일함
   - 이때 나오는 파일은 자바 바이트 코드(.class) 파일로 JVM이 이해할 수 있는 코드이다.
3. 컴파일된 바이트 코드를 JVM의 클래스 로더(Class Loader)에게 전달
4. Class Loader는 동적로딩을 통해 필요한 클래스들을 로딩 및 링크하여 런타임 영역, 즉 JVM의 메모리에 올린다.
   - **클래스 로더의 세부 동작**
     1. **로드** : 클래스 파일을 가져와서 JVM의 메모리에 로드
     2. **검증** : 자바 언어 명세 및 JVM의 명세에 명시된 대로 구성되어 있는지 검사
     3. **준비** : 클래스가 필요로 하는 메모리를 할당
     4. **분석** : 클래스의 상수 풀 내 모든 심볼릭 레퍼런스를 다이렉트 레퍼런스로 변경
     5. **초기화** : 클래스 변수들을 적절한 값으로 초기화 ( static 필드 )

5. 실행엔진은 JVM 메모리에 올라온 바이트 코드들을 명령어 단위로 하나씩 가져와서 실행.
   이때 실행 방법은 두가지
   1. 인터프리터
      - 바이트 코드 명령어를 하나씩 읽어서 해석하고 실행
      - 하나하나의 실행은 빠른데 전체적인 실행 속도가 느리다는 단점 존재
   2. JIT(Just-In-Time) 컴파일러
      - 인터프리터의 느린 실행속도를 보완하기 위해 도입된 방식
      - 바이트 코드 전체를 컴파일 해 바이너리 코드로 변경하고 이후에는 메서드를 더 이상 인터프리팅 하지 않음, 컴파일된 전체 바이트 코드만 실행하면 됨
      - C1 컴파일러
        - 주로 GUI 애플리케이션 및 기타 클라이언트 프로그램에 사용
        - C2보다 컴파일 시간도 짧고 단순하게 설계됨
      - C2 컴파일러
        - 주로 실행시간이 긴 서버 애플리케이션에 사용
        - C1보다 컴파일 시간이 길지만 높은 수준의 최적화를 지원



## JVM

> 시스템 메모리를 관리하며, 자바 기반 애플리케이션을 위해 이식 가능한 독립적인 실행 환경 제공

#### 목적

`어떤 기기상에서 실행되고 있는 프로세스, 특히 자바 앱에 대한 리소스를 대표하고 통제하는 서버`

- 자바 프로그램이 어느 기기나 운영체제 상에서도 실행될 수 있도록 하는 것
- 프로그램 메모리를 관리하고 최적화하는 것



#### JVM 구성

- **Class Loader(클래스 로더)**

  > JVM내로 클래스 파일(.class)들을 로드하고, 링크를 통해 배치하는 작업을 수행

  - 자바는 동적코드이기에 컴파일 타임이 아니라 런타임에(클래스를 최초로 참조할때) 클래스로더가 해당 클래스를 로드하고 링크 한다.

- **Excution Engine(실행 엔진)**

  > 클래스 로더에 의해 JVM내에 링크 + 로드된 클래스(바이트 코드, .class)를 실행시킨다
  >
  > 자바 바이트 코드(.class)는 기계가 바로 수행 불가한 언어, 그래서 실행 엔진이 바이트 코드를 JVM 내부에서 기계가 실행할 수 있는 형태로 변경(아래 두가지 방식 채택)

  - Interpreter(인터프리터)
    - 명령어 단위로 한 줄씩 변경하여 실행
  - JIT(Just-In-Time) Compiler
    - 인터프리터 방식을 수행하다가 적절한 시점에 **바이트코드 전체를 네이티브 코드로 변경** 이후 인터프리팅 X
    - 물론 JIT 컴파일러가 컴파일하는 과정이 인터프리팅 하는 것보다 훨씬 오래 걸리므로 한 번만 실행되는 코드라면 굳이...?
      - 이 때문에 JVM들은 내부적으로 메서드 얼마나 자주 수행되는지 체크해야함
  - Garbage Collector
    - Garbage Collecting을 수행하는 쓰레드가 있다

- **Runtime Data Area(JVM의 메모리 공간)**

  > 프로그램을 수행하기 위해 OS로부터 할당받은 메모리 공간

  ![](https://user-images.githubusercontent.com/41468004/137437262-af96dff9-2911-4494-b240-6345dc01dc56.png)

  **PC Register**

  - Thread가 시작될 때 생성, 생성되는 Thread 공간마다 하나씩 존재
  - 현재 수행 중인 JVM 명령의 주소를 갖는다
    

  **JVM 스택 영역**

  - 메소드 영역을 나가면 소멸되는 데이터들을 위한 영역
  - 메소드 호출 시마다 각각의 스택 프레임이 생성
  - 메소드 수행 끝나면 그 프레임 별로 삭제
  - 메소드 안의 지역변수, 매개변수, 리턴 값 및 연산 시 일어나는 값들이 임시로 저장

  

  **Native method stack**

  - 자바 프로그램(.java)가 컴파일되어 생성된 바이트 코드(.class)가 아닌 실제 실행될 수 있는 기계어로 작성된 프로그램을 실행시키는 영역

  

  **Method Area(= Class area, Static area)**

  - 클래스 정보를 처음 메모리 공간에 올릴 때 `초기화되는 대상을 저장하기 위한 메모리 공간`
  - main 메소드에서 다른 메소드 호출, 여러 호출 흐름을 이어가이에 사실상 컴파일된 바이트코드의 대부분이 메소드 바이트코드 -> 즉 메소트 바이트코드의 전부가 올라간다고 보면 됨
  - 이 공간에는 `Runtime Constant Pool`이라는 별도의 관리 영역도 함께 존재
    - 상수 자료형을 저장 및 참조

  

  **올라가는 정보의 종류**

  1. Field Information
     - 멤버변수의 이름, 데이터 타입, 접근 제어자(public, private, default, protected)에 대한 정보
  2. Method Information
     - 메소드의 이름, 리턴타입, 매개변수, 접근제어자에 대한 정보
  3. Type Information
     - Class인지 Interface인지 여부를 저장

  

  ***Method Area는 클래스의 데이터들을 위한 공간.***

  ***Heap 영역은 객체를 위한 공간***

  ***둘 다 GC의 대상이다***

  

  **Heap Area**

  > 객체를 저장하는 가상 메모리 공간
  >
  > new 연산자로 생성된 객체와 배열을 저장
  >
  > 물론 Method Area에 올라간 클래스만 Heap에서 인스턴스로 생성 가능(클래스에 대한 정보가 필요)

  ![](https://user-images.githubusercontent.com/41468004/137440383-2acab9fb-be7c-4575-808d-758c87ee08c1.png)

  ##### Permanent Generation

  - Class Loader에 의해 Load되는 Class, Method 등에 대한 메타 데이터가 저장되는 곳, JVM이 사용

  - 여기서 GC가 발생할수도 있는데 Major GC임

  - Reflection을 사용하여 동적으로 클래스가 로딩되는 경우에 사용

    - 내부적으로 Reflection을 사용하는 Spring Framework의 경우 이 영역에 대한 고려 필요

      > Reflection?
      >
      > - 구체적인 클래스 타입을 알지 못해 그 클래스의 메소드와 타입, 변수들을 접근할 수 있도록 해주는 자바 API
      > - ex) Class.forName()

      

  ##### New/Young 영역

  - Eden : 객체들이 최초로 생성되는 공간
  - Survivor 0 / 1
    - Eden  영역이 가차면, Minor GC가 발생하는데 여기서 살아남은 애들을 넣어 놓는 곳
    - Minor GC는 Survivor 영역도 체크해서 살아남은 애들 다 한쪽으로 몰아넣는다 그래서 Survivor 영역 두개 중 하나는 꼭 비어있다
      

  ##### Old 영역

  - 여러 번의 Minor GC를 겪고 살아남은 객체들이 존재
  - Old 영역에서 메모리가 가득차면 Major GC가 발생 -> Minor GC보다 오래걸림



## Java Garbage Collection

>더 이상 참조되지 않는 쓰레기 객체들을 JVM이 알아서 찾아서 해제해주는 행위



### Stop The World🖐

- GC(Garbage-Collection)을 실행하기 위해 JVM이 어플리케이션 실행을 멈추는 것
- GC를 수행하는 쓰레드를 제외한 나머지 쓰레드 모두 작업을 멈춤
- 어떤 GC를 사용하더라도 Stop-The-World는 발생하므로 **GC 튜닝이랑 이 Stop-The-World 시간을 줄이는 것**



### GC 탄생 가설

- 대부분의 객체는 금방 접근 불가 상태(unreachable)가 된다.
- 오래된 객체에서 젊은 객체로의 참조는 아주 적게 존재
- 이 가설들의 장점을 최대한 살리기 위해 Heap영역을 크게 2개의 물리적 공간으로 나누었다. `Young`, `Old`



### Young & Old?

- **Young**
  - 새롭게 생성한 객체가 위치하는 곳
  - 첫번째 가설상 대부분의 객체는 금방 unreachable 해지기 때문에 Young영역에 생성되었다가 사라진다
  - 여기에서 객체가 사라졌을떄 Minor GC가 발생했다고 한다
- **Old**
  - reachable상태이며 Young 영역에서 오래 살아남은 객체가 여기로 복사된다
  - 대부분 Young보다 Old의 영역이 더 큼 그렇기에 Young보다 GC는 덜 일어난다
  - 여기에서 객체가 사라질때 Major GC(Full GC)라고 한다

- **Old -> Young 참조**

  <img src="https://user-images.githubusercontent.com/41468004/137458747-53c39828-a8c6-418d-a9d2-d53028da05c7.png" style="zoom:50%;" />

  - 두번째 가설에 대한 궁금증
  - 이러한 경우를 처리하기 위해 Old영역에 512바이트의 카드 테이블이 존재
  - Old 영역의 객체가 Young 영역의 객체를 참조할때 이 카드 테이블에 정보를 표시
  - Young 영역의 GC를 실행할때 Old 영역에 있는 모든 객체를 확인해보지 않고 이 테이블만 뒤져서 GC 대상인지 체크



### Young 영역 GC

<img src="https://user-images.githubusercontent.com/41468004/137459014-1adb4c81-5067-48a9-a663-262068452f24.png" style="zoom:50%;" />

#### Young 영역 구조

- Eden
- Survivor 1/0



#### GC 과정

1. 새로 생성된 객체 Eden에 배치
2. Eden 영역에서 GC가 한번 발생한 후 살아남은 애들 Survivor중 하나로 이동
3. 2과정이 반복되다보면 Survivor영역중 하나는 꽉차게 됨
4. 하나의 영역이 가득차면 GC돌고 거기서 살아남은 애들과 Eden에서 살아남은 애들 Survivor 한쪽으로 몰아넣음
5. 이 과정을 반복하다가 계속 살아남은 애는 Old 영역으로 넣음



#### Young에서 메모리 할당 방식

##### bump-the-pointer

- Eden 영역에 할당된 마지막 객체를 추적
- 마지막 객체는 Eden 영역의 맨 위에 위치
- 새로 생성된 객체의 크기가 적당한지 아닌지 판단, 새로운 객체 생성시 마지막에 추가된 객체만 점검하면 되므로 매우 빠른 메모리 할당이 이루어짐
- 하지만 다중 쓰레드 환경에서는 이 Eden영역에 대해 Lock이 걸릴수 있음 => 성능 저하 (TLABs 탄생)



##### TLABs(Thread-Local Allocation Buffers)

- 각각의 스레드가 각각의 몫에 해당하는 Eden 영역의 작은 덩어리를 가지게 하는 것(Thread Local Allocation)



### Old 영역의 GC

> 데이터가 가득차면 GC를 수행 GC방식은 JDK 7을 기준으로 5가지 방식 존재

#### **GC 방식**

- Serial GC
  - 절대 사용하면 안되는 방식
  - CPU 코어나 하나만 있을때 사용하기 위한 방식
- Parallel GC
- Parallel Old GC
- Cocurrent Mark & Sweep GC(CMS)
- G1 GC



#### G1 GC

<img src="https://user-images.githubusercontent.com/41468004/137474088-59bd971a-cf94-4618-8b0a-c2a0c15d7fba.png" style="zoom: 33%;" />

- 지금까지의 Young과 Old 영역을 잊자
- 그림과 같이 바둑판의 각 영역에 객체를 할당하고 GC를 수행
- 즉, Young의 세가지 영역에서 Old 영역으로 이동하는 방식이 사라진 GC방식



[출처 - Naver D2](#https://d2.naver.com/helloworld/1329)



## Java SE, EE

> 둘 다 Java 프로그래밍 언어로 애플리케이션 서버를 프로그래밍하기 위해 폭 넓게 사용되는 플랫폼

### Java SE

> Java Platform, Standard Edition이라고 한다

- 대부분의 사람들이 자바 프로그래밍 하면 떠올리는 플랫폼



### Java EE

> Java Platform, Enterprise Edition이라고 한다

- Java SE 스펙을 기반으로 하며 그 위에 탑재된다
- 대규모, 다계층, 확장성, 신뢰성 네트워크 어플리케이션의 개발과 실행을 위한 API 및 환경을 제공한다

- SE의 API에 JAR파일들을 추가한것



## JDK, JRE

### JDK

> Java를 사용하기 위해 필요한 모든 기능을 갖춘 Java용 SDK(Software Development Kit)

- 프로그램을 생성하고 컴파일 할 수 있음( Java 프로그래밍 및 실행을 하고 싶으면 )
- JRE를 포함하고 있으면 JRE의 상위 집합이다



### JRE

> Java Runtime Envirionment의 약자이다
>
> JVM이 자바 프로그램을 동작시킬 때 필요한 라이브러리들을 가지고 있다

- JVM의 실행환경을 구현했다고 생각하면 된다
- Java 프로그램을 실행시키는데 목적이 있다
- 다만 그냥 실행을 시키더라도 JDK를 다운 받아야하는 경우 존재
  - JSP를 이용하여 웹 어플리케이션을 배포하는 경우 => JSP를 Java 서블릿으로 변환 -> JDK를 이용한 서블릿 컴파일



## Java의 장단점

### 장점

- OS에 독립적이다
  - Java는 JVM이라는 가상머신 위에서 동작하기에 특정 운영체제에 종속되지 않는다
- 객체지향 언어이다
  - 객체지향적으로 코딩하기 위해 여러 언어적 지원을 한다(캡슐화, 상속, 추상화, 다형성 등)
  - 객체지향 패러다임 특성상 비교적 이해하고 배우기 쉽다
- 자동으로 메모리 관리를 해준다
  - JVM에서 Garbage Collector라고 불리는 데몬 쓰레드에 의해 GC(Garbage Collection)가 일어난다
  - 개발자는 메모리를 할당하고 해제하는 것에 신경쓸 필요 없이 비즈니스 로직에만 집중 가능
- 오픈소스
  - 정확히 말하자면 OpenJDK가 오픈소스이다
- 멀티쓰레드 쉽게 구현 가능
  - 스레드 생성 및 제어 관련 API를 제공하고 있기에 운영체제에 상관없이 멀티쓰레드 쉽게 구현 가능
- 동적 로딩을 지원
  - 애플리케이션이 실행될때 모든 객체가 로딩되는 것이 아니라 그때그때 필요한 클래스가 동적 로딩되어 생성된다
  - 또한 유지보수 시 해당 클래스만 수정하면 되기에 재컴파일 필요 없다 => 유지보수가 쉽고 빠르다



### 단점

- 비교적 느리다
  - 자바는 한번의 컴파일로 실행 가능한 기계어가 만들어지는 것이 아님
  - 자바 컴파일러에 의해 변환된 .class 파일들을 JVM이 바로 실행 가능한 기계어가 아닌 바이트 코드
  - 인터프리터 방식을 이용하기에 그때그때 필요한 명령어를 기계어로 변환하여 수행한다. 그렇기에 비교적 느림
    - JIT 컴파일러를 도입하여 JVM의 성능이 꽤 향상됨
- 예외처리가 불편
  - 검사가 필요한 예외가 등장한다면 무조건 프로그래머가 선언해주어야 함



## Java의 접근제어자

| 접근 제어자 | 표시 | 설명                                                   |
| :---------: | :--: | ------------------------------------------------------ |
|   public    |  +   | 어떤 클래스의 객체에서든 접근 가능                     |
|   private   |  -   | 외부에서 접근 불가능, 동일 클래스 내에서만 접근이 가능 |
|  protected  |  #   | 상속받은 클래스 또는 같은 패키지에서만 접근이 가능     |
|   package   |  ~   | 동일 패키지 내에 있는 클래스의 객체들만 접근 가능      |



### Private

<img src="https://user-images.githubusercontent.com/41468004/137480947-b72107a3-a2ae-4eb7-9463-54a8fc010357.png" style="zoom:50%;" />

### Public

<img src="https://user-images.githubusercontent.com/41468004/137481048-edf98477-1ae5-4fd0-8a68-871d4fcee41e.png" style="zoom:50%;" />



### Protected

<img src="https://user-images.githubusercontent.com/41468004/137481140-607c006b-d50c-4c9a-bc40-f5d67dfe3217.png" style="zoom:50%;" />



### Default

<img src="/Users/giri/Library/Application Support/typora-user-images/image-20211015204012869.png" style="zoom:50%;" />

[출처](http://tcpschool.com/java/java_modifier_accessModifier)



## Java의 데이터

### Primitive Type(기본 데이터 타입)

- 기본 타입의 종류는  byte, short, char, int, float, double, boolean이 있다
  - 정수형 : byte, short, int, long
  - 실수형 : float, double
  - 논리형 : boolean
  - 문자형 : char



### Reference Type(참조 데이터 타입)

- 참조 데이터 타입의 종류는 class, array, interface Enumeration이 있다
  - 기본형을 제외하고는 모두 참조형
  - new 키워드를 이용하여 인스턴스를 생성해 그 인스턴스의 주소를 참조하는 형태이다
  - String, StringBuffer, StringBuilder, Collection 등...
  - String과 배열은 참조 타입과 달리 new 없이도 생성 가능하지만 참조형이다
- 참조 데이터는 크기가 가변적이기에 동적으로 관리되는 Heap 영역에 저장
  - Heap 영역은 GC 대상이므로 참조형 데이터들은 GC의 대상이다
- 참조 타입은 값이 지정된 곳의 주소, 즉 객체의 주소를 저장하는 공간이다(Call by Value(주소값))



### Collection

> List, Map, Set 인터페이스를 기준으로 여러 구현체가 존재



##### Why? 왜 쓸까?

- 다수의 Data를 다루는데 표준화된 클래스들을 제공해주기에 Data Structure를 직접 구현하지 않아도 되는 편리함
- 또한 객체의 수를 동적으로 할당 가능
  - 하지만 자료형의 크기가 예측 가능하다면 지정해주는 것이 좋음
  - Collection들은 보통 default 사이즈가 존재하는데 이 사이즈보다 커질 경우 재할당이 일어나기 때문
  - 약간의 성능 저하 이슈



##### List

- List 인터페이스를 `@Override`를 통해 직접 구현할 수도 있으며 `ArrayList, LinkedList` 구현체를 사용하기도 함



##### Map

- 대표적인 구현체로는 `HashMap` 존재
  - 멀티쓰레드 환경에서 `HashTable`과 차이점이 있다
- key-value의 구조로 이루어져 있으며 순서를 보장하지 않는다
- 순서를 보장하기 위해서는 `LinkedHashMap` 사용



##### Set

- 대표적인 구현체로는 `HashSet`존재

- Value에 대한 종복값을 저장하지 않는다
- 순서를 보장하지 않으며 순서를 보장하기 위해서는 `LinkedHashSet` 사용



##### Stack & Queue

- `Stack`은 직접 `new 키워드`로 사용가능
- `Queue`는 `JDK1.5`부터 `LinkedList`에 `new`를 사용해서 사용 가능



### Wrapper Class

> 프로그램에 따라 기본 타입의 데이터를 객체로 취급해야 하는 경우 존재
>
> 이때 기본 타입의 데이터를 Wrapper Class로 변환한 후 작업을 수행해야 한다

#### Java에서 제공하는 Wrapper Class

| 기본 타입 | Wrapper Class |
| :-------: | :-----------: |
|   byte    |     Byte      |
|   short   |     Short     |
|    int    |    Integer    |
|   long    |     Long      |
|   float   |     Float     |
|  double   |    Double     |
|   char    |   Character   |
|  boolean  |    Boolean    |



#### Boxing, Unboxing

> wrapper class는 산술 연산을 위해 정의된 클래스가 아니므로 인스턴스에 저장된 값을 변경할 수 없음
>
> 단지 값을 참조하기 위해 새로운 인스턴스를 생성하고, 생성된 인스턴스의 값을 참조할 수 있다

<img src="https://github.com/WeareSoft/tech-interview/blob/master/contents/images/java_boxing_unboxing.png?raw=true"  />

```java
//Boxing
int i = 10;
Integer num = new Integer(i);

//Unboxing
Integer num = new Integer(10);
int i = num.intValue();
```



#### Auto Boxing & Unboxing

> jdk 1.5 부터는 자바 컴파일러가 박싱과 언박싱이 필요한 상황에 자동으로 처리를 해줌

```java
//Auto Boxing
int i = 10;
Integer num = i;

//Auto Unboxing
Integer num = new Integer(10);
int i = num;
```



#### 성능

편의성을 위해 오토 박싱과 언박싱을 제공하지만 내부적으로 추가적인 연산이 발생함

그러므로 동일한 타입 연산이 이루어지도록 구현하는 것이 중요



#### 래퍼 클래스 비교 연산

- 래퍼 클래스의 비교 연산도 오토언박싱을 통해 가능, but 인스턴스에 저장된 값의 동등 여부 판단은 `==`가 아닌 `equals()`메소드를 사용해야 함
- 래퍼 클래스도 객체이므로 `==`연산자를 사용하게 되면 값의 비교가 아니라 주소값의 비교가 이루어지기 때문



## Call By Value & Call By Reference

#### Call By Value

> 값에 의한 호출

- 함수가 호출될 때, 메모리 공간 안에서는 함수를 위한 별도의 임시공간이 생성됨(종료 시 해당 공간 사라짐)
- 함수 호출 시 전달되는 변수 값을 복사해서 함수 인자로 전달함
- 이때 복사된 인자는 함수 안에서 지역적으로 사용되기에 local value 속성을 가짐
  - `따라서, 함수 안에서 인자 값이 변경되어도, 외부 변수 값은 변경안됨`



#### Call By Reference

>참조에 의한 호출

- 함수 호출 시 인자로 변수의 레퍼런스를 전달함
- 따라서 함수 안에서 인자 값이 변경되면 전달인자로 전달된 객체의 값도 변경됨



#### Java함수 호출 방식

> 자바의 경우는 항상 **Call By Value**로 값을 넘긴다

- C/C++와 같이 변수의 주소값 자체를 가져올 방법이 없음, 이를 넘길 방법도 없음
- reference type(참조 자료형)을 넘길 시에는 해당 객체의 주소값을 복사해서 복사된 주소값을 넘김
- 따라서 **원본 객체의 프로퍼티까지는 접근 가능, 원본 객체를 교체할 수는 없다**

```java
User a = new User("gyoogle");   // 1

foo(a);

public void foo(User b){        // 2
    b = new User("jongnan");    // 3
}
```

- 주소값을 복사하는 것이기에 위 코드를 실행하고나서 a객체의 값을 호출해보면 `gyoogle`이 출력됨



## String / new String

```java
String test1 = "test";
String test2 = "test";
String test3 = new String("test");
String test4 = new String("test");
```

#### 왜 이렇게 다양한 방식이 존재? 우선 두 방식의 차이점을 알아보자

1. `String`으로  선언하는 객체는 JVM 메모리구조에서 `String Constant Pool(SCP)`내에 객체로 생성 후 존재
2. `new String`으로 선언하는 객체는 SCP가 아닌` JVM의 Heap영역` 내의 개별 객체로 만들어짐

- ?? 뭔소리 그냥 두 객체가 서로 다른 공간에 할당된다는 것만 알자!!



#### 그럼 어떻게 다른 공간에 할당되는건지 알아보자!



##### String으로 선언해서 사용하는 방식

<img src="https://user-images.githubusercontent.com/41468004/137495315-684c0e9b-56bf-4a87-8f54-aa59d7a7e2be.png" style="zoom: 55%; " />

- SCP내의 하나의 인스턴스로 생성 된다
- 만약 동일한 리터럴(문자열 값)을 가지는 인스턴스가 여러개 존재한다면 새로운 String 인스턴스를 생성하지 않는다
- 기존에 SCP에 존재하는 인스턴스의 주소값을 참조하게 된다
  - 위에서 test1, test2 변수



##### new String으로 선언해서 사용하는 방식

<img src="https://user-images.githubusercontent.com/41468004/137495901-2c9305c2-b50c-46db-888e-dacaa6292da5.png" style="zoom:55%;" />

- new String으로 선언하는 경우 Heap 영역 어딘가에 새로운 객체로 생성된다
- 리터럴이 같아도 독립적으로 생성됨



이걸 통해 알 수 있듯이 `String`은 `Immutable`하며 문자열을 조작한다고 해서 `인스턴스의 값이 변경되는 것이 아니다`

**새로운 인스턴스가 생성되는 것이다**



#### 따라서 맨위의 코드에서 생성되는 객체는 총 3개이다



## String, StringBuilder, StringBuffer

| 분류   | String    | StringBuffer                  | StringBuilder       |
| ------ | --------- | ----------------------------- | ------------------- |
| 변경   | Immutable | Mutable                       | Mutable             |
| 동기화 |           | Synchronized가능(Thread-safe) | Synchronized 불가능 |

1, String

- 문자열 연산시 새로 객체를 만드는 오버헤드 발생
- 객체가 불변하므로, 멀티쓰레드 환경에서 동기화를 신경쓰지 않아도 된다. (조회 연산에 매우 큰 장점)

- ***문자열 연산이 적고, 조회가 많은 멀티쓰레드 환경에서 좋음***



2. StringBuffer, StringBuilder
   - 공통점
     - new 연산으로 클래스를 하나만 만듬 (Mutable)
     - 문자열 연산시 세로 객체를 만들지 않고 크기를 변경시킨다
     - StringBuffer와 StringBuilder 클래스의 매소드가 동일함
   - 차이점
     - StringBuffer는 Thread-Safe
     - StringBuilder는 Thread-Unsafe
   - StringBuffer
     - 문자열 연산이 많은 Multi-Thread 환경
     - 동기화를 해야하기에 StringBuilder보다는 속도가 느리지만 안정적이다
   - StringBuilder
     - 문자열 연산이 많은 Single-Thread 또는 Thread 신경 안쓰는 환경
     - 동기화가 없기에 StringBuffer 보다는 빠르지만 안정적이지 못하다

## OOP의 4가지 특징

1. Abstraction(추상화)

   - 구체적인 사물들의 공통적인 특징을 파악해서 이를 하나의 개념으로 다루는 것

   

2. Encapsulation(캡슐화)

   - 정보 은닉: 외부에서 접근하지 못하도록 정보를 제한하는 것
   - 높은 응집도, 낮은 결합도를 유지하여 유연함과 유지보수성 증가

   

3. Inheritance(상속)

   - 여러 개체들이 가진 공통된 특성을 부각시켜 하나의 개념이나 법칙으로 성립시키는 과정

   

4. Polymorphism(다형성)

   - 서로 다른 클래스의 객체가 같은 메시지를 받았을때 각자의 방식으로 동작하는 능력
   - 오버라이딩, 오버로딩



## OOP의 5대 원칙

> **SOLID** 원칙

- S: 단일 책임 원칙
  - 객체는 단 하나의 책임만 가져야 함
- O: 개방-폐쇄의 원칙
  - 기존의 코드를 변경하지 않으면서 기능을 추가할 수 있어야함
- L: 리스코프 치환 원칙
  - 자식 클래스는 최소한 부모 클래스에서 가능한 행위는 수행 가능해야함
- I: 인터페이스 분리 원칙
  - 인터페이스를 클라이언트에 특화되도록 분리시키라는 원칙
- D: 의존 역전 원칙
  - 의존 관계를 맺을 때 거의 변화하기 어려운 것, 변화가 없는 것에 의존하라는 것



## 객체지향 프로그래밍 vs 절차지향 프로그래밍

- 절차지향 프로그래밍
  - 실행하고자 하는 절차를 정해서 그 절차대로 프로그래밍
  - 목적을 달성하기 위한 흐름에 중점을 둠
- 객체지향 프로그래밍
  - 실세상의 물체를 객체로 표현하고, 이들 사이의 관계, 상호 작용을 프로그램으로 나타낸다
  - 객체를 추출하고 객체들의 관계를 결정, 이들의 상호 작용에 필요한 함수와 변수를 설계 및 구현한다
  - 객체 지향의 핵심은 연관되어 있는 변수와 메서드를 하나의 그룹으로 묶는 것이다
  - 사람의 사고와 가장 비슷하게 프로그래밍 하기 위해 생성된 기법



## 함수형 프로그래밍

- Side Effect가 없는 순수 함수를 1급 객체로 간주해 참조 투명성을 지킬 수 있다
- 순수 함수란 함수의 실행이 외부에 영향을 끼치지 않는 것을 읨;
- 1급 객체는 파라미터, 반환값으로 사용 등 할 수 있는 것을 의미
- 참조 투명성이란 동일한 인자에 대해 항상 동일한 결과를 반환한다는 것
- 즉, 함수를 실행해도 어떠한 상태 변화 없이 동일한 실행 결과를 예측할 수 있게끔 하는 것이 함수형 프로그래밍이다



## 객체지향

> 객체
>
> - 현실세계의 실체 및 개념을 반영하여 상태와 행위를 정의한 데이터의 집합
>
> 객체지향 프로그래밍
>
> - 각자의 역할을 지닌 객체들끼리 서로 메시지를 주고 받으며 동작할 수 있도록 프로그래밍 하는 것



#### 장점

- 사람의 관점에서 프로그래밍 하고 이해하기 쉽다
- 강한 응집력, 약한 결합력을 가진다
- 재사용성, 확장성이 높다



#### 단점

- 객체 간의 정보 교환이 모두 메시지 교환을 통해 이루어지기에 시스템이 많은 오버헤드 발생

  - 처리속도가 상대적으로 느리다
  - 하드웨어의 발전으로 이 단점 어느정도 커버

- 객체가 상태(변수)를 갖기에 객체가 예측할 수 없는 상태값을 가져 버그를 일으킬 수 있다

  - 이는 함수형 프로그래밍 등장의 패러다임이다

    > 함수형 프로그래밍
    >
    > - 모든 것을 순수 함수로 나누는 기법, 문제를 작은 문제들로 쪼개고 작은 문제를 해결하기 위한 함수를 작성



#### 특징

- 추상화
  - 객체의 공통된 속성이나 기능을 추출하는 것
  - 중요하지 않은 것은 감추거나 무시, 중요한 것만 추출
- 캡슐화
  - 내부의 데이터나 함수를 외부헤서 참조하지 못하도록 차단
  - 정보 은닉화
- 다형성
  - 같은 코드라고 하더라도 상황에 따라 다르게 동작하는 성질
  - Overriding
    - 임의의 클래스가 어떤 클래스를 상속 받거나 인터페이스를 구현했을 시 상위 클래스, 인터페이스에 정의되어 있는 함수를 재정의 하는 것
    - 인터페이스의 경우는 오버라이딩이 강제
  - Overloading
    - 메소드명, 리턴값 동일, but 주어진 인자에 따라 다르게 동작
- 상속
  - 부모의 형질을 이어받는다는 뜻
  - 부모의 속성과 메소드를 그대로 사용할 수 있으며 거기에 속성/메소드를 추가할 수 있다
  - 상속시 오버라이딩을 통해 재정의하여 다르게 동작하게끔 가능
- 클래스
  - 객체를 만들기 위해 상태(field)와 행위(method)를 정의한 틀
- 메시지
  - 객체지향적으로 구현된 프로그램은 객체끼리 메시지를 주고받으며 상호작용함
  - 즉, 임의의 객체에게 전달인자를 주어 메소드 호출 그리고 리턴값을 받아 처리



## Java의 Static 멤버와 Non-Static 멤버의 차이

#### Non-Static 멤버

- 공간적 특성: 멤버가 객체마다 별도로 존재
  - `인스턴스 멤버`라고 부른다
- 시간적 특성: 인스턴스의 생명 주기를 따라간다
- 공유의 특성: 공유되지 않는다
  - 인스턴스 멤버는 인스턴스 각각의 독립적인 공간을 유지



#### Static 멤버

- 공간적 특성: 멤버가 클래스당 존재
  - 멤버는 객체 내부가 아닌 별도의 공간에 생성
  - `클래스 멤버`라고 부른다
- 시간적 특성: 클래스 로딩시에 생성
  - 프로그램이 종료되면 사라진다
  - 인스턴스가 사라져도 멤버가 사라지지 않음
  - 인스턴스를 만들지 않아도 사용 가능
  - 프로그램이 종료될 때 사라진다
- 공유의 특성: 동일한 클래스 내의 모든 인스턴스들이 공유한다



## Java의 main함수가 static인 이유

- `static`키워드
  - `static`멤버는 클래스 로딩(프로그램 시작)시 메모리에 로드되어 인스턴스를 생성하지 않아도 사용 가능
- main 메서드가 `static`인 이유
  - JVM은 인스턴스가 없는 클래스의 `main()`함수를 호출해야하기에 `static`이어야 한다
- `JVM`과 `static`
  - 코드를 실행하면 컴파일러가 `.java` 코드를 `.class(바이트 코드)`로 변환
  - 클래스 로더가 `.class` 파일을 JVM의 `메모리 영역(Runtime Data Area)`에 올린다
  - `Runtime Data Area` 중` Method Area(= Class Area, Static Area)`라고 불리는 영역에 `Class Variable`이 저장되는데 여기에 `static 변수`도 저장
  - JVM은 `Method Area`에 로드된 `main()`을 실행



## Java의 final 키워드

- final

  > 변수나 메서드 또는 클래스가 `변경 불가능` 하도록 만든다

  - 원시(Primitive) 변수에 적용 시
    - 해당 변수의 값은 변경 불가
  - 참조(Reference) 변수에 적용 시
    - 해당 참조 변수가 힙 내의 다른 객체를 가리키도록 변경 불가능
  - 메서드에 적용
    - 해당 메서드를 오버라이드 불가
  - 클래스에 적용
    - 해당 클래스의 하위 클래스 정의 불가

- finally

  > try/catch 블록이 종료될 때 항상 실행될 코드 블록을 정의하기 위해 사용

  - finally 블록은 예외가 발생하더라도 항상 실행
    - 단 JVM이 try 수행중에 종료되는 경우는 제외

- finalize() 메소드

  > GC가 더상의 참조가 되지 않는 객체들을 메모리에서 삭제하겠다고 결정하는 순간 호출되는 메소드

  - Object 클래스에 정의되어 있으며 오버라이드 해서 맞춤별 GC 정의 가능
    - `protected void finalize() throws Throwable { //파일 닫기, 소켓 닫기, 자원 반환 등 }`



## Java의 Serialization, Deserialization

#### 직렬화(Serialization)

> - 자바 시스템 내부에서 사용되는 객체, 데이터를 외부의 자바 시스템에서도 사용 가능하게 바이트 형태로 변환하는 기술
> - 즉, JVM의 Heap 영역에 상주되어 있는 객체 데이터를 바이트 형태로 변환하는 기술



- 직렬화 조건
  - 자바에서는 간단하게 `java.io.Serializable`인터페이스 구현으로 직렬화/역직렬화 가능
- 직렬화 대상
  - Serializable 인터페이스 상속받은 객체
  - Primitive 타입의 데이터
  - Primitive 타입이 아닌 Reference 타입의 객체들은 Serializable 인터페이스를 구현해야 한다

- 직렬화 방법

  - serialVersionUID를 만들어준다

  ```java
  @Entity
  @AllArgsConstructor
  @toString
  public class Post implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String title;
    private String content;
  }
  ```

  

  - `ObjectOutputStream`으로 직렬화 진행.

  ```java
  Post post = new Post("title", "content");
  byte[] serializedPost;
  
  //아래와 같은 try문은 소괄호 안의 자원을 try문 종료시에 해제시키겠다는 것
  try (ByteArrayOutputStream baos = new ByteArrayOnputStream(serializedPost)) {
    try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
      oos.writeObject(post);
      
      serializedPost = baos.toByteArray();
    }
  }
  ```



#### 역직렬화(Deserialization)

> - 바이트로 변환된 데이터를 다시 객체로 변환
> - 직렬화된 바이트 데이터를 객체로 변환해 JVM의 Heap에 상주시키는 것



- 역직렬화 조건

  - 직렬화 된 객체의 클래스가 클래스 패스에 존재해야하며 import되어 있어야 한다
  - 동일한 serialVersionUID를 가지고 있어야 함
    - `private static final long serialVersionUID = 1L;`

- 역직렬화 방법

  - `java.io.ObjectInputStream`객체를 이용

  ```java
  try (ByteArrayInputStream bais = new ByteArrayInputStream(serializedPost)) {
    try (ObjectInputStream ois = new ObjectInputStream(bais)) {
  
      Object objectPost = ois.readObject();
      Post post = (Post) objectPost;
    }
  }
  ```

  

#### 직렬화 serialVersionUID

위의 코드에서 `serialVersionUID`를 직접 지정해줬었다. 사실 지정하지 않아도 알아서 해시값이 할당 됨

직접 설정 이유는 기존의 클래스 멤버 변수가 변경되면 `serialVersionUID`가 달라지는데, 역직렬화 시 오류 발생



#### 요약

- 데이터를 통신 상에서 전송 및 저장하기 위해 사용되는 기술
- 개발자가 직접 컨트롤할 수 없는 클래스는 직렬화 사용 지양
- 직렬화 데이터는 타입,클래스 메타정보를 포함하고 있기에 사이즈가 큼
  - 트래픽에 따라 비용 증가할 수 있으므로 JSON을 사용하는 것이 좋다



## 클래스, 객체, 인스턴스

### 클래스

- 객체를 만들어 내기 위한 틀
- 연관되어 있는 변수와 메서드의 집합



### 객체

- 소프트웨어 세계에 구현할 대상
- 클래스에 선언된 모양 그대로 생성된 실체
- `클래스의 인스턴스`라고도 한다
- OOP관점에서 클래스 타입으로 선언되었을 때 객체라고 한다



### 인스턴스

- 설계도를 바탕으로 소프트웨어 세계에 구현화된 실체
  - 객체를 소프트웨어에 실체화 하면 인스턴스 라고 부른다
  - 즉, 실체화된 인스턴스는 메모리에 할당된다.
- 인스턴스는 객체에 포함된다

- OOP관점에서 객체가 메모리에 할당되어 실제 사용될(인스턴스화) 때 인스턴스라고 한다



```java
/* Class */
public class Animal {
	...
}

public class Main {
  public static void main(String[] args) {
    Animal cat, dog; //객체
    
    //인스턴스화
    cat = new Animal(); //cat은 Animal 클래스의 인스턴스
    dog = new Animal(); //dog은 Animal 클래스의 인스턴스
  }
}
```

- 클래스 vs 객체
  - 클래스는 설계도, 객체는 설계도로 구현할 대상
- 객체 vs 인스턴스
  - 크래스의 타입으로 단지 선언만 되었을 때 객체라고 하고, 그 객체가 메모리에 할당되었을 때가 인스턴스
  - 객체는 현실세계에 가깝고, 인스턴스는 소프트웨어 세계에 가깝다
- 추상화 기법
  - 분류
    - 객체 -> 클래스
    - 실존하는 객체들을 공통적인 속성을 공유하는 하나의 범주 또는 추상적인 개념으로 묶는 행위
  - 인스턴스화
    - 객체 -> 인스턴스
    - 분류의 반대, 범주나 개념으로부터 실존하는 객체를 만드는 과정



## 오버로딩과 오버라이딩

- 오버로딩

  - 두 메서드가 같은 이름을 갖고 있으나 인자의 수나 자료형이 다른 경우
  - 리턴값과 함수명 동일, 인자가 다름

- 오버라이딩

  - 상위 클래스의 메서드와 이름과 용례가 같은 함수를 하위 클래스에 재정의하는 것

  - 상속 관계에 있는 클래스 간에 같은 이름의 메서드를 정의

    ```java
    public abstract class Shape {
        public void printMe() { System.out.println("Shape"); }
        public abstract double computeArea();
    }
    public class Circle extends Shape {
        private double rad = 5;
        @Override // 개발자의 실수를 방지하기 위해 @Override(annotation) 쓰는 것을 권장
        public void printMe() { System.out.println("Circle"); }
        public double computeArea() { return rad * rad * 3.15; }
    }
    public class Ambiguous extends Shape {
        private double area = 10;
        public double computeArea() { return area; }
    }
    
    ```



## 인터페이스와 추상클래스

### 추상 메서드

- abstract 키워드와 함께 원형만 선언되고, 코드는 작성되지 않은 메서드

  ```java
  public abstract String getName(); //추상 메서드
  public abstract String fail() { return "Fail" } //추상 메서드 아님
  ```

  

### 추상 클래스

#### 개념

- abstarct 키워드로 선언된 클래스

- 추상 메서드를 최송 하나 이상 가지고 abstract로 선언된 클래스
- 추상 메서드가 없어도 abstract로 선언한 클래스

#### 추상 클래스의 구현

- 서브 클래스에서 슈퍼 클래스의 모든 추상 메서드를 오버라이딩하여 실행 가능한 코드로 구현

#### 추상 클래스의 목적

- 객체를 생성하기 위한 목적이 아님, 상속을 위한 부모 클래스로 활용하기 위함
- 여러 클래스들의 공통된 부분을 추상화하여 상속받는 클래스에서 구현을 강제하기 위함



### 인터페이스

#### 개념

- 추상 메서드와 상수만을 포함, interface 키워드를 사용하여 선언한다 

#### 인터페이스의 구현

- 인터페이스를 상속받고(implements), 추상 메서드를 모두 구현한 클래스를 작성한다

#### 인터페이스의 목적

- 상속받을 서브 클래스에게 구현할 메서드들의 원형을 모두 알려주어, 클래스가 자신의 목적에 맞게 메서드를 구현하도록 하는 것이다.
- 구현 객체들의 같은 동작을 보장하기 위한 목적



### 추상 클래스 vs 인터페이스

#### 공통점

- 그 자체만으로 인스턴스 생성 불가능
- 선언만 있고 구현은 없다
- 자식 클래스가 상속받아서 사용하며 구체적은 내용을 구현하는 것을 강제한다

#### 차이점

- 서로 다른 목적을 가지고 있다
  - 추상 클래스는 상속을 위한 부모 클래스
  - 인터페이스는 구현 객체의 같은 동작을 보장

- 추상 클래스는 클래스, 인터페이스는 클래스가 아님
- 추상 클래스는 단일 상속이지만 인터페이스는 다중 상속이 가능





## Reflection

>  자바에서 이미 로딩이 완료된 클래스에서 또 다른 클래스를 동적으로 로딩하여 생성자, 멤버, 메서드 등을 사용할 수 있는 기법

- 사용 방법
  - Class.forName("name");

- 사용 이유?
  - 실행 시간에 다른 클래스를 동적으로 로딩해 접근할 때
  - 클래스와 멤버 필드 그리고 메서드 등에 대한 정보를 얻어야할 때



## Stream

> Java8에서 추가된 API이며 컬렉션 타입의 데이터를 Stream 메소드에서 내부 반복을 통해 정렬, 필터링 가능

#### 특징

- 병렬 처리에 유리
  - 병렬 처리를 위해 `common fork join pool`을 사용하는 `parallel()`메소드 제공
  - `common fork join pool`
    - 각 스레드가 개별 큐를 가지고 있으며, 놀고 있는 스레드가 일하고 있는 스레드의 작업을 가져와 수행함
  - 코어의 수와 데이터의 수가 많고 데이터당 처리 시간이 길수록 병렬 처리가 빛을 발한다
  - 배열, ArrayList 사용 시 유리
- Immutable
  - 원본 데이터 변경이 불가함
- lazy
  - 스트림에는 중간 연산과 최종 연산이 있는데 중간 연산을 여러개 작성 시 모두 합쳐서 진행하고, 합쳐진 연산을 최종 연산에서 한 번에 처리



#### 장점

- 내부 반복을 사용하기에 불필요한 코딩 줄이고 가독성 향상 + 최적화된 병렬 처리
  - Collection은 외부 반복을 사용, 병렬처리 하기 위해서는 `synchronized`사용해야함

  <img src="https://user-images.githubusercontent.com/41468004/137576340-6c9d605d-2d6d-4b9c-b350-8fd0b5ff13a9.png" style="zoom: 50%;" />



## HashMap vs HashTable

#### 공통점

- 유니크한 Key-Value의 쌍을 저장하는 자바 Collection 객체

#### 차이점

- HashTable의 경우 내부 메서드에 `synchronized`키워드를 붙여서 명시적으로 동기화 수행하지만 HashMap의 경우 그렇지 않다

  - 따라서 멀티 쓰레드 환경에서 HashTable은 Thread Safe를 보장하지만 상대적으로 느리다
  - HashMap은 Thread Safe하지 않지만 상대적으로 빠르다

- HashTable에서는 객체 저장시에 hashCode를 구현한 객체만 저장 가능하기에 null 저장 불가능, HashMap의 경우에는 null에 대한 예외 허용

- HashMap은 보조 해시함수와 Seperate Chaining 방식을 사용하기에 HashTable보다 충돌이 덜 일어난다.

  



## HashSet vs TreeSet

#### HashSet

- Set 인터페이스의 구현체
- 객체들을 순서 없이 저장하고 동일한 객체는 중복 저장 안함
- 커스텀 객체의 중복 여부 확인
  - 커스텀 객체 저장 시 객체의 `hashCode()`메소드를 호출 해 해시코드를 얻어낸 뒤
  - 동일한 hashCode를 가지는 두 객체가 있다면 `equals()`메소드로 두 객체를 비교해 true가 나오면 동일 객체라고 판단

#### TreeSet

- Set 컬렉션의 검색 기능을 강화한 컬렉션
- 이진 트리 구조를 사용하여 tree 구조를 가진다



#### HashSet vs TreeSet

- HashSet
  - 해시테이블을 이용하여 구현하기에 그 요소들이 정렬되지 않음 
  - add, remove ,contains 메소드들 O(1) 시간복잡도
- TreeSet
  - 트리 형태이며 각각의 요소들을 자동으로 정렬
  - add, remove, contains 메소드들 O(log N) 시간복잡도



#### LinkedHashMap

> HashMap을 확장한 클래스, HashMap과 다르게 입려된 자료들의 순서를 기억한다

## Java 버전 특징

### Java 8

### Optional

`Java 8부터 지원하는 클래스`

#### NPE(NullPointerException)

- 개발할 때 가장 많이 발생하는 예외 중 하나
- 이를 회피하기 위해 null 검사 로직 추가 필요

#### Optional?

- NPE를 방지할 수 있도록 도와주는 클래스
- Optional<T>는 null이 올 수 있는 값을 감싸주는 Wrapper 클래스
- 아래와 같은 value에 값을 저장하기에 null이더라도 바로 NPE가 발생하지 않음
- 또한 클래스이기에 각종 메소드를 제공해준다

```java
public final class Optional<T> {
  //If non-null,the value
  private final T value;
}
```

#### Optional 활용

##### 생성

- Wrapper 클래스이기에 빈 값이 올 수도 있음

- 아래와 같이 생성

  ```java
  Optional<String> optional = Optional.empty();
  
  System.out.println(optional.isPresent()); //false
  ```



- Null이 올 수도 있을 데이터를 감싸서 생성 가능

- 그리고 orElse 또는 orElseGet 메소드를 이용해 값이 없는 경우라도 안전하게 값을 가져올 수 있다

  ```java
  Optional<String> optional = Optional.ofNullable(getName());
  String name = optional.orElse("anonymous"); //값이 없다면 anonymous 리턴
  ```

##### 사용

- null인 경우 새로운 객체를 생성해줘야하는 경우에는 복잡했었는데 람다와  Optional<T>를 통해 간소화

  ```java
  //Java 8 이전
  List<String> names = getNames();
  List<String> tmpNames = list != null ? list : new ArrayList<>();
  
  //이후
  List<String> names = Optional.ofNullable(getNames()).orElseGet(() -> new ArrayList<>());
  ```

#### Optional 정리

- null 또는 실제 값을 value로 갖는 Wrapper로 감싸 NPE로부터 자유로워지기 위한 Wrapper Class
- 하지만 값을 Wrapper하고 다시 풀고, null인 경우에는 대체 함수를 호출하는 오버헤드가 있으므로 성능 저하 가능
- 따라서 메소드의 반환 값이 절대 null이 아니라면 사용하지 말자

### Optional의 orElse와 orElseGet의 차이





#### lambda expression(람다 표현식)

`메소드를 하나의 식으로 표현하는 방식`

특징

- 메소드의 이름이 필요없다 => 익명 함수라고도 불림
- 람다식 내에서 사용되는 지역변수는 `final`이 붙지 않아도 상수로 간주
- 람다식으로 선언된 변수명은 다른 변수명과 중복 불가능



장점

- 코드를 간결하게 만들어 가독성 좋아짐
- 함수식에 개발자의 의도가 명확히 드러남
- 함수를 만드는 과정 없이 한번에 처리할 수 있어 생산성 향상



단점

- 람다를 사용하면서 만든 무명함수는 재사용 불가
- 디버깅 어려움
- 재귀로 만들경우 부적합





#### stream api

#### 
