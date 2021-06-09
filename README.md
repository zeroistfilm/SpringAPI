# 1. 프로젝트 실행 방법
- MySQL서버를 생성하기 위해 다음 도커 명령어를 터미널에 입력합니다.
```
docker run --name kyd-woowahan -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=BookEx
cerpt -e MYSQL_USER=sa -e MYSQL_PASSWORD=password -d -p 3306:3306 mysql:5.7
```

- IntelliJ<br>  
  - 해당 프로젝트 import 후 실행 하시면 됩니다.


- CLI</br>
  해당 폴더에서 다음 명령어를 입력합니다.
  ```
  $ ./gradlew build
  ```
  ```
  $ java -jar ./build/libs/woowahan-0.0.1-SNAPSHOT.jar
  ```
# 2. API 호출<br>
- User
  - Create  
      - POST http://localhost:8080/user/new <br>
        JSON {name, email}
  - Read  
      - GET http://localhost:8080/user/all
      - GET http://localhost:8080/user/?id={UID}
      - GET http://localhost:8080/user/allPages?pagesize={pagesize}}&requestpage={requestpage}
  - Update 
      - PUT http://localhost:8080/user/?id={UID} <br>
        JSON {uid, name, email}
- Book
  - Create
    - POST http://localhost:8080/book/new <br>
      JSON {title,author,publisher}
  - Read
    - GET http://localhost:8080/book/all
    - GET http://localhost:8080/book/?id={UID}
    - GET http://localhost:8080/book/allPages?pagesize={pagesize}}&requestpage={requestpage}
  - Update
    - PUT http://localhost:8080/book/?id={UID} <br>
      JSON {bid,title,author,publisher}
- Contents
  - Create
    - POST http://localhost:8080/contents/new <br>
      JSON {uid,bid,page,contents}
  - Read
    - GET http://localhost:8080/contents/all
    - GET http://localhost:8080/contents/?id={UID}
    - GET http://localhost:8080/contents/allPages?pagesize={pagesize}}&requestpage={requestpage}
  - Update
    - PUT http://localhost:8080/contents/?id={UID} <br>
      JSON {cid,uid,bid,page,contents}


# 3. 테스트<br>
- func : 데이터 유효성 체크
- HTTP : http요청을 통한 테스트 
<br>
  <img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F1ce5f5ba-97ce-4c45-ad28-1483a85c10b3%2FUntitled.png?table=block&id=28fccf25-8c71-4772-9a2d-36b907952c70&spaceId=971d9453-b60c-4792-b1d2-d39ee79ec29f&width=2980&userId=14ad980b-ed44-4307-8ea9-6d98b0f9e4fd&cache=v2">

# 4. 기술스택<br>
  - JAVA11
  - Spring 2.4.6
  - FlyWay
  - Spring Data JPA
  - Mysql 5.7
  - Docker
