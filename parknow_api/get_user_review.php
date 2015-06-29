<?php
	header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: PUT, GET, POST, DELETE, OPTIONS');
	header('Access-Control-Allow-Headers: Content-Type');
	
	include_once 'db_con.php';
	if(isset($_REQUEST['parkingSpotId']) && $_REQUEST['parkingSpotId']!='')
	{
		$sql = "SELECT user_review.review_star, user_review.review, user_details.username FROM user_review INNER JOIN user_details ON user_review.user_id  = user_details.id AND user_review.parking_spot_id = '".$_REQUEST['parkingSpotId']."';";
		
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