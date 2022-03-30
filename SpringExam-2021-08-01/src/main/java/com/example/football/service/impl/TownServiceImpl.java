package com.example.football.service.impl;

import com.example.football.models.dto.json.TownDTO;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;


@Service
public class TownServiceImpl implements TownService {

    private TownRepository townRepository;

    private Gson gson;
    private ModelMapper modelMapper;
    private Validator validator;

    private final String path = "src/main/resources/files/json/towns.json";

    public TownServiceImpl(TownRepository townRepository, Gson gson, ModelMapper modelMapper, Validator validator) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported()  {
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

        for (TownDTO townInfo : townDTOS) {
            Set<ConstraintViolation<TownDTO>> errors = validator.validate(townInfo);

            if (errors.isEmpty()){
             Optional<Town> checked = townRepository.findByName(townInfo.getName());
             if (checked.isEmpty()){
                 Town town = modelMapper.map(townInfo,Town.class);

                 sb.append(String.format("Successfully imported Town %s - %d",town.getName(),town.getPopulation()));

                 townRepository.save(town);
             }
             else {
                 sb.append("Invalid Town");
             }
            }
            else {
                sb.append("Invalid town");
            }
                sb.append(System.lineSeparator());
        }

        return sb.toString();
    }
}
