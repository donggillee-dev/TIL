# Gradle



## Gradle이란?

### 그루비(Groovy)를 기반으로 한 빌드 자동화, 개발 자원에 중점을 둔 빌드 도구

- 그래들(Gradle)은 그루비(Groovy)를 기반으로 한 빌드 도구
- Ant, Maven과 같은 이전 세대 빌드 도구의 단점을 보완하고 장점을 취합해 만든 오픈소스 기반 빌드 도구
- 여기서 잠깐 Groovy?
  - JVM에서 실행되는 스크립트 언어, Java와 비슷한 문법
- Gradle이 어떤 단점들을 보완했고 어떤 장점들을 합쳐 만든건지 알기 위해 Ant, Maven에 대해 잠깐 알아보자



## Gradle의 특징

- Ant&Maven이 가진 장점을 모아 만들었으며 XML이 아닌 JVM에서 동작하는 스크립트 언어 Groovy 기반 DSL(Domain Specific Language)을 사용

- Gradle이 설치되어 있지 않아도 Gradle Wrapper를 통해 사용할 수 있음

- Gradle Wrapper? => 아래에서 따로
  

  ### High performance

  - Gradle은 실행해야만 하는 task를 실행하고 불필요한 동작 안함
  - build cache를 이용하므로 이전 실행의 task output 재사용 가능
  - 심지어 다른 기계에서도 build cache를 공유해 성능 향상 가능
  
  ### JVM foundation
  
  - Gradled은 JVM에서 실행되며 이를 위해서는 JDK설치 필요.
  - 그러므로 Java Standard API를 빌드 로직에 사용 가능
  - JVM 기반이기에 다양한 플랫폼에서 실행 가능
  
  ### Conventions

  - Maven으로부터 의존 라이브러리 관리 기능 차용
  - Java 프로젝트와 같은 일반적인 유형의 프로젝트 쉽게 빌드 가능
  
  ### Extensibility
  
  - Gradle을 확장하면 고유의 task 타입을 제공 or 모델 빌드 가능
  
  ### IDE, Build Scan support
  
  - 다양한 IDE에서 Gradle 임포트해서 사용 가능
  - 빌드 모니터링 가능한 Build Scan 지원



## Gradle 기본 구조

- 모든 Gradle Script는 하나 이상의 Project로 구성되며 모든 프로젝트는 하나 이상의 Task로 구성된다
  - Project : 소스를 jar로 모으거나, 자바 프로젝트를 컴파일, 테스트 실행, 어플리케이션을 배포하는 등의 업무
  - Task : 작업의 최소단위, Task간 의존관계 설정 가능, 동적인 테스크 생성도 가능

- ![Gradle 기본 구조](https://user-images.githubusercontent.com/41468004/125332569-28862400-e384-11eb-9c0f-1d57053c068d.png)

- gradlew
  - 리눅스 또는 MacOS용 실행 쉘 스크립트 파일
- gradlew.bat
  - 윈도우용 실행 배칠 스크립트 파일
- gradelw-wrapper.jar
  - JAR 형식으로 압축된 Wrapper파일.
  - 프로젝트 안에 자동으로 설치되는 파일이다
  - gradlew나 gradlew.bat파일이 이 파일을 사용하여 Gradle task를 수행
- build.gradle
  - 프로젝트의 라이브러리 의존성, 플러그인, 라이브러리 저장소 등을 설정할 수 있는 빌드 스크립트 파일
  - gradle 생명주기의 2~3단게에서 사용된다
- settings.gradle
  - 프로젝트의 구성 정보 파일이다
  - gradle 생명주기의 1단계에서 사용되는 파일
  - 멀티 프로젝트, 싱글 프로젝트를 구분 및 설정 가능

## Gradle Build Lifecycle

### Build는 3단계로 되어있다

1. 초기화(Initialization)

   1. 빌드 대상 프로젝트를 결정, 각각에 대한 Project 생성. 
   2. settings.gradle 파일에서 프로젝트 구성 (멀티프로젝트, 싱글프로젝트 구분)

2. 구성(Configuration)

   1. 빌드 대상이 되는 모든 프로젝트의 빌드 스크립트 실행 (프로젝트 객체 구성)
   2. configured Task실행

3. 실행(Excution)

   1. 구성 단계에서 생성하고 설정된 프로젝트의 Task 중에 실행 대상 결정

   2. gradle 명령행에서 지정한 Task 이름 인자와 현재 디렉토리를 기반으로 Task 결정해서 선택된 Task들을 실행

      

## 라이브러리 의존성 관리

- 의존성은 종종 모듈로 제공, 이 모듈들을 저장하고 있는 공간이 **<u>Repository</u>** 이다.
- repository는 `로컬 저장소`가 될 수도 있고 `원격 저장소`가 될 수도 있다
- Gradle에게 어디서 의존성 모듈을 가져올지 명시해줘야하는데, `repository 선언`을 통해 할 수 있다
- Gradle은 특정 task를 수행하기 위해 필요한 의존성들을 런타임시에 원격으로부터 다운받거나 로컬에서 가져온다. 보통 `멀티 프로젝트`의 경우에는 저장소 말고 해당 프로젝트를 가져온다
  - => 이 과정을 **<u>dependency resolution</u>** 이라고 한다
- Gradle은 향후에 불필요한 네트워크 호출을 하지 않기 위해 의존성 파일들을 **<u>dependency cache</u>** 라는 로컬 캐시에 저장

### 저장소 설정

- Gradle은 Maven repository, JCenter repository, Ivy rirectory등을 지원하며 이와 같이 다양한 종류의 repository가 존재한다

- repository를 형식과 연결 방법으로 구분지을 수 있다

  - Format에 따른 구분

    - Maven 기반 저장소

      - 가장 일반적으로 Maven Central, JCenter, Google Andriod 등

    - Ivy 기반 저장소

    - 로컬 디렉토리 형식 저장소

      ![Gradle Repository notaion](https://user-images.githubusercontent.com/41468004/125337713-48204b00-e38a-11eb-8757-a481b33c5b40.png)

    

  - Connectivity에 따른 구분

    - 인증 체계가 구성된 저장소

      - BasicAuthentication, DigestAuthentication, HttpHeaderAuthentication 등의 인증 체계가 존재

    - HTTPS, SFTP, AWS S3, Google Cloud Storage 원격 프로토콜로 연결 가능한 저장소

      

  - 문법

  ```groovy
  repositories { //각종 의존성들을 어떤 원격 저장소에서 받을지 설정
    mavenLocal() //maven 로컬 캐시 저장소
    mavenCentral() //maven 중앙 저장소, 기본적으로 많이 사용, but library 업로드 난이도 때문에 아래 많이 사용 추세
    jcenter() //업로드 간단하게, mavenCentral에도 업로드 할 수 있는 자동화 기능 탑재
    maven { url "http://repo.company.com/maven"} //maven 원격 저장소
  }
  ```

  

## 사용법 및 문법

### plugin 설정

- plugin을 이용한 빌드파일 설정 gradle plugin

- ```groovy
  plugins {
      id 'java'
      id 'idea'
      id 'org.springframework.boot' version "${springBootVer}"
  }
  ```



### 의존관계 설정

- Gradle은 java의 의존성 관리를 위해 다양한 구성을 제공
- 의존관계는 group, name, version 순으로 의존성 기술
- implementation => ? compile이랑은 무슨차이
  - 프로젝트 컴파일 과정에서 필요한 라이브러리
- providedCompile
  - compile시에는 필요하지만, 배포시에는 제외될 dependency를 지정
  - war plugin이 설정된 경우에만 사용 가능
- providedRuntime
  - runtime시에만 필요하고, 실행환경에서 제공되는 dependency를 설정
  - war plugin이 설정된 경우에만 사용 가능
- testImplementation
  - test시에 필요한 dependency 관리
- annotationProcessor
  - 컴파일 시에 사용되는 어노테이션 프로세서
  - 어노테이션에 대한 코드베이스 검사, 수정, 생성하는 역할
  - 실행 순서
    - 어노테이션 프로세서를 사용한다는 걸 인지하고 컴파일러는 컴파일 수행
    - 각 어노테이션 프로세서들이 역할에 맞게 구현된 상태에서 각 어노테이션 프로세스를 수행
    - 어노테이션 프로세서들 내부에서 어노테이션에 대한 처리
    - 모든 어노테이션 프로세서들이 수행될때까지 자바 컴파일러는 이를 반복

```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.webjars:bootstrap:4.5.0'
    implementation 'org.webjars:jquery:3.5.1'
    compileOnly 'org.projectlombok:lombok'
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    runtimeOnly 'mysql:mysql-connector-java'
    annotationProcessor 'org.projectlombok:lombok'
    testImplementation('org.springframework.boot:spring-boot-starter-test') {
        exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
    }
}
```



## Gradle Wrapper

- Gradle을 개발자나 CI서버에 깔지 않고 프로젝트에 함께 포함시켜서 배포할 수 있는 방법

- 결과적으로는 수동 설치 없이도 Gradle 프로젝트를 빠르게 시작 가능

- 환경에 종속적이지 않음

- gradlew

  - ```bash
    # 그냥 실행
    gradle wrapper
    
    # 옵션을 사용하는 경우
    gradle wrapper --gradle-version 6.0.1 --distribution-type all
    ```

  - ```bash
    #컴퓨터에 설치된 gradle과 java를 기준으로 build
    gradle build
    
    #build.gradle파일에 정의된 내용 기준으로 build
    ./gradlew build
    ```

