package business;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.ActivityModel;
import models.AuthResponseModel;
import models.ConfigModel;
import models.HttpHeadersModel;
import models.HttpResponseModel;
import shared.APIHelper;
import shared.GeneralMethods;

public class ActivityManager {
	
	public ConfigModel config;
	public AuthResponseModel authResponse;
	public HttpHeadersModel httpHeader;
	public AuthManager authManager;
	public HttpResponseModel httpResponse;
	
	public ActivityManager() throws Exception {
		try {
			this.config = GeneralMethods.GetConfig();
			
			this.authResponse = new AuthResponseModel();
			this.authManager = new AuthManager();
			
			if(this.authManager.GetAuthResponse() != null) {
				this.authResponse = this.authManager.GetAuthResponse();
				this.httpHeader = new HttpHeadersModel();
				this.httpResponse = new HttpResponseModel();
				this.httpHeader.authorization = "Bearer " + this.authManager.GetAuthResponse().access_token;
			}
		} catch (Exception ex) {
			throw ex;
		}		
	}
	
	public List<ActivityModel> GetList() throws Exception {
		try {						
			ObjectMapper mapper = new ObjectMapper();
			String activityJson;
			List<ActivityModel> activityList = null;
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.setSerializationInclusion(Include.NON_EMPTY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);			
			this.httpResponse = APIHelper.Get(this.config.CoreAPIBaseUrl + "/activity?page=0,100&orderby=name", this.httpHeader);
			if(this.httpResponse.header_code == HttpURLConnection.HTTP_UNAUTHORIZED) { //UnAuthorised
				AuthManager authManager = new AuthManager();
        		this.authResponse = authManager.ReAuthorize();        		
        		if(authResponse != null ) {
        			this.httpHeader.authorization = "Bearer " + this.authResponse.access_token;
        			return this.GetList();
        		}				
			} else if (this.httpResponse.header_code == HttpURLConnection.HTTP_OK) { //Success
				activityJson = httpResponse.body;
				activityList = Arrays.asList(mapper.readValue(activityJson, ActivityModel[].class));	
			} else {
				throw new Exception(this.httpResponse.header);
			}		
			return activityList;								
		} catch (Exception ex) {
			throw ex;
		}	
	}
	
	public ActivityModel Get(String id) throws Exception {
		try {			
			ObjectMapper mapper = new ObjectMapper();
			String activityJson;
			ActivityModel activity = null;
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.setSerializationInclusion(Include.NON_EMPTY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			this.httpResponse = APIHelper.Get(this.config.CoreAPIBaseUrl + "/activity/" + id, this.httpHeader);
			if(this.httpResponse.header_code == HttpURLConnection.HTTP_UNAUTHORIZED) { //UnAuthorised
				AuthManager authManager = new AuthManager();
        		this.authResponse = authManager.ReAuthorize();        		
        		if(authResponse != null ) {
        			this.httpHeader.authorization = "Bearer " + this.authResponse.access_token;
        			return this.Get(id);
        		}
			} else if (this.httpResponse.header_code == HttpURLConnection.HTTP_OK) { //Success
				activityJson = httpResponse.body;
				activity = mapper.readValue(activityJson, ActivityModel.class);	
			} else {
				throw new Exception(this.httpResponse.header);
			}
			return activity;								
		} catch (Exception ex) {
			throw ex;
		}	
	}
	
	public HttpResponseModel Create(ActivityModel activity) throws Exception {
		try {			
			ObjectMapper mapper = new ObjectMapper();
			String activityJson;
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.setSerializationInclusion(Include.NON_EMPTY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			activityJson = mapper.writeValueAsString(activity);
			this.httpResponse = APIHelper.Post(this.config.CoreAPIBaseUrl + "/activity/", activityJson, this.httpHeader);
			if(this.httpResponse.header_code == HttpURLConnection.HTTP_UNAUTHORIZED) { //UnAuthorised
				AuthManager authManager = new AuthManager();
        		this.authResponse = authManager.ReAuthorize();        		
        		if(authResponse != null ) {
        			this.httpHeader.authorization = "Bearer " + this.authResponse.access_token;
        			return this.Create(activity);
        			//HttpResponseModel newHttpResponse = new HttpResponseModel();
        			//newHttpResponse = APIHelper.Post(this.config.CoreAPIBaseUrl + "/activity/", activityJson, this.httpHeader);
        			//return newHttpResponse;        			
        		}
			} else if (this.httpResponse.header_code == HttpURLConnection.HTTP_OK || this.httpResponse.header_code == HttpURLConnection.HTTP_CREATED) { //Success or Created
				activityJson = httpResponse.body;	
			} else {
				throw new Exception(this.httpResponse.header);
			}
			return this.httpResponse;								
		} catch (Exception ex) {
			throw ex;
		}	
	}
	
	public ActivityModel Update(String id, ActivityModel activity) throws Exception {
		try {			
			ObjectMapper mapper = new ObjectMapper();
			String activityJson;
			ActivityModel newActivity = null;
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.setSerializationInclusion(Include.NON_EMPTY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			activityJson = mapper.writeValueAsString(activity);
			this.httpResponse = APIHelper.Put(this.config.CoreAPIBaseUrl + "/activity/" + id, activityJson, this.httpHeader);
			if(this.httpResponse.header_code == HttpURLConnection.HTTP_UNAUTHORIZED) { //UnAuthorised
				AuthManager authManager = new AuthManager();
        		this.authResponse = authManager.ReAuthorize();        		
        		if(authResponse != null ) {
        			this.httpHeader.authorization = "Bearer " + this.authResponse.access_token;
        			return this.Update(id, activity);
        		}
			} else if (this.httpResponse.header_code == HttpURLConnection.HTTP_OK) { //Success
				activityJson = httpResponse.body;
				newActivity = mapper.readValue(activityJson, ActivityModel.class);	
			} else {
				throw new Exception(this.httpResponse.header);
			}
			return newActivity;								
		} catch (Exception ex) {
			throw ex;
		}	
	}
	
	public HttpResponseModel Delete(String id) throws Exception {
		try {	
			String activityJson;
			this.httpResponse = APIHelper.Delete(this.config.CoreAPIBaseUrl + "/activity/" + id, this.httpHeader);
			if(this.httpResponse.header_code == HttpURLConnection.HTTP_UNAUTHORIZED) { //UnAuthorised
				AuthManager authManager = new AuthManager();
        		this.authResponse = authManager.ReAuthorize();        		
        		if(authResponse != null ) {
        			this.httpHeader.authorization = "Bearer " + this.authResponse.access_token;
        			return this.Delete(id);
        		}
			} else if (this.httpResponse.header_code == HttpURLConnection.HTTP_OK || this.httpResponse.header_code == HttpURLConnection.HTTP_NO_CONTENT) { //Success or No conflict
				activityJson = httpResponse.body;	
			} else {
				throw new Exception(this.httpResponse.header);
			}
			return httpResponse;								
		} catch (Exception ex) {
			throw ex;
		}	
	}

}
