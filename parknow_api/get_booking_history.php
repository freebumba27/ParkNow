<?php
	header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: PUT, GET, POST, DELETE, OPTIONS');
	header('Access-Control-Allow-Headers: Content-Type');
	
	include_once 'db_con.php';
	if(isset($_REQUEST['purchaser_user_id']) && $_REQUEST['purchaser_user_id']!='')
	{
		$sql = "SELECT order_details.id, order_details.parking_spot_id, parking_location_details.address, order_details.spot_booking_from, order_details.spot_booking_upto, order_details.price FROM parking_location_details INNER JOIN order_details ON order_details.parking_spot_id=parking_location_details.id AND order_details.purchaser_user_id = '".$_REQUEST['purchaser_user_id']."';";
		
		$res = mysql_query($sql);
	
		if (mysql_num_rows($res)==0)
		{
			echo 'NO DATA';
		}
		else
		{
			$d = array();
			while ($row = mysql_fetch_assoc($res)) 
			{
				$d[] = $row;
			}
			echo $json = json_encode($d);
		}
	}
	else
	{	
		echo 'request without parameter.';
	}
?>