package com.josephcsoftware.tsgstage2.repositories;

import java.util.UUID;

import com.josephcsoftware.tsgstage2.models.Member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    //
}
