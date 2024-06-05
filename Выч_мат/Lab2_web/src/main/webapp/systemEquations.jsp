<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Веб-страница с интерфейсом</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="static/styles.css">
    <script src="https://polyfill.io/v3/polyfill.min.js?features=es6"></script>
    <script id="MathJax-script" async src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js"></script>
</head>
<body>
<div class="header text-center">
    <h1><a href="./">Лаба 2</a></h1>
    <h5>Дьячков Андрей</h5>
    <h5>P3209</h5>
</div>
<div class="container">
    <form action="" method="post" id="data-form">
        <h3>Выберете уравнение</h3>
        <div class="form-group">
            <label>
                <input type="radio" name="choiceSystemEquations" value="1"> <span class="equationsSystem">\begin{cases}
        x^3 - 2.561x^2 - 1.325x + 4.395 = 0 \\
        -1.38x^3 - 5.42x^2 + 2.57x + 10.95 = 0
        \end{cases}</span>
            </label>
            <label>
                <input type="radio" name="choiceSystemEquations" value="2"> <span class="equationsSystem">\begin{cases}
        -2.4x^3 + 1.27x^2 - 8.63x + 2.31 = 0 \\
        -1.8x^3 - 2.94x^2 + 10.37x + 5.38 = 0
        \end{cases}</span>
            </label>
        </div>



        <div class="form-group">
            <label for="eps"><b>Введите eps:</b></label>
            <input class="form-control" name="eps" id="eps" type="number" min="-3" max="3" step="0.1" value="0"
                   required>
        </div>


        <button type="button" class="btn btn-primary" id="submitBtn">Вычислить</button>
    </form>


    <h3>Результаты:</h3>

    <div class="section">

        <p>Метод простой итерации:</p>
        <ul>
            <li>Итераций: <span id="simpleIterations"></span></li>
            <li>x: <span id="simpleX"></span></li>
            <li>f(x): <span id="simpleFx"></span></li>
        </ul>
        <canvas width="400" height="400"></canvas>

    </div>
</div>

<script>
    window.addEventListener('DOMContentLoaded', (event) => {
        MathJax.typesetPromise();

        const equations = document.querySelectorAll('.equation');
        equations.forEach((equation) => {
            equation.innerHTML = '$$' + equation.innerHTML + '$$';
        });
    });
</script>

</body>