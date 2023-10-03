create table cursos(
	id bigint not null auto_increment,
	nombre varchar(255) not null,
	categoria varchar(255) not null,
	activo tinyint not null,
	
	primary key(id)

);