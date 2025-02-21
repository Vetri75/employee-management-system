import axios from "axios";

const AUTH_API_BASE_URL = 'http://localhost:8080/api/auth';

export const login = (credentials) => axios.post(`${AUTH_API_BASE_URL}/login`, credentials);
export const logout = () => localStorage.removeItem('token');
export const getToken = () => localStorage.getItem('token');
export const isAuthenticated = () => !!getToken();
