CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR NOT NULL,
    email    VARCHAR NOT NULL UNIQUE,
    phone    VARCHAR NOT NULL UNIQUE
);

ALTER TABLE users
    ADD CONSTRAINT username_email_phone_unique UNIQUE (username, email, phone);

CREATE TABLE sessions
(
    id   SERIAL PRIMARY KEY,
    name text
);

CREATE TABLE ticket
(
    id         SERIAL PRIMARY KEY,
    session_id INT NOT NULL REFERENCES sessions (id),
    row        INT NOT NULL,
    cell       INT NOT NULL,
    user_id    INT NOT NULL REFERENCES users (id)
);

ALTER TABLE ticket
    ADD CONSTRAINT session_id_row_cell UNIQUE (session_id, row, cell);

CREATE TABLE films
(
    id             SERIAL PRIMARY KEY,
    name           VARCHAR(256),
    productionYear VARCHAR(256),
    poster         bytea
);