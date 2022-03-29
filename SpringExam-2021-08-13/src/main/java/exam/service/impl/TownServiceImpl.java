package exam.service.impl;

import exam.model.Town;
import exam.model.dto.xml.town.TownDTO;
import exam.model.dto.xml.town.TownRootDTO;
import exam.repository.TownRepository;
import exam.service.TownService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
public class TownServiceImpl implements TownService {
    private TownRepository townRepository;

    private final String path = "src/main/resources/files/xml/towns.xml";

    private ModelMapper modelMapper;
    private Validator validator;

    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, Validator validator) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(path));
    }

    @Override
    public String importTowns() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(TownRootDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        TownRootDTO townRootDTO = (TownRootDTO) unmarshaller.unmarshal(new FileReader(path));




        return townRootDTO.getTowns().stream().map(this::importTown).collect(Collectors.joining("\n"));



    }

    private  String importTown(TownDTO townDTO) {


        Set<ConstraintViolation<TownDTO>> errors = validator.validate(townDTO);

        if (errors.isEmpty()){

            Town town = modelMapper.map(townDTO,Town.class);

            townRepository.save(town);

            return String.format("Successfully imported Town %s",town.getName());
        }
        else {
            return "Invalid Town";
        }
    }
}
