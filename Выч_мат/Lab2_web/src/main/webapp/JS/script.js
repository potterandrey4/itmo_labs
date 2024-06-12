document.getElementById('choiceMode1').addEventListener('click', function () {
    window.location.href = 'http://localhost:8080/equations.jsp';
});
document.getElementById('choiceMode2').addEventListener('click', function () {
    window.location.href = 'http://localhost:8080/systemEquations.jsp';
});

window.addEventListener('DOMContentLoaded', (event) => {
    MathJax.typesetPromise();

    const equations = document.querySelectorAll('.equation');
    equations.forEach((equation) => {
        equation.innerHTML = '$$' + equation.innerHTML + '$$';
    });
});


function handleSubmitEq() {
    let choiceRadioBtn = document.getElementsByName("choiceEquation");
    let selectedChoiceRadioBtn;
    for (let i = 0; i < choiceRadioBtn.length; i++) {
        if (choiceRadioBtn[i].checked) {
            selectedChoiceRadioBtn = choiceRadioBtn[i].value;
            break;
        }
    }

    let formData = {
        choiceEquation: selectedChoiceRadioBtn,
        eps: ($("#eps").val()),
        a: ($("#a").val()),
        b: ($("#b").val())
    };

    fetch("/lab2/controller", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
        },
        body: new URLSearchParams({
            "choiceEquation": formData.choiceEquation,
            "eps": formData.eps,
            "a": formData.a,
            "b": formData.b
        })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Server responded with bad status: ${response.status} ${response.statusText}`);
            }
            return response.json();
        })
        .then(function(serverAnswer) {
            console.log(serverAnswer.toString());
        })
        .catch(error => {
            console.log(`При обработке вашего запроса произошла ошибка: ${error.message}`);
        });
}


