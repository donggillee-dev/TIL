# DB

> 여러 사람들이 공유하고 사용할 목적으로 통합 관리되는 데이터들의 모임

- [키](#키)



## 키

> 검색이나 정렬 시 튜플을 구분할 수 있는 기준이 되는 속성

튜플 : 릴레이션을 구성하는 각각의 행, 속성의 모임

- 튜플의 수 = 카디널리티(Cardinality)



1. 후보키(Candidate Key)
   - 튜플을 유일하게 식별할 수 있는 속성들의 부분 집합
     - 기본키로 사용할 수 있는 속성들
   - 모든 릴레이션은 반드시 하나 이상의 후보키를 가져야 함
   - 릴레이션의 모든 튜플에 대해서 **유일성**과 **최소성**을 만족시켜야 함
     - 유일성 : Key로 하나의 튜플을 유일하게 식별할 수 있음
     - 최소성: 꼭 필요한 속성으로만 구성
2. 기본키(Primary Key)
   - 후보키 중 선택한 메인 키
   - 한 릴레이션에서 **특정 튜플을 유일하게 구별할 수 있는 속성**
   - Null 불가능
   - 기본키로 정의된 속성에는 동일한 값이 중복되어 저장될 수 없음
     - 개체 무결성: 기본키가 가지고 있는 값의 유일성을 보장받아야 함
3. 대체키(Alternate Key)
   - 후보키가 둘 이상일 때, 기본 키를 제외한 나머지 후보키
   - 보조키라고도 함
4. 슈퍼키(Super Key)
   - 한 릴레이션 내에 있는 속성들의 집합으로 구성된 키
   - 유일성은 만족하지만, 최소성은 만족시키지 않음
5. 외래키(Foreign Key)
   - 관계를 맺고 있는 릴레이션 R1, R2에서 릴레이션 R1이 참조하고 있는 릴레이션 R2의 기본키와 같은 R1 릴레이션의 속성
   - 참조되는 릴레이션의 기본키와 대응되어 릴레이션 간에 참조 관계를 표현하는데 중요한 도구로 사용됨
   - 외래키로 지정되면 참조 테이블(R2)의 기본키에 없는 값은 입력할 수 없음



## 스키마

> 데이터베이스의 구조와 제약조건에 관한 전반적인 명세를 기술한 것

- 속성, 개체, 관계에 대한 정의, 제약 조건 등을 기술

![](https://user-images.githubusercontent.com/55429912/120893143-9ab06e00-c64c-11eb-9741-48f1f3b4b0e6.png)

1. 외부스키마(External Schema)
   - 사용자 View
   - 사용자의 입장에서 정의한 DB의 논리적 구조
   - 하나의 데이터베이스 시스템에는 여러개의 외부 스키마 존재 가능
   - 하나의 외부 스키마를 여러개의 응용 프로그램이나 사용자가 공용할 수 있음
2. 개념스키마(Conceptual Schema)
   - 전체적인 View
   - DB의 전체적인 논리적 구조를 기술하기 때문에 DB에 하나만 존재하며, 통상 스키마라 하면 개념 스키마를 일컫음

3. 내부 스키마
   - 저장 스키마
   - 실제로 데이터베이스에 저장될 레코드의 물리적인 구조를 정의



## 조인

> 두 개 이상의 테이블이나 데이터베이스를 연결하여 데이터를 검색하는 방법

- 테이블을 연결하려면 적어도 하나의 컬럼을 서로 공유하고 있어야 하므로 이를 이용하여 데이터 검색에 활용

![](https://user-images.githubusercontent.com/55429912/120887283-e05d3e80-c62c-11eb-89da-c4284c1ef529.png)

## Index

> 책의 색인이라고 생각하면됨
>
> 어떠한 레코드가 저장된 주소를 키와 값의 쌍으로 만들어 두는 것



**사용되는 자료구조**

DBMS는 인덱스를 어떻게 관리하나?

- B+ Tree 인덱스 알고리즘
  - 칼럼의 값을 변형하 않고 원해의 값을 이용해 인덱싱하는 알고리즘
- Hash 인덱스 알고리즘
  - 칼럼의 값으로 해시 값을 계산해 인덱싱하는 알고리즘
  - 매우 빠른 검색 지원

**Hash vs B+ Tree**

- 데이터에 접근하는 시간복잡도 Hash가 더 빠른데 왜 트리?
- SELECT 질의에는 부등호(<>)연산도 포함이 됨
- 이 부등호 연산을 Hash에서는 불가능

### Simple Indexes(Primary Indexes)

`정렬되어있는 파일에 대한 간단한 인덱스`

#### Indexes on Sequential Files

> 정렬된 파일은 Data file에게 Key-Pointer 쌍을 가지고 있는 Index file이 주어짐
>
> Index file안의 Search Key K는 Data file을 참조하고 있는 포인터와 연관이 있음
>
> - Dense Index : Data File의 모든 레코드 항목이 존재하는 인덱스
> - Sparse Index : 몇몇 Data Record에 대해서만 표현되는 인덱스 구조, 보통 파일 or 블록당 하나의 매치

#### Sequential Files

`Index의 컬럼에 의해 정렬되는 파일`

- 보통 이러한 구조는 Search Key가 릴레이션의 PK일때 유용

<img src="https://user-images.githubusercontent.com/41468004/140704730-0e887dbb-04e6-49d3-9b2c-faeb30afbbeb.png" alt="image" style="zoom:50%;" />

#### Dense Index

`모든 키가 인덱스에 표현되어진 밀집이기에 Dense`

- Key와 레코드 자체에 대한 포인터만 보유하는 블록의 나열
- 키와 포인터만 보유한 인덱스, 파일 자체보다 훨씬 적은 공간을 소모
- 1번의 접근 -> 1번의 Disk I/O로 원하는 레코드를 찾을 수 있음 

<img src="https://user-images.githubusercontent.com/41468004/140705258-85cfc052-e537-48c3-9ca0-cd56ad2d43d6.png" alt="image" style="zoom:50%;" />

- Dense Index는 원하는 값 K를 찾을때까지 탐색해야함 (평균적으로는 절반)
- 성능이 별로인거같은데 다음과 같은 이유로 좋다
  - 키값이 정렬되어있기에 이진탐색 가능
  - 인덱스는 메인 메모리에 저장할 수 있을만큼 작다 => Disk I/O 수행이 필요 없음

#### Sparse Index

`데이터 블록마다 오직 한개의 Key-Pointer 쌍을 보유한다`

- Dense Index는 모든 데이터 블록에 대한 저장을 하기에 비교적 큼
- Sparse는 그에 비해 훨씬 적은 공간을 사용하지만 탐색하는데 더 비용이 많이 든다
- Sparse는 해당 블록에 K가 있을지도 모르니 disk I/O를 해야한다

<img src="https://user-images.githubusercontent.com/41468004/140706202-d4ebc0cf-e2fd-4689-8054-2f32f3e58c97.png" alt="image" style="zoom:50%;" />

- 찾으려는 K값과 작거나 같은 것들중 가장 큰 값을 찾는다
- Dense와 마찬가지로 정렬되어있기에 이진 탐색 가능
- 찾은 값을 통해 해당 블록으로 들어가 Disk I/O

#### Multiple Level Index

`인덱스를 위한 인덱스를 둠으로써 효율적으로 인덱스를 사용하는 방법`

- 이진탐색으로 레코드를 찾더라도 여전히 수차례의 Disk I/O를 수행해야함

- 이를 해결하기 위해 Multiple Level Index

<img src="https://user-images.githubusercontent.com/41468004/140706742-fd282628-de0d-4965-93e8-8ff7d4a7e1bb.png" alt="image" style="zoom:50%;" />

- 위 사진은 기존의 Sparse Index에 레벨 하나 더 추가한 것
  - 원래의 인덱스를 First Level Index
  - 추가한 인덱스를 Second Level Index
- 최초의 인덱스가 아니라면 반드시 `sparse index`, 최초는 둘 다 가능
- 하지만 이것도 한계가 존재하므로 B-Tree 구조로 빌드하는 것이 좋다

### Secondary Indexes

> 이전까지는 인덱스와 레코드가 직접적으로 연결되어 위치를 결정 => Primary Indexes
>
> 다양한 쿼리문을 유용하게 사용하기 위해 한 릴레이션에 여러 인덱스를 두고자 => Secondary Indexes

- 다른 인덱스를 돕는 보조 인덱스
- 레코드의 위치를 결정짓지 않는다, 레코드의 위치가 어딘지 알려주는 역할일뿐
- 그러므로 보조 인덱스는 Dense Index
  - Sparse Index는 위치에 영향을 끼치기 때문

<img src="https://user-images.githubusercontent.com/41468004/140707885-a421fabe-4ab7-469c-95a3-74e535dfcc65.png" alt="image" style="zoom:50%;" />

- 데이터는 정렬하지 않아도 인덱스는 정렬

<img src="https://user-images.githubusercontent.com/41468004/140708273-a37af1d9-1e28-4adf-b54a-3a2a332dff03.png" alt="image" style="zoom:50%;" />

- 이전 그림처럼 Secondary Index를 설계하면 공간의 낭비 발생
- 특정 값을 가진 데이터 레코드에 대한 포인터에 대해 한번의 키 값만 사용하게끔 위 그림처럼 처리하면 효율 good
  - Open Addressing 방식

## 정규화

> 관계형 데이터베이스에서 데이터 중복을 최소화 하기 위해 데이터를 구조화 하는 작업

#### 목적

- 이상 현상 제거
  1. 삽입 이상 : 새로운 데이터를 삽입하기 위해 불필요한 데이터도 함께 삽입
  2. 갱신 이상 : 반복된 데이터 중 일부를 갱신할 경우 데이터의 불일치 발생
  3. 삭제 이상 : 데이터를 삭제하기 위해 필요한 데이터도 함께 삭제
- 데이터베이스 구조 확장시 재 디자인 최소화
- 사용자에게 데이터 모델을 더욱 의미있게 제공



#### 정규화 종류

1. 제 1정규화

   ![](https://user-images.githubusercontent.com/55429912/120803172-3cb55500-c57e-11eb-8474-48a988b4050c.png)

   - 도메인이 오직 `원자값`을 포함하 속성의 원자성을 확보, 기본키를 설정

2. 제 2정규화

   ![](https://user-images.githubusercontent.com/55429912/120803386-76865b80-c57e-11eb-8953-a9536b26912c.png)

   - 기본키가 2개 이상의 속성으로 이루어진 경우
   - 부분 함수 종속성 제거

3. 제 3정규화

   ![](https://user-images.githubusercontent.com/55429912/120804048-28be2300-c57f-11eb-8225-18d02de76be0.png)

   - 이행적 함수 종속성 제거
   - X -> Y, Y -> Z, X -> Z (이행적 함수 종속성)

4. BCNF

   - 결정자이면서 후보키가 아닌 것 제거

5. 제 4정규화

   - 다치 종속성 제거

6. 제 5정규화

   - 조인 속성이용



#### 함수적 종속성이란?

> X의 값을 알면 Y의 값을 바로 식별할 수 있고, X의 값에 Y의 값이 달라질 때, Y는 X에 함수적 종속이라고 한다.

- X를 결정자, Y를 종속자(X -> Y)

1. 완전 함수적 종속

   ![](https://user-images.githubusercontent.com/55429912/120884906-e3eac880-c620-11eb-9437-5422cf08bc2e.png)

   - 종속자가 기본키에만 종속되어 있음

   ![](https://user-images.githubusercontent.com/55429912/120884937-1399d080-c621-11eb-8b15-932e18468cb3.png)

   - 기본키가 여러 속성으로 구성되어 있을 경우, 기본키를 구성하는 모든 속성이 포함된 기본키의 부분집합에 종속된 경우

2. 부분 함수적 종속

   ![](https://user-images.githubusercontent.com/55429912/120884971-49d75000-c621-11eb-9ee7-b66aea863c9a.png)

   - 종속자가 기본키가 아닌 다른 속성에 종속되거나, 기본키가 여러 속성으로 구성되어 있을 경우 **기본키를 구성하는 속성중 일부만 종속**되는 경우

3. 이행적 함수 종속

   ![](https://user-images.githubusercontent.com/55429912/120885300-2f9e7180-c623-11eb-8aaa-810786782b7a.png)

   - 릴레이션에서 X,Y,X라는 3개의 속성이 있을때, **X → Y → Z란 종속 관계가 있을 경우, X → Z가 성립**되는 경우



## 트랜잭션

> 하나의 논리적 작업 단위를 구성하는 일련의 연산들의 집합

#### ACID

1. 원자성(Atomicity)
   - 트랜잭션이 DB에 모두 반영되거나, 혹은 전혀 반영되지 않아야 한다.
   - 100개의 연산으로 이루어진 트랜잭션에서 1개의 오류만 발생해도 100개는 모두 Fail처리
   - 즉, 트랜잭션에서 중간상태는 없다
2. 일관성(Consistency)
   - 트랜잭션의 작업 처리 결과는 항상 일관성 있어야 한다.
   - A계좌에서 B계좌로 이체를 하기 전/후 모두 A + B 금액은 같아야한다
3. 독립성(Isolation)
   - 둘 이상의 트랜잭션이 동시에 병행 실행되고 있을 때, 어떤 트랜잭션도 다른 트랜잭션 연산에 끼어들 수 없다.
   - A계좌에 대한 어떤 트랜잭션이 이미 작동중이라면 다른 트랜잭션이 끼어들 수 없음
4. 지속성(Durability)
   - 트랜잭션이 성공적으로 완료되었으면, 결과는 영구적으로 반영되어야 한다.
   - 어떠한 치명적 오류로 인해 시스템 장애가 발생되어도 회복 후 그 데이터는 복구되어야 한다
     - 주기억장치가 아닌 디스크, 보조기억장치에 저장하면된다

#### 연산

**Commit**

- 하나의 트랜잭션이 성공적으로 끝났고, DB가 일관성있는 상태일때 이를 알려주기 위해 사용하는 연산

**Rollback**

- 하나의 트랜잭션 처리가 비정상적으로 종료되어 트랜잭션 원자성이 깨진 경우
- 트랜잭션이 정상적으로 종료되지 않았을때, Last Consistent State로 Rollback 할 수 있음

#### 상태

- 활동(Active)
  - 트랜잭션이 실행 중에 있는 상태, 연산들이 정상적으로 실행 중인 상태
- 장애(Failed)
  - 트랜잭션이 실행에 오류가 발생하여 중단된 상태
- 철회(Aborted)
  - 트랜잭션이 비정상적으로 종료되어 Rollback 연산을 수행한 상태
- 부분 완료(Paritally Committed)
  - 트랜잭션이 마지막 연산까지 실행했지만, Commit 연산이 실행되기 직전의 상태
- 완료(Committed)
  - 트랜잭션이 성공적으로 정료되어 Commit 연산을 실행한 후의 상태

- `Partially Committed` 와 `Committed`의 차이
  - `Commit` 요청이 들어오면 상태는 `Partial Committed` 상태가 된다
  - 이후 `Commit`을 문제없이 수행할 수 있으면 `Committed`로 전이
  - 만약 오류라면은 `Failed`로 전이
  - `Partial Committed`는 `Commit` 요청이 들어왔을 때를 의미하고 `Committed`는 `Commit`이 완료 되었음을 의미

#### 격리 수준

`트랜잭션에서 일관성 없는 데이터를 허용하도록 하는 수준`

- DB는 ACID특성에 의해 트랜잭션이

- Locking을 통해 트랜잭션이 DB를 다루는 동안 다른 트랜잭션이 관여하지 못하도록 막아야 함
  - Locking 범위 ↑, 데이터베이스 성능 ↓
  - Locking 범위 ↓, 잘못된 값이 처리될 가능성 ↑

1. Read Uncommitted(레벨 0)
   - `트랜잭션이 처리중이거나, 아직 Commit 되지 않은 데이터를 다른 트랜잭션이 읽는 것을 허용`
     - DB의 일관성을 유지하는 것이 불가능
     - `Dirty Read`, `Non-Repeatable Read`, `Phantom Read`
2. Read Committed(레벨 1)
   - `트랜잭션이 수행되는 동안 다른 트랜잭션이 접근할 수 없어 대기하게 됨`
     - Commit이 이루어진 트랜잭션만 조회 가능
     - SQL 서버가 Default로 사용하는 Isolation Level
     - `Non-Repeatable Read`, `Phantom Read`
3. Repetable Read(레벨 2)
   - `트랜잭션의 범위 내에서 조회한 데이터의 내용이 항상 동일함을 보장`
     - 다른 사용자는 그 영역에 해당하는 데이터에 대한 수정이 불가능
     - `Phantom Read`
4. Serializable(레벨 3)
   - `완벽한 읽기 일관성 모드를 제공`
     - 다른 사용자는 그 영역에 해당되는 데이터에 대한 수정 및 입력이 불가능

##### Dirty Read

- 어떤 트랜잭션에서 아직 실행이 끝나지 않은 다른 트랜잭션에 의한 변경 사항을 보게 되는 경우


##### Non-Repeatable Read

- 한 트랜잭션에서 같은 쿼리를 두 번 수행할 때, 그 사이에 다른 트랜잭션이 값을 수정 또는 삭제함으로써 두 쿼리의 결과가 상이하게 나타나는 비일관성 현상

##### Phantom Read

- 한 트랜잭션 안에서 일정 범위의 레코드를 두 번 이상 읽을 때, 첫 번째 쿼리에서 없는 레코드가 두 번째 레코드에서 나타나는 현상



## 클러스터링, 레플리케이션, 샤딩

우선 가장 기본적인 DB구조를 보자

<img src="https://user-images.githubusercontent.com/41468004/140709654-8aa9e81e-e5be-4a99-9dae-796740e7b546.png" alt="image" style="zoom: 67%;" />

- DB 서버와 디스크 역할을 하는 DB 스토리지가 한 구성으로 되어있음

- 그런데 이 구성과 같이 운영할 경우 DB 서버가 죽으면 관련된 서비스 전체가 중단



### 클러스터링(Clustering)

#### 동작 과정

- 1개의 노드에 쓰기 트랜잭션이 수행, COMMIT을 수행
- 실제 디스크에 쓰기 전에 다른 분산 노드들에게 동일하게 복제를 요청
- 다른 노드에서 복제 요청을 받고 각각 디스크에 동기적으로 데이터를 저장
  - 이런 동기적인 방법으로 인해 데이터의 무결성을 보장
  - 다만, Replication 방식에 비해 성능이 떨어진다

#### Active-Active 클러스터링

<img src="https://user-images.githubusercontent.com/41468004/140709901-6e868d21-dc90-4251-9317-0836e48b04cc.png" alt="image" style="zoom: 67%;" />

장점

- 하나의 DB 서버가 죽어도 다른 DB서버가 동작
- 하나의 서버가 부담하던 부하를 두 개의 DB 서버가 분담하기에 CPU, Memory 부하도 적어진다

단점

- 하나의 DB 스토리지를 두개의 DB 서버가 공유하기에 병목 현상 발생 가능
- 이전보다 많은 비용의 투자가 이루어져야 한다

#### Active, Stand-By 클러스터링

<img src="https://user-images.githubusercontent.com/41468004/140711124-6b5869f2-9d1d-4c5c-a564-a3327345cfc2.png" alt="image" style="zoom: 67%;" />

장점

- Active-Active의 단점을 보완하기 위한 방법
- Active 서버의 문제 생기면 Fail Over를 통해 Active와 Stand-By를 상호 전환 => 장애 대응 가능

단점

- Fail Over가 이루어지는 수초~수분 간의 시간동안 영엽 손실 필연적 발생
- 하나뿐인 스토리지에 오류가 발생하면 데이터 복구는 어떻게...?

#### Shared Disk

- 위의 스토리지 하나 여러대의 DB 서버의 방식
- 병목현상 발생 가능하기도 하고 각 서버간의 정보 공유를 위한 오버헤드도 크다

#### Shared Nothing

- 아무것도 공유하지 않는다는 것, 즉 자원을 분리
- Active Stand-By에서 저장소 하나만 있을때 해당 스토리지에 오류가 발생하게 될 경우를 방지하기 위한 방법
- 하지만 지속적으로 모든 저장소에 대해 데이터 무결성, 동기화를 동기적으로 해야하기에 구조가 복잡하다

### 레플리케이션(Replication)

<img src="https://user-images.githubusercontent.com/41468004/140864459-5009c49f-848b-4e5c-9d32-455687f93631.png" alt="image" style="zoom: 67%;" />

- Master 노드에 쓰기 트랜잭션이 수행
- Master 노드는 데이터를 저장하고 트랜잭션에 대한 로그를 파일에 기록(BIN LOG)
- Slave 노드의 I/O Thread는 Master노드의 BIN LOG를 Relay Log에 복사
- Slave 노드의 SQL Thread는 Relay Log를 한 줄씩 읽으며 데이터를 저장

장점

- 이를 통해 스토리지가 1개 였을때의 데이터 손실을 방지할 수 있음
- 비동기 방식으로 운영되어 지연 시간이 거의 없다

단점

- Slave DB는 놀게된다
- 이를 해결하기 위해 Master DB에는 Insert, Update, Delete, Slave DB에는 Select
  - DB 서버 부하를 줄일 수 있음
- 특정 테이블에 데이터가 엄청 많다면?
  - Slave DB를 여러대 두더라도 특정 값을 찾기 위해서는 동일하게 오랜 시간이 걸린다
  - 이를 해결하기 위한게 `샤딩`
- 노드들 간의 데이터 동기화가 보장되지 않는다(데이터 무결성 검사 x) -> 일관성있는 데이터를 얻지 못하게 될 수 있다
- Master 노드가 죽게될 경우 장애 대처, 복구가 복잡하다

### 샤딩(Sharding)

`테이블을 특정 기준으로 나누어 저장 및 검색하는 것`

- Data를 어떻게 잘 분산시킬지, 어떻게 잘 읽을지가 핵심
- 어떻게 잘 분산시킬지의 기준 => `Shard Key`
  - 세 가지 방법이 존재
  - Hash Sharding
  - Dynamic Sharding
  - Entity Group

#### Hash Sharding

<img src="https://user-images.githubusercontent.com/41468004/140712758-de469671-7ffc-44a9-a5c4-9b3d26ca9d03.png" alt="image" style="zoom:67%;" />

- Shard의 수만큼 Hash 함수를 이용
- Hash 함수의 결과에 따라 DB 서버에 저장하는 방식

장점

- 구현이 정말 간단

단점

- 확장성이 낮음
- DB 서버가 추가될 경우 Hash Function이 변경
  - => 기존 Shard에 들어가 있던 데이터들의 정합성이 깨짐

#### Dynamic Sharding

<img src="https://user-images.githubusercontent.com/41468004/140713110-6ed0fcad-8163-4cfb-b35d-75de760133ef.png" alt="image" style="zoom:67%;" />

`Locator Service`

- 테이블 형식의 데이터를 바탕으로 샤드를 결정
- 그 결과를 통해 적절한 샤드에 데이터 배치

장점

- Hash Sharding과 달리 Locator Service에 키만 추가해주면 되기에 확장 쉽다

단점

- Locator Service가 단말 장애점이 될 수 있음
- Locator Service가 장애가 발생하면 모든 샤드들에 대해 문제가 발생할 수 있다

#### Entity Group

<img src="https://user-images.githubusercontent.com/41468004/140713461-4ea615a7-95d0-48ef-86dc-f84ace0139d1.png" alt="image" style="zoom:67%;" />

- Hash, Dynamic Sharding 둘 다 NoSQL에 최적화되어있다
- Entity Group은 RDB에 최적화된 방식
- `연관된 Entity Group을 한 Shard`에 두는 방식

장점

- 같은 Shard에 존재하는 데이터를 조회할떄는 효과적

단점

- 다른 Shard에 존재하는 데이터를 조회하게 될 경우에는 성능이 떨어진다

## SQL vs NoSQL

#### SQL(관계형 DB)

> SQL을 사용하면 RDBMS에서 데이터를 저장, 수정, 삭제 및 검색 가능

##### 특징

- 데이터는 **정해진 데이터 스키마에 따라 테이블에 저장**
  - SQL에서 데이터는 테이블에 레코드로 저장되는데, 각 테이블마다 **명확하게 정의된 구조에 준수하여 데이터 추가** 가능.
- 데이터는 **관계를 통해 여러 테이블에 분산**
  - 데이터의 중복을 피하기 위해 **관계**를 이용 



#### NoSQL(비관계형 DB)

> 말그대로 SQL의 반대, 스키마도 없고 관계도 없다

**특징**

- NoSQL에서 레코드를 **문서**라고 부른다
  - SQL은 정해진 스키마를 따르지 않으면 불가능했는데 NoSQL에서는 **다른 구조의 데이터를 같은 컬렉션에 추가 가능**
- 문서는 Json과 비슷한 형태, 관련 데이터를 동일한 **컬렉션**에 넣는다

- 즉, 여러 테이블에 조인할 필요없이 이미 필요한 모든 것을 갖춘 문서를 작성하는 것이 NoSQL
- 조인을 할 필요가 없음, 그럼 조인하고 싶을떄는?
  - 컬렉션을 통해 데이터를 복제, 각 컬렉션 일부분에 속하는 데이터를 정확하게 산출
- 자주 변경되지 않는 데이터일 경우 NoSQL을 쓰면 상당히 효율적



#### SQL 장점

- 명확하게 정의된 스키마, 데이터 무결성 보장
- 관계는 각 데이터를 중복없이 한번만 저장

#### SQL 단점

- 덜 유연함. 데이터 스키마를 사전에 계획하고 알려야 함. (나중에 수정하기 힘듬)
- 관계를 맺고 있어서 조인문이 많은 복잡한 쿼리가 만들어질 수 있음
- 대체로 수직적 확장만 가능함



#### NoSQL 장점

- 스키마가 없어서 유연함. 언제든지 저장된 데이터를 조정하고 새로운 필드 추가 가능
- 데이터는 애플리케이션이 필요로 하는 형식으로 저장됨. 데이터 읽어오는 속도 빨라짐
- 수직 및 수평 확장이 가능해서 애플리케이션이 발생시키는 모든 읽기/쓰기 요청 처리 가능

#### NoSQL 단점

- 유연성으로 인해 데이터 구조 결정을 미루게 될 수 있음
- 데이터 중복을 계속 업데이트 해야 함
- 데이터가 여러 컬렉션에 중복되어 있기 때문에 수정 시 모든 컬렉션에서 수행해야 함 (SQL에서는 중복 데이터가 없으므로 한번만 수행이 가능)



#### SQL 데이터베이스 사용이 더 좋을 때

- 관계를 맺고 있는 데이터가 자주 변경되는 애플리케이션의 경우

  > NoSQL에서는 여러 컬렉션을 모두 수정해야 하기 때문에 비효율적

- 변경될 여지가 없고, 명확한 스키마가 사용자와 데이터에게 중요한 경우



#### NoSQL 데이터베이스 사용이 더 좋을 때

- 정확한 데이터 구조를 알 수 없거나 변경/확장 될 수 있는 경우
- 읽기를 자주 하지만, 데이터 변경은 자주 없는 경우
- 데이터베이스를 수평으로 확장해야 하는 경우 (막대한 양의 데이터를 다뤄야 하는 경우)



[출처]

https://gyoogle.dev/blog/computer-science/data-base/SQL%20&%20NOSQL.html



## 커넥션 풀(Connection Pool)

> Connection을 여러개 만들어 저장해 놓은 공간(Cache), 또는 이 공간의 Connection을 필요할 때 꺼내 쓰고 반환하는 기법

![](https://user-images.githubusercontent.com/55429912/120885589-c4ee3580-c624-11eb-96a5-16ea9e9d3a59.png)



#### 접근 방법

![](https://user-images.githubusercontent.com/55429912/120885679-18f91a00-c625-11eb-859b-59d283af9d12.png)

1. 웹 컨테이너가 실행되면서 DB와 연결된 Connection 객체들을 미리 생성해 풀에 저장
2. DB에 요청시 풀에 있는 Connection 객체를 가져와 DB에 접근
   - Connection이 부족하다면?
     - 모든 요청이 DB에 접근하고 있어서 남는 Connection이 없다면, 해다 클라이언트는 대기 상태로 전환시키고 풀에 Connection이 반환되는 경우 대기 상태의 클라이언트들에게 순차적으로 Connection 제공
3. 처리가 끝나면 다시 풀에 Connection 반환



#### 사용 이유

- 매 연결마다 Connection 객체를 생성하고 소멸시키는 비용을 줄일 수 있다
- 미리 생성된 Connection 객체를 사용하기에 DB 접근 시간 단축
- DB에 접근하는 Connection 수를 제한해, 메모리와 DB에 걸리는 부하 조정 가능



#### 비슷한 개념

Thread Pool

- 매 요청마다 요청을 처리할 스레드를 만드는게 아닌 미리 생성한 풀내의 스레드를 소멸시키지 않고 재사용하여 효율적으로 자원 활용하는 기법
- WAS에서 스레드와 커넥션 수는 메모리와 직접적인 연관이 있기에 많이 사용할수록 메모리 많이 점유
- 메모리를 위해 그 수를 적게 지정하면 요청이 늦어질 수도 있음
- WAS에서 스레드 수가 커넥션 수보다 많은 것이 좋다
  - 모든 요청이 DB에 접근하는 작업이 아니기 때문
