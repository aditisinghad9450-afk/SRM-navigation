package com.srm.navigation.controller;

import com.srm.navigation.entity.Location;
import com.srm.navigation.entity.Review;
import com.srm.navigation.entity.User;
import com.srm.navigation.repository.LocationRepository;
import com.srm.navigation.repository.ReviewRepository;
import com.srm.navigation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allows frontend to connect
public class ApiController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private LocationRepository locationRepository;

    // ---------------- LOGIN ----------------
    // This now checks the H2 database
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> creds) {
        String email = creds.get("email");
        String password = creds.get("password");

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            // In a real app, use password hashing!
            return Map.of("status", "success", "message", "Welcome back, " + user.get().getEmail());
        } else {
            return Map.of("status", "failed", "message", "Invalid email or password");
        }
    }

    // ---------------- CHATBOT ----------------
    // This logic remains the same as it doesn't need a database
    @PostMapping("/chatbot")
    public Map<String, String> chatbot(@RequestBody Map<String, String> req) {
        String message = req.get("message").toLowerCase();
        String reply;

        if (message.contains("library"))
            reply = "📚 The Central Library is beside the Main Building!";
        else if (message.contains("canteen"))
            reply = "🍴 The Tech Park Canteen is near the lake!";
        else if (message.contains("hostel"))
            reply = "🏠 Boys hostel is behind the Admin Block, Girls hostel near Medical College.";
        else
            reply = "🤖 I’m still learning! Try asking about 'library' or 'canteen'.";

        return Map.of("reply", reply);
    }

    // ---------------- REVIEWS (Database) ----------------
    @PostMapping("/reviews")
    public Map<String, String> addReview(@RequestBody Map<String, String> body) {
        String reviewText = body.get("review");
        if (reviewText != null && !reviewText.isEmpty()) {
            reviewRepository.save(new Review(reviewText));
            return Map.of("status", "success", "message", "Review added successfully");
        }
        return Map.of("status", "failed", "message", "Review cannot be empty");
    }

    @GetMapping("/reviews")
    public List<Review> getReviews() {
        // Fetches all reviews from the database
        return reviewRepository.findAll();
    }
    
    // ---------------- LOCATIONS (Database) ----------------
    @GetMapping("/locations")
    public List<Location> getLocations() {
        // Fetches all map locations from the database
        return locationRepository.findAll();
    }
}