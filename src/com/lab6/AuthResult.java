package com.lab6;

//Класс возвращаемый функцией авторизации.
//Содержит ссылку на пользователя и код ошибки (или успеха)
public class AuthResult {
    public final AuthCode authCode;
    public final User user;


    public AuthResult(AuthCode authCode, User user) {
        this.authCode = authCode;
        this.user = user;
    }
}
