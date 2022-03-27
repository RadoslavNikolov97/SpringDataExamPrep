package softuni.exam.models.dto.jsonDTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.Size;


public class PictureDTO {

    @SerializedName("name")
    @Expose
    @Size(min = 2 , max = 20)
    private String name;
    @SerializedName("dateAndTime")
    @Expose
    private String dateAndTime;
    @SerializedName("car")
    @Expose
    private int car;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public int getCar() {
        return car;
    }

    public void setCar(Integer car) {
        this.car = car;
    }

}