package de.pim.spring.repository;

import de.pim.spring.model.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ItemRepoJpa implements ItemRepo {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Item> findAll() {
        var query = entityManager.createQuery("SELECT i FROM Item i ORDER BY i.title", Item.class);
        return query.getResultList();
    }

    @Override
    public Optional<Item> findById(long id) {
        Item result = entityManager.find(Item.class, id);
        return Optional.ofNullable(result);
    }

    @Override
    public Item save(Item item) {
        if (item.getId() == 0) {
            entityManager.persist(item);
            return item;
        } else {
            return entityManager.merge(item);
        }
    }
}
