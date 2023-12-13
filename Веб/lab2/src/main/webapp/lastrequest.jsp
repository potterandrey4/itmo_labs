<%--
  Created by IntelliJ IDEA.
  User: andrey
  Date: 13.12.2023
  Time: 01:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
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

            <div class="col-md-2"></div>

            <div class="col-md-8">
                <table class="table table-bordered" id="lastRequestDataTable">

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
<%--                    <jsp:useBean id="results" scope="session" class="beans.DataListBean"/>--%>
<%--                    <core:forEach var="result" items="${results.results}">--%>
<%--                        <tr>--%>
<%--                            <td>${result.x}</td>--%>
<%--                            <td>${result.y}</td>--%>
<%--                            <td>${result.r}</td>--%>
<%--                            <td>${result.isHit}</td>--%>
<%--                            <td>${result.executionTime}</td>--%>
<%--                            <td>${result.time}</td>--%>
<%--                        </tr>--%>
<%--                    </core:forEach>--%>
                    </tbody>
                </table>
            </div>

            <div class="col-md-2"></div>

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


<script src="JS/lastRequest.js"></script>

</body>

</html>
