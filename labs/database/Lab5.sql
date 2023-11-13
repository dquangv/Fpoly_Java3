create database Java3_Lab5_PS36680;
go

use Java3_Lab5_PS36680;
go

create table students (
masv nvarchar(50) not null,
hoten nvarchar(50),
email nvarchar(50),
sodt nvarchar(12),
gioitinh bit,
diachi nvarchar(100));
go

insert into students values
('SV001', 'Le Van Phung', 'phunglv@fpt.edu.vn', '0903414749', 0, 'Ninh Thuan'),
('SV002', 'Le Quang Trung', 'trunglq@gmail.com', '0901234567', 0, '222 Le Van Si'),
('SV003', 'Le Thi Bao Hieu', 'hieultb@gmail.com', '0683872432', 1, 'Phan Rang'),
('SV004', 'Le Thi H Hanh', 'hanhlthh@gmail.com', '090999999', 1, 'Quan 12');
go

