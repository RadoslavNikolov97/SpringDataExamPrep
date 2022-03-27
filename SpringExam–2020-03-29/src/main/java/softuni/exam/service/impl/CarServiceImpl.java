package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Car;
import softuni.exam.models.dto.jsonDTO.CarDTO;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

@Service
public class CarServiceImpl implements CarService {

    private CarRepository carRepository;

    private final String path = "src/main/resources/files/json/cars.json";

    private Gson gson;
    private ModelMapper modelMapper;
    private Validator validator;


    public CarServiceImpl(CarRepository carRepository, Gson gson, ModelMapper modelMapper, Validator validator) {
        this.carRepository = carRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return Files.readString(Path.of(path));
    }

    @Override
    public String importCars() throws IOException {
        String json = this.readCarsFileContent();
        StringBuilder sb = new StringBuilder();

        CarDTO[] carDTOS = gson.fromJson(json,CarDTO[].class);

        for (CarDTO carDTO : carDTOS) {
            Set<ConstraintViolation<CarDTO>> errors = validator.validate(carDTO);
            if (errors.isEmpty()) {
                Car car = modelMapper.map(carDTO,Car.class);
                sb.append(String.format("Successfully imported car - %s - %s",car.getMake(),car.getModel()));
                carRepository.save(car);
            }
            else {
                sb.append("Invalid Car");
            }
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
      List<Car> cars = carRepository.findAllOrderByCountByPictureIdDescAndMakeDesc();

      StringBuilder sb = new StringBuilder();
        for (Car car : cars) {
            int carCount = carRepository.countByPictureIdWhereCarIdEquals(car.getId());
            sb.append(String.format("Car make - %s, model - %s\n" +
                    "\tKilometers - %d\n" +
                    "\tRegistered on - %s\n" +
                    "\tNumber of pictures - %d\n",
                    car.getMake(),
                    car.getModel(),
                    car.getKilometers(),
                    car.getRegisteredOn(),
                    carCount))
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }
}
