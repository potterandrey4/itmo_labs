const errorMsg = document.getElementById("form-for-msg");

const minY = parseFloat("-3"), maxY = parseFloat("3");

let dots = [];

function validateY(yNum) {
    let floatValue = parseFloat(yNum.replace(',', '.')); // Replace ',' with '.' for consistent parsing
    return !isNaN(floatValue) && minY <= floatValue && floatValue <= maxY;
}


function validateForm(y) {
    const errorMessage = document.getElementById("error-message");
    const errorAudio = document.getElementById("error-audio");

    if (!validateY(y)) {
        errorMessage.textContent = "Введёные данные некорректны";
        errorAudio.play();
        return false;
    } else {
        errorMessage.textContent = '';
        errorAudio.pause();
        errorAudio.currentTime = 0;
        return true;
    }
}

$(document).ready(function () {
    restoreDataFromLocalStorage();
    drawDots(dots, JSON.parse(localStorage.getItem("formData")).r);
    getData('getAll');

    let submitBtn = document.getElementById("submitBtn");

    submitBtn.addEventListener("click", function () {

        let xCheckboxes = document.getElementsByName("x");
        let selectedXValues = [];
        for (let i = 0; i < xCheckboxes.length; i++) {
            if (xCheckboxes[i].checked) {
                selectedXValues.push(xCheckboxes[i].value);
            }
        }

        let rRadioButton = document.getElementsByName("r");
        let selectedR;
        for (let i = 0; i < rRadioButton.length; i++) {
            if (rRadioButton[i].checked) {
                selectedR = rRadioButton[i].value;
                break;
            }
        }

        let formData = {
            x: selectedXValues,
            y: ($("#y").val()),
            r: selectedR
        };

        if (!validateForm(formData.y)) {
            return;
        }

        errorMsg.textContent = "";

        drawGraph(formData.r);
        for (let i = 0; i < formData.x.length; i++) {
            dots.push({x: formData.x[i], y: formData.y});
        }
        drawDots(dots, formData.r);

        saveDataToLocalStorage();

        fetch("controller", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: new URLSearchParams({
                "x": formData.x.join(';'),
                "y": formData.y,
                "r": formData.r
            })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Server responded with bad getaway status: ${response.status} ${response.text()}`);
                }
                return response.json();
            })
            .then(function (serverAnswer) {
                insertData(serverAnswer);

                setLastData(serverAnswer);
                // window.location.href = "lastRequest";
            })
            .catch(error => {
                console.log(`При обработке вашего запроса произошла ошибка: ${error.message} ${error.body}`);
            });

    });


    // Восстанавливаем данные из localStorage
    function restoreDataFromLocalStorage() {
        dots = JSON.parse(localStorage.getItem('dots')) || [];

        let formData = JSON.parse(localStorage.getItem("formData"));
        if (formData) {
            if (Array.isArray(formData.x)) {
                formData.x.forEach(function (xValue) {
                    $("input[name='x'][value='" + xValue + "']").prop("checked", true);
                });
            } else if (typeof formData.x === 'string') {
                // Преобразуйте строку в массив
                formData.x = JSON.parse(formData.x);
                formData.x.forEach(function (xValue) {
                    $("input[name='x'][value='" + xValue + "']").prop("checked", true);
                });
            } else {
                // Обработайте случай, если formData.x не является массивом или строкой JSON (по желанию)
                console.error("formData.x не является массивом или строкой JSON:", formData.x);
            }
            $("#y").val(formData.y);
            $("input[name='r'][value='" + formData.r + "']").prop("checked", true);
            drawGraph(formData.r);
        }
    }

    let yInput = document.getElementById("y");

    // Добавляем обработчик события keydown
    yInput.addEventListener("keydown", function (event) {
        // Проверяем, является ли нажатая клавиша Enter (код клавиши 13)
        if (event.keyCode === 13) {
            // Предотвращаем стандартное поведение формы (отправку)
            event.preventDefault();
        }
    });
});

// Сохраняем данные в localStorage
function saveDataToLocalStorage() {
    localStorage.setItem('dots', JSON.stringify(dots));
    saveFormData();
}

function insertData(data) {
    const table = document.getElementById('resultTable');

    while (table.rows.length > 1) {
        table.deleteRow(1);
    }

    for (let i = 0; i < data.results.length; i++) {
        const result = data.results[i];

        const newRow = table.insertRow();
        const cell1 = newRow.insertCell(0);
        const cell2 = newRow.insertCell(1);
        const cell3 = newRow.insertCell(2);
        const cell4 = newRow.insertCell(3);
        const cell5 = newRow.insertCell(4);
        const cell6 = newRow.insertCell(5);

        cell1.innerHTML = result.x;
        cell2.innerHTML = result.y.toString();
        cell3.innerHTML = result.r;
        cell4.innerHTML = result.isHit;
        cell5.innerHTML = result.executionTime;
        cell6.innerHTML = result.time;
    }
}


const canvas = document.getElementById(`canvas`);
canvas.addEventListener("click", function (event) {
    let x = event.clientX - canvas.getBoundingClientRect().left;
    let y = event.clientY - canvas.getBoundingClientRect().top;

    let rRadioButtons = document.getElementsByName("r");
    let r;
    for (let i = 0; i < rRadioButtons.length; i++) {
        if (rRadioButtons[i].checked) {
            r = rRadioButtons[i].value;
            break;
        }
    }
    if (r === undefined) {
        errorMsg.textContent = "Невозможно определить координаты точки без радиуса";
        return;
    } else {
        errorMsg.textContent = "";
    }

    let graphX = (x - canvas.width / 2) / 40;
    let graphY = (canvas.height / 2 - y) / 40;

    dots.push({x: graphX, y: graphY});
    drawDots(dots, r);
    saveDataToLocalStorage();

    if (r !== null) {

        fetch("controller", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: new URLSearchParams({
                "x": graphX,
                "y": graphY,
                "r": r
            })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Server responded with bad getaway status: ${response.status} ${response.text()}`);
                }
                return response.json();
            })
            .then(function (serverAnswer) {
                insertData(serverAnswer);

                setLastData(serverAnswer);
                // window.location.href = "lastRequest";
            })
            .catch(error => {
                console.log(`При обработке вашего запроса произошла ошибка: ${error.message} ${error.body}`);
            });
    } else {
        console.log(`поле r пустое, заполни его`);
    }
});

function setLastData(serverAnswer) {
    if (serverAnswer.results && serverAnswer.results.length > 0) {
        // Сортируем массив результатов по свойству 'time' в порядке возрастания
        serverAnswer.results.sort((a, b) => {
            return new Date(a.time) - new Date(b.time);
        });

        // Берем все элементы с одинаковым временем
        let lastResults = serverAnswer.results.filter(result => result.time === serverAnswer.results[serverAnswer.results.length - 1].time);

        let lastRequestData = {
            results: lastResults
            // Добавьте другую нужную информацию, если необходимо
        };

        let jsonResult = JSON.stringify(lastRequestData);
        console.log(jsonResult);

        localStorage.removeItem('lastRequestData');
        localStorage.setItem('lastRequestData', JSON.stringify(lastRequestData));
    }
}

function handleRadioButtonChange(radioButton) {
    let selectedR = radioButton.value;
    drawGraph(selectedR);
    drawDots(dots, selectedR);
}

function saveFormData() {
    localStorage.removeItem("formData")
    let xValues = [];
    $("input[name='x']:checked").each(function () {
        xValues.push($(this).val());
    });

    let formData = {
        x: xValues,
        y: $("#y").val(),
        r: $("input[name='r']:checked").val()
    };

    localStorage.setItem("formData", JSON.stringify(formData));
}

document.getElementById("clearDotsBtn").addEventListener("click", function () {
    dots.length = 0; // Очистка массива
    localStorage.removeItem('dots'); // Удаление данных из localStorage по ключу 'dots'
    drawGraph(JSON.parse(localStorage.getItem("formData")).r);
    drawDots(dots, 0);
});

// Вызов функции сохранения данных при изменении формы
$("input[name='x'], #y, input[name='r']").change(function () {
    saveFormData();
});


function getData(clearOrGetAll) {

    fetch("controller", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
        },
        body: new URLSearchParams({
            "load&clear": clearOrGetAll
        })
    })
        .then(response => {
            if (!response.ok) {
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
}