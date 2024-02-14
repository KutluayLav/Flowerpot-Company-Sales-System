import Layout from '../pages/Layout';
import { useNavigate } from 'react-router-dom';
import React, { useState, useEffect } from 'react';
import { getAllCategoriesName } from '../api/productManagmentRequest';

const AddProduct = () => {
    const navigate = useNavigate();

    // State for form fields
    const [productName, setProductName] = useState('');
    const [category, setCategory] = useState('');
    const [price, setPrice] = useState('');
    const [quantity, setQuantity] = useState('');
    const [description, setDescription] = useState('');
    const [image, setImage] = useState(null);
    const [existingCategories, setExistingCategories] = useState([]);

    useEffect(() => {
        // Tüm kategorileri getiren Axios isteğini yap
        getAllCategoriesName()
            .then(categories => {
                // Başarılı yanıtta kategorileri ayarla
                setExistingCategories(categories);
            })
            .catch(error => {
                // Hata durumunda konsola yazdır
                console.error('Kategoriler alınamadı:', error);
            });
    }, []); // Sadece bir kere yükle

    const handleFormSubmit = (e) => {
        e.preventDefault();
        // Form gönderme mantığını burada gerçekleştirin
        // Örneğin, ürünü eklemek için bir POST isteği gönderebilirsiniz
        // FormData oluşturulabilir ve POST isteği gönderilebilir
        const formData = new FormData();
        formData.append('productName', productName);
        formData.append('category', category);
        formData.append('price', price);
        formData.append('quantity', quantity);
        formData.append('description', description);
        formData.append('image', image);

        // Ürün başarıyla eklendikten sonra, ürünler tablosuna geri dön
        navigate('/products');
    };

    const handleImageChange = (e) => {
        const file = e.target.files[0];
        setImage(file);
    };

    return (
        <Layout>
            <div className="flex justify-center items-center w-full h-full">
                <div className="max-w-lg w-full">
                    <h1 className="text-4xl mb-4 text-gray-500 text-center">Add Product</h1>
                    <form onSubmit={handleFormSubmit} className="grid grid-cols-1 md:grid-cols-2 gap-4">
                        <div className="">
                            <label htmlFor="productName" className="block text-gray-700 font-bold mb-2">
                                Product Name
                            </label>
                            <input
                                type="text"
                                id="productName"
                                value={productName}
                                onChange={(e) => setProductName(e.target.value)}
                                className="border rounded p-2 w-full"
                                required
                            />
                        </div>
                        <div className="">
                            <label htmlFor="category" className="block text-gray-700 font-bold mb-2">
                                Category
                            </label>
                            <select
                                id="category"
                                value={category}
                                onChange={(e) => setCategory(e.target.value)}
                                className="border rounded p-2 w-full"
                                required
                            >
                                <option value="">Select a category</option>
                                {existingCategories.map((cat, index) => (
                                    <option key={index} value={cat}>
                                        {cat}
                                    </option>
                                ))}
                            </select>
                        </div>
                        <div className="">
                            <label htmlFor="price" className="block text-gray-700 font-bold mb-2">
                                Price
                            </label>
                            <input
                                type="text"
                                id="price"
                                value={price}
                                onChange={(e) => setPrice(e.target.value)}
                                className="border rounded p-2 w-full"
                                required
                            />
                        </div>
                        <div className="">
                            <label htmlFor="quantity" className="block text-gray-700 font-bold mb-2">
                                Quantity
                            </label>
                            <input
                                type="number"
                                id="quantity"
                                value={quantity}
                                onChange={(e) => setQuantity(e.target.value)}
                                className="border rounded p-2 w-full"
                                required
                            />
                        </div>
                        <div className=" col-span-2">
                            <label htmlFor="description" className="block text-gray-700 font-bold mb-2">
                                Description
                            </label>
                            <textarea
                                id="description"
                                value={description}
                                onChange={(e) => setDescription(e.target.value)}
                                className="border rounded p-2 w-full h-24 resize-none"
                                required
                            ></textarea>
                        </div>
                        <div className=" col-span-2">
                            <label htmlFor="image" className="block text-gray-700 font-bold mb-2">
                                Product Image
                            </label>
                            <div className="flex items-center justify-center w-full h-24 border border-gray-300 border-dashed rounded-md">
                                <label htmlFor="file-upload" className="flex flex-col items-center justify-center">
                                    <svg
                                        className="w-10 h-10 text-gray-400 group-hover:text-gray-600"
                                        xmlns="http://www.w3.org/2000/svg"
                                        fill="none"
                                        viewBox="0 0 24 24"
                                        stroke="currentColor"
                                    >
                                        <path
                                            strokeLinecap="round"
                                            strokeLinejoin="round"
                                            strokeWidth={2}
                                            d="M12 6v6m0 0v6m0-6h6m-6 0H6"
                                        />
                                    </svg>
                                    <span className="text-sm text-gray-600 group-hover:text-gray-700">Select a file</span>
                                    <input
                                        id="file-upload"
                                        name="file-upload"
                                        type="file"
                                        accept="image/*"
                                        onChange={handleImageChange}
                                        className="sr-only"
                                    />
                                </label>
                            </div>
                        </div>
                        <div className="flex justify-center col-span-2">
                            <button
                                type="submit"
                                className="bg-green-500 hover:bg-green-700 text-white py-2 px-4 rounded"
                            >
                                Add Product
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </Layout>
    );
};

export default AddProduct;