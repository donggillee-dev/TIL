# DFS & BFS

## DFS(깊이 우선 탐색)

> 시간 복잡도
>
> - 인접 행렬 : O(V^2)
> - 인접 리스트 : O(V + E)

스택 or 재귀를 통해서 구현 가능

- 모든 경로를 방문해야 할 경우 사용에 적합하다

<img src="https://user-images.githubusercontent.com/55429912/119339590-4ca27e80-bccc-11eb-91ac-75c3452161c3.png" style="zoom:50%;" />

```java
private static void dfs(int cur) {
  visited[cur] = true;
  
  for(int next : graph[cur]) {
    if(!visited[next]) {
      dfs(next);
    }
  }
}
```

장점 : 현 경로상의 노드들만 기억하면 되므로 저장 공간의 수요가 비교적 적다

단점 : 얻어지는 해가 최단 경로가 된다는 보장이 없다.

- 목표에 이르는 경로가 다수인 문제에 대해 dfs는 해에 다다르면 탐색을 끝내버리므로 최적의 해가 아닐수도 있다는 것



## BFS(너비 우선 탐색)

> 시간 복잡도
>
> - 인접 행렬 : O(V^2)
> - 인접 리스트 : O(V + E)

큐를 통해서 구현할 수 있다

- 최소 비용(즉, 모든 곳을 탐색하기 보다는 최소 비용이 우선일 때) 적합

![](https://user-images.githubusercontent.com/55429912/119339896-ba4eaa80-bccc-11eb-8deb-2f524851051f.png)

```java
private static void dfs(int node) {
  Queue<Integer> q = new LinkedList<>();
  q.add(node);
  visited[node] = true;
  
  while(!q.isEmpty()) {
    int cur = q.poll();
    
    for(int next : graph[cur]) {
      if(!visited[next]) {
        visited[next] = true;
        q.add(next);
      }
    }
  }
}
```

장점 : 답이 여러개인 경우에도 최적의 해 보장

단점 : 큐를 이용하여 다음 탐색 노드를 저장하기에 노드의 수가 많을수록 불필요한 노드까지 저장

