$(document).ready(function () {

    const table = document.getElementById('lastRequestDataTable');
    const data = JSON.parse(localStorage.getItem('lastRequestData'));

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

});