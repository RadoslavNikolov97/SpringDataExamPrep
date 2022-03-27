package softuni.exam.models.dto.xmlDTO.offer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "offers")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferRootDTO {

    @XmlElement(name = "offer")
    private List<OfferDTO> offers;

    public OfferRootDTO() {}

    public List<OfferDTO> getOffers() {
        return offers;
    }
}
