package com.polo.apollo.web.view;

import com.polo.apollo.common.entity.PoloModule;
import com.polo.apollo.common.util.Utils;
import com.polo.apollo.common.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author baoqianyong
 */
@Slf4j
public abstract class ModuleHandler {

    private HashMap<String, Method> methodMap = new HashMap<>();

    ModuleHandler() {
        for (Method method : this.getClass().getDeclaredMethods()) {
            if (method.getAnnotation(PoloModule.class) != null) {
                method.setAccessible(true);
                methodMap.put(method.getName(), method);
            }
        }
    }

    void moduleInvoke(String path, String name, Model model, HttpServletRequest request) {
        if (StringUtils.hasLength(path) && name != null) {
            path = path.toLowerCase();
            name = name.toLowerCase();
            String methodName = path + Utils.firstUpper(name);
            Method method = methodMap.get(methodName);
            if (method != null) {
                try {
                    method.invoke(this, model, WebUtils.getParamMap(request));
                } catch (IllegalAccessException e) {
                    log.error("moduleInvoke", e);
                } catch (InvocationTargetException e) {
                    log.error("moduleInvoke", e);
                }
            }
        }
    }
}
