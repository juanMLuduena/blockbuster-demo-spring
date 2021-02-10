CREATE DATABASE blockbuster;
USE blockbuster;
SET GLOBAL event_scheduler = ON;
SET GLOBAL time_zone = '-3:00';
# drop database blockbuster

create table countries(
id int auto_increment primary key,
name varchar(50) unique
);

create table provinces(
id int auto_increment primary key,
name varchar(50),
id_country int,
constraint fk_country foreign key (id_country) references countries(id)
);

create table cities(
id int auto_increment primary key,
name varchar(50),
prefix_number varchar(4),
id_province int,
constraint fk_province foreign key (id_province) references provinces(id)
);

create table clients(
id INT AUTO_INCREMENT PRIMARY KEY,
firstname VARCHAR(50),
lastname VARCHAR(50),
dni VARCHAR(10) UNIQUE,
premium boolean default false
);

create table employees(
id INT AUTO_INCREMENT PRIMARY KEY,
firstname VARCHAR(50),
lastname VARCHAR(50),
dni VARCHAR(10) UNIQUE,
salary int default 0
);

create table movies(
id INT AUTO_INCREMENT PRIMARY KEY,
title varchar(100),
rented boolean default false
);

create table rents(
id INT AUTO_INCREMENT PRIMARY KEY,
id_employee int,
id_client int, 
id_movie int,
constraint fk_client foreign key (id_client) references clients(id),
constraint fk_employee foreign key (id_employee) references employees(id),
constraint fk_movie foreign key (id_movie) references movies(id)
);
