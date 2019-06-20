package com.polo.apollo.web.view;

import com.polo.apollo.Application;
import com.polo.apollo.aop.Log;
import com.polo.apollo.common.Constant;
import com.polo.apollo.entity.modal.blog.Blog;
import com.polo.apollo.entity.modal.system.SkillTag;
import com.polo.apollo.service.blog.BlogService;
import com.polo.apollo.service.note.TagService;
import com.polo.apollo.service.sytem.DataDicService;
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
    private SeoService seoService;

    @Autowired
    private DataDicService dataDicService;

    @Log("首页")
    @RequestMapping
    public String index(Model model) {
        layout(model);
        // 轮播图配置
        model.addAttribute(Constant.DIC_CAROUSEL, dataDicService.queryListByType(Constant.DIC_CAROUSEL));
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
    @ResponseBody
    @Log("网站地图")
    public String sitemaps() {
        // todo 网站地图
        return "功能暂未开发";
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
        return MODULE + "/tag";
    }

    @Log("博客")
    @RequestMapping("/blog/{uid}.html")
    public String blog(@PathVariable String uid, Model model) {
        layout(model);
        blogService.updateBlogRead(uid);
        Blog blog = blogService.queryById(uid);
        if (blog != null) {
            model.addAttribute("blog", blog);
            model.addAttribute("seo", seoService.querySeoByRelateId(Constant.SEO_BLOG, uid));
            //相邻的博客
            model.addAttribute("adjacent", blogService.queryPreAndNextBlog(blog));
        }
        return MODULE + "/blog";
    }

    private void layout(Model model) {
        model.addAttribute(Constant.SYS, Application.sys);
        model.addAttribute("hots", blogService.queryHotBlog(5));
        model.addAttribute("tags", tagService.queryBlogCount());
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
