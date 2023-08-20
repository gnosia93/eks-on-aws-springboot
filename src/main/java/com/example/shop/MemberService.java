package com.example.shop;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;

@RequiredArgsConstructor
@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Transactional
    public Member add(MemberDto memberDto) {
        return memberRepository.save(memberDto.toEntity());
    }

}
