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
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {

    @Autowired
    private GiftCertificateService service;

    @GetMapping("/")
    public ResponseEntity<List<GiftCertificateDTO>> findAllCertificates(@RequestParam Map<String, String> params) {
        List<GiftCertificateDTO> results = null;
        try {
            results = service.searchByParam(params);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<GiftCertificateDTO> findSpecificCertificate(@PathVariable(required = false) String name) {
        Optional<GiftCertificateDTO> result;
        try {
            result = service.find(name);
            if (!result.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Integer> createNewCertificate(@RequestBody GiftCertificateDTO certificateDTO) {
        certificateDTO.setCreateDate(Instant.now().toString());
        certificateDTO.setLastUpdateDate(Instant.now().toString());
        try {
            int result = service.create(certificateDTO);
            if (result != 0) {
                return new ResponseEntity(result, HttpStatus.CREATED);
            } else {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } catch (ServiceException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCertificate(@PathVariable(required = false) long id) {
        try {
            service.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    @PatchMapping("/{id}")
    public ResponseEntity editCertificate(@RequestParam Map<String, String> paramsMap, @PathVariable long id) {
        try {
            if(paramsMap != null || !paramsMap.isEmpty()) {
                Instant lastUpdateDate = Instant.now();
                boolean result = service.update(paramsMap, id, lastUpdateDate);
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (ServiceException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
