package com.Practice.DIACataloge;

import java.util.List;

public class UserDataClass {
     public String Email;
    public String Name;
    public List<String> LastView;
    public List<String> Favorites;

    public UserDataClass(String email, String name, List<String> lastView, List<String> favorites) {
        Email = email;
        Name = name;
        LastView = lastView;
        Favorites = favorites;
    }
    public UserDataClass() {
    }


}
