<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Веб-страница с интерфейсом</title>
<style>
        body {
            font-family: Arial, sans-serif;
        }

        .container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
        }

        .section {
            margin-bottom: 20px;
        }

        .radio-group {
            display: flex;
            flex-wrap: wrap;
        }

        .radio-button {
            margin-right: 10px;
        }

        .input-group {
            display: flex;
            margin-bottom: 10px;
        }

        .input-label {
            width: 20%;
            text-align: right;
            padding-right: 10px;
        }

        .input-field {
            width: 80%;
            padding: 5px;
            border: 1px solid #ccc;
        }

        .chart {
            width: 100%;
            height: 200px;
            background-color: #eee;
        }
    </style>
</head>

<body>

<div class="container">
    <h1>Выберете 1 из 4 уравнений</h1>

    <div class="section">
        <h3>График метода дихотомии</h3>

        <div class="radio-group">
            <label class="radio-button">
                <input type="radio" name="equation" value="1">
                1 уравнение
            </label>
            <label class="radio-button">
                <input type="radio" name="equation" value="2">
                2 уравнение
            </label>
            <label class="radio-button">
                <input type="radio" name="equation" value="3">
                3 уравнение
            </label>
            <label class="radio-button">
                <input type="radio" name="equation" value="4">
                4 уравнение
            </label>
        </div>

        <div class="input-group">
            <label class="input-label">a:</label>
            <input type="number" class="input-field" id="a">
        </div>

        <div class="input-group">
            <label class="input-label">b:</label>
            <input type="number" class="input-field" id="b">
        </div>

        <button onclick="calculate()">Вычислить</button>

        <div class="chart" id="chart1"></div>

        <div class="section">
            <h3>Результат:</h3>

            <p>Метод дихотомии:</p>
            <ul>
                <li>Итераций: <span id="dichotomyIterations"></span></li>
                <li>x: <span id="dichotomyX"></span></li>
                <li>f(x): <span id="dichotomyFx"></span></li>
            </ul>

            <p>Метод хорд:</p>
            <ul>
                <li>Итераций: <span id="secantIterations"></span></li>
                <li>x: <span id="secantX"></span></li>
                <li>f(x): <span id="secantFx"></span></li>
            </ul>

            <p>Метод Ньютона:</p>
            <ul>
                <li>Итераций: <span id="newtonIterations"></span></li>
                <li>x: <span id="newtonX"></span></li>
                <li>f(x): <span id="newtonFx"></span></li>
            </ul>
        </div>
    </div>
</div>

</body>
</html>
