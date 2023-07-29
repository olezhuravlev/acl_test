package de.pim.spring.controller.rest;

import de.pim.spring.model.Item;
import de.pim.spring.service.ApiGate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ItemsRestController {

    private final ApiGate apiGate;

    public ItemsRestController(ApiGate apiGate) {
        this.apiGate = apiGate;
    }

    @PostMapping(value = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllItems() {
        List<Item> allItems = apiGate.getAllItems();
        return ResponseEntity.ok().body(allItems);
    }

    @PostMapping(value = "/items/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findById(@PathVariable("id") long id, @CurrentSecurityContext SecurityContext securityContext) {
        Optional<Item> item = apiGate.getItemById(id);
        if (item.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(item.get());
        }
    }

    // localhost:8080/items?action=add-item
    @PutMapping(value = "/items", params = "action=add-item", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addItem(@RequestBody Item item) {
        Item saved = apiGate.save(item);
        return ResponseEntity.ok().body(saved);
    }
}
