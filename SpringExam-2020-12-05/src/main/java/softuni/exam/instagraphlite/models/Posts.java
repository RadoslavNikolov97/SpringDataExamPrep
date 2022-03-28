package softuni.exam.instagraphlite.models;

import javax.persistence.*;

@Entity
@Table
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String caption;
    @ManyToOne
    private Users user;
    @ManyToOne
    private Pictures picture;

    public Posts() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Pictures getPicture() {
        return picture;
    }

    public void setPicture(Pictures picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return String.format("==Post Details:\n" +
                "----Caption: %s\n" +
                "----Picture Size: %.2f\n",getCaption(),getPicture().getSize());
    }
}
