INSERT INTO public.admins (id, email, login, password_hash)
VALUES (2, 'asdfkl@gmail.com', 'asdlkfhjasdfhlk', 'asdfahsdfasjhd13');

INSERT INTO public.clients (id, email, login, password_hash, active, first_name, last_name, phone_number,
                            registration_date)
VALUES (1, 'jajko@gmail.com', 'jajko1', 'as;dlfjkhgaksd', true, 'Michał', 'Jajeczny', '294-373-173',
        '2021-05-17 20:52:35.287151');
INSERT INTO public.clients (id, email, login, password_hash, active, first_name, last_name, phone_number,
                            registration_date)
VALUES (3, 'jajko2@gmail.com', 'jajko2', 'aasdfas123ddfasdf', true, 'Roman', 'Jajeczny', '294-373-113',
        '2021-05-17 20:52:35.340153');
INSERT INTO public.clients (id, email, login, password_hash, active, first_name, last_name, phone_number,
                            registration_date)
VALUES (4, 'jajko3@gmail.com', 'jajko3', 'aasdfas123ddfasdf', true, 'Marek', 'Jajeczny', '1312-373-113',
        '2021-05-17 20:52:35.343140');

INSERT INTO public.clients_driving_license_categories (clients_id, driving_license_categories)
VALUES (1, 'C1E');
INSERT INTO public.clients_driving_license_categories (clients_id, driving_license_categories)
VALUES (1, 'B');
INSERT INTO public.clients_driving_license_categories (clients_id, driving_license_categories)
VALUES (3, 'C1E');
INSERT INTO public.clients_driving_license_categories (clients_id, driving_license_categories)
VALUES (3, 'B');
INSERT INTO public.clients_driving_license_categories (clients_id, driving_license_categories)
VALUES (4, 'T');
INSERT INTO public.clients_driving_license_categories (clients_id, driving_license_categories)
VALUES (4, 'B');

INSERT INTO public.vehicles (id, available, brand, dlc, engine_capacity, model, number_of_seats, vin)
VALUES (5, true, 'Opel', 'B', 1.8, 'Corsa', 5, 'AGJGSKL7381');
INSERT INTO public.vehicles (id, available, brand, dlc, engine_capacity, model, number_of_seats, vin)
VALUES (6, true, 'Fiat', 'B', 1.4, 'Punto', 5, 'ABNASDG73FB');
INSERT INTO public.vehicles (id, available, brand, dlc, engine_capacity, model, number_of_seats, vin)
VALUES (7, true, 'BetaJulietta', 'B', 0.1, 'MX5', 0, 'FHSKFGJSJK331');
INSERT INTO public.vehicles (id, available, brand, dlc, engine_capacity, model, number_of_seats, vin)
VALUES (8, true, 'Lamborghini', 'T', 512, 'Trattori', 58, 'FHSKFGJSJK341');

INSERT INTO public.rents (id, actual_finished_date, declared_finished_date, start_date, uuid, client_id,
                          rented_vehicle_id)
VALUES (9, null, '2021-05-18 20:52:35.358151', '2021-05-17 20:52:35.358151', '296dcd4f-e1c9-496e-8944-a6f118bfeb45', 1,
        5);
INSERT INTO public.rents (id, actual_finished_date, declared_finished_date, start_date, uuid, client_id,
                          rented_vehicle_id)
VALUES (10, null, '2021-05-18 20:52:35.359149', '2021-05-17 20:52:35.359149', 'cb8afc22-409b-4bc3-9d50-2cb2718c9840', 1,
        5);
INSERT INTO public.rents (id, actual_finished_date, declared_finished_date, start_date, uuid, client_id,
                          rented_vehicle_id)
VALUES (11, null, '2021-05-19 20:52:35.359149', '2021-05-17 20:52:35.359149', 'e5af4cd0-4273-434f-bbe1-d08050771b05', 3,
        6);
INSERT INTO public.rents (id, actual_finished_date, declared_finished_date, start_date, uuid, client_id,
                          rented_vehicle_id)
VALUES (12, null, '2021-05-21 20:52:35.359149', '2021-05-17 20:52:35.359149', '5cedaf61-a799-45eb-a02f-5f90151ac77a', 4,
        8);