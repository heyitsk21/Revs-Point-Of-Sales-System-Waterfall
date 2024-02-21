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
CREATE TABLE Order (
    OrderID SERIAL PRIMARY KEY,
    CustomerName VARCHAR(100),
    TaxPrice NUMERIC(10, 2), -- Same as above ^
    BasePrice NUMERIC(10, 2), -- Same as above ^
    OrderDateTime TIMESTAMP,
    EmployeeID INT REFERENCES Employee(EmployeeID)
);

-- Create InventoryLog table
CREATE TABLE InventoryLog (
    LogID SERIAL PRIMARY KEY,
    IngredientID INT REFERENCES Ingredients(IngredientID),
    AmountChanged NUMERIC(10, 2), -- Same as above ^
    LogMessage TEXT,
    LogDateTime TIMESTAMP
);

-- Create MenuItemIngredients junction table
CREATE TABLE MenuItemIngredients (
    MenuID INT REFERENCES MenuItems(MenuID),
    IngredientID INT REFERENCES Ingredients(IngredientID),
    PRIMARY KEY (MenuID, IngredientID)
);


-- Create OrderMenuItems junction table
CREATE TABLE OrderMenuItems (
    OrderID INT REFERENCES Order(OrderID),
    MenuID INT REFERENCES MenuItems(MenuID),
    PRIMARY KEY (OrderID, MenuID)
);


--COPY CHUNKS BELOW
-- Copy data from CSV files into Ingredients tables
COPY Ingredients (IngredientID, IngredientName, PPU, Count)
FROM 'DatabaseGenerationScript/Ingredients.csv' DELIMITER ',' CSV HEADER;

-- Copy data from CSV files into MenuItemIngredients tables
COPY MenuItemIngredients (MenuID, IngredientID)
FROM 'DatabaseGenerationScript/MenuItemIngredients.csv' DELIMITER ',' CSV HEADER;

-- Copy data from CSV files into MenuItems tables
COPY MenuItems (MenuID, ItemName, Price)
FROM 'DatabaseGenerationScript/MenuItems.csv' DELIMITER ',' CSV HEADER;

-- Copy data from CSV files into Employee tables
COPY Employee (EmployeeID, EmployeeName, IsManager, Salary, Password)
FROM 'DatabaseGenerationScript/Employee.csv' DELIMITER ',' CSV HEADER;

-- Copy data from CSV files into Order tables
COPY Order (OrderID, CustomerName, TaxPrice, BasePrice, OrderDateTime, EmployeeID)
FROM 'DatabaseGenerationScript/Orders.csv' DELIMITER ',' CSV HEADER;

-- Copy data from CSV files into InventoryLog tables
COPY InventoryLog (LogID, IngredientID, AmountChanged, "Log message", LogDateTime)
FROM 'DatabaseGenerationScript/InventoryLog.csv' DELIMITER ',' CSV HEADER;

COPY OrderMenuItems  (OrderID, MenuID)
FROM 'DatabaseGenerationScript/JunctionOrdersMenu.csv' DELIMITER ',' CSV HEADER;
