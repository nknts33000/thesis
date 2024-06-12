package com.example.platform.data;

import com.example.platform.dto.RegistrationDTO;
import com.example.platform.exceptions.UserExistsException;
import com.example.platform.exceptions.UserNotFoundException;
import com.example.platform.model.Advert;
import com.example.platform.model.Company;
import com.example.platform.model.User;
import com.example.platform.repo.AdvertRepo;
import com.example.platform.repo.CompanyRepo;
import com.example.platform.repo.UserRepo;
import com.example.platform.service.UserService;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final CompanyRepo companyRepository;
    private final AdvertRepo advertRepository;

    private final UserService userService;

    public DataLoader(CompanyRepo companyRepository, AdvertRepo advertRepository, UserService userService) {
        this.companyRepository = companyRepository;
        this.advertRepository = advertRepository;
        this.userService=userService;
    }

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("user_data.txt"))) {
            // Create Users
            for (int i = 0; i < 100; i++) {
                String firstName = faker.name().firstName();
                String lastName = faker.name().lastName();
                String email = faker.internet().emailAddress();
                String password = faker.internet().password();
                String location = faker.address().city();
                //String roles = "USER";

                //User user = new User(email, password, firstName, lastName, location);
                //user.setRoles(roles);
//                userRepository.save(user);
                userService.addUser(
                        new RegistrationDTO(email,password,firstName,lastName,location)
                );

                User user=userService.getUser(email);

                // Write user data to file
                writer.write(String.format("User %d: %s %s, Email: %s, Password: %s, Location: %s", i + 1, firstName, lastName, email, password, location + "\n"));

                // Create Companies for the User
                int numOfCompanies = faker.number().numberBetween(1, 5);
                for (int j = 0; j < numOfCompanies; j++) {
                    String companyName = faker.company().name();
                    String mission = faker.company().catchPhrase();

                    Company company = new Company(companyName, mission, user);
                    companyRepository.save(company);

                    // Write company data to file
                   // writer.write(String.format("  Company %d: %s, Mission: %s%n", j + 1, companyName, mission));

                    // Create Adverts for the Company
                    int numOfAdverts = faker.number().numberBetween(1, 10);
                    Set<Advert> adverts = new HashSet<>();
                    for (int k = 0; k < numOfAdverts; k++) {
                        String jobTitle = faker.job().title();
                        String jobSummary = faker.lorem().paragraph();
                        String jobLocation = faker.address().city();
                        String contactInformation = faker.internet().emailAddress();

                        Advert advert = new Advert(jobTitle, jobSummary, jobLocation, contactInformation, company);
                        adverts.add(advert);
                        advertRepository.save(advert);

                        // Write advert data to file
                        //writer.write(String.format("    Advert %d: Job Title: %s, Job Summary: %s, Location: %s, Contact Information: %s%n", k + 1, jobTitle, jobSummary, jobLocation, contactInformation));
                    }

                    company.setAdverts(adverts);
                    companyRepository.save(company);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UserExistsException e) {
            throw new RuntimeException(e);
        }
    }
}
