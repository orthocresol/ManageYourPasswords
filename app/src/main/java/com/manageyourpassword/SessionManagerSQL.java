package com.manageyourpassword;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagerSQL {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SessionManagerSQL(Context context){
        sharedPreferences = context.getSharedPreferences("App Key", 0);
        editor = sharedPreferences.edit();
        editor.apply();
    }
    public void setLogin(Boolean login){
        editor.putBoolean("KEY_LOGIN", login);
        editor.commit();
    }
    public Boolean getLogin(){
        return sharedPreferences.getBoolean("KEY_LOGIN", false);
    }
    public void setUsername(String name){
        editor.putString("KEY_USERNAME", name);
        editor.commit();
    }
    public String getUsername(){
        return sharedPreferences.getString("KEY_USERNAME", "");
    }
}
