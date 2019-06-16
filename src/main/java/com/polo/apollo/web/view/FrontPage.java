package com.polo.apollo.web.view;

import com.polo.apollo.Application;
import com.polo.apollo.common.Constant;
import com.polo.apollo.entity.modal.system.SkillTag;
import com.polo.apollo.service.blog.BlogService;
import com.polo.apollo.service.note.TagService;
import com.polo.apollo.service.sytem.DataDicService;
import com.polo.apollo.service.sytem.FriendLinkService;
import com.polo.apollo.service.sytem.SeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private BlogService blogService;

    @Autowired
    private TagService tagService;

    @Autowired
    private FriendLinkService friendLinkService;

    @Autowired
    private SeoService seoService;

    @Autowired
    private DataDicService dataDicService;

    @RequestMapping
    public String index(Model model) {
        layout(model);
        // 轮播图配置
        model.addAttribute(Constant.DIC_CAROUSEL, dataDicService.queryListByType(Constant.DIC_CAROUSEL));
        return MODULE + "/index";
    }

    @RequestMapping("/login")
    public String login() {
        return MODULE + "/login";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    @RequestMapping("sitemap.xml")
    @ResponseBody
    public String sitemaps() {
        // todo 网站地图
        return "功能暂未开发";
    }

    @RequestMapping("/about.html")
    public String about(Model model) {
        model.addAttribute(Constant.SYS, Application.sys);
        return MODULE + "/about";
    }

    @RequestMapping("/tag/{tag}.html")
    public String tag(@PathVariable String tag, Model model) {
        layout(model);
        model.addAttribute("tag", tag);
        return MODULE + "/tag";
    }

    @RequestMapping("/blog/{uid}.html")
    public String blog(@PathVariable String uid, Model model) {
        layout(model);

        blogService.updateBlogRead(uid);
        model.addAttribute("blog", blogService.queryById(uid));
        model.addAttribute("seo", seoService.querySeoByRelateId(Constant.SEO_BLOG, uid));
        return MODULE + "/blog";
    }

    private void layout(Model model) {
        model.addAttribute(Constant.SYS, Application.sys);
        model.addAttribute("hots", blogService.queryHotBlog(5));
        model.addAttribute("tags", tagService.queryBlogCount());
        model.addAttribute("friendLinks", friendLinkService.queryListBySize(10));
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
