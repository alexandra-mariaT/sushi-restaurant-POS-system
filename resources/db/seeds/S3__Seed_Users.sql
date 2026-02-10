IF NOT EXISTS (SELECT 1 FROM Users WHERE UserName = 'Alexandra')
    INSERT INTO Users (UserName, PinCode, UserRole) VALUES ('Alexandra', '9999', 'Admin');

IF NOT EXISTS (SELECT 1 FROM Users WHERE UserName = 'Mihai')
    INSERT INTO Users (UserName, PinCode, UserRole) VALUES ('Mihai', '1234', 'Ospatar');

IF NOT EXISTS (SELECT 1 FROM Users WHERE UserName = 'Ioana')
    INSERT INTO Users (UserName, PinCode, UserRole) VALUES ('Ioana', '5678', 'Ospatar');