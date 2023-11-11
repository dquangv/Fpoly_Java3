create database Java3_ASM_PS36680_FPL_DaoTao;
go

use Java3_ASM_PS36680_FPL_DaoTao;
go

create table users (
username nvarchar(50) not null,
password nvarchar(50),
role nvarchar(50),
primary key (username));
go

create table students (
masv nvarchar(50) not null,
hoten nvarchar(50),
email nvarchar(50),
sodt nvarchar(15),
gioitinh bit,
diachi nvarchar(50),
hinh nvarchar(50),
primary key (masv));
go

create table grade (
id int not null,
masv nvarchar(50),
tienganh int,
tinhoc int,
gdtc int,
primary key (id));
go

alter table grade
add constraint fk_gr_st
foreign key (masv)
references students (masv);
go