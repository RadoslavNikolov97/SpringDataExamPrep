package exam.model.dto.json;

import javax.annotation.Generated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CustomerDTO {

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
    @SerializedName("email")
    @Expose
    @Email
    @NotNull
    private String email;
    @SerializedName("registeredOn")
    @Expose
    @NotNull
    private String registeredOn;
    @SerializedName("town")
    @Expose
    @NotNull
    private TownNameDTOForJSON town;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(String registeredOn) {
        this.registeredOn = registeredOn;
    }

    public TownNameDTOForJSON getTown() {
        return town;
    }

    public void setTown(TownNameDTOForJSON town) {
        this.town = town;
    }

}
