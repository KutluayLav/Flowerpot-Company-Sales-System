import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080'; 

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const registerUser = async (userData) => {
  try {
    const response = await api.post('/api/auth/signup', userData);
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};