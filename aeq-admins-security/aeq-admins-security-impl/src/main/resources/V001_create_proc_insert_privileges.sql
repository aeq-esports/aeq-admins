-- =============================================
-- Creates the insert_privileges procedure.
-- =============================================

DROP PROCEDURE IF EXISTS insert_privileges;

-- =============================================
-- Inserts an array of privileges.
--
-- Parameters:
--   @array - comma separated list of privilege names to be inserted
-- =============================================
CREATE PROCEDURE insert_privileges(
    array TEXT
)
BEGIN
    DECLARE element varchar(50);

    -- replace whitespace
    SET array = REGEXP_REPLACE(array, '\\s+', '');

    WHILE array != '' DO
    SET element = SUBSTRING_INDEX(array, ',', 1);
    SET element = UPPER(element);

    -- insert the privilege
    INSERT INTO aeq_privilege(name)
    SELECT element
    FROM dual
    WHERE NOT EXISTS(SELECT * FROM aeq_privilege WHERE name = element);

    IF LOCATE(',', array) > 0 THEN
        SET array = SUBSTRING(array, LOCATE(',', array) + 1);
    ELSE
        SET array = '';
    END IF;
    END WHILE;
END
