package softuni.exam.models.dto.xmlDTO.offer;


import softuni.exam.models.dto.xmlDTO.CarIdDTO;
import softuni.exam.models.dto.xmlDTO.SellerIdDTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OfferDTO {
    @XmlElement(name = "description")
    @Size(min = 5)
    private String description;

    @XmlElement(name = "price")
    @Positive
    private double price;

    @XmlElement(name = "added-on")
    private String addedOn;

    @XmlElement(name = "has-gold-status")
    private boolean hasGoldStatus;

    @XmlElement(name = "car")
    private CarIdDTO carId;

    @XmlElement(name = "seller")
    private SellerIdDTO sellerId;

    public OfferDTO() {}

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public boolean isHasGoldStatus() {
        return hasGoldStatus;
    }

    public CarIdDTO getCarId() {
        return carId;
    }

    public SellerIdDTO getSellerId() {
        return sellerId;
    }
}
