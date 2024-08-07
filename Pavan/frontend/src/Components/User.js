import React, { useState } from 'react';
import axios from 'axios';
import Memory from './Memory';
import "./User.css"

const User = () => {
  const [image, setImage] = useState(null);
  const [memoryDescriptions, setMemoryDescriptions] = useState([]);
  const [noMatchMessage, setNoMatchMessage] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();
    const formData = new FormData();
    formData.append('image', image);

    try {
      const response = await axios.post('http://localhost:8080/user/memory', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });

      const data = response.data;
      if (data.length === 0) {
        setNoMatchMessage('No matching memories found.');
        setMemoryDescriptions([]);
      } else {
        setNoMatchMessage('');
        setMemoryDescriptions(data);
      }
    } catch (error) {
      console.error('Error fetching memory:', error);
    }
  };

  return (
    <div>
      <h2>User Page</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <input type="file" onChange={(e) => setImage(e.target.files[0])} required />
        </div>
        <button type="submit">Get Memory</button>
      </form>
      {noMatchMessage && (
        <h2 style={{"color":"red"}}>{noMatchMessage}</h2>
      )}
      {memoryDescriptions.length > 0 && (
        <Memory memoryDescriptions={memoryDescriptions} />
      )}
    </div>
  );
};

export default User;
