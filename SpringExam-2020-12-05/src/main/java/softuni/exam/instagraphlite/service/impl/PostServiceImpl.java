package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.Pictures;
import softuni.exam.instagraphlite.models.Posts;
import softuni.exam.instagraphlite.models.Users;
import softuni.exam.instagraphlite.models.dto.xmlDTO.PostDTO;
import softuni.exam.instagraphlite.models.dto.xmlDTO.PostRootDTO;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PostService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PictureRepository pictureRepository;
    private UserRepository userRepository;
    private PostRepository postRepository;

    private ModelMapper modelMapper;
    private Validator validator;

    private final String path = "src/main/resources/files/posts.xml";

    public PostServiceImpl(PictureRepository pictureRepository, UserRepository userRepository, PostRepository postRepository, ModelMapper modelMapper, Validator validator) {
        this.pictureRepository = pictureRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(path));
    }

    @Override
    public String importPosts() throws IOException, JAXBException {

        JAXBContext context = JAXBContext.newInstance(PostRootDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        PostRootDTO postRootDTO = (PostRootDTO) unmarshaller.unmarshal(new FileReader(path));


        return postRootDTO.getPosts().stream().map(this::importPost).collect(Collectors.joining("\n"));

    }

    private  String importPost(PostDTO postDTO) {

        Set<ConstraintViolation<PostDTO>> errors = validator.validate(postDTO);

        if (errors.isEmpty()){
            Optional<Pictures> picture = pictureRepository.findByPathEquals(postDTO.getPicture().getPath());
            Optional<Users> user = userRepository.findByUsernameEquals(postDTO.getUser().getUsername());

            if (picture.isPresent() && user.isPresent()){
                Posts post = new Posts();

                String caption = postDTO.getCaption();

                post.setCaption(caption);
                post.setUser(user.get());
                post.setPicture(picture.get());

                postRepository.save(post);

                return String.format("Successfully imported Post, made by %s",post.getUser().getUsername());
            }
        }
            return "Invalid Post";

    }
}
