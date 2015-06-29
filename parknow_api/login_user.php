<?php
	header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: PUT, GET, POST, DELETE, OPTIONS');
	header('Access-Control-Allow-Headers: Content-Type');
	
	include_once 'db_con.php';
	if(isset($_REQUEST['username']) && $_REQUEST['username']!='' && isset($_REQUEST['password']) && $_REQUEST['password']!='')
	{
		$sql = "SELECT id FROM user_details WHERE username ='".$_REQUEST['username']."' AND password ='".$_REQUEST['password']."';";
		
		$res = mysql_query($sql);
	
		if (mysql_num_rows($res)==0)
		{
			echo 'NO';
		}
		else
		{
			while ($row = mysql_fetch_assoc($res)) 
			{
				echo 'user_id'.$row['id'];
			}
		}
	}
	else
	{	
		echo 'request without parameter.';
	}
?>