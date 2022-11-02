package edu.rice.comp504.model.user;

public class UserFactory implements IUserFactory{
    private static UserFactory ONLY;
    /**
     * Create a singleton factory.
     * @return The singleton factory
     */
    public static UserFactory makeFactory() {
        if (ONLY == null ) {
            ONLY = new UserFactory();
        }
        return ONLY;
    }

    /**
     * Make a user object.
     * @param username unique username
     * @param pwd password
     * @param age age
     * @param school school
     * @param interests list of interests of user combined into a string
     * @return the user object
     */
    public AUser makeUser(String username, String pwd, int age, String school, String[] interests) {
        return new User(username, pwd, age, school, interests);
    }
}
