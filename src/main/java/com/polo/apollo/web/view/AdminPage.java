package com.polo.apollo.web.view;

import com.polo.apollo.common.Constant;
import com.polo.apollo.common.entity.PoloModule;
import com.polo.apollo.common.util.Utils;
import com.polo.apollo.entity.modal.system.User;
import com.polo.apollo.service.note.NoteService;
import com.polo.apollo.service.sytem.LogService;
import com.polo.apollo.service.sytem.SysConfigService;
import com.polo.apollo.service.sytem.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author baoqianyong
 */
@Controller
@RequestMapping(AdminPage.MODULE)
public class AdminPage extends ModuleHandler{

    protected static final String MODULE = "admin";

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserService userService;

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private LogService logService;

    @RequestMapping
    public String index(Model model) {
        model.addAttribute(Constant.SYS, sysConfigService.getSysConfig());
        return MODULE + "/" + "admin";
    }

    @RequestMapping("/{path}")
    public String module(@PathVariable String path,  Model model) {
        moduleInvoke(path, "", model, request);
        return this.urlHandler(path);
    }

    @RequestMapping("/{path}/{name}")
    public String module(@PathVariable String path, @PathVariable String name, Model model) {
        moduleInvoke(path, name, model, request);
        model.addAttribute(Constant.SYS, sysConfigService.getSysConfig());
        return this.urlHandler(String.format("%s/%s", path, name));
    }

    private String urlHandler(String path) {
        return String.format("%s/module/%s", MODULE, path);
    }

    @PoloModule
    private void index(Model model, Map<String, Object> params) {
        model.addAttribute("recentBlogs", noteService.queryRecentNote(10));
        model.addAttribute("publishCount", noteService.publishCount());
        model.addAttribute("weekCount", noteService.queryWeekCount());
        model.addAttribute("visteCount", Utils.obj2Json(logService.queryMonthCount()));
    }

    @PoloModule
    private void noteIndex(Model model, Map<String, Object> params) {
        String noteId = (String) params.get("noteId");
        model.addAttribute("load_by_note", noteId);
    }

    @PoloModule
    private void systemUser(Model model, Map<String, Object> params) {
        User user = userService.queryById(SecurityUtils.getSubject().getPrincipal().toString());
        model.addAttribute("user", user);
    }
}
