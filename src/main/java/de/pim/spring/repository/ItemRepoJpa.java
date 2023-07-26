package de.pim.spring.repository;

import de.pim.spring.model.Item;
import de.pim.spring.service.AclPermissionService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ItemRepoJpa implements ItemRepo {

    @PersistenceContext
    private final EntityManager entityManager;

    private AclPermissionService aclPermissionService;

    @Override
    public List<Item> findAll() {
        var query = entityManager.createQuery("SELECT i FROM Item i ORDER BY i.title", Item.class);
        //query.setHint(EntityGraph.EntityGraphType.FETCH.getKey(), entityManager.getEntityGraph("item"));
        return query.getResultList();
    }

    @Override
    public Optional<Item> findById(long id) {
        Item result = entityManager.find(Item.class, id);
        return Optional.ofNullable(result);
    }
}
