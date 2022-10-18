package edu.rice.comp504.model.user;

public interface IUserFactory {

    IUserFactory makeFactory();

    User makeUser(String username, String pwd, int age, String school, String[] interests);
}
