import React from 'react';
import ProductCard from './ProductCard';
import saksiresim from '../assets/saksi.jpg'
const ProductCardList = () => {

  const productData = [
    { id: 1, title: 'Product 1', description: 'Description for Product 1', imageUrl: saksiresim },
    { id: 2, title: 'Product 2', description: 'Description for Product 2', imageUrl: saksiresim },
    { id: 3, title: 'Product 3', description: 'Description for Product 2', imageUrl: saksiresim },
    { id: 4, title: 'Product 1', description: 'Description for Product 1', imageUrl: saksiresim },
    { id: 5, title: 'Product 2', description: 'Description for Product 2', imageUrl: saksiresim },
    { id: 6, title: 'Product 3', description: 'Description for Product 2', imageUrl: saksiresim },
    { id: 1, title: 'Product 1', description: 'Description for Product 1', imageUrl: saksiresim },
    { id: 2, title: 'Product 2', description: 'Description for Product 2', imageUrl: saksiresim },
    { id: 3, title: 'Product 3', description: 'Description for Product 2', imageUrl: saksiresim },
    { id: 4, title: 'Product 1', description: 'Description for Product 1', imageUrl: saksiresim },
    { id: 5, title: 'Product 2', description: 'Description for Product 2', imageUrl: saksiresim },
    { id: 6, title: 'Product 3', description: 'Description for Product 2', imageUrl: saksiresim },
   
  ];

  return (
    <div className="grid grid-cols-1 gap-8 md:grid-cols-2 lg:grid-cols-3 mx-20 mb-60">
      {productData.map(product => (
        <ProductCard key={product.id} title={product.title} description={product.description} imageUrl={product.imageUrl} />
      ))}
    </div>
  );
};

export default ProductCardList;