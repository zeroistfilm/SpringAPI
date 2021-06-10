# 1. 프로젝트 실행 방법
- MySQL서버를 생성하기 위해 다음 도커 명령어를 터미널에 입력합니다.
```
docker run --name mysql-woowahan 
-e MYSQL_ROOT_PASSWORD=password 
-e MYSQL_DATABASE=BookContents 
-e MYSQL_USER=sa
-e MYSQL_PASSWORD=password 
-e TZ=Asia/Seoul 
-d 
-p 3306:3306 
mysql:5.7 
--character-set-server=utf8mb4 
--collation-server=utf8mb4_unicode_ci
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
      - 유저 등록<br>
        POST http://localhost:8080/user/new <br>
        JSON {name, email}
  - Read  
      - 특정 유저 조회<br>
      GET http://localhost:8080/user/?id={UID}
      - 모든 유저 조회<br>
        GET http://localhost:8080/user/all
      - 모든 유저 조회(페이징)<br>
        GET http://localhost:8080/user/allPages?pagesize={pagesize}}&requestpage={requestpage}
  - Update 
      - 유저 수정<br>
        PUT http://localhost:8080/user/?id={UID} <br>
        JSON {uid, name, email}
- Book
  - Create
    - 책 등록<br>
      POST http://localhost:8080/book/new <br>
      JSON {title,author,publisher}
  - Read
    - 특정 책 조회<br>
      GET http://localhost:8080/book/?id={UID}
    - 모든 책 조회<br>
      GET http://localhost:8080/book/all
    - 모든 책 조회(페이징)<br>
      GET http://localhost:8080/book/allPages?pagesize={pagesize}}&requestpage={requestpage}
  - Update
    - 책 정보 수정<br>
      PUT http://localhost:8080/book/?id={UID} <br>
      JSON {bid,title,author,publisher}
- Contents
  - Create
    - 발췌문 등록 <br>
      POST http://localhost:8080/contents/new <br>
      JSON {uid,bid,page,contents}
  - Read
    - 특정 발췌문 조회<br>GET http://localhost:8080/contents/?id={UID}
    - 모든 발췌문 조회
      <br>GET http://localhost:8080/contents/all
    - 모든 발췌문 조회(페이징)<br>GET http://localhost:8080/contents/allPages?pagesize={pagesize}}&requestpage={requestpage}
  - Update
    - 발췌문 수정<br>
      PUT http://localhost:8080/contents/?id={UID} <br>
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
