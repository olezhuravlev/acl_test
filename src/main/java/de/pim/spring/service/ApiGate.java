package de.pim.spring.service;

import de.pim.spring.model.Item;

import java.util.List;
import java.util.Optional;

public interface ApiGate {

    List<Item> getAllItems();

    Optional<Item> getItemById(long id);

    Item save(Item item);

    void addPermission(String userName, Long itemId);

    void revokePermission(String userName, Long itemId);
}
