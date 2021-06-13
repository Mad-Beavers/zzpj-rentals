package com.rentalhub.service;

import com.rentalhub.currencyService.AcceptedCurrencies;
import com.rentalhub.currencyService.ExchangeRatesReceiver;
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
    private ExchangeRatesReceiver exchangeRatesReceiver;

    @Autowired
    public ArchivedRentService(ArchivedRentRepository repository, ExchangeRatesReceiver exchangeRatesReceiver) {
        this.repository = repository;
        this.exchangeRatesReceiver = exchangeRatesReceiver;
    }

    public Optional<ArchivedRent> getArchivedRent(UUID uuid) {
        return repository.findByUuid(uuid);
    }

    public List<ArchivedRent> getArchivedRents() {
        return repository.findAll();
    }

    public void addArchivedRent(Rent rent, AcceptedCurrencies currency) throws CurrencyServiceException {
        Double exchangeRate = exchangeRatesReceiver.getExchangeRate(currency);
        Double costInPln = 10.0;
        ArchivedRent archivedRent = new ArchivedRent(rent.getUuid(), rent, costInPln, currency.name(), exchangeRate);
        repository.save(archivedRent);
    }
}
