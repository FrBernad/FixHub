CREATE TABLE IF NOT EXISTS USERS
(
    id           SERIAL,
    password     VARCHAR(128)        NOT NULL,
    name         VARCHAR(50)         NOT NULL,
    surname      VARCHAR(50)         NOT NULL,
    email        VARCHAR(200) UNIQUE NOT NULL,
    phone_number VARCHAR(50)         NOT NULL,
    state        VARCHAR(50)         NOT NULL,
    city         VARCHAR(50)         NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS JOBS
(
    id           SERIAL,
    description  VARCHAR(300) NOT NULL,
    category     VARCHAR(50)  NOT NULL,
    job_provided VARCHAR(50)  NOT NULL,
    provider_id  BIGINT       NOT NULL,
    price        DECIMAL      NOT NULL,
    FOREIGN KEY (provider_id) REFERENCES USERS (id),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS REVIEWS
(
    id            SERIAL,
    description   VARCHAR(300) NOT NULL,
    job_id        BIGINT    NOT NULL,
    rating        INT       NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    FOREIGN KEY (job_id) REFERENCES JOBS (id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);





