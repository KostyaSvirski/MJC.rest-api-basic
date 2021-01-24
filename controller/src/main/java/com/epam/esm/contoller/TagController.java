package com.epam.esm.contoller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping("/")
    public ResponseEntity<List<TagDTO>> findAllTags() {
        List<TagDTO> allTags = null;
        try {
            allTags = tagService.findAll();
            return new ResponseEntity<>(allTags, HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>(HttpStatus.resolve(500));
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<TagDTO> findSpecificTag(@PathVariable(required = false) String name) {
        Optional<TagDTO> tagToFind = null;
        try {
            tagToFind = tagService.find(name);
            if (!tagToFind.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(tagToFind.get(), HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>(HttpStatus.resolve(500));
        }
    }


    @PostMapping("/")
    public ResponseEntity<Integer> addTag(@RequestBody TagDTO newTag) {
        try {
            int result = tagService.create(newTag);
            if (result != 0) {
                return new ResponseEntity(result, HttpStatus.CREATED);
            } else {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } catch (ServiceException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/")
    public ResponseEntity deleteTag(@PathVariable(required = true) long id) {
        try {
            tagService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


}
