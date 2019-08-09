DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS res_dishes;
DROP TABLE IF EXISTS taking_parts;
DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS restaurants;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name             VARCHAR                 NOT NULL,
  email            VARCHAR                 NOT NULL,
  password         VARCHAR                 NOT NULL,
  registered       TIMESTAMP DEFAULT now() NOT NULL,
  enabled          BOOL DEFAULT TRUE       NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR                 NOT NULL

);

CREATE TABLE res_dishes
(
    date TIMESTAMP NOT NULL,
    res_id INTEGER NOT NULL,
    name    VARCHAR NOT NULL,
    price INTEGER NOT NULL ,
    UNIQUE (res_id,name) ,
    FOREIGN KEY (res_id) REFERENCES restaurants (id) ON DELETE CASCADE
);


CREATE TABLE votes
(
    res_id INTEGER NOT NULL,
    date TIMESTAMP DEFAULT now() NOT NULL,
    user_id INTEGER NOT NULL ,
    UNIQUE (user_id,date) ,
    FOREIGN KEY (res_id) REFERENCES restaurants (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);


