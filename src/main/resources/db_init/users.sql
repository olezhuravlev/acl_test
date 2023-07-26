INSERT INTO users(id, username, password, enabled)
VALUES (1, 'admin', '$2a$04$6DHBRTbJAwZHcSW6wq9RhuMDt5MI3rOHWHVRkBtMQwcuN3g/OFrvy', true), -- 'admin'
       (2, 'user', '$2a$04$/JjwEIOvaWY.fhd5.afLaOR/E1J./8N.FP81W3jjM3dvvmG1ESO7u', true),  -- 'user'
       (3, 'reader', '$2a$04$pXuNYkVdb2AlOLSl8mxBB.Y3pX0KUMrv1moeIYk8Cyexn3Q6EZyxi', true); -- 'reader'

INSERT INTO authorities(id, authority, username)
VALUES (1, 'ROLE_ADMIN', 'admin'),
       (2, 'ROLE_USER', 'user'),
       (3, 'ROLE_READER', 'reader');
