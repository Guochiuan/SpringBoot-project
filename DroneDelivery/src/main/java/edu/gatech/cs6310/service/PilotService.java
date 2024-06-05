package edu.gatech.cs6310.service;

import edu.gatech.cs6310.entity.Pilot;
import edu.gatech.cs6310.repository.PilotRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PilotService {
    private PilotRepository pilotRepository;

    public Optional<Pilot> findById(int id) {
        return pilotRepository.findById(id);
    }

    public Optional<Pilot> findByAccountName(String accountName) {
        return pilotRepository.findByAccountName(accountName);
    }
    public void saveAndFlush(Pilot pilot) {
        pilotRepository.saveAndFlush(pilot);
    }

    //new add
    public void deleteById(int id) {
        pilotRepository.deleteById(id);
    }
    public List<Pilot> findAll() {
        return pilotRepository.findAll();
    }


}
