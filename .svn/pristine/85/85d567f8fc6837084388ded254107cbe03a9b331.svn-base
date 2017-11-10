package com.usi.service.utils;


import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * XML解析工具
 */
public class XMLAnalysis {
    public static Object getRecords(InputStream input, String type) throws Exception {
        if (type.equals("current")) {
            List<Record> list = new ArrayList<Record>();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document parse = builder.parse(input);
            Element documentElement = parse.getDocumentElement();
            NodeList value = documentElement.getElementsByTagName("value");
            int len = value.getLength();
            for (int i = 0; i < len; i++) {
                Node item = value.item(i);
                if (item == null) return list;
                NamedNodeMap attributes = item.getAttributes();
                Node name = attributes.getNamedItem("name");
                String nodeName = name.getTextContent();
                Record record = new Record();
                record.put(nodeName, item.getTextContent());
                list.add(record);
            }
            return list;
        } else if (type.equals("period")) {
            Map<String, List<Record>> map = new HashMap<String, List<Record>>();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document parse = builder.parse(input);
                Element documentElement = parse.getDocumentElement();
                NodeList table = documentElement.getChildNodes();
                Node records = table.item(3);
                NodeList childNodes = records.getChildNodes();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node item = childNodes.item(i);
                    String nodeName = item.getNodeName();
                    if (nodeName.equals("record")) {
                        NodeList childNodes1 = item.getChildNodes();
                        String textContent = childNodes1.item(1).getTextContent();
                        List<Record> list1 = new ArrayList<Record>();
                        map.put(textContent, list1);
                        Node item1 = childNodes1.item(3).getChildNodes().item(1).getChildNodes().item(3);
                        if (item1 != null) {
                            NodeList childNodes2 = item1.getChildNodes();
                            int len = childNodes2.getLength();
                            for (int j = 0; j < len; j++) {
                                Node item2 = childNodes2.item(j);
                                if (item2.getNodeName().equals("record")) {
                                    Record record = new Record();
                                    list1.add(record);
                                    record.put("beginDate", item2.getChildNodes().item(1).getTextContent());
                                    record.put("endDate", item2.getChildNodes().item(3).getTextContent());
                                    record.put("average", item2.getChildNodes().item(5).getTextContent());
                                    record.put("maximum", item2.getChildNodes().item(7).getTextContent());
                                    record.put("minimum", item2.getChildNodes().item(9).getTextContent());
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return map;
        }
        return null;
    }

    private static class Record extends HashMap<String, Object> {

    }
}
