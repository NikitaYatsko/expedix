CREATE TABLE IF NOT EXISTS expedix.users (
                                             id SERIAL PRIMARY KEY,
                                             personal_code VARCHAR(20) NOT NULL UNIQUE,
                                             full_name VARCHAR(100) NOT NULL,
                                             phone VARCHAR(20),
                                             email VARCHAR(100)
);