package softuni.exam.models.dto.xmlDTO;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SellerIdDTO {
    @XmlElement(name = "id")
    @NotNull
    private int id;

    public SellerIdDTO() {}

    public int getId() {
        return id;
    }
}
