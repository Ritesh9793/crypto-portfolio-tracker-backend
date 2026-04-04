package com.CryptoProject.CryptoInfosys.repository;

import com.CryptoProject.CryptoInfosys.model.RiskAlert;
import com.CryptoProject.CryptoInfosys.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RiskAlertRepository extends JpaRepository<RiskAlert, Long> {

    List<RiskAlert> findByUserOrderByCreatedAtDesc(User user);
}
