package models;

import java.io.Serializable;
import java.math.BigInteger;

public class AuthResponseModel implements Serializable  {

	private static final long serialVersionUID = 1L;
	public String id_token;
    public String access_token;
    public BigInteger expires_in;
    public String token_type;
    public String refresh_token;
    public String scope;
    public String endpoint;
}
