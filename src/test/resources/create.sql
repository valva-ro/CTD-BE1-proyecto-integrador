CREATE TABLE IF NOT EXISTS odontologos (
                                           id INT AUTO_INCREMENT PRIMARY KEY,
                                           nombre VARCHAR(50) NOT NULL,
                                           apellido VARCHAR(50) NOT NULL,
                                           numeroMatricula VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS domicilios (
                                          id INT AUTO_INCREMENT PRIMARY KEY,
                                          calle VARCHAR(50) NOT NULL,
                                          numero INT NOT NULL,
                                          localidad VARCHAR(50) NOT NULL,
                                          provincia VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS pacientes (
                                         id INT AUTO_INCREMENT PRIMARY KEY,
                                         nombre VARCHAR(50) NOT NULL,
                                         apellido VARCHAR(50) NOT NULL,
                                         dni INT NOT NULL,
                                         fechaIngreso DATE,
                                         domicilioID INT,
                                         FOREIGN KEY (domicilioID) REFERENCES domicilios(id)
                                             ON DELETE CASCADE
                                             ON UPDATE CASCADE
);

INSERT INTO domicilios (calle, numero, localidad, provincia) VALUES ('Calle Falsa', 123, 'Springfield', 'Springfield');

INSERT INTO odontologos (nombre, apellido, numeroMatricula) VALUES ('Pepe', 'Pepardo', '123456');
INSERT INTO odontologos (nombre, apellido, numeroMatricula) VALUES ('Pipo', 'Pipardo', '112233');
INSERT INTO odontologos (nombre, apellido, numeroMatricula) VALUES ('Pepa', 'Peparda', '111222');