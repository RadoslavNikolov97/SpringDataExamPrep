package exam.service.impl;

import exam.model.Shop;
import exam.model.Town;
import exam.model.dto.xml.shop.ShopDTO;
import exam.model.dto.xml.shop.ShopRootDTO;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.ShopService;
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
public class ShopServiceImpl implements ShopService {
    private TownRepository townRepository;
    private ShopRepository shopRepository;

    private final String path = "src/main/resources/files/xml/shops.xml";

    private ModelMapper modelMapper;
    private Validator validator;

    public ShopServiceImpl(TownRepository townRepository, ShopRepository shopRepository, ModelMapper modelMapper, Validator validator) {
        this.townRepository = townRepository;
        this.shopRepository = shopRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(Path.of(path));
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(ShopRootDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ShopRootDTO shopRootDTO = (ShopRootDTO) unmarshaller.unmarshal(new FileReader(path));




        return shopRootDTO.getShops().stream().map(this::importShop).collect(Collectors.joining("\n"));


    }

    private String importShop(ShopDTO shopDTO) {


        Set<ConstraintViolation<ShopDTO>> errors = validator.validate(shopDTO);

        if (errors.isEmpty()){

           Optional<Town> town = townRepository.findByName(shopDTO.getTownNameDTO().getName());

           // here we check if the shop is already in the database. if it is we will skip this phase , if not we will save it in the database
           Optional<Shop> checked = shopRepository.findByName(shopDTO.getName());

           if (town.isPresent() && checked.isEmpty()){


               Shop shop = new Shop();

               String name = shopDTO.getName();
               int shopArea = shopDTO.getShopArea();
               String address = shopDTO.getAddress();
               int employeeCount = shopDTO.getEmployeeCount();
               double income = shopDTO.getIncome();

               shop.setName(name);
               shop.setShopArea(shopArea);
               shop.setAddress(address);
               shop.setEmployeeCount(employeeCount);
               shop.setIncome(income);
               shop.setTown(town.get());

               shopRepository.save(shop);


               return String.format("Successfully imported Shop %s - %.0f",shop.getName(),shop.getIncome());
           }
            else {
                return "Invalid Shop";
           }
        }
        else {
            return "Invalid Shop";
        }
    }
}
