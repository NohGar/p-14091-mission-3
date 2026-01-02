/*
package com.back.sbb.myPage;

import com.back.sbb.question.QuestionForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("")
    public String myPage(Model model, Principal principal) {

        MyPageForm myPage = myPageService.getMyPage(principal.getName());
        model.addAttribute("myPage", myPage);

        return "mypage/index";
    }

    @GetMapping("/questions")
    public String myQuestions(Model model, Principal principal) {

        List<QuestionForm> questions =
                myPageService.getQuestions(principal.getName());

        model.addAttribute("questions", questions);
        return "mypage/questions";
    }

    @GetMapping("/answers")
    public String myAnswers(Model model, Principal principal) {

        List<AnswerForm> answers =
                myPageService.getMyAnswers(principal.getName());

        model.addAttribute("answers", answers);
        return "mypage/answers";
    }
}*/
