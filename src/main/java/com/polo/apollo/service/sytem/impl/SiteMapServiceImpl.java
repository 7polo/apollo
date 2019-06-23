package com.polo.apollo.service.sytem.impl;

import com.polo.apollo.dao.system.SiteMapDao;
import com.polo.apollo.entity.dto.SiteMapDto;
import com.polo.apollo.entity.modal.system.SiteMap;
import com.polo.apollo.service.sytem.SiteMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author baoqianyong
 * @date 2019/06/23
 */
@Service
public class SiteMapServiceImpl implements SiteMapService {

    @Autowired
    private SiteMapDao siteMapDao;

    /**
     * 生成 sitmap.xml
     *
     * @param list
     * @return
     */
    @Override
    public String buildXml(List<SiteMapDto> list) {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            Document document = db.newDocument();
            document.setXmlStandalone(true);

            Element root = document.createElement("urlset");
            root.setAttribute("xmlns", "http://www.sitemaps.org/schemas/sitemap/0.9");
            root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            root.setAttribute("xsi:schemaLocation", "http://www.sitemaps.org/schemas/sitemap/0.9  http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd");

            Element url, loc, lastmod, priority;
            for (SiteMapDto item : list) {
                url = document.createElement("url");

                // loc
                loc = document.createElement("loc");
                loc.setTextContent(item.getUrl());
                url.appendChild(loc);

                // lastmod
                if (item.getLastMod() != null) {
                    lastmod = document.createElement("lastmod");
                    lastmod.setTextContent(sdf.format(item.getLastMod()));
                    url.appendChild(lastmod);
                }

                // priority
                priority = document.createElement("priority");
                priority.setTextContent(item.getPriority() + "");
                url.appendChild(priority);


                root.appendChild(url);
            }

            document.appendChild(root);

            // 创建 Transformer对象
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            DOMSource domSource = new DOMSource(document);
            StringWriter sw = new StringWriter();
            StreamResult xmlResult = new StreamResult(sw);
            tf.transform(domSource, xmlResult);
            return sw.toString();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SiteMap getSiteMap() {
        return siteMapDao.selectOne(null);
    }
}
