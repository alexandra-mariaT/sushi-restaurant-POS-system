IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'DailyClosing')
BEGIN
    CREATE TABLE DailyClosing (
        DailyClosingID INT PRIMARY KEY IDENTITY(1,1),
        ClosingDate DATE NOT NULL UNIQUE,
        TotalRevenue DECIMAL(10, 2) NOT NULL,
        TotalOrders INT NOT NULL,
        ClosedByUserID INT NOT NULL
    )
END