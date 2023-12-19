import React from 'react'
import UsersTable from '../component/UsersTable'
import Navbar from '../component/Navbar'
import Footer from '../component/Footer'

const Users = () => {
  return (
    <div>
    <Navbar/>
        <div>
            <UsersTable/>
        </div>
    <Footer/>
  </div>
  )
}

export default Users