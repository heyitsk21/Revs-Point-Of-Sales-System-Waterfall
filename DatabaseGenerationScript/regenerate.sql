DROP TABLE IF EXISTS Employee CASCADE;
DROP TABLE IF EXISTS Ingredients CASCADE;
DROP TABLE IF EXISTS MenuItems CASCADE;
DROP TABLE IF EXISTS Orders CASCADE;
DROP TABLE IF EXISTS InventoryLog CASCADE;
DROP TABLE IF EXISTS MenuItemIngredients CASCADE;
DROP TABLE IF EXISTS OrderMenuItems CASCADE;


--CREATE TABLES AND JUNCTIONTABLE BELOW
-- Create Ingredients table
CREATE TABLE Ingredients (
    IngredientID SERIAL PRIMARY KEY,
    IngredientName VARCHAR(100),
    PPU NUMERIC(10, 2), -- I used NUMERIC (10,2) because it cuts us off to 2 decimal places for money.
    Count INT
);

-- Create MenuItems table
CREATE TABLE MenuItems (
    MenuID SERIAL PRIMARY KEY,
    ItemName VARCHAR(100),
    Price NUMERIC(10, 2) -- Same as above ^
);

-- Create Employee table
CREATE TABLE Employee (
    EmployeeID SERIAL PRIMARY KEY,
    EmployeeName VARCHAR(100),
    IsManager BOOLEAN,
    Salary NUMERIC(10, 2), -- Same as above ^
    Password VARCHAR(100)
);

-- Create Order table
CREATE TABLE Orders (
    OrderID SERIAL PRIMARY KEY,
    CustomerName VARCHAR(100),
    TaxPrice NUMERIC(10, 2), -- Same as above ^
    BasePrice NUMERIC(10, 2), -- Same as above ^
    OrderDateTime TIMESTAMP,
    EmployeeID INT, 
    CONSTRAINT fk_employee
        FOREIGN KEY(EmployeeID) 
        REFERENCES Employee(EmployeeID)
);

-- Create InventoryLog table
CREATE TABLE InventoryLog (
    LogID SERIAL PRIMARY KEY,
    IngredientID INT,
    AmountChanged NUMERIC(10, 2), -- Same as above ^
    LogMessage TEXT,
    LogDateTime TIMESTAMP,
    CONSTRAINT fk_ingredient
        FOREIGN KEY(IngredientID) 
        REFERENCES Ingredients(IngredientID)
);

-- Create MenuItemIngredients junction table
CREATE TABLE MenuItemIngredients (
    MenuID INT ,
    IngredientID INT,
    PRIMARY KEY (MenuID, IngredientID),
    CONSTRAINT fk_ingredient
        FOREIGN KEY(IngredientID) 
        REFERENCES Ingredients(IngredientID),
    CONSTRAINT fk_menu
        FOREIGN KEY(MenuID) 
        REFERENCES MenuItems(MenuID)
);


-- Create OrderMenuItems junction table
CREATE TABLE OrderMenuItems (
    JoinID SERIAL,
    OrderID INT,
    MenuID INT,
    PRIMARY KEY (JoinID),
    CONSTRAINT fk_menu
        FOREIGN KEY(MenuID) 
        REFERENCES MenuItems(MenuID),
    CONSTRAINT fk_order
        FOREIGN KEY(OrderID) 
        REFERENCES Orders(OrderID)   
);


--COPY CHUNKS BELOW
-- Copy data from CSV files into Ingredients tables
\COPY Ingredients (IngredientID, IngredientName, PPU, Count) FROM 'DatabaseGenerationScript/Ingredients.csv' DELIMITER ',' CSV HEADER;

-- Copy data from CSV files into MenuItems tables
\COPY MenuItems (MenuID, ItemName, Price) FROM 'DatabaseGenerationScript/MenuItems.csv' DELIMITER ',' CSV HEADER;

-- Copy data from CSV files into MenuItemIngredients tables
\COPY MenuItemIngredients (MenuID, IngredientID) FROM 'DatabaseGenerationScript/MenuItemsIngredients.csv' DELIMITER ',' CSV HEADER;

-- Copy data from CSV files into Employee tables
\COPY Employee (EmployeeID, EmployeeName, IsManager, Salary, Password) FROM 'DatabaseGenerationScript/Employee.csv' DELIMITER ',' CSV HEADER;

-- Copy data from CSV files into Order tables
\COPY Orders (OrderID, CustomerName, TaxPrice, BasePrice, OrderDateTime, EmployeeID) FROM 'DatabaseGenerationScript/Orders.csv' DELIMITER ',' CSV HEADER;

-- Copy data from CSV files into InventoryLog tables
\COPY InventoryLog (LogID, IngredientID, AmountChanged, LogMessage, LogDateTime) FROM 'DatabaseGenerationScript/InventoryLog.csv' DELIMITER ',' CSV HEADER;

\COPY OrderMenuItems  (OrderID, MenuID) FROM 'DatabaseGenerationScript/JunctionOrdersMenu.csv' DELIMITER ',' CSV HEADER;

-- For demo, Josephs path: \i C:/Users/jnucc/Desktop/CSCE/project-2-database-gui-group-21/DatabaseGenerationScript/regenerate.sql