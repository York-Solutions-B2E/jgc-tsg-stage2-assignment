package com.josephcsoftware.tsgstage2.services;

import java.util.List;
import java.util.UUID;

import com.josephcsoftware.tsgstage2.SimpleSession;
import com.josephcsoftware.tsgstage2.Utils;
import com.josephcsoftware.tsgstage2.models.Claim;
import com.josephcsoftware.tsgstage2.models.ClaimLine;
import com.josephcsoftware.tsgstage2.models.Member;
import com.josephcsoftware.tsgstage2.models.Provider;
import com.josephcsoftware.tsgstage2.models.User;
import com.josephcsoftware.tsgstage2.repositories.UserRepository;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    // Other services for creating new data
    private final MemberService memberService;
    private final ProviderService providerService;
    private final ClaimService claimService;

    public UserService(
                       UserRepository userRepository,
                       MemberService memberService,
                       ProviderService providerService,
                       ClaimService claimService
                       ) {
        this.userRepository = userRepository;
        this.memberService = memberService;
        this.providerService = providerService;
        this.claimService = claimService;
    }

    // Tries to retrieve a user.
    // If this fails, then a new one is created.
    public User findUserBySession(SimpleSession session) {
        List<User> userList = userRepository
            .findByEmailAndAuthSubAndAuthProvider(
                                                  session.getEmail(),
                                                  session.getSubject(),
                                                  session.getProvider()
                                                  );

        if (userList.size() > 0) { // SHOULD be 1, but we're not picky
            User foundUser = userList.get(0);
            return foundUser;
        }

        // None found; create a new one
        User newUser = new User();
        newUser.setAuthSub(session.getSubject());
        newUser.setAuthProvider(session.getProvider());
        newUser.setEmail(session.getEmail());
        userRepository.save(newUser);

        UUID userId = newUser.getId();

        // Create the member
        Member member = memberService.createMember(userId, session);

        // Create the provider
        Provider provider = providerService.createProvider();

        // Collect reasons for expenses
        String[] reasons = Utils.randomReasons();

        // Create the claims;
        // We want about 2 lines per claim, for a proper demo.
        int numberOfClaims = reasons.length / 2;
        Claim[] claimsInProgress = new Claim[numberOfClaims];

        for (int i = 0; i < numberOfClaims; i++) {
            claimsInProgress[i] = claimService.beginClaim(member.getId(), provider.getId());
        }

        for (int i = 0; i < reasons.length; i++) {
            // Spread out the reasons, and leave none unused
            int destination = i % numberOfClaims;
            List<ClaimLine> lines = claimsInProgress[destination].getLines();
            //TODO: Create claim line and add to list
            // I suspect lists are cloned within getters,
            // as this would make sense for concurrency,
            // so assume we need to send the clone back in after mods.
            claimsInProgress[destination].setLines(lines);
        }

        return null;
    }

    // If a user has an account, then do nothing
    // If a user has no account, then make one from the JWT
    public User findUserByJWT(Jwt jwt) {
        try {
            SimpleSession session = new SimpleSession(jwt);

            User user = this.findUserBySession(session);
            if (user == null) {
                System.out.println("Failed to find user: " + session.getEmail());
                System.out.println("                     " + session.getProvider());
                System.out.println("                     " + session.getSubject());
                return null;
            }
        
            return user;
        } catch (Exception ex) {
            // SimpleSession creation already has some error handling,
            // so if we still get an error here, then something has gone
            // VERY wrong.
            System.out.println(ex);
            return null;
        }
    }
}
