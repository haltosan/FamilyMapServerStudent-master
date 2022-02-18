DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Person;
DROP TABLE IF EXISTS Event;
DROP TABLE IF EXISTS Authtoken;

CREATE TABLE User (
    username varchar(255) not null,
    password varchar(255) not null,
    email varchar(255) not null,
    firstName varchar(255) not null,
    lastName varchar(255) not null,
    gender varchar(255) not null,
    personID varchar(255) not null primary key,
);

CREATE TABLE Person (
    personID varchar(255) not null primary key,
    associatedUsername varchar(255) not null,
    firstName varchar(255) not null,
    lastName varchar(255) not null,
    gender varchar(255) not null,
    fatherID varchar(255),
    motherID varchar(255),
    spouseID varchar(255)
);

CREATE TABLE Event (
    eventID varchar(255) not null primary key,
    associatedUsername varchar(255) not null,
    personID varchar(255) not null,
    latitude float not null,
    longitude float not null,
    country varchar(255) not null,
    city varchar(255) not null,
    eventType varchar(255) not null,
    year int not null
);

CREATE TABLE Authtoken(
    authtoken varchar(255) not null,
    username varchar(255) not null
);