import React, { useState } from 'react';
import axios from 'axios';

const Admin = () => {
  const [image, setImage] = useState(null);
  const [description, setDescription] = useState('');
  const [date, setDate] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();
    const formData = new FormData();
    formData.append('image', image);
    formData.append('memoryDesc', description);
    formData.append('date', date);

    try {
      const response = await axios.post('http://localhost:8080/admin/memory/upload', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      alert('Memory uploaded successfully');
    } catch (error) {
      console.error('Error uploading memory:', error);
    }
  };

  return (
    <div>
      <h2>Admin Page</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <input type="file" onChange={(e) => setImage(e.target.files[0])} required />
        </div>
        <div>
          <input
            type="text"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            placeholder="Memory Description"
            required
          />
        </div>
        <div>
          <input
            type="date"
            value={date}
            onChange={(e) => setDate(e.target.value)}
            required
          />
        </div>
        <button type="submit">Upload Memory</button>
      </form>
    </div>
  );
}

export default Admin;
