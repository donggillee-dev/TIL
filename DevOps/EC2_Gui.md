# AWS EC2 Ubuntu(18.04 LTS) GUI로 연결하기

> * [AWS 계정 및 인스턴스 생성](#aws-계정-및-인스턴스-생성)
> * [서버와 ssh 연결](#서버와-ssh-연결)
> * [gui 설정](#gui-설정)



### AWS 계정 및 인스턴스 생성

계정을 생성한 후에 인스턴스 선택, EC2 생성
![ec2](https://user-images.githubusercontent.com/41468004/130927802-40929605-7e3f-4f81-ad19-3f27786e17bb.png)



선택한 ec2 인스턴스의 OS를 Ubuntu 18.04 LTS로 선택해줬다

![ubuntu18.04 LTS](https://user-images.githubusercontent.com/41468004/130928058-614583e0-bf0c-459d-850c-c79172dba671.png)



프리티어 사용자니까 t2.micro를 선택해준다

그리고 생성되는 pem 키체인은 잘 보관해두도록 한다

![성능 선택](https://user-images.githubusercontent.com/41468004/130928384-f2783f90-89f4-4dc0-9b7a-59efebe4d450.png)

### 서버와 ssh 연결

여기서부터는 내가 사용하는 mac 기반으로 작성되어있다

우선 아까 생성한 키체인(pem파일)을 ~/.ssh로 이동시킨다

![ssh 디렉토리](https://user-images.githubusercontent.com/41468004/130929513-0fdc5a82-d044-4f59-a48a-f0123d4d8e09.png)



연결을 하기 이전에 해당 키체인의 권한 설정을 해준다

이걸 해주지 않으면 ssh 접속시에 private key의 Permission 오류가 발생하게 된다

```bash
chmode 600 <키체인 이름>.pem
```



간편하게 ssh로 내가 원하는 서버에 접속하기 위해서 config 파일을 수정해준다

```bash
Host <내가 접속할 인스턴스의 별칭>
HostName <인스턴스의 public ip>
User ubuntu
IdentityFile <인스턴스의 pem 키체인 위치>
```



이후에 아래의 명령어를 쳐서 인스턴스에 원격 접속이 가능하다

```bash
ssh <Host>
```



### gui 설정

일단 gui로 접속하는 방식은 gui대로 cli는 cli대로 접속되는 것이라고 한다

cli에서의 설정을 통해 열린서버를 원격 데탑으로 접속 or vnc를 이용해서 따로 들어가지게 되는 것이다

cli를 대신해서 gui가 뜨는 것이 아니다



cli 원격 접속을 하고 나면 아래의 명령어를 쳐서 필요한 것들을 설치해준다

```bash
 sudo apt-get update #최신 패키지 리스트로 업데이트
 sudo apt-get upgrade #update를 통해 가져온 패키지들을 최신 버전으로 업그레이드
 sudo apt-get install ubuntu-desktop
 sudo apt-get install vnc4server
 sudo apt-get install gnome-panel
```



그 후에 다음과 같이 명령어를 치면

```bash
$ vncserver
```

비번을 세팅하라고 나온다 (8문자 이상 쓰게 되면은 앞 8자리만 잘라서 비번이 되므로 주의하도록 하자)

gui연결하는데 필요한 비번이니 까먹지 않도록 하자



그러고 나서는

```bash
New 'X' desktop is ip-프라이빗아이피:1

Starting applications specified in /home/ubuntu/.vnc/xstartup
Log file is /home/ubuntu/.vnc/ip-프라이빗아이피:1.log
```

이렇게 뜰텐데

이 vncserver를 죽이자, 왜 죽이는지는 잘 모르겠다...



```bash
$ vncserver -kill :1
```



그 이후 .vnc 디렉토리에 있는 xstartup 파일을 수정해주어야 한다

```bash
cd ~/.vnc
vi xstartup

#파일 안에 다음과 같은 내용을 복붙한다

#!/bin/sh
# Uncomment the following two lines for normal          desktop:
unset SESSION_MANAGER
# exec /etc/X11/xinit/xinitrc
gnome-session –session=gnome-classic &
gnome-panel&

#저장해주고 나서 다시 서버 실행

$ vncserver
```



이렇게 서버를 세팅해준 후에 이 vncserver와 gui를 연결해줄 무언가가 필요한데 `realvnc`를 사용했다

그냥 다운로드 받아서 사용하는게 아니라 aws에서 설정이 필요하다

inbound/outbound 규칙에 5901 포트를 열어준다

![포트 열어주기](https://user-images.githubusercontent.com/41468004/130938495-98064665-e631-46d7-a0a0-be22b1c9154d.png)



그 이후 `<ip>:<port>` 이렇게 입력해주고  연결해주면 연결이 된다

![](https://user-images.githubusercontent.com/41468004/130940989-30747d6e-a829-4603-ac4e-45d56b45fb39.png)

회색회면이 뜬다면은 아래와 같은 설정을 인스턴스에서 해주자

```bash
$ sudo apt-get install lxde
$ cd ~
$ cd .vnc
$ nano xstartx 
```



아래의 내용을 작성해준다

```v
#!/bin/sh
def
export XKL_XMODMAP_DISABLE=1
unset SESSION_MANAGER
unset DBUS_SESSION_BUS_ADDRESS

gnome-panel &
gnome-settings-daemon &
metacity &
nautilus &

gnome-terminal &
```

