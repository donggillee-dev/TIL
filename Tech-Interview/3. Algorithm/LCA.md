# LCA(Least Common Ancestor)

> 최소 공통 조상을 찾는 알고리즘
>
> - 두 정점이 만나는 최초 부모 노드를 찾는 것

<img src="https://user-images.githubusercontent.com/41468004/140512383-61f890be-5929-4960-ba2d-c314616296cb.png" style="zoom:50%;" />

- 위 트리에서
  - LCA(9, 13) -> 2
  - LCA(6, 7) -> 1
  - LCA(14, 3) -> 3
- LCA 알고리즘 구현 방식에는 여러가지가 있지만 DP를 통해 O(logN)으로 푸는 방식이 효과적

```java
private static ArrayList<Integer>[] list = new ArrayList[N + 1];

//노드를 하나씩 탐색하여 인접 노드간의 부모관계 및 해당 노드의 깊이를 명시
//특정 노드의 2 ^ 0 번째 부모를 초기화해주는 과정이다
private static void dfs(int cnt, int node, int[] depths, int[][] parents) {
  depths[node] = cnt;

  for(int next : list.get(node)) {
    if(depths[next] == 0) {
      dfs(cnt + 1, next, depths, parents);
      parents[next][0] = node;
    }
  }
}

//dp로 구현한 lca에서 정말 중요한 함수
//parents[j][i] => j노드의 2^i번째 부모값을 구하는 방법
//parents[j][2] => parents[parents[j][i - 1]][i - 1]
//j 노드의 2^2번쨰 부모는 j 노드의 2^1번째 노드의 2^1번째 부모
//즉 j의 2^i 부모 = j의 2^(i-1) 부모늬 2^(i-1)번째 부모
private static void initParents(int[][] parents) {
  for(int i = 1; i < D; i++) {
    for(int j = 1; j <= N; j++) {
      parents[j][i] = parents[parents[j][i - 1]][i - 1];
    }
  }
}

//lca 알고리즘
private static int lca(int a, int b, int[] depths, int[][] parents) {
  if(depths[a] < depths[b]) { //한쪽으로 처리하기 위한 로직, b를 무조건 더 깊은애로
    int tmp = a;
    a = b;
    b = tmp;
  }

  for(int i = D - 1; i >= 0; i--) { //O(logN), 두 노드의 깊이 차이를 2씩 반감
    if(Math.pow(2, i) <= depths[a] - depths[b]) {
      a = parents[a][i];
    }
  }

  if(a == b) return a; //두 노드가 동일해졌다면 리턴

  for(int i = D - 1; i >= 0; i--) { //깊이가 동등한 상태에서 부모를 하나씩 올려본다 ( 2 배수씩 )
    if(parents[a][i] != parents[b][i]) {
      a = parents[a][i];
      b = parents[b][i];
    }
  }

  return parents[a][0]; //위 for문이 두 노드의 동일한 부모를 찾은 경우 더이상 변경 안하니까 a의 1번쨰 부모를 리턴해주어야함
}
```

