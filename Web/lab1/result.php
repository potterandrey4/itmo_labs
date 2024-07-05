<?php
// Получаем данные из POST-запроса
$x = htmlspecialchars($_POST['x']);
$y = htmlspecialchars($_POST['y']);
$r = htmlspecialchars($_POST['r']);

$rowCount = 0;

// валидация на случай подмены данных
function xval($x){
    if( $x == '' ) {
        return false;
    }
    if( !is_numeric($x) ) {
        return false;
    }
    if( !in_array($x, array(-4, -3, -2, -1, 0, 1, 2, 3, 4)) ) {
        return false;
    }

    return true;
}

function yval($y){
    if($y == ''){
        return false;
    }
    if(!is_numeric($y)){
        return false;
    }
    $floaty = floatval($y);
    return $floaty >= -5 && $floaty <= 5;
}

function rval($r){
    if($r == ''){
        return false;
    }
    if(!is_numeric($r)){
        return false;
    }
    if(!in_array($r,array(1, 2, 3, 4, 5))){
        return false;
    }

    return true;
}


if(xval($x) and rval($r) and yval($y)){
    $startTime = microtime(true);

    $result = "";

    if ($x >= 0 && $y >= 0 && $x <= $r/2 && $y <= $r) {
        $result = "+";
    } elseif ($x >= 0 && $y <= 0 && ($y >= $x - $r/2)) {
        $result = "+";
    } elseif ($x <= 0 && $y <= 0 && (($r^2)/4 >= ($x * $x + $y * $y))) {
        $result = "+";
    } else {
        $result = "-";
    }

    $executionTime = (microtime(true) - $startTime) * 1000;


    date_default_timezone_set('Europe/Moscow');

    $rowCount++;

    echo '<tr class="text">
      <td class="text">' . $x . '</td>
      <td class="text">' . $y . '</td>
      <td class="text">' . $r . '</td>
      <td class="text">'. $result . '</td>
      <td class="text">' . number_format($executionTime, 3) . ' с </td>
      <td class="text">' .date("H:i:s",time()) . '</td>
      </tr>';
}
else{
    http_response_code(400);
    echo '<h5>Data is invalid</h5>';
}
?>
