CREATE TABLE IF NOT EXISTS odontologos (
                                           id INT AUTO_INCREMENT PRIMARY KEY,
                                           nombre VARCHAR(255) NOT NULL,
                                           apellido VARCHAR(255) NOT NULL,
                                           numeroMatricula VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS domicilios (
                                          id INT AUTO_INCREMENT PRIMARY KEY,
                                          calle VARCHAR(255) NOT NULL,
                                          numero INT NOT NULL,
                                          localidad VARCHAR(255) NOT NULL,
                                          provincia VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS pacientes (
                                         id INT AUTO_INCREMENT PRIMARY KEY,
                                         nombre VARCHAR(255) NOT NULL,
                                         apellido VARCHAR(255) NOT NULL,
                                         dni INT NOT NULL,
                                         fechaIngreso DATE,
                                         domicilioID INT,
                                         FOREIGN KEY (domicilioID) REFERENCES domicilios(id)
                                             ON DELETE CASCADE
                                             ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS turnos(
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     idPaciente INT NOT NULL,
                                     idOdontologo INT NOT NULL,
                                     fecha DATE NOT NULL,
                                     FOREIGN KEY (idPaciente) REFERENCES pacientes(id)
                                         ON DELETE CASCADE
                                         ON UPDATE CASCADE,
                                     FOREIGN KEY (idOdontologo) REFERENCES odontologos(id)
                                         ON DELETE CASCADE
                                         ON UPDATE CASCADE
);