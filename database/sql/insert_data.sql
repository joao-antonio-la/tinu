INSERT INTO users (user_email, user_password_hash)
VALUES ('admin@admin.admin', crypt('admin', gen_salt('bf', 12)));