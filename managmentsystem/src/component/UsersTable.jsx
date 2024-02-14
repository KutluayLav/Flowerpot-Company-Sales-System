import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Fragment } from 'react';
import { Disclosure, Menu, Transition } from '@headlessui/react';

function classNames(...classes) {
  return classes.filter(Boolean).join(' ');
}

export default function UsersTable() {
  const [people, setPeople] = useState([]);
  const [search, setSearch] = useState('');

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/auth/getallusers'); 
        setPeople(response.data);
      } catch (error) {
        console.error('Error fetching data: ', error);
      }
    };

    fetchData();
  }, []);

  return (
    <div className='mt-10'>
      <h1 className='flex justify-center text-4xl'>Users</h1>
      <div className="mt-5 mx-20">
        <input
          type="text"
          placeholder="Search by email"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="border-2 border-gray-300 bg-white h-10 px-5 rounded-lg text-sm focus:outline-none"
        />
      </div>
      <ul role="list" className="divide-y divide-gray-100 mt-5">
        {people.map(({ id, firstname, lastname, phoneNo, email, createdDate, lastLoginDate, roles }) => (
          <li key={id} className="flex justify-between gap-x-6 py-5 mx-20">
            <div className="flex min-w-0 gap-x-4">
              <div className="min-w-0 flex-auto">
                <p className="text-sm font-semibold leading-6 text-gray-900">{`${firstname} ${lastname}`}</p>
                <p className="mt-1 truncate text-xs leading-5 text-gray-500"><strong>Email:</strong>{email}</p>
                <p className="mt-1 truncate text-xs leading-5 text-gray-500"><strong>Phone Number:</strong>{phoneNo}</p>
              </div>
            </div>
            <div className="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
              <p className="text-sm leading-6 text-gray-900">
                {roles.join(', ')}
              </p>
              <p className="mt-1 text-xs leading-5 text-gray-500">
                Account created <time dateTime={createdDate}>{createdDate}</time>
              </p>
              {lastLoginDate && (
                <p className="mt-1 text-xs leading-5 text-gray-500">
                  Last logged in <time dateTime={lastLoginDate}>{lastLoginDate}</time>
                </p>
              )}
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}