CREATE TABLE "user"
(
    id       BIGINT GENERATED ALWAYS AS IDENTITY,
    login    VARCHAR(100) NOT NULL,
    password TEXT         NOT NULL,

    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT uq_user_login UNIQUE (login)
);

CREATE TABLE event
(
    id        BIGINT GENERATED ALWAYS AS IDENTITY,
    user_id   BIGINT       NOT NULL,
    title     VARCHAR(100) NOT NULL,
    location  VARCHAR(300) NOT NULL,
    starts_at TIMESTAMP    NOT NULL,
    ends_at   TIMESTAMP    NOT NULL,

    CONSTRAINT pk_event PRIMARY KEY (id),
    CONSTRAINT fk_event_user FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE CASCADE
);
