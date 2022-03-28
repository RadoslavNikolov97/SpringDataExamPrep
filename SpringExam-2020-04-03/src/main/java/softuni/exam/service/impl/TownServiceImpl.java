package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Town;
import softuni.exam.models.dto.json.TownDTO;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

@Service
public class TownServiceImpl implements TownService {

    private TownRepository townRepository;

    private final String path = "src/main/resources/files/json/towns.json";

    private Gson gson;
    private ModelMapper modelMapper;
    private Validator validator;

    public TownServiceImpl(TownRepository townRepository, Gson gson, ModelMapper modelMapper, Validator validator) {
        this.townRepository = townRepository;
        this.gson = gson;
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
    public String importTowns() throws IOException {
        String json = this.readTownsFileContent();
        StringBuilder sb = new StringBuilder();

        TownDTO[] townDTOS = gson.fromJson(json,TownDTO[].class);

        for (TownDTO townInfo : townDTOS ) {

            Set<ConstraintViolation<TownDTO>> errors = validator.validate(townInfo);

            if (errors.isEmpty()){
                Town town = modelMapper.map(townInfo , Town.class);

                sb.append(String.format("Successfully imported Town %s - %d",town.getName(),town.getPopulation()));

                townRepository.save(town);
            }
            else {
                sb.append("Invalid Town");
            }
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }
}
