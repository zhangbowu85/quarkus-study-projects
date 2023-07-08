package cn.zbw.example.sp4.entity;

public class Token {
    public String userName;
    public String token;

    public long expiredAt;
    public long generatedAt;

    public Token(String userName, String token, long generatedAt, long expiredAt) {
        this.userName = userName;
        this.token = token;
        this.generatedAt = generatedAt;
        this.expiredAt = expiredAt;
    }

    public Token() {

    }
}
