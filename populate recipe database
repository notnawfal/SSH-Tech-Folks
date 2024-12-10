-- Switch to Recipe_database
\c Recipe_database

-- Insert data into Recipes
INSERT INTO Recipes (recipe_id, dish_name, meal_type, protein, cooking_time, img_url)
VALUES
(1,'Spaghetti Bolognese', 'Dinner', 'Beef', 45, 'https://example.com/spaghetti.jpg'),
(2,'Vegetarian Curry', 'Lunch', 'Tofu', 30, 'https://example.com/veg-curry.jpg'),
(3,'Chicken Caesar Salad', 'Lunch', 'Chicken', 20, 'https://example.com/caesar-salad.jpg'),
(4,'Grilled Salmon', 'Dinner', 'Salmon', 25, 'https://example.com/salmon.jpg'),
(5,'Pancakes', 'Breakfast', 'None', 15, 'https://example.com/pancakes.jpg'),
(6,'Fruit Salad', 'Snack', 'None', 10, NULL),
(7,'Almond Smoothie', 'Beverage', 'Almond', 5, 'https://example.com/smoothie.jpg');

-- Insert data into Instructions
INSERT INTO Instructions (instruction_id, recipe_id, step_number, instruction_description)
VALUES
(1,1, 1, 'Boil water and cook spaghetti until al dente.'),
(2,1, 2, 'Heat oil, cook onions and garlic, then add ground beef.'),
(3,1, 3, 'Add tomato sauce and simmer for 20 minutes.'),
(4,2, 1, 'Heat oil and fry onions and spices.'),
(5,2, 2, 'Add vegetables and tofu, then cook for 15 minutes.'),
(6,3, 1, 'Grill chicken breast until golden.'),
(7,3, 2, 'Toss lettuce, croutons, and Caesar dressing.'),
(8,3, 3, 'Top with grilled chicken and Parmesan cheese.'),
(9,4, 1, 'Season salmon fillets and grill for 15 minutes.'),
(10,4, 2, 'Serve with lemon slices and steamed vegetables.'),
(11,5, 1, 'Mix flour, milk, and eggs into a smooth batter.'),
(12,5, 2, 'Cook batter on a hot pan until golden brown.'),
(13,6, 1, 'Chop all fruits and mix in a bowl.'),
(14,6, 2, 'Serve fresh or chilled.'),
(15,7, 1, 'Blend almond milk, banana, and honey until smooth.'),
(16,7, 2, 'Serve in a glass with ice.');

-- Insert data into Dietary_requirement
INSERT INTO Dietary_requirement (dietary_id, name)
VALUES
(1,'Vegetarian'),
(2,'Vegan'),
(3,'Gluten-Free'),
(4,'Nut-Free'),
(5,'Dairy-Free'),
(6,'Paleo');

-- Insert data into Dietary_suitability
INSERT INTO Dietary_suitability (recipe_id, dietary_id)
VALUES
(2, 1), -- Vegetarian Curry is suitable for Vegetarians
(2, 2), -- Vegetarian Curry is also suitable for Vegans
(3, 4), -- Chicken Caesar Salad is Nut-Free
(4, 6), -- Grilled Salmon is Paleo
(5, 4), -- Pancakes are Nut-Free
(5, 3), -- Pancakes are Gluten-Free
(6, 1), -- Fruit Salad is Vegetarian
(6, 2), -- Fruit Salad is Vegan
(7, 1), -- Almond Smoothie is Vegetarian
(7, 3); -- Almond Smoothie is Gluten-Free;

-- Insert data into Units
INSERT INTO Units (unit_id, name)
VALUES
(1, 'grams'),
(2, 'milliliters'),
(3, 'pieces'),
(4, 'cups'),
(5, 'tablespoons');

-- Insert data into Ingredients
INSERT INTO Ingredients (ingredient_id, name, unit_id)
VALUES
(1, 'Spaghetti', 1),
(2, 'Ground Beef', 1),
(3, 'Tomato Sauce', 2),
(4, 'Tofu', 1),
(5, 'Chicken Breast', 1),
(6, 'Salmon Fillet', 1),
(7, 'Flour', 1),
(8, 'Milk', 2),
(9, 'Eggs', 3),
(10, 'Apple', 3),
(11, 'Banana', 3),
(12, 'Orange', 3),
(13, 'Almond Milk', 2),
(14, 'Honey', 5);

-- Insert data into Recipe_ingredients
INSERT INTO Recipe_ingredients (recipe_id, ingredient_id, quantity, unit_id)
VALUES
(1, 1, 250, 1), -- 250 grams of Spaghetti for Spaghetti Bolognese
(1, 2, 200, 1), -- 200 grams of Ground Beef
(1, 3, 300, 2), -- 300 milliliters of Tomato Sauce
(2, 4, 200, 1), -- 200 grams of Tofu for Vegetarian Curry
(3, 5, 150, 1), -- 150 grams of Chicken Breast for Caesar Salad
(4, 6, 200, 1), -- 200 grams of Salmon Fillet for Grilled Salmon
(5, 7, 100, 1), -- 100 grams of Flour for Pancakes
(5, 8, 200, 2), -- 200 milliliters of Milk
(5, 9, 2, 3), -- 2 Eggs
(6, 10, 1, 3), -- 1 Apple for Fruit Salad
(6, 11, 1, 3), -- 1 Banana for Fruit Salad
(6, 12, 1, 3), -- 1 Orange for Fruit Salad
(7, 13, 200, 2), -- 200 milliliters of Almond Milk for Smoothie
(7, 11, 1, 3), -- 1 Banana for Smoothie
(7, 14, 2, 5); -- 2 Tablespoons of Honey

-- Insert data into Fridge_contents
INSERT INTO Fridge_contents (item_id, user_id, ingredient_id, quantity, unit_id)
VALUES
(1, 1, 1, 500, 1), -- User 1 has 500 grams of Spaghetti
(2, 1, 2, 300, 1), -- User 1 has 300 grams of Ground Beef
(3, 2, 7, 500, 1), -- User 2 has 500 grams of Flour
(4, 2, 9, 6, 3), -- User 2 has 6 Eggs
(5, 1, 10, 3, 3), -- User 1 has 3 Apples
(6, 2, 13, 500, 2); -- User 2 has 500 milliliters of Almond Milk
