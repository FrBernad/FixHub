package ar.edu.itba.paw.webapp.dto.response;

public class AuthenticationTokenDto {

    private String tokenId;
    private int expiresIn;

    public AuthenticationTokenDto() {
    }

    public AuthenticationTokenDto(String tokenId, int expiresIn) {
        this.tokenId = tokenId;
        this.expiresIn = expiresIn;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
