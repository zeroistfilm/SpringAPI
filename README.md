# 1. 프로젝트 실행 방법
- MySQL서버를 생성하기 위해 도커 컴포즈 파일이 있는 곳에서 다음  명령어를 터미널에 입력합니다.
  ```shell
  $ docker-compose up -d
  ```

- IntelliJ<br>  
  - 해당 프로젝트 import 후 실행 하시면 됩니다.


- CLI</br>
  프로젝트 최상위 폴더에서 다음 명령어를 입력합니다.
  ```shell
  $ ./gradlew build
  ```
  ```shell
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
      GET http://localhost:8080/user/?id={UID} <br>
        
      - 모든 유저 조회<br>
        GET http://localhost:8080/user/all <br>

      - 모든 유저 조회(페이징)<br>
        GET http://localhost:8080/user/allPages?pagesize={pagesize}}&requestpage={requestpage} <br>

  - Update 
      - 유저 수정<br>
        PUT http://localhost:8080/user/?id={UID} <br>
        JSON {uid, name, email} <br>

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

#2.1 Response Status<br>

| |성공|실패|
|:---:|:---:|:---:|
|Create|201|400|
|ReadOne|200|204|
|ReadAll|200|204|
|ReadPage|200|400|
|Update|201|204|


# 3. 테스트<br>
- JPA : Service Layer에서 이루어지는 메서드를 테스트합니다.
- HTTP : http요청을 통해 호출되는 컨트롤러를 테스트합니다. 
<br>
  <img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F8def6b55-848a-425e-adf0-e7f54db0cc98%2FUntitled.png?table=block&id=4668ff2d-ebbd-454c-9f15-b9dc0db77bfe&width=2980&userId=14ad980b-ed44-4307-8ea9-6d98b0f9e4fd&cache=v2">

# 4. 기술스택<br>
  - JAVA11
  - Spring 2.4.6
  - FlyWay
  - Spring Data JPA
  - Mysql 5.7
  - Docker

# 5. 구조<br>
API structure 
<img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fb60dc3d9-853e-4385-bdf1-6c88d999e22f%2FUntitled.png?table=block&id=d8c303cb-01b0-4937-a80a-f4f1873caf31&width=1450&userId=14ad980b-ed44-4307-8ea9-6d98b0f9e4fd&cache=v2">
DB schema
<img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F723e1955-c12f-4575-9c39-03fa2a49f7c0%2FUntitled.png?table=block&id=362b4f8e-5783-48bc-b0c7-598d181934ce&width=2980&userId=14ad980b-ed44-4307-8ea9-6d98b0f9e4fd&cache=v2">

# 6.인터페이스 설계 <br>
User, Book, Contents 항목 모두 같은 요구사항이 같기 때문에 공통의 인터페이스를 둔다. <br>
- Service Interface<br>

  
  
```java
public interface ServiceInterface<DTO,Entity> {
    Entity create(DTO dto);
    Entity readOne(long id);
    List<Entity> readAll();
    Page readPage(int requestpage, int pagesize);
    void update(long id,DTO dto);
    void isVaild(DTO dto);
}
```

- Repository Interface <br>
```java
public interface RepositoryInterface<Entity> {
    Entity save(Entity entity);
    Optional<Entity> findById(Long id);
    List<Entity> findAll();
    Page<Entity> findAll(Pageable sortedById);
}
```

# 7. 클래스 구현<br>

- Service Class<br>
각각의 클래스는 서비스 인터페이스를 상속받고, 인터페이스에 명시된 메서드를 오버라이드하여 구체적인 메서드를 정의한다.
  - UserService에는 Email을 검증하는 로직이 추가가 되어있다. (userService 단독으로 사용되기 때문에 인터페이스에는 정의하지 않았다.)
    