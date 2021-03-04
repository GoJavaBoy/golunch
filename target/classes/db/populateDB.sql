DELETE FROM user_roles;
DELETE FROM restaurant_votes;
DELETE FROM restaurant_menu;
DELETE FROM users;
DELETE FROM restaurants;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('User1', 'user1@yandex.ru', 'password'),
       ('User2', 'user2@yandex.ru', 'password'),
       ('User3', 'user3@yandex.ru', 'password'),
       ('User4', 'user4@yandex.ru', 'password'),
       ('User5', 'user5@yandex.ru', 'password');

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
VALUES (100007, 'Chicken Burger', 12.99),
       (100007, 'Bacon Burger', 10.99),
       (100007, 'Chicken Nugets', 3.50),
       (100007, 'Coca-Cola', 1.99),
       (100007, 'Sprite', 1.99),

       (100008, 'Big Roaster', 11.99),
       (100008, 'Chips', 3.85),
       (100008, 'Chocolate Cake', 5.10),
       (100008, 'Fanta', 1.99),

       (100009, 'Salmon Poke', 5.99),
       (100009, 'Avokado Poke', 5.99),
       (100009, 'Ginger Shot', 5.99),

       (100010, 'Thai Pad', 15.85),
       (100010, 'Thai Beef Pasta', 8.75),
       (100010, 'Thai Menu of The Day', 25.10),
       (100010, 'Thai Drink', 2.99),

       (100011, 'Deal for One', 15.20),
       (100011, 'Potatoes with Avokado', 8.90),
       (100011, 'Beaf on Grill', 7.25),
       (100011, 'Lemon Juice', 3.25);

INSERT INTO restaurant_votes (restaurant_id, voted_user_id)
VALUES (100007, 100000),
       (100008, NULL),
       (100009, NULL),
       (100010, NULL),
       (100011, NULL);

