<?php
	header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: PUT, GET, POST, DELETE, OPTIONS');
	header('Access-Control-Allow-Headers: Content-Type');
	
	include_once 'db_con.php';
	if(isset($_REQUEST['review']) && $_REQUEST['review']!='' && isset($_REQUEST['parking_spot_id']) && $_REQUEST['parking_spot_id']!='' && isset($_REQUEST['review_star']) && $_REQUEST['review_star']!='' && isset($_REQUEST['user_id']) && $_REQUEST['user_id']!='')
	{
		$sql=mysql_query("SELECT `user_id` FROM `user_review` WHERE `user_id` = '".$_REQUEST['user_id']."' AND `parking_spot_id` = '".$_REQUEST['parking_spot_id']."'");
		if(mysql_num_rows($sql)>=1)
	    {
			echo"EXISTS";
	    }
		else
		{
			$sql = "INSERT INTO user_review ( parking_spot_id, review, review_star, user_id) VALUES ( '".$_REQUEST['parking_spot_id']."' ,  '".$_REQUEST['review']."' , '".$_REQUEST['review_star']."' , '".$_REQUEST['user_id']."' );"; 	

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
	}
	else
	{	
		echo 'request without parameter.';
	}
?>