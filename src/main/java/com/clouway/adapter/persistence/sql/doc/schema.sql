create database bank;

use bank;

create table users(email varchar(40),userName varchar(20),nickName varchar(20), password varchar(20), city varchar(20), age int(3), primary key(email));

create table sessions(ID varchar(50),userEmail varchar(40), sessionExpiresOn bigint(20),primary key (ID),foreign key (userEmail) references users(email));

create table accounts(userEmail varchar(40),balance double(10,2), primary key(userEmail));

create table loggedusers (userEmail varchar(40), primary key(userEmail));