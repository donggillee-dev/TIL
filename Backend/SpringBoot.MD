## 스붓 속성강의

- 제일 기초!! SpringBootApplication 얘만 있으면 일단 application생성 완료

- 추가적으로 AOP하려면 @EnableAspectJAutoProxy 추가

  [URL Handler](https://user-images.githubusercontent.com/41468004/125250641-758cda80-e331-11eb-9bea-1f81c4c8a3ef.png)



### 주요 어노테이션

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