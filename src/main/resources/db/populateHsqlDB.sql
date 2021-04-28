DELETE FROM user_roles;
DELETE FROM restaurant_menu;
DELETE FROM users;
DELETE FROM restaurants;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO restaurants (name)
VALUES ('Five Guys'),
       ('Absurd Bird'),
       ('Honi Poke'),
       ('Rosa Thai'),
       ('Eat Active');

INSERT INTO users (name, email, password, restaurant_id)
VALUES ('User', 'user@yandex.ru', '{noop}password', 100001),
       ('Admin', 'admin@gmail.com', '{noop}admin', 100002),
       ('User1', 'user1@yandex.ru', '{noop}password', 100004),
       ('User2', 'user2@yandex.ru', '{noop}password', 100001),
       ('User3', 'user3@yandex.ru', '{noop}password', 100003),
       ('User4', 'user4@yandex.ru', '{noop}password', 100003),
       ('User5', 'user5@yandex.ru', '{noop}password', 100003);

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100005),
       ('ADMIN', 100006),
       ('USER', 100006),
       ('USER', 100007),
       ('USER', 100008),
       ('USER', 100009),
       ('USER', 100010),
       ('USER', 100011);

INSERT INTO restaurant_menu (restaurant_id, name, price)
VALUES (100000, 'Chicken Burger', 12.99),
       (100000, 'Bacon Burger', 10.99),
       (100000, 'Chicken Nugets', 3.50),
       (100000, 'Coca-Cola', 1.99),
       (100000, 'Sprite', 1.99),

       (100001, 'Big Roaster', 11.99),
       (100001, 'Chips', 3.85),
       (100001, 'Chocolate Cake', 5.10),
       (100001, 'Fanta', 1.99),

       (100002, 'Salmon Poke', 5.99),
       (100002, 'Avocado Poke', 5.99),
       (100002, 'Ginger Shot', 5.99),

       (100003, 'Thai Pad', 15.85),
       (100003, 'Thai Beef Pasta', 8.75),
       (100003, 'Thai Menu of The Day', 25.10),
       (100003, 'Thai Drink', 2.99),

       (100004, 'Deal for One', 15.20),
       (100004, 'Potatoes with Avocado', 8.90),
       (100004, 'Beef on Grill', 7.25),
       (100004, 'Lemon Juice', 3.25);

