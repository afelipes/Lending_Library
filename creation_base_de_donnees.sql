-- Titre :             Création base projet bibliothèque
-- Version :           1.0
-- Version :      1.0
-- Date :         26 novembre 2008
-- Auteur :       Philippe TANGUY
-- Description :  Création de la table "livre" pour la réalisation de la fonctionnalité "liste de tous les livres"

-- +----------------------------------------------------------------------------------------------+
-- | Suppression des tables                                                                       |
-- +----------------------------------------------------------------------------------------------+
drop table if exists "emprunt";
drop table if exists "exemplaire";
drop table if exists "usager";
drop table if exists "livre";
-- +----------------------------------------------------------------------------------------------+
-- | Création des tables                                                                          |
-- +----------------------------------------------------------------------------------------------+

create table livre
(
	id			serial primary key,
	isbn10		varchar(25) unique,
	isbn13		varchar(25) unique,
	titre		varchar(50) not null,
	auteur		varchar(30),
	nb_exempl	int8
);
create table usager 
(
	id_abonne			serial primary key,
	nom					varchar(15),
	prenom				varchar(15),
	statut				varchar(15) not null,
	email				varchar(50)
);

create table exemplaire
(
	id_exempl				serial primary key,
	etat					varchar(15),
	id_livre				int8 references "livre"
	
);

create table emprunt 
(
	id_emprunt			serial primary key,
	id_exemplaire		int8 references "exemplaire",		
	id_usager 			int8 references "usager",
	date_empr			varchar(30) not null,
	date_retour			varchar(30) 
	);

-- +----------------------------------------------------------------------------------------------+
-- | Insertion de quelques données de pour les tests                                              |
-- +----------------------------------------------------------------------------------------------+

insert into livre values(nextval('livre_id_seq'), '2-84177-042-7', '24-5425-45-4','JDBC et JAVA','George Reese');    -- id = 1
insert into livre values(nextval('livre_id_seq'), '25-4-47-4758', '978-2-7440-7222-2', 'Sociologie des organisations', 'Michel Foudriat'); -- id = 2
insert into livre values(nextval('livre_id_seq'), '2-212-11600-4', '978-2-212-11600-7', 'Le data warehouse', 'Ralph Kimball');   -- id = 3
insert into livre values(nextval('livre_id_seq'), '2-7117-4811-1', '5-47-8657-7', 'Entrepots de donnees','Ralph Kimball');   -- id = 4
insert into livre values(nextval('livre_id_seq'), '2012250564',    '978-220567',    'Oui-Oui et le nouveau taxi',              'Enid Blyton');     -- id = 5
insert into livre values(nextval('livre_id_seq'), '2203001011',    '978-20301015',    'Tintin au Congo',                         'Hergé');           -- id = 6
insert into livre values(nextval('livre_id_seq'), '201201173',    '978-201011373',    'Harry Potter and the Chamber of Secret', 'J. K. Rowling');     -- id = 7
insert into livre values(nextval('livre_id_seq'), '20121373',    '978-201373',    'Fahrenheit 451', 'Ray Bradbury');     -- id = 7
insert into livre values(nextval('livre_id_seq'), '2020173',    '978-20011373',    'Forrest Gump', 'Winston Groom');     -- id = 7

insert into usager values(nextval('usager_id_abonne_seq'), 'Bruschi', 'Agnese', 'Etudiant', 'a_b@tele.com');    -- id = 1
insert into usager values(nextval('usager_id_abonne_seq'), 'Perin', 'Gustavo', 'Etudiant', 'gp@tele.com');  	-- id = 2
insert into usager values(nextval('usager_id_abonne_seq'), 'Felipe', 'Alejandro', 'Etudiant', 'ale@tele.com'); 	-- id = 3
insert into usager values(nextval('usager_id_abonne_seq'), 'Einstein', 'Albert', 'Ensegnant', 'emc@genius.com');    -- id = 4
insert into usager values(nextval('usager_id_abonne_seq'), 'Ronaldo', 'Cristiano', 'Ensegnant', 'ilove@myself.com');  	-- id = 5
insert into usager values(nextval('usager_id_abonne_seq'), 'Rossi', 'Valentino', 'Ensegnant', 'lorenzo@agagne.com'); 	-- id = 6

insert into exemplaire values(nextval('exemplaire_id_exempl_seq'), 'Disponible','7');    -- id = 1