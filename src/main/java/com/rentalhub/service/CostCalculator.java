package com.rentalhub.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CostCalculator {

    public static double calculateCost(LocalDateTime startDate, LocalDateTime declaredEndDate, LocalDateTime endDate,
                                double dailyLoanPrice) {
        long duration = ChronoUnit.DAYS.between(startDate.toLocalDate(), endDate.toLocalDate());
        long daysOver = ChronoUnit.DAYS.between(declaredEndDate.toLocalDate(), endDate.toLocalDate());

        double cost = 0;

        cost += dailyLoanPrice * (duration - daysOver);
        cost += dailyLoanPrice * daysOver * 2.0;

        return cost;
    }
}
