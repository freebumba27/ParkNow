<?php
	header('Access-Control-Allow-Origin: *');
	header('Access-Control-Allow-Methods: PUT, GET, POST, DELETE, OPTIONS');
	header('Access-Control-Allow-Headers: Content-Type');
	
	include_once 'db_con.php';
	if(isset($_REQUEST['name']) && $_REQUEST['name']!='' && isset($_REQUEST['email_id']) && $_REQUEST['email_id']!='' && isset($_REQUEST['mobile_no']) && $_REQUEST['mobile_no']!='' && isset($_REQUEST['username']) && $_REQUEST['username']!='' && isset($_REQUEST['password']) && $_REQUEST['password']!='')
	{
		$sql=mysql_query("SELECT `id` FROM `user_details` WHERE `username` = '".$_REQUEST['username']."'");
		if(mysql_num_rows($sql)>=1)
	    {
			echo"EXISTS";
	    }
		else
		{
			$sql = "INSERT IGNORE INTO user_details ( name, email_id, mobile_no, username, password ) VALUES ( '".$_REQUEST['name']."' ,  '".$_REQUEST['email_id']."' ,  '".$_REQUEST['mobile_no']."' ,  '".$_REQUEST['username']."' ,  '".$_REQUEST['password']."' );"; 	
	 
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