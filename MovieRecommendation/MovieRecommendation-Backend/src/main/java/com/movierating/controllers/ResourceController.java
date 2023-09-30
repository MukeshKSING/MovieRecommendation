  package com.movierating.controllers;

   import com.movierating.obj.MovieRating;
   import com.movierating.obj.UserEntry;
   import com.movierating.obj.requests.MovieRatingRequest;
   import com.movierating.services.RatingService;
   import com.movierating.services.UserService;
   import java.util.HashMap;
   import javax.servlet.http.HttpServletRequest;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.http.ResponseEntity;
   import org.springframework.web.bind.annotation.*;

 @RestController
 @RequestMapping("/api/v1/")
   public class ResourceController {
    @Autowired private UserService userService;
    @Autowired private RatingService ratingService;
  
   
    @PostMapping("/user/register")
    public ResponseEntity<Object> register(@RequestBody UserEntry newUserEntry) {
      return userService.register(newUserEntry);
    }
  
   
      @PostMapping("/user/login")
      public ResponseEntity<Object> login(@RequestBody UserEntry userEntry) {
      return userService.login(userEntry);
    }
  
      @GetMapping("/greetings")
      public ResponseEntity<Object> greeting() {
      HashMap<String, String> map = new HashMap<>();
      map.put("response", "hello");
      return ResponseEntity.ok(map);
    }
  
    
     @PostMapping("/user/welcome")
     public ResponseEntity<Object>  authenticatedGreeting() {
      return ResponseEntity.ok().body("{\"response\":\"authenticated \"}");
     }
//for rating
      @PutMapping("/user/ratings")
      public ResponseEntity<Object> addRating(
         @RequestBody MovieRatingRequest movieRatingRequest, HttpServletRequest request) {
       int userId = (int) request.getAttribute("user_id");
       return ratingService.rateMovie(
           new MovieRating(movieRatingRequest.getMovie_id(), userId, movieRatingRequest.getRating()));
         }
// for delete
 
      @DeleteMapping("/user/rating")
      public ResponseEntity<Object> deleteRating(
          @RequestBody MovieRatingRequest movieRatingRequest, HttpServletRequest request) {
        int userId = (int) request.getAttribute("user_id");
        return ratingService.deleteRating(
            new MovieRating(movieRatingRequest.getMovie_id(), userId, movieRatingRequest.getRating()));
     }
//rate specific
  
     @GetMapping("/user/rating/{user_name}")
     public ResponseEntity<Object> getRating(@PathVariable("user_name") String username) {
      int user_id = userService.findUserByUsername(username).getId();
      return ratingService.getRatings(user_id);
    }
   }
