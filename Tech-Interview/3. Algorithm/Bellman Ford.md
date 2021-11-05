# Bellman-Ford(벨만-포드)

> 음의 가중치를 가진 경로가 존재할 때 최단 경로 알고리즘
>
> 시간 복잡도
>
> - O(VE)
> - V - 1번 인접한 모든 간선(E)을 검사하는 과정

다익스트라를 개선한 최단 경로 알고리즘

다익스트라의 경우 음의 싸이클이 존재하면 무한루프를 돌게 된다

- 음의 가중치가 있다고 무조건 무한루프 x
- 음의 싸이클이 존재해야 무한루프



### 거리 값을 갱신하는 과정을 V - 1번으로 제한함으로써 음의 싸이클 유무를 판별할 수 있다

1. 시작 정점 결정
2. 시작 정점부터 다른 정점까지의 거리 값 무한대로 초기화(시작 정점은 다익과 마찬가지로 0)
3. 현재 정점의 인접 정점들을 탐색, 기존의 거리값보다 더 짧은 거리값이 있을 경우 갱신(다익스트라와 동일한 갱신 방식)
4. 3의 과정을 V - 1번 반복
5. 이 이상으로 갱신되는 경우 싸이클 존재

### 왜 V - 1?

- 최단 경로를 구하는 알고리즘에서 특정 정점을 2번 이상 지날 수 없기때문
- V - 1번 돌았다면 모든 정점들에 대해서 최소 경로가 정해졌다는 뜻, V개의 노드에서 가중치 V-1개 선택하면 되기때문
- 그 이상으로 한번 더 선택된다는 것은 싸이클이 존재한다는 것

### Code

```java
boolean bf() {
  for(int i = 1; i <= V; i++) {
    dist[i] = INF;
  }
  
  dist[1] = 0;
  boolean isUpdate = false;
  
  for(int loop = 0; loop < V - 1; loop++) {
    update = false;
    
    for(int cur = 1; cur <= V; cur++) {
      for(Info next : graph[cur]) {
        int nextNode = next.node;
        int nextCost = next.cost;
        
        if(dist[cur] != INF && dist[cur] > d[cur] + nextCost) {
          update = true;
          dist[cur] = d[cur] + nextCost;
        }
      }
    }
    
    //전체 노드에 대해서 아무것도 갱신이 된 게 없다면 굳이 V - 1번 반복할 필요도 없다
    if(!update) return update;
  }
  
  return update;
}
```

장점 : 음의 가중치가 존재하는 곳에서도 사용가능

단점 : 다익스트라에 비해 높은 시간 복잡도(O(VE) > O((V + E)logV))