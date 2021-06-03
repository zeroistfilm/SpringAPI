# woowahanAssignment

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


5. domain 비지니스 로직 작성