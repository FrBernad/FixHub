-- FIXME: FIJARSE PROS Y CONS DE VARCHAR(N)
CREATE TABLE IF NOT EXISTS IMAGES
(
    i_id        SERIAL,
    i_data      BYTEA,
    i_mime_type TEXT,
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
    u_cover_picture   BIGINT,
    u_state           VARCHAR(50)         NOT NULL,
    u_city            VARCHAR(50)         NOT NULL,
    PRIMARY KEY (u_id),
    FOREIGN KEY (u_profile_picture) REFERENCES IMAGES (i_id),
    FOREIGN KEY (u_cover_picture) REFERENCES IMAGES (i_id)
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
    r_reviewer_id   BIGINT       NOT NULL,

    FOREIGN KEY (r_job_id) REFERENCES JOBS (j_id) ON DELETE CASCADE,
    FOREIGN KEY (r_reviewer_id) REFERENCES USERS(u_id) ON DELETE CASCADE,
    PRIMARY KEY (r_id)
);

CREATE TABLE IF NOT EXISTS JOB_IMAGE
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

CREATE TABLE IF NOT EXISTS CONTACT_INFO
(
    ci_id                SERIAL,
    ci_user_id           BIGINT      NOT NULL,
    ci_city              VARCHAR(50) NOT NULL,
    ci_state             VARCHAR(50) NOT NULL,
    ci_street            VARCHAR(50) NOT NULL,
    ci_address_number    INT         NOT NULL,
    ci_floor             VARCHAR(5)  NOT NULL,
    ci_department_number VARCHAR(50) NOT NULL,
    primary key (ci_id),
    FOREIGN KEY (ci_user_id) REFERENCES USERS (u_id)
);

CREATE TABLE IF NOT EXISTS CONTACT
(
    c_id          SERIAL,
    c_provider_id BIGINT    NOT NULL,
    c_user_id     BIGINT    NOT NULL,
    c_job_id      BIGINT    NOT NULL,
    c_info_id     BIGINT    NOT NULL,
    c_message     VARCHAR(300),
    c_date        TIMESTAMP NOT NULL,
    primary key (c_id),
    FOREIGN KEY (c_provider_id) REFERENCES USERS (u_id),
    FOREIGN KEY (c_job_id) REFERENCES JOBS (j_id),
    FOREIGN KEY (c_user_id) REFERENCES USERS (u_id),
    FOREIGN KEY (c_info_id) REFERENCES CONTACT_INFO (ci_id)
);


CREATE TABLE IF NOT EXISTS STATES
(
    s_id   SERIAL,
    s_name varchar(255) NOT NULL,
    PRIMARY KEY (s_id)
);

CREATE TABLE IF NOT EXISTS CITIES
(
    c_id       SERIAL,
    c_state_id BIGINT       NOT NULL,
    c_name     varchar(255) NOT NULL,
    FOREIGN KEY (c_state_id) REFERENCES STATES (s_id) ON DELETE CASCADE,
    PRIMARY KEY (c_id)
);

CREATE TABLE IF NOT EXISTS USER_LOCATION
(
    ul_city_id BIGINT NOT NULL,
    ul_user_id BIGINT NOT NULL,
    FOREIGN KEY (ul_user_id) REFERENCES USERS (u_id),
    FOREIGN KEY (ul_city_id) REFERENCES CITIES (c_id),
    PRIMARY KEY (ul_city_id, ul_user_id)
);

CREATE TABLE IF NOT EXISTS USER_SCHEDULE
(
    us_user_id    BIGINT     NOT NULL,
    us_start_time VARCHAR(5) NOT NULL,
    us_end_time   VARCHAR(5) NOT NULL,
    FOREIGN KEY (us_user_id) REFERENCES USERS (u_id),
    PRIMARY KEY (us_user_id, us_start_time, us_end_time)
);