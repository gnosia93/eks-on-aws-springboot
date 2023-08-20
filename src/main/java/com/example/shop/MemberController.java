package com.example.shop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping(value="/member")
@RestController
public class MemberController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MemberService memberService;
    @RequestMapping(value="/{memberId}", method= RequestMethod.GET)
    public String findMember(@PathVariable Integer memberId) {

        logger.trace("trace..." + memberId);
        logger.debug("debug...");
        logger.info("info..." + memberId);
        logger.warn("warn ...");
        logger.error("error ...");

        return "dummy member";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public int addMember(@RequestBody MemberDto memberDto) {
        Member member = memberService.add(memberDto);
        // member 리턴값이 NULL인경우 체크 필요.
        return member.getMemberId();
    }

}
