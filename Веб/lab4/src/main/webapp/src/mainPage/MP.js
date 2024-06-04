// import React, { useState, useEffect } from 'react';
// import XInput from './XInputComponent';
// import YInput from './YInputComponent';
// import RInput from './RInputComponent';
// import SubmitButton from './SubmitButtonComponent';
// import CanvasComponent from './CanvasComponent';
// import TableComponent from './TableComponent';

// import '../styles/reactstyle.css'
// import '../styles/styles.css';


// export default function Form() {
//   const [selectedXValues, setSelectedXValues] = useState([]);
//   const [yValue, setYValue] = useState(0);
//   const [selectedRValues, setSelectedRValues] = useState([]);

//   const handleXChange = (values) => {
//     setSelectedXValues(values);
//   };

//   const handleYChange = (value) => {
//     setYValue(value);
//   };

//   const handleRChange = (values) => {
//     setSelectedRValues(values);
//   };

//   useEffect(() => {
//     // Вызовите ваш метод drawGraph с новыми значениями X, Y, и R
//     drawGraph(selectedXValues, yValue, selectedRValues);
//   }, [selectedXValues, yValue, selectedRValues]);

//   const drawGraph = (xValues, yValue, rValues) => {
//     console.log('График обновлен с новыми значениями X, Y, и R:', xValues, yValue, rValues);
//   };

//   return (
//     <div className="row">
//       <div className="col-md-5">
//         <div id="canvas">
//         <CanvasComponent rValues={selectedRValues} />
//         </div>
//       </div>

//       <div className="col-md-2">
//         <div id="form">
//           <XInput onChange={handleXChange} />
//         <YInput onChange={handleYChange} />
//         <RInput onChange={handleRChange} />
//         <SubmitButton onSubmit={handleSubmit} />
//         <>
//         </div>
//       </div>

//       <div className="col-md-5 col-xs-12">
//         <div id="table">
//           <TableComponent />
//         </div>
//       </div>
//     </div>
//   );
// }
