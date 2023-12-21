import React from 'react'
import UsersTable from '../component/UsersTable'
import Layout from './Layout'

const Users = () => {
  return (
    <Layout>
     <div>
        <UsersTable/>
     </div>
  </Layout>
  )
}

export default Users