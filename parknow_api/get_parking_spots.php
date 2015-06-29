<?php
	header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: PUT, GET, POST, DELETE, OPTIONS');
	header('Access-Control-Allow-Headers: Content-Type');
	
	include_once 'db_con.php';
	if(isset($_REQUEST['lat']) && $_REQUEST['lat']!='' && isset($_REQUEST['lng']) && $_REQUEST['lng']!='')
	{
		$sql = "SELECT id, user_id, address, spaceDesc, price, lat, lng FROM parking_location_details WHERE lat = '".$_REQUEST['lat']."' and lng = '".$_REQUEST['lng']."';";
		
		$res = mysql_query($sql);
	
		if (mysql_num_rows($res)==0)
		{
			echo 'NO';
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
		$sql = "SELECT id, user_id, address, spaceDesc, price, lat, lng FROM parking_location_details;";
		
		$res = mysql_query($sql);
	
		if (mysql_num_rows($res)==0)
		{
			echo 'NO';
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
?>