create table if not exists admins
(
    id            bigint not null
        constraint admins_pkey
            primary key,
    email         varchar(255)
        constraint admins_email_unique_constraint unique,
    login         varchar(255)
        constraint admins_login_unique_constraint unique,
    password_hash varchar(255)
);

alter table admins
    owner to "rentalHubAdmin";

create table if not exists clients
(
    id                bigint not null
        constraint clients_pkey
            primary key,
    email             varchar(255)
        constraint clients_email_unique_constraint
            unique,
    login             varchar(255)
        constraint clients_login_unique_constraint
            unique,
    password_hash     varchar(255),
    active            boolean,
    first_name        varchar(255),
    last_name         varchar(255),
    phone_number      varchar(255),
    registration_date timestamp
);

alter table clients
    owner to "rentalHubAdmin";

create table if not exists clients_driving_license_categories
(
    clients_id                 bigint not null
        constraint clients_driving_license_categories_client_foreign_key
            references clients,
    driving_license_categories varchar(255)
);

alter table clients_driving_license_categories
    owner to "rentalHubAdmin";

create table if not exists vehicles
(
    id              bigint not null
        constraint vehicles_pkey
            primary key,
    available       boolean,
    brand           varchar(255),
    dlc             varchar(255),
    engine_capacity double precision,
    model           varchar(255),
    number_of_seats integer,
    vin             varchar(255)
        constraint vin_unique_constraint
            unique
);

alter table vehicles
    owner to "rentalHubAdmin";

create table if not exists rents
(
    id                     bigint not null
        constraint rents_pkey
            primary key,
    actual_finished_date   timestamp,
    declared_finished_date timestamp,
    start_date             timestamp,
    uuid                   uuid
        constraint rents_uuid_unique_constraint
            unique,
    client_id              bigint
        constraint rents_client_id_foreign_key
            references clients,
    rented_vehicle_id      bigint
        constraint rents_rented_vehicle_id_foreign_key
            references vehicles
);

alter table rents
    owner to "rentalHubAdmin";

create table if not exists archived_rents
(
    id              bigint not null
        constraint archived_rents_pkey
            primary key,
    cost_in_pln     double precision,
    currency        varchar(255),
    pln_to_currency double precision,
    uuid            uuid,
    rent_id         bigint
        constraint archived_rents_rent_id_foreign_key
            references rents
);

alter table archived_rents
    owner to "rentalHubAdmin";

