package de.pim.spring.service;

import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import de.pim.spring.model.Item;

@Service
@Transactional
public class AclPermissionService {

    private final MutableAclService aclService;
    private final PlatformTransactionManager transactionManager;

    public AclPermissionService(MutableAclService aclService, PlatformTransactionManager transactionManager) {
        this.aclService = aclService;
        this.transactionManager = transactionManager;
    }

    public void addPermissionForUser(Item item, Permission permission, Authentication authentication) {
        Sid sid = new PrincipalSid(authentication.getName());
        addPermissionForSid(item, permission, sid);
    }

    public void addPermissionForAuthority(Item item, Permission permission, GrantedAuthority grantedAuthority) {
        Sid sid = new GrantedAuthoritySid(grantedAuthority);
        addPermissionForSid(item, permission, sid);
    }

    private void addPermissionForSid(Item item, Permission permission, Sid sid) {

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
                acl.insertAce(acl.getEntries().size(), permission, sid, true);

                aclService.updateAcl(acl);
            }
        });
    }
}
