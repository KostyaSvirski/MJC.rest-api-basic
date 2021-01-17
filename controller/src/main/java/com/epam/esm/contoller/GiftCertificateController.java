package com.epam.esm.contoller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {

    @Autowired
    private GiftCertificateServiceImpl service;

    @GetMapping
    @RequestMapping("/")
    public ResponseEntity<List<GiftCertificateDTO>> findAllCertificates() {
        List<GiftCertificateDTO> results = null;
        try {
            results = service.findAll();
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>(HttpStatus.resolve(500));
        }
    }

    @GetMapping
    @RequestMapping("/{name}")
    public ResponseEntity<GiftCertificateDTO> findSpecificCertificate(@PathVariable(required = true) String name) {
        GiftCertificateDTO result = null;
        try {
            result = service.find(name);
            if(result == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>(HttpStatus.resolve(500));
        }
    }

    @PostMapping
    @RequestMapping("/addCertificate")
    public ResponseEntity createNewCertificate(@ModelAttribute("newCertificate") GiftCertificateDTO certificateDTO ) {
        certificateDTO.setCreateDate(LocalDateTime.now());
        certificateDTO.setLastUpdateDate(LocalDateTime.now());
        try {
            service.create(certificateDTO);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity(HttpStatus.resolve(500));
        }
    }


}
