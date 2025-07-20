-- Insert sample users if table is empty
INSERT INTO `user` (`username`, `email`)
SELECT 'admin', 'admin@example.com'
WHERE NOT EXISTS (SELECT 1 FROM `user` WHERE `username` = 'admin');

INSERT INTO `user` (`username`, `email`)
SELECT 'user1', 'user1@example.com'
WHERE NOT EXISTS (SELECT 1 FROM `user` WHERE `username` = 'user1');

INSERT INTO `user` (`username`, `email`)
SELECT 'user2', 'user2@example.com'
WHERE NOT EXISTS (SELECT 1 FROM `user` WHERE `username` = 'user2'); 