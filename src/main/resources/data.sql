INSERT INTO public.admins (id, email, login, password_hash)
VALUES (-1, 'asdfkl@gmail.com', 'asdlkfhjasdfhlk', 'asdfahsdfasjhd13');

INSERT INTO public.clients (id, email, login, password_hash, active, first_name, last_name, phone_number,
                            registration_date)
VALUES (-1, 'jajko@gmail.com', 'jajko1', 'as;dlfjkhgaksd', true, 'Michał', 'Jajeczny', '294-373-173',
        '2021-05-17 20:52:35.287151');
INSERT INTO public.clients (id, email, login, password_hash, active, first_name, last_name, phone_number,
                            registration_date)
VALUES (-2, 'jajko2@gmail.com', 'jajko2', 'aasdfas123ddfasdf', true, 'Roman', 'Jajeczny', '294-373-113',
        '2021-05-17 20:52:35.340153');
INSERT INTO public.clients (id, email, login, password_hash, active, first_name, last_name, phone_number,
                            registration_date)
VALUES (-3, 'jajko3@gmail.com', 'jajko3', 'aasdfas123ddfasdf', true, 'Marek', 'Jajeczny', '1312-373-113',
        '2021-05-17 20:52:35.343140');

INSERT INTO public.clients_driving_license_categories (clients_id, driving_license_categories)
VALUES (-1, 'C1E');
INSERT INTO public.clients_driving_license_categories (clients_id, driving_license_categories)
VALUES (-1, 'B');
INSERT INTO public.clients_driving_license_categories (clients_id, driving_license_categories)
VALUES (-2, 'C1E');
INSERT INTO public.clients_driving_license_categories (clients_id, driving_license_categories)
VALUES (-2, 'B');
INSERT INTO public.clients_driving_license_categories (clients_id, driving_license_categories)
VALUES (-3, 'T');
INSERT INTO public.clients_driving_license_categories (clients_id, driving_license_categories)
VALUES (-3, 'B');

INSERT INTO public.vehicles (id, available, brand, dlc, engine_capacity, model, number_of_seats, daily_loan_price, vin)
VALUES (-1, true, 'Opel', 'B', 1.8, 'Corsa', 5, 173.12, 'AGJGSKL7381');
INSERT INTO public.vehicles (id, available, brand, dlc, engine_capacity, model, number_of_seats, daily_loan_price, vin)
VALUES (-2, true, 'Fiat', 'B', 1.4, 'Punto', 5, 263.50, 'ABNASDG73FB');
INSERT INTO public.vehicles (id, available, brand, dlc, engine_capacity, model, number_of_seats, daily_loan_price, vin)
VALUES (-3, true, 'BetaJulietta', 'B', 0.1, 'MX5', 0, 3613.15, 'FHSKFGJSJK331');
INSERT INTO public.vehicles (id, available, brand, dlc, engine_capacity, model, number_of_seats, daily_loan_price, vin)
VALUES (-4, true, 'Lamborghini', 'T', 512, 'Trattori', 58, 500.00, 'FHSKFGJSJK341');

INSERT INTO public.rents (id, actual_finished_date, declared_finished_date, start_date, uuid, client_id,
                          rented_vehicle_id)
VALUES (-1, null, '2021-05-18 20:52:35.358151', '2021-05-17 20:52:35.358151',
        '296dcd4f-e1c9-496e-8944-a6f118bfeb45', -1, -1);
INSERT INTO public.rents (id, actual_finished_date, declared_finished_date, start_date, uuid, client_id,
                          rented_vehicle_id)
VALUES (-2, null, '2021-05-18 20:52:35.359149', '2021-05-17 20:52:35.359149',
        'cb8afc22-409b-4bc3-9d50-2cb2718c9840', -1, -1);
INSERT INTO public.rents (id, actual_finished_date, declared_finished_date, start_date, uuid, client_id,
                          rented_vehicle_id)
VALUES (-3, null, '2021-05-19 20:52:35.359149', '2021-05-17 20:52:35.359149',
        'e5af4cd0-4273-434f-bbe1-d08050771b05', -2, -2);
INSERT INTO public.rents (id, actual_finished_date, declared_finished_date, start_date, uuid, client_id,
                          rented_vehicle_id)
VALUES (-4, null, '2021-05-21 20:52:35.359149', '2021-05-17 20:52:35.359149',
        '5cedaf61-a799-45eb-a02f-5f90151ac77a', -2, -4);