SET IDENTITY_INSERT Categories ON;

INSERT INTO Categories (CategoryID, CategoryName, IsFood)
SELECT 1, 'Rolls', 1 WHERE NOT EXISTS (SELECT 1 FROM Categories WHERE CategoryID = 1);
INSERT INTO Categories (CategoryID, CategoryName, IsFood)
SELECT 2, 'Maki', 1 WHERE NOT EXISTS (SELECT 1 FROM Categories WHERE CategoryID = 2);
INSERT INTO Categories (CategoryID, CategoryName, IsFood)
SELECT 3, 'Nigiri', 1 WHERE NOT EXISTS (SELECT 1 FROM Categories WHERE CategoryID = 3);
INSERT INTO Categories (CategoryID, CategoryName, IsFood)
SELECT 4, 'Ramen', 1 WHERE NOT EXISTS (SELECT 1 FROM Categories WHERE CategoryID = 4);
INSERT INTO Categories (CategoryID, CategoryName, IsFood)
SELECT 5, 'Coffee', 0 WHERE NOT EXISTS (SELECT 1 FROM Categories WHERE CategoryID = 5);
INSERT INTO Categories (CategoryID, CategoryName, IsFood)
SELECT 6, 'Refreshers', 0 WHERE NOT EXISTS (SELECT 1 FROM Categories WHERE CategoryID = 6);

SET IDENTITY_INSERT Categories OFF;