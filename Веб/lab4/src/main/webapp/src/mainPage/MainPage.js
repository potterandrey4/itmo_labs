import React, {useState, useEffect, useRef} from 'react';
import axios from 'axios';

import XInput from './XInputComponent';
import YInput from './YInputComponent';
import RInput from './RInputComponent';
import SubmitButton from './SubmitButtonComponent';
import CanvasComponent from './CanvasComponent';
import TableComponent from './TableComponent';

import '../styles/reactstyle.css'
import '../styles/styles.css';

export default function MainPage() {
    const [selectedXValues, setSelectedXValues] = useState([]);
    const [yValue, setYValue] = useState(0);
    const [selectedRValues, setSelectedRValues] = useState([]);
    const canvasRef = useRef(null);

    const handleXChange = (values) => {
        setSelectedXValues(values);
    };

    const handleYChange = (value) => {
        setYValue(value);
    };

    const handleRChange = (values) => {
        setSelectedRValues(values);
    };

    const handleSubmit = () => {
        console.log('Получены:', {
            x: selectedXValues,
            y: yValue,
            r: selectedRValues,
        });

        const existingDots = JSON.parse(localStorage.getItem('dots')) || [];
        const newDots = selectedXValues.map((x) => ({x, y: yValue}));
        const allDots = [...existingDots, ...newDots];
        localStorage.setItem('dots', JSON.stringify(allDots));

        if (canvasRef.current) {
            if (selectedRValues.length == 0) {
                localStorage.removeItem('r')
            } else {
                localStorage.setItem('r', Math.max.apply(null, selectedRValues))
            }
            canvasRef.current.drawDots();
        }

        let dataToSend = {
            x: selectedXValues,
            y: yValue,
            r: selectedRValues
        }

        //todo: вкорячить отправку на сервер
        axios.post('/api/your-endpoint', dataToSend)
            .then(response => {
                // Обработка успешного ответа от сервера
                console.log('Сервер ответил:', response.data);
            })
            .catch(error => {
                // Обработка ошибки
                console.error('Произошла ошибка при отправке запроса:', error);
            });

    };


    const handleClearClick = () => {
        if (canvasRef.current) {
            localStorage.removeItem('dots');
            canvasRef.current.drawDots();
        }
    };


    return (
        <div className="row">
            <div className="col-md-5">
                <div id="canvas">
                    <CanvasComponent
                        ref={canvasRef}
                        rValues={Math.max.apply(null, selectedRValues)}
                    />
                </div>
            </div>

            <div className="col-md-2">
                <div id="form">
                    <XInput onChange={handleXChange}/>
                    <YInput onChange={handleYChange}/>
                    <RInput onChange={handleRChange}/>
                    <SubmitButton onSubmit={handleSubmit}/>
                    <button onClick={handleClearClick}>Очистить</button>
                </div>
            </div>

            <div className="col-md-5 col-xs-12">
                <div id="table">
                    <TableComponent/>
                </div>
            </div>
        </div>
    );
}
