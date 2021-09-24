package edu.tjaacks.springlogindemo.reponses;

public class LoginResponse extends IResponse {
    private String loginToken;
    private String loginExpiration;

    public LoginResponse(String loginToken, String loginExpiration) {
        this.loginToken = loginToken;
        this.loginExpiration = loginExpiration;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getLoginExpiration() {
        return loginExpiration;
    }

    public void setLoginExpiration(String loginExpiration) {
        this.loginExpiration = loginExpiration;
    }
}
