package com.nhn.minidooray.gateway.controller;

import com.nhn.minidooray.gateway.domain.Account;
import com.nhn.minidooray.gateway.domain.request.ProjectAccountCreateRequest;
import com.nhn.minidooray.gateway.domain.request.ProjectAccountModifyRequest;
import com.nhn.minidooray.gateway.domain.response.AccountByProjectResponse;
import com.nhn.minidooray.gateway.service.AccountApiService;
import com.nhn.minidooray.gateway.service.TaskApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.mapping.project.prefix}/{projectId}${com.nhn.minidooray.mapping.project-account.prefix}")
public class ProjectAccountController {

    private final TaskApiService taskApiService;
    private final AccountApiService accountApiService;

    @GetMapping("${com.nhn.minidooray.mapping.project-account.list}")
    public String getList(@PathVariable Long projectId, Model model, @PageableDefault Pageable pageable) {
        Page<AccountByProjectResponse> page = taskApiService.getAccountList(projectId, pageable);

        model.addAttribute("page", page);
        model.addAttribute("projectAccountList", page.getContent());
        model.addAttribute("totalCount", page.getTotalElements());
        model.addAttribute("pageCount", page.getTotalPages());

        Page<Account> accounts = accountApiService.getAccounts(PageRequest.of(0, 5));
        model.addAttribute("accountList", accounts.getContent());
        model.addAttribute("accountTotalCount", accounts.getTotalElements());
        model.addAttribute("accountPageCount", accounts.getTotalPages());

        model.addAttribute(projectId);
        return "project/account/list";
    }

    @GetMapping("${com.nhn.minidooray.mapping.project-account.account-list}")
    @ResponseBody
    public Page<Account> getAccountList(@PageableDefault Pageable pageable) {
        return accountApiService.getAccounts(pageable);
    }

    @GetMapping("${com.nhn.minidooray.mapping.project-account.read}")
    public String getAccount(@PathVariable Long projectId, Model model) {
        model.addAttribute(projectId);
        return "project/account/list";
    }

    @PostMapping("${com.nhn.minidooray.mapping.project-account.write}")
    public String writeAccount(@PathVariable Long projectId,
                               @Valid ProjectAccountCreateRequest projectAccountCreateRequest,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
//       TODO     throw new BadRequestException();
        }

        taskApiService.writeAccount(projectId, projectAccountCreateRequest);

        return redirectList(projectId);
    }

    @PostMapping("${com.nhn.minidooray.mapping.project-account.modify}")
    public String modifyAccount(@PathVariable Long projectId,
                                @Param("accountId") String accountId,
                                @Valid ProjectAccountModifyRequest projectAccountModifyRequest,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // TODO thr new
        }

        taskApiService.modifyAccount(projectId, accountId, projectAccountModifyRequest);

        return redirectList(projectId);
    }

    @GetMapping("${com.nhn.minidooray.mapping.project-account.delete}")
    public String deleteAccount(@PathVariable Long projectId, @Param("accountId") String accountId) {
        taskApiService.deleteAccount(projectId, accountId);

        return redirectList(projectId);
    }

    private String redirectList(Long projectId) {
        return "redirect:/project/" + projectId + "/account/list";
    }

}
