INSERT INTO "user" (first_name, last_name, username, password, role, date_of_birth, version, created_at, modified_at)
VALUES
    ('Admin', 'Admin', 'admin', '$2a$12$kj4WD/I2.8PcrkN6zI/Q6OKs2NAseJ76vQiVW5mIWfNXEM0lSyosm', 'ADMIN', '1999-01-01', 1, now(), now()),
    ('User', 'User', 'user', '$2a$12$B565molci59ijQ93vrGcc..uLqBehHDiHVt7Be5s3WnG5LK76kne.', 'USER', '1999-01-01', 1, now(), now());