import React from 'react'
import Layout from './Layout'
import ProductCardList from '../component/ProductCardList'
import Timelineschedule from '../component/Timelineschedule'



export const HomeScreen = () => {
  return (
    <Layout>
    <div>
      <Timelineschedule/>
      <ProductCardList/>
    </div>
  </Layout>
        
    
  )
}
