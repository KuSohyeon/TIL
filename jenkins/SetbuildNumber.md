### JOB BUILD NUMBER 수정
Jenkins 관리 -> Script Console에서 아래 명령어 실행
```
Jenkins.instance.getItemByFullName("Job Name").updateNextBuildNumber(build Number)
```
