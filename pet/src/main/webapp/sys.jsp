<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>接口测试</title>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.ztree.all-3.2.min.js"></script>
</head>
<body>
	<div>
		<form action="<%=request.getContextPath() %>/client/upload" enctype="multipart/form-data" method="post">
			<input type="file"  name="file" />
			<input type="submit" value="图片提交" />
		</form>
		<table id="result" width="820px" >
		</table>
	</div>
	
	<div>
		<form action="<%=request.getContextPath() %>/core/uploadOpenPic" enctype="multipart/form-data" method="post">
			<input type="file"  name="file" />
			<input type="submit" value="开机图片提交（原始版本）" />
		</form>
		<table id="result" width="820px" >
		</table>
	</div>
	
	<div>
		<form action="<%=request.getContextPath() %>/core/androidVersion" enctype="multipart/form-data" method="post">
			<input type="file"  name="file" />
			<input type="submit" value="android版本提交" />
		</form>
		<table id="result" width="820px" >
		</table>
	</div>
	
	<div>
		<form action="<%=request.getContextPath() %>/core/iosVersion" enctype="multipart/form-data" method="post">
			版本号：<input type="text"  name="iosversion" />
			<input type="submit" value="ios版本号提交" />
		</form>
		<table id="result" width="820px" >
		</table>
	</div>
	
	<div>
		<form action="<%=request.getContextPath() %>/core/getPicId" enctype="multipart/form-data" method="post">
			<input type="submit" value="获取图片编号" />
		</form>
		<table id="result" width="820px" >
		</table>
	</div>
	
	<div>
		<form action="<%=request.getContextPath() %>/core/uploadPic" enctype="multipart/form-data" method="post">
			<input type="file"  name="file" />
			图片编号：<input type="text" name="picId" />
			<input type="submit" value="开机图片提交" />
		</form>
		<table id="result" width="820px" >
		</table>
	</div>
	
</body>
</html>