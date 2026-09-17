package com.back.boundedContext.member.in;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.member.app.MemberService;
import com.back.shared.post.event.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberEventListener {

    private final MemberService memberService;

    public void handle(PostCreatedEvent event) {
        Member member = memberService.findById(event.getPost().getAuthorId()).get();

        member.increaseActivityScore(3);
    }
}
