package com.CryptoProject.CryptoInfosys.service;

import com.CryptoProject.CryptoInfosys.dto.PricingDTO;
import com.CryptoProject.CryptoInfosys.dto.RiskAlertDTO;
import com.CryptoProject.CryptoInfosys.model.RiskAlert;
import com.CryptoProject.CryptoInfosys.model.User;
import com.CryptoProject.CryptoInfosys.repository.RiskAlertRepository;
import com.CryptoProject.CryptoInfosys.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RiskAnalysisService {

    private final NotificationService notificationService;
    private final RiskAlertRepository riskAlertRepository;
    private final UserRepository userRepository;

    public RiskAnalysisService(
            NotificationService notificationService,
            RiskAlertRepository riskAlertRepository,
            UserRepository userRepository
    ) {
        this.notificationService = notificationService;
        this.riskAlertRepository = riskAlertRepository;
        this.userRepository = userRepository;
    }

    public List<RiskAlertDTO> analyze(List<PricingDTO> prices) {
        List<RiskAlertDTO> alerts = new ArrayList<>();

        for (PricingDTO price : prices) {
            RiskAlertDTO alert = new RiskAlertDTO();
            alert.asset = price.name;
            alert.symbol = price.symbol;

            if (Math.abs(price.change24h) > 15) {
                alert.riskLevel = "HIGH";
                alert.reason = "Extreme 24h price volatility";
                alert.alertType = "rugpull_warning";
                alert.source = "CoinGecko volatility rule";
            } else if (price.marketCap < 10_000_000_000L) {
                alert.riskLevel = "MEDIUM";
                alert.reason = "Low market capitalization";
                alert.alertType = "contract_risk";
                alert.source = "CoinGecko market-cap rule";
            } else {
                alert.riskLevel = "LOW";
                alert.reason = "Stable market conditions";
                alert.alertType = "news";
                alert.source = "Internal risk baseline";
            }

            alerts.add(alert);
        }

        return alerts;
    }

    public List<RiskAlertDTO> analyzeWithNotifications(List<PricingDTO> prices, String email) {
        List<RiskAlertDTO> alerts = analyze(prices);

        for (RiskAlertDTO alert : alerts) {
            if ("HIGH".equals(alert.riskLevel)) {
                boolean alreadySent = notificationService.hasNotification(
                        email,
                        "High Risk Alert",
                        "WARNING"
                );

                if (!alreadySent) {
                    notificationService.createNotification(
                            email,
                            "High Risk Alert",
                            alert.symbol + " is showing extreme volatility. Trade carefully.",
                            "WARNING"
                    );
                }
            }
        }

        return alerts;
    }

    public List<RiskAlertDTO> analyzeForUser(String email, List<PricingDTO> prices) {
        List<RiskAlertDTO> alerts = analyzeWithNotifications(prices, email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        for (RiskAlertDTO alert : alerts) {
            RiskAlert entity = new RiskAlert();
            entity.setUser(user);
            entity.setAssetSymbol(alert.symbol);
            entity.setAlertType(alert.alertType);
            entity.setDetails(alert.reason + " (" + alert.riskLevel + ")");
            riskAlertRepository.save(entity);
        }

        return alerts;
    }
}
