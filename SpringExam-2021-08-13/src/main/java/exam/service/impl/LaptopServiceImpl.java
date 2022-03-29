package exam.service.impl;

import com.google.gson.Gson;
import exam.model.Laptop;
import exam.model.Shop;
import exam.model.dto.json.CustomerDTO;
import exam.model.dto.json.LaptopDTO;
import exam.repository.CustomerRepository;
import exam.repository.LaptopRepository;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.LaptopService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class LaptopServiceImpl implements LaptopService {
    private TownRepository townRepository;
    private ShopRepository shopRepository;
    private LaptopRepository laptopRepository;


    private final String path = "src/main/resources/files/json/laptops.json";

    private ModelMapper modelMapper;
    private Validator validator;
    private Gson gson;


    public LaptopServiceImpl(TownRepository townRepository, ShopRepository shopRepository, LaptopRepository laptopRepository, ModelMapper modelMapper, Validator validator, Gson gson) {
        this.townRepository = townRepository;
        this.shopRepository = shopRepository;
        this.laptopRepository = laptopRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(Path.of(path));
    }

    @Override
    public String importLaptops() throws IOException {

        String json = this.readLaptopsFileContent();
        StringBuilder sb = new StringBuilder();

        LaptopDTO[] laptopDTOS = gson.fromJson(json,LaptopDTO[].class);
        for (LaptopDTO laptopInfo : laptopDTOS) {

            Set<ConstraintViolation<LaptopDTO>> errors = validator.validate(laptopInfo);

            if (errors.isEmpty()){
                Optional<Shop> shop = shopRepository.findByName(laptopInfo.getShop().getName());
                Optional<Laptop> checked = laptopRepository.findByMacAddress(laptopInfo.getMacAddress());
                if (shop.isPresent() && checked.isEmpty()){
                    Laptop laptop = modelMapper.map(laptopInfo,Laptop.class);

                    laptop.setShop(shop.get());

                    sb.append(String.format("Successfully imported Laptop %s - %.2f - %d - %d",laptop.getMacAddress(),laptop.getCpuSpeed(),laptop.getRam(),laptop.getStorage()));

                    laptopRepository.save(laptop);


                }
                else {
                    sb.append("Invalid Laptop");
                }
            }
            else {
                sb.append("Invalid Laptop");
            }
                sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public String exportBestLaptops() {
        StringBuilder sb = new StringBuilder();

      List<Laptop> laptops = laptopRepository.findAllOrderByCpuSpeedAndRamAndStorage();

        for (Laptop laptop :
                laptops) {
            sb.append(String.format("Laptop - %s\n" +
                    "*Cpu speed - %.2f\n" +
                    "**Ram - %d\n" +
                    "***Storage - %d\n" +
                    "****Price - %.2f\n" +
                    "#Shop name - %s\n" +
                    "##Town - %s\n",
                    laptop.getMacAddress(),
                    laptop.getCpuSpeed(),
                    laptop.getRam(),
                    laptop.getStorage(),
                    laptop.getPrice(),
                    laptop.getShop().getName(),
                    laptop.getShop().getTown().getName())
            );
        }

        return sb.toString();
    }
}
