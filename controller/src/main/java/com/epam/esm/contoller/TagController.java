package com.epam.esm.contoller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/tags")
@RestController
public class TagController {

    @Autowired
    BaseService<TagDTO> tagService;

    @GetMapping
    @RequestMapping("/")
    public ResponseEntity<List<TagDTO>> findAllTags() {
        List<TagDTO> allTags = null;
        try {
            allTags = tagService.findAll();
            return new ResponseEntity<>(allTags, HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>(HttpStatus.resolve(500));
        }
    }

    @GetMapping
    @RequestMapping("/{name}")
    public ResponseEntity<TagDTO> findSpecificTag(@PathVariable(required = false) String name) {
        TagDTO tagToFind = null;
        try {
            tagToFind = tagService.find(name);
            if(tagToFind == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(tagToFind, HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>(HttpStatus.resolve(500));
        }
    }

    @PostMapping
    @RequestMapping("/")
    public ResponseEntity addTag(@ModelAttribute("newTag") TagDTO newTag) {
        try {
            tagService.create(newTag);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (ServiceException e) {
            return new ResponseEntity(HttpStatus.resolve(500));
        }
    }

    @PatchMapping
    @RequestMapping("/{id}")
    public ResponseEntity editTag(@ModelAttribute("updatedTag") TagDTO updatedTag) {
        try {
            tagService.update(updatedTag);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    @RequestMapping("/{id}")
    public ResponseEntity deleteTag(@ModelAttribute("tagToDelete") TagDTO tagToDelete) {
        try {
            tagService.delete(tagToDelete);
            return new ResponseEntity(HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


}
