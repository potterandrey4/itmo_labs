<%@ page contentType="text/html; charset=UTF-8" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>нЛУ</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="static/styles.css">

</head>
<body>
<div class="header text-center">
    <h1><a href="./index.jsp">Лаба 2</a></h1>
    <h5>Дьячков Андрей</h5>
    <h5>P3209</h5>
</div>
<div class="container">
    <form action="" method="post" id="data-form">
        <h3>Выберете уравнение</h3>
        <div class="form-group">
            <label>
                <input type="radio" name="choiceEquation" value="1"> <span class="equation">\(x^3 - 2.561x^2 - 1.325x + 4.395 = 0\)</span>
            </label>
            <label>
                <input type="radio" name="choiceEquation" value="2"> <span class="equation">\(-1.38x^3 - 5.42x^2 + 2.57x + 10.95 = 0\)</span>
            </label>
            <label>
                <input type="radio" name="choiceEquation" value="3"> <span class="equation">\(-2.4x^3 + 1.27x^2 - 8.63x + 2.31 = 0\)</span>
            </label>
            <label>
                <input type="radio" name="choiceEquation" value="4"> <span class="equation">\(-1.8x^3 - 2.94x^2 + 10.37x + 5.38 = 0\)</span>
            </label>
        </div>


        <div class="form-group">
            <label for="eps"><b>Введите eps:</b></label>
            <input class="form-control" name="eps" id="eps" type="number" min="-3" max="3" step="0.1" value="0"
                   required>
        </div>

        <div class="form-group">
            <label for="eps"><b>Введите a:</b></label>
            <input class="form-control" name="a" id="a" type="number" min="-3" max="3" step="0.1" value="0"
                   required>
        </div>

        <div class="form-group">
            <label for="eps"><b>Введите b:</b></label>
            <input class="form-control" name="b" id="b" type="number" min="-3" max="3" step="0.1" value="0"
                   required>
        </div>


        <button type="button" class="btn btn-primary" onclick="handleSubmitEq()">Вычислить</button>
    </form>


    <h3>Результаты:</h3>

    <div class="section">

        <p>Метод дихотомии:</p>
        <ul>
            <li>Итераций: <span id="dichotomyIterations"></span></li>
            <li>x: <span id="dichotomyX"></span></li>
            <li>f(x): <span id="dichotomyFx"></span></li>
        </ul>
        <canvas width="400" height="400"></canvas>


        <p>Метод хорд:</p>
        <ul>
            <li>Итераций: <span id="secantIterations"></span></li>
            <li>x: <span id="secantX"></span></li>
            <li>f(x): <span id="secantFx"></span></li>
        </ul>
        <canvas width="400" height="400"></canvas>


        <p>Метод Ньютона:</p>
        <ul>
            <li>Итераций: <span id="newtonIterations"></span></li>
            <li>x: <span id="newtonX"></span></li>
            <li>f(x): <span id="newtonFx"></span></li>
        </ul>
        <canvas width="400" height="400"></canvas>

    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="https://polyfill.io/v3/polyfill.min.js?features=es6"></script>
<script id="MathJax-script" async src="https://cdnjs.cloudflare.com/ajax/libs/mathjax/3.2.2/es5/tex-mml-chtml.min.js"></script>
<script src="JS/script.js" defer></script>

</body>