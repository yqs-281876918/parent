package org.mixed.exam.admin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@PreAuthorize("hasAnyRole('ROLE_adm')")
@Controller
public class NeedExamineController {
}
