CREATE TABLE IF NOT EXISTS USERS
(
    id          SERIAL,
    password    TEXT NOT NULL ,
    name        TEXT NOT NULL,
    surname     TEXT NOT NULL,
    email       TEXT UNIQUE NOT NULL,
    phone_number TEXT NOT NULL,
    state       TEXT NOT NULL,
    city        TEXT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS JOBS
(
    id            SERIAL,
    description   TEXT NOT NULL,
    category      TEXT NOT NULL,
    job_provided   TEXT NOT NULL,
    provider_id    BIGINT NOT NULL,
    price         DECIMAL NOT NULL,
    FOREIGN KEY (provider_id) REFERENCES USERS (id),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS REVIEWS
(
    id           SERIAL,
    description  TEXT NOT NULL,
    job_id        BIGINT NOT NULL,
    rating       INT NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    FOREIGN KEY (job_id) REFERENCES JOBS (id) ON DELETE  CASCADE,
    PRIMARY KEY (id)
);


