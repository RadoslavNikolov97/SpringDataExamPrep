package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.Pictures;
import softuni.exam.instagraphlite.models.Posts;
import softuni.exam.instagraphlite.models.Users;
import softuni.exam.instagraphlite.models.dto.jsonDTO.UserDTO;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.UserService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private PictureRepository pictureRepository;
    private UserRepository userRepository;
    private PostRepository postRepository;


    private Gson gson;
    private ModelMapper modelMapper;
    private Validator validator;

    private final String path = "src/main/resources/files/users.json";

    public UserServiceImpl(PictureRepository pictureRepository, UserRepository userRepository, PostRepository postRepository, Gson gson, ModelMapper modelMapper, Validator validator) {
        this.pictureRepository = pictureRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;

        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(path));
    }

    @Override
    public String importUsers() throws IOException {

        String json = this.readFromFileContent();
        StringBuilder sb = new StringBuilder();

        UserDTO[] userDTOS = gson.fromJson(json,UserDTO[].class);

        for (UserDTO userInfo : userDTOS) {
            Set<ConstraintViolation<UserDTO>> errors = validator.validate(userInfo);

            if (errors.isEmpty()){
                Optional<Pictures> picture = pictureRepository.findByPathEquals(userInfo.getProfilePicture());

                if (picture.isPresent()){
                    Users user = modelMapper.map(userInfo, Users.class);

                    sb.append(String.format("Successfully imported User: %s",user.getUsername()));

                    userRepository.save(user);

                }

            }

            else {
                sb.append("Invalid user");
            }

            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public String exportUsersWithTheirPosts() {
        List<Users> users = userRepository.findAllOrderByPostCountAsc();


        StringBuilder sb = new StringBuilder();
        for (Users user : users) {
            List<Posts> posts = postRepository.findAllByUserIdOrderByPictureSize(user.getId());
            sb.append("User: ")
                    .append(user.getUsername())
                    .append(System.lineSeparator())
                    .append("Posts count: ")
                    .append(posts.size())
                    .append(System.lineSeparator())
                    .append(posts.toString());



        }

        return sb.toString();
    }
}
