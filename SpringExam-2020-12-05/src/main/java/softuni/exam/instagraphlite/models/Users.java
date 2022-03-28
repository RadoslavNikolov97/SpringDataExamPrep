package softuni.exam.instagraphlite.models;

import javax.persistence.*;

@Entity
@Table
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String username;
    @Column
    private String password;
    @ManyToOne
    private Pictures profilePicture;

    public Users() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Pictures getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Pictures profilePicture) {
        this.profilePicture = profilePicture;
    }
}
