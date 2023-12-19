import React from 'react'
import Navbar from '../component/Navbar'
import Footer from '../component/Footer'
import UserProfile from '../component/UserProfile'


const Profile = () => {
  return (
    <div>
        <Navbar/>        
        <div className='m-60 mt-10'>
           <UserProfile/>
        </div>
        <Footer/>
    </div>
  )
}

export default Profile;