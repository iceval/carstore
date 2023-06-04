package org.rosatom.carstore.services;

import org.rosatom.carstore.exceptions.CarNotFoundException;
import org.rosatom.carstore.models.Car;
import org.rosatom.carstore.repositories.CarRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CarService {

        private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getCars() {
        return Streamable.of(carRepository.findAll()).toList();
    }

    public Car getCarById(int id){
        Optional<Car> foundCar = carRepository.findById(id);
        return foundCar.orElseThrow(CarNotFoundException::new);
    }

    @Transactional
    public void addCar(Car car) {
        carRepository.save(car);
    }

    @Transactional
    public void updateCar(int id, Car newCar) {
        carRepository.findById(id)
                .map(car -> {
                    car.setBrand(newCar.getBrand());
                    car.setColor(newCar.getColor());
                    return carRepository.save(car);
                })
                .orElseThrow(CarNotFoundException::new);
    }

    @Transactional
    public void deleteCar(int id) {
        if (!carRepository.existsById(id)){
            throw new CarNotFoundException();
        }
        carRepository.deleteById(id);
    }

}
