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

    @Autowired
    MemberRepository memberRepository;

    @Transactional
    public Member add(MemberDto memberDto) {
        return memberRepository.save(memberDto.toEntity());
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
