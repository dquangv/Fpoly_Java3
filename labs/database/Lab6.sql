drop database Java3_Lab6_PS36680;
go

create database Java3_Lab6_PS36680;
go

use Java3_Lab6_PS36680;
go

create table books (
id int identity not null,
title nvarchar(50),
price float,
primary key (id));
go

insert into books values
(N'Lập trình C', 100),
(N'Lập trình Java', 200),
(N'Lập trình C#', 150);
go

select *
from books
where title like N'%Lập trình%';
go

create table students (
regid int identity not null,
name nvarchar(50),
address nvarchar(50),
parentname nvarchar(50),
phone nvarchar(50),
standard nvarchar(30),
regdate datetime,
primary key (regid));
go

create table standards (
st