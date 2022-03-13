package org.mixed.exam.verify.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@PreAuthorize("hasAnyRole('ROLE_adm')")
@Controller
public class NeedExamineController {
}
