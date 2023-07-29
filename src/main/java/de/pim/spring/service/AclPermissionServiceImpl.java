package de.pim.spring.service;

import de.pim.spring.model.Item;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
@Transactional
public class AclPermissionServiceImpl implements AclPermissionService {

    private final MutableAclService aclService;
    private final PlatformTransactionManager transactionManager;

    public AclPermissionServiceImpl(MutableAclService aclService, PlatformTransactionManager transactionManager) {
        this.aclService = aclService;
        this.transactionManager = transactionManager;
    }

    @Override
    public void addPermissionForAuthentication(Item item, Permission permission) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {

                ObjectIdentity objectIdentity = new ObjectIdentityImpl(item.getClass(), item.getId());

                MutableAcl acl;
                try {
                    acl = (MutableAcl) aclService.readAclById(objectIdentity);
                } catch (final NotFoundException e) {
                    acl = aclService.createAcl(objectIdentity);
                    Sid owner = new PrincipalSid(authentication);
                    acl.setOwner(owner);
                }

                // Add permission at the last position.
                Sid sid = new PrincipalSid(authentication);
                acl.insertAce(acl.getEntries().size(), permission, sid, true);

                aclService.updateAcl(acl);
            }
        });
    }

    @Override
    public void addPermissionForSid(ObjectIdentity objectIdentity, Permission permission, Sid sid) {
        setPermissionForSid(objectIdentity, permission, sid, true);
    }

    @Override
    public void removePermissionForSid(ObjectIdentity objectIdentity, Permission permission, Sid sid) {
        setPermissionForSid(objectIdentity, permission, sid, false);
    }

    private void setPermissionForSid(ObjectIdentity objectIdentity, Permission permission, Sid sid, boolean granting) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        new TransactionTemplate(transactionManager).execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {

                MutableAcl acl;
                //Map<ObjectIdentity, Acl> aclMap;
                try {
                    acl = (MutableAcl) aclService.readAclById(objectIdentity);
                    //aclMap = aclService.readAclsById(Arrays.asList(objectIdentity));
                } catch (final NotFoundException e) {
                    acl = aclService.createAcl(objectIdentity);
                    Sid owner = new PrincipalSid(authentication);
                    acl.setOwner(owner);
                }

                // Add permission at the last position.
                if (granting) {
                    acl.insertAce(acl.getEntries().size(), permission, sid, granting);
                } else {
                    removeAce(acl, sid);
                }

                aclService.updateAcl(acl);
            }
        });
    }

    private void removeAce(MutableAcl acl, Sid sid) {

        List<AccessControlEntry> accessControlEntries = acl.getEntries();
        for (AccessControlEntry accessControlEntry : accessControlEntries) {
            Sid assessSid = accessControlEntry.getSid();
            if (assessSid.equals(sid)) {
                int idx = accessControlEntries.indexOf(accessControlEntry);
                acl.deleteAce(idx);
            }
        }
    }
}
