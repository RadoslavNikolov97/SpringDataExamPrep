package softuni.exam.domain.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column( name = "url")
    private String url;


    public Picture(int id, String url) {
        this.id = id;
        this.url = url;


    }

    public Picture() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Picture)) return false;
        Picture picture = (Picture) o;
        return getId() == picture.getId() && getUrl().equals(picture.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUrl());
    }
}
