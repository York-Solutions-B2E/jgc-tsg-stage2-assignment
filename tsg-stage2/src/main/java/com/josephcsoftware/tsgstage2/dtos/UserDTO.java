package com.josephcsoftware.tsgstage2.dtos;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.josephcsoftware.tsgstage2.models.Address;
import com.josephcsoftware.tsgstage2.models.Member;
import com.josephcsoftware.tsgstage2.models.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// This is a fusion of User and Member

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phone;
    private Address mailingAddress;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public static UserDTO from(User user, Member member) {
        UserDTO dto = new UserDTO();

        dto.setEmail(user.getEmail());
        dto.setFirstName(member.getFirstName());
        dto.setLastName(member.getLastName());
        dto.setDateOfBirth(member.getDateOfBirth());
        dto.setPhone(member.getPhone());
        dto.setMailingAddress(member.getMailingAddress());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        return dto;
    }
}
