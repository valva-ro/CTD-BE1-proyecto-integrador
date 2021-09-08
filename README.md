# CTD-BE1 - Proyecto Integrador: Clínica Odontológica

El proyecto es una API REST en proceso.

## Odontólogos
  - Buscar por id: `GET` a `PATH/odontologos/{id}`
      - `200 OK` → devuelve el odontólogo
      - `404 NOT FOUND` → no se encontró un odontólogo con ese ID


  - Registrar nuevo: `POST` a `PATH/odontologos`
      - `200 OK` → se registró correctamente
      - `400 BAD REQUEST` → hubo un error en los datos recibidos
          ```json
          {
                "nombre": "Pipo",
                "apellido": "Pipardo",
                "matricula": "123456"
          }
          ```
        
  - Actualizar existente: `PUT` a `PATH/odontologos`
      - `200 OK` → se actualizó correctamente
      - `400 BAD REQUEST` → hubo un error en los datos recibidos
      - `404 NOT FOUND` → no se encontró el odontólogo con id recibido
          ```json
        {
              "id": "1",
              "nombre": "Pipo",
              "apellido": "Pipardisimo",
              "matricula": "654321"
        }
          ```
        
  - Eliminar por id: `DELETE` a `PATH/odontologos/{id}`
      - `204 NO CONTENT` → se borró correctamente
      - `404 NOT FOUND` → no se encontró el odontólogo con id recibido


  - Obtener todos: `GET` a `PATH/odontologos`


## Pacientes

- Buscar por id: `GET` a `PATH/pacientes/{id}`
  - `200 OK` → devuelve el paciente
  - `404 NOT FOUND` → no se encontró un paciente con ese ID


- Registrar nuevo: `POST` a `PATH/pacientes`
  - `200 OK` → se registró correctamente
  - `400 BAD REQUEST` → hubo un error en los datos recibidos
      ```json
    {
          "nombre": "Pepe",
          "apellido": "Pepardo",
          "dni": "123456789",
          "domicilio": {
              "calle": "Calle Falsa",
              "numero": "123",
              "localidad": "Springfield",
              "provincia": "Springfield"
           }
    }
      ```
    
- Actualizar existente: `PUT` a `PATH/pacientes`
  - `200 OK` → se actualizó correctamente
  - `400 BAD REQUEST` → hubo un error en los datos recibidos
  - `404 NOT FOUND` → no se encontró el paciente con id recibido
    ```json
    {
          "id": "1",
          "nombre": "Pepe",
          "apellido": "Pepardo",
          "dni": "987654321",
          "fechaIngreso": "2021-09-08",
          "domicilio": {
               "id": 1,
               "calle": "Calle Falsisima",
               "numero": "123",
               "localidad": "Springfield",
               "provincia": "Springfield"
           }
    }
    ```
    
- Eliminar por id: `DELETE` a `PATH/pacientes/{id}`
  - `204 NO CONTENT` → se borró correctamente
  - `404 NOT FOUND` → no se encontró el paciente con id recibido


- Obtener todos: `GET` a `PATH/pacientes`


## Turnos
    
- Buscar por id: `GET` a `PATH/turnos/{id}`
  - `200 OK` → devuelve el turno
  - `404 NOT FOUND` → no se encontró un turno con ese ID


- Registrar nuevo: `POST` a `PATH/turnos`
  - `200 OK` → se registró correctamente
  - `400 BAD REQUEST` → hubo un error en los datos recibidos
    ```json
    {
          "paciente": {"id": "1"},
          "odontologo": {"id": "1"},
          "fecha": "2021-10-25"
    }
    ```

- Actualizar existente: `PUT` a `PATH/turnos`
    - `200 OK` → se actualizó correctamente
    - `400 BAD REQUEST` → hubo un error en los datos recibidos
    - `404 NOT FOUND` → no se encontró el turno con id recibido
      ```json
      {
            "id": "1",
            "paciente": {"id": "1"},
            "odontologo": {"id": "1"},
            "fecha": "2021-10-21"
      }
      ```
    
- Eliminar por id: `DELETE` a `PATH/turnos/{id}`
  - `204 NO CONTENT` → se borró correctamente
  - `404 NOT FOUND` → no se encontró el turno con id recibido


- Obtener todos: `GET` a `PATH/turnos`
