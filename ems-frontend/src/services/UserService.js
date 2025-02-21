import axios from "axios";

const USER_API_BASE_URL = 'http://localhost:8080/api/users';

export const listUsers = () => axios.get(USER_API_BASE_URL);
export const updateUserRole = (userId, role) => axios.put(`${USER_API_BASE_URL}/${userId}/role`, { role });
export const deleteUser = (userId) => axios.delete(`${USER_API_BASE_URL}/${userId}`);
