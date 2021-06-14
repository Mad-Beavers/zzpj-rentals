package com.rentalhub.service;

import com.rentalhub.currencyService.AcceptedCurrencies;
import com.rentalhub.currencyService.ExchangeRatesService;
import com.rentalhub.exception.CurrencyServiceException;
import com.rentalhub.model.ArchivedRent;
import com.rentalhub.model.Rent;
import com.rentalhub.repository.ArchivedRentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ArchivedRentService {

    private ArchivedRentRepository repository;
    private ExchangeRatesService exchangeRatesService;

    @Autowired
    public ArchivedRentService(ArchivedRentRepository repository, ExchangeRatesService exchangeRatesService) {
        this.repository = repository;
        this.exchangeRatesService = exchangeRatesService;
    }


    public Optional<ArchivedRent> getArchivedRent(UUID uuid) {
        return repository.findByUuid(uuid);
    }

    public List<ArchivedRent> getArchivedRents() {
        return repository.findAll();
    }

    public void addArchivedRent(Rent rent, AcceptedCurrencies currency) throws CurrencyServiceException {
        Double exchangeRate = exchangeRatesService.getExchangeRate(currency);
        Double costInPln = CostCalculator.calculateCost(rent.getStartDate(), rent.getDeclaredFinishedDate(),
                rent.getDeclaredFinishedDate(), rent.getRentedVehicle().getDailyLoanPrice());
        ArchivedRent archivedRent = new ArchivedRent(rent.getUuid(), rent, costInPln, currency.name(), exchangeRate);
        repository.save(archivedRent);
    }
}
