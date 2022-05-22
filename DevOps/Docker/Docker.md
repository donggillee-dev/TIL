## Docker

> 컨테이너 기반의 오픈소스 가상화 플랫폼

- 어떤 문제점들 때문에 이것이 활성화? => 서버 관리의 복잡성, 중요성
- 서버의 인프라는 급변한다
- 서버 관리 방식은 Docker 있기 전/후로 바뀐다

## Docker vs VM(Virtual Machine)

<img src="https://user-images.githubusercontent.com/41468004/169692071-ed18c2ec-4b05-434c-9be4-6749ccddeb65.png" style="zoom:50%;" />

1. #### 기존의 가상화

   - 가상화 이전에는 하나의 서버에 하나의 Ap
   - 남아도는 자원들을 활용하기 위해 가상화 기술 도입
   - 그 중 하이퍼바이저(Hypervisor) 기술이 가장 많이 사용
     - 논리적 분할된 공간에 가상환경 생성하여 게스트 OS를 구동하는 것

2. #### 도커와 VM의 차이

   - 게스트 OS의 유무
   - 이는 자원의 효율설 측면에서 커다란 차이
   - VM의 경우에는 하나씩 늘 때마다 새로운 OS를 위한 자원 할당 필요
   - Docker의 경우에는 독립되어 있음

3. #### 도커의 동작방식

   - 도커의 컨테이너는 호스트 OS의 커널을 공유한다
     - docker로 어떠한 MySQL를 실행시키고 host os에서 `ps -ef | grep MYSQL`를 실행해보면 프로세스 발견 가능

4. #### 하나의 커널, 독립된 공간

   - 그렇다면 Host OS를 공유하는데 진정 독립된 것일까?
   - 도커 컨테이너들은 리눅스의 커널을 공유하는것
   - 그렇기에 사용자가 MacOS/Window를 사용하더라도 그 위에 Linux가 깔리고 그 위에서 컨테이너들이 동작
     - 즉, Docker의 Guest OS가 Linux가 되는 것
     - 그리고 각 컨테이너에는 OS가 설치되지 않음

## 도커의 특징

### 이미지(Image)

- 이미지를 통해서 컨테이너를 생성하기에 이미지를 만드는 과정이 필요
- Dockerfile을 이용하여 이미지를 만들고 처음부터 재현 가능
- 빌드 서버에서 이미지를 만들어 해당 이미지를 이미지 저장소에 저장하고 운영서버에서 이미지 불러와서 컨테이너 생성하면 그대로 복원 가능
- 현재 빌드, 구축 환경에 대한 스냅샷이라고 생각하면 될듯

### 설정관리

- 환경변수로 설정 관리
- 환경변수를 가지고 도커 생성

### 자원관리

- 컨테이너는 삭제 후 새로 만들면 모든 데이터가 초기화
- 따라서 별도의 저장소가 필요하다
- 세션, 캐시를 memcached나 redis로 외부 분리

### 컨테이너(Container)

- 기존의 서버 개발자들은 모든 프로그램을 한땀한땀 설치
- 현재는 컨테이너라는 형태로 모든 프로그램들을 설치할 수 있다
- 해당 컨테이너들은 어디서든 돌릴 수 있다
- VM인가? => 반은 맞고 반은 틀림
  - VM과 동일하게 각 컨테이너들끼리 독립성을 보장하지만
  - VM보다 빠르고 효율적임

## 쿠버네티스(k8s)

> 컨테이너라는 기술을 효율적으로 사용하기 위한 기술
>
> 도커는 하나의 서비스에 대해서만 관리 가능
>
> 쿠버네티스의 경우에는 내부에 여러 도커가 있다고 생각하면 된다 => 여러 서비스를 동시에 관리 가능

### 스케줄링

- 컨테이너를 적당한 서버에 배포해 주는 작업
- 여러 대의 서버 중 가장 idle한 서버에 배포, 랜덤 배포, 순차 배포
- 컨테이너 개수를 여러개로 늘려가며 적당히 나눠 배포, 서버가 죽으면 실행중이던 컨테이너를 다른 서버에 띄움

### 클러스터링

- 여러 서버를 하나의 서버처럼 동시성 있게 사용
- 여기저기 흩어진 컨테이너를 가상 네트워크를 통해 하나에 있는거처럼 쉽게 통신

### 서비스 디스커버리

- 서비스 찾아주는 기능
- 클러스터 환경에서 컨테이너는 어느 서버에 생성될지 알 수 없음
- 따라서 컨테이너와 통신하기 위해서는 어디에 위치하는지 알아야하고 연결 된 후 중지 되었다가 다른곳에 띄워지면 연결 정보도 바꿔줘야함(Ip, Port)
- 일일히 찾는 수고를 덜어주는 편의성



## Docker + Jenkins 배운점들

1. (맥북에서) Docker를 설치할때 

- 아래와 같은 오류 발생
- docker 사이트 내에서 Mac용 Docker 어플리케이션 설치 이후
- 터미널을 켜서 ( brew install --cask docker )로 문제 해결
- docker가 System-level 패키지이기에 --cask 옵션을 넣어주어야함

![docker Cannot connect](https://user-images.githubusercontent.com/41468004/127697154-904f92d7-c134-4ad9-8a4d-05f59e6a40fe.png)



2. jenkins 실행시에 -v 옵션을 주어서 컨테이너들끼리 디렉토리를 공유할 수 있게끔 한다

![Docker Run](https://user-images.githubusercontent.com/41468004/127697225-44d68fdf-a88f-474f-8a0a-b5709bed4e8f.png)

- 로컬 디렉토리에 있는 dist파일, 즉 vue-cli 프로젝트를 생성하여 build된 디렉토리를 컨테이너들끼리 공유할 수 있게끔한다
- 추 후 nginx 웹 서버가 이 디렉토리를 사용하여 dist폴더를 서버에 띄우기 때문



3. ngrok

![Session Status](https://user-images.githubusercontent.com/41468004/127697288-98fbf360-5344-4cda-b568-6b44b6932323.png)

- PipeLine과 GitLab의 WebHook을 위해서는 localhost로 연결할 수 없다는 에러가 나온다
- 이 문제를 해결하기 위해서 ngrok을 이용해 localhost를 가상화 해줘 외부에서 접근할 수 있도록 해준다
- 추가적으로 ngrok을 이용하는 이상 ngrok의 Session이 만료되면 다시 수행해서 gitalb에 다시 연결해줘야 하는 불편함이 존재한다.



4. PipeLine으로 GitLab과 연결

![PipeLine Gitlab](https://user-images.githubusercontent.com/41468004/127697369-5fa1b119-8833-4036-83d3-ce5047c0c024.png)

- Pipeline script from SCM으로 선택하여 레포와 Credential을 추가해준다
- 웹훅으로 인해서 push 될떄 Jenkins가 자동적으로 읽은 script파일명을 설정해준다
- 이 Script 파일은 내가 CI/CD를 적용할 Repository의 최상단에 존재하면 된다.
- 설정해주면 Jenkins는 해당 레포에 대해서 push가 일어날때마다 해당 깃의 파일들을 Jenkins으로 pull해와 JenkinsFile을 읽어 script내용을 수행하게 된다



5. Pipeline Script

![pipeline](https://user-images.githubusercontent.com/41468004/127697486-39f1f0fa-1ac1-43dc-ba9c-f1186bf81bc4.png)

- 위와 같은 방식으로 script를 작성하였다
- 위의 tools에서 사용될 것들에 대해서는 global tool 설정을 해주어야하는데 다음 페이지에서 설명
- Jenkins가 각 stage별 step을 하나씩 실행하게 된다
- 나는 yarn으로 vue-cli프로젝트를 빌드하고 실행하게끔해놔서 Jenkins으로 빌드시에 yarn이 필요하여 npm으로 yarn을 설치하게끔 했다
- 그리고 빌드된 dist폴더를 jenkins 실행시에 공유해준 디렉토리로 복사해주면 자동으로 배포되어 다음과 같이 변경이 잘 된 모습이 보인다

