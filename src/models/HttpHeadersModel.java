package models;

public class HttpHeadersModel {	
	public String userAgent;
	public String authorization;
	public String contentType;
	public HttpHeadersModel() {
		this.userAgent = "Mozilla/5.0";
		this.contentType = "application/json; charset=UTF-8";
	}
}

