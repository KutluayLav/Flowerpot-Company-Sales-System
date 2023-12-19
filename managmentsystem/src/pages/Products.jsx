import React from 'react'
import Navbar from '../component/Navbar'
import ProductsTable from '../component/ProductsTable'
import Footer from '../component/Footer'

const Products = () => {
  return (
    <div>
        <Navbar/>
        <div className='mt-10'>
            <ProductsTable/>
        </div> 
        <Footer/>
    </div>
  )
}

export default Products