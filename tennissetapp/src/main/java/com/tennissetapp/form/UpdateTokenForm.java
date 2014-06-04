package com.tennissetapp.form;

public class UpdateTokenForm extends AbstractForm{
    public String token;
    public String provider;

    public UpdateTokenForm(){}
    public UpdateTokenForm(String provider,String token){
        this.provider = provider;
        this.token = token;
    }

    @Override
    public String toString() {
        return "UpdateTokenForm [token=" + token + ", provider=" + provider
                + "]";
    }
}