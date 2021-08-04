# Data Structure

* [Data Structure?](#data-structure?)
* [Array vs LinkedList](#array-vs-linkedlist)
* [ArrayList vs LinkedList][#arraylist-vs-linkedlist]
* [Stack vs Queue](#stack-vs-queue)
* [Tree](#tree)



## Data Structure?

- 프로그램에서 사용할 많은 데이터를 메모리 상에서 관리하는 여러 구현방법들
- 효율적인 자료구조가 성능 좋은 알고리즘의 기반이 된다
- 자료의 효율적인 관리는 프로그램의 수행속도와 밀접한 관계가 있다
- 여러 자료구조 중 구현하려는 프로그램의 목적에 맞는 최적의 자료구조를 활용할 줄 알아야 하므로 그 이해가 중요하다



### 선형 자료구조

- 하나의 자료 뒤에 하나의 자료가 존재하는 것
- 자료들 간의 앞뒤 관계가 1 : 1
- 배열, 리스트, 더 나아가 스택, 큐도 해당된다



### 비선형 자료구조

- 하나의 자료 뒤에 여러개의 자료가 존재할 수 있는 것
- 자료들 간의 앞뒤 관계가 1 : n , n : n



## Array vs LinkedList

### Array

- 가장 기본적인 자료구조
- 선형으로 자료를 관리한다.
- 정해진 크기의 메모리를 먼저 할당받아 사용하고, 자료의 물리적 위치와 논리적 위치가 동일하다
- 할당받은 크기에 대해서 변경 불가능, 다른 크기고 재할당해야함
- Access : O(1)
  - 배열은 찾고자 하는 원소의 인덱스 값을 알고 있으면 O(1)에 해당 원소로 접근할 수 있음
  - Random Access 가능
    - Random Access란?
      - 데이터를 저장하는 블록을 한 번에 여러 개 접근하는게 아니라 한 번에 하나의 블록만을 접근하는 싱글 블록 I/O 방식
      - 비순차적 접근이라고도 한다 => 랜덤하게 특정 하나의 원소에 접근 가능?
- Search : O(n)
  - 순차 탐색의 경우, 배열에서 찾고자 하는 원소가 가장 끝에 위치하고 있을 시 => Wost-case time complexity O(n)
- Insertion/Deletion : O(n)
  - 원소를 삽입, 삭제하는 과정에서 많은 원소들을 이동시켜야함
  - 만약 첫 위치의 원소를 삭제하거나 해당 위치에 원소를 삽입한다면 그 뒤의 모든 원소들을 왼/오로 shift해야하므로 => Wost-case time complexity O(n)



### LinkedList

- 선형으로 자료를 관리한다.
- 자료가 추가될 때마다 메모리 할당, 해당 자료는 링크로 연결된다.
- 자료의 물리적 위치와 논리적 위치가 다를 수 있다 
  - 그러므로 항상 처음 데이터부터 찾는다.
- 크기가 유동적이어서 크기를 미리 몰라도 되며 데이터의 삽입/삭제가 용이하다
- 빈 엘리먼트를 허용하지 않으며(null은 가능) , 중복 엘리먼트 허용
- add()
  - 끝에 데이터가 추가되므로 O(1)
- add(index, element)
  - 특정 인덱스에 값을 추가해야하므로 O(n)
- get()
  - 원소를 하나씩 탐색해야하므로 O(n)
- remove(element)
  - 해당 원소를 알고 있다면 해당 원소의 포인터만 바뀌면 되므로 O(1)
- remove(index)
  - 특정 인덱스에 접근하려면 타고타고 가야함 O(n)
- contains()
  - 탐색해야하므로 O(n)



### Array vs LinkedList

![Array vs LinkedList](https://user-images.githubusercontent.com/41468004/126497627-be48a699-a0cb-4ff1-915b-41c933f74c04.png)



## ArrayList vs LinkedList

- List 인터페이스의 구현체(Stack, Vector, ArrayList, LinkedList)가 있는데 그 중 가장 많이 쓰이는 두개



### ArrayList?

- 중복을 허용, 순서를 유지, 인덱스로 원소들을 관리 => 배열과 상당히 유사

- 배열은 크기가 고정 but ArrayList는 클래스이기에 배열을 추가, 삭제 가능한 메소드들 존재

- 하지만 원소의 추가로 인해 배열의 크기가 늘어나는 것이 아니라 `용량이 꽉 찼을떄` 더큰 용량의 배열을 만들어 옮기는 작업을 수행

  ![ArrayList](https://user-images.githubusercontent.com/41468004/126501992-25e4c316-c59d-4870-8677-b25f7fb3b76a.png)

- ArrayList 내부를 뜯어보자

  ```java
  public class ArrayList<E> extends AbstractList<E>
          implements List<E>, RandomAccess, Cloneable, java.io.Serializable
  {
      private static final int DEFAULT_CAPACITY = 10;
  
      private static final Object[] EMPTY_ELEMENTDATA = {};
  
      private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
      
      transient Object[] elementData; // non-private to simplify nested class access
  
      private int size;
  
      public ArrayList(int initialCapacity) {
          if (initialCapacity > 0) {
              this.elementData = new Object[initialCapacity];
          } else if (initialCapacity == 0) {
              this.elementData = EMPTY_ELEMENTDATA;
          } else {
              throw new IllegalArgumentException("Illegal Capacity: "+
                                                 initialCapacity);
          }
      }
  
      public ArrayList() {
          this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
      }
  
      public ArrayList(Collection<? extends E> c) {
          Object[] a = c.toArray();
          if ((size = a.length) != 0) {
              if (c.getClass() == ArrayList.class) {
                  elementData = a;
              } else {
                  elementData = Arrays.copyOf(a, size, Object[].class);
              }
          } else {
              // replace with empty array.
              elementData = EMPTY_ELEMENTDATA;
          }
      }
  }
  ```

- 3개의 생성자 존재

  - 매개변수 x
  - initial size를 입력으로 받는 생성자
  - Collection 타입을 매개변수로 받는 생성자

  

- 

  

## Stack vs Queue

### Stack

- 선형 자료구조
- Random Access 불가능
- LIFO -> 나중에 들어간 원소가 가장 먼저 나옴
- 차곡차곡 쌓이는 구조로 먼저 들어간 원소는 Stack의 바닥에 깔리게 됨

- DFS를 재귀적으로 구현하면 왜 Stack을 안써도 되는지??
  - 컴퓨터 내부 구조적으로 함수의 호출 순서가 Stack으로 되어있기에 따로 Stack을 쓰지 않아도 된다.



### Queue

- 선형 자료구조
- Random Access 불가능
- FIFO -> 먼저 들어간 원소가 가장 먼저 나옴
- Java Collection에서 Queue는 인터페이스임, 이걸 구현한 Priority Queue나 , LinkedList로 해서 사용 가능