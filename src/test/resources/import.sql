INSERT INTO users(user_id, birth_date, email, name, rate, status) VALUES (1, '1995-07-20', 'olya.ivanova@gmail.com', 'Olya', 900.00, 'ACTIVE');
INSERT INTO users(user_id, birth_date, email, name, rate, status) VALUES (2, '2012-01-01', 'vasya12354@gmail.com', 'Vasya', 500.00, 'ACTIVE');
INSERT INTO users(user_id, birth_date, email, name, rate, status) VALUES (3, '2012-01-01', 'ivan2354@gmail.com', 'Ivan', 300.00, 'INACTIVE');

INSERT INTO addresses(address_id, city, street, postal_code, type, user_id) VALUES (1, 'Kyiv', 'K14', '02121', 'WORK', 1);
INSERT INTO addresses(address_id, city, street, postal_code, type, user_id) VALUES (2, 'Kyiv', 'Virmenskya', '04452', 'HOME', 1);
INSERT INTO addresses(address_id, city, street, postal_code, type, user_id) VALUES (3, 'Lviv', 'Peremogy', '12314', 'WORK', 2);

INSERT INTO departments(dep_id, name) VALUES (uuid(), 'Java Solutions 1');
INSERT INTO departments(dep_id, name) VALUES (uuid(), 'Java Solutions 2');
