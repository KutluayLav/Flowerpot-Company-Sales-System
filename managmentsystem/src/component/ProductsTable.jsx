import React from 'react'
import { useNavigate } from 'react-router-dom';

const ProductsTable = () => {

  const navigate = useNavigate();

  const handleAddProductClick = () => {
  
    navigate('/add-product');
  };



  return (
    <div >
      <div class="flex justify-center items-center w-full space-x-60">
          <h1 class="text-4xl mb-4 text-gray-500">Products</h1>
       <div class="flex space-x-4 items-center">
        <div className="relative">
              <input type="text" placeholder="Search" className="border rounded p-2 pl-10" />
              <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-6 h-6">
                  <path strokeLinecap="round" strokeLinejoin="round" d="M21 21l-5.197-5.197m0 0A7.5 7.5 0 105.196 5.196a7.5 7.5 0 0010.607 10.607z" />
                </svg>
              </div>
            </div>
            <button class="bg-green-500 hover:bg-green-700 text-white  py-2 px-4 rounded" onClick={handleAddProductClick}>
                Add Product
            </button>
            <button class="bg-blue-500 hover:bg-blue-700 text-white py-2 px-4 rounded">
                Create Order
            </button>
    </div>
</div>
    <div className="flex justify-center items-center ">
    <table className="w-full max-w-4xl table-auto border border-collapse shadow-lg bg-white">
      <thead>
        <tr>
          <th className="border p-3">Product Image</th>
          <th className="border p-3">Product Name</th>
          <th className="border p-3">Category</th>
          <th className="border p-3">Price</th>
          <th className="border p-3">Quantity</th>
          <th className="border p-3">Description</th>
          <th className="border p-3">Edit</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td className="border p-3">
          <div className='flex items-center justify-center'>
              <img
                src="https://placekitten.com/50/50"
                alt="Product 1"
                className="w-10 h-10 object-cover rounded-full"
              />
            </div>
          </td>
          <td className="border p-3">Product 1</td>
          <td className="border p-3">Electronics</td>
          <td className="border p-3">$499.99</td>
          <td className="border p-3">10</td>
          <td className="border p-3">High-quality electronics with advanced features.</td>
          <td className="border p-3">
              <span className="text-blue-500  cursor-pointer hover:text-black flex justify-center items-center text-2xl">Edit</span>
         </td>
        </tr>
        <tr>
          <td className="border p-3">
          <div className='flex items-center justify-center'>
              <img
                src="https://placekitten.com/50/50"
                alt="Product 1"
                className="w-10 h-10 object-cover rounded-full"
              />
            </div>
          </td>
          <td className="border p-3">Product 2</td>
          <td className="border p-3">Clothing</td>
          <td className="border p-3">$39.99</td>
          <td className="border p-3">50</td>
          <td className="border p-3">Comfortable and stylish clothing for all occasions.</td>
          <td className="border p-3">
              <span className="text-blue-500  cursor-pointer hover:text-black flex justify-center items-center text-2xl">Edit</span>
         </td>
        </tr>
        <tr>
          <td className="border p-3">
          <div className='flex items-center justify-center'>
              <img
                src="https://placekitten.com/50/50"
                alt="Product 1"
                className="w-10 h-10 object-cover rounded-full"
              />
            </div>
          </td>
          <td className="border p-3">Product 3</td>
          <td className="border p-3">Home & Kitchen</td>
          <td className="border p-3">$129.99</td>
          <td className="border p-3">20</td>
          <td className="border p-3">Durable and functional products for your home.</td>
          <td className="border p-3">
              <span className="text-blue-500  cursor-pointer hover:text-black flex justify-center items-center text-2xl">Edit</span>
         </td>
        </tr>
        <tr>
          <td className="border p-3">
            <div className='flex items-center justify-center'>
              <img
                src="https://placekitten.com/50/50"
                alt="Product 1"
                className="w-10 h-10 object-cover rounded-full"
              />
            </div>
          </td>
          
          <td className="border p-3">Product 1</td>
          <td className="border p-3">Electronics</td>
          <td className="border p-3">$499.99</td>
          <td className="border p-3">10</td>
          <td className="border p-3">High-quality electronics with advanced features.</td>
          <td className="border p-3">
              <span className="text-blue-500  cursor-pointer hover:text-black flex justify-center items-center text-2xl">Edit</span>
         </td>
        </tr>        <tr>
          <td className="border p-3">
          <div className='flex items-center justify-center'>
              <img
                src="https://placekitten.com/50/50"
                alt="Product 1"
                className="w-10 h-10 object-cover rounded-full"
              />
            </div>
          </td>
          <td className="border p-3">Product 1</td>
          <td className="border p-3">Electronics</td>
          <td className="border p-3">$499.99</td>
          <td className="border p-3">10</td>
          <td className="border p-3">High-quality electronics with advanced features.</td>
          <td className="border p-3">
              <span className="text-blue-500  cursor-pointer hover:text-black flex justify-center items-center text-2xl">Edit</span>
         </td>
        </tr>      
        {/* Add more rows as needed */}
      </tbody>
    </table>
  </div>
</div>
  )
}

export default ProductsTable