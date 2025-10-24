package com.josephcsoftware.tsgstage2.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.josephcsoftware.tsgstage2.SimpleSession;
import com.josephcsoftware.tsgstage2.Utils;
import com.josephcsoftware.tsgstage2.dtos.ClaimDTO;
import com.josephcsoftware.tsgstage2.models.Claim;
import com.josephcsoftware.tsgstage2.models.ClaimLine;
import com.josephcsoftware.tsgstage2.models.Enrollment;
import com.josephcsoftware.tsgstage2.models.Member;
import com.josephcsoftware.tsgstage2.models.Plan;
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
    private final PlanService planService;
    private final EnrollmentService enrollmentService;

    public UserService(
                       UserRepository userRepository,
                       MemberService memberService,
                       ProviderService providerService,
                       ClaimService claimService,
                       PlanService planService,
                       EnrollmentService enrollmentService
                       ) {
        this.userRepository = userRepository;
        this.memberService = memberService;
        this.providerService = providerService;
        this.claimService = claimService;
        this.planService = planService;
        this.enrollmentService = enrollmentService;
    }

    public Member findMemberByUserId(UUID userId) {
        return memberService.findMemberByUserId(userId);
    }

    public List<Claim> findClaimsByMemberId(UUID memberId) {
        return claimService.findClaimsByMemberId(memberId);
    }

    public Provider findProviderById(UUID providerId) {
        return providerService.findProviderById(providerId);
    }

    // Tries to retrieve a user.
    // If this fails, then a new one is created.
    public User findUserBySession(SimpleSession session) {
        System.out.println("Searching by: " + session.getSubject());
        Optional<User> userResult = userRepository.findByAuthSub(session.getSubject());

        // There should only be 1 or 0 results in this list,
        // but also the time is gettin' crunchyyyyyy, so we're
        // just pulling the first one we find.
        if (userResult.isPresent()) {
            User foundUser = userResult.get();
            if (foundUser != null) {
                System.out.println("Found existing user: " + foundUser.getEmail());
                System.out.println("                     " + foundUser.getId().toString());
                return foundUser;
            }
        }

        // If we made it this far, then no matching user was found,
        // so now we undertake the process of creating a new one,
        // with its own set of demo data for the front-end to show off.
        
        User newUser = new User();
        newUser.setAuthSub(session.getSubject());
        newUser.setAuthProvider(session.getProvider());
        newUser.setEmail(session.getEmail());
        userRepository.save(newUser);

        UUID userId = newUser.getId();

        // Create the member
        Member member = memberService.createMember(userId, session);

        // Create the provider
        Provider[] providers = {
            providerService.createProvider("Dr. Leslie Wimsie"),
            providerService.createProvider("Dr. Royd Reigh"),
            providerService.createProvider("Dr. Neili Notahorse")
        };

        // Collect reasons for expenses
        String[] reasons = Utils.randomReasons();

        // Create the claims;
        // We want about 2 lines per claim, for a proper demo.
        int numberOfClaims = reasons.length / 3;
        Claim[] claimsInProgress = new Claim[numberOfClaims];
        ArrayList<String>[] distributedReasons = new ArrayList[numberOfClaims];

        // Distribute the reasons
        for (int i = 0; i < reasons.length; i++) {
            int destination = i % numberOfClaims;
            if (distributedReasons[destination] == null) {
                distributedReasons[destination] = new ArrayList<String>();
            }
            distributedReasons[destination].add(reasons[i]);
        }

        // Set up buckets for claims each year,
        // which we will need to calculate plan accumulators
        int yearsActive = (LocalDate.now().getYear() + 1) - Utils.START_YEAR;
        ArrayList<Claim>[] timeSortedClaims = new ArrayList[yearsActive];
        for (int i = 0; i < yearsActive; i++) {
            timeSortedClaims[i] = new ArrayList<Claim>();
        }

        // Create the claims
        for (int i = 0; i < numberOfClaims; i++) {
            ArrayList<String> theseReasons = distributedReasons[i];
            String[] reasonsSubArray = theseReasons.toArray(new String[theseReasons.size()]);
            Provider provider = providers[ThreadLocalRandom.current().nextInt(providers.length)];
            Claim newClaim = claimService.createClaim(
                                                      member.getId(),
                                                      provider.getId(),
                                                      reasonsSubArray
                                                      );
            claimsInProgress[i] = newClaim;
            int claimYear = newClaim.getServiceEndDate().getYear();
            for (int j = 0; j < yearsActive; j++) {
                int bucketYear = Utils.START_YEAR + j;
                if (claimYear == bucketYear) {
                    timeSortedClaims[j].add(newClaim);
                }
            }
        }

        // Create plans and enrollments
        ArrayList<Enrollment> enrollments = new ArrayList<Enrollment>();
        for (int i = 0; i < yearsActive; i++) {
            Plan newPlan = planService.createPlan(Utils.START_YEAR + i);
            enrollments.add(enrollmentService.createEnrollment(
                                                               member.getId(),
                                                               newPlan.getId(),
                                                               Utils.START_YEAR + yearsActive,
                                                               i == yearsActive - 1,
                                                               timeSortedClaims[i]
                                                               )
                            );
        }

        memberService.enrollMember(member, enrollments);

        // Aaaaaand done!
        System.out.println("New user created: " + session.getEmail());
        return newUser;
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
