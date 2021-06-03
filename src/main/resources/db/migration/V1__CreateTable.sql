create table user
(
    UID   int          not null auto_increment primary key,
    Email varchar(100) not null,
    Name  varchar(40) not null,

    CreatedAt timestamp not null default current_timestamp,
    UpdatedAt timestamp not null default current_timestamp on update current_timestamp
);

create table book
(
    BID       int          not null auto_increment primary key,
    Title     varchar(100) not null,
    Author    varchar(40)  not null,
    Publisher varchar(40)  not null,

    CreatedAt timestamp not null default current_timestamp,
    UpdatedAt timestamp not null default current_timestamp on update current_timestamp
);

create table contents
(
    EID     int          not null auto_increment primary key,

    User_UID     int ,
    FOREIGN KEY (User_UID) REFERENCES user (UID),

    Book_BID     int,
    FOREIGN KEY (Book_BID) REFERENCES book (BID),

    Page    int not null,
    Contents varchar(200) not null,

    CreatedAt timestamp not null default current_timestamp,

    UpdatedAt timestamp not null default current_timestamp on update current_timestamp
);