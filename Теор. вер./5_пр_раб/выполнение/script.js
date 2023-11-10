let submitBtn = document.getElementById("submitBtn");
let arr;

submitBtn.addEventListener("click", function() {
	let inputValues = document.querySelector('#inputValues').value;
    arr = inputValues.split(' ').map(function(item) {
        return parseFloat(item);
    });

	task_1();
	task_2();
});


// вариационный ряд
function task_1() {
	arr.sort(function(a, b) {
        return a - b;
    });

    let sortedString = arr.join(' ');

	const res_task_1 = document.getElementById("res_task_1");
	res_task_1.textContent = sortedString;

}


// экстремальные значение и размах
function task_2() {
	const res_max_value = document.querySelector('#max');
	const res_min_value = document.querySelector('#min');
	const res_scope = document.querySelector('#scope');

	if (arr.length === 0) {
        return { max: undefined, min: undefined };
    }

    // Инициализация переменных для максимума и минимума
    let max = arr[0];
    let min = arr[0];

    // Проходим по массиву и обновляем максимум и минимум при необходимости
    for (let i = 1; i < arr.length; i++) {
        if (arr[i] > max) {
            max = arr[i];
        } else if (arr[i] < min) {
            min = arr[i];
        }
    }

    res_max_value.textContent = max;
    res_min_value.textContent = min;
    res_scope.textContent = Math.abs(max-min).toFixed(2);


// оценка математического ожидания
function calculateProbabilities(values) {
    var totalValues = values.length;

    // Вычисляем равномерные вероятности для каждого значения
    var probabilities = values.reduce(function (acc, value) {
        acc[value] = (acc[value] || 0) + 1 / totalValues;
        return acc;
    }, 
    {});


	console.log('Значения:', values);
	console.log('Вероятности:', probabilities);
}

}