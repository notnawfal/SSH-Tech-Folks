

CREATE TABLE Recipes (
    recipe_id SERIAL PRIMARY KEY,
    dish_name VARCHAR(255) NOT NULL,
    meal_type VARCHAR(100),
    protein VARCHAR(100),
    cooking_time INT,
    img_url TEXT
);

-- Create table: Recipe_ingredients
CREATE TABLE Recipe_ingredients (
    recipe_id INT,
    ingredient_id INT,
    quantity DECIMAL(10, 2),
    unit_id INT,
    PRIMARY KEY (recipe_id, ingredient_id),
    FOREIGN KEY (recipe_id) REFERENCES Recipes(recipe_id) ON DELETE CASCADE
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