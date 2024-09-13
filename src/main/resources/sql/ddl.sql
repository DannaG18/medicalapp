CREATE DATABASE IF NOT EXISTS medicalapp;
USE medicalapp;

CREATE TABLE specialty (
    id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_specialty_id PRIMARY KEY (id)
);

CREATE TABLE doctor (
    id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    specialty_id INT NOT NULL,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    CONSTRAINT pk_doctor_id PRIMARY KEY (id),
    CONSTRAINT fk_doctor_specialty_id FOREIGN KEY (specialty_id) REFERENCES specialty(id)
);

CREATE TABLE pacient (
    id INT AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    birth_date DATE NOT NULL,
    adress VARCHAR(100),
    phone VARCHAR(30),
    email VARCHAR(100),
    CONSTRAINT pk_pacient_id PRIMARY KEY (id)
);


CREATE TABLE appointment (
    id INT AUTO_INCREMENT,
    doctor_id INT NOT NULL,
    pacient_id INT NOT NULL,
    date_time TIMESTAMP NOT NULL,
    status VARCHAR(20),
    CONSTRAINT pk_appointment_id PRIMARY KEY (id),
    CONSTRAINT fk_appointment_doctor_id FOREIGN KEY (doctor_id) REFERENCES doctor(id),
    CONSTRAINT fk_appointment_pacient_id FOREIGN KEY (pacient_id) REFERENCES pacient(id)
);

