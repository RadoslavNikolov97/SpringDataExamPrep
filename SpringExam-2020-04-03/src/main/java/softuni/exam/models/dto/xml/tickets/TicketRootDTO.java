package softuni.exam.models.dto.xml.tickets;

import softuni.exam.models.dto.xml.tickets.TicketDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "tickets")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketRootDTO {

    @XmlElement(name = "ticket")
    private List<TicketDTO> tickets ;

    public TicketRootDTO() {}

    public List<TicketDTO> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketDTO> tickets) {
        this.tickets = tickets;
    }
}
