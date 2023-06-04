package org.rosatom.carstore.controllers;

import jakarta.validation.Valid;
import org.rosatom.carstore.exceptions.CarErrorResponse;
import org.rosatom.carstore.exceptions.CarNotAddedException;
import org.rosatom.carstore.exceptions.CarNotFoundException;
import org.rosatom.carstore.models.Car;
import org.rosatom.carstore.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping()
    public List<Car> getCars(){
        return carService.getCars();
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable("id") int id){
        return carService.getCarById(id);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> addCar(@RequestBody @Valid Car car,
                                              BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors){
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }

            throw new CarNotAddedException(errorMsg.toString());
        }

        carService.addCar(car);

        return ResponseEntity.ok(HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateCar(@PathVariable("id") int id, @RequestBody @Valid Car car,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors){
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }

            throw new CarNotAddedException(errorMsg.toString());
        }

        carService.updateCar(id, car);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCar(@PathVariable("id") int id) {
        carService.deleteCar(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<CarErrorResponse> handleException(CarNotFoundException e) {
        CarErrorResponse response = new CarErrorResponse("Car with this id wasn't found!");

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<CarErrorResponse> handleException(CarNotAddedException e) {
        CarErrorResponse response = new CarErrorResponse(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
