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
    jobType       INT,
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

CREATE TABLE IF NOT EXISTS JOBCATEGORIES
(
    id   SERIAL,
    name TEXT UNIQUE,
    PRIMARY KEY (id)
);

/*

INSERT INTO jobcategories(name) VALUES ('Mecánico');
INSERT INTO jobcategories(name) VALUES ('Electricista');
INSERT INTO jobcategories(name) VALUES ('Plomero');
INSERT INTO jobcategories(name) VALUES ('Jardinero');
INSERT INTO jobcategories(name) VALUES ('Gasista');
INSERT INTO jobcategories(name) VALUES ('Carpintero');
INSERT INTO jobcategories(name) VALUES ('Pintor');
INSERT INTO jobcategories(name) VALUES ('Herrero');
INSERT INTO jobcategories(name) VALUES ('Techista');



*/

