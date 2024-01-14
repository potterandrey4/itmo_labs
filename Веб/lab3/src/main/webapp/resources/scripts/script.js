let dots = [];

let xval = 0;

function changeXValue(x) {
    xval = x;
}

let yval = 0;

function changeYValue(y) {
    yval = y;
}

$(document).ready(function () {
    restoreDataFromLocalStorage();
    drawDots(dots, JSON.parse(localStorage.getItem("formData")).r);

    const canvas = document.querySelector('#canvas');

    canvas.addEventListener("click", function (event) {
        let r = document.getElementById('data-form:r').value;
        if (!r) {
            alert("Невозможно определить координаты точки без радиуса")
            return;
        }

        let x = event.clientX - canvas.getBoundingClientRect().left;
        let y = event.clientY - canvas.getBoundingClientRect().top;

        let graphX = (x - canvas.width / 2) / 40;
        let graphY = (canvas.height / 2 - y) / 40;


        dots.push({x: graphX, y: graphY});
        drawDots(dots, r);
        saveDataToLocalStorage();

        document.getElementById('data-form:hiddenX').value = graphX;
        document.getElementById('data-form:hiddenY').value = graphY;
        $('#hiddenAutoFill').val(true);
        $('.submit-button').click();

    });


    // Восстанавливаем данные из localStorage
    function restoreDataFromLocalStorage() {
        dots = JSON.parse(localStorage.getItem('dots')) || [];

        let formData = JSON.parse(localStorage.getItem("formData"));

        let form = document.getElementById('data-form');

        let storedData = localStorage.getItem('formData');
        if (storedData) {
            let formData = JSON.parse(storedData);
            let elements = form.elements;

            for (let i = 0; i < elements.length; i++) {
                let element = elements[i];
                let id = element.id;

                if (!id) {
                    continue;
                }

                if (formData.hasOwnProperty(id)) {
                    element.value = formData[id];
                }
            }
        }
        drawGraph(formData.r);
    }

    //
    // function updateHiddenFields() {
    //     let x = document.getElementById('data-form:x').value;
    //     let y = document.getElementById('data-form:y').value;
    //     let r = document.getElementById('data-form:r').value;
    //
    //     document.getElementById('data-form:hiddenX').value = x;
    //     document.getElementById('data-form:hiddenY').value = y;
    //     document.getElementById('data-form:hiddenR').value = r;
    // }
    //
    // document.getElementById('data-form:x').addEventListener('change', updateHiddenFields);
    // document.getElementById('data-form:y').addEventListener('change', updateHiddenFields);
    // document.getElementById('data-form:r').addEventListener('change', updateHiddenFields);
});

// Сохраняем данные в localStorage
function saveDataToLocalStorage() {
    localStorage.setItem('dots', JSON.stringify(dots));
    saveFormData();
}

function handleRSelection() {
    let selectElement = document.getElementById('data-form:r');
    let selectedR = selectElement.options[selectElement.selectedIndex].value;
    drawGraph(selectedR);
    drawDots(dots, selectedR);
}


function saveFormData() {
    localStorage.removeItem("formData");

    let elementX = document.getElementById('data-form:x');
    let x = elementX.options[elementX.selectedIndex].value;

    let elementY = document.getElementById('data-form:y');
    let y = elementY.value;

    let elementR = document.getElementById('data-form:r');
    let r = elementR.options[elementR.selectedIndex].value;


    let formData = {
        x: x,
        y: y,
        r: r
    };

    localStorage.setItem("formData", JSON.stringify(formData));
}

// document.getElementById("clearDotsBtn").addEventListener("click", function () {
//     dots.length = 0; // Очистка массива
//     localStorage.removeItem('dots'); // Удаление данных из localStorage по ключу 'dots'
//     drawGraph(JSON.parse(localStorage.getItem("formData")).r);
//     drawDots(dots, 0);
// });

// Вызов функции сохранения данных при изменении формы
$("input[name='x'], #y, input[name='r']").change(function () {
    saveFormData();
});
