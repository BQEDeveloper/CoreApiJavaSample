<%@page import="business.ActivityManager"%>
<%@page import="models.ActivityModel"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create Activity</title>
<!-- Jquery -->
<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
<!-- Bootstrap JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
</head>
<body style="margin: 20px;">
	<%
		ActivityModel activity = new ActivityModel();
		ActivityManager activityManager = new ActivityManager();
		String id = request.getParameter("id");
		if(id != null)
			activity = activityManager.Get(id);		
	%>
	<a href="ActivityListView.jsp" class="col-sm-10 col-sm-offset-2">Back
		to List</a>
	<form class="form-horizontal" method="post" style="padding-top: 50px;"
		action="../indexServlet">
		<div class="form-group">
			<label class="control-label col-sm-2">Code: *</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" name="code"
					placeholder="Enter code" required
					value="<% out.print(activity.code == null ? "" : activity.code); %>">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2">Description: *</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" name="description"
					placeholder="Enter Description" required
					value="<% out.print(activity.description == null ? "" : activity.description); %>">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2">Bill Rate:</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" name="billRate"
					placeholder="Enter Bill Rate"
					value="<% out.print(activity.billRate == null ? "" : activity.billRate); %>">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2">Cost Rate:</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" name="costRate"
					placeholder="Enter Cost Rate"
					value="<% out.print(activity.costRate == null ? "" : activity.costRate); %>">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-6">
				<div class="checkbox">
					<label><input type="checkbox" name="isBillable" <% 
						out.print(activity.billable != null && activity.billable == true ? "checked" : ""); %> > Billable</label>
				</div>
			</div>
		</div>
		<input type="hidden" name="id" id="id" value="<% out.print(activity.id != null ? activity.id : ""); %>"> 
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-6">
				<button type="submit" class="btn btn-success" name="btnSubmitActivity" id="btnSubmitActivity" value="Submit Activity">Submit</button>
			</div>
		</div>
	</form>
</body>
</html>