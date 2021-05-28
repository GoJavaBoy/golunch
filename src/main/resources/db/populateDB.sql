DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM menu;
DELETE FROM users;
DELETE FROM restaurants;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO restaurants (name)
VALUES ('Five Guys'),
       ('Absurd Bird'),
       ('Honi Poke'),
       ('Rosa Thai'),
       ('Eat Active');

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('User1', 'user1@yandex.ru', '{noop}password'),
       ('User2', 'user2@yandex.ru', '{noop}password'),
       ('User3', 'user3@yandex.ru', '{noop}password'),
       ('User4', 'user4@yandex.ru', '{noop}password'),
       ('User5', 'user5@yandex.ru', '{noop}password');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100005),
       ('ADMIN', 100006),
       ('USER', 100006),
       ('USER', 100007),
       ('USER', 100008),
       ('USER', 100009),
       ('USER', 100010),
       ('USER', 100011);

INSERT INTO menu (restaurant_id, name, price, dish_add_date)
VALUES (100000, 'Chicken Burger', 12.99, '2021-05-28 17:07:43'),
       (100000, 'Bacon Burger', 10.99, '2021-05-28 16:07:43'),
       (100000, 'Chicken Nugets', 3.50, '2021-05-28 15:07:43'),
       (100000, 'Coca-Cola', 1.99, '2021-05-28 14:07:43'),
       (100000, 'Sprite', 1.99, '2021-05-28 13:07:43'),

       (100001, 'Big Roaster', 11.99, '2021-05-28 17:07:43'),
       (100001, 'Chips', 3.85, '2021-05-28 17:07:43'),
       (100001, 'Chocolate Cake', 5.10, '2021-05-28 17:07:43'),
       (100001, 'Fanta', 1.99, '2021-05-28 17:07:43'),

       (100002, 'Salmon Poke', 5.99, '2021-05-28 17:07:43'),
       (100002, 'Avocado Poke', 5.99, '2021-05-28 17:07:43'),
       (100002, 'Ginger Shot', 5.99, '2021-05-28 17:07:43'),

       (100003, 'Thai Pad', 15.85, '2021-05-28 17:07:43'),
       (100003, 'Thai Beef Pasta', 8.75, '2021-05-28 17:07:43'),
       (100003, 'Thai Menu of The Day', 25.10, '2021-05-28 17:07:43'),
       (100003, 'Thai Drink', 2.99, '2021-05-28 17:07:43'),

       (100004, 'Deal for One', 15.20, '2021-05-28 17:07:43'),
       (100004, 'Potatoes with Avocado', 8.90, '2021-05-28 17:07:43'),
       (100004, 'Beef on Grill', 7.25, '2021-05-28 17:07:43'),
       (100004, 'Lemon Juice', 3.25, '2021-05-28 17:07:43');

INSERT INTO votes (user_id, restaurant_id, voted_date)
VALUES (100005, 100000, '2021-05-28 17:07:43'),
       (100006, 100000, '2021-05-28 17:07:43'),
       (100007, 100000, '2021-05-28 17:07:43'),
       (100011, 100004, '2021-05-28 17:07:43'),
       (100008, 100003, '2021-05-28 17:07:43'),
       (100009, 100002, '2021-05-28 17:07:43');
