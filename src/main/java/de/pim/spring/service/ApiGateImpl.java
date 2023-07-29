package de.pim.spring.service;

import de.pim.spring.model.Item;
import de.pim.spring.repository.ItemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApiGateImpl implements ApiGate {

    private final ItemRepo itemRepo;
    private final AclPermissionService aclPermissionService;

    @Override
    @Transactional(readOnly = true)
    public List<Item> getAllItems() {
        return itemRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Item> getItemById(long id) {
        return itemRepo.findById(id);
    }

    @Override
    @Transactional
    public Item save(Item item) {

        // Item saved along with its acl_object_identity and acl_entry rows.
        Item saved = itemRepo.save(item);
        ObjectIdentity objectIdentity = new ObjectIdentityImpl(Item.class, saved.getId());
        Permission permission = BasePermission.READ;

        aclPermissionService.addPermissionForAuthentication(item, permission);

        return saved;
    }


    @Override
    @Transactional
    public void addPermission(String userName, Long itemId) {

        Optional<Item> item = itemRepo.findById(itemId);
        if (item.isEmpty()) {
            return;
        }

        ObjectIdentity objectIdentity = new ObjectIdentityImpl(Item.class, itemId);
        Permission permission = BasePermission.READ;
        Sid sid = new PrincipalSid(userName);
        aclPermissionService.addPermissionForSid(objectIdentity, permission, sid);
    }

    @Override
    @Transactional
    public void revokePermission(String userName, Long itemId) {

        Optional<Item> item = itemRepo.findById(itemId);
        if (item.isEmpty()) {
            return;
        }

        ObjectIdentity objectIdentity = new ObjectIdentityImpl(Item.class, itemId);
        Permission permission = BasePermission.READ;
        Sid sid = new PrincipalSid(userName);
        aclPermissionService.removePermissionForSid(objectIdentity, permission, sid);
    }
}
