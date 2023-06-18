package com.nhn.minidooray.gateway.controller;

import com.nhn.minidooray.gateway.domain.Account;
import com.nhn.minidooray.gateway.domain.request.AccountCreateRequest;
import com.nhn.minidooray.gateway.exception.BadRequestException;
import com.nhn.minidooray.gateway.service.AccountApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.mapping.account.prefix}")
public class AccountController {
    private final AccountApiService accountApiService;

    @GetMapping("${com.nhn.minidooray.mapping.account.write}")
    public String getWriteForm(Model model) {
        model.addAttribute("account", new AccountCreateRequest());
        return "account/form";
    }

    @PostMapping("${com.nhn.minidooray.mapping.account.write}")
    public String postWrite(@Valid AccountCreateRequest accountCreateRequest,
                            BindingResult bindingResult,
                            Model model) {

        if (bindingResult.hasErrors()) {
            // TODO 타임리프에서 bindingresult 받아서 에러 메세지 표기하는거 ..
            return "account/form";
        }

        try {
            accountApiService.addAccount(accountCreateRequest.toAccount());
        } catch (BadRequestException e) {
            model.addAttribute("account", new AccountCreateRequest());
            model.addAttribute("failMsg", e.getMessage());
            return "account/form";
        }

        return "redirect:/redirect-index";
    }
    @GetMapping("${com.nhn.minidooray.mapping.account.modify}")
    public String getModifyForm(Authentication authentication, Model model) {
        Account account = accountApiService.getAccountById(authentication.getName());

        model.addAttribute("account", AccountCreateRequest.builder()
                .id(account.getId())
                .name(account.getName())
                .email(account.getEmail())
                .build());
        return "account/form";
    }

    @PostMapping("${com.nhn.minidooray.mapping.account.modify}")
    public String postModify(Authentication authentication,
                            @Valid AccountCreateRequest accountCreateRequest,
                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // TODO error 처리
        }

        accountCreateRequest.setId(authentication.getName());
        accountApiService.updateAccount(accountCreateRequest.toAccount());

        return "redirect:/redirect-index";
    }


}
