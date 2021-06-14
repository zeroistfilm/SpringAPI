# 1. 프로젝트 실행 방법
- MySQL서버를 생성하기 위해 도커 컴포즈 파일이 있는 곳에서 다음  명령어를 터미널에 입력합니다.
  ```shell
  $ docker-compose up -d
  ```

- CLI</br> 
  프로젝트 최상위 폴더에서 다음 명령어를 입력합니다.
  ```shell
  $ ./gradlew build
  ```
  ```shell
  $ java -jar ./build/libs/woowahan-0.0.1-SNAPSHOT.jar
  ```
- IntelliJ<br>
  - 해당 프로젝트 import 후 실행 하시면 됩니다.
  
- 참조
    - docker compose file
    ```yml
    version: '3.7'
    
    services:
      mysql-database:
        image: mysql:5.7
        container_name: mysql-woowahan
        environment:
          MYSQL_DATABASE: BookContents
          MYSQL_ROOT_PASSWORD: password
          MYSQL_USER: sa
          MYSQL_PASSWORD: password
          TZ: Asia/Seoul
        ports:
          - 3306:3306
        command:
          - --character-set-server=utf8mb4
          - --collation-server=utf8mb4_unicode_ci
    ```
    - docker for cli
    ```shell
    $ docker run \
    --name mysql-woowahan \
    -e MYSQL_ROOT_PASSWORD=password \
    -e MYSQL_DATABASE=BookContents \
    -e MYSQL_USER=sa \
    -e MYSQL_PASSWORD=password \
    -e TZ=Asia/Seoul \
    -d \
    -p 3306:3306 \
    mysql:5.7 \
    --character-set-server=utf8mb4 \
    --collation-server=utf8mb4_unicode_ci
    ```


# 2. 요구사항
- 사용자의 이름을 변경할 수 있는 API를 제공합니다.
- 책의 제목과 저자, 출판사를 변경할 수 있는 API를 제공합니다.
- 발췌한 페이지 번호와 내용을 변경할 수 있는 API를
  ### 구현
  - 사용자의 이름만 변경할 수 있습니다.
  - 제목, 저자, 출판사를 변경할 수 있습니다. 3가지중 1가지만 업데이트 하더라도 1가지만 변경되고 나머지는 기존 데이터가 유지됩니다.
  - 페이지 번호화 내용을 변경할 수 있습니다. 2가지 중 한가지만 수정해도 됩니다.


# 3. API 호출<br>
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
        JSON {name} <br>

- Book
  - Create
    - 책 등록<br>
      POST http://localhost:8080/book/new <br>
      JSON {title,author,publisher}
  - Read
    - 특정 책 조회<br>
      GET http://localhost:8080/book/?id={BID}
    - 모든 책 조회<br>
      GET http://localhost:8080/book/all
    - 모든 책 조회(페이징)<br>
      GET http://localhost:8080/book/allPages?pagesize={pagesize}}&requestpage={requestpage}
  - Update
    - 책 정보 수정<br>
      PUT http://localhost:8080/book/?id={BID} <br>
      JSON {title,author,publisher}
- Contents
  - Create
    - 발췌문 등록 <br>
      POST http://localhost:8080/contents/new <br>
      JSON {uid,bid,page,contents}
  - Read
    - 특정 발췌문 조회<br>GET http://localhost:8080/contents/?id={CID}
    - 모든 발췌문 조회
      <br>GET http://localhost:8080/contents/all
    - 모든 발췌문 조회(페이징)<br>GET http://localhost:8080/contents/allPages?pagesize={pagesize}}&requestpage={requestpage}
  - Update
    - 발췌문 수정<br>
      PUT http://localhost:8080/contents/?id={CID} <br>
      JSON {page,contents}

## 3.1 Response Status<br>

| |성공|실패|Exception|Message|
|:---:|:---:|:---:|:---:|:---:|
|Create|201|400|IllegalArgumentException|잘못된 인자 전달| 
|ReadOne|200|204|NoSuchElementException|특정ID 데이터 없음|
|ReadAll|200|204|NoSuchElementException|데이터 없음|
|ReadPage|200|400|IllegalArgumentException|잘못된 인자 전달|
|Update|201|204|NoSuchElementException|특정ID 데이터 없음|


# 4. 테스트<br>
- JPA : Service Layer에서 이루어지는 메서드를 테스트합니다.
- HTTP : http요청을 통해 호출되는 컨트롤러를 테스트합니다. 
<br>
<br>
<img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F8def6b55-848a-425e-adf0-e7f54db0cc98%2FUntitled.png?table=block&id=4668ff2d-ebbd-454c-9f15-b9dc0db77bfe&width=2980&userId=14ad980b-ed44-4307-8ea9-6d98b0f9e4fd&cache=v2">

# 5. DB schema
<br>
<img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F723e1955-c12f-4575-9c39-03fa2a49f7c0%2FUntitled.png?table=block&id=362b4f8e-5783-48bc-b0c7-598d181934ce&width=2980&userId=14ad980b-ed44-4307-8ea9-6d98b0f9e4fd&cache=v2">
<br>

- *Table간의 N:M관계* 
  - User Table
    - 유저는 여러 발췌문을 작성할 수 있습니다. (*many*)
    - 유저는 등록되어 있지만 발췌문을 작성하지 않을 수 있습니다. (*Optional*)
    - 따라서 유저 테이블은 발췌문 테이블과 `1:N Optional` 관계 입니다.
  - Book Table
    - 책은 여러 발췌문에 소속될 수 있습니다. (*many*)
    - 책은 발췌문에 소속되지 않을 수 있습니다. (*Optional*)
    - 따라서 책 테이블은 발췌문 테이블과 `1:N Optional` 관계 입니다.
  - Contents Table
    - 발췌문은 반드시 하나의 유저 정보를 포함해야 합니다. (*Mandatory*)
    - 발췌문은 반드시 하나의 책 정보를 포함해야 합니다. (*Mandatory*)
    - 유저와 책 테이블에 관계를 가진 발췌문 테이블은 `유저와 책 테이블의 각각 PK를 FK`로 가집니다.
- 테이블을 생성하기 위한 SQL  
  ```sql
  set time_zone = 'Asia/Seoul';
  
  create table user
  (
      UID   bigint not null auto_increment primary key,
      Email varchar(100) not null,
      Name  varchar(40) not null,
  
      created_date timestamp not null default current_timestamp,
      updated_date timestamp not null default current_timestamp on update current_timestamp
  );
  
  create table book
  (
      BID       bigint not null auto_increment primary key,
      Title     varchar(100) not null,
      Author    varchar(40)  not null,
      Publisher varchar(40)  not null,
  
      created_date timestamp not null default current_timestamp,
      updated_date timestamp not null default current_timestamp on update current_timestamp
  );
  
  create table contents
  (
      CID     bigint not null auto_increment primary key,
  
      User_UID     bigint ,
      FOREIGN KEY (User_UID) REFERENCES user ( UID),
  
      Book_BID     bigint,
      FOREIGN KEY (Book_BID) REFERENCES book (BID),
  
      Page    int not null,
      Contents varchar(255) not null,
  
      created_date timestamp not null default current_timestamp,
      updated_date timestamp not null default current_timestamp on update current_timestamp
  );
  ```
  
 - hibernate의 테이블 자동 생성 옵션을 off 합니다.
  ```properties
  #자동 테이블 생성 끄기
  spring.jpa.hibernate.ddl-auto=none
  ```

# 6. API structure <br>
  <img src="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fb60dc3d9-853e-4385-bdf1-6c88d999e22f%2FUntitled.png?table=block&id=d8c303cb-01b0-4937-a80a-f4f1873caf31&width=1450&userId=14ad980b-ed44-4307-8ea9-6d98b0f9e4fd&cache=v2">

# 7.인터페이스 설계 <br>
User, Book, Contents 항목 모두 요구사항이 같기 때문에 공통의 인터페이스를 둡니다. <br>
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

# 8. 클래스 구현<br>

- Service Class<br>
각각의 서비스 클래스는 서비스 인터페이스를 상속받고, 인터페이스에 명시된 메서드를 오버라이드하여 구체적인 메서드를 정의합니다.
  - create : DTO를 입력받아 Repository의 Save를 호출하고 응답으로 Entity를 반환합니다.
  - readOne : ID를 입력받아 Repository의 findbyID를 호출하고 응답으로 Entity를 반환합니다.
  - readAll : Repository의 findAll을 호출하고 응답으로 List<Entity>를 반환합니다.
  - readPage :  Repository의 findAll(Pageable sortedById)를 호출하고 Page<Entity>를 반환합니다.
  - update : ID와 DTO를 입력받아 Repository의 findbyID, Save를 순차적으로 호출합니다.
  - isVaild : 클래스로 넘어오는 DTO객체의 유효성을 검증하며 유효성오류시 `IllegalArgumentException`을 throw합니다.
  - UserService에는 Email을 검증하는 로직이 추가가 되어있습니다. <br> (userService 단독으로 사용되기 때문에 인터페이스에는 정의하지 않았습니다.)
    
# 9. DTO

- API에 요구되는 핵심 테이터만 다룹니다. Setter 없이 오직 생성자만을 통해 객체를 생성할 수 있습니다.
- User <br>
  ``` java
  private String name;
  private String email;
  ```
- Book <br>
   ``` java
  private String title;
  private String author;
  private String publisher;
  ```
- Contents<br>
  ``` java
  private Long uid;
  private Long bid;
  private Integer page;
  private String contents;
  ```

#  10. DAO(Entity)
- 데이터 베이스 테이블에 직접 매칭되는 객체입니다.
- 데이터베이스에 데이터를 CRUD하기 위해 필요한 모든 항목이 포함됩니다.
- DTO에서 사용자가 컨트롤 하지 않은 `Unique ID, Create time, Update time`  이 추가됩니다.
- User
  ``` java
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "UID")
  private Long uid;
  
  @Column(name = "Name")
  private String name;
  
  @Column(name = "Email")
  private String email;
  
  @Column(name = "created_date")
  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private Date createdDate;
  
  @Column(name = "updated_date")
  @Temporal(TemporalType.TIMESTAMP)
  @UpdateTimestamp
  private Date updatedDate;
  
  public User(String name, String email) {
      this.name = name;
      this.email = email;
  }
  ```
- Book DAO (main/Entity/Book 참조)
- Contents DAO (main/Entity/Contents 참조)

# 11.Logging
- slf4j를 사용하여 Service 레이어에서 Info레벨로 CRU성공 여부와 실패 여부를 로깅합니다. 

# 12. 기술스택<br>
- JAVA11
- Spring 2.4.6
- FlyWay
- Spring Data JPA
- Mysql 5.7
- Docker
