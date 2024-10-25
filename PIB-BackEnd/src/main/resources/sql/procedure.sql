USE mydatabase;

DELIMITER //

CREATE PROCEDURE InsertBoards(IN num INT)
BEGIN
    DECLARE i INT DEFAULT 0;

    WHILE i < num DO
        INSERT INTO board (id, member_id, title, content, type, views, likes)
        VALUES (UUID_TO_BIN(UUID()), FLOOR(RAND() * 100) + 1, CONCAT('Title ', i), 'This is sample content', 'General', 0, 0);
        SET i = i + 1;
END WHILE;
END //

DELIMITER ;
