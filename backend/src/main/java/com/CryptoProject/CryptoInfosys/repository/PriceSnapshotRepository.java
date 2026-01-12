package com.CryptoProject.CryptoInfosys.repository;

import com.CryptoProject.CryptoInfosys.model.PriceSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceSnapshotRepository
        extends JpaRepository<PriceSnapshot, Long> {

    // Get price history for an asset
    List<PriceSnapshot> findByAssetSymbolOrderByCapturedAtAsc(String assetSymbol);

    // Get snapshots within a time range
    List<PriceSnapshot> findByAssetSymbolAndCapturedAtBetween(
            String assetSymbol,
            LocalDateTime start,
            LocalDateTime end
    );
    List<PriceSnapshot> findAllByOrderByCapturedAtAsc();
}
