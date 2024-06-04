import React, { useState } from 'react';

export default function RInput({ onChange }) {
  const [selectedValues, setSelectedValues] = useState([]);

  const handleCheckboxChange = (value) => {
    const updatedValues = new Set(selectedValues);

    if (updatedValues.has(value)) {
      updatedValues.delete(value);
    } else {
      updatedValues.add(value);
    }
    const updatedValuesArray = [...updatedValues];

    setSelectedValues(updatedValuesArray);
    if (updatedValuesArray.length == 0) {localStorage.removeItem('r')}
    else { localStorage.setItem('r', Math.max.apply(null, updatedValuesArray)); }
    onChange(updatedValuesArray);
  };


  const initValues = [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2];

  return (
    <>
      <div className="checkbox-title">Изменение R</div>
      <div className="checkbox-container">
        <div className="checkbox-group">
          {initValues.map((value) => (
            <label key={value} className="checkbox-label">
              <div
                className={`checkbox-custom ${selectedValues.includes(value) ? 'checked' : ''}`}
                onClick={() => handleCheckboxChange(value)}
              />
              <input
                type="checkbox"
                className="checkbox-input"
                name="r"
                value={value >= 0.5 && value <= 2 ? value : ''}
                checked={selectedValues.includes(value)}
                onChange={() => handleCheckboxChange(value)}
              />
              <span className="checkbox-text">{` ${value} `}</span>
            </label>
          ))}
        </div>
      </div>
    </>
  );
}
