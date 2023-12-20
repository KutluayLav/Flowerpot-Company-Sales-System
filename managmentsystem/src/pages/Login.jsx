import React, { useState } from 'react';
import bahceResim from '../assets/bahce.jpeg';
import { loginUser } from '../api/loginRequest';
import { useNavigate } from 'react-router-dom';
export const Login = () => {

  const [error, setError] = useState('');
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });

  const handleInputChange = (e) => {
    setFormData({ ...formData, [e.target.id]: e.target.value });
  };

  const handleFormSubmit = async (e) => {
    e.preventDefault();

    try {
      const response =await loginUser(formData);

      const { accessToken } = response;

      localStorage.setItem('accessToken', accessToken);
      
      console.log('User Login successfully!');
      navigate('/home');
    } catch (error) {
      console.error('User Login failed:', error);
      setError('Email or password is incorrect. Please try again.');
    }
  };


  return (
    <div className="min-h-screen flex w-full">
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
            Giriş Yap
          </h2>
          {error && (
            <div className="mb-4 text-red-500 text-center">{error}</div>
          )}
          <form onSubmit={handleFormSubmit}>
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
            <div className='mt-8 flex justify-between items-center'>
                    <div>
                        <input  type="checkbox" id='remember'/>
                        <label className='ml-2 font-medium text-base' htmlFor="remember">Remember</label>
                    </div>
                    <button className='font-medium text-base text-violet-500' href='/login'>Forgot password</button>
                </div>
            <button
                type="submit"
                className="w-full  mt-4 bg-gradient-to-r active:scale-[.98] active:duration-75
                 from-purple-500 to-purple-700 hover:from-purple-600 hover:to-purple-800 
                 hover:scale-[1.01] text-white py-3 px-6 rounded-full border border-purple-800 
                 focus:outline-none hover:shadow-md transition-all 
                 duration-300 ease-in-out"
                >
                Giriş Yap
            </button>
          </form>
          <div className="mt-4 text-center">
            <p className="text-gray-600 text-sm">
              Do you Have a Account?
              <a href="/register" className="text-blue-500 hover:underline">
                Register
              </a>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};