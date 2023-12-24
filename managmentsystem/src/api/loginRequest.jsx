import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080'; 

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    // Eğer hata durumu 403 ise ve orijinal istek daha önce tekrar deneme yapılmamışsa
    // Bu durumda token'in süresi dolmuş demektir ve yenileme işlemi yapılmalı
    if (error.response.status === 403 && !originalRequest._retry) {
      originalRequest._retry = true;

      try {
        // localStorage'dan refreshToken'i al
        const refreshToken = localStorage.getItem('token');
        
        // refreshToken ile yeni bir token almak için sunucuya istek yap
        const refreshResponse = await api.post('/api/auth/refreshToken', {
          token: refreshToken,
        });

        // Yeniden alınan access token'i localStorage'a kaydet
        const newAccessToken = refreshResponse.data.accessToken;
        localStorage.setItem('accessToken', newAccessToken);

        // Yeniden alınan token ile orijinal isteği tekrarla
        originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
        return api(originalRequest);
      } catch (refreshError) {
        console.error('Access token yenileme hatası:', refreshError);
        // Token yenileme hatasıyla başa çık veya giriş sayfasına yönlendir
        return Promise.reject(refreshError);
      }
    }

    // Diğer hata durumlarında promise'ı reddet
    return Promise.reject(error);
  }
);

export const loginUser = async (userData) => {
  try {
    const response = await api.post('/api/auth/login', userData);
    return response;
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
    console.error('Error fetching user info:', error);
    throw error.response.data; 
  }
};

export const refreshAccessToken = async (refreshToken) => {
  try {
    const response = await api.post('/api/auth/refreshToken', {
      token: refreshToken,
    }, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return response.data;
  } catch (error) {
    console.error('Error refreshing access token:', error);
    throw error.response.data;
  }
};
