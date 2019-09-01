package com.polo.apollo.web.view;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.polo.apollo.aop.Log;
import com.polo.apollo.common.Constant;
import com.polo.apollo.entity.dto.NoteDto;
import com.polo.apollo.entity.modal.note.Note;
import com.polo.apollo.entity.modal.system.SiteMap;
import com.polo.apollo.entity.vo.NoteVo;
import com.polo.apollo.service.note.NoteService;
import com.polo.apollo.service.note.TagService;
import com.polo.apollo.service.sytem.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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

    @Autowired
    private UserService userService;

    @Autowired
    private SysConfigService sysConfigService;

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
        model.addAttribute(Constant.SYS, sysConfigService.getSysConfig());
        return MODULE + "/about";
    }

    @Log("首页")
    @RequestMapping
    public String index(Model model, @RequestParam(required = false, defaultValue = "1") int page) {
        layout(model);
        // 轮播图配置
        model.addAttribute(Constant.DIC_CAROUSEL, dataDicService.queryListByType(Constant.DIC_CAROUSEL));
        model.addAttribute("blogPage", noteService.queryBlogPage(null, page, 10));
        return MODULE + "/index";
    }

    @Log("标签")
    @RequestMapping("/tag/{tag}.html")
    public String tag(Model model, @ModelAttribute("tag") @PathVariable String tag, @RequestParam(required = false, defaultValue = "1") int page) {
        layout(model);
        // 已发布的 note 才是 blog
        NoteVo vo = new NoteVo().setTagName(tag).setAbbre(true).setPublished(true);
        model.addAttribute("blogPage", noteService.queryPage(vo, page, 10));
        return MODULE + "/tag";
    }

    @Log("搜索")
    @RequestMapping("/search")
    public String search(Model model, @ModelAttribute("search") String search, @RequestParam(required = false, defaultValue = "1") int page) {
        layout(model);
        // 已发布的 note 才是 blog
        NoteVo vo = new NoteVo().setAbbre(true).setPublished(true).setSearch(search);
        IPage<NoteDto> pageResult = noteService.queryPage(vo, page, 10);
        model.addAttribute("blogPage", pageResult);
        return MODULE + "/search";
    }

    @Log("博客")
    @RequestMapping("/blog/{uid}.html")
    public String blog(@PathVariable String uid, Model model) {
        blogLayout(model);
        noteService.addRead(uid);
        Note blog = noteService.queryPublishedById(uid);
        if (blog != null) {
            blog.setContent(null);
            model.addAttribute("blog", blog);
            model.addAttribute("blogTag", tagService.queryByNoteId(uid));
            model.addAttribute("seo", seoService.querySeoByRelateId(Constant.SEO_BLOG, uid));
            //相邻的博客
            model.addAttribute("adjacent", noteService.queryPublishedPreAndNext(blog));
        }
        return MODULE + "/blog";
    }

    private void layout(Model model) {
        model.addAttribute(Constant.SYS, sysConfigService.getSysConfig());

        model.addAttribute("author", userService.getAuthor());
        model.addAttribute("hots", noteService.queryHotBlog(10));
        model.addAttribute("tags", tagService.queryTagCount(true));
        model.addAttribute("friendLinks", dataDicService.queryListByType(Constant.DIC_FRIEND_LINK));
    }

    private void blogLayout(Model model) {
        model.addAttribute(Constant.SYS, sysConfigService.getSysConfig());
        model.addAttribute("author", userService.getAuthor());
        model.addAttribute("tags", tagService.queryTagCount(true));
    }
}
