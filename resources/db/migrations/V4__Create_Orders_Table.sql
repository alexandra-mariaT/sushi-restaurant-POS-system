IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Orders')
BEGIN
    CREATE TABLE Orders (
        OrderID INT PRIMARY KEY IDENTITY(1,1),
        TableNumber INT NOT NULL,
        TotalAmount DECIMAL(10, 2) NOT NULL,
        OrderDate DATETIME DEFAULT GETDATE(),
        Status NVARCHAR(20) DEFAULT 'Pending'
    )
END