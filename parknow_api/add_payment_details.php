<?php
	header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: PUT, GET, POST, DELETE, OPTIONS');
	header('Access-Control-Allow-Headers: Content-Type');
	
	include_once 'db_con.php';
	if(isset($_REQUEST['parking_spot_id']) && $_REQUEST['parking_spot_id']!='' && isset($_REQUEST['purchaser_user_id']) && $_REQUEST['purchaser_user_id']!='' && isset($_REQUEST['paypal_payment_id']) && $_REQUEST['paypal_payment_id']!='' && isset($_REQUEST['spot_booking_from']) && $_REQUEST['spot_booking_from']!='' && isset($_REQUEST['spot_booking_upto']) && $_REQUEST['spot_booking_upto']!='' && isset($_REQUEST['price']) && $_REQUEST['price']!='')
	{
		$sql = "INSERT INTO order_details ( parking_spot_id, purchaser_user_id, paypal_payment_id, spot_booking_from, spot_booking_upto, price ) VALUES ( '".$_REQUEST['parking_spot_id']."' ,  '".$_REQUEST['purchaser_user_id']."' ,  '".$_REQUEST['paypal_payment_id']."' ,  '".$_REQUEST['spot_booking_from']."' ,  '".$_REQUEST['spot_booking_upto']."' ,  '".$_REQUEST['price']."' );"; 	
 
		$res = mysql_query($sql);
		if($res)
		{
			echo 'YES';
		}
		else
		{
			echo 'NO';
		}
	}
	else
	{	
		echo 'request without parameter.';
	}
?>