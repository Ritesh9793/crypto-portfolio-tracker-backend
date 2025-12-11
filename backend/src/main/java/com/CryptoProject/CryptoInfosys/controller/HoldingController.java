package com.CryptoProject.CryptoInfosys.controller;

import com.CryptoProject.CryptoInfosys.dto.HoldingRequest;
import com.CryptoProject.CryptoInfosys.model.Holding;
import com.CryptoProject.CryptoInfosys.security.JwtUtils;
import com.CryptoProject.CryptoInfosys.service.HoldingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/holdings")
@CrossOrigin(origins = "http://localhost:3000")
public class HoldingController {

    private final HoldingService holdingService;
    private final JwtUtils jwtUtils;

    public HoldingController(HoldingService holdingService, JwtUtils jwtUtils) {
        this.holdingService = holdingService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping
    public ResponseEntity<?> getHoldings(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String email = jwtUtils.extractUsername(token);
        return ResponseEntity.ok(holdingService.getHoldings(email));
    }

    @PostMapping
    public ResponseEntity<?> addHolding(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Holding holding) {

        String token = authHeader.substring(7);
        String email = jwtUtils.extractUsername(token);

        // FIX: Validate missing fields
        if (holding.getAsset() == null || holding.getSymbol() == null) {
            return ResponseEntity.badRequest().body("Asset & Symbol are required");
        }

        Holding saved = holdingService.addHolding(holding, email);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHolding(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable int id,
            @RequestBody Holding updatedHolding) {

        String token = authHeader.substring(7);
        String email = jwtUtils.extractUsername(token);

        return ResponseEntity.ok(holdingService.updateHolding(id, updatedHolding, email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHolding(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable int id) {

        String token = authHeader.substring(7);
        String email = jwtUtils.extractUsername(token);

        holdingService.deleteHolding(id, email);

        return ResponseEntity.ok("Deleted successfully");
    }

}
