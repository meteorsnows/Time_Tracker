import Mongoose from "mongoose";
import {DBConfig} from "./config";

// Make the ObjectID type available for the database schema.
const ObjectIDType = Mongoose.Schema.Types.ObjectId;

// Export type aliases for use in other script files.
export type ObjectID = Mongoose.Types.ObjectId;
export type DocumentType = Mongoose.Document;

// Create the schema for all of the data.
const DataSchema = new Mongoose.Schema({
    // User's name, optional. Will use email address if name not supplied.
    Name: {type: String},
    // User's password, it is required.
    Password: {type: String, required: true},
    // Stores the user's permission level. The default is the user permission level.
    PermissionLevel: {type: String, default: "User"},
    // This is where all fo the magic happens.
    // Users store their timing data in here.
    // The start and stop times are represented in milliseconds since the epoch.
    Timing: [{
        Category: ObjectIDType,
        StartTime: Number,
        StopTime: Number,
    }],
    // User's email address, needs to be unique. Required.
    eMail: {type: String, unique: true, required: true},
});

// Create data schema interface for use by the TypeScript engine to understand the custom data structure.
// This is essentially meta-data for the TypeScript engine.
interface IDataSchema {
    Name?: string;
    Password: string;
    PermissionLevel: string;
    Timing: [{
        Category: Mongoose.Types.ObjectId,
        StartTime: number,
        StopTime: number,
    }];
    eMail: string;
}

// Define the data structure of the document models.
// This is also meta-data for the typescript compiler.
interface IDataModel extends IDataSchema, Mongoose.Document {}

// Compile the schemas into models.
const DataModel = Mongoose.model<IDataModel>("Data", DataSchema);

export class Database {
    // Define the class properties.
    protected Host: string;
    protected Database: string;
    protected Port: number|string|undefined;
    protected User: string|undefined;
    protected Password: string|undefined;
    protected Session: Promise<typeof Mongoose>;

    // Set the initial settings when the class is instantiated.
    constructor(HostName: string, DB: string, PortNumber?: number, UserName?: string, PWD?: string) {
        // Gets the parameters and sets the class properties with them.
        this.Host = HostName;
        this.Database = DB;
        this.Port = PortNumber;
        this.User = UserName;
        this.Password = PWD;

        // Set the base connection protocol.
        let connectionURL = "mongodb://";

        // Set the port URL syntax if the user specifies the port.
        this.Port = (typeof this.Port === "undefined") ? this.Port = "" : this.Port = ":" + this.Port;

        // Add the username, password, host, port and database items to the base connection url.
        if (typeof this.User === "undefined" || typeof this.Password === "undefined") {
            connectionURL = connectionURL +  this.Host + this.Port + "/" + this.Database;
        } else {
            connectionURL = connectionURL + this.User + ":" +
            this.Password + "@" + this.Host + this.Port + "/" + this.Database;
        }

        // Connect to the database.
        this.Session =  Mongoose.connect(connectionURL);

        // Error check the connection.
        Mongoose.connection.on("error", (error) => {
            // Do error stuff here...
            return;
        });
    }
}
