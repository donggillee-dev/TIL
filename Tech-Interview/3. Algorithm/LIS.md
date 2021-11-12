# LIS(Longest Increasing Subsequence)

> 최장 증가 수열 : 가장 긴 증가하는 부분 수열
>
> 구현 방법은 두 가지가 있다
>
> 1. DP : O(N^2)
> 2. Lower bound : O(NlogN)



## DP를 이용한 LIS

```java
int[] arr = {7 ,2 ,3, 8, 4, 5};
int[] dp = new int[arr.length];
int max = 1;

for(int i = 1; i > arr.legnth; i++) {
  for(int j = i - 1; j >= 0; j--) {
    if(arr[i] > arr[j]) {
      dp[i] = Math.max(dp[i], dp[j] + 1);
      max = Math.max(max, dp[i]);
    }
  }
}

System.out.println(max);
```



## Lower bound를 이용한 LIS

> lower bound?
>
> 어떠한 정렬된 배열 arr에서 특정 값 val을 집어 넣을 때
>
> arr의 정렬된 상태를 유지하면서 val이 삽입될 수 있는 위치중 가장 인덱스가 작은 위치

```java
private static int lowerBound(int end, int val, int[] dp) {
  int start = 0;
  
  while(start < end) {
    int mid = (start + end) >> 1;
    
    if(dp[mid] >= val) {
      end = mid - 1;
    } else {
      start = mid + 1;
    }
  }
  
  return end;
}

private static int lis(int[] arr) {
  int length = arr.length;
  int[] dp = new int[length];
  
  int ans = 0;
  dp[0] = arr[0];
  
  for(int i = 1; i < length; i++) {
    if(dp[ans] < arr[i]) { //lis 배열의 맨 끝값보다 큰 값이라면 넣어주고 길이 + 1
      dp[++ans] = arr[i];
    } else { //그렇지 않은 경우에는 넣어야하는 lower bound를 구한다
      int ii = lowerBound(ans, arr[i], dp);
      
      dp[ii] = arr[i]; //lower bound에 해당 원소값 넣어줌
    }
  }
  
  return ans + 1; //idx가 0부터 시작하기에 길이는 + 1
}
```

