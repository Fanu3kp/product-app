package com.productapp.backend.config;

import com.productapp.backend.entity.Product;
import com.productapp.backend.entity.Role;
import com.productapp.backend.entity.User;
import com.productapp.backend.repository.ProductRepository;
import com.productapp.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.math.BigDecimal;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(UserRepository userRepository, ProductRepository productRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setEmail("admin@productapp.com");
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);

                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setEmail("user@productapp.com");
                user.setRole(Role.USER);
                userRepository.save(user);
            }

            if (productRepository.count() == 0) {
                Product p1 = new Product();
                p1.setName("Wireless Mouse");
                p1.setDescription("Ergonomic wireless mouse with USB receiver");
                p1.setPrice(new BigDecimal("3.33"));
                p1.setCategory("Electronics");
                p1.setStock(50);
                p1.setImageUrl("http://localhost:8080/uploads/wireless-mouse.jpg");
                productRepository.save(p1);

                Product p2 = new Product();
                p2.setName("Mechanical Keyboard");
                p2.setDescription("Tactile mechanical keyboard with RGB backlight");
                p2.setPrice(new BigDecimal("89.99"));
                p2.setCategory("Electronics");
                p2.setStock(30);
                p2.setImageUrl("http://localhost:8080/uploads/mechanical-keyboard.jpg");
                productRepository.save(p2);

                Product p3 = new Product();
                p3.setName("USB-C Hub");
                p3.setDescription("7-in-1 USB-C hub with HDMI and card reader");
                p3.setPrice(new BigDecimal("13.33"));
                p3.setCategory("Electronics");
                p3.setStock(40);
                p3.setImageUrl("http://localhost:8080/uploads/usb-c-hub.jpg");
                productRepository.save(p3);

                Product p4 = new Product();
                p4.setName("Coffee Maker");
                p4.setDescription("Programmable drip coffee maker, 12-cup");
                p4.setPrice(new BigDecimal("23.33"));
                p4.setCategory("Home");
                p4.setStock(25);
                p4.setImageUrl("http://localhost:8080/uploads/coffee-maker.jpg");
                productRepository.save(p4);

                Product p5 = new Product();
                p5.setName("Yoga Mat");
                p5.setDescription("Non-slip exercise yoga mat, 6mm thick");
                p5.setPrice(new BigDecimal("6.67"));
                p5.setCategory("Sports");
                p5.setStock(60);
                p5.setImageUrl("http://localhost:8080/uploads/yoga-mat.jpg");
                productRepository.save(p5);

                Product p6 = new Product();
                p6.setName("Bluetooth Speaker");
                p6.setDescription("Portable waterproof Bluetooth speaker");
                p6.setPrice(new BigDecimal("39.99"));
                p6.setCategory("Electronics");
                p6.setStock(35);
                p6.setImageUrl("http://localhost:8080/uploads/bluetooth-speaker.jpg");
                productRepository.save(p6);

                Product p7 = new Product();
                p7.setName("Smartwatch");
                p7.setDescription("Fitness tracking smartwatch with heart rate monitor");
                p7.setPrice(new BigDecimal("26.67"));
                p7.setCategory("Electronics");
                p7.setStock(20);
                p7.setImageUrl("http://localhost:8080/uploads/smartwatch.jpg");
                productRepository.save(p7);

                Product p8 = new Product();
                p8.setName("Backpack");
                p8.setDescription("Water-resistant travel backpack with laptop compartment");
                p8.setPrice(new BigDecimal("6.67"));
                p8.setCategory("Sports");
                p8.setStock(30);
                p8.setImageUrl("http://localhost:8080/uploads/backpack.jpg");
                productRepository.save(p8);

                Product p9 = new Product();
                p9.setName("Running Shoes");
                p9.setDescription("Lightweight running shoes for daily training");
                p9.setPrice(new BigDecimal("3.67"));
                p9.setCategory("Sports");
                p9.setStock(45);
                p9.setImageUrl("http://localhost:8080/uploads/running-shoes.jpg");
                productRepository.save(p9);

                Product p10 = new Product();
                p10.setName("Desk Lamp");
                p10.setDescription("LED desk lamp with adjustable brightness");
                p10.setPrice(new BigDecimal("2.00"));
                p10.setCategory("Home");
                p10.setStock(40);
                p10.setImageUrl("http://localhost:8080/uploads/desk-lamp.jpg");
                productRepository.save(p10);

                Product p11 = new Product();
                p11.setName("Cookware Set");
                p11.setDescription("Non-stick 10-piece cookware set");
                p11.setPrice(new BigDecimal("40.00"));
                p11.setCategory("Home");
                p11.setStock(15);
                p11.setImageUrl("http://localhost:8080/uploads/cookware-set.jpg");
                productRepository.save(p11);

                Product p12 = new Product();
                p12.setName("Sunglasses");
                p12.setDescription("Polarized UV protection sunglasses");
                p12.setPrice(new BigDecimal("10.00"));
                p12.setCategory("Sports");
                p12.setStock(55);
                p12.setImageUrl("http://localhost:8080/uploads/sunglasses.jpg");
                productRepository.save(p12);
            }
        };
    }
}
