package com.example.football.models.dto.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "stats")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatsRootDTO {

    @XmlElement(name = "stat")
    private List<StatsDTO> stats;

    public StatsRootDTO() {
    }

    public List<StatsDTO> getStats() {
        return stats;
    }

    public void setStats(List<StatsDTO> stats) {
        this.stats = stats;
    }
}
