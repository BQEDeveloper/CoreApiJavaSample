<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="models.ActivityModel"%>
<%@page import="business.ActivityManager"%>
<%@page import="models.UserInfoModel"%>
<%@page import="business.UserInfoManager"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Activities List</title>
<!-- Jquery -->
<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
<!-- Bootstrap JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script>
	function loadActivity(id) {
		window.open("CreateActivityView.jsp?id=" + encodeURI(id), "_self");
	}
	function deleteActivity(id) {
		window.open("../indexServlet?id=" + encodeURI(id) + "&btnDeleteActivity=DeleteActivity", "_self");
	}
</script>
</head>
<body style="margin: 20px;">
	<h2 style="text-align: center">Core API - Java Sample</h2>
	<h4 style="text-align: center" title="Company">
		<%
			try {
				UserInfoManager userInfoManager = new UserInfoManager();
				//UserInfoModel userInfo = userInfoManager.GetUserInfo();		
				//out.print(userInfo.company);
			} catch (Exception ex) {
				out.print("<div style='color:red'>" + ex.getMessage() + "</div>");
			}
		%>
	</h4>
	<!-- Disconnect from Core -->
	<div style="text-align: center">
		<form method="post" action="../indexServlet">
			<input type="submit" class="btn btn-danger"
				name="btnDisconnectFromCore" id="btnDisconnectFromCore"
				value="Disconnect from Core" />
		</form>
	</div>
	<div style="text-align: right">
		<a href="CreateActivityView.jsp" class="btn btn-primary" role="button">Create
			Activity</a>
	</div>
	<h3>Activities List</h3>
	<%
		try {
			ActivityManager activityManager = new ActivityManager();
			List<ActivityModel> activityList = activityManager.GetList();
			out.print("<table style=\"margin:20 0 20 0\" class=\"table table-striped\">"
					+ "<thead style=\"background: #000; color: #fff\">" + "<th>Code</th>" + "<th>Description</th>"
					+ "<th>Billable</th>" + "<th>Bill Rate</th>" + "<th>Cost Rate</th>" + "<th></th>" + "</thead>");
			for (int count = 0; count < activityList.size(); count++) {
				ActivityModel activity = (ActivityModel) activityList.toArray()[count];
				out.print("<tr style=\"cursor:pointer\">" + "<td onclick=loadActivity('" + activity.id + "')>"
						+ activity.code + "</td>" + "<td onclick=loadActivity('" + activity.id + "')>"
						+ activity.description + "</td>" + "<td onclick=loadActivity('" + activity.id + "')>"
						+ (activity.billable == true ? "true" : "false") + "</td>" + "<td onclick=loadActivity('"
						+ activity.id + "')>" + (activity.billRate != null ? activity.billRate : "")  + "</td>" + "<td onclick=loadActivity('"
						+ activity.id + "')>" + (activity.costRate != null ? activity.costRate : "") + "</td>" + "<td onclick=deleteActivity('" + activity.id + "')>Delete</td>" + "</tr>");
			}

		} catch (Exception ex) {
			out.print("<div style='color:red'>" + ex.getMessage() + "</div>");
		}
	%>
</body>
</html>