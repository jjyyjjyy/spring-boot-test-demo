truncate public.user;
INSERT INTO public."user" (username, gender, address, created_at, updated_at)
VALUES ('test1', 1, 'van', now(), now()),
       ('test2', 0, 'van l', now(), now());
