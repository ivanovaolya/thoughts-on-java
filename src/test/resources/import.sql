INSERT INTO users(user_id, birth_date, email, name) VALUES (1, '1995-07-20', 'olya.ivanova@gmail.com', 'Olya');
INSERT INTO users(user_id, birth_date, email, name) VALUES (2, '2012-01-01', 'vasya12354@gmail.com', 'Vasya');
INSERT INTO users(user_id, birth_date, email, name) VALUES (3, '2012-01-01', 'ivan2354@gmail.com', 'Ivan');

INSERT INTO addresses(address_id, city, street, postal_code, user_id) VALUES (1, 'Kyiv', 'K14', '02121', 1);
INSERT INTO addresses(address_id, city, street, postal_code, user_id) VALUES (2, 'Kyiv', 'Virmenskya', '04452', 1);
INSERT INTO addresses(address_id, city, street, postal_code, user_id) VALUES (3, 'Lviv', 'Peremogy', '12314', 2);