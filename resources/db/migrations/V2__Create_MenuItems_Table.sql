IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'MenuItems')
BEGIN
    CREATE TABLE MenuItems (
        MenuItemID INT PRIMARY KEY IDENTITY(1,1),
        CategoryID INT NOT NULL,
        Name NVARCHAR(100) NOT NULL,
        Ingredients NVARCHAR(500),
        Price DECIMAL(10, 2) NOT NULL,
        ImageFile NVARCHAR(100),
        CONSTRAINT FK_MenuItems_Categories FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)
    )
END