CREATE TABLE singer
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(256)
);

CREATE TABLE recording
(
    id           BIGSERIAL PRIMARY KEY,
    song_code    VARCHAR(32),
    title        VARCHAR(4096),
    version      VARCHAR(128),
    release_time TIMESTAMP,
    singer_id    BIGINT REFERENCES singer (id)
);

CREATE TABLE company
(
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(256)
);

CREATE TABLE copyright
(
    id              BIGSERIAL PRIMARY KEY,
    start_time      TIMESTAMP,
    end_time        TIMESTAMP,
    fee             NUMERIC(20, 2),
    company_id      BIGINT REFERENCES company (id),
    recording_id    BIGINT REFERENCES recording (id)
);
