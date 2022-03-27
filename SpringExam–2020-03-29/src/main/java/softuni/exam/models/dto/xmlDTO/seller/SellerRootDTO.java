package softuni.exam.models.dto.xmlDTO.seller;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sellers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellerRootDTO {

    @XmlElement(name = "seller")
    private List<SellerDTO> offers;

    public SellerRootDTO() {
    }

    public List<SellerDTO> getOffers() {
        return offers;
    }
}
