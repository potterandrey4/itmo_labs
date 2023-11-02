<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
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
    <div class="container-fluid">
        <div class="row">


            <div class="col-md-5">
                <canvas id="canvas" width="400" height="400"></canvas>
            </div>

            <div class="col-md-2">
                <form action="public_html/result.php" method="post" id="data-form">
                    <div class="form-group">
                        <label for="x"><b>Изменение X</b></label>
                        <select class="form-control" name="x" id="x" required>
                            <option value="-4">-4</option>
                            <option value="-3">-3</option>
                            <option value="-2">-2</option>
                            <option value="-1">-1</option>
                            <option value="0">0</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="y"><b>Изменение Y:</b></label>
                        <input class="form-control" name="y" id="y" type="number" min="-5" max="5" step="0.1" required>
                        <p id="msg-y">Введите число в диапазоне [-5, 5]</p>
                        <div id="error-message" style="color: red;"></div>
                        <audio id="error-audio" src="static/неправильно.mp3"></audio>
                    </div>

                    <div class="form-group">
                        <label for="r"><b>Изменение R:</b></label>
                        <select class="form-control" name="r" id="r">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <button type="reset" class="btn btn-secondary">Не жми</button>
                        <button type="button" class="btn btn-primary" id="submitBtn">Жмяк</button>
                    </div>
                </form>
                <button type="button" class="btn btn-dark" id="clearTable">Очистить таблицу</button>
            </div>

            <div class="col-md-5 col-xs-12">
                <table class="table table-bordered" id="resultTable">

                    <thead>
                    <tr>
                        <th scope="col">X</th>
                        <th scope="col">Y</th>
                        <th scope="col">R</th>
                        <th scope="col">Result</th>
                        <th scope="col">Ex.time</th>
                        <th scope="col">Time</th>
                    </tr>
                    </thead>

                    <tbody>

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

<%--<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>--%>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<script src="JS/script.js"></script>
<script src="JS/graph.js"></script>

</body>

</html>
