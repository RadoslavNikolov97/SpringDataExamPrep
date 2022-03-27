package softuni.exam.models.dto.jsonDTO;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;


public class CarDTO {

    @SerializedName("make")
    @Expose
    @Size(min = 2 , max = 20)
    private String make;
    @SerializedName("model")
    @Expose
    @Size(min = 2 , max = 20)
    private String model;
    @SerializedName("kilometers")
    @Expose
    @Positive
    private Integer kilometers;
    @SerializedName("registeredOn")
    @Expose
    private String registeredOn;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
    }

    public String getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(String registeredOn) {
        this.registeredOn = registeredOn;
    }

}