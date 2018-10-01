import express from "express";

// Initialize express.
const app = express();

// Define variables
const port = 8080;

app.get("/", (req, res) => {
    res.send("Hello world!");
});

app.listen(port, () => {
    console.log("server running.");
});
