<%@page import="business.JWTManager"%>
<%@page import="shared.GeneralMethods"%>
<%@page import="models.JWTModel"%>
<%@page import="models.AuthResponseModel"%>
<%@page import="models.ConfigModel"%>
<%@page import="business.AuthManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%! int fontSize; %> 
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Core API - Java Sample</title>
	<meta name="viewport" content="width=device-width, initial-scale=1" />
    <!-- Jquery -->
    <script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
    <!-- Bootstrap JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
</head>
<body style="margin: 20px;">
	<h2 style="text-align:center">Core API - Java Sample</h2>
	<!-- //Connect To Core -->
	<div style="text-align:center;padding-top: 20px;">
    	<form action="indexServlet" method="post">
      		<input type="submit" class="btn btn-primary" name="btnConnectToCore" id="btnConnectToCore" value="Connect to Core API" />
    	</form>
	</div>
	<%
		try {
			AuthManager authManager = new AuthManager();
			AuthResponseModel authResponse = new AuthResponseModel();
			JWTModel jwt = new JWTModel();
			
			ConfigModel configs = GeneralMethods.GetConfig();
			
			//Authenticate (Code Exchange)
			if(request.getParameter("code") != null) {
				//verfiy that the state parameter returned by the server is the same that was sent earlier.
				if(authManager.IsValidState(request.getParameter("state"), session)) {
					authResponse = authManager.Authorize(request.getParameter("code"));
					JWTManager jwtManager = new JWTManager(configs, authResponse.id_token);
					//Decode id_token (JWT) 
					jwt = jwtManager.DecodeJWT();
					//Validate the Decoded Token
					if(jwtManager.ValidateJWT(jwt)){
						//Save Auth Response
			            authManager.SaveAuthResponse(authResponse);
					}	
					else
						throw new Exception("Invalid JWT");
				}
				else
					throw new Exception("State Parameter returned doesn't match to the one sent to Core API Server.");
			}
			
			//Load Activity List
		    if(authManager.GetAuthResponse() != null){
		    	response.sendRedirect("views/ActivityListView.jsp");		         
		    }
			
		} catch(Exception ex){
			out.print("<div style='color:red'>" + ex.getMessage() + "</div>");
		}		
	%>
</body>
</html>