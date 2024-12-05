CREATE ROLE tech_folks NOINHERIT LOGIN PASSWORD 'password';

CREATE DATABASE Fridge_ingredients
	WITH
	OWNER = tech_folks
	ENCODING = 'UTF8'
	CONNECTION LIMIT = -1;

CREATE TABLE Fridge_contents
(
	item_id integer NOT NULL,
	user_id integer,
	ingredient_id integer,
	quantity integer,
	unit_id integer,
	PRIMARY KEY (item_id),
	FOREIGN KEY (ingredient_id) REFERENCES Ingredients(ingredient_id) ON DELETE CASCADE ON UPDATE CASCADE, 
	FOREIGN KEY (unit_id) REFERENCES Units(unit_id) ON DELETE CASCADE ON UPDATE CASCADE 
	
);

CREATE TABLE Units
(
	unit_id integer NOT NULL,
	name text,
	PRIMARY KEY(unit_id)
);

CREATE TABLE Ingredients
(
	ingredient_id integer NOT NULL,
	name text,
	unit_id integer,
	PRIMARY KEY(ingredient_id),
	FOREIGN KEY (unit_id) REFERENCES Units(unit_id) ON DELETE CASCADE ON UPDATE CASCADE 
);
	
