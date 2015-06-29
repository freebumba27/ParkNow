<?php
	header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: PUT, GET, POST, DELETE, OPTIONS');
	header('Access-Control-Allow-Headers: Content-Type');
	
	include_once 'db_con.php';
	if(isset($_REQUEST['address']) && $_REQUEST['address']!='' && isset($_REQUEST['spaceDesc']) && $_REQUEST['spaceDesc']!='' && isset($_REQUEST['price']) && $_REQUEST['price']!='' && isset($_REQUEST['lat']) && $_REQUEST['lat']!='' && isset($_REQUEST['lng']) && $_REQUEST['lng']!='' && isset($_REQUEST['user_id']) && $_REQUEST['user_id']!='')
	{
		$sql = "INSERT INTO parking_location_details ( address, spaceDesc, price, lat, lng, user_id ) VALUES ( '".$_REQUEST['address']."' ,  '".$_REQUEST['spaceDesc']."' ,  '".$_REQUEST['price']."' ,  '".$_REQUEST['lat']."' ,  '".$_REQUEST['lng']."' ,  '".$_REQUEST['user_id']."' );"; 	
 
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