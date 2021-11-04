# Binary Search

> 탐색 범위를 두 부분으로 분할해가며 원하는 값을 찾는 방식

처음부터 끝까지 찾는 선형 탐색보다는 훨씬 빠름

```
Time Complexity
Linear Search : O(N)
Binary Search : O(logN)
```



### 순서

- 정렬이 되어야 한다
- left와 right로 mid값 설정
- mid와 내가 구하고자 하는 값을 비교
- 구할 값이 mid보다 높다면 오른쪽 부분 탐색(left = mid + 1) 낮으면 왼쪽 탐색(right = mid - 1)
- left > right 될때까지 수행



### Code

```java
public static int binarySearch(int[] arr, int target) {
  Arrays.sort(arr);
  int left = 0, right = arr.length - 1;
  
  while(left <= right) {
    int mid = (left + right) >> 1;
    
    if(arr[mid] == target) {
      return arr[mid];
    } else if(arr[mid] < target) {
      left = mid + 1;
    } else if(arr[mid] > target) {
      right = mid - 1;
    }
  }
  
  return -1; //fail
}
```



### Arrays.sort()?

> Java에서 제공하는 배열 정렬 알고리즘

내부적으로 3가지의 Sorting을 사용

- Insertion Sort
- Merge Sort
- Quick Sort



이전에는 각각 따로 사용했지만 지금은 Insertion + Merge, Insertion + Quick 방식으로 사용

Insertion + Merge => Tim Sort

Insertion + Quick => Dual Pivot Quick Sort