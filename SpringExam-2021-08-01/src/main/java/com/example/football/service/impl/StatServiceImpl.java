package com.example.football.service.impl;

import com.example.football.models.dto.xml.StatsDTO;
import com.example.football.models.dto.xml.StatsRootDTO;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.StatService;
import com.google.gson.Gson;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StatServiceImpl implements StatService {

    private StatRepository statRepository;


    private ModelMapper modelMapper;
    private Validator validator;

    private final String path = "src/main/resources/files/xml/stats.xml";

    public StatServiceImpl(StatRepository statRepository, ModelMapper modelMapper, Validator validator) {
        this.statRepository = statRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(Path.of(path));
    }

    @Override
    public String importStats() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(StatsRootDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        StatsRootDTO statsRootDTO = (StatsRootDTO) unmarshaller.unmarshal(new FileReader(path));




        return statsRootDTO.getStats().stream().map(this::importStat).collect(Collectors.joining("\n"));

    }

    private  String importStat(StatsDTO statsDTO) {

        Set<ConstraintViolation<StatsDTO>> errors = validator.validate(statsDTO);

        if (errors.isEmpty()) {
        Optional<Stat> checked = statRepository.findBYShootingPassingEndurance(statsDTO.getShooting(),statsDTO.getPassing(),statsDTO.getEndurance());

            if (checked.isEmpty()) {
                Stat stat = modelMapper.map(statsDTO,Stat.class);

                statRepository.save(stat);

                return String.format("Successfully imported Stat %.2f - %.2f - %.2f",stat.getShooting(),stat.getPassing(),stat.getEndurance());
            }
            else {
                return "Invalid Stats";
            }
        }
        else {
            return "Invalid Stats";
        }

    }
}
