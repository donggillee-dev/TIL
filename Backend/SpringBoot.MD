# 스붓 속성강의

- 제일 기초!! SpringBootApplication 얘만 있으면 일단 application생성 완료

- 추가적으로 AOP하려면 @EnableAspectJAutoProxy 추가

  [URL Handler](https://user-images.githubusercontent.com/41468004/125250641-758cda80-e331-11eb-9bea-1f81c4c8a3ef.png)



## 주요 어노테이션

- @EnableAspectJAutoProxy

- @Aspect => AOP 함수

- @Component => 모르겠으면 걍 이걸로, 의존성 주입이 됨 일단

- @Controller => DispatcherServelt이 얘한테 요청 처리 위임 View를 리턴(jsp 파일명)

- @ResponseBody => @Controller의 경우 JSON 타입으로 보내고 싶을떄 명시해주는 어노테이션

- @RequestBody => Controller로 들어올떄 클라에서 서버로 보내는 Body에 들어있는 값

- @RestController => JSON 타입 무적권 리턴

  - JSON 리턴시 HTTP 상태도 같이 리턴하고 싶으면

  - 리턴 값 존재할때

    - return new ResponseEntity<List<MemberDto>>(list, *HttpStatus*.***OK\***);
    - return new ResponseEntity<리턴타입>(리턴값, HttpStatus.Code);
      

  - 리턴 값 없을때

    - return new ResponseEntity(*HttpStatus*.***NO_CONTENT\***);
      

  - 리턴 값을 다중 자료구조로 보내고 싶을때

    - ```java
      BoardDto board = boardService.getArticle(articleno);
      
      // 해당 게시글 객체
      map.put("BoardDto", board);
      
      List<CommentDto> commentList = commentService.getComments(articleno);
      
      map.put("CommentList", commentList);
      
      return new ResponseEntity<HashMap<String, Object>>(map, *HttpStatus*.***OK\***);
      ```



- @PathVariable => api url이 ' / '인 경우

- @RequestParam => api url이 query String인 경우

  - ```java
    @RequestParam(value = "articleno") int articleno
    ```

    

- @GetMapping => 조회

- @PutMapping => 갱신

- @PostMapping => 삽입

- @DeleteMapping => 삭제

  

- @Service => 비즈니스 로직 처리부분

  

- @Mapper => MyBatis사용시 Mapper.xml와 연동되는 인터페이스 위에 작성

- @Repository => DAO위에 작성 약간,,, DB전용 로직처리를 담당하는 애
   JPA에서는 JPARepository를 extend

- public interface PostsRepository extends JpaRepository<Posts => 엔티티, Long => PK타입> { }

- @Entity => JPA에서 사용되는 DB Table과 매핑되는 애

- @Transactional => 다중 트랜잭션 처리 도중 RollBack위함



## Entity, Dto?

### Entity

- 실제 DB와 연동되는 것으로 영속성을 띈 모델이다

- DB와 1 : 1매핑되는 것



### Dto

- Api 통신시에 사용되는 모델로 주고받는 객체



#### 왜?

- 솔직히 두개를 어디에다가 혼용해서 사용해도 로직상 정상적으로 처리될 것이고 문제 없다
- MVC 지향하는 스붓에서 좋은 구조라고 볼 수 없음

- 이유
  1. Viewdp서 표현되는 속성값들은 요청에 따라 달라질 수 있음, 그때마다 영속성을 가진 Entity의 속성값들이 변하게 되면 Entity클래스의 순수성이 모호해짐
  2. API영역간 불필요한 속성값들이 들어간 객체가 리턴될 수 있다 => 명세가 달라지는 이슈 발생



**그래서 반드시 두가지를 분리해야함!!**

- 아래는 두가지가 분리된 아키텍처이다

![](https://user-images.githubusercontent.com/41468004/127250037-4a1f9973-6198-4115-ad13-dfd5a2e040ba.png)



## ModelMapper, MapStruct

위 Entity와 Dto를 매핑해주기 위한 가장 대표적인 매핑 라이브러리

보통은 객체의 프로퍼티 값을 단순하게 매핑(get, set)하거나

일부 형변환 정도가 대부분인 작업이다

- 즉, 반복과 중복이 필연적인 작업
- 매핑 코드에 대한 휴먼 오류가 발생할 가능성이 농후한 작업이다
- 이런 오류와 반복적인 작업을 최소화 할 수 있는 것이 위 두개



### 차이점?

- 트랜드
  - 압도적으로 MapStruct가 많다
  - 단순히 이거만으로 MapStruct 채택?
- 기술적 측면
  - MapStruct가 구현의 방식, 성능적 측면에서 유리한 부분이 더 많다
  - 압도적으로 속도차이가 난다
  - 속도로 인해 처리량의 차이가 많이 난다



### 사용법

Gradle 기반으로 작성

- 이 두가지를 같이 쓴다는게 쉬운게 아님...
- lombok의 버전과 mapstruct의 버전 호환 자세히 알아봐야할 것
  - lombok의 버전과 mapstruct간의 버전 충돌은 해결했지만 swagger와의 버전 충돌을 막지 못해 ModelMapper로 넘어간다
  - 아니다...걍 뻘짓함... ApiModelProperty부분에서 제대로 명시 안해줘서 나는 오류였음... 오류문구 자세히 보자

```groovy
dependencies {
    implementation 'org.mapstruct:mapstruct:1.3.1.Final'
    annotationProcessor "org.mapstruct:mapstruct-processor:1.3.1.Final"

    compileOnly 'org.projectlombok:lombok:1.18.12'
    annotationProcessor 'org.projectlombok:lombok:1.18.12'
    // lombok과 같이 사용할 때는 mapstruct annotationProcessor를
    // 반드시 lombok보다 일찍 선언해야됩니다!
}
```



##### Entity

```java
package com.huisam.springstudy.mapstruct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class Order {

    private Long id;

    private String name;

    private String product;

    private Integer price;

    private String address;

    private LocalDateTime orderedTime;
}
```



##### DTO

```java
package com.huisam.springstudy.mapstruct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
class OrderDto {

    private String name;

    private String product;

    private Integer price;

    private String address;

    private String img;

    private LocalDateTime orderedTime;
}
```

<u>DTO에는 id 필드가 없고 img필드가 존재</u>



일반적으로 mapping을 위해서는

**Order -> OrderDto** : id필드 제외, img 필드 추가

**OrderDto -> Order** : id필드 추가, img필드 제외



변환과정에서

**꺼내오는 객체(source)**에는 `Getter`가 있어야 하고

변환해서 **저장하고자 하는 객체(target)**에는 `Builder` 혹은 `Setter`가 있어야 한다



<u>여기서는 **Builder** 를 이용해보자</u>



### OrderMapper

```java
package com.huisam.springstudy.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper // 1
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class); // 2

    @Mapping(target = "id", constant = "0L") // 3
    Order orderDtoToEntity(OrderDto orderDto);

    @Mapping(target = "img", expression = "java(order.getProduct() + \".jpg\")") // 4
    OrderDto orderToDto(Order order);
}
```



### 호출 방법

![페이지네이션 포함 호출방법](https://user-images.githubusercontent.com/41468004/127270201-ea71a777-342d-4732-a7a4-a2efcd5d9d21.png)
