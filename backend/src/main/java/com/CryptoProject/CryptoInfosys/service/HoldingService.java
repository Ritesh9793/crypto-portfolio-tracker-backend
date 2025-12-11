package com.CryptoProject.CryptoInfosys.service;

import com.CryptoProject.CryptoInfosys.model.Holding;
import com.CryptoProject.CryptoInfosys.model.User;
import com.CryptoProject.CryptoInfosys.repository.HoldingRepository;
import com.CryptoProject.CryptoInfosys.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoldingService {

    private final HoldingRepository holdingRepo;
    private final UserRepository userRepo;

    public HoldingService(HoldingRepository holdingRepo, UserRepository userRepo) {
        this.holdingRepo = holdingRepo;
        this.userRepo = userRepo;
    }

    public List<Holding> getHoldings(String email) {
        User user = userRepo.findByEmail(email).orElseThrow();
        return holdingRepo.findByUser(user);
    }

    public Holding addHolding(Holding holding, String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Holding newHolding = new Holding();

        newHolding.setAsset(holding.getAsset());
        newHolding.setSymbol(holding.getSymbol());
        newHolding.setQuantity(holding.getQuantity());
        newHolding.setPrice(holding.getPrice());
        newHolding.setUser(user);

        return holdingRepo.save(newHolding);
    }

    public Holding updateHolding(int id, Holding updatedHolding, String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Holding existing = holdingRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Holding not found"));

        if (existing.getUser().getId() != user.getId()) {
            throw new RuntimeException("Unauthorized update");
        }

        existing.setAsset(updatedHolding.getAsset());
        existing.setSymbol(updatedHolding.getSymbol());
        existing.setQuantity(updatedHolding.getQuantity());
        existing.setPrice(updatedHolding.getPrice());

        return holdingRepo.save(existing);
    }

    public void deleteHolding(int id, String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Holding existing = holdingRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Holding not found"));

        if (existing.getUser().getId() != user.getId()) {
            throw new RuntimeException("Unauthorized delete");
        }

        holdingRepo.delete(existing);
    }

}

