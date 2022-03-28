package softuni.exam.instagraphlite.models.dto.jsonDTO;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class PictureDTO {

    @SerializedName("path")
    @Expose
    @NotNull
    private String path;
    @SerializedName("size")
    @Expose
    @NotNull
    @Min(500)
    @Max(600000)
    private double size;

    public PictureDTO() {}


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

}