# woowahanAssignment

Request table
    
User 
Create
요청 
POST http://localhost:8080/users/new
Content-Type: application/json

{
"name": "테스트테스트",
"email": "zeroistfilm@naver.com"
}
성공 201 json
실패 400 회원 이름 정보가 없습니다
실패 400 회원 회원 이름과 이메일 정보가 없습니다
실패 400 회원 이메일 정보가 양식에 맞지 않습니다

Read
-all GET http://localhost:8080/users/all
-one GET http://localhost:8080/users/?id=1

성공 200 json
성공-값없음 204 null
실패 500 Could not open JPA EntityManager for transaction; nested exception is org.hibernate.exception.JDBCConnectionException: Unable to acquire JDBC Connection // DB연결 불가

Update
  -put
  -patch
Delete


과정
1. docker를 통해 로컬 mysql container생성.
```
docker run --name mysql-woowahan -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=BookEx
cerpt -e MYSQL_USER=sa -e MYSQL_PASSWORD=password -d -p 3306:3306 mysql:5.7
```

2. flyway를 이용해 테이블 마이그레이션.
```
create table User
(
    UID   int          not null auto_increment primary key,
    Email varchar(100) not null,
    Name  varchar(40),
    CreatedAt timestamp not null default current_timestamp,
    UpdatedAt timestamp not null default current_timestamp on update current_timestamp
);

create table Book
(
    BID       int          not null auto_increment primary key,
    Title     varchar(100) not null,
    Author    varchar(40)  not null,
    Publisher varchar(40)  not null,
    CreatedAt timestamp not null default current_timestamp,
    UpdatedAt timestamp not null default current_timestamp on update current_timestamp
);

create table Excerption
(
    EID     int          not null auto_increment primary key,
    User_UID     int,
    FOREIGN KEY (User_UID) REFERENCES User (UID),

    Book_BID     int,
    FOREIGN KEY (Book_BID) REFERENCES Book (BID),

    Page    int,
    content varchar(200) not null,

    CreatedAt timestamp not null default current_timestamp,
    UpdatedAt timestamp not null default current_timestamp on update current_timestamp
);
```

3. domain 작성 <br>
    - User, Book, Contents

4. domain test 작성
    - 4.1 이메일 점검 테스트 작성<br>
      - 비정상 이메일 <br>
        - (계정', '@', '도메인', '.', '최상위 도메인')중 1~5개를 선택하여 정상 이메일에서 해당 요소를 제거.<br>
        - 공백 문자 랜덤으로 추가함.<br>
      - 정상 이메일 <br>
        - 이메일 생성 사이트에서 정상 이메일 생성.<br>
   - 4.2 이메일 점검 함수 작성<br>
   - 4.3 이메일 테스트 실행<br>


5. domain 비지니스 로직 작성<br>

6. Spring Data JPA
   <br>
   - 6.1 비지니스 로직에서 할 것과 SD JPA 매서드 잘 구분해야 한다.
     (비지니스 로직에서 구현 해야 할 것을 DB메서드로 구현 하려고 하니 SDJPA가 올바른 메서드를 생성하지 못함)
