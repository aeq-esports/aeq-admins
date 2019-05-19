-- =============================================
-- Insert default privileges.
-- @see Privileges
-- =============================================

CALL insert_privileges('
    PRV_READ_PRIVILEGES
');

CALL insert_privileges('
    PRV_READ_ROLES
    PRV_MANAGE_ROLES
');

CALL insert_privileges('
    PRV_READ_USERS
    PRV_MANAGE_USERS
');