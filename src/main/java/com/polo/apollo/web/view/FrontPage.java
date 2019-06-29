package com.polo.apollo.web.view;

import com.polo.apollo.Application;
import com.polo.apollo.aop.Log;
import com.polo.apollo.common.Constant;
import com.polo.apollo.entity.dto.NoteDto;
import com.polo.apollo.entity.modal.note.Note;
import com.polo.apollo.entity.modal.system.SiteMap;
import com.polo.apollo.entity.modal.system.SkillTag;
import com.polo.apollo.service.note.NoteService;
import com.polo.apollo.service.note.TagService;
import com.polo.apollo.service.sytem.DataDicService;
import com.polo.apollo.service.sytem.SeoService;
import com.polo.apollo.service.sytem.SiteMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author baoqianyong
 * @date 2019/05/19
 */
@Controller
@RequestMapping
public class FrontPage {

    protected static final String MODULE = "front";

    @Autowired
    private NoteService noteService;

    @Autowired
    private TagService tagService;

    @Autowired
    private SeoService seoService;

    @Autowired
    private DataDicService dataDicService;

    @Autowired
    private SiteMapService siteMapService;

    @Log("首页")
    @RequestMapping
    public String index(Model model) {
        layout(model);
        // 轮播图配置
        model.addAttribute(Constant.DIC_CAROUSEL, dataDicService.queryListByType(Constant.DIC_CAROUSEL));
        model.addAttribute("blogPage", noteService.queryPage(new NoteDto(), 1, 10));
        return MODULE + "/index";
    }

    @Log("登录页")
    @RequestMapping("/login")
    public String login() {
        return MODULE + "/login";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    @RequestMapping("sitemap.xml")
    @Log("网站地图")
    public void sitemaps(HttpServletResponse resp) {
        SiteMap siteMap = siteMapService.getSiteMap();
        try {
            PrintWriter out = resp.getWriter();
            resp.setContentType("text/xml;charset=UTF-8");
            String content = siteMap.getContent() == null ? "" : siteMap.getContent();
            out.write(content);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Log("关于")
    @RequestMapping("/about.html")
    public String about(Model model) {
        model.addAttribute(Constant.SYS, Application.sys);
        return MODULE + "/about";
    }

    @Log("标签")
    @RequestMapping("/tag/{tag}.html")
    public String tag(@PathVariable String tag, Model model) {
        layout(model);
        model.addAttribute("tag", tag);
        NoteDto vo = new NoteDto();
//        vo.setTagName(tag);
        model.addAttribute("blogPage", noteService.queryPage(vo, 1, 10));
        return MODULE + "/tag";
    }

    @Log("博客")
    @RequestMapping("/blog/{uid}.html")
    public String blog(@PathVariable String uid, Model model) {
        layout(model);
        noteService.addRead(uid);
        Note blog = noteService.queryPublishedById(uid);
        if (blog != null) {
            model.addAttribute("blog", blog);
            model.addAttribute("seo", seoService.querySeoByRelateId(Constant.SEO_BLOG, uid));
            //相邻的博客
            model.addAttribute("adjacent", noteService.queryPublishedPreAndNext(blog));
        }
        return MODULE + "/blog";
    }

    private void layout(Model model) {
        model.addAttribute(Constant.SYS, Application.sys);
//        model.addAttribute("hots", blogService.queryHotBlog(5));
        model.addAttribute("tags", tagService.queryTagCount(true));
        model.addAttribute("friendLinks", dataDicService.queryListByType(Constant.DIC_FRIEND_LINK));
        model.addAttribute("skills", getList());
    }

    private List<SkillTag> getList() {
        List<SkillTag> list = new ArrayList<>();
        for (String skill : new String[]{"Java", "Spring", "MySQL", "Linux", "Docker"}) {
            SkillTag tag = new SkillTag();
            tag.setName(skill);
            list.add(tag);
        }
        return list;
    }
}
