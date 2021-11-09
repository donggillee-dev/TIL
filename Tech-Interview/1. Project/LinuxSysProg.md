# Linux System Programming

- [Linux](#linux)
- [make](#make)

## Linux

운영체제 중 하나

다중 사용자, 다중 작업을 지원하는 네트워크 운영체제

Kernel, Shell, Application으로 이루어져있다

### Kernel

- 프로그램 실행 과정에서 가장 핵심적인 연산을 이루는 코어
- 사용자가 사용하는 Application과 HW 사이에서 관리자 역할을 수행한다

### Shell

- 사용자가 입력하는 명령을 대신 해석해 Kernel에게 전달해주며 커널의 결과를 사용자에게 보여주는 역할을 하는 프로그램

### Application

- 개발자, 사용자에게 편집기, 네트워크 관련 도구 등 여러 응용프로그램 제공

## Make

- MakeFile에 기술된 의존성 정보를 기반으로 파일을 빌드해줌
- 아래의 그림에서 보이듯이
- app이라는 결과물을 만들기 위해 main. foo, bar가 필요함
- 각각을 목적 파일로 생성해서 그 목적 파일들을 가지고 최종 결과물을 생성
  - 이 일련의 과정을 MakeFile에 명시해주고 make는 이를 해독해서 의존성대로 파일 결과물을 생성해냄
  - .o파일이란 소스 프로그램을 컴파일 할 때 생기는 목적 코드 파일임

![image](https://user-images.githubusercontent.com/41468004/141020347-cdbe5fa4-b1ce-44b5-9436-fdaf8fb4ee7f.png)
