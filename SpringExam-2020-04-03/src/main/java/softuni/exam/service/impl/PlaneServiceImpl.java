package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Plane;
import softuni.exam.models.dto.xml.planes.PlaneDTO;
import softuni.exam.models.dto.xml.planes.PlaneRootDTO;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.service.PlaneService;

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
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlaneServiceImpl implements PlaneService {

    private PlaneRepository planeRepository;

    private final String path = "src/main/resources/files/xml/planes.xml";

    private ModelMapper modelMapper;
    private Validator validator;

    public PlaneServiceImpl(PlaneRepository planeRepository, ModelMapper modelMapper, Validator validator) {
        this.planeRepository = planeRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return planeRepository.count() > 0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {
        return Files.readString(Path.of(path));

    }

    @Override
    public String importPlanes() throws JAXBException, FileNotFoundException {

        JAXBContext context = JAXBContext.newInstance(PlaneRootDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        PlaneRootDTO planeRootDTO = (PlaneRootDTO) unmarshaller.unmarshal(new FileReader(path));




        return planeRootDTO.getPlanes().stream().map(this::importPlane).collect(Collectors.joining("\n"));

    }

    private  String importPlane(PlaneDTO planeDTO) {

        Set<ConstraintViolation<PlaneDTO>> errors = validator.validate(planeDTO);

        if (errors.isEmpty()){

            Plane plane = modelMapper.map(planeDTO,Plane.class);

            planeRepository.save(plane);

            return  String.format("Successfully imported Plane %s",plane.getRegistrationNumber());
        }

        else {
            return "Invalid Plane";
        }

    }
}
