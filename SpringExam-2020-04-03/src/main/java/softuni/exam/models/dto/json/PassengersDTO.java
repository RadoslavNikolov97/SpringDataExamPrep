package softuni.exam.models.dto.json;

import javax.annotation.Generated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PassengersDTO {

    @SerializedName("firstName")
    @Expose
    @NotNull
    @Size(min = 2)
    private String firstName;
    @SerializedName("lastName")
    @Expose
    @NotNull
    @Size(min = 2)
    private String lastName;
    @SerializedName("age")
    @Expose
    @Positive
    private Integer age;
    @SerializedName("phoneNumber")
    @Expose
    @NotNull
    private String phoneNumber;
    @SerializedName("email")
    @Expose
    @Email
    @NotNull
    private String email;
    @SerializedName("town")
    @Expose
    @NotNull
    private String town;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

}