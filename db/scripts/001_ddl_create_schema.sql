CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    email    VARCHAR NOT NULL UNIQUE,
    phone    VARCHAR NOT NULL UNIQUE
);

ALTER TABLE users
    ADD CONSTRAINT username_email_phone_unique UNIQUE (username, email, phone);

CREATE TABLE tickets
(
    id      SERIAL PRIMARY KEY,
    film_id INT NOT NULL REFERENCES films (id),
    row     INT NOT NULL,
    cell    INT NOT NULL,
    user_id INT NOT NULL REFERENCES users (id)
);

ALTER TABLE tickets
    ADD CONSTRAINT session_id_row_cell UNIQUE (film_id, row, cell);

CREATE TABLE films
(
    id             SERIAL PRIMARY KEY,
    name           VARCHAR(256),
    productionYear VARCHAR(256),
    poster         bytea
);

CREATE TABLE halls
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    row  INT     NOT NULL,
    cell INT     NOT NULL
);