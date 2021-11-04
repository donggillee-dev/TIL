# Bubble Sort

> N개의 원소를 가진 배열 정렬 시 In-Place sort로 인접한 두 개의 데이터를 비교해 정렬 진행하는 방식

`In-Place Sort : 제자리 정렬, 추가적인 메모리 공간이 거의 안드는 정렬`



### Code

```java
int tmp = 0;

for(int i = 0; i < length; i++) {
	for(int j = 1; j < length - i; j++) {
		if(arr[j - 1] > arr[j]) {
			tmp = arr[j - 1];
			arr[j - 1] = arr[j];
			arr[j] = tmp;
		}
	}
}
```

