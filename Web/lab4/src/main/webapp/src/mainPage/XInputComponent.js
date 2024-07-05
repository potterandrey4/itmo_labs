import React, { useState, useEffect } from 'react';

export default function XInput({ onChange }) {
  const [selectedValues, setSelectedValues] = useState([]);

  const handleCheckboxChange = (value) => {
  const updatedValues = new Set(selectedValues);

  if (updatedValues.has(value)) {
    // Если значение уже есть в Set, удаляем его
    updatedValues.delete(value);
  } else {
    // Если значения нет в Set, добавляем его
    updatedValues.add(value);
  }

  // Преобразуем Set обратно в массив
  const updatedValuesArray = [...updatedValues];

  setSelectedValues(updatedValuesArray);
  onChange(updatedValuesArray); // Обновляем значения X, когда меняются
  };

  useEffect(() => {
    onChange(selectedValues);
  }, [selectedValues, onChange]);

  const initValues = [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2];

  return (
    <>
      <div className="checkbox-title">Изменение X</div>
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
                name="x"
                value={value}
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
