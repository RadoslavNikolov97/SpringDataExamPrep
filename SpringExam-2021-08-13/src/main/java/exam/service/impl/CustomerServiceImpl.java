package exam.service.impl;


import com.google.gson.Gson;
import exam.model.Customer;
import exam.model.Town;
import exam.model.dto.json.CustomerDTO;
import exam.repository.CustomerRepository;
import exam.repository.TownRepository;
import exam.service.CustomerService;
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
public class CustomerServiceImpl implements CustomerService {
    private TownRepository townRepository;
    private CustomerRepository customerRepository;


    private final String path = "src/main/resources/files/json/customers.json";

    private ModelMapper modelMapper;
    private Validator validator;
    private Gson gson;

    public CustomerServiceImpl(TownRepository townRepository, CustomerRepository customerRepository, ModelMapper modelMapper, Validator validator, Gson gson) {
        this.townRepository = townRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(Path.of(path));
    }

    @Override
    public String importCustomers() throws IOException {

        String json = this.readCustomersFileContent();
        StringBuilder sb = new StringBuilder();

        CustomerDTO[] customerDTOS = gson.fromJson(json,CustomerDTO[].class);

        for (CustomerDTO customerInfo : customerDTOS) {
            Set<ConstraintViolation<CustomerDTO>> errors = validator.validate(customerInfo);

            if (errors.isEmpty()){
                Optional<Town> town = townRepository.findByName(customerInfo.getTown().getName());
                if (town.isPresent()){
                    Customer customer = modelMapper.map(customerInfo,Customer.class);
                    customer.setTown(town.get());

                    sb.append(String.format("Successfully imported Customer %s %s - %s",customer.getFirstName(),customer.getLastName(),customer.getEmail()));

                    customerRepository.save(customer);

                }
                else {
                    sb.append("Invalid Customer");
                }

            }
            else {
                sb.append("Invalid Customer");
            }
            sb.append(System.lineSeparator());

        }

        return sb.toString();
    }
}
