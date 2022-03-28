package softuni.exam.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Passenger;
import softuni.exam.models.Plane;
import softuni.exam.models.Ticket;
import softuni.exam.models.Town;
import softuni.exam.models.dto.xml.planes.PlaneDTO;
import softuni.exam.models.dto.xml.planes.PlaneRootDTO;
import softuni.exam.models.dto.xml.tickets.TicketDTO;
import softuni.exam.models.dto.xml.tickets.TicketRootDTO;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.repository.TicketRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TicketService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    private TownRepository townRepository;
    private PassengerRepository passengerRepository;
    private PlaneRepository planeRepository;
    private TicketRepository ticketRepository;

    private final String path = "src/main/resources/files/xml/tickets.xml";

    private ModelMapper modelMapper;
    private Validator validator;

    public TicketServiceImpl(TownRepository townRepository, PassengerRepository passengerRepository, PlaneRepository planeRepository, TicketRepository ticketRepository, ModelMapper modelMapper, Validator validator) {
        this.townRepository = townRepository;
        this.passengerRepository = passengerRepository;
        this.planeRepository = planeRepository;
        this.ticketRepository = ticketRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return ticketRepository.count() >0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return Files.readString(Path.of(path));
    }

    @Override
    public String importTickets() throws JAXBException, FileNotFoundException {

        JAXBContext context = JAXBContext.newInstance(TicketRootDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        TicketRootDTO planeRootDTO = (TicketRootDTO) unmarshaller.unmarshal(new FileReader(path));




        return planeRootDTO.getTickets().stream().map(this::importTicket).collect(Collectors.joining("\n"));


    }

    private  String importTicket(TicketDTO ticketDTO) {


        Set<ConstraintViolation<TicketDTO>> errors = validator.validate(ticketDTO);

        if (errors.isEmpty())
        {
            Optional<Town> fromTown = townRepository.findByName(ticketDTO.getFromTown().getName());
            Optional<Town> toTown = townRepository.findByName(ticketDTO.getToTown().getName());
            Optional<Passenger> passenger = passengerRepository.findByEmail(ticketDTO.getPassenger().getEmail());
            Optional<Plane> plane = planeRepository.findByRegistrationNumber(ticketDTO.getPlane().getRegistrationNumber());

            if (fromTown.isPresent() && toTown.isPresent() && passenger.isPresent() && plane.isPresent())
            {

                double price = ticketDTO.getPrice();
                String serialNumber = ticketDTO.getSerialNumber();
                LocalDateTime takeOff = modelMapper.map(ticketDTO.getTakeOff(),LocalDateTime.class);

                Ticket ticket = new Ticket();

                ticket.setSerialNumber(serialNumber);
                ticket.setPrice(price);
                ticket.setTakeOff(takeOff);
                ticket.setFromTown(fromTown.get());
                ticket.setToTown(toTown.get());
                ticket.setPassenger(passenger.get());
                ticket.setPlane(plane.get());


                ticketRepository.save(ticket);


                return String.format("Successfully imported Ticket %s - %s",ticket.getFromTown().getName(),ticket.getToTown().getName());

            }
            else
            {
                return "Invalid Ticket";
            }
        }
       else
       {
            return "Invalid Ticket";
        }
    }
}
