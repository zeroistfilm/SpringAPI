create table User
(
    UID   int          not null auto_increment primary key,
    Email varchar(100) not null,
    Name  varchar(40) not null,

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

create table Contents
(
    EID     int          not null auto_increment primary key,

    User_UID     int ,
    FOREIGN KEY (User_UID) REFERENCES User (UID),

    Book_BID     int,
    FOREIGN KEY (Book_BID) REFERENCES Book (BID),

    Page    int not null,
    Contents varchar(200) not null,

    CreatedAt timestamp not null default current_timestamp,

    UpdatedAt timestamp not null default current_timestamp on update current_timestamp
);