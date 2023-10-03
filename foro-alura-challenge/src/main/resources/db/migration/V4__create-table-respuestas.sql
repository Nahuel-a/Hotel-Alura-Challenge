create table respuestas(
	id bigint not null auto_increment,
	mensaje varchar(600) not null,
	fecha_de_creacion datetime not null,
	id_topico bigint not null,
	id_autor bigint not null,
	solucion tinyint not null,
	activo tinyint not null,
	
	primary key(id)

);