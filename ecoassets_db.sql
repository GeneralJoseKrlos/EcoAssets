create database eco_assets
character set utf8mb4
collate utf8mb4_spanish_ci;

use eco_assets;

create table usuario (
id_usuario int unsigned auto_increment primary key,
nombre varchar(50) not null,
correo varchar(50) not null unique,
celular int not null,
contraseña varchar(50) not null);

create table marca (
id_marca int unsigned auto_increment primary key,
nombre varchar(50) not null,
modelo varchar(50) not null);

create table dispositivo(
codigo int unsigned auto_increment primary key,
nombre_del_dispositivo varchar(50) not null,
estado_del_dispositivo varchar(50) not null,
año_de_fabricacion date,
año_de_primera_compra date,
valor varchar(50) not null,
usuario_idusuario int unsigned not null,
foreign key (usuario_idusuario) references usuario(id_usuario)
on delete cascade
on update cascade,
marca_idmarca int unsigned not null,
foreign key (marca_idmarca) references marca(id_marca)
on delete restrict
on update cascade
);

create table pedido (
id_pedido int unsigned auto_increment primary key,
valor varchar(50) not null,
usuario_idusuario int unsigned not null,
foreign key (usuario_idusuario) references usuario(id_usuario)
on delete cascade
on update cascade
);
