-- Principals and their SIDs.
INSERT INTO acl_sid (id, principal, sid)
VALUES (1, TRUE, 'admin'),
       (2, TRUE, 'user'),
       (3, TRUE, 'reader');

-- Object classes, covered with ACL protection.
INSERT INTO acl_class (id, class)
VALUES (1, 'de.pim.spring.model.Item');
