CREATE TABLE IF NOT EXISTS USERS
(
    id          SERIAL,
    password    TEXT,
    name        TEXT,
    surname     TEXT,
    email       TEXT UNIQUE,
    phoneNumber TEXT,
    state       TEXT,
    city        TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS JOBS
(
    id            SERIAL,
    description   TEXT,
    averageRating INT,
    category      TEXT,
    jobProvided   TEXT,
    providerId    BIGINT,
    price         DECIMAL,
    FOREIGN KEY (providerId) REFERENCES USERS (id),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS REVIEWS
(
    id           SERIAL,
    description  TEXT,
    jobId        BIGINT,
    rating       INT,
    creationDate TIMESTAMP,
    FOREIGN KEY (jobId) REFERENCES JOBS (id),
    PRIMARY KEY (id)
);


