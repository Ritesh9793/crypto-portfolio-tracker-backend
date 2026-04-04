package com.CryptoProject.CryptoInfosys.repository;

import com.CryptoProject.CryptoInfosys.model.ScamToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScamTokenRepository extends JpaRepository<ScamToken, Long> {

    List<ScamToken> findByRiskLevelOrderByLastSeenDesc(String riskLevel);
}
