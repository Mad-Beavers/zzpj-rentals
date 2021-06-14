package com.rentalhub.service;

import com.rentalhub.currencyService.AcceptedCurrencies;
import com.rentalhub.dto.RentDto;
import com.rentalhub.exception.*;
import com.rentalhub.model.ArchivedRent;
import com.rentalhub.model.Client;
import com.rentalhub.model.Rent;
import com.rentalhub.model.Vehicle;
import com.rentalhub.repository.ArchivedRentRepository;
import com.rentalhub.repository.RentRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class RentService {

    private final RentRepository rentRepository;
    private final VehicleService vehicleService;
    private final UserService userService;
    private final ArchivedRentService archivedRentService;
    private ArchivedRentRepository repository;

    @Autowired
    public RentService(RentRepository rentRepository, VehicleService vehicleService, UserService userService, ArchivedRentService archivedRentService) {
        this.rentRepository = rentRepository;
        this.vehicleService = vehicleService;
        this.userService = userService;
        this.archivedRentService = archivedRentService;
    }


    public Rent acceptRentAlgorithm(LocalDateTime acceptDate, String vehicleVin) throws UnavailableVehicleException, NoSuchClientException {
        List<Rent> rentList = rentRepository.findByRentedVehicle_Vin(vehicleVin);


        if (rentList.stream().anyMatch(rent -> rent.isAccepted() &&
                (acceptDate.toLocalDate().isBefore(rent.getDeclaredFinishedDate().plusDays(1).toLocalDate()) &&
                        acceptDate.toLocalDate().isAfter(rent.getStartDate().toLocalDate())))) {
            throw new UnavailableVehicleException("Vehicle is already reserved this day");
        }

        List<Rent> rentListFromDay = rentList.stream().filter(rent -> rent.getStartDate().toLocalDate().equals(acceptDate.toLocalDate())).collect(Collectors.toList());

        if (rentListFromDay.isEmpty()) {
            throw new NoSuchClientException("There is no rent in rent list");
        }

//        List<Integer> indexList = new ArrayList<>();
//        for (int i = 0; i < rentListFromDay.size(); i++) {
//            Rent rent = rentListFromDay.get(i);
//            if (rentList.stream().anyMatch(var -> var.isAccepted() &&
//                    (rent.getDeclaredFinishedDate().toLocalDate().isBefore(var.getDeclaredFinishedDate().plusDays(1).toLocalDate()) &&
//                            rent.getDeclaredFinishedDate().toLocalDate().isAfter(var.getStartDate().toLocalDate())))) {
//                indexList.add(i);
//            }
//        }
//        for (int i = indexList.size() - 1; i >= 0; i--) {
//            rentListFromDay.remove(indexList.get(i));
//        }


        rentListFromDay.removeIf(rent -> rentList.stream()
                .anyMatch(var -> var.isAccepted() &&
                        rent.getDeclaredFinishedDate().toLocalDate().isBefore(var.getDeclaredFinishedDate().plusDays(1).toLocalDate()) &&
                        rent.getDeclaredFinishedDate().toLocalDate().isAfter(var.getStartDate().toLocalDate()))
        );

        if (rentListFromDay.isEmpty()) {
            throw new NoSuchClientException("There is no rent in rent list");
        }


        Map<Rent, Integer> rentWithRating = new HashMap<>();

        for (Rent rent : rentListFromDay) {
            int rating = 0;
            if ((rent.getClient().getDrivingLicenseCategories().containsKey(rent.getRentedVehicle().getDlc()))) {
                rating =+ LocalDateTime.now().getYear() - rent.getClient().getDrivingLicenseCategories().get(rent.getRentedVehicle().getDlc()).getYear();
            }

            long daysBetween = Duration.between(rent.getStartDate().toLocalDate(), rent.getDeclaredFinishedDate().toLocalDate()).toDays();

            rating += daysBetween;

            List<ArchivedRent> archivedRent = repository.findArchivedRentByRent_Client(rent.getClient());
            if (!archivedRent.isEmpty()) {
                for (ArchivedRent var : archivedRent) {
                    rating += var.getRentRating();
                    if (var.getRent().getRentedVehicle().getVehicleType().equals(rent.getRentedVehicle().getVehicleType())) {
                        rating += 5;
                    }
                }
                for (ArchivedRent var : archivedRent) {
                    if (var.getDelayed()) {
                        if (rating != 0) {
                            rating--;
                        }
                    }
                }

            }
            rentWithRating.put(rent, rating);

        }

        int rate = 0;
        Rent resultRent = null;
        for (Map.Entry<Rent, Integer> var : rentWithRating.entrySet()) {
            if(var.getValue() >= rate) {
                rate = var.getValue();
                resultRent = var.getKey();
            }
        }
        resultRent.setAccepted(true);
        rentRepository.save(resultRent);
        return resultRent;
    }


    public Rent acceptRent(LocalDateTime acceptDate, String vehicleVin) throws UnavailableVehicleException, NoSuchClientException {
        return acceptRentAlgorithm(acceptDate, vehicleVin);
    }

    public Rent addRent(RentDto dto) throws UnavailableVehicleException, InsufficientClientDlcException,
            NoSuchClientException, NoSuchVehicleException {

        Optional<Vehicle> vehicle = vehicleService.getVehicle(dto.vin());
        Optional<Client> client = userService.getClient(dto.login());
        if (client.isEmpty()) {
            throw new NoSuchClientException("There is no client like that in the database");
        }
        if (vehicle.isEmpty()) {
            throw new NoSuchVehicleException("There is no vehicle like that in the database");
        }
        if (!vehicle.get().getAvailable()) {
            throw new UnavailableVehicleException("This vehicle is already rented");
        }
        if (!client.get().getDrivingLicenseCategories().keySet().contains(vehicle.get().getDlc())) {
            throw new InsufficientClientDlcException("Client does not have required driving license category");
        }
        vehicleService.changeAvailability(dto.vin(), false);
        Rent rent = new Rent(vehicle.get(), client.get(), dto.startDate(), dto.declaredFinishedDate(), dto.actualFinishedDate());

        return rentRepository.save(rent);
    }

    public Optional<Rent> getRent(UUID uuid) {
        return rentRepository.findByUuid(uuid);
    }

    public List<Rent> getRents() {
        return rentRepository.findAll();
    }

    public List<Rent> getRentsForVehicle(String vin) {
        return rentRepository.findByRentedVehicle_Vin(vin);
    }

    public List<Rent> getRentsForClient(String login) {
        return rentRepository.findByClient_Login(login);
    }

    public Optional<Rent> endRent(UUID uuid, AcceptedCurrencies currency, int rate) throws CurrencyServiceException {
        Optional<Rent> rent = rentRepository.findByUuid(uuid);
        if (rent.isPresent()) {
            archivedRentService.addArchivedRent(rent.get(), currency, rate);

            rent.get().setActualFinishedDate(LocalDateTime.now());
            rentRepository.save(rent.get());

            vehicleService.changeAvailability(rent.get().getRentedVehicle().getVin(), true);
        }
        return rent;
    }
}
