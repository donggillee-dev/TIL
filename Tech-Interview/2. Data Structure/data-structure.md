# Data Structure

* [Data Structure?](#data-structure?)
* [Sequential Access & Random Access](#sequential-access-&-random-access)
* [ArrayList][#arraylist]
* [LinkedList](#linkedlist)
* [ArrayList vs LinkedList](#arraylist-vs-linkedlist)

- [Stack](#stack)
- [Queue](#queue)
- [Tree](#tree)

- [Binary Tree](#binary-tree)

- [Binary Search Tree](#binary-search-tree)
- [Heap](#heap)
- [Trie](#trie)
- [RBT](#rbt)
- [Graph](#graph)

- [Hash](#hash)



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



## Sequential Access & Random Access

### Sequential Access(순차 접근)

> 순차 접근, 정해진 원소 그룹을 순차적으로 접근해나가는 방식

- 한 가지 특정 순서로만 포함된 값을 방문할 수 있는 경우 순차 접근 사용

- 대표적인 예로 LinkedList 존재, 인덱싱하는데 O(N)의 복잡도



### Random Access(비순차 접근)

> 비순차 접근, 정해진 원소 그룹을 비순차적으로 접근 가능

- 원소 그룹내에 있는 원소의 주소값을 알고 있다면 모든 원소들에 대해 동일한 시간에 접근할 수 있는 방식
- 어떤 원소에 접근하기 위해 첨부터 순차적으로 접근해야 하는 Sequential Access와 대비되는 방식



## Array

`동일한 자료형의 데이터가 선형으로 나열된 자료구조`

- 연속된 메모리 공간에 순차적으로 저장
- 정의와 동시에 크기를 할당, 크기 변경 불가능
- Access : O(1)
  - 배열은 찾고자 하는 원소의 인덱스 값을 알고 있으면 O(1)에 해당 원소로 접근할 수 있음
- Search : O(n)
  - 순차 탐색의 경우, 배열에서 찾고자 하는 원소가 가장 끝에 위치하고 있을 시 => Wost-case time complexity O(n)
- Insertion/Deletion : O(n)
  - 원소를 삽입, 삭제하는 과정에서 많은 원소들을 이동시켜야함
  - 만약 첫 위치의 원소를 삭제하거나 해당 위치에 원소를 삽입한다면 그 뒤의 모든 원소들을 왼/오로 shift해야하므로 => Wost-case time complexity O(n)



## ArrayList

`크기가 가변적인 선형리스트`

> 제네릭(generic)
>
> 데이터의 타입을 일반화하는 것, 클래스나 메소드에서 사용할 내부 데이터 타입을 컴파일시에 미리 지정하는 방법
>
> - 타입의 안정성 : 의도하지 않은 타입의 객체가 저장되는 것을 막음
> - 불필요한 형변환을 줄여 코드 간결함
> - 거의 모든 Java Collection에서 사용된다

- 순서를 유지, 인덱스로 원소들을 관리 => 배열과 상당히 유사 

- ArrayList는 가변적이며 추가, 변경, 삭제 가능한 내부 메소드 존재

  - 가변적으로 늘어난다는 느낌보다는 새로이 더 큰 배열을 선언하여 값을 옮긴다라고 생각

- Object Type만 담을 수 있음(Primitive Type은 불가능)

  ![ArrayList](https://user-images.githubusercontent.com/41468004/126501992-25e4c316-c59d-4870-8677-b25f7fb3b76a.png)



## LinkedList

![](https://user-images.githubusercontent.com/55429912/120528055-fb099a80-c415-11eb-8795-07c1ef2c2075.png)

`연속적이지 않은 메모리 위치에 저장되는 선형 자료구조`

- 자료가 추가될 때마다 메모리 할당, 해당 자료는 링크로 연결된다.
- 자료의 물리적 위치와 논리적 위치가 다를 수 있다 
  - 그러므로 항상 처음 데이터부터 찾는다.
- 크기가 유동적이어서 크기를 미리 몰라도 되며 데이터의 삽입/삭제가 용이하다
- 빈 엘리먼트를 허용하지 않으며(null은 가능) , 중복 엘리먼트 허용



### ArrayList vs LinkedList

|          연산           |                     ArrayList                      |             LinkedList             |
| :---------------------: | :------------------------------------------------: | :--------------------------------: |
|         Access          |                        O(1)                        |                O(n)                |
|         Search          | O(n) - linear search<br />O(log n) - binary search |                O(n)                |
| **Insertion** at first  |                        O(n)                        |                O(1)                |
|  **Insertion** at last  |                        O(1)                        |                O(n)                |
| **Insertion** at middle |                        O(n)                        |                O(n)                |
|  **Deletion** at first  |                        O(n)                        |                O(1)                |
|  **Deletion** at last   |                        O(1)                        |                O(n)                |
| **Deletion** at middle  |      O(1)의 접근 속도, O(n)의 shift 연산 시간      | O(n)의 접근 속도, O(1)의 삭제 시간 |



## Stack

> LIFO(Last-In-First-Out)형태의 선형 자료구조

- 콜스택, 문자열 역순 출력 등에 사용
  - 콜스택 : 컴퓨터 프로그래밍에서 현재 실행 중인 서브루틴에 대한 정보를 저장하는 스택 자료구조
  - 서브루틴이 종료된 이후에는 호출 시점으로 제어를 넘겨야함. 때문에 서브루틴 호출시에는 콜스택에 호출 지점의 주소를 집어 넣는다
- DFS를 재귀적으로 구현하면 왜 Stack을 안써도 되는지??
  - 컴퓨터 내부 구조적으로 함수의 호출 순서가 Stack으로 되어있기에 따로 Stack을 쓰지 않아도 된다.

- Stack 두개로 Queue 구현

  ```java
  class Queue<T> {
      Stack<T> inStack = new Stack<>();
      Stack<T> outStack = new Stack<>();
  
      public void push(T elem) {
          this.inStack.push(elem);
      }
  
      public T pop() {
          if(outStack.isEmpty()) {
              while(!inStack.isEmpty()) {
                  outStack.add(inStack.pop());
              }
          }
  
          return outStack.pop();
      }
  }
  ```

  

## Queue

> FIFO(First-In-First-Out)형태의 선형 자료구조

- Java Collection에서 Queue는 인터페이스임, 이걸 구현한 Priority Queue나 , LinkedList, ArrayDeque로 해서 사용 가능
- BFS, OS에서 대기큐 등에 사용 가능



## Tree

> 계층적 관계를 표현하는 비선형 자료구조

![](https://user-images.githubusercontent.com/55429912/120596118-4eafce80-c47e-11eb-9f0c-5bc33705f0d0.png)

- 사이클이 없는 그래프의 한 종류(사이클이 존재한다면 그래프)
- 루트 노드에서 한 노드로 가는 경로는 유일한 경로 뿐
- 노드의 개수가 N개면 간선의 개수는 N - 1개

#### 트리 순회 방식

1. 전위 순회(pre-order)

   - 각 루트를 순차적으로 먼저 방문

   - (왼쪽 자식 → 루트 → 오른쪽 자식)

     > 1 → 2 → 4 → 8 → 9 → 5 → 10 → 11 → 3 → 6 → 13 → 7 → 14

     

2. 중위 순회(in-order)

   - 왼쪽 하위 트리를 먼저 방문한 후 루트를 방문하는 방식

   - (왼쪽 자식 -> 루트 -> 오른쪽 자식)

     > 8 → 4 → 9 → 2 → 10 → 5 → 11 → 1 → 6 → 13 → 3 → 14 → 7

     

3. 후위 순회(post-order)

   - 왼쪽 하위 트리부터 모든 하위를 다 방문한 후 루트를 방문하는 방식

   - (왼쪽 자식 → 오른쪽 자식 → 루트)

     > 8 → 9 → 4 → 10 → 11 → 5 → 2 → 13 → 6 → 14 → 7 → 3 → 1

4. 레벨 순회(level-order)

   - 루트로부터 level 별로 방문하는 방식

     > 1 → 2 → 3 → 4 → 5 → 6 → 7 → 8 → 9 → 10 → 11 → 13 → 14



## Binary Tree

> 루트 노드를 중심으로 두 개의 서브 트리로 나뉘어 지는 형태이며, 두 서브 트리도 Binary Tree 형태이어야함

![](https://camo.githubusercontent.com/78163ac30efb3626bee26e7269938859caec33b6def337ff7657046122bb78ff/68747470733a2f2f7777772e6765656b73666f726765656b732e6f72672f77702d636f6e74656e742f75706c6f6164732f62696e6172792d747265652d746f2d444c4c2e706e67)

- 공집합도 Binary Tree에 포함된다
- 각 층별로 숫자를 매기는데 이를 `level`이라고 하며 0부터 시작 (root -> 0), 최고 레벨을 가리켜 `height` 라고 한다



### Perfect Binary Tree(포화 이진 트리)

- 모든 레벨이 꽉찬 이진 트리

### Complete Binary Tree(완전 이진 트리)

- 위에서 아래로, 왼쪽에서 오른쪽으로 순차대로 채워진 이진 트리

### Full Binary Tree(정 이진 트리)

- 모든 노드가 0개 혹은 2개의 자식 노드만을 갖는 이진 트리



## Binary Search Tree

> Binary Search + LinkedList
>
> 효율적인 탐색과 저장을 위한 자료구조

![](https://user-images.githubusercontent.com/55429912/120593467-86b51280-c47a-11eb-97ec-06dc0179e148.png)

Binary Search

- O(log N)이지만 삽입, 삭제 불가능

LinkedList

- 삽입, 삭제 → O(1) but, 탐색 → O(N)



위 두가지의 장점을 결합하여 만듬(효율적인 탐색 능력 + 삽입 삭제 가능)

- 각 노드의 자식이 2개 이하
- 각 노드의 왼쪽 자식은 부모보다 작고 오른쪽 자식은 부모보다 크다
- 중복된 노드가 없어야함
  - 검색 목적의 자료구조인데 중복을 넣어 시간복잡도를 늘릴 필요 없다
  - 이는 각 노드에 `count`를 통해서 처리하는 것이 더욱 효율적



이진탐색 트리의 순회는 `중위 순회(in-order)`로 정렬된 순서를 읽을 수 있다.

```java
public void inorder(Node p) {
	if(p != null) {
    inorder(p.leftChild);
    System.out.println(p.elem);
    inorder(p.rightChild);
  }
}
```



시간 복잡도

- 균등 트리 : 노드의 개수가 N개일 때 O(log N)
- 편향 트리 : 노드의 개수가 N개일 때 O(N)
  - 이를 위한 개선된 트리가 `ALV, RedBlack Tree`

> 삽입, 검색, 삭제의 시간복잡도의 트리의 Depth에 비례한다



삭제의 3가지 Case

1. 자식이 없는 leaf 노드일 때 → 그냥 삭제
2. 자식이 1개인 leaf 노드일 때 → 지워진 노드에 자식 올리기
3. 자식이 2개인 leaf 노드일 때 → 왼쪽 자식에서 가장 큰값 or 오른쪽 자식에서 가장 작은 값 올리기



## Heap

> 완전 이진 트리의 일종으로 여러 값들 중에 최소, 최대값을 빠르게 찾기 위한 자료구조
>
> 우선순위 큐를 위한 자료구조라고도 한다

![](https://user-images.githubusercontent.com/55429912/120530590-aa477100-c418-11eb-9da6-d12bcd873b58.png)

검색 : O(1)

삽입 : O(log N)

삭제 : O(log N)

힙 트리는 중복된 값을 허용한다(Binary Search Tree X)

- 배열을 기반으로 한 Complete Binary Tree
  - 왼쪽 자식 노드 idx : 현재 노드 idx * 2
  - 오른쪽 자식 노드 idx : 현재 노드 idx * 2 + 1
  - idx를 이용한 Random Access 가능

- 최소힙, 최대힙이 존재
  - 최대힙 : 부모 노드의 값 >= 자식 노드의 값
  - 최소힙 : 부모 노드의 값 <= 자식 노드의 값
  - 루트 노드가 이미 최소, 최대이기에 최대, 최소값 찾는데 O(1) 소요

**삽입**

1. 새로운 요소가 들어오면 (힙 크기)++, 마지막 요소에 삽입
2. 새로운 마지막 노드를 부모와 계속 비교해가며 swap



**삭제**

1. 최대/최소는 루트이기에 루트 삭제
2. 맨 마지막 원소를 루트로 올림, (힙 크기)--
3. 자식과 비교해가며 swap
   1. 왼쪽 자식, 오른쪽 자식보다 크거나 작다면 end
   2. 왼쪽 노드가 오른쪽 노드보다 크거나 작다면 왼쪽과 swap
   3. 오른쪽 노드가 왼쪽 노드보다 크거나 작다면 오른쪽과 swap



## Trie

> 문자열에서 검색을 빠르게 도와주는 자료구조

![](https://camo.githubusercontent.com/7024b55e64516062054e9b5bccf35dc72d5e7a4cca88c8f57810804b955cb849/68747470733a2f2f74312e6461756d63646e2e6e65742f6366696c652f746973746f72792f323433353445333335383333413743463137)

```
정수형에서 이진탐색트리를 이용하면 시간복잡도 O(log)
하지만 문자열에 적용하면 문자열 최대길이가 L일때 O(L * logN)

트라이를 이용하게 된다면 → O(M)으로 문자열 검색이 가능
```

**시간 복잡도**

제일 긴 문자열의 길이 : L

문자열의 개수 : N

- 생성시 시간 복잡도 : O(N * L)
  - 모든 문자열들을 넣어야하므로 `N`개에 대해 트라이 자료구조에 넣는건 가장 긴 문자열 `L`만큼 걸리므로 `O(N * L)`
- 탐색 : O(L)
  - 트리를 최대 깊이까지 들어간다고 하더라도 가장 긴 문자열의 길이만큼만 탐색하면 되므로 O(L)



## RBT

![](https://user-images.githubusercontent.com/55429912/120613287-0d74ea00-c491-11eb-8df0-0a6fb437aaff.png)

> 레드 블랙트리라고 불린다
>
> 자가 균형 BST로써, 동일한 노드의 개수일 때, Depth를 최소화하여 시간 복잡도를 줄이는 트리 형식의 자료구조
>
> BST에서 편향 트리의 경우를 배제할 수 있음

**RBT의 조건**

1. 각 노드는 `Red `혹은` Black`의 색을 갖는다
2. **Root Property** : 루트노드의 색깔은 `Black`이다
3. **External Property** : 모든 **External Node**들은` Black`이다
4. **Internal Property** : `Red`노드의 자식은 `Black`이다
   - `No Double Red`(Red 노드가 연속으로 나올 수 없음)
5. **Depth Property** : 모드 리프노드에서 **Black Depth**는 동일
   - 리프노드에서 루트노드까지 가는 경로에서 만나는 `Black`노드의 개수는 동일



**삽입**

1. BST의 특성을 유지하며 삽입, 루트노드는 `Black`

2. 삽입된 노드의 색깔은 `Red`로 지정

   - Black Height를 최소로 하기 위함

3. RBT의 조건에 맞도록 노드들 설정

   ![](https://user-images.githubusercontent.com/55429912/121356383-bdec5d80-c96b-11eb-9462-c8bea12d4fc0.png)
   - 연속된 Red 노드가 나오게 되었을시 부모의 사촌(w)의 색상에 따라 두 가지로 분기
     - w가 Black인 경우 Restructuring
     - w가 Red인 경우 Recoloring
   - Restructuring
     - **나(z)와 내 부모(v), 내 부모의 부모(Grand Parent)를 오름차순으로 정렬**
     - **무조건 가운데 있는 값을 부모로 만들고 나머지 둘을 자식으로 만든다.**
     - **올라간 가운데 있는 값을 검정(Black)으로 만들고 그 두자식들을 빨강(Red)로 만든다.** 
     - Restructuring 자체는 O(1)에 끝남, 하지만 Restructuring자체가 Insertion 이후에 일어나므로 O(logN)
   - Recoloring
     - **현재 삽입된 나(z)의 부모(v)와 형제(w)를 Black으로 하고 Grand Parent는 Red로 한다**
       - 이 경우 Grand Parent가 루트노드라면 Black으로 됨
       - 하지만 서브트리의 경우 Grand Parent의 부모 색상을 생각해줘야하는데 Double Red의 경우에는 propagation 발생 => **최악의 경우 Root Node까지감, 그래도 O(logN)**

   - 즉, RBT의 시간 복잡도는 O(log N)



## Graph

> 노드와 그 노드를 연결하는 간선을 하나로 모아 놓은 자료구조

- 연결되어 있는 객체 간의 관계를 표현할 수 있는 자료구조
- 네트워크 모델
- 그래프는 순환 or 비순환
- 그래프에는 방향, 무방향 존재



**구현 방식**

1. 인접 행렬(adjacent matrix)

`정방 행렬을 사용하는 방법`

공간 복잡도 : O(V ^ 2)

그래프에 간선이 많이 존재한 밀접 그래프에서 사용

장점:

- 노드간의 연결 관계를 O(1)로 알 수 있음

단점:

- 어떤 노드에 인접한 노드들을 찾기 위해서는 모든 노드를 전부 순회
- 그래프에 존재하는 모든 간선의 수를 순회하려면 O(V ^ 2) 소요
  - 인접 행렬 전체를 탐색해야하기 때문



2. 인접 리스트(adjacent list)

`연결 리스트를 사용하는 방식`

공간 복잡도 : O(V + E)

- 간선의 개수에 비례하는 메모리만 차지
- 그래프내에 간선이 적은 희소 그래프에서 사용

장점:

- 특정 노드의 인접한 노드들을 쉽게 찾을 수 있음
- 그래프에 존재하는 모든 간선의 수를 O(V + E)만에 알 수 있음

단점:

- 특정 노드간의 연결 여부를 확인하기 위해서는 O(V)만큼 탐색해야함

**그래프와 노드의 차이**

![](https://user-images.githubusercontent.com/55429912/121016900-aaa88900-c7d7-11eb-97ae-1cb30aeddd29.png)



## Hash