<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="ru">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Laba2</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="static/styles.css">
    <link rel="shortcut icon" href="static/favicon.ico" type="image/x-icon">

</head>

<body>
<div class="header text-center">
    <h1><a href="./">Лаба 2</a></h1>
    <h5>Дьячков Андрей</h5>
    <h5>P3209</h5>
    <h5>Номер варианта: <i>90840</i></h5>
</div>

<div class="text-center wrapper">
    <h6 id="form-for-msg" style="text-align:center; color: darkred; padding:3px; border-radius:5px"></h6>
    <div class="container-fluid">
        <div class="row">


            <div class="col-md-5">
                <canvas id="canvas" width="400" height="400"></canvas>
            </div>

            <div class="col-md-2">
                <form action="" method="post" id="data-form">
                    <div class="form-group">
                        <label>
                            <input type="checkbox" name="x" value="-3"> X = -3
                        </label>
                        <label>
                            <input type="checkbox" name="x" value="-2"> X = -2
                        </label>
                        <label>
                            <input type="checkbox" name="x" value="-1"> X = -1
                        </label>
                        <label>
                            <input type="checkbox" name="x" value="0"> X = 0
                        </label>
                        <label>
                            <input type="checkbox" name="x" value="1"> X = 1
                        </label>
                        <label>
                            <input type="checkbox" name="x" value="2"> X = 2
                        </label>
                        <label>
                            <input type="checkbox" name="x" value="3"> X = 3
                        </label>
                        <label>
                            <input type="checkbox" name="x" value="4"> X = 4
                        </label>
                        <label>
                            <input type="checkbox" name="x" value="5"> X = 5
                        </label>
                    </div>

                    <div class="form-group">
                        <label for="y"><b>Изменение Y:</b></label>
                        <input class="form-control" name="y" id="y" type="number" min="-3" max="3" step="0.1" value="0"
                               required>
                        <p id="msg-y">Введите число в диапазоне [-3, 3]</p>
                        <div id="error-message" style="color: red;"></div>
                        <audio id="error-audio" src="static/неправильно.mp3"></audio>
                    </div>

                    <div class="form-group">
                        <label><b>Изменение R:</b></label>
                        <label>
                            <input type="radio" name="r" value="1" onchange="handleRadioButtonChange(this)"> R = 1
                        </label>
                        <label>
                            <input type="radio" name="r" value="2" onchange="handleRadioButtonChange(this)"> R = 2
                        </label>
                        <label>
                            <input type="radio" name="r" value="3" onchange="handleRadioButtonChange(this)"> R = 3
                        </label>
                        <label>
                            <input type="radio" name="r" value="4" onchange="handleRadioButtonChange(this)"> R = 4
                        </label>
                        <label>
                            <input type="radio" name="r" value="5" onchange="handleRadioButtonChange(this)"> R = 5
                        </label>

                    </div>

                    <button type="button" class="btn btn-primary" id="submitBtn">Вычислить</button>
                </form>
                <button type="button" class="btn btn-dark" onclick="getData('clear')">Очистить таблицу</button>
                <button type="button" class="btn btn-dark" id="clearDotsBtn">Очистить точки</button>
            </div>

            <div class="table col-md-5 col-xs-12">
                <table class="table table-bordered" id="resultTable">

                    <thead>
                    <tr>
                        <th scope="col">X</th>
                        <th scope="col">Y</th>
                        <th scope="col">R</th>
                        <th scope="col">Result</th>
                        <th scope="col">Ex.time (мс)</th>
                        <th scope="col">Time</th>
                    </tr>
                    </thead>

                    <tbody>
                    <jsp:useBean id="results" scope="session" class="beans.DataListBean"/>
                    <core:forEach var="result" items="${results.results}">
                        <tr>
                            <td>${result.x}</td>
                            <td>${result.y}</td>
                            <td>${result.r}</td>
                            <td>${result.isHit}</td>
                            <td>${result.executionTime}</td>
                            <td>${result.time}</td>
                        </tr>
                    </core:forEach>
                    </tbody>
                </table>
            </div>

        </div>

    </div>
</div>

<footer>
    <div class="container">
        <h6>Претензии и предложения направлять в бумажном виде</h6>
        <p><a href="static/lgd.pdf" target="_blank">*бланк*</a></p>
    </div>

</footer>

<%--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>--%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/big.js/5.2.2/big.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>


<script src="JS/script.js"></script>
<script src="JS/graph.js"></script>

</body>

</html>
