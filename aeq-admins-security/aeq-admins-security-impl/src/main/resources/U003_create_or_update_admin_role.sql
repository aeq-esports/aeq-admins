-- =============================================
-- Creates the insert_privileges procedure.
-- =============================================

BEGIN

    INSERT INTO aeq_sec_role(name)
    SELECT element
    FROM dual
    WHERE NOT EXISTS(SELECT * FROM aeq_sec_role WHERE name = element);
END;

