package business;

import java.net.HttpURLConnection;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.AuthResponseModel;
import models.ConfigModel;
import models.HttpHeadersModel;
import models.HttpResponseModel;
import models.UserInfoModel;
import shared.APIHelper;
import shared.GeneralMethods;

public class UserInfoManager {
	public ConfigModel config;
	public AuthResponseModel authResponse;
	public HttpResponseModel httpResponse;
	public AuthManager authManager;
	public HttpHeadersModel headers;

	public UserInfoManager() throws Exception {
		try {
			this.config = GeneralMethods.GetConfig();
			this.authResponse = new AuthResponseModel();
			this.authManager = new AuthManager();
			this.headers = new HttpHeadersModel();
			if (this.authManager.GetAuthResponse() != null)
				this.authResponse = this.authManager.GetAuthResponse();
			this.headers.authorization = "Bearer " + this.authResponse.access_token;
		} catch (Exception ex) {
			throw ex;
		}
	}

	public UserInfoModel GetUserInfo() throws Exception {
		try {
			UserInfoModel userInfo = new UserInfoModel();
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.setSerializationInclusion(Include.NON_EMPTY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			this.httpResponse = APIHelper.Get(this.config.CoreIdentityBaseUrl + "/connect/userinfo", this.headers);
			if (this.httpResponse.header_code == HttpURLConnection.HTTP_UNAUTHORIZED) { // UnAuthorised
				AuthManager authManager = new AuthManager();
				this.authResponse = authManager.ReAuthorize();
				if (authResponse != null) {
					this.headers.authorization = "Bearer " + this.authResponse.access_token;
					return this.GetUserInfo();
				}
			} else if (this.httpResponse.header_code == HttpURLConnection.HTTP_OK) { // Success
				userInfo = mapper.readValue(this.httpResponse.body, UserInfoModel.class);
			}
			return userInfo;
		} catch (Exception ex) {
			throw ex;
		}
	}
}
