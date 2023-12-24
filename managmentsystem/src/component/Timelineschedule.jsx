import React from 'react';
import TimeLineItem from './TimeLineItem';

const Timelineschedule = () => {
  const libraryData = [
    { version: 'v1.0.0', releaseDate: 'December 2, 2021', description: 'Get started with dozens of web components and interactive elements.', name:'kutluaylav' },
    { version: 'v1.2.0', releaseDate: 'December 23, 2021', description: 'Get started with dozens of web components and interactive elements.', name:'kutluaylav' },
    { version: 'v1.3.0', releaseDate: 'January 5, 2022', description: 'Get started with dozens of web components and interactive elements.', name:'kutluaylav' },
    { version: 'v1.4.0', releaseDate: 'February 15, 2022', description: 'Get started with dozens of web components and interactive elements.', name:'kutluaylav' },
    { version: 'v1.5.0', releaseDate: 'March 10, 2022', description: 'Get started with dozens of web components and interactive elements.', name:'kutluaylav' },
    { version: 'v1.6.0', releaseDate: 'April 5, 2022', description: 'Get started with dozens of web components and interactive elements.', name:'kutluaylav' },
  ];

  return (
    <div className="grid grid-cols-1 gap-8 md:grid-cols-2 lg:grid-cols-3 mx-20 ">
      {libraryData.map(library => (
        <TimeLineItem key={library.version} version={library.version} releaseDate={library.releaseDate} description={library.description} name={library.name}/>
      ))}
    </div>
  );
};

export default Timelineschedule;