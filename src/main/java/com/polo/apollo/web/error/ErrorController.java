package com.polo.apollo.web.error;

import com.polo.apollo.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @author: bqy
 * @date: 2018-12-21 11:49
 */
@Controller
@RequestMapping("/error")
public class ErrorController extends AbstractErrorController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    public ErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    public ErrorController(ErrorAttributes errorAttributes, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorViewResolvers);
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String errorHtml(HttpServletResponse response, Model model) {
        HttpStatus status = getStatus(request);

        model.addAllAttributes(getErrorAttributes(request, false));
        response.setStatus(status.value());
        return "components/error/404";
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Result errorJson(HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        response.setStatus(status.value());
        return Result.error(getErrorAttributes(request, false), "");
    }

    @RequestMapping
    @ResponseBody
    public Object handleError() {
        return this.getErrorAttributes(request, false);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @Override
    protected Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        Map<String, Object> attrs = super.getErrorAttributes(request, includeStackTrace);
        attrs.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return attrs;
    }

}
