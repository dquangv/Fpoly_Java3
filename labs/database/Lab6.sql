-- Reset identity column regid của bảng students về giá trị 1
DBCC CHECKIDENT ('students', RESEED, 0);

delete from students;
go

delete from students where regid = 1;
go

dbcc checkident('students', reseed);
go

select * from students
order by regid
offset 1 rows
fetch next 1 row only;
go

update students 
set name = 'Quang', address = 'ưẻưẻ',
parentname = 'ưẻ', phone = '213123', standard = 'Curriculum Standards'
where regid = 
(select regid from students order by regid offset 3 rows fetch next 1 row only);
go

insert into students values
('quang', 'sdkflj', 'fjsdkf', 'fsdkjf', 'Curriculum Standards', '11-16-2023 11:17:20');
go