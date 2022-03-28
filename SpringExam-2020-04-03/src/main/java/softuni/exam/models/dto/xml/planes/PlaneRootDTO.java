package softuni.exam.models.dto.xml.planes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "planes")
@XmlAccessorType(XmlAccessType.FIELD)

public class PlaneRootDTO {

    @XmlElement(name = "plane")
    private List<PlaneDTO> planes;

    public PlaneRootDTO() {
    }

    public List<PlaneDTO> getPlanes() {
        return planes;
    }

    public void setPlanes(List<PlaneDTO> planes) {
        this.planes = planes;
    }
}
