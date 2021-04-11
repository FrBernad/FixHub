-- FIXME: FIJARSE PROS Y CONS DE VARCHAR(N)
-- FIXME: AGREAGAR IDENTIFICARO A CADA CAMPO DE LAS TABLAS
CREATE TABLE IF NOT EXISTS USERS
(
    id             SERIAL,
    password       VARCHAR(128)        NOT NULL,
    name           VARCHAR(50)         NOT NULL,
    surname        VARCHAR(50)         NOT NULL,
    email          VARCHAR(200) UNIQUE NOT NULL,
    phone_number   VARCHAR(50)         NOT NULL,
    profilePicture BIGINT,
    state          VARCHAR(50)         NOT NULL,
    city           VARCHAR(50)         NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (profilePicture) REFERENCES IMAGES (id)
);

CREATE TABLE IF NOT EXISTS ROLES
(
    id            SERIAL,
    role          TEXT      NOT NULL,
    user_id       BIGINT    NOT NULL,
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE,
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
    job_id        BIGINT       NOT NULL,
    rating        INT          NOT NULL,
    creation_date TIMESTAMP    NOT NULL,
    FOREIGN KEY (job_id) REFERENCES JOBS (id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS IMAGES
(
    id    SERIAL,
    image BYTEA,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS JOB_IMAGES
(
    image_id BIGINT,
    job_id   BIGINT,
    PRIMARY KEY (image_id, job_id),
    FOREIGN KEY (image_id) REFERENCES IMAGES (id),
    FOREIGN KEY (job_id) REFERENCES JOBS (id)
);




