create table user
(
    UID   bigint not null auto_increment primary key,
    Email varchar(100) not null,
    Name  varchar(40) not null,

    CreatedAt timestamp not null default current_timestamp,
    UpdatedAt timestamp not null default current_timestamp on update current_timestamp
);

create table book
(
    BID       bigint not null auto_increment primary key,
    Title     varchar(100) not null,
    Author    varchar(40)  not null,
    Publisher varchar(40)  not null,

    created_date timestamp default current_timestamp,
    updated_date timestamp default current_timestamp
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

    CreatedAt timestamp not null default current_timestamp,

    UpdatedAt timestamp not null default current_timestamp on update current_timestamp
);