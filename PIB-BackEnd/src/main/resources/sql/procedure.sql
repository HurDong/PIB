USE mydatabase;

DELIMITER //

DROP PROCEDURE IF EXISTS InsertBoards;

CREATE PROCEDURE InsertBoards(IN num INT)
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE random_title VARCHAR(100);
    DECLARE random_content TEXT;
    DECLARE random_type VARCHAR(50);
    DECLARE random_views INT;
    DECLARE random_likes INT;

    DECLARE titles TEXT DEFAULT 'Breaking News,Tech Update,Health Tips,Travel Guide,Finance Insights';
    DECLARE contents TEXT DEFAULT 'Detailed analysis of current events.,Recent advancements in technology and innovation.,Tips for maintaining a healthy lifestyle.,Exploring the world and hidden places.,Advice on managing personal finance.';
    DECLARE types TEXT DEFAULT 'General,Technology,Health';

    WHILE i < num DO
            SET random_title = SUBSTRING_INDEX(SUBSTRING_INDEX(titles, ',', FLOOR(RAND() * 5) + 1), ',', -1);
            SET random_content = SUBSTRING_INDEX(SUBSTRING_INDEX(contents, ',', FLOOR(RAND() * 5) + 1), ',', -1);
            SET random_type = SUBSTRING_INDEX(SUBSTRING_INDEX(types, ',', FLOOR(RAND() * 3) + 1), ',', -1);
            SET random_views = FLOOR(RAND() * 1000);
            SET random_likes = FLOOR(RAND() * 500);

            INSERT INTO board (id, member_id, title, content, type, views, likes)
            VALUES (UUID_TO_BIN(UUID()), FLOOR(RAND() * 100) + 1, random_title, random_content, random_type, random_views, random_likes);

            SET i = i + 1;
        END WHILE;
END //

DELIMITER ;
