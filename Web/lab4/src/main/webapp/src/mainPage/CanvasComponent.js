import React, { useRef, useState, useEffect } from 'react';
import axios from 'axios';

export default React.forwardRef(function CanvasComponent({ rValues }, ref) {
  const canvasRef = useRef(null);

  const drawAxes = (ctx, canvasPlotWidth, canvasPlotHeight, scaleX, scaleY, xAxis, yAxis) => {
    ctx.font = `${Math.round(scaleX / 2)}px Arial`;
    ctx.textAlign = 'left';
    ctx.textBaseline = 'top';
    ctx.beginPath();
    ctx.strokeStyle = '#f5f0e8';

    let zz = 5;

    for (let i = 0; i <= canvasPlotWidth; i = i + scaleX) {
      ctx.moveTo(i, 0);
      ctx.lineTo(i, canvasPlotHeight);
      ctx.fillText((i - xAxis) / scaleX, i + zz, yAxis + zz);
    }

    for (let i = 0; i <= canvasPlotHeight; i = i + scaleY) {
      ctx.moveTo(0, i);
      ctx.lineTo(canvasPlotWidth, i);
      ctx.fillText((yAxis - i) / scaleY, xAxis + zz, i + zz);
    }
    ctx.stroke();
    ctx.closePath();

    const zxy = 50;
    ctx.beginPath();
    ctx.strokeStyle = '#000000';
    ctx.moveTo(xAxis, 0);
    ctx.lineTo(xAxis, canvasPlotHeight);
    ctx.fillText('y', xAxis - zxy, 0);
    ctx.moveTo(0, yAxis);
    ctx.lineTo(canvasPlotWidth, yAxis);
    ctx.fillText('x', canvasPlotWidth - zxy, yAxis - zxy);
    ctx.stroke();
    ctx.closePath();
  };

  const drawGraph = (ctx, rValues, xAxis, yAxis, scaleX, scaleY, canvasPlotWidth, canvasPlotHeight) => {
  	let r = localStorage.getItem('r');
    if (r > 0 && r <= 2) {
      ctx.clearRect(0, 0, canvasPlotWidth, canvasPlotHeight);
      drawAxes(ctx, canvasPlotWidth, canvasPlotHeight, scaleX, scaleY, xAxis, yAxis);

      ctx.beginPath();
      ctx.fillStyle = 'rgba(0, 0, 0, 0.66)';
      const xTriangle = xAxis;
      const yTriangle = yAxis;
      const xVertex1 = xTriangle;
      const yVertex1 = yTriangle;

      const xVertex2 = xTriangle;
      const yVertex2 = yTriangle - r / 2 * scaleY;

      const xVertex3 = xTriangle - r / 2 * scaleX;
      const yVertex3 = yTriangle;
      ctx.moveTo(xVertex1, yVertex1);
      ctx.lineTo(xVertex2, yVertex2);
      ctx.lineTo(xVertex3, yVertex3);
      ctx.fill();
      ctx.closePath();

      ctx.beginPath();
      ctx.fillStyle = 'rgba(0, 0, 0, 0.66)';

      const xRect = xAxis;
      const yRect = yAxis;

      const widthRect = r * scaleX;
      const heightRect = r / 2 * scaleY;

      ctx.fillRect(xRect, yRect - heightRect, widthRect, heightRect);
      ctx.closePath();

      ctx.beginPath();
      ctx.fillStyle = 'rgba(0, 0, 0, 0.66)';
      const xCircle = xAxis;
      const yCircle = yAxis;
      ctx.arc(xCircle, yCircle, r / 2 * scaleX, 0, Math.PI / 2);
      ctx.lineTo(xCircle, yCircle);
      ctx.fill();
      ctx.closePath();
    }
    if (r == null) {
      ctx.clearRect(0, 0, canvasPlotWidth, canvasPlotHeight);
      drawAxes(ctx, canvasPlotWidth, canvasPlotHeight, scaleX, scaleY, xAxis, yAxis);
    }
  };

  const drawDots = () => {
    const canvas = canvasRef.current;
    const ctx = canvas.getContext('2d');
    const canvasPlotWidth = canvas.width;
    const canvasPlotHeight = canvas.height;
    const scaleX = 40;
    const scaleY = 40;
    const xAxis = Math.round(canvasPlotWidth / scaleX / 2) * scaleX;
    const yAxis = Math.round(canvasPlotHeight / scaleY / 2) * scaleY;
    let points = JSON.parse(localStorage.getItem('dots')) || [];
    const originalColor = ctx.fillStyle;
    let r = JSON.parse(localStorage.getItem('r'));

    if (typeof r === 'undefined' || r === null) {
      r = 0;
    }

    ctx.clearRect(0, 0, canvasPlotWidth, canvasPlotHeight);
    drawAxes(ctx, canvasPlotWidth, canvasPlotHeight, scaleX, scaleY, xAxis, yAxis);
    drawGraph(ctx, rValues, xAxis, yAxis, scaleX, scaleY, canvasPlotWidth, canvasPlotHeight);

    points.forEach((point) => {
      const xGraph = xAxis + point.x * scaleX;
      const yGraph = yAxis - point.y * scaleY;

      const x = point.x;
      const y = point.y;
      ctx.beginPath();

      if (r / 2 >= x * x + y * y && x >= 0 && y <= 0) {
        ctx.fillStyle = 'green';
      } else if (y <= r - y && y <= r && x <= r && x >= 0 && y >= 0) {
        ctx.fillStyle = 'green';
      } else if (y <= x + r / 2 && x <= 0 && y >= 0) {
        ctx.fillStyle = 'green';
      } else {
        ctx.fillStyle = 'red';
      }

      ctx.arc(xGraph, yGraph, 3, 0, 2 * Math.PI);
      ctx.fill();
      ctx.closePath();
    });

    ctx.fillStyle = originalColor;
  };

  useEffect(() => {
    if (canvasRef.current) {
      const canvas = canvasRef.current;
      const ctx = canvas.getContext('2d');
      const canvasPlotWidth = canvas.width;
      const canvasPlotHeight = canvas.height;
      const scaleX = 40;
      const scaleY = 40;
      const xAxis = Math.round(canvasPlotWidth / scaleX / 2) * scaleX;
      const yAxis = Math.round(canvasPlotHeight / scaleY / 2) * scaleY;

      drawDots();
    }
  }, [rValues]);

  useEffect(() => {
    const canvas = canvasRef.current;
    const ctx = canvas.getContext('2d');

    const handleCanvasClick = (event) => {
      const x = event.clientX - canvas.getBoundingClientRect().left;
      const y = event.clientY - canvas.getBoundingClientRect().top;

      let graphX = (x - canvas.width / 2) / 40;
      let graphY = (canvas.height / 2 - y) / 40;

      const existingDots = JSON.parse(localStorage.getItem('dots')) || [];
      existingDots.push({ x: graphX, y: graphY });
      localStorage.setItem('dots', JSON.stringify(existingDots));
      drawDots();

      //todo: вкорячить отправку на сервер
    };

    canvas.addEventListener('click', handleCanvasClick);

    // Очистка слушателя событий при размонтировании компонента
    return () => {
      canvas.removeEventListener('click', handleCanvasClick);
    };
  }, []); // Пустой массив зависимостей означает, что эффект будет запущен только при монтировании и размонтировании компонента

  // Передаем ref в компонент для доступа к методу drawDots
  React.useImperativeHandle(ref, () => ({
    drawDots: drawDots,
  }));

  return <canvas ref={canvasRef} width={400} height={400} />;
});
