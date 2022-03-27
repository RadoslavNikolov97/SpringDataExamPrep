package softuni.exam.models.dto.xmlDTO.seller;

import softuni.exam.models.enums.Rating;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;



@XmlAccessorType(XmlAccessType.FIELD)
public class SellerDTO {
    @XmlElement(name = "first-name")
    @NotNull
    @Size(min = 2 , max = 20)
    private String firstName;
    @XmlElement(name = "last-name")
    @NotNull
    @Size(min = 2 , max = 20)
    private String lastName;
    @XmlElement(name = "email")
    @Email
    @NotNull
    private String email;
    @XmlElement(name = "rating")
    @NotNull
    private Rating rating;
    @XmlElement(name = "town")
    @NotNull
    private String town;

    public SellerDTO() {}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Rating getRating() {
        return rating;
    }

    public String getTown() {
        return town;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
