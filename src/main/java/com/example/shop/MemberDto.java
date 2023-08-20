package com.example.shop;

import jakarta.persistence.GeneratedValue;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDto {
    private int memberId;
    private String password;
    private String name;
    private String phoneNumber;
    private String emailAddress;

    @Builder
    public MemberDto(int memberId, String password, String name, String phoneNumber, String emailAddress ) {
        this.memberId = memberId;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public Member toEntity() {
        return Member.builder()
                .password(this.password)
                .name(this.name)
                .phoneNumber(this.phoneNumber)
                .emailAddress(this.emailAddress)
                .build();
    }
}
