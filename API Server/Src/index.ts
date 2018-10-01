import Express from "express";
import { ServerConfig } from "./config";
import { Database } from "./database";

// Initialize express.
const app = Express();

// Demo Hello world API.
app.get("/", (req, res) => {
    res.send("Hello world!");
});

app.listen(ServerConfig.Port);
