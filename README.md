# woowahanAssignment

##User
|CRUD|설명|Request Method| URI | Body | Respose Status |Response contents|
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
|**Create**|유저 데이터 생성|POST|http://localhost:8080/user/new|JSON(name, email)|201|JSON(uid,name,email,create date, update date)|
| | 요청 데이터 누락| | | |400|Error Message|
|**Read**|전체 유저 조회|GET|http://localhost:8080/user/all| |200|JSON(uid,name,email,create date, update date)|
| | 데이터 없음 | | | |204| |
| | DB연결 없음 | | | |500|Error Message|
|**Read**|특정 유저 조회 (UID)|GET|http://localhost:8080/user/?id={UID}| |200| JSON(uid,name,email,create date, update date)|
| |  데이터 없음 | | | |204| |
|**Read**|페이지 조회|GET|http://localhost:8080/user/allPages?pagesize={pagesize}}&requestpage={requestpage}| |200| JSON(전체 정보가 page형태로 제공된다.)|
| |  페이지를 초과한 요청 | | | |400| 페이지 범위를 초과하는 요청입니다 MaxPage : |
|**Update**|UID를 통한 유저 데이터 수정|PUT|http://localhost:8080/user/?id={UID}|JSON(name, email)|201| |
| |  데이터 없음 | | | |204| |


##Book
|CRUD|설명|Request Method| URI | Body | Respose Status |Response contents|
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
|**Create**|책 데이터 생성|POST|http://localhost:8080/book/new|Json(title,author, publisher)|201|Json(bid,title,author, publisher,create date, update date)|
| |요청 데이터 누락| | | |400|Error Message|
|**Read**|전체 책 정보 조회|GET|http://localhost:8080/book/all| |200|Json(bid,title,author, publisher,create date, update date)|
| | 데이터 없음 | | | |204| |
| | DB연결 없음 | | | |500|Error Message|
| **Read**|특정 책 정보 조회 (UID)|GET|http://localhost:8080/book/?id={BID}| |200| Json(bid,title,author, publisher,create date, update date)|
| | 데이터 없음 | | | |204| |
| **Read**|페이지 조회|GET|http://localhost:8080/book/allPages?pagesize={pagesize}}&requestpage={requestpage}| |200| Json(전체 정보가 page형태로 제공된다.)|
| |  페이지를 초과한 요청 | | | |400| 페이지 범위를 초과하는 요청입니다 MaxPage : |
|**Update**|BID를 통한 유저 데이터 수정|PUT|http://localhost:8080/book/?id={BID}|Json(title,author, publisher)|201| |
| | 데이터 없음 | | | |204| |


##Contents
|CRUD|설명|Request Method| URI | Body | Respose Status |Response contents|
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
|**Create**|콘텐츠 데이터 생성|POST|http://localhost:8080/contents/new|Json(uid, bid, page, contents)|201|Json(cid, uid, bid, page, contents,create date, update date)|
| |요청 데이터 누락| | | |400|Error Message|
|**Read**|전체 콘텐츠 정보 조회|GET|http://localhost:8080/contents/all| |200|Json(cid, uid, bid, page, contents,create date, update date)|
| | 데이터 없음 | | | |204| |
| | DB연결 없음 | | | |500|Error Message|
|**Read** |특정 책 정보 조회 (UID)|GET|http://localhost:8080/contents/?id={BID}| |200| Json(cid, uid, bid, page, contents,create date, update date)|
| | 데이터 없음 | | | |204| |
|**Read** |페이지 조회|GET|http://localhost:8080/contents/allPages?pagesize={pagesize}}&requestpage={requestpage}| |200| Json(전체 정보가 page형태로 제공된다.)|
| |  페이지를 초과한 요청 | | | |400| 페이지 범위를 초과하는 요청입니다 MaxPage : |
|**Update**|BID를 통한 유저 데이터 수정|PUT|http://localhost:8080/contents/?id={BID}|Json(uid, bid, page, contents)|201| |
| | 데이터 없음 | | | |204| |


실행 방법
1. 도커 컨테이너 생성
```
docker run --name mysql-woowahan -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=BookEx
cerpt -e MYSQL_USER=sa -e MYSQL_PASSWORD=password -d -p 3306:3306 mysql:5.7
```

2. flyway를 이용해 테이블 마이그레이션.

```
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

3. domain 작성 <br>
    - User, Book, Contents


4. domain test 작성
    - func : 입력값 null check, Email 유효성 검사 등.
    - Http : 작성한 API를 통한 테스트

5. domain 비지니스 로직 작성<br>



