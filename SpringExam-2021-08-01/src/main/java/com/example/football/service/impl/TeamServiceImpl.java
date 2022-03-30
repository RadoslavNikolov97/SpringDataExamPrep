package com.example.football.service.impl;

import com.example.football.models.dto.json.TeamDTO;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
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
public class TeamServiceImpl implements TeamService {
    private TownRepository townRepository;
    private TeamRepository teamRepository;

    private Gson gson;
    private ModelMapper modelMapper;
    private Validator validator;

    private final String path = "src/main/resources/files/json/teams.json";

    public TeamServiceImpl(TownRepository townRepository, TeamRepository teamRepository, Gson gson, ModelMapper modelMapper, Validator validator) {
        this.townRepository = townRepository;
        this.teamRepository = teamRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(path));
    }

    @Override
    public String importTeams() throws IOException {
        String json = readTeamsFileContent();
        StringBuilder sb = new StringBuilder();

        TeamDTO[] teamDTOS = gson.fromJson(json,TeamDTO[].class);

        for (TeamDTO teamInfo :
                teamDTOS) {
            Set<ConstraintViolation<TeamDTO>> errors = validator.validate(teamInfo);

            if (errors.isEmpty()){

              Optional<Team> checked = teamRepository.findByName(teamInfo.getName());
              Optional<Town> town = townRepository.findByName(teamInfo.getTownName());

              if (checked.isEmpty() && town.isPresent()) {
                  Team team = new Team();

                  String name = teamInfo.getName();
                  Integer fanBase = teamInfo.getFanBase();
                  String history = teamInfo.getHistory();
                  String stadiumName = teamInfo.getStadiumName();

                  team.setName(name);
                  team.setFanBase(fanBase);
                  team.setHistory(history);
                  team.setStadiumName(stadiumName);
                  team.setTown(town.get());


                  teamRepository.save(team);

                  sb.append(String.format("Successfully imported Team %s - %d",team.getName(),team.getFanBase()));
              }

              else {
                  sb.append("Invalid Team");
              }

            }
            else {
                sb.append("Invalid Team");
            }
            sb.append(System.lineSeparator());
        }


        return sb.toString();
    }
}
