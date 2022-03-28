package softuni.exam.instagraphlite.models.dto.xmlDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PostDTO {
    @XmlElement(name = "caption")
    @NotNull
    @Size(min = 21)
    private String caption;
    @XmlElement(name = "user")
    @NotNull
    private UserNameDTO user;
    @XmlElement(name = "picture")
    @NotNull
    private PicturePathDTO picture;

    public PostDTO() {
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public UserNameDTO getUser() {
        return user;
    }

    public void setUser(UserNameDTO user) {
        this.user = user;
    }

    public PicturePathDTO getPicture() {
        return picture;
    }

    public void setPicture(PicturePathDTO picture) {
        this.picture = picture;
    }
}
