
<html>
<head>
	<title>Hello</title>
	</head>
	<body bgcolor="white">
	<img src="<%=request.getAttribute("img") %>">
	<h2>My name is Duke. What is yours?</h2>

	<form method="get">
		<input type="text" name="userName" size="25">
		<input type="submit" value="Submit"> 
		<input type="reset" value="Reset">
	</form>
	
	<%
		String userName = request.getParameter("userName");
		if ( userName!=null && !"".equals(userName))
		{
	%>
		<%@include file="response.jsp"%>
	<%
		}
	%>
</body>
</html>