## How to Save Grep Output to File in Linux

1. Overwrite grep output to file
- test.txt 파일 내에서 `test` 라는 글자를 찾아 grep_test.txt로 저장
```
$ grep "test" test.txt > grep_test.txt 
```

2. Append grep output to file
- test.txt 파일 내에서 `test` 라는 글자를 찾아 grep_test.txt에 이어붙이기
```
$ grep "test" test.txt >> grep_test.txt 
```
