create table topicos(
	id bigint not null auto_increment,
	titulo varchar(255) not null unique,
    mensaje varchar(600) not null,
    fecha_de_creacion datetime not null,
    status varchar(100) not null,
    id_autor bigint not null,
    id_curso bigint not null,
    activo tinyint not null,
    
    primary key(id)
);
