if(Exists(select * from sys.sysobjects where id=OBJECT_ID('pluten_date')))
  print '该数据库表已经存在'
else
 CREATE TABLE pluten_date
(
id int NOT NULL PRIMARY KEY IDENTITY(1,1),
baobiaoId int,
daoliaoDate datetime,
)