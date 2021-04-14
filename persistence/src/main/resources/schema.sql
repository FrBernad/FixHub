-- FIXME: FIJARSE PROS Y CONS DE VARCHAR(N)
-- FIXME: AGREAGAR IDENTIFICARO A CADA CAMPO DE LAS TABLAS
CREATE TABLE IF NOT EXISTS IMAGES
(
    i_id    SERIAL,
    i_data BYTEA,
    data BYTEA,
    PRIMARY KEY (i_id)
);

CREATE TABLE IF NOT EXISTS USERS
(
    u_id              SERIAL,
    u_password        VARCHAR(128)        NOT NULL,
    u_name            VARCHAR(50)         NOT NULL,
    u_surname         VARCHAR(50)         NOT NULL,
    u_email           VARCHAR(200) UNIQUE NOT NULL,
    u_phone_number    VARCHAR(15)         NOT NULL,
    u_profile_picture BIGINT,
    u_state           VARCHAR(50)         NOT NULL,
    u_city            VARCHAR(50)         NOT NULL,
    PRIMARY KEY (u_id),
    FOREIGN KEY (U_profile_picture) REFERENCES IMAGES (i_id)
);

CREATE TABLE IF NOT EXISTS ROLES
(
    r_id      SERIAL,
    r_role    TEXT   NOT NULL,
    r_user_id BIGINT NOT NULL,
    FOREIGN KEY (r_user_id) REFERENCES USERS (u_id) ON DELETE CASCADE,
    PRIMARY KEY (r_id)
);

CREATE TABLE IF NOT EXISTS JOBS
(
    j_id           SERIAL,
    j_description  VARCHAR(300) NOT NULL,
    j_category     VARCHAR(50)  NOT NULL,
    j_job_provided VARCHAR(50)  NOT NULL,
    j_provider_id  BIGINT       NOT NULL,
    j_price        DECIMAL      NOT NULL,
    FOREIGN KEY (j_provider_id) REFERENCES USERS (u_id),
    PRIMARY KEY (j_id)
);

CREATE TABLE IF NOT EXISTS REVIEWS
(
    r_id            SERIAL,
    r_description   VARCHAR(300) NOT NULL,
    r_job_id        BIGINT       NOT NULL,
    r_rating        INT          NOT NULL,
    r_creation_date TIMESTAMP    NOT NULL,
    FOREIGN KEY (r_job_id) REFERENCES JOBS (j_id) ON DELETE CASCADE,
    PRIMARY KEY (r_id)
);

CREATE TABLE IF NOT EXISTS JOB_IMAGES
(
    ji_image_id BIGINT,
    ji_job_id   BIGINT,
    PRIMARY KEY (ji_image_id, ji_job_id),
    FOREIGN KEY (ji_image_id) REFERENCES IMAGES (i_id),
    FOREIGN KEY (ji_job_id) REFERENCES JOBS (j_id)
);

CREATE TABLE IF NOT EXISTS VERIFICATION_TOKENS
(
    vt_id              SERIAL,
    vt_user_id         BIGINT    NOT NULL,
    vt_token           TEXT,
    vt_expiration_date TIMESTAMP NOT NULL,
    FOREIGN KEY (vt_user_id) REFERENCES USERS (u_id) ON DELETE CASCADE,
    PRIMARY KEY (vt_id)
);

CREATE TABLE IF NOT EXISTS PASSWORD_RESET_TOKENS
(
    prt_id              SERIAL,
    prt_user_id         BIGINT    NOT NULL,
    prt_token           TEXT,
    prt_expiration_date TIMESTAMP NOT NULL,
    FOREIGN KEY (prt_user_id) REFERENCES USERS (u_id) ON DELETE CASCADE,
    PRIMARY KEY (prt_id)
);

