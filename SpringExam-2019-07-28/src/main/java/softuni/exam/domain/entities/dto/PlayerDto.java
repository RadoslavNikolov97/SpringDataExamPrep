package softuni.exam.domain.entities.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class PlayerDto {

        @SerializedName("firstName")
        @Expose
        @NotNull
        private String firstName;

        @SerializedName("lastName")
        @Expose
        @Size(min = 3 , max = 15)
        @NotNull
        private String lastName;

        @SerializedName("number")
        @Expose
        @Min(1)
        @Max(99)
        @NotNull
        private Integer number;

        @SerializedName("salary")
        @Expose
        private BigDecimal salary;

        @SerializedName("position")
        @Expose
        @Size(max = 3)
        @NotNull
        private String position;

        @SerializedName("picture")
        @Expose
        private UrlDto picture;

        @SerializedName("team")
        @Expose
        private NameDto team;

    public PlayerDto() {}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getNumber() {
        return number;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public String getPosition() {
        return position;
    }

    public UrlDto getPicture() {
        return picture;
    }

    public NameDto getTeam() {
        return team;
    }
}
