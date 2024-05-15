package ru.manannikov.cafeBarBusinessSystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.manannikov.cafeBarBusinessSystem.model.ProductEntity;
import ru.manannikov.cafeBarBusinessSystem.service.ProductService;

@SpringBootApplication
public class CafeBarBusinessSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CafeBarBusinessSystemApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(ProductService service) {
		return args -> {
			service.save(new ProductEntity(
				"Иван чай", "Давно забытый, вкусный и полезный чай."
			));
		};
	}

}
