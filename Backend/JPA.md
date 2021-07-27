# JPA



## JPA 페이징

- JPA를 이용하여 데이터를 페이징할 수 있는 방법
- DB에 저장된 Entity들을 페이지로 나누는 것
- ex) 프론트에서 DB에 있는 책을 5권씩 분류해서 두번쨰 파트를 줘~ 이런식으로 요청 가능



### 그럼 어떻게?

- repository의 findAll메서드의 파라미터에 Pageable 또는 Pageable의 구현체인 PageRequest를 넣어주면 된다.

- Pageable을 컨트롤러에서 받는 것이 더욱 효과적
  - 그 외의 방식은 뭔가 다른 정렬 조건을 붙이고 싶다거나? 이럴떄 메소드를 추가해야함
  - 이 방식은 클라쪽에서 원하는 정렬방식, 몇 번 페이지, 페이지 사이즈라던지 다 세팅 가능
- Page를 클라이언트에 반환해주는 것이 좋아보인다
  - 클라이언트가 데이터를 전달받기보다는 Page를 전달받아 총 페이지의 수가 얼마가 되는지 마지막 페이지가 어딘지 등등 클라쪽에 다양한 정보 제공

![Jpa Pagination](https://user-images.githubusercontent.com/41468004/127185606-10fc336f-fe02-4eec-8adc-5cb0fd4780ea.png)



### 실제 코드

```

```

