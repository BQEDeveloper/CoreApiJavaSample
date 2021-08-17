package business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.AuthResponseModel;
import models.ConfigModel;
import models.HttpHeadersModel;
import models.HttpResponseModel;
import shared.APIHelper;
import shared.GeneralMethods;

public class AuthManager {
	public ConfigModel config;
	public AuthResponseModel authResponse;
	public HttpResponseModel httpResponse;
	public HttpHeadersModel headers;
	
	
	public AuthManager() throws Exception {
		try {
			this.config = GeneralMethods.GetConfig();
			this.authResponse = new AuthResponseModel();
			this.httpResponse = new HttpResponseModel();
			this.headers = new HttpHeadersModel();
			if(GetAuthResponse() != null)
				this.authResponse = GetAuthResponse();
		}
		catch (Exception ex) {
			throw ex;
		}		
	}
	
	public void ConnectToCore(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String state = URLEncoder.encode(GeneralMethods.GenerateRandomString(), StandardCharsets.UTF_8.toString());			
			HttpSession session = request.getSession();  
	        session.setAttribute("state", state); 
			response.sendRedirect(this.config.CoreIdentityBaseUrl + "/connect/authorize?client_id=" + this.config.ClientID
					+ "&response_type=code&scope=" + this.config.Scopes + "&redirect_uri=" + this.config.RedirectURI + "&state="
					+ state);
			
		} catch (Exception ex) {
			throw ex;
		}	
	}
	
	public void DisconnectFromCore(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			this.headers.contentType = "application/x-www-form-urlencoded";
			String data = "token=" + this.authResponse.access_token + "&client_id=" + this.config.ClientID + "&client_secret=" + this.config.Secret;
			this.httpResponse = APIHelper.Post(this.config.CoreIdentityBaseUrl + "/connect/revocation", data, this.headers);
			if(this.httpResponse.header_code == HttpURLConnection.HTTP_OK) {
				SaveAuthResponse(null);
				response.sendRedirect("index.jsp");
			}
			
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public AuthResponseModel Authorize(String code) throws Exception {
		try {
			ObjectMapper mapper = new ObjectMapper();
			this.headers.contentType = "application/x-www-form-urlencoded";
			String data = "code=" + code + "&redirect_uri=" + this.config.RedirectURI 
                    + "&grant_type=authorization_code&client_id=" + this.config.ClientID + "&client_secret=" + this.config.Secret;
			this.httpResponse = APIHelper.Post(this.config.CoreIdentityBaseUrl + "/connect/token", data, this.headers);
			if(this.httpResponse.header_code == 200) 
				this.authResponse = mapper.readValue(this.httpResponse.body, AuthResponseModel.class);
			return this.authResponse;
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public AuthResponseModel ReAuthorize() throws Exception {
		try {
			ObjectMapper mapper = new ObjectMapper();
			AuthResponseModel auth = new AuthResponseModel();
			this.headers.contentType = "application/x-www-form-urlencoded";
			if(GetAuthResponse() != null) {
				auth = GetAuthResponse();
			}
			String data = "refresh_token=" + auth.refresh_token + "&grant_type=refresh_token" 
                    + "&client_id=" + this.config.ClientID + "&client_secret=" + this.config.Secret;
			this.httpResponse = APIHelper.Post(this.config.CoreIdentityBaseUrl + "/connect/token", data, this.headers);
			if(this.httpResponse.header_code == 200) {
				this.authResponse = mapper.readValue(this.httpResponse.body, AuthResponseModel.class);
				SaveAuthResponse(this.authResponse);
			}
			return this.authResponse;
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public void SaveAuthResponse(AuthResponseModel authResponse) throws Exception {
		try {
			URL url = AuthManager.class.getProtectionDomain().getCodeSource().getLocation();		    
		    Path path = Paths.get(url.toURI());		    
			FileOutputStream fileOut = new FileOutputStream(path.getParent().getParent() + "/AuthResponse.ini");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            if(authResponse.endpoint.endsWith("/"))
            	authResponse.endpoint =  authResponse.endpoint.substring(0, authResponse.endpoint.length() - 1);
            objectOut.writeObject(authResponse);
            objectOut.close();

		} catch (Exception ex) {
			throw ex;
		}
	}

	public AuthResponseModel GetAuthResponse() throws Exception {
		try {			
			URL url = AuthManager.class.getProtectionDomain().getCodeSource().getLocation();		    
		    Path path = Paths.get(url.toURI());
		    File file = new File(path.getParent().getParent() + "/AuthResponse.ini");			
			if(file.length() > 0) {
				FileInputStream fileIn = new FileInputStream(file);
				AuthResponseModel authResponse = new AuthResponseModel();
	            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
	            authResponse = (AuthResponseModel) objectIn.readObject();
	            objectIn.close();
				return authResponse;
			} else
				return null;

		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public Boolean IsValidState(String state, HttpSession session) throws Exception {
		try {			
			return URLEncoder.encode(state,"UTF-8").toString().equals(session.getAttribute("state"));
		} catch (Exception ex) {
			throw ex;
		}
	}
}
