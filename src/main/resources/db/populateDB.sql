DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM users;
DELETE FROM res_dishes;
DELETE FROM restaurants;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
('User', 'user@yandex.ru', '{noop}password'),
('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id) VALUES
('ROLE_USER', 100000),
('ROLE_ADMIN', 100001);

INSERT INTO restaurants (name) VALUES ('R1'),('R2'),('R3'),('RWM');
INSERT INTO res_dishes(name,price,date,res_id) VALUES
                                                      ('Pizza',100,'2015-05-30',100005),
                                                      ('Cake',200,'2015-05-29',100005);

INSERT INTO votes(res_id,date,user_id) VALUES
(100005,'2015-05-30',100000),
(100005,'2015-05-30',100001);




