﻿create database Java3_ASM_PS36680_FPL_DaoTao;
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
hinh nvarchar(200),
primary key (masv));
go

create table grade (
masv nvarchar(50),
tienganh float,
tinhoc float,
gdtc float,
primary key (masv));
go

alter table grade
add constraint fk_gr_st
foreign key (masv)
references students (masv);
go

insert into users values
('vdquang', '123', 'GiangVien'),
('vhlong', '123', 'CanBo'),
('ttmngoc', '123', 'GiangVien');
go

delete from users;
go

insert into students values
('SV001', N'Vũ Đăng Quang', 'vdq@fpt.edu.vn', '0101010101', 1, '1 LTT', 'C:\Users\Quang\OneDrive - FPT Polytechnic\Desktop\img\bin\1.jpg'),
('SV002', N'Trương Thị Minh Ngọc', 'ttmn@fpt.edu.vn', '0202020202', 0, '2 LTT', 'C:\Users\Quang\OneDrive - FPT Polytechnic\Desktop\img\bin\2.jpg');
go

delete from students;
go

insert into grade values
('SV001', 7, 9, 8);
go

select *
from grade
where masv = (select masv 
			from students 
			where masv = 'SV001');
go

create trigger insert_diem_sv on grade
for insert
as
	begin
		declare @masv nvarchar(50);

		select @masv = ins.masv
		from inserted ins
		left join students std
		on std.masv = ins.masv;

		if @masv is null
		begin
			raiserror(N'Mã sinh viên không tồn tại', 16, 1);
			rollback transaction;
		end
	end
go

select s.masv, g.tienganh, g.tinhoc, g.gdtc, hoten
from grade g
right join students s
on g.masv = s.masv
where s.masv = 'sv002';
go

update grade
set tienganh = 10, tinhoc = 10, gdtc = 10
where masv = 'sv001';
go

select top 3 *, (tienganh + tinhoc + gdtc)/3 as dtb
from grade
order by dtb desc;
go