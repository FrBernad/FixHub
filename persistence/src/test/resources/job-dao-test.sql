TRUNCATE TABLE users RESTART IDENTITY AND COMMIT NO CHECK;
TRUNCATE TABLE jobs RESTART IDENTITY AND COMMIT NO CHECK;

insert into USERS(u_password, u_name, u_surname, u_email, u_phone_number,
                  u_profile_picture, u_cover_picture, u_state, u_city)
VALUES ('password','name','surname','email','phoneNumber',null,null,'state','city');

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Limpieza total','MECANICO','Limpieza de filtros',1,800,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Limpieza total','MECANICO','Limpieza de filtros',1,300,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Limpieza total','MECANICO','Limpieza de filtros',1,500,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Limpieza total','MECANICO','Limpieza de filtros',1,400,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Limpieza total','MECANICO','Limpieza de filtros',1,900,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Limpieza total','MECANICO','Limpieza de filtros',1,1000,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Soluciono problemas de tensión','ELECTRICISTA','Revisión de tableros',1,900,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Instalación de riegos de ultima generación','PLOMERO','Instalación de riegos',1,300,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Corto el pasto y hago plantaciones','JARDINERO','Corte de pasto',1,400,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Descripción de trabajo','GASISTA','Instalación de medidor de gas',1,3000,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Descripcion de trbajo','CARPINTERO','Armado de muebles',1,3001,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Descripcion de trbajo','PINTOR','Pintura en techos',1,3500,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Descripcion de trbajo','HERRERO','Colocación de portones',1,2000,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Descripcion de trbajo','TECHISTA','Arreglo de tejas',1,200,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Descripcion de trbajo','LIMPIEZA','Limpieza de departamentos',1,100,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Descripcion de trbajo','MANTENIMIENTO','Mantenimiento de equipos de aire acondicionado',1,300,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Descripcion de trbajo','ENTREGA','Entregas de productos',1,50,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Descripcion de trbajo','MUDANZA','Mudanza de muebles',1,300,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Descripcion de trbajo','NINERA','Cuido niños de 1 a 10 años',1,300,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Descripcion de trbajo','PASEADOR_DE_PERRO','Paseo de 2 horas de perros',1,800,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Descripcion de trbajo','CUIDADOR_DE_ANCIANO','Cuidado de personas mayores',1,900,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Descripcion de trbajo','CHEF','Cocina gourmet',1,1000,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Descripcion de trbajo','CATERING','Fiesta de 15',1,1500,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Descripcion de trbajo','VIDRIERO','Colocación de vidrio en ventanales',1,1800,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Descripcion de trbajo','FOTOGRAFO','Book para casamiento',1,1900,false);

INSERT INTO JOBS(j_description, j_category, j_job_provided, j_provider_id, j_price,j_paused)
VALUES ('Descripcion de trbajo','FUMIGADOR','Fumigación contra hormigas',1,1999,false);
