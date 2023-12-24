import bahceResim from '../assets/bahce.jpeg';
import { registerUser } from '../api/registerRequest';
import { useNavigate } from 'react-router-dom';
import React, { useState } from 'react';

const Register = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    firstname: '',
    lastname:'',
    email: '',
    phoneNo: '',
    password: '',
  });

  const handleInputChange = (e) => {
    setFormData({ ...formData, [e.target.id]: e.target.value });
  };

  const handleFormSubmit = async (e) => {
    e.preventDefault();

    try {
      await registerUser(formData);
      console.log('User registered successfully!');
      navigate('/login');
    } catch (error) {
      console.error('User registration failed:', error);
    }
  };

  return (
    <div className="min-h-screen flex w-full ">
    <div className="w-1/2 h-screen">
      <img
        src={bahceResim}
        alt="Bahçe"
        className="h-full w-full object-cover object-left"
      />
    </div>
    <div className="w-1/2 flex items-center justify-center">
      <div className="bg-white p-8 rounded shadow-lg w-3/4 border-2 border-purple-500 border-opacity-50">
        <h2 className="text-2xl font-semibold mb-6 text-center text-blue-500">
          Kayit Ol
        </h2>
        <form onSubmit={handleFormSubmit}>
        <div className="mb-4">
            <label htmlFor="firstname" className="block text-gray-600 text-sm mb-2">
              Ad
            </label>
            <input
              type="text"
              id="firstname"
              onChange={handleInputChange}
              value={formData.firstname}
              className="w-full p-3 border rounded focus:outline-none focus:border-blue-500"
              placeholder="İsim giriniz"
            />
        </div>
             <div className="mb-4">
                <label htmlFor="lastname" className="block text-gray-600 text-sm mb-2">
                  Soyad
                </label>
                <input
                  type="text"
                  id="lastname"
                  onChange={handleInputChange}
                  value={formData.lastname}
                  className="w-full p-3 border rounded focus:outline-none focus:border-blue-500"
                  placeholder="Soyisim giriniz"
                />
            </div>
          <div className="mb-4">
            <label htmlFor="email" className="block text-gray-600 text-sm mb-2">
              E-posta
            </label>
            <input
              type="email"
              id="email"
              onChange={handleInputChange}
              value={formData.email}
              className="w-full p-3 border rounded focus:outline-none focus:border-blue-500"
              placeholder="E-posta adresinizi girin"
            />
          </div>
          <div className="mb-4">
            <label htmlFor="phoneNo" className="block text-gray-600 text-sm mb-2">
              Telefon No
            </label>
            <input
              type="phone"
              id="phoneNo"
              onChange={handleInputChange}
              value={formData.phoneNo}
              className="w-full p-3 border rounded focus:outline-none focus:border-blue-500"
              placeholder="Telefon Numaranizi Giriniz"
            />   
          </div>
          <div className="mb-6">
            <label htmlFor="password" className="block text-gray-600 text-sm mb-2">
              Şifre
            </label>
            <input
              type="password"
              id="password"
              onChange={handleInputChange}
              value={formData.password}
              className="w-full p-3 border rounded focus:outline-none focus:border-blue-500"
              placeholder="Şifrenizi girin"
            />
          </div>
          <button
              type="submit"
              className="w-full  mt-4 bg-gradient-to-r active:scale-[.98] active:duration-75
               from-purple-500 to-purple-700 hover:from-purple-600 hover:to-purple-800 
               hover:scale-[1.01] text-white py-3 px-6 rounded-full border border-purple-800 
               focus:outline-none hover:shadow-md transition-all 
               duration-300 ease-in-out"
              >
              Kayit Ol
          </button>
        </form>
        <div className="mt-4 text-center">
          <p className="text-gray-600 text-sm">
            Do you Have a Already Account?
            <a href="/login" className="text-blue-500 hover:underline">
              Login
            </a>
          </p>
        </div>
      </div>
    </div>
  </div>
  );
};

export default Register;