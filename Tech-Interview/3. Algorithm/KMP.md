# KMP(Knuth, Morris, Prett)

> 문자열 검색 알고리즘
>
> 시간 복잡도
>
> - O(N + M)
> - 원본 문자열 길이 N, 탐색 문자열 길이 M

## Process

1. 접두사(prefix)와 접미사(suffix)가 같은 가장 긴 문자열 길이를 저장하는 배열(pi) 생성

ex) 문자열 "ABAABAB"

| idx  | 생성된 부분 문자열 | 접두사 | 중간 문자열 | 접미사 | pi[idx] |
| ---- | ------------------ | ------ | ----------- | ------ | ------- |
| 0    | A                  | -      | A           | -      | 0       |
| 1    | AB                 | -      | AB          | -      | 0       |
| 2    | ABA                | A      | B           | A      | 1       |
| 3    | ABAA               | A      | BA          | A      | 1       |
| 4    | ABAAB              | AB     | A           | AB     | 2       |
| 5    | ABAABA             | ABA    | -           | ABA    | 3       |
| 6    | ABAABAB            | AB     | AAB         | AB     | 2       |



2. pi배열을 이용하여 기존 문자열과 비교

ex) 문자열 S1에 "ABAABACABAABAB"에 문자열 S2가 "ABAABAB"이 있는가

```
s1[6] != s2[6]
```

| 인덱스 | 0    | 1    | 2    | 3    | 4    | 5    | 6    | 7    | 8    | 9    | 10   | 11   | 12   | 13   |
| ------ | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| S1     | A    | B    | A    | A    | B    | A    | C    | A    | B    | A    | A    | B    | A    | B    |
| S2     | A    | B    | A    | A    | B    | A    | B    |      |      |      |      |      |      |      |



```
s2의 부분문자열 "ABAABA"의 pi[5] = 3    // 3칸 이동
```

| 인덱스 | 0    | 1    | 2    | 3    | 4    | 5    | 6    | 7    | 8    | 9    | 10   | 11   | 12   | 13   |
| ------ | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| S1     | A    | B    | A    | A    | B    | A    | C    | A    | B    | A    | A    | B    | A    | B    |
| S2     |      |      |      | A    | B    | A    | A    | B    | A    | B    |      |      |      |      |



```
s1[6] != s2[4]
```

| 인덱스 | 0    | 1    | 2    | 3    | 4    | 5    | 6    | 7    | 8    | 9    | 10   | 11   | 12   | 13   |
| ------ | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| S1     | A    | B    | A    | A    | B    | A    | C    | A    | B    | A    | A    | B    | A    | B    |
| S2     |      |      |      | A    | B    | A    | A    | B    | A    | B    |      |      |      |      |



```
s2의 부분문자열 "ABA"의 pi[2] = 1    // 1칸 이동
```

| 인덱스 | 0    | 1    | 2    | 3    | 4    | 5    | 6    | 7    | 8    | 9    | 10   | 11   | 12   | 13   |
| ------ | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| S1     | A    | B    | A    | A    | B    | A    | C    | A    | B    | A    | A    | B    | A    | B    |
| S2     |      |      |      |      |      | A    | B    | A    | A    | B    | A    | B    |      |      |



```
s1[6] != s2[2]
```

| 인덱스 | 0    | 1    | 2    | 3    | 4    | 5    | 6    | 7    | 8    | 9    | 10   | 11   | 12   | 13   |
| ------ | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| S1     | A    | B    | A    | A    | B    | A    | C    | A    | B    | A    | A    | B    | A    | B    |
| S2     |      |      |      |      |      | A    | B    | A    | A    | B    | A    | B    |      |      |



```
s1[6] != s2[0]  // 겹치는 것이 없어서 1칸만 이동
```

| 인덱스 | 0    | 1    | 2    | 3    | 4    | 5    | 6    | 7    | 8    | 9    | 10   | 11   | 12   | 13   |
| ------ | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| S1     | A    | B    | A    | A    | B    | A    | C    | A    | B    | A    | A    | B    | A    | B    |
| S2     |      |      |      |      |      |      | A    | B    | A    | A    | B    | A    | B    |      |



```
s1문자열안에 s2문자열 포함
```

| 인덱스 | 0    | 1    | 2    | 3    | 4    | 5    | 6    | 7    | 8    | 9    | 10   | 11   | 12   | 13   |
| ------ | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| S1     | A    | B    | A    | A    | B    | A    | C    | A    | B    | A    | A    | B    | A    | B    |
| S2     |      |      |      |      |      |      |      | A    | B    | A    | A    | B    | A    | B    |

## Code

```java
//pi 배열 만드는 함수
private static int[] makeTable(char[] pattern) {
  int patternSize = pattern.length;
  int[] pi = new int[patternSize];
  int j = 0;

  for(int i = 1; i < patternSize; i++) {
    while(j > 0 && pattern[j] != pattern[i]) {
      j = result[j - 1];
    }
    if(pattern[j] == pattern[i]) {
      result[i] = ++j;
    }
  }

  return pi;
}

private static boolean KMP(char[] parent, char[] pattern, int[] table, StringBuilder sb) {
  int j = 0, parentSize = parent.length, patternSize = pattern.length;

  for(int i = 0; i < parentSize; i++) {
    while(j > 0 && pattern[j] != parent[i]) {
      j = table[j - 1];
    }
    if(pattern[j] == parent[i]) {
      if(j == patternSize - 1) {
        cnt++;
        return true;
      } else {
        j++;
      }
    }
  }
  return false;
}
```

