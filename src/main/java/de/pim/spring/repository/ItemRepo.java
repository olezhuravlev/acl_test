package de.pim.spring.repository;

import de.pim.spring.model.Item;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

public interface ItemRepo {

    @PostFilter("hasPermission(filterObject, 'READ')")
    List<Item> findAll();

    @PreAuthorize("@authorizationLogic.hasPermission(#id, 'de.pim.spring.model.Item', 'READ')")
    Optional<Item> findById(@Param("id") long id);

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    Item save(Item item);
}
