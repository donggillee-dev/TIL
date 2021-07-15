# JPA 연관 관계(ERD)

* [JPA에서 가장 중요한 것](#jpa에서-가장-중요한-것)
* [연관 관계 정의 규칙](#연관-관계-정의-규칙)
* [다중성](#다중성)



## JPA에서 가장 중요한 것

- `객체`와 `RDB 테이블`이 어떻게 매핑되는지를 이해하는 것
- JPA의 목적이 `객체 지향 프로그래밍과 데이터베이스 사이의 패러다임 불일치 해결` 이라는 것과 가장 연관되어있기 떄문
- 주로 `1차원적인 매핑`과 `연관 관계 매핑`으로 나뉨
  - 1차원적 매핑
    - 말 그대로 객체와 데이터베이스 사이의 일대일로 대응되는 것
    - 기본적인 @Entity, @Column, @Id 등의 Annotation을 숙지하면 됨
  - 연관 관계 매핑
    - 비즈니스 로직 및 요구사항과 개발자의 역량에 따라 관계 설정 방법을 선택해 나가는 방식



## 연관 관계 정의 규칙

- 연관 관계 매핑시에 생각해야 하는 가장 큰 3개 존재
- 방향 : 단방향, 양방향 ( 객체 참조 )
- 연관 관계의 주인 : 양방향일 때, 연관 관계에서 관리 주체
- 다중성 : 다대일, 일대다, 일대일, 다대다

### 

### 단방향, 양방향

DB 테이블은 외래 키 하나로 양 쪽 테이블 조인이 가능

하지만 객체는 참조용 필드가 있는 객체만 다른 객체 참조 가능

- 단방향 : 두 객체 사이에서 한 객체만 참조용 필드 소유
- 양방향 : 두 객체 사이에서 두 객체 모두 참조용 필드 소유



#### 그럼 어떻게 구분?

비즈니스 로직에서 두 객체가 참조가 필요한지 아닌지 고민

- Board.getPost()처럼 참조가 필요?
  - Board -> Post 참조 추가
- Post.getBoard()처럼 참조가 필요?
  - Post -> Board 참조 추가



#### 그냥 양방향 하면 안되나...?

무작정 양방향 해버리면 객체 간의 관계도가 복잡해짐

가장 좋은 방법은 기본적으로 단방향으로 매핑해놓고 역방향으로 참조가 필요해지는 경우에 양방향으로 전환



### 연관 관계의 주인

- 두 객체가 양방향 참조를 할때 (A -> B, B -> A) 연관 관계의 주인을 지정해야 한다

- 주인을 지정함으로써 제어의 권한(CRUD)을 갖는 실질적 주체가 누구인지 JPA에게 알려준다.

- 연관 관계의 주인은 다양한 작업이 가능하지만 주인이 아니라면 조회만 가능
  

  #### 왜 지정해야 할까?

  - 두 객체(Board, Post)가 양방향 연관 관계를 갖는다고 생각
  - 이 상황에서 Board의 Post를 다른 Board로 옮기고 싶을떄?
  - Post의 settBoard로 수정해야하는지?
  - Board의 getPost를 가져와 List를 수정해야하는지?
  - 다 맞는 방법인데 두 객체의 연관 관계를 명확하게 해서 어떻게 수정할떄 FK를 수정할지 정해주는 과정 필요

  

## 다중성

- DB를 기준으로 다중성 결정
  - 연관 관계는 대칭성을 가진다
    - 일대다 <-> 다대일
    - 일대일 <-> 일대일
    - 다대다 <-> 다대다



### 다대일(N : 1)

- Board와 Post를 예시로

  - 요구 사항

    - 하나의 게시판(1)에는 여러 게시글(N)을 작성할 수 있음
    - 하나의 게시글은 하나의 게시판에만 작성 가능
    - 게시글과 게시판은 다대일 관계를 맺는다
    - ![image-20210715140510539](/Users/giri/Library/Application Support/typora-user-images/image-20210715140510539.png)

  - 다대일 단방향

    ```java
    @Entity public class Post { 
      @Id @GeneratedValue
      @Column(name = "POST_ID")
      private Long id;
      
      @Column(name = "TITLE")
      private String title;
      
      @ManyToOne
      @JoinColumn(name = "BOARD_ID")
      private Board board;
      //... getter, setter
    } 
    
    @Entity
    public class Board {
      @Id @GeneratedValue
      private Long id;
      private String title; 
      //... getter, setter
    }
    ```

    - 다대일 단방향이기에 Post(N) 쪽에서 @ManyToOne만 추가해줌
      

  - 다대일 양방향

    ```java
    @Entity public class Post { 
      @Id @GeneratedValue
      @Column(name = "POST_ID")
      private Long id;
      
      @Column(name = "TITLE")
      private String title;
      
      @ManyToOne
      @JoinColumn(name = "BOARD_ID")
      private Board board;
      //... getter, setter
    } 
    
    @Entity
    public class Board {
      @Id @GeneratedValue
      private Long id;
      private String title; 
      
      @OneToMany(mappedBy = "board")
      List<Post> posts = new ArrayList<>();
      //... getter, setter
    }
    ```

    - 다대일 양방향이기에 Board(1) 쪽에 @OneToMany를 추가해주고 연관 관계의 주인을 명시
    - mappedBy의 주체를 명시해주면 됨
      - 연관 관계의 주인쪽은 mappedBy를 사용하지 않음
      - 주인이 아니라면 mappedBy를 사용하여 주인을 지정해야 한다
      - 연관관계의 주인은 외래 키가 있는 곳으로 설정해야 함 => 여기서는 Post



### 일대다(1 : N)

- 그냥 다대일의 거꾸로 버전아님? 이라고 생각할 수도 있는데

- 보통의 외래키는 N의 쪽에 갖지만 => 관계의 주체가 N이며 N쪽에서의 업데이트가 이뤄졌을때 FK의 조작이 이루어진다

- 1 : N 은 N : 1 과는 다르게 1쪽이 연관 관계의 주체가 되는 것

- 즉 Board쪽에서 Post의 데이터를 CRUD하는 것임

  - 일대다 단방향

    ```java
    @Entity public class Post { 
      @Id @GeneratedValue
      @Column(name = "POST_ID")
      private Long id;
      
      @Column(name = "TITLE")
      private String title;
     	//... getter, setter
    } 
    
    @Entity
    public class Board {
      @Id @GeneratedValue
      private Long id;
      private String title;
      
      @OneToMany(name = "POST_ID") //일대다 단방향
      List<Post> posts = new ArrayList<>();
      //... getter, setter
    }
    ```

    - @OneToMany에 mappedBy 사라짐 => 양방향 아니기때문
    - @JoinColumn 이용해서 조인

  - 실제 사용 방법

    ```java
    //... 
    Post post = new Post(); 
    post.setTitle("가입인사"); 
    
    entityManager.persist(post); // post 저장 
    
    Board board = new Board(); 
    board.setTitle("자유게시판"); 
    board.getPosts().add(post); // board 저장시 Board insert 후 post쪽 업데이트문 날아감
    
    entityManager.persist(board); // board 저장 //...
    ```

    - Board 테이블에서 Post테이블의 FK를 수정할 방법이 ㅇ벗음
    - 치명적인 단점
      - 1쪽만 수정한거 같은데 N쪽의 수정 쿼리도 날아감
      - 성능상 이슈는 그렇게 크지는 않음
      - 실무에서 사용하는거 금지

  - 일대다 양방향 실질적으로 존재 X



### 일대일( 1 : 1 )

- JPA에서 단방향 지원 안함

- 1쪽에서 참조하는 것이 좋지 않기 때문, 그래서 1 : 1은 양방향으로

  - 일대일 양방향

    ```java
    @Entity
    public class Post { 
      @Id @GeneratedValue 
      @Column(name = "POST_ID") 
      private Long id; 
      
      @Column(name = "TITLE") 
      private String title; 
      
      @OneToOne
      @JoinColumn(name = "ATTACH_ID")
      private Attach attach; 
      //... getter,setter 
    
    }
    
    @Entity 
    public class Attach { 
      @Id @GeneratedValue 
      @Column(name = "ATTACH_ID") 
      private Long id; 
      private String name; @
      
      OneToOne(mappedBy = "attach") 
        private Post post; 
      //... getter, setter 
    }
    ```

    - 주체는 왜 Post?
    - 객체 입장에서 생각해보자
      - POST쪽에서 외래키를 갖게되면 Post 조회할 때마다 이미 Attach의 참조를 갖고 있기 때문에 성능상 이득
    - 결과적으로 보통 일대일이라고 정할 때도 주 테이블에 외래키를 놓는 것이 옳다



### 다대다( N : N )

- 사실상 사용이 금지 되어있음
- 중간 테이블이 숨겨져 있어 자신도 모르는 복잡한 조인의 쿼리가 발생할 수 있음
- 자동 생성된 중간테이블은 두 객체 테이블의 외래 키만 저장하기에 문제됨
- 해결방법
  - 중간 테이블을 Entity로 명시해줘서 만드는 것으로 해결 가능