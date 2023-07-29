package de.pim.spring.service;

import de.pim.spring.model.Item;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

public interface AclPermissionService {

    void addPermissionForAuthentication(Item item, Permission permission);
    void addPermissionForSid(ObjectIdentity objectIdentity, Permission permission, Sid sid);
    void removePermissionForSid(ObjectIdentity objectIdentity, Permission permission, Sid sid);
}
