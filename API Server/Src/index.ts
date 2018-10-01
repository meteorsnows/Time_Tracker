import Express from "express";
import Mongoose from "mongoose";
import {DBConfig, ServerConfig} from "./config";

// Define variables
const port = 8080;

// Initialize express.
const app = Express();
const ObjectIDType = Mongoose.Schema.Types.ObjectId;

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

// Demo Hello world API.
app.get("/", (req, res) => {
    res.send("Hello world!");
});

app.listen(ServerConfig.Port);
