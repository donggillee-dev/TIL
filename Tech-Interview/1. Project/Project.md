# Project 관련 복기

Node.js

비동기/동기 처리

- https://meetup.toast.com/posts/73

Node.js의 메모리 관리 동작 방식

- https://codingjuny.tistory.com/58

- https://hyunjun19.github.io/2018/02/19/node-js-at-scale-node-js-garbage-collection/

Gradle

- 이거 Git에 정리해놓음

JPA vs QueryDSL

- JPA에서 제공해주는 메서드로 처리 가능한 쿼리의 경우 JPA로 처리
- 다중 조인, fetch(다중 컬럼 조회) 같은 경우 Custom 인터페이스를 정의 -> 해당 인터페이스를 상속받아 Qclass를 통해 로직을 구현하였습니다



QueryDSL vs JPQL

- ERD가 상당히 복잡하였기에 다중조인이 필수적인 상황
- QueryDSL을 이용하기에는 복잡한 이슈들 많이 발생
  - 우선 사용법에 대한 숙지가 완벽하지 못했다
  - 프로젝트 기간이 얼마 남지 않았다
  - 즉 비교적 쉬운 쿼리들은 QueryDSL로 처리하고 복잡한 다중조인(5중 조인 ㅎㄷㄷ...)들의 경우에는 JPQL로 쿼리를 짜자
    - 여기서 또 핵심적인 문제가 발생하는데 트랜잭션을 반환해주지 않아 Connection leak발생

Jira

- 이거 Git에 정리해놓음

Kurento 동작방식

- 이거 Git에 정리해놓음

Docker + Jenkins

- 정리했지만 더 정리해야할듯?

Redis

- 정리해야함

JWT

- 이거 Git에 정리해놓음

MapStruct

- 이거 Git에 정리해놓음

쿼리 튜닝방식(OR보다는 AND, Having보다는 Where에서 처리)

- https://cornswrold.tistory.com/87