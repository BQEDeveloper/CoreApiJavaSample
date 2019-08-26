package Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import business.ActivityManager;
import business.AuthManager;
import models.ActivityModel;

/**
 * Servlet implementation class indexServlet
 */
public class indexServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    private AuthManager authManager;
    private ActivityManager activityManager;
    /**
     * @throws Exception 
     * @see HttpServlet#HttpServlet()
     */
    public indexServlet() throws Exception {
        super();
        this.authManager = new AuthManager();
        this.activityManager = new ActivityManager();        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			if(request.getParameter("btnConnectToCore") != null && request.getParameter("btnConnectToCore").toString().equals("Connect to Core API"))
				this.authManager.ConnectToCore(request, response);		
			else if(request.getParameter("btnDisconnectFromCore") != null && request.getParameter("btnDisconnectFromCore").toString().equals("Disconnect from Core"))
				this.authManager.DisconnectFromCore(request, response);
			else if(request.getParameter("btnSubmitActivity") != null && request.getParameter("btnSubmitActivity").toString().equals("Submit Activity"))
				createOrUpdateActivity(request, response);
			else if(request.getParameter("btnDeleteActivity") != null && request.getParameter("btnDeleteActivity").toString().equals("DeleteActivity"))
				deleteActivity(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
			response.getOutputStream().print("<div style='color:red'>" + ex.getMessage() + "</div>");
		}

		//print result
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void createOrUpdateActivity(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			ActivityModel activity = new ActivityModel();
			activity.code = request.getParameter("code");
			activity.description = request.getParameter("description");
			if(request.getParameter("billRate") != null && !request.getParameter("billRate").isEmpty())
				activity.billRate = Double.parseDouble(request.getParameter("billRate"));
			if(request.getParameter("costRate") != null && !request.getParameter("costRate").isEmpty())
				activity.costRate = Double.parseDouble(request.getParameter("costRate"));
			if(request.getParameter("isBillable") != null && !request.getParameter("isBillable").isEmpty())
				activity.billable = Boolean.parseBoolean(request.getParameter("isBillable").toUpperCase().equals("ON") ? "true" : request.getParameter("isBillable"));
			if(request.getParameter("id") == null || request.getParameter("id").isEmpty()) { // Create Activity
				this.activityManager.Create(activity);	
				response.sendRedirect("views/ActivityListView.jsp");
			} else { // Update Activity
				activity.id = request.getParameter("id");
				this.activityManager.Update(activity.id, activity);
				response.sendRedirect("views/ActivityListView.jsp");
			}
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	private void deleteActivity(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
				if(request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
					this.activityManager.Delete(request.getParameter("id"));
					response.sendRedirect("views/ActivityListView.jsp");
				}		
		} catch (Exception ex) {
			throw ex;
		}
	}

}
