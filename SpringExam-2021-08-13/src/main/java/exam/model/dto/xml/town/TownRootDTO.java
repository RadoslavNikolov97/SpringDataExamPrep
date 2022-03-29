package exam.model.dto.xml.town;

import javax.xml.bind.annotation.XmlAccessType;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "towns")
@XmlAccessorType(XmlAccessType.FIELD)
public class TownRootDTO {

    @XmlElement(name = "town")
    private List<TownDTO> towns;

    public TownRootDTO() {
    }

    public List<TownDTO> getTowns() {
        return towns;
    }

    public void setTowns(List<TownDTO> towns) {
        this.towns = towns;
    }
}
