import React from 'react'
import UserProfile from '../component/UserProfile'
import Layout from './Layout'

const Profile = () => {
  return (
    <Layout>
    <div className='m-60 mt-10'>
      <UserProfile/>
    </div>
  </Layout>
  )
}

export default Profile;
