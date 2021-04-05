CREATE TABLE IF NOT EXISTS USERS
(
    id          SERIAL,
    password    TEXT,
    name        TEXT,
    surname     TEXT,
    email       TEXT UNIQUE,
    phone_number TEXT,
    state       TEXT,
    city        TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS JOBS
(
    id            SERIAL,
    description   TEXT,
    category      TEXT,
    job_provided   TEXT,
    provider_id    BIGINT,
    price         DECIMAL,
    FOREIGN KEY (provider_id) REFERENCES USERS (id),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS REVIEWS
(
    id           SERIAL,
    description  TEXT,
    job_id        BIGINT,
    rating       INT,
    creation_date TIMESTAMP,
    FOREIGN KEY (job_id) REFERENCES JOBS (id),
    PRIMARY KEY (id)
);


