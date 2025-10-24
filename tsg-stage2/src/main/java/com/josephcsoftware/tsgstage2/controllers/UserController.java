package com.josephcsoftware.tsgstage2.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import com.josephcsoftware.tsgstage2.dtos.ClaimDTO;
import com.josephcsoftware.tsgstage2.dtos.UserDTO;
import com.josephcsoftware.tsgstage2.models.Claim;
import com.josephcsoftware.tsgstage2.models.Member;
import com.josephcsoftware.tsgstage2.models.Provider;
import com.josephcsoftware.tsgstage2.models.User;
import com.josephcsoftware.tsgstage2.services.UserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@CrossOrigin(origins = "${jgc.cors.origin}")
@RequestMapping("${jgc.project.root}/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private class UserMemberFusion {
        public User user;
        public Member member;

        public UserMemberFusion(User user, Member member) {
            this.user = user;
            this.member = member;
        }
    }

    @GetMapping("auth/me")
    public ResponseEntity<UserDTO> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        UserMemberFusion login = findUserMemberByJWT(jwt);

        if (login == null) {
            // Something went very wrong, so report back
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(UserDTO.from(login.user, login.member));
    }

    @GetMapping("dashboard")
    public ResponseEntity<List<ClaimDTO>> getDashboard(@AuthenticationPrincipal Jwt jwt) {
        UserMemberFusion login = findUserMemberByJWT(jwt);

        if (login == null) {
            // Something went very wrong, so report back
            return ResponseEntity.badRequest().build();
        }

        List<Claim> findings = userService.findClaimsByMemberId(login.member.getId());

        findings.sort(Comparator.comparing(Claim::getReceivedDate));

        ArrayList<ClaimDTO> dto = new ArrayList<ClaimDTO>();

        for (int i = 0; i < findings.size(); i++) {
            Claim claim = findings.get(i);
            Provider provider = userService.findProviderById(claim.getProviderId());
            if (provider == null) {
                // Something is missing in the database; OOPS!
                return ResponseEntity.badRequest().build();
            }
            dto.add(ClaimDTO.from(findings.get(i), provider));
        }
        
        return ResponseEntity.ok(dto);
    }

    private UserMemberFusion findUserMemberByJWT(Jwt jwt) {
        User foundLogin = userService.findUserByJWT(jwt);

        if (foundLogin == null) {
            return null;
        }

        Member foundMember = userService.findMemberByUserId(foundLogin.getId());

        if (foundMember == null) {
            return null;
        }

        return new UserMemberFusion(foundLogin, foundMember);
    }
}
