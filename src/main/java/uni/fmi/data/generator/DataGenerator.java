package uni.fmi.data.generator;

import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import uni.fmi.data.entity.SamplePerson;
import uni.fmi.data.entity.UserEntity;
import uni.fmi.data.repo.SamplePersonRepository;
import uni.fmi.data.service.UserService;

@SpringComponent
public class DataGenerator {

	@Bean
	public CommandLineRunner loadData(SamplePersonRepository samplePersonRepository, final UserService userService) {
		return args -> {
			Logger logger = LoggerFactory.getLogger(getClass());
			int seed = 123;
			if (samplePersonRepository.count() == 0L) {
				logger.info("Using existing database");

				logger.info("Generating demo data");

				logger.info("... generating 100 Sample Person entities...");
				ExampleDataGenerator<SamplePerson> samplePersonRepositoryGenerator = new ExampleDataGenerator<>(
						SamplePerson.class, LocalDateTime.of(2021, 12, 4, 0, 0, 0));
				samplePersonRepositoryGenerator.setData(SamplePerson::setId, DataType.ID);
				samplePersonRepositoryGenerator.setData(SamplePerson::setFirstName, DataType.FIRST_NAME);
				samplePersonRepositoryGenerator.setData(SamplePerson::setLastName, DataType.LAST_NAME);
				samplePersonRepositoryGenerator.setData(SamplePerson::setEmail, DataType.EMAIL);
				samplePersonRepositoryGenerator.setData(SamplePerson::setPhone, DataType.PHONE_NUMBER);
				samplePersonRepositoryGenerator.setData(SamplePerson::setDateOfBirth, DataType.DATE_OF_BIRTH);
				samplePersonRepositoryGenerator.setData(SamplePerson::setOccupation, DataType.OCCUPATION);
				samplePersonRepositoryGenerator.setData(SamplePerson::setImportant, DataType.BOOLEAN_10_90);
				samplePersonRepository.saveAll(samplePersonRepositoryGenerator.create(100, seed));

			}

			if (userService.count() == 0L) {
				ExampleDataGenerator<UserEntity> userRepositoryGenerator = new ExampleDataGenerator<>(UserEntity.class,
						LocalDateTime.of(2021, 12, 4, 0, 0, 0));
				userRepositoryGenerator.setData(UserEntity::setEmail, DataType.EMAIL);
				userRepositoryGenerator.setData(UserEntity::setPassword, DataType.WORD);
				userRepositoryGenerator.setData(UserEntity::setUsername, DataType.FIRST_NAME);
				userRepositoryGenerator.setData(UserEntity::setAvatarLocation, DataType.EMAIL);
				userRepositoryGenerator.setData(UserEntity::setId, DataType.ID);
				userService.saveAll(userRepositoryGenerator.create(20, seed));
			}

			logger.info("Generated demo data");
		};
	}

}