package edu.rice.comp504.model.user;

/**
 * A factory that will make messages.
 */
public interface IUserFactory {
    /**
     * Create a singleton factory.
     * @return The singleton factory
     */
    IUserFactory makeFactory();

    /**
     * Make a user object.
     * @param username unique username
     * @param pwd password
     * @param age age
     * @param school school
     * @param interests list of interests of user combined into a string
     * @return the user object
     */
    User makeUser(String username, String pwd, int age, String school, String interests);
}
