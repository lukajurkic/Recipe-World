INSERT INTO comment (id, title, text, user_id, recipe_id, version, created_at, modified_at)
VALUES
    (10, 'Great dish', 'Tried it yesterday and it was awesome.', 1, 10, 1, now(), now()),
    (11, 'Baddd', 'I thought it was good but i regretted trying it.', 1, 15, 1, now(), now()),
    (12, 'Not recommended', 'Made it to try, but gave it to my dogs.', 2, 15, 1, now(), now()),
    (13, 'Best ever', 'My tongue is happy,', 3, 10, 1, now(), now());