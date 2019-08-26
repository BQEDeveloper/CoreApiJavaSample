package shared;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import models.HttpHeadersModel;
import models.HttpResponseModel;

public class APIHelper {

	public static HttpResponseModel Get(String uri, HttpHeadersModel httpHeaders) throws Exception {
		try {
			HttpResponseModel httpResponse = new HttpResponseModel();
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", httpHeaders.userAgent);
			if (httpHeaders.authorization != null && !httpHeaders.authorization.isEmpty())
				con.setRequestProperty("Authorization", httpHeaders.authorization);
			con.setRequestProperty("Content-Type", httpHeaders.contentType);
			httpResponse.header_code = con.getResponseCode();
			httpResponse.header = con.getHeaderFields().toString();
			if(httpResponse.header_code == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				httpResponse.body = response.toString();
				httpResponse.response = response.toString();

			}
			con.disconnect();
			return httpResponse;
		} catch (Exception ex) {
			throw ex;
		}
	}

	public static HttpResponseModel Post(String uri, String data, HttpHeadersModel httpHeaders) throws Exception {
		try {
			HttpResponseModel httpResponse = new HttpResponseModel();
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();			
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", httpHeaders.userAgent);
			if (httpHeaders.authorization != null && !httpHeaders.authorization.isEmpty())
				con.setRequestProperty("Authorization", httpHeaders.authorization);
			con.setRequestProperty("Content-Type", httpHeaders.contentType);
			con.setRequestProperty( "Accept", "*/*" );
			OutputStream os = (OutputStream) con.getOutputStream();
			os.write(data.getBytes("UTF-8"));
			os.close();

			httpResponse.header_code = con.getResponseCode();
			httpResponse.header = con.getHeaderFields().toString();
			if(httpResponse.header_code == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				httpResponse.body = response.toString();
				httpResponse.response = response.toString();
			}
			con.disconnect();
			return httpResponse;

		} catch (Exception ex) {
			throw ex;
		}
	}

	public static HttpResponseModel Put(String uri, String data, HttpHeadersModel httpHeaders) throws Exception {
		try {
			HttpResponseModel httpResponse = new HttpResponseModel();
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("Content-Type", httpHeaders.contentType);
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestMethod("PUT");
			con.setRequestProperty("User-Agent", httpHeaders.userAgent);
			if (httpHeaders.authorization != null && !httpHeaders.authorization.isEmpty())
				con.setRequestProperty("Authorization", httpHeaders.authorization);
			con.setRequestProperty("Content-Type", httpHeaders.contentType);

			OutputStream os = (OutputStream) con.getOutputStream();
			os.write(data.getBytes("UTF-8"));
			os.close();

			httpResponse.header_code = con.getResponseCode();
			httpResponse.header = con.getHeaderFields().toString();
			if(httpResponse.header_code == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				httpResponse.body = response.toString();
				httpResponse.response = response.toString();	
			}			
			con.disconnect();
			return httpResponse;
		} catch (Exception ex) {
			throw ex;
		}
	}

	public static HttpResponseModel Delete(String uri, HttpHeadersModel httpHeaders) throws Exception {
		try {
			HttpResponseModel httpResponse = new HttpResponseModel();
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("DELETE");
			// con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setRequestProperty("User-Agent", httpHeaders.userAgent);
			if (httpHeaders.authorization != null && !httpHeaders.authorization.isEmpty())
				con.setRequestProperty("Authorization", httpHeaders.authorization);
			con.setRequestProperty("Content-Type", httpHeaders.contentType);
			httpResponse.header_code = con.getResponseCode();
			httpResponse.header = con.getHeaderFields().toString();
			if(httpResponse.header_code == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				httpResponse.body = response.toString();
				httpResponse.response = response.toString();	
			}			
			con.disconnect();
			return httpResponse;
		} catch (Exception ex) {
			throw ex;
		}
	}
}