package de.pim.spring.service;

import de.pim.spring.model.Item;
import de.pim.spring.repository.ItemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApiGateImpl implements ApiGate {

    private final ItemRepo itemRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Item> getAll() {
        return itemRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Item> getById(long id) {
        return itemRepo.findById(id);
    }
}
