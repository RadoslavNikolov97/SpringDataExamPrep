package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Car;
import softuni.exam.models.Picture;
import softuni.exam.models.dto.jsonDTO.CarDTO;
import softuni.exam.models.dto.jsonDTO.PictureDTO;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.PictureService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

@Service
public class PictureServiceImpl implements PictureService {

    private PictureRepository pictureRepository;
    private CarRepository carRepository;

    private final String path = "src/main/resources/files/json/pictures.json";

    private Gson gson;
    private ModelMapper modelMapper;
    private Validator validator;

    public PictureServiceImpl(PictureRepository pictureRepository, CarRepository carRepository,Gson gson, ModelMapper modelMapper, Validator validator) {
        this.pictureRepository = pictureRepository;
        this.carRepository = carRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return Files.readString(Path.of(path));
    }

    @Override
    public String importPictures() throws IOException {
        String json = this.readPicturesFromFile();
        StringBuilder sb = new StringBuilder();

        PictureDTO[] pictureDTOS = gson.fromJson(json,PictureDTO[].class);

        for (PictureDTO pictureDTO : pictureDTOS) {
            Set<ConstraintViolation<PictureDTO>> errors = validator.validate(pictureDTO);
            if (errors.isEmpty()){
                Optional<Car> car = this.carRepository.findById(pictureDTO.getCar());
                if (car.isPresent()){
                    Picture picture = modelMapper.map(pictureDTO,Picture.class);
                    picture.setCar(car.get());
                    sb.append(String.format("Successfully import picture - %s",picture.getName()));
                    pictureRepository.save(picture);
                }
                else {
                    sb.append("Invalid Picture");
                }
            }
            else {
                sb.append("Invalid Picture");
            }
            sb.append(System.lineSeparator());

        }

        return sb.toString();
    }
}
