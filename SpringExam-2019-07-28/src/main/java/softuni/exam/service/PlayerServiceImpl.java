package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Team;
import softuni.exam.domain.entities.dto.PlayerDto;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PlayerServiceImpl implements PlayerService {

    private TeamRepository teamRepository;
    private PictureRepository pictureRepository;
    private PlayerRepository playerRepository;

    private final String path = "src/main/resources/files/json/players.json";

    private Validator validate;
    private Gson gson;
    private ModelMapper modelMapper;
    private FileUtil fileUtil;

    public PlayerServiceImpl(TeamRepository teamRepository, PictureRepository pictureRepository, PlayerRepository playerRepository, Validator validate, Gson gson, ModelMapper modelMapper, FileUtil fileUtil) {
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
        this.playerRepository = playerRepository;
        this.validate = validate;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
    }



    @Override
    public String importPlayers() throws IOException {
       String json = this.readPlayersJsonFile();
       StringBuilder sb = new StringBuilder();

      PlayerDto[] players  = this.gson.fromJson(json,PlayerDto[].class);

        for (PlayerDto playerDTO : players) {
            Set<ConstraintViolation<PlayerDto>> errors = this.validate.validate(playerDTO);
            if (errors.isEmpty()) {
                Optional<Picture> picture = this.pictureRepository.findByUrl(playerDTO.getPicture().getUrl());
                Optional<Team> team = this.teamRepository.findByName(playerDTO.getTeam().getName());
                if (picture.isPresent() && team.isPresent()){
                    Player player = new Player();
                    String firstName = playerDTO.getFirstName();
                    String lastName = playerDTO.getLastName();
                    int number = playerDTO.getNumber();
                    BigDecimal salary = playerDTO.getSalary();
                    String position = playerDTO.getPosition();

                    player.setFirstName(firstName);
                    player.setLastName(lastName);
                    player.setNumber(number);
                    player.setSalary(salary);
                    player.setPosition(position);
                    player.setTeam(team.get());
                    player.setPicture(picture.get());

                    sb.append( String.format("Successfully imported player - %s" ,player.getLastName())).append(System.lineSeparator());

                    this.playerRepository.save(player);
                }

                else {
                    sb.append("Invalid Player").append(System.lineSeparator());
                }
            }
            else {
                sb.append("Invalid Player").append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return fileUtil.readFile(path);
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        StringBuilder sb = new StringBuilder();

        BigDecimal salaryToBeat = BigDecimal.valueOf(100000);

        List<Player> playersWithGreaterSalary = playerRepository.findBySalaryGreaterThanOrderBySalaryDesc(salaryToBeat);

        for (Player playerInfo :
                playersWithGreaterSalary) {
            sb.append(String.format("Player name: %s %s %n Number: %d%n Salary: %.2f%n Team: %s%n",
                    playerInfo.getFirstName(),
                    playerInfo.getLastName(),
                    playerInfo.getNumber(),
                    playerInfo.getSalary(),
                    playerInfo.getTeam().getName())).append(System.lineSeparator());
        }
        
        return sb.toString();
    }

    @Override
    public String exportPlayersInATeam() {
        StringBuilder sb = new StringBuilder();

        String teamToGet = "North Hub";

        List<Player> allPlayersWithTeamName = playerRepository.findByTeamNameLikeOrderById(teamToGet);

        sb.append(String.format("Team: %s",teamToGet)).append(System.lineSeparator());
        for (Player playerInfo :
                allPlayersWithTeamName) {
            sb.append(String.format("Player name: %s %s - %s\n" + "Number: %d\n",
                    playerInfo.getFirstName(),
                    playerInfo.getLastName(),
                    playerInfo.getPosition(),
                    playerInfo.getNumber()))
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }
}
