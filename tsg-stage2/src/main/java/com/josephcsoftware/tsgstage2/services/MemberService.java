package com.josephcsoftware.tsgstage2.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import com.josephcsoftware.tsgstage2.SimpleSession;
import com.josephcsoftware.tsgstage2.Utils;
import com.josephcsoftware.tsgstage2.models.Address;
import com.josephcsoftware.tsgstage2.models.Enrollment;
import com.josephcsoftware.tsgstage2.models.Member;
import com.josephcsoftware.tsgstage2.repositories.MemberRepository;

import org.springframework.stereotype.Service;

@Service
public class MemberService {
    
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createMember(UUID userId, SimpleSession session) {
        Member newMember = new Member();
        newMember.setUserId(userId);
        newMember.setFirstName(session.getFirstName());
        newMember.setLastName(session.getLastName());
        newMember.setDateOfBirth(
                                 Utils.randomDateBetween(
                                                         Utils.randomInYear(1975),
                                                         Utils.randomInYear(1995)
                                                         )
                                 );
        newMember.setEmail(session.getEmail());
        // Legally, it's best to use the stereotypical phone number
        newMember.setPhone("(555) 555-5555");

        newMember.setMailingAddress(Utils.randomClientAddress());

        newMember.setEnrollments(new ArrayList<Enrollment>());

        memberRepository.save(newMember);

        return newMember;
    }
}
