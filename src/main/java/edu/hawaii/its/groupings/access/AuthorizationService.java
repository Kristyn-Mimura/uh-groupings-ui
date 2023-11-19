package edu.hawaii.its.groupings.access;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import edu.hawaii.its.api.controller.GroupingsRestController;
import edu.hawaii.its.groupings.service.UhUuidCheckerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    @Autowired
    GroupingsRestController groupingsRestController;

    @Autowired
    UhUuidCheckerService uhUuidCheckerService;
    private static final Log logger = LogFactory.getLog(AuthorizationService.class);

    /**
     * Assign roles to user
     */

    public RoleHolder fetchRoles(String uhUuid, String uid) {
        RoleHolder roleHolder = new RoleHolder();
        roleHolder.add(Role.ANONYMOUS);

        //Determine if uhUuid is valid
        if (uhUuidCheckerService.isValidUhUuid(uhUuid, uid)) {
            roleHolder.add(Role.UH);
        }

        if (uhUuidCheckerService.isDepartmentAccount(uhUuid, uid)) {
            roleHolder.add(Role.DEPARTMENT);
        }
        //Determine if user is an owner.
        if (checkResult(groupingsRestController.hasOwnerPrivs())) {
            roleHolder.add(Role.OWNER);
        }

        //Determine if a user is an admin.
        if (checkResult(groupingsRestController.hasAdminPrivs())) {
            roleHolder.add(Role.ADMIN);
        }

        logger.info("fetchRoles: username: " + uid + " " + roleHolder.getAuthorities() + ";");
        return roleHolder;
    }

    /**
     * Return a boolean based on parsed response.
     */
    private boolean checkResult(ResponseEntity response) {
        if (response == null || response.getBody() == null) {
            return false;
        }

        return Boolean.parseBoolean((String) response.getBody());
    }
}
