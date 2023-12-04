drop database if exists litemall;
drop user if exists 'litemall'@'%';
-- 支持emoji：需要mysql数据库参数： character_set_server=utf8mb4
create database litemall default character set utf8mb4 collate utf8mb4_unicode_ci;
use litemall;
-- 注：这一行用于把密码安全等级降低，不建议在生产环境下执行。这里特意增加了这一行，否则在 MySQL 8.0.35 中，后续的命令会报错。
SET GLOBAL validate_password.policy=LOW;
create user 'litemall'@'%' identified by 'litemall123456';
grant all privileges on litemall.* to 'litemall'@'%';
flush privileges;