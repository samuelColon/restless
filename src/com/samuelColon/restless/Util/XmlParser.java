package com.samuelColon.restless.Util;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.*;

public class XmlParser {

    public static Document parseDocument (InputStream inputStream) throws ParserConfigurationException, org.xml.sax.SAXException, IOException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(inputStream));
        return document;
    }

    public static Document parseDocument (Reader reader) throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(reader));
        return document;
    }

    public static List<Element> getAllElements (Element element, String tagName) {
        ArrayList<Element> elements = new ArrayList<>();
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String nodeName = node.getNodeName();
                if (nodeName != null && nodeName.equals(tagName)) {
                    elements.add((Element) node);
                }
            }
        }
        return elements;
    }

    private void inspectXml (Node node) {
        //        printNode(node);
        if (node.hasAttributes()) {
            NamedNodeMap nodeMap = node.getAttributes();
            for (int i = 0; i < nodeMap.getLength(); ++ i) {
                inspectXml(nodeMap.item(i));
            }
        }
        if (node.hasChildNodes()) {
            NodeList nodeList = node.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); ++ i) {
                inspectXml(nodeList.item(i));
            }
        }
    }
}