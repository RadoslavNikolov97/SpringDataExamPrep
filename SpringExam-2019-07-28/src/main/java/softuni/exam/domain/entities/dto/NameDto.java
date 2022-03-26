package softuni.exam.domain.entities.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.NotNull;


public class NameDto {
    @SerializedName("name")
    @Expose
    @NotNull
    private String name;

    public NameDto() {}

    public String getName() {
        return name;
    }
}
