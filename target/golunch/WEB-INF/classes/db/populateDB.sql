DELETE FROM user_roles;
DELETE FROM restaurant_votes;
DELETE FROM restaurant_menu;
DELETE FROM users;
DELETE FROM restaurants;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO restaurants (name)
VALUES ('Five Guys'),
       ('Absurd Bird'),
       ('Honi Poke'),
       ('Rosa Thai'),
       ('Eat Active');

INSERT INTO restaurant_menu (restaurant_id, item_map_key, price_map_value)
VALUES (100002, 'Chicken Burger', 12.99),
       (100002, 'Bacon Burger', 10.99),
       (100002, 'Chicken Nugets', 3.50),
       (100002, 'Coca-Cola', 1.99),
       (100002, 'Sprite', 1.99),

       (100003, 'Big Roaster', 11.99),
       (100003, 'Chips', 3.85),
       (100003, 'Chocolate Cake', 5.10),
       (100003, 'Fanta', 1.99),

       (100004, 'Salmon Poke', 5.99),
       (100004, 'Avokado Poke', 5.99),
       (100004, 'Ginger Shot', 5.99),

       (100005, 'Thai Pad', 15.85),
       (100005, 'Thai Beef Pasta', 8.75),
       (100005, 'Thai Menu of The Day', 25.10),
       (100005, 'Thai Drink', 2.99),

       (100006, 'Deal for One', 15.20),
       (100006, 'Potatoes with Avokado', 8.90),
       (100006, 'Beaf on Grill', 7.25),
       (100006, 'Lemon Juice', 3.25);

INSERT INTO restaurant_votes (restaurant_id, voted_user_id)
VALUES (100002, 100000),
       (100003, NULL),
       (100004, NULL),
       (100005, NULL),
       (100006, NULL);

