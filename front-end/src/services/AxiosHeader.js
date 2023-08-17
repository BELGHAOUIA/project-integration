import axios from "axios";
import authHeader from './auth-header';

const apiClient = axios.create({
  baseURL: "http://localhost:8088",
  withCredentials: false,
  headers: {
    // Accept: "*/*",
    // "Content-Type": "*/*",
    "Access-Control-Allow-Origin": "*",
    "Authorization": authHeader()
  },
});

export default apiClient;