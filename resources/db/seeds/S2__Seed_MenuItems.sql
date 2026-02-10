SET IDENTITY_INSERT MenuItems ON;

INSERT INTO MenuItems (MenuItemID, Name, Price, Ingredients, ImageFile, CategoryID)
SELECT T.ID, T.Name, T.Price, T.Ingredients, T.Img, T.CatID
FROM (
    VALUES
    (1, 'Sushi Ton Roll', 45.00, 'rice tuna avocado cucumber mayo tobiko chives', 'sushi_ton_roll.jpg', 1),
    (2, 'Sushi Salmon Philadelphia Roll', 35.00, 'rice salmon philadelphia mayo cucumber sesame', 'salmon_philadelphia.jpg', 1),
    (3, 'Sushi Shrimp Roll', 40.00, 'rice shrimp mayo chives radish tobiko', 'sushi_shrimp.jpg', 1),
    (4, 'Teriyaki Roll', 35.00, 'rice salmon teriyaki cucumber radish sesame', 'teriyaki_roll.jpg', 1),
    (5, 'Veggie Roll', 35.00, 'rice tofu cucumber radish wasabi', 'veggie_roll.jpg', 1),
    (6, 'Salmon Maki', 26.00, 'rice salmon wasabi', 'salmon_maki.jpg', 2),
    (7, 'Tuna Maki', 26.00, 'rice tuna wasabi', 'tuna_maki.jpg', 2),
    (8, 'Shrimp Maki', 26.00, 'rice shrimp wasabi', 'shrimp_maki.jpg', 2),
    (9, 'Salmon Nigiri', 8.00, 'rice salmon', 'salmon_nigiri.jpg', 3),
    (10, 'Fire Salmon Nigiri', 10.00, 'rice salmon teriyaki', 'fire_salmon_nigiri.jpg', 3),
    (11, 'Tuna Nigiri', 13.00, 'rice tuna wasabi', 'tuna_nigiri.jpg', 3),
    (12, 'Shrimp Nigiri', 15.00, 'rice shrimp', 'shrimp_nigiri.jpg', 3),
    (13, 'Unagi Nigiri', 20.00, 'rice unagi', 'unagi_nigiri.jpg', 3),
    (14, 'Beef Nigiri', 20.00, 'rice beef wasabi chives', 'beef_nigiri.jpg', 3),
    (15, 'Ramen Miso Chicken', 38.00, 'chicken noodles tobiko egg sesame chives', 'ramen_chicken.jpg', 4),
    (16, 'Ramen Miso Pork', 38.00, 'pork noodles tobiko egg sesame chives', 'ramen_pork.jpg', 4),
    (17, 'Ramen Miso Fish', 38.00, 'salmon noodles tobiko egg sesame chives', 'ramen_fish.jpg', 4),
    (18, 'Espresso', 10.00, 'cafea, apa', 'espresso.jpg', 5),
    (19, 'Cappuccino', 12.00, 'cafea, lapte, spuma', 'cappuccino.jpg', 5),
    (20, 'Flat White', 13.00, 'cafea, lapte', 'flat_white.jpg', 5),
    (21, 'Latte', 16.00, 'cafea, lapte, spuma', 'latte.jpg', 5),
    (22, 'Pepsi', 10.00, 'soda', 'pepsi.jpg', 6),
    (23, 'Mirinda', 10.00, 'soda', 'mirinda.jpg', 6),
    (24, 'Prigat', 10.00, 'suc de fructe', 'prigat.jpg', 6),
    (25, 'Sprite', 10.00, 'soda', 'sprite.jpg', 6),
    (26, 'Evervess', 10.00, 'soda', 'evervess.jpg', 6),
    (27, 'Lipton', 10.00, 'ceai rece', 'lipton.jpg', 6)
) AS T(ID, Name, Price, Ingredients, Img, CatID)
WHERE NOT EXISTS (SELECT 1 FROM MenuItems WHERE MenuItemID = T.ID);

SET IDENTITY_INSERT MenuItems OFF;