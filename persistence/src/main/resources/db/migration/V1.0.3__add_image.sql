CREATE TABLE recipe_image
(
    id           bigserial NOT NULL PRIMARY KEY,
    picture_data bytea     NOT NULL,
    description  VARCHAR(1024),
    recipe_id    bigserial NOT NULL,
    version      integer,
    created_at   timestamp NOT NULL,
    modified_at  timestamp NOT NULL,
    CONSTRAINT fk_image_recipe FOREIGN KEY (recipe_id) REFERENCES recipe (id)
);

CREATE TABLE ingredient_image
(
    id            bigserial NOT NULL PRIMARY KEY,
    picture_data  bytea     NOT NULL,
    description   VARCHAR(1024),
    ingredient_id bigserial NOT NULL,
    version       integer,
    created_at    timestamp NOT NULL,
    modified_at   timestamp NOT NULL,
    CONSTRAINT fk_image_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredient (id)
);