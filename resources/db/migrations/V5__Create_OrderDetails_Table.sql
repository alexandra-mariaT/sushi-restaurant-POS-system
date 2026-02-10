IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'OrderDetails')
BEGIN
    CREATE TABLE OrderDetails (
        OrderDetailID INT PRIMARY KEY IDENTITY(1,1),
        OrderID INT NOT NULL,
        MenuItemID INT NOT NULL,
        Quantity INT NOT NULL DEFAULT 1,
        UnitPrice DECIMAL(10, 2) NOT NULL,
        CONSTRAINT FK_OrderDetails_Orders FOREIGN KEY (OrderID) REFERENCES Orders(OrderID) ON DELETE CASCADE,
        CONSTRAINT FK_OrderDetails_MenuItems FOREIGN KEY (MenuItemID) REFERENCES MenuItems(MenuItemID)
    )
END