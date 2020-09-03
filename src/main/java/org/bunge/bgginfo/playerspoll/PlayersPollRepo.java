package org.bunge.bgginfo.playerspoll;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayersPollRepo extends CrudRepository<PlayersPollDAO, Integer> {
    
}