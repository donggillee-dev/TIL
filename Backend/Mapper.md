# Mapper

- Spring MVC 패턴에서 중요한 Mapper에 대해서 알아보자
- 우선 왜 쓰는지, 왜 필요한지에 대해서 먼저 학습 후 개념 및 로직 이해



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



### 이제 프로젝트에 한번 적용해보자!!

#### DTO

```java
package com.teamgu.api.dto.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@ApiModel("NoticeListResponse")
public class NoticeListResDto extends BaseResDto {
    @ApiModelProperty(name = "공지사항 번호", example = "")
    int id;

    @ApiModelProperty(name = "공지사항 제목", example = "")
    String title;

    @ApiModelProperty(name = "공지사항 생성일", example = "")
    String date;
}

```



#### Entity

```java
package com.teamgu.database.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class Notice extends BaseEntity {

	Date createDate;
	Date mdifyDate;
	@Column(length = 80)
	String title;
	@Column(length = 2000)
	String content;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "writer_id")
	private User user;
	

	@OneToMany(mappedBy="notice")
	private List<NoticeFile> noticeFiles = new ArrayList<>();
	
}

```



#### Mapper

```java
package com.teamgu.mapper;

import com.teamgu.api.dto.res.NoticeListResDto;
import com.teamgu.database.entity.Notice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NoticeListMapper {
    NoticeListMapper INSTANCE = Mappers.getMapper(NoticeListMapper.class);

    @Mapping(source = "createDate", target = "date", dateFormat = "yyyy.mm.dd")
    NoticeListResDto noticeToDto(Notice notice);
}

```

- 계속 날짜가 변환이 안돼서 막막했는데
- 단순히 dto쪽의 날짜를 String으로 변경해주면 해결됨
  - util.date -> String (Formatting)



### 호출 방법

![페이지네이션 포함 호출방법](https://user-images.githubusercontent.com/41468004/127270201-ea71a777-342d-4732-a7a4-a2efcd5d9d21.png)





## JPA 순환참조 오류

- 오류 상황

  #### Notice Entity

  ```java
  package com.teamgu.database.entity;
  
  import java.util.ArrayList;
  import java.util.Date;
  import java.util.List;
  
  import javax.persistence.Column;
  import javax.persistence.Entity;
  import javax.persistence.FetchType;
  import javax.persistence.JoinColumn;
  import javax.persistence.ManyToOne;
  import javax.persistence.OneToMany;
  
  import lombok.Getter;
  import lombok.Setter;
  @Entity
  @Getter
  @Setter
  public class Notice extends BaseEntity {
  
  	Date createDate;
  	Date mdifyDate;
  	@Column(length = 80)
  	String title;
  	@Column(length = 2000)
  	String content;
  	
  	@ManyToOne(fetch = FetchType.LAZY)
  	@JoinColumn(name= "writer_id")
  	private User user;
  	
  
  	@OneToMany(mappedBy="notice")
  	private List<NoticeFile> noticeFiles = new ArrayList<>();
  	
  }
  ```

  

  #### NoticeFile Entity

  ```java
  package com.teamgu.database.entity;
  
  import java.sql.Date;
  
  import javax.persistence.Column;
  import javax.persistence.Entity;
  import javax.persistence.FetchType;
  import javax.persistence.ManyToOne;
  
  import lombok.Getter;
  import lombok.Setter;
  
  @Entity
  @Getter
  @Setter
  public class NoticeFile extends BaseEntity {
  	
  	@Column(length = 120)
  	String originalName;
  	@Column(length = 45)
  	String name;
  	@Column(length = 45)
  	String extension;
  	Date registDate;
  	
  	@ManyToOne(fetch = FetchType.LAZY)
  	private Notice notice;
  }
  
  ```

  우선 Notice와 NoticeFile은 1 : N 관계이다
  특정 게시물에 대해서 가져올때 Dto를 NoticeDetailDto를 선언하여 반환하게끔 함

  

  ### 계속 순환참조가 일어나길래 이유를 조사해봄

  - 양방향 참조가 문제가 된다
  - 응답에 양방향 참조가 되어있는 Entity를 보내면 JsonSerializer가 toString()을 호출하는 시 property를 매핑하는 과정에서 무한 참조가 일어남
  - 해결방법은
    - **<u>DTO</u>**를 사용하여 엔티티 리턴 x
    - `@JsonIgnore`, `@JsonManagedReference`, `@JsonBackReference` 이 세가지 이용
      - **@JsonIgnore** : 이 어노테이션 붙이면 json 데이터에 해당 프로퍼티는 null로 들어가짐, 즉 데이터에 아예 포함안됨 ( 참조를 막아버림 )
      - **@JsonManagedReference** : 부모 클래스에 붙이면 되는 어노테이션
      - **@JsonBackReference** : 자식 클래스측에 붙이면 되는 어노테이션
    - Entity 매핑 관계 재설정

  

  ### DTO 사용하는 방법 채택

  - 이 방법으로 해도 순환참조 오류가 나길래 멘붕,,,

  - 알고보니 너무 멍청했던 짓 => Notice는 DTO로 잘 변환해주고 그 안에 있는 NoticeFile은 매핑을 안해줬던것
    최종적으로 완성된 코드는 아래와 같다

    #### NoticeDetailResDto

    ```java
    package com.teamgu.api.dto.res;
    
    import io.swagger.annotations.ApiModel;
    import io.swagger.annotations.ApiModelProperty;
    import lombok.Getter;
    import lombok.Setter;
    
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;
    
    @Getter
    @Setter
    @ApiModel("NoticeDetailResponse")
    public class NoticeDetailResDto extends BaseResDto {
        @ApiModelProperty(name = "공지사항 생성일", example = "")
        Date createDate;
    
        @ApiModelProperty(name = "공지사항 수정일", example = "")
        Date mdfyDate;
    
        @ApiModelProperty(name = "공지사항 제목", example = "")
        String title;
    
        @ApiModelProperty(name = "공지사항 내용", example = "")
        String content;
    
        @ApiModelProperty(name = "공지사항 첨부파일 목록", example = "")
        private List<NoticeFileResDto> noticeFiles = new ArrayList<>();
    }
    
    ```

    

    #### NoticeFileResDto

    ```java
    package com.teamgu.api.dto.res;
    
    import io.swagger.annotations.ApiModel;
    import io.swagger.annotations.ApiModelProperty;
    import lombok.Getter;
    import lombok.Setter;
    
    @Getter
    @Setter
    @ApiModel("NoticeFileResponse")
    public class NoticeFileResDto extends BaseResDto {
        @ApiModelProperty(name = "파일 원본명", example = "")
        String originalName;
    
        @ApiModelProperty(name = "암호화된 파일명", example = "")
        String name;
    
        @ApiModelProperty(name = "파일 화장자", example = "")
        String extension;
    
        @ApiModelProperty(name = "파일 등록일", example = "")
        String date;
    }
    
    ```

    

    #### NoticeDetailMapper

    ```java
    package com.teamgu.mapper;
    
    import com.teamgu.api.dto.res.NoticeDetailResDto;
    import com.teamgu.database.entity.Notice;
    import org.mapstruct.Mapper;
    import org.mapstruct.factory.Mappers;
    
    @Mapper(uses = NoticeFileMapper.class)
    public interface NoticeDetailMapper {
        NoticeDetailMapper INSTANCE = Mappers.getMapper(NoticeDetailMapper.class);
    
        NoticeDetailResDto noticeToDto(Notice notice);
    }
    
    ```

    

    #### NoticeFileMapper

    ```java
    package com.teamgu.mapper;
    
    import com.teamgu.api.dto.res.NoticeFileResDto;
    import com.teamgu.database.entity.NoticeFile;
    import org.mapstruct.Mapper;
    import org.mapstruct.Mapping;
    import org.mapstruct.Named;
    import org.mapstruct.factory.Mappers;
    
    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.Date;
    
    @Mapper
    public interface NoticeFileMapper {
        NoticeDetailMapper INSTANCE = Mappers.getMapper(NoticeDetailMapper.class);
    
      	@Mapping(target = "statusCode", ignore = true) //매핑 무시하고싶은 속성에 대해 처리
        @Mapping(source = "registDate", target = "date", dateFormat = "yyyy.mm.dd")
        NoticeFileResDto noticeFileToDto(NoticeFile noticeFile);
    
        @Mapping(source = "date", target = "registDate", qualifiedByName = "stringToDate")
        NoticeFile dtoToNoticeFile(NoticeFileResDto noticeFileResDto);
    
        @Named("stringToDate")
        static Date asDate(String date) {
            try {
                return date != null ? new SimpleDateFormat(" yyyy.mm.dd")
                        .parse(date) : null;
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
    
        }
    }
    
    ```

    