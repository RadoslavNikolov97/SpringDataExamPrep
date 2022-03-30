package com.example.football.service.impl;

import com.example.football.models.dto.xml.PlayerDTO;
import com.example.football.models.dto.xml.PlayerRootDTO;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.models.enums.Position;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {


    private StatRepository statRepository;
    private TownRepository townRepository;
    private TeamRepository teamRepository;
    private PlayerRepository playerRepository;


    private ModelMapper modelMapper;
    private Validator validator;

    private final String path = "src/main/resources/files/xml/players.xml";

    public PlayerServiceImpl(StatRepository statRepository, TownRepository townRepository, TeamRepository teamRepository, PlayerRepository playerRepository, ModelMapper modelMapper, Validator validator) {
        this.statRepository = statRepository;
        this.townRepository = townRepository;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;

        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(path));
    }

    @Override
    public String importPlayers() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(PlayerRootDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        PlayerRootDTO playerRootDTO = (PlayerRootDTO) unmarshaller.unmarshal(new FileReader(path));




        return playerRootDTO.getPlayers().stream().map(this::importPlayer).collect(Collectors.joining("\n"));

    }

    private String importPlayer(PlayerDTO playerDTO) {

        Set<ConstraintViolation<PlayerDTO>> errors = validator.validate(playerDTO);

        if (errors.isEmpty()) {

          Optional<Player> checked = playerRepository.findByFirstNameAndLastName(playerDTO.getFirstName(),playerDTO.getLastName());
            Optional<Team> team = teamRepository.findByName(playerDTO.getTeamNameDTO().getName());
            Optional<Town> town = townRepository.findByName(playerDTO.getTownNameDTO().getName());
            Optional<Stat> stats = statRepository.findById(playerDTO.getStatIdDTO().getId());

            if (team.isPresent() && town.isPresent() && stats.isPresent() && checked.isEmpty()) {

                Player player = new Player();

                String firstName = playerDTO.getFirstName();
                String lastName = playerDTO.getLastName();
                LocalDate birth = modelMapper.map(playerDTO.getBirth(),LocalDate.class) ;
                String email = playerDTO.getEmail();
                Position position = modelMapper.map(playerDTO.getPosition(),Position.class);

                player.setFirstName(firstName);
                player.setLastName(lastName);
                player.setBirth(birth);
                player.setEmail(email);
                player.setPosition(position);
                player.setStats(stats.get());
                player.setTown(town.get());
                player.setTeam(team.get());


                playerRepository.save(player);

                return String.format("Successfully imported Player %s %s - %s",player.getFirstName(),player.getLastName(),player.getPosition());

            }
            else {
                return "Invalid Player";
            }
        }
        else {
            return "Invalid Player";
        }
    }

    @Override
    public String exportBestPlayers() {

        StringBuilder sb = new StringBuilder();

        LocalDate before = LocalDate.of(2003, 1, 1);
        LocalDate after = LocalDate.of(1995, 1, 1);

        List<Player> players = this.playerRepository.findAllByBirthAfterAndBirthBefore(after, before);

        for (Player player : players) {
            sb.append(String.format("Player - %s %s\n" +
                    "\tPosition - %s\n" +
                    "Team - %s\n" +
                    "\tStadium - %s\n",
                    player.getFirstName()
                    ,player.getLastName()
                    ,player.getPosition()
                    ,player.getTeam().getName()
                    ,player.getTeam().getStadiumName()));
        }


        return sb.toString();
    }
}
