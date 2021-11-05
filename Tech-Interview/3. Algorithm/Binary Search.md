# Binary Search

> 탐색 범위를 두 부분으로 분할해가며 원하는 값 찾는 방식
>
> 시간 복잡도 : O(logN)

![](https://user-images.githubusercontent.com/55429912/119336229-5de98c00-bcc8-11eb-9621-8caa78cf2ca9.png)

## Process

1. 우선 정렬을 해아한다
2. left right로 mid값 설정
3. mid와 내가 구하고자 하는 값을 비교
4. 구할 값이 mid보다 크면 left = mid + 1
5. mid보다 작으면 right = mid - 1
6. left > right일 동안 반복



## Code

```java
private static void binarySearch(int[] arr, int target) {
	int s = 0, e = arr.length - 1;
  
  while(s <= e) {
    int mid = (s + e) >> 1;
    
		if(arr[mid] == target) {
      return mid;
    }
    
    if(arr[mid] > target) {
      e = mid - 1;
    } else {
      s = mid + 1;
    }
  }
  
  return -1;
}
```

