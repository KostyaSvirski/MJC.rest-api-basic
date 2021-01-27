package com.epam.esm.contoller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {

    @Autowired
    private GiftCertificateService service;

    @GetMapping(value = "/")
    public ResponseEntity retrieveCertificates
            (@RequestParam(required = false) String partOfName,
             @RequestParam(required = false) String partOfDescription,
             @RequestParam(required = false) String nameOfTag,
             @RequestParam(required = false) String field,
             @RequestParam(required = false) String method) {
        List<GiftCertificateDTO> results = null;
        try {
            if (partOfName != null) {
                results = service.findByPartOfName(partOfName);
            } else if (partOfDescription != null) {
                results = service.findByPartOfDescription(partOfDescription);
            } else if (nameOfTag != null) {
                results = service.findByTag(nameOfTag);
            } else if (field != null && method != null) {
                results = service.sortByField(field, method);
            } else {
                results = service.findAll();
            }
            if (results == null || results.isEmpty()) {
                return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity findSpecificCertificate(@PathVariable long id) {
        Optional<GiftCertificateDTO> result;
        try {
            result = service.find(id);
            return result
                    .map(certificateDTO -> new ResponseEntity(certificateDTO, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity("certificate with id " + id + "not found",
                            HttpStatus.NOT_FOUND));
        } catch (ServiceException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity createNewCertificate(@RequestBody GiftCertificateDTO certificateDTO) {
        certificateDTO.setCreateDate(Instant.now().toString());
        certificateDTO.setLastUpdateDate(Instant.now().toString());
        try {
            int result = service.create(certificateDTO);
            if (result != 0) {
                return new ResponseEntity(result, HttpStatus.CREATED);
            } else {
                return new ResponseEntity("not valid data", HttpStatus.BAD_REQUEST);
            }
        } catch (ServiceException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCertificate(@PathVariable long id) {
        try {
            service.delete(id);
            return new ResponseEntity("certificate with id " + id + " deleted", HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PatchMapping("/{id}")
    public ResponseEntity updateCertificate(@RequestBody GiftCertificateDTO certificate, @PathVariable long id) {
        try {
            service.update(certificate, id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (ServiceException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }


}
