<?php
header("Content-Type: application/json");

require 'init.php';
$sql = "select * from contacts";
$result = mysqli_query($con,$sql);
//$row = mysqli_fetch_all($result);

//$rows = array();
while ($row = mysqli_fetch_assoc($result)) {
    $rows [] = $row;
}

echo json_encode($rows);
