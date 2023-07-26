package de.pim.spring.service;

import de.pim.spring.model.Item;

import java.util.List;
import java.util.Optional;

public interface ApiGate {

    List<Item> getAll();
    Optional<Item> getById(long id);
}
