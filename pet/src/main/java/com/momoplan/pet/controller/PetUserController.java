package com.momoplan.pet.controller;

import com.momoplan.pet.domain.PetUser;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/petusers")
@Controller
@RooWebScaffold(path = "petusers", formBackingObject = PetUser.class)
public class PetUserController {
}

