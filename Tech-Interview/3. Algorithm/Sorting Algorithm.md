# Sorting Algorithm

- [Sorting Algorithm Time Complexity](sorting-algorithm-time-complexity)

- [Bubble Sort](bubble-sort(버블-정렬))
- [Quick Sort](quick-sort(퀵-정렬))
- [Heap Sort](heap-sort(힙-정렬))
- [Merge Sort](merge-sort(합병-정렬))
- [Selection Sort](selection-sort(선택-정렬))
- [Insertion Sort](insertion-sort(삽입-정렬))

## Sorting Algorithm Time Complexity

|   Algorithm    | Best Case | Average Case | Worst Case |
| :------------: | :-------: | :----------: | :--------: |
|  Bubble Sort   |  O(N^2)   |    O(N^2)    |   O(N^2)   |
|   Quick Sort   | O(NlogN)  |   O(NlogN)   |   O(N^2)   |
|   Heap Sort    | O(NlogN)  |   O(NlogN)   |  O(NlogN)  |
|   Merge Sort   | O(NlogN)  |   O(NlogN)   |  O(NlogN)  |
| Selection Sort |  O(N^2)   |    O(N^2)    |   O(N^2)   |
| Insertion Sort |   O(N)    |    O(N^2)    |   O(N^2)   |



## Bubble Sort(버블 정렬)

`시간 복잡도 : O(N^2)`

N개의 원소를 가진 배열 정렬시 In-Place Sort로 인접한 두 원소를 비교해 정렬해나가는 방식

> In-Place Sort?
>
> - 제자리 정렬
> - 추가적인 메모리 공간이 거의 안드는 정렬

<img src="https://user-images.githubusercontent.com/41468004/140465495-a8dc653c-dca5-4597-89ba-ff9e3dff4203.png" style="zoom:50%;" />

### Code

```java
public void bubbleSort(int length, int[] arr) {
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
}
```



## Quick Sort(퀵 정렬)

`시간 복잡도 : 평균 O(NlogN), 최악 O(N^2)`

[참고](https://st-lab.tistory.com/250)

- 참고 링크의 중간 pivot방식을 정리했다

### Code

```java
private static void swap(int[] arr, int i, int j) {
  int temp = arr[i];
  arr[i] = arr[j];
  arr[j] = temp;
}
private static int partition(int[] arr, int left, int right) {
  int pivot = arr[(left + right) >> 1]; //가운데를 피봇으로 잡는다

  while(left <= right) {
    while(arr[left] < pivot) left++; //기준값보다 작고 큰 애들은 스킵
    while(arr[right] > pivot) right--;

    if(left <= right) { //그 외의 애들은 swap후 left, right 인덱스 이동
      swap(arr, left++, right--);
    }
  }

  return left;
}
private static void qSort(int[] arr, int low, int high) {
  if(low >= high) return;

  int pivot = partition(arr, low, high); //기준점 가져온다
  qSort(arr, low, pivot - 1); //왼쪽과 오른쪽으로 분기
  qSort(arr, pivot, high);
}
```

장점

- 추가적인 메모리 필요없다
- 빠른 시간 복잡도를 가진다

단점

- 최악의 경우(이미 정렬된 경우) 계속 불균형하게 좌우가 나뉘어지기에 시간 복잡도 O(N ^ 2) => 불안정 정렬



## Heap Sort(힙 정렬)

`시간 복잡도: O(NlogN)`

Heap(완전 이진 트리 구조)을 이용한 정렬 구현(최대힙, 최소힙)

![](https://user-images.githubusercontent.com/55429912/119266564-435ed680-bc26-11eb-940c-c00f5a515e8e.png)

### Code

```java
private static void heapSort(int[] arr) {
  int n = arr.length;
  
  //init max heap
  for(int i = n / 2 - 1; i >= 0; i--) {
    heapify(arr, n, i);
  }
  
  //최대값 하나씩 뽑아내는 과정
  for(int i = n - 1; i >= 0; i--) {
    System.out.println(arr[0]);
    arr[0] = -1;
    swap(arr, 0, i);
    heapify(arr, i, 0);
  }
}

private static void heapify(int[] arr, int n, int i) {
  int parent = i;
  int left = parent * 2 + 1;
  int right = parent * 2 + 2;
  
  if(left < n && array[left] > array[parent]) {
    parent = left;
  }
  
  if(right < n && array[right] > array[parent]) {
    parent = right;
  }
  
  //변경이 일어난건 부모 < 자식때문
  if(parent != i) {
    swap(arr, parent, i);
    heapify(arr, n, p);
  }
}
```

#### 첫번쨰 heapify

- 일반 배열을 힙으로 구성하는 역할
- 왜 n / 2 - 1부터 0까지?
  - 부모 노드의 인덱스 기준으로 i * 2 + 1이 왼쬑 자식, i * 2 + 2가 오른쪽 자식



#### 두번째 heapify

- 요소가 하나 제거 => 가장 큰 요소를 뽑은 후 최대 힙 재구성 위함



## Merge Sort(합병 정렬)

`시간 복잡도 : O(NlogN), 공간 복잡도 : O(2N)`

Divide & Conquer를 이용한 방식

![](https://user-images.githubusercontent.com/55429912/119265683-861eaf80-bc22-11eb-81c8-da9a890a187d.png)

### Code

```java
private static void mergeSort(int[] arr, int left, int right) {
	if(left < right) {
    int mid = (left + right) >> 1;
    
    mergeSort(arr, left, mid);
    mergeSort(arr, mid + 1, right);
    merge(arr, left, mid, right);
  }
}

private static void merge(int[] arr, int left, int mid, int right) {
	int idx = left; //정렬된 값을 담을 배열의 idx
  int l = left; //정렬 대상 배열의 왼쪽 파티션 idx
  int r = mid + 1; //정렬 대상 배열의 오른쪽 파티션 idx
  
  //왼쪽 오른쪽에 대해서 하나씩 비교해가며 더 작은 애를 sorted 배열에 담는다
  while(l <= mid && r <= right) {
    if(arr[l] < arr[r]) sorted[idx++] = arr[l++];
    else sorted[idx++] = arr[r++];
  }
  
  //왼쪽 배열 idx가 덜 탐색됨
  //왼쪽 나머지 애들을 모두 sorted에 넣어준다
  if(r >= right) {
    for(; l <= mid; l++, idx++) {
      sorted[idx] = arr[l];
    }
  } else {
    for(; r <= right; r++, idx++) {
      sorted[idx] = arr[r];
    }
  }

  //정렬된 부분은 원본 배열에도 반영시킨다
  for(l = left; l <= right; l++) {
    arr[l] = sorted[l];
  }
}
```

장점 : 안정 정렬(동일한 값에 대해 기존 순서가 유지된다), 빠른 시간 복잡도

단점 : 정렬된 배열을 담을 동일한 크기의 임시 배열이 필요함(공간복잡도 2N)



## Selection Sort(선택 정렬)

`시간 복잡도 : O(N^2)`

> 버블 정렬과 상당히 유사하다
> But 버블 정렬은 N^2만큼 비교해나가면서 그때그때 최소 or 최대를 맨끝으로 밀어넣는것
> 선택 정렬은 특정 위치에 넣을 값을 정해서 그 위치에 넣어주는 정렬

### Process

- 최소값을 선택
- 그 값을 맨 정렬되지 않은 부분중 가장 첫번째와 교체
- 그 다음 위치부터 위 방법과 동일하게 진행

<img src="https://user-images.githubusercontent.com/55429912/119270338-2aaaec80-bc37-11eb-9c10-9b35b28737f9.png" style="zoom:50%;" />

### Code

```java
private static void swap(int[] arr, int i, int j) {
  int tmp = arr[i];
  arr[i] = arr[j];
  arr[j] = tmp;
}

private static void selectionSort(int[] arr) {
	int minIdx = 0;
  int length = arr.length;
  
  for(int i = 0; i < length - 1; i++) {
    minIdx = i;
    
    for(int j = i + 1; j < length; j++) {
      if(arr[midIdx] > arr[j]) {
        minIdx = j;
      }
    }
    
    if(i != minIdx) {
      swap(arr, i, minIdx);
    }
  }
}
```

장점 : 정렬을 위한 비교 횟수는 많으나 교환 횟수가 적어서 역순 정렬에 효과적

단점 : 정렬을 위한 비교횟수가 많아서 이미 정렬된 상태에서 소수의 원소가 추가된다면 재정렬시 최악의 처리속도를 보여준다



## Insertion Sort(삽입 정렬)

`시간 복잡도 : O(N^2)`

> 손 안에 카드를 정렬하는 방법과 유사
>
> 2번쨰 원소부터 선택해서 그 전의 원소들을 봐가며 어디에 삽입할지를 정해서 삽입하는 알고리즘
>
> 최선의 경우 O(N)이다 => 비교할 필요가 없으면 이중 반복문 돌지 않기 때문

### Process (Ascending)

- 정렬을 2번쨰 위치에 있는 값부터, 기준값을 temp로 저장
- temp와 이전에 있는 값들을 비교해가며 더 큰 애들을 앞으로 당겨준다
- 적절한 위치를 찾으면 해당 위치에 값을 넣어주고 다시 첫 process로

![](https://user-images.githubusercontent.com/55429912/119269333-229c7e00-bc32-11eb-927b-242e29978717.png)

### Code

```java
private static void insertionSort(int[] arr) {
	int length = arr.length;
  
  for(int idx = 1; idx < length; idx++) {
    int tmp = arr[idx];
    int prev = idx - 1;
    
    while((prev >= 0) && (arr[prev] > tmp)) {
      arr[prev + 1] = arr[prev];
      prev--;
    }
    
    arr[prev + 1] = tmp;
  }
}
```

장점

- 이미 정렬되어있는 경우에는 O(N)으로 매우 효율적
- in-place sorting 방식
- 안정 정렬이다

단점

- 평균, 최악의 경우 O(N^2)이다
- 배열의 길이가 길어질수록 비효율적이다



