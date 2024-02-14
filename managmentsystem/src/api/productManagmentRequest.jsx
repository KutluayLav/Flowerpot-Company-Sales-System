import axios from 'axios';

const BASE_URL = 'http://localhost:9090/category/v1'; // Base URL

// Tüm kategorileri getiren fonksiyon
export const getAllCategoriesName = () => {
    return axios.get(`${BASE_URL}/getAllCategories`)
        .then(response => {
            // Başarılı yanıtta kategorileri ayarla
            return response.data.map(category => category.categoryName);
        })
        .catch(error => {
            // Hata durumunda hata mesajını döndür
            throw Error('Kategoriler alınamadı:', error);
        });
};