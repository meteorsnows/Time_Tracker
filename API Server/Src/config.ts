export const DBConfig = {
    // Name of the database to open after connected to the DB server.
    DatabaseName: "DataStorage",
    // URL needed for connecting to the DB,
    // starts with "mongodb://" and then has standard domain notation, e.g. "elliot-labs.com".
    Host: "localhost",
    // Password for DB authentication.
    Password: "blank",
    // Port number that the DB runs off of.
    Port: 27017,
    // Username for DB authentication.
    UserName: "blank",
};

// Configure the API HTTP REST server.
export const ServerConfig = {
    ListenOnDomain: "127.0.0.1",
    ListenOnIP: "localhost",
    Port: 8080,
};
