DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
('User', 'user@yandex.ru', 'password'),
('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
('ROLE_USER', 100000),
('ROLE_ADMIN', 100001);

INSERT INTO restaurants (name) VALUES ('R1'),('R2'),('R3'),('RWM');
INSERT INTO res_dishes(name,price,date,res_id) VALUES
                                                      ('D',100,'2015-05-30',100005),
                                                      ('D2',200,'2015-05-29',100005);




