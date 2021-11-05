# Dijkstra

> 최단 경로 알고리즘
>
> 기본 로직 : 첫 정점을 기준으로 연결되어 있는 정점들을 추가해가며 각 정점까지의 최단 거리를 갱신해가는 것
>
>
> 시간 복잡도
>
> 모든 노드를 각각 비교하는 선형탐색의 경우 O(V ^ 2)의 시간복잡도를 가진다
>
> 힙구조로 구현시 : O((V + E)logV)

- 각 노드마다 미방문 노드중 출발점 ~ 현재까지 계산된 최단거리를 가지는 노드를 찾는데 O(VlogV)의 시간이 필요
  - 모든 노드 O(V)에 대해
  - 힙에서 추출하는 것이기에 O(logV)
  - 즉 O(VlogV)
- 각 노드마다 이웃한 노드의 최단거리를 갱신하는데 O(ElogV)의 시간이 필요
  - 각 노드마다 모든 이웃을 확인하는 것은 모든 Egde를 확인하는 것과 같기에 O(E)
  - 그리고 매번 힙에서 최단거리를 뽑아서 갱신해야하기에 O(logV)
  - 즉 O(ElogV)

### 우선순위큐를 이용하여 구현

![](https://user-images.githubusercontent.com/55429912/119345274-a0649600-bcd3-11eb-9f1b-d689d592c8ba.png)

```java
private static void dijkstra(int start) {
  for(int i = 1; i <= N; i++) {
    dist[i] = INF;
  }
  
  d[start] = 0;
  PriorityQueue<int[]> pq = new PriorityQueue<>();
  pq.add(new int[]{start, dist[start]});
  
  while(!pq.isEmpty()) {
    int cur = pq.peek()[0];
    int cost = pq.peek()[1];
    
    pq.poll();
    
    //현재 노드의 최소비용이 현재까지 탐색해온 값보다 작은 경우 skip
    if(dist[cur] < cost) continue;
    
    //현재 노드에서 탐색 가능한 애들중에
    for(Info next : graph[cur]) {
      int nextCost = cost + next.cost;
      
      //현재위치에서의 최소비용 + 비용이 그 노드까지의 최소비용보다 작으면 수행
      if(d[next.node] > nextCost) {
        d[next].node] = nextCost;
        pq.add(new int[]{next.node, nextCose});
      }
    }
  }
}
```

장점 : 인터넷 라우팅에서 사용되는 OSPF(Open Shortest Path First) 방식의 프로토콜과 같이 현실에서 최단 경로를 찾기에 적절한 알고리즘

단점 : 음의 가중치 간선이 있으면 사용 불가하다

