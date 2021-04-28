DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS restaurant_menu;
DROP TABLE IF EXISTS restaurant_menu_history;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS restaurants_history;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE restaurants
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR                           NOT NULL,
    CONSTRAINT restaurant_name_idx UNIQUE (name)
);

CREATE TABLE restaurants_history
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR                           NOT NULL,
    saved            TIMESTAMP,
    votes            INTEGER
);

CREATE TABLE restaurant_menu
(
    restaurant_id INTEGER NOT NULL,
    name VARCHAR NOT NULL,
    price DECIMAL NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES RESTAURANTS (id) ON DELETE CASCADE
);

CREATE TABLE restaurant_menu_history
(
    restaurant_id INTEGER NOT NULL,
    name VARCHAR NOT NULL,
    price DECIMAL NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES RESTAURANTS_HISTORY (id) ON DELETE CASCADE
);

CREATE TABLE users
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR            NOT NULL,
    email            VARCHAR            NOT NULL,
    password         VARCHAR            NOT NULL,
    registered       TIMESTAMP DEFAULT now() NOT NULL,
    voted            TIMESTAMP,
    restaurant_id    INTEGER,
    FOREIGN KEY (restaurant_id) REFERENCES RESTAURANTS (id) ON DELETE SET NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON USERS (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);
