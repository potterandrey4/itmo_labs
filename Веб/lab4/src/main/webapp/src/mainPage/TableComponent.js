import React from 'react';

// Предположим, что у вас есть массив объектов с результатами, например:
const results = [
  { x: 1, y: 2, r: 3, isHit: true, executionTime: 5, time: '2022-02-04 12:34:56' },
  // Добавьте остальные результаты сюда
];

export default function Table() {
  return (
    <table className="table table-bordered" id="resultTable">
      <thead>
        <tr>
          <th scope="col">X</th>
          <th scope="col">Y</th>
          <th scope="col">R</th>
          <th scope="col">Result</th>
          <th scope="col">Ex.time (мс)</th>
          <th scope="col">Time</th>
        </tr>
      </thead>
      <tbody>
        {results.map((result, index) => (
          <tr key={index}>
            <td>{result.x}</td>
            <td>{result.y}</td>
            <td>{result.r}</td>
            <td>{result.isHit.toString()}</td>
            <td>{result.executionTime}</td>
            <td>{result.time}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
