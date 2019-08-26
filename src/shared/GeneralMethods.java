package shared;

import java.io.FileInputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Random;
import java.util.Base64;

import models.ConfigModel;

public class GeneralMethods {

	public static ConfigModel GetConfig() throws Exception {
		try {
			ConfigModel config = new ConfigModel();
			Properties properties = new Properties();
			URL url = GeneralMethods.class.getProtectionDomain().getCodeSource().getLocation();		    
		    Path path = Paths.get(url.toURI());		    
		    properties.load(new FileInputStream(path.getParent().getParent() + "/config.ini"));
		    config.CoreAPIBaseUrl = properties.getProperty("CoreAPIBaseUrl");
		    config.CoreIdentityBaseUrl = properties.getProperty("CoreIdentityBaseUrl");
		    config.Scopes = properties.getProperty("Scopes");
		    config.Secret = properties.getProperty("Secret");
		    config.ClientID = properties.getProperty("ClientID");
		    config.RedirectURI = properties.getProperty("RedirectURI");		    
			// properties.list(System.out);
			return config;
		} catch (Exception ex) {
			throw ex;
		}
	}	

	public static String GenerateRandomString() throws Exception {
		try {
			String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_+\":?><";
			int length = 20, charactersLength = characters.length();
			String randomString = "";
			Random r = new Random();
			for (int i = 0; i < length; i++) {
				randomString += characters.charAt((r.nextInt((charactersLength - 1) - 0) + 0));
			}
			return randomString;
		} catch (Exception ex) {
			throw ex;
		}
	}

	public static String Base64UrlDecode(String uri) throws Exception {
		try {
			// Getting decoder  
	        Base64.Decoder decoder = Base64.getUrlDecoder();
			return new String(decoder.decode(uri), StandardCharsets.UTF_8);
		} catch (Exception ex) {
			throw ex;
		}
	}

}
