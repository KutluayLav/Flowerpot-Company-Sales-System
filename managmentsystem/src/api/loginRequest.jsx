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
    if (error.response && error.response.status === 403) {
      const refreshedToken = await refreshAccessToken(localStorage.getItem('refreshToken'));
      
      try {
        const newResponse = await api.get('/api/auth/getuserinfo', {
          headers: {
            Authorization: `Bearer ${refreshedToken.accessToken}`,
          },
        });
        return newResponse.data;
      } catch (newError) {
        console.error('Error fetching user info with refreshed token:', newError);
        throw newError.response.data;
      }
    } else {
      console.error('Error fetching user info:', error);
      throw error.response.data;
    }
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
