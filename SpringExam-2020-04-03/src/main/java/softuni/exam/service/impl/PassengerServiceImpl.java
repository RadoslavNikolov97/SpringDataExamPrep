package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Passenger;
import softuni.exam.models.Town;
import softuni.exam.models.dto.json.PassengersDTO;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.PassengerService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PassengerServiceImpl implements PassengerService {


    private TownRepository townRepository;
    private PassengerRepository passengerRepository;

    private final String path = "src/main/resources/files/json/passengers.json";

    private Gson gson;
    private ModelMapper modelMapper;
    private Validator validator;

    public PassengerServiceImpl(TownRepository townRepository, PassengerRepository passengerRepository, Gson gson, ModelMapper modelMapper, Validator validator) {
        this.townRepository = townRepository;
        this.passengerRepository = passengerRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return Files.readString(Path.of(path));
    }

    @Override
    public String importPassengers() throws IOException {
        String json = this.readPassengersFileContent();
        StringBuilder sb = new StringBuilder();

        PassengersDTO[] passengersDTOS = gson.fromJson(json,PassengersDTO[].class);

        for (PassengersDTO passengerInfo : passengersDTOS ) {

            Set<ConstraintViolation<PassengersDTO>> errors = validator.validate(passengerInfo);

            if (errors.isEmpty()){
                Optional<Town> town = townRepository.findByName(passengerInfo.getTown());
                if (town.isPresent()) {
                    Passenger passenger = new Passenger();

                    String firstName = passengerInfo.getFirstName();
                    String lastName = passengerInfo.getLastName();
                    Integer age = passengerInfo.getAge();
                    String email = passengerInfo.getEmail();
                    String phoneNumber = passengerInfo.getPhoneNumber();

                    passenger.setFirstName(firstName);
                    passenger.setLastname(lastName);
                    passenger.setAge(age);
                    passenger.setEmail(email);
                    passenger.setPhoneNumber(phoneNumber);
                    passenger.setTown(town.get());


                    sb.append(String.format("Successfully imported Passenger %s - %s",passenger.getLastname(),passenger.getEmail()));


                    passengerRepository.save(passenger);
                }
            }
            else {
                sb.append("Invalid Town");
            }
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {

        List<Passenger> passengers = passengerRepository.findAllOrderByTicketsCountDescAndByEmailDesc();
        StringBuilder sb = new StringBuilder();

        for (Passenger passenger : passengers) {

            int countOfTickets = passengerRepository.countTicketsByPassengerId(passenger.getId());

            sb.append(String.format("Passenger %s  %s\n \tEmail - %s\n Phone - %s\n \tNumber of tickets - %d\n",
                    passenger.getFirstName(),
                    passenger.getLastname(),
                    passenger.getEmail(),
                    passenger.getPhoneNumber(),
                    countOfTickets));
        }

        return sb.toString();
    }
}
