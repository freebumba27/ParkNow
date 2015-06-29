<?php
$con = mysql_connect("sql311.byethost16.com","b16_11436169","jana27");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }
mysql_select_db("b16_11436169_all_in_one") or die(mysql_error());

?>