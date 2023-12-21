import React from 'react'
import Navbar from '../component/Navbar'
import Footer from '../component/Footer'

const Layout = ({ children }) => {
    return (
      <div>
        <Navbar />
        <div className='mt-10'>
          {children}
        </div> 
        <Footer />
      </div>
    );
  }

export default Layout