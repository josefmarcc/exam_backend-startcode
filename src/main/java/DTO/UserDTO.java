package DTO;

import entities.User;

/**
 *
 * @author Josef Marc Pedersen <cph-jp325@cphbusiness.dk>
 */
public class UserDTO {

    private String userName;

    public UserDTO(User user) {
        this.userName = user.getUserName();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
