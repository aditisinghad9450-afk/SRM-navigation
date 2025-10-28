package com.srm.navigation.config;

import com.srm.navigation.entity.Location;
import com.srm.navigation.entity.User;
import com.srm.navigation.repository.LocationRepository;
import com.srm.navigation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public void run(String... args) throws Exception {
        // Pre-load admin user if not exists
        if (userRepository.findByEmail("admin@srm.com").isEmpty()) {
            userRepository.save(new User("admin@srm.com", "12345"));
        }

        // Pre-load locations if database is empty
        if (locationRepository.count() == 0) {
            locationRepository.save(new Location("Hostel A", 12.8235, 80.0465, "https://cdn-icons-png.flaticon.com/512/3448/3448599.png", "Capacity: 300 students"));
            locationRepository.save(new Location("Library", 12.8226, 80.0448, "https://cdn-icons-png.flaticon.com/512/29/29302.png", "Open: 8 AM – 8 PM"));
            locationRepository.save(new Location("Canteen", 12.8242, 80.0453, "https://cdn-icons-png.flaticon.com/512/1046/1046784.png", "Open: 7 AM – 10 PM"));
            locationRepository.save(new Location("Main Gate", 12.8230, 80.0459, "https://cdn-icons-png.flaticon.com/512/854/854878.png", "SRM KTR Main Gate"));
        }
    }
}