# Java

- [컴파일 과정](#컴파일-과정)

- [JVM](#jvm)

- [Java Garbage Collection](#java-garbage-collection)
- [JRE, JDK](#jre,-jdk)

- [JAR, WAR](#jar,-war)

- [Java SE, EE](#java-se,-ee)





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



## JVM

> 시스템 메모리를 관리하며, 자바 기반 애플리케이션을 위해 이식 가능한 독립적인 실행 환경 제공

#### 목적

`어떤 기기상에서 실행되고 있는 프로세스, 특히 자바 앱에 대한 리소스를 대표하고 통제하는 서버`

- 자바 프로그램이 어느 기기나 운영체제 상에서도 실행될 수 있도록 하는 것
- 프로그램 메모리를 관리하고 최적화하는 것



#### JVM 구성

- Class Loader(클래스 로더)

  > JVM내로 클래스 파일(.class)들을 로드하고, 링크를 통해 배치하는 작업을 수행

  - 자바는 동적코드이기에 컴파일 타임이 아니라 런타임에(클래스를 최초로 참조할때) 클래스로더가 해당 클래스를 로드하고 링크 한다.

- Excution Engine(실행 엔진)

  > 클래스 로더에 의해 JVM내에 링크 + 로드된 클래스(바이트 코드)를 실행시킨다
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

- Runtime Data Area

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



### G1 GC



[출처 - Naver D2](#https://d2.naver.com/helloworld/1329)

