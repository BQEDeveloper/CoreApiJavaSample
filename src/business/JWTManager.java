package business;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



import models.AuthResponseModel;
import models.ConfigModel;
import models.HttpHeadersModel;
import models.HttpResponseModel;
import models.JWKSModel;
import models.JWTHeader;
import models.JWTModel;
import models.JWTPayload;
import shared.APIHelper;
import shared.GeneralMethods;

public class JWTManager {
	
	public ConfigModel config;
	public AuthResponseModel authResponse;
	public HttpHeadersModel httpHeader;
	public HttpResponseModel httpResponse;
	public AuthManager authManager;
	public JWTModel jwt;
	public JWKSModel jwks;
	public String id_token;
	
	public JWTManager(ConfigModel config, String id_token) throws Exception {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.setSerializationInclusion(Include.NON_EMPTY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			this.config = config;
			this.id_token = id_token;	
			
			this.authManager = new AuthManager();
			
			this.httpHeader = new HttpHeadersModel();
			if(this.authManager.GetAuthResponse() != null)
				this.httpHeader.authorization = "Bearer " + this.authManager.GetAuthResponse().access_token;
			
			this.jwt = new JWTModel();
			
			this.httpResponse = APIHelper.Get(this.config.CoreIdentityBaseUrl + "/.well-known/openid-configuration/jwks", this.httpHeader);
			JsonNode arrNode = mapper.readTree(this.httpResponse.body).get("keys");
			if (arrNode.isArray()) {
			    for (final JsonNode objNode : arrNode) {
			    	this.jwks = mapper.convertValue(objNode, JWKSModel.class);
			    }
			}

		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public JWTModel DecodeJWT() throws Exception {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.setSerializationInclusion(Include.NON_EMPTY);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
						
			String header = this.id_token.split("\\.")[0];
			String payload = this.id_token.split("\\.")[1];
			String signature = this.id_token.split("\\.")[2];
			this.jwt.header = mapper.readValue(GeneralMethods.Base64UrlDecode(header), JWTHeader.class);
			this.jwt.payload = mapper.readValue(GeneralMethods.Base64UrlDecode(payload), JWTPayload.class);
			this.jwt.signature = GeneralMethods.Base64UrlDecode(signature);
			return this.jwt;
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	public Boolean ValidateJWT(JWTModel jwt) throws Exception {
		try {
			this.jwt = jwt;
			return ValidateJWTHeader() && ValidateJWTPayload() && VerifyJWTSingature();
		} catch (Exception ex) {
			throw ex;
		}
	}	
	
	private boolean ValidateJWTHeader() throws Exception {
		try {
			//verify whether algorithm mentioned in Id Token (JWT) matches to the one in JWKS
            if(!this.jwt.header.alg.equals(this.jwks.alg))
               throw new Exception("JWT algorithm doesn't match to the one mentioned in the Core API JWKS");
            //verify whether kid mentioned in Id Token (JWT) matches to the one in JWKS
            if(!this.jwt.header.kid.equals(this.jwks.kid))
               throw new Exception("JWT kid doesn't match to the one mentioned in the Core API JWKS");
            return true;
		} catch (Exception ex) {
			throw ex;
		}
	}

	private boolean ValidateJWTPayload() throws Exception {
		try {
			//verify issuer (iss) mentioned in Id Token (JWT) matches to the one in config.ini
            if(!this.jwt.payload.iss.equals(this.config.CoreIdentityBaseUrl))
               throw new Exception("JWT issuer (iss) doesn't match to the one mentioned in the config.ini");
            //verify audience (aud) mentioned in Id Token (JWT) matches to the one in config.ini
            if(!this.jwt.payload.aud.equals(this.config.ClientID))
               throw new Exception("JWT audience (aud) doesn't match to the one mentioned in the config.ini");
            //verify expiry time (exp) mentioned in Id Token (JWT) has not passed
            if(Long.parseLong(this.jwt.payload.exp) < (System.currentTimeMillis()/1000L))
              throw new Exception("JWT expiry time (exp) has already passed. Verify if the Java server timezone (current timestamp) is correct or the JWT is already expired.");
            return true;
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	private boolean VerifyJWTSingature() throws Exception {
		try {
			//Use RSA algo to validate the signature.
			return true;
		} catch (Exception ex) {
			throw ex;
		}
		
	}

	
}
