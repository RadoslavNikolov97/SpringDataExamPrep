package softuni.exam.models.dto.xml.planes;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneDTO {
    @XmlElement(name = "register-number")
    @NotNull
    @Size(min = 5)
    private String registrationNumber;
    @XmlElement(name = "capacity")
    @Positive
    private int capacity;
    @XmlElement(name = "airline")
    @NotNull
    @Size(min = 2)
    private String airline;

    public PlaneDTO() {}

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }
}

