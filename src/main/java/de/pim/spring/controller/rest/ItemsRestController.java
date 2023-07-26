package de.pim.spring.controller.rest;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import de.pim.spring.model.Item;
import de.pim.spring.service.ApiGate;

import java.util.List;
import java.util.Optional;

@RestController
public class ItemsRestController {

    private final ApiGate apiGate;

    public ItemsRestController(ApiGate apiGate) {
        this.apiGate = apiGate;
    }

    @PostMapping(value = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Item> findAll(@CurrentSecurityContext SecurityContext securityContext) {

        Authentication authentication = securityContext.getAuthentication();

        List<Item> items = apiGate.getAll();
        return items;
    }

    @PostMapping(value = "/items/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Item findById(@PathVariable("id") long id, @CurrentSecurityContext SecurityContext securityContext) {

        Authentication authentication = securityContext.getAuthentication();

        Optional<Item> item = apiGate.getById(id);
        return item.get();
    }
}
