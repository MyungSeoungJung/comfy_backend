package com.comfy_backend.heart.controller;

import com.comfy_backend.heart.entity.HeartRequestDto;
import com.comfy_backend.heart.heartService.HeartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/heart")
public class HeartController {
    @Autowired
    HeartService heartService;
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody HeartRequestDto heartRequestDto) {
        ResponseEntity<?> response =  heartService.insert(heartRequestDto);
        if (response.getStatusCode() == HttpStatus.CONFLICT) {

            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return  ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping
    public void delete(@RequestBody HeartRequestDto heartRequestDto) {
        heartService.delete(heartRequestDto);
    }

}

