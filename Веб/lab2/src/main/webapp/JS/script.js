const minY = -5, maxY = 5;
let r;

let dots = [];

function isNumeric(val) {
    return !isNaN(parseFloat(val)) && isFinite(val);
}

function isInt(val){
    return /^-?[0-9]{1,10}$/.test(val);
}

function isFloat(val){
    return /^-?[0-9]{0,6}(.|,)[0-9]{0,15}$/.test(val);
}

function validateY() {
    let yField = $('#y');
    let yNum = yField.val().replace(',', '.');
    if (isNumeric(yNum) && isFloat(yNum) && minY <= yNum && yNum <= maxY) {
        return true;
    }
    return false;
}


function validateForm(){
    const errorMessage = document.getElementById("error-message");
    const errorAudio = document.getElementById("error-audio");

    if(!validateY()){
        errorMessage.textContent = "Введёные данные некорректны";
        errorAudio.play();
        return false;
    }
    else{
        errorMessage.textContent = '';
        errorAudio.pause();
        errorAudio.currentTime = 0;
        return true;
    }
}


$(document).ready(function() {
    restoreDataFromLocalStorage();
    let submitBtn = document.getElementById("submitBtn");

    submitBtn.addEventListener("click", function() {
        if (!validateForm()) {
            return;
        }

        let formData = {
            x: $("#x").val(),
            y: $("#y").val(),
            r: $("#r").val()
        };

        dots.push( {x: formData.x, y: formData.y} );

        drawGraph(formData.r);
        drawDots(dots);

        saveDataToLocalStorage();

        fetch("controller", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: new URLSearchParams({
                "x": formData.x,
                "y": formData.y,
                "r": formData.r
            })
        })
            .then(response => {
                if (!response.ok) {
                    console.log("зашёл в ошибку");
                    throw new Error(`Server responded with bad getaway status: ${response.status} ${response.text()}`);
                }
                return response.json();
            })
            .then(function (serverAnswer) {
                insertData(serverAnswer);
            })
            .catch(error => {
                console.log(`При обработке вашего запроса произошла ошибка: ${error.message} ${error.body}`);
            });

    });


    // Сохраняем данные в localStorage
    function saveDataToLocalStorage() {
        let tableData = [];

        $("#resultTable tbody tr").each(function() {
            let rowData = {
                x: $(this).find("td:eq(0)").text(),
                y: $(this).find("td:eq(1)").text(),
                r: $(this).find("td:eq(2)").text(),
                result: $(this).find("td:eq(3)").text(),
                executionTime: $(this).find("td:eq(4)").text(),
                currentTime: $(this).find("td:eq(5)").text()
            };
            tableData.push(rowData);
        });

        localStorage.setItem("tableData", JSON.stringify(tableData));

        let formData = {
            x: $("#x").val(),
            y: $("#y").val(),
            r: $("#r").val()
        };

        localStorage.setItem("formData", JSON.stringify(formData));

        localStorage.setItem('dots', JSON.stringify(dots));

    }


    // Восстанавливаем данные из localStorage
    function restoreDataFromLocalStorage() {
        let storedData = localStorage.getItem("tableData");
        dots = JSON.parse(localStorage.getItem('dots')) || [];

        if (storedData) {
            let tableData = JSON.parse(storedData);

            $("#resultTable tbody").empty();

            $.each(tableData, function(index, rowData) {
                $("#resultTable tbody").append(
                    "<tr><td>" + rowData.x + "</td><td>" + rowData.y + "</td><td>" + rowData.r + "</td><td>" +
                    rowData.result + "</td><td>" + rowData.executionTime + "</td><td>" + rowData.currentTime + "</td></tr>"
                );
            });
        }

        let formData = JSON.parse(localStorage.getItem("formData"));

        if (formData) {
            $("#x").val(formData.x);
            $("#y").val(formData.y);
            $("#r").val(formData.r);
        }
    }


    $("#clearTable").click(function() {
        localStorage.removeItem("tableData");
        $("#resultTable tbody").empty();
    });





let yInput = document.getElementById("y");

    // Добавляем обработчик события keydown
    yInput.addEventListener("keydown", function(event) {
        // Проверяем, является ли нажатая клавиша Enter (код клавиши 13)
        if (event.keyCode === 13) {
            // Предотвращаем стандартное поведение формы (отправку)
            event.preventDefault();
        }
    });
});


function insertData(data) {
    const results = data.results;

    // Очищаем таблицу перед вставкой новых данных
    $("#resultTable tbody").empty();

    // Проходим по массиву и вставляем данные в таблицу
    results.forEach(result => {
        const row = `
            <tr>
                <td>${result.x}</td>
                <td>${result.y}</td>
                <td>${result.r}</td>
                <td>${result.isHit}</td>
                <td>${result.executionTime} мс</td>
                <td>${result.time}</td>
            </tr>`;
        $("#resultTable tbody").append(row);
    });
}