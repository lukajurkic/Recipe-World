-- Define SQL tables and migration here

CREATE TABLE ingredient
(
    id          bigserial   NOT NULL PRIMARY KEY,
    name        varchar(64) NOT NULL,
    category    varchar(32) NOT NULL,
    version     integer,
    created_at  timestamp   NOT NULL,
    modified_at timestamp   NOT NULL
);

CREATE TABLE "user"
(
    id            bigserial    NOT NULL PRIMARY KEY,
    first_name    VARCHAR(64)  NOT NULL,
    last_name     VARCHAR(64)  NOT NULL,
    username      VARCHAR(128) NOT NULL,
    password      VARCHAR(64)  NOT NULL,
    role          VARCHAR(32)  NOT NULL,
    date_of_birth DATE         NOT NULL,
    version       integer,
    created_at    timestamp    NOT NULL,
    modified_at   timestamp    NOT NULL
);

CREATE TABLE recipe
(
    id          bigserial    NOT NULL PRIMARY KEY,
    name        varchar(128) NOT NULL,
    user_id     bigserial    NOT NULL,
    version     integer,
    created_at  timestamp    NOT NULL,
    modified_at timestamp    NOT NULL,
    CONSTRAINT fk_recipe_user FOREIGN KEY (user_id) REFERENCES "user" (id)
);

CREATE TABLE recipe_ingredient
(
    id            bigserial  NOT NULL PRIMARY KEY,
    recipe_id     bigserial  NOT NULL,
    ingredient_id bigserial  NOT NULL,
    amount        integer    NOT NULL,
    unit          varchar(8) NOT NULL,
    version       integer,
    created_at    timestamp  NOT NULL,
    modified_at   timestamp  NOT NULL,
    CONSTRAINT fk_recipe_ingredient_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredient (id),
    CONSTRAINT fk_recipe_ingredient_recipe FOREIGN KEY (recipe_id) REFERENCES recipe (id)
);

CREATE TABLE comment
(
    id          bigserial     NOT NULL PRIMARY KEY,
    title       varchar(64)   NOT NULL,
    text        varchar(1024) NOT NULL,
    user_id     bigserial     NOT NULL,
    recipe_id   bigserial     NOT NULL,
    version     integer,
    created_at  timestamp     NOT NULL,
    modified_at timestamp     NOT NULL,
    CONSTRAINT fk_comment_user FOREIGN KEY (user_id) REFERENCES "user" (id),
    CONSTRAINT fk_comment_recipe FOREIGN KEY (recipe_id) REFERENCES recipe (id)
);