DROP TABLE user_roles IF EXISTS;
DROP TABLE votes IF EXISTS;
DROP TABLE menu IF EXISTS;
DROP TABLE users IF EXISTS;
DROP TABLE restaurants IF EXISTS;
DROP SEQUENCE global_seq IF EXISTS;

CREATE SEQUENCE GLOBAL_SEQ AS INTEGER START WITH 100000;

CREATE TABLE restaurants
(
    id   INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT restaurant_name_idx UNIQUE (name)
);

CREATE TABLE menu
(
    id    INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    restaurant_id INTEGER         NOT NULL,
    name  VARCHAR(255)    NOT NULL,
    price DECIMAL(128, 2) NOT NULL,
    menu_item_add_date DATE    NOT NULL,
    CONSTRAINT menu_item_name_idx UNIQUE (restaurant_id, menu_item_add_date, name),
    FOREIGN KEY (restaurant_id) REFERENCES RESTAURANTS (id) ON DELETE CASCADE
);

CREATE TABLE users
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name          VARCHAR(255)            NOT NULL,
    email         VARCHAR(255)            NOT NULL,
    password      VARCHAR(255)            NOT NULL,
    registered    TIMESTAMP DEFAULT now() NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON USERS (email);


CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE votes
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    user_id       INTEGER NOT NULL,
    restaurant_id INTEGER NOT NULL,
    voted_date    DATE,
    CONSTRAINT unique_user_id_voted_date_idx UNIQUE (user_id, voted_date),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES RESTAURANTS (id) ON DELETE CASCADE
);
