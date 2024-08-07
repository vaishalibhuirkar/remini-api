import React from 'react';
import './Memory.css';

const Memory = ({ memoryDescriptions }) => {
  return (
    <div>
      <h3>Memory Descriptions:</h3>
      <table className="memory-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Description</th>
            <th>Date</th>
            <th>Image Path</th>
          </tr>
        </thead>
        <tbody>
          {memoryDescriptions.map((memory) => (
            <tr key={memory.pm_id}>
              <td>{memory.pm_id}</td>
              <td>{memory.memory_desc}</td>
              <td>{new Date(memory.date).toLocaleDateString()}</td>
              <td>{memory.image_path}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Memory;
