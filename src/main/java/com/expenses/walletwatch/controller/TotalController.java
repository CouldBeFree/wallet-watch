package com.expenses.walletwatch.controller;

import com.expenses.walletwatch.dto.TotalDto;
import com.expenses.walletwatch.service.TotalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/")
public class TotalController {

    private final TotalService totalService;

    public TotalController(TotalService totalService) {
        this.totalService = totalService;
    }

    @GetMapping("/total")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TotalDto> getTotal(@RequestParam Optional<String> startDate, Optional<String> endDate) {
        return new ResponseEntity<>(totalService.getTotal(startDate, endDate), HttpStatus.OK);
    }
}
