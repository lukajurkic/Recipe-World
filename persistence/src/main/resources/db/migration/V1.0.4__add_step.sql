CREATE TABLE step
(
    id          bigserial NOT NULL PRIMARY KEY,
    recipe_id   bigserial NOT NULL,
    description VARCHAR(2048) NOT NULL ,
    number      integer NOT NULL ,
    version     integer,
    created_at  timestamp NOT NULL,
    modified_at timestamp NOT NULL,
    CONSTRAINT fk_step_recipe FOREIGN KEY (recipe_id) REFERENCES recipe (id)
);