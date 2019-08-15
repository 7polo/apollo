package com.polo.apollo.web.view;

import com.polo.apollo.Application;
import com.polo.apollo.common.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author baoqianyong
 */
@Controller
@RequestMapping(AdminPage.MODULE)
public class AdminPage {

    protected static final String MODULE = "admin";

    @RequestMapping
    public String index(Model model) {
        model.addAttribute(Constant.SYS, Application.sys);
        return MODULE + "/" + "admin";
    }

    @RequestMapping("/note/index")
    public String module(Model model, String noteId) {
        model.addAttribute("load_by_note", noteId);
        return this.module(String.format("%s/%s", "note", "index"));
    }

    @RequestMapping("/{path}")
    public String module(@PathVariable String path) {
        return String.format("%s/module/%s", MODULE, path);
    }

    @RequestMapping("/{path}/{name}")
    public String module(@PathVariable String path, @PathVariable String name, Model model) {
        model.addAttribute(Constant.SYS, Application.sys);
        return this.module(String.format("%s/%s", path, name));
    }
}
