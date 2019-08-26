package models;

public class JWTModel {
	public JWTHeader header;
    public JWTPayload payload;
    public String signature;
    
    public JWTModel() {
        this.header = new JWTHeader();
        this.payload = new JWTPayload();
    }
}