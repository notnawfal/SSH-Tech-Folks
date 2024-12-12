# SSH-Tech-Folks
Repository to work on a recipe suggestion feature embedded into the SSH App which uses the database populated by the SSH Camera and the SSH Cloud, to recommend meals based on the dietary preferences and available ingredients. It simplifies meal planning for university students by generating recipes. The project involves the usage of Java and SQL.

Explanation for each files.

Credentials stores sql user detials to connect to the database. It includes the database URL, username and password. This information is used to access the recipe database and is been used in other java files.

RecipeFilterApp Creates a UI window which displays checkboxes for users to select their preferences and provide them with recipes based on those preferences. At the end, it returns a list of recipe IDs based on the ingredients available in the fridge of the user.

RecipeFilterTesting is used to tests different filters for recipe sereaches. It checks filters like meal type, dietary restrictions, protein options and cooking time. The program builds SQL queries based on the selected filters to ensure they work correctly. 

RecipeSelectionUI displays a table of recipes which users can click on a recipe to see the detailed preparation. 

PreparationOfRecipes creates a new window that displays step-by-syep instructions for the selected recipe. 

pom.xml is a configuration file for a Maven project. It sets up Java project with dependencies like JavaFX for creating GUI and PostgreSQL for database connection. It also includes settings for compiling and running the project. 

populateRecipeDatabase this populate the database for out tests ( this is created by AI ).

recipe database query creates for recipes and sets up tables for storing recipes, intructions, dietary requirements, ingredients and fridge contents, with relationship between them to ensure data is well-organized and connected.

