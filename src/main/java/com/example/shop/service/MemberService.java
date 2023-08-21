package com.example.shop.service;

import com.example.shop.entity.Member;
import com.example.shop.dto.MemberDto;
import com.example.shop.repository.MemberRepository;
import com.example.shop.exception.MemberNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    // 서비스 메소스 호출시 In/Out 는 Dto 이다.
    // Controller 는 Entity 의 존재를 알지 못한다.
    // 서비스는 트랜잭션을 처리한다.

    @Autowired
    MemberRepository memberRepository;

    @Transactional
    public MemberDto add(MemberDto memberDto) {
        Member member = memberRepository.save(memberDto.toEntity());

        return MemberDto.builder()
                .memberId(member.getMemberId())
                .password(member.getPassword())
                .name(member.getName())
                .emailAddress(member.getEmailAddress())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }

    public MemberDto findMember(Integer memberId) throws MemberNotFoundException {
        Optional<Member> optMember = memberRepository.findById(memberId);

        Member member = optMember.orElseThrow(MemberNotFoundException::new);
        return MemberDto.builder()
                .memberId(member.getMemberId())
                .password(member.getPassword())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .emailAddress(member.getEmailAddress())
                .build();
    }
}
