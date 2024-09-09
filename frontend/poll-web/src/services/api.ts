import axios from "axios";

// TODO: use environment variables to set the base URL
export const api = axios.create({
  baseURL: "http://localhost:8080",
  headers: {
    "Content-Type": "application/json",
  },
});
