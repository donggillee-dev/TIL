## 

## Docker



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

