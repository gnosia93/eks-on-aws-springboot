package com.example.shop.controller;

import com.example.shop.entity.Member;
import com.example.shop.dto.MemberDto;
import com.example.shop.service.MemberService;
import com.example.shop.exception.MemberNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value="/member")
@RestController
public class MemberController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MemberService memberService;

    @ResponseBody
    @RequestMapping(value="/{memberId}", method= RequestMethod.GET)
    public MemberDto findMember(@PathVariable Integer memberId) throws MemberNotFoundException {

        logger.trace("trace..." + memberId);
        logger.debug("debug...");
        logger.info("info..." + memberId);
        logger.warn("warn ...");
        logger.error("error ...");

        return memberService.findMember(memberId);
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public int addMember(@RequestBody MemberDto memberDto) {
        Member member = memberService.add(memberDto);
        // member 리턴값이 NULL 인경우 체크 필요.
        return member.getMemberId();
    }

}
