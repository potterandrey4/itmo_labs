import React, { useState } from 'react';

export default function YInput({ onChange }) {
  const [yValue, setYValue] = useState(0);
  const [errorMessage, setErrorMessage] = useState('');

  const handleInputChange = (event) => {
    const inputValue = parseFloat(event.target.value);

    if (!isNaN(inputValue) && inputValue >= -3 && inputValue <= 3) {
      setYValue(inputValue);
      setErrorMessage('');
    } else {
      setErrorMessage('Введите число в диапазоне [-3, 3]');
    }
    onChange(inputValue)
  };

  return (
    <div className="form-group">
      <label htmlFor="y">
        <b>Изменение Y:</b>
      </label>
      <input
        className="form-control"
        name="y"
        id="y"
        type="number"
        min="-3"
        max="3"
        step="0.1"
        value={yValue}
        onChange={handleInputChange}
        required
      />
      <p id="msg-y">{errorMessage}</p>
      <div id="error-message" style={{ color: 'red' }}></div>
      <audio id="error-audio" src="static/неправильно.mp3"></audio>
    </div>
  );
}
