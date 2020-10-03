package org.bunge.bgginfo.game;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepo extends CrudRepository<GameDAO, Integer> {
    
    @Query(value = "SELECT `name`, average, num_ratings, weight " +
                    "FROM game " + 
                    "CROSS JOIN (" +
                        "SELECT MAX(average) AS average_max FROM game) AS maxAverageTable " +
                    "CROSS JOIN ( " +
                        "SELECT MAX(num_ratings) AS num_max FROM game) AS maxNumTable " +
                    "WHERE error = false AND num_ratings > 4 " + 
                    "ORDER BY ((average / (average_max * 1.0)) * ?1 + (num_ratings / (num_max * 1.0)) * (1.0 - ?1)) DESC LIMIT 500;",
        nativeQuery = true)
    List<GameDTO> findByAverage(Double average);
}