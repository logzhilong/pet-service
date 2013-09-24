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
		<form action="<%=request.getContextPath() %>/core/addstates" enctype="multipart/form-data" method="post">
			<table border="1">
				<tr>
					<td>
						<div>
							<tt>
								用户编号：<input type= "text" name = "id"/>
							</tt><tt>
								类型：<input type= "text" name = "type"/>
							</tt>
						</div>
						<div>
							<tt>
								&nbsp&nbsp&nbsp&nbsp经度：<input type= "text" name = "longitude"/>
							</tt><tt>	
								纬度：<input type= "text" name = "latitude"/>
							</tt>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div>
							消息：<textarea rows="3" cols="1" style="width:90%;height:expression((this.scrollHeight>150)?'150px':(this.scrollHeight+5)+'px');overflow:auto;" name="msg"></textarea>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div>
							<tt>
								小图片：<input type="file"  name="fileM" />
								大图片：<input type="file"  name="filemini" />
							</tt>
						</div>
					</td>
				</tr>
				<tr>
					<td align="right">
						<div>
							<tt>
								<input type="submit" value="提交" />
							</tt>
						</div>
					</td>
				</tr>
			</table>
		</form>
		<table id="result" width="820px" >
		</table>
	</div>
	
</body>
</html>