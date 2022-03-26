package softuni.exam.domain.entities.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import javax.validation.constraints.NotNull;


public class UrlDto {
    @SerializedName("url")
    @Expose
    @NotNull
    private String url;

    public UrlDto() {}

    public String getUrl() {
        return url;
    }
}
