package softuni.exam.models.dto.xml.tickets;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TicketDTO {

    @XmlElement(name = "serial-number")
    @Size(min = 2)
    @NotNull
    private String serialNumber;
    @XmlElement(name = "take-off")
    @NotNull
    private String takeOff;
    @XmlElement(name = "price")
    @Positive
    private double price;
    @XmlElement(name =  "from-town")
    @NotNull
    private TownNameDto fromTown;
    @XmlElement(name = "to-town")
    @NotNull
    private TownNameDto toTown;
    @XmlElement(name = "passenger")
    @NotNull
    private PassengerEmailDTO passenger;
    @XmlElement(name = "plane")
    @NotNull
    private PlaneRegistrationNumberDTO plane;

    public TicketDTO() {
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getTakeOff() {
        return takeOff;
    }

    public void setTakeOff(String takeOff) {
        this.takeOff = takeOff;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public TownNameDto getFromTown() {
        return fromTown;
    }

    public void setFromTown(TownNameDto fromTown) {
        this.fromTown = fromTown;
    }

    public TownNameDto getToTown() {
        return toTown;
    }

    public void setToTown(TownNameDto toTown) {
        this.toTown = toTown;
    }

    public PassengerEmailDTO getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerEmailDTO passenger) {
        this.passenger = passenger;
    }

    public PlaneRegistrationNumberDTO getPlane() {
        return plane;
    }

    public void setPlane(PlaneRegistrationNumberDTO plane) {
        this.plane = plane;
    }
}
