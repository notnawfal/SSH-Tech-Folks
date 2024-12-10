CREATE DATABASE "Recipe_database"
	WITH
	OWNER = r
	ENCODING = 'UTF8'
	CONNECTION LIMIT = -1;
\c Recipe_database

CREATE TABLE Recipes (
    recipe_id SERIAL PRIMARY KEY,
    dish_name VARCHAR(255) NOT NULL,
    meal_type VARCHAR(100),
    protein VARCHAR(100),
    cooking_time INT,
    img_url TEXT
);



-- Create table: Instructions
CREATE TABLE Instructions (
    instruction_id SERIAL PRIMARY KEY,
    recipe_id INT,
    step_number INT NOT NULL,
    instruction_description TEXT NOT NULL,
    FOREIGN KEY (recipe_id) REFERENCES Recipes(recipe_id) ON DELETE CASCADE
);

-- Create table: Dietary_requirement
CREATE TABLE Dietary_requirement (
    dietary_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- Create table: Dietary_suitability
CREATE TABLE Dietary_suitability (
    recipe_id INT,
    dietary_id INT,
    PRIMARY KEY (recipe_id, dietary_id),
    FOREIGN KEY (recipe_id) REFERENCES Recipes(recipe_id) ON DELETE CASCADE,
    FOREIGN KEY (dietary_id) REFERENCES Dietary_requirement(dietary_id) ON DELETE CASCADE
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
	FOREIGN KEY (unit_id) REFERENCES Units(unit_id) ON DELETE CASCADE
);

-- Create table: Recipe_ingredients
CREATE TABLE Recipe_ingredients (
    recipe_id INT,
    ingredient_id INT,
    quantity DECIMAL(10, 2),
    unit_id INT,
    PRIMARY KEY (recipe_id, ingredient_id),
    FOREIGN KEY (recipe_id) REFERENCES Recipes(recipe_id) ON DELETE CASCADE,
    FOREIGN KEY (ingredient_id) REFERENCES Ingredients(ingredient_id) ON DELETE CASCADE,
    FOREIGN KEY (unit_id) REFERENCES Units(unit_id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE Fridge_contents
(
	item_id integer NOT NULL,
	user_id integer,
	ingredient_id integer,
	quantity integer,
	unit_id integer,
	PRIMARY KEY (item_id),
	FOREIGN KEY (ingredient_id) REFERENCES Ingredients(ingredient_id) ON DELETE CASCADE, 
	FOREIGN KEY (unit_id) REFERENCES Units(unit_id) ON DELETE CASCADE ON UPDATE CASCADE 
	
);
