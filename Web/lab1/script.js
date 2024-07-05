const minY = -5, maxY = 5;
let r;

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
        y = yNum;
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

    var form = document.getElementById("data-form");
    var submitBtn = document.getElementById("submitBtn");

    submitBtn.addEventListener("click", function() {
        if (!validateForm()) {
            return;
        }

        var formData = {
            x: $("#x").val(),
            y: $("#y").val(),
            r: $("#r").val()
        };

        $.ajax({
            type: "POST",
            url: "result.php",
            data: formData,
            success: function(response) {
                $("#resultTable tbody").append(response);

                saveDataToLocalStorage();
            },
            error: function(xhr, status, error) {
                console.error(xhr.responseText);
            }
        });
    });


    // Сохраняем данные в localStorage
    function saveDataToLocalStorage() {
        var tableData = [];

        $("#resultTable tbody tr").each(function() {
            var rowData = {
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

        var formData = {
            x: $("#x").val(),
            y: $("#y").val(),
            r: $("#r").val()
        };

        localStorage.setItem("formData", JSON.stringify(formData));
    }


    // Восстанавливаем данные из localStorage
    function restoreDataFromLocalStorage() {
        var storedData = localStorage.getItem("tableData");

        if (storedData) {
            var tableData = JSON.parse(storedData);

            $("#resultTable tbody").empty();

            $.each(tableData, function(index, rowData) {
                $("#resultTable tbody").append(
                    "<tr><td>" + rowData.x + "</td><td>" + rowData.y + "</td><td>" + rowData.r + "</td><td>" +
                    rowData.result + "</td><td>" + rowData.executionTime + "</td><td>" + rowData.currentTime + "</td></tr>"
                );
            });
        }

        var formData = JSON.parse(localStorage.getItem("formData"));

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





var yInput = document.getElementById("y");

    // Добавляем обработчик события keydown
    yInput.addEventListener("keydown", function(event) {
        // Проверяем, является ли нажатая клавиша Enter (код клавиши 13)
        if (event.keyCode === 13) {
            // Предотвращаем стандартное поведение формы (отправку)
            event.preventDefault();
        }
    });
});