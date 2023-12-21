import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080'; 

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const loginUser = async (userData) => {
  try {
    const response = await api.post('/api/auth/login', userData);
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};

export const logoutUser = async () => {
  try {
    const response = await api.post('/api/auth/logout');
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};

export const getUserInfo = async (token) => {
  try {
    const response = await api.get('/api/auth/getuserinfo', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    // Token geçerli değil veya kullanıcı oturumu kapalıysa 401 hatası alabilirsiniz
    console.error('Error fetching user info:', error);

    // Kullanıcıyı oturum açma sayfasına yönlendir
    // Örneğin, React Router kullanılıyorsa:
    // navigate('/login');

    throw error.response.data; // Hatayı tekrar fırlat, gerekirse componentte de ele alabilirsiniz
  }
};