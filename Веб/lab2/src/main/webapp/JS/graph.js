const canvasPlot = document.getElementById(`canvas`);
const ctx = canvasPlot.getContext(`2d`);

//Рисуем сетку
const canvasPlotWidth = canvasPlot.clientWidth;
const canvasPlotHeight = canvasPlot.clientHeight;
const scaleX = 40; //расстояние между элементами сетки по Х
const scaleY = 40; //расстояние между элементами сетки по Y

const xAxis = Math.round(canvasPlotWidth / scaleX / 2) * scaleX;
const yAxis = Math.round(canvasPlotHeight / scaleY / 2) * scaleY;

function drawAxes() {
// Выбор шрифта и его вид, размер

    ctx.font = `${Math.round(scaleX / 2)}px Arial`;
    ctx.textAlign = `left`;
    ctx.textBaseline = `top`;
    ctx.beginPath();
    ctx.strokeStyle = `#f5f0e8`;

    let zz = 5; //Зазор между осями и числами нумерации чисел

    for (let i = 0; i <= canvasPlotWidth; i = i + scaleX) {
        ctx.moveTo(i, 0);
        ctx.lineTo(i, canvasPlotHeight); // нанесение вертикальных линий разметки
        ctx.fillText((i - xAxis) / scaleX, i + zz, yAxis + zz); //нанесение числовых значений на ось Х
    }

    for (let i = 0; i <= canvasPlotHeight; i = i + scaleY) {
        ctx.moveTo(0, i);
        ctx.lineTo(canvasPlotWidth, i); // нанесение горизонтальных линий разметки
        ctx.fillText((yAxis - i) / scaleY, xAxis + zz, i + zz); //нанесение числовых значений на ось Y
    }
    ctx.stroke();
    ctx.closePath(); //закрыть путь, чтобы рисовать новые линии другим цветом


    const zxy = 50; //Зазор между осями и обозначением этих осей  x,y
    ctx.beginPath();
    ctx.strokeStyle = `#000000`;
    ctx.moveTo(xAxis, 0);
    ctx.lineTo(xAxis, canvasPlotHeight);
    ctx.fillText(`y`, xAxis - zxy, 0); // означение оси Y
    ctx.moveTo(0, yAxis);
    ctx.lineTo(canvasPlotWidth, yAxis);
    ctx.fillText(`x`, canvasPlotWidth - zxy, yAxis - zxy); // означение оси X
    ctx.stroke();
    ctx.closePath(); //закрыть путь, чтобы рисовать новые линии другим цветом
}


//ПРЕОБРАЗОВАНИЕ CANVAS СИСТЕМУ КООРДИНАТ В ДЕКАРТОВУ СИСТЕМУ КООРДИНАТ

// Рисуем график функции
function drawGraph(r) {
    ctx.clearRect(0, 0, canvasPlotWidth, canvasPlotHeight); // Очистить весь холст
    drawAxes();

    // Квадрат
    ctx.fillStyle = `rgba(0, 0, 0, 0.66)`;
    const xSquare = xAxis - r * scaleX;
    const ySquare = yAxis - r * scaleY;
    ctx.fillRect(xSquare, ySquare, r * scaleX, r * scaleY);

    // Четверть круга
    ctx.beginPath();
    ctx.fillStyle = `rgba(0, 0, 0, 0.66)`;
    const xCircle = xAxis;
    const yCircle = yAxis;
    ctx.arc(xCircle, yCircle, r * scaleX, Math.PI / 2, Math.PI);
    ctx.lineTo(xCircle, yCircle);
    ctx.fill();
    ctx.closePath();

    // Треугольник
    ctx.beginPath();
    ctx.fillStyle = `rgba(0, 0, 0, 0.66)`;
    const xTriangle = xAxis;
    const yTriangle = yAxis + r * scaleY;
    ctx.moveTo(xTriangle, yTriangle);
    ctx.lineTo(xTriangle, yTriangle - r * scaleY);
    ctx.lineTo(xTriangle + r * scaleX, yTriangle - r * scaleY);
    ctx.closePath();
    ctx.fill();
}

window.onload = function () {
    ctx.fillStyle = `rgba(0, 0, 0, 0.66)`; // цвет графика чёрный
    drawAxes();
}

function drawDots(points) {
    // Точки
    ctx.fillStyle = `rgba(0, 0, 0, 0.66)`;
    points.forEach(point => {
        const x = xAxis + point.x * scaleX;
        const y = yAxis - point.y * scaleY;
        ctx.beginPath();
        ctx.arc(x, y, 3, 0, 2 * Math.PI);
        ctx.fill();
        ctx.closePath();
    });
}

function clearDots() {
    dots = [];
    localStorage.removeItem("dots");
}