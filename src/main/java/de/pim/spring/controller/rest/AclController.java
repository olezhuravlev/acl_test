package de.pim.spring.controller.rest;

import de.pim.spring.service.ApiGate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/acl")
public class AclController {

    private final ApiGate apiGate;

    // localhost:8080/acl?action=add-permission&username=user&itemid=1
    @PostMapping(params = "action=add-permission")
    public ResponseEntity<Object> addPermission(@RequestParam("username") String userName, @RequestParam("itemid") Long itemId) {
        apiGate.addPermission(userName, itemId);
        return ResponseEntity.ok().build();
    }

    // localhost:8080/acl?action=revoke-permission&username=user&itemid=1
    @PostMapping(params = "action=revoke-permission")
    public ResponseEntity<Object> revokePermission(@RequestParam("username") String userName, @RequestParam("itemid") Long itemId) {
        apiGate.revokePermission(userName, itemId);
        return ResponseEntity.ok().build();
    }
}
