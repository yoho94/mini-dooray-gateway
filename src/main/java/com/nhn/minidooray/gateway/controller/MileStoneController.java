package com.nhn.minidooray.gateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.mapping.account.prefix}")
public class MileStoneController {
}
