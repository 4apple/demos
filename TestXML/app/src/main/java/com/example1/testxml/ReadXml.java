package com.example1.testxml;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by user on 3/25/16.
 */
public class ReadXml extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text = (TextView)findViewById(R.id.textView);

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(getAssets().open("languages.xml"));
            Element element = document.getDocumentElement();
            text.append("\n");
            text.append(element.getAttribute("cat") + "\n");
            NodeList list = element.getElementsByTagName("lan");
            for(int i = 0; i < list.getLength(); i++) {
                Element lan = (Element)list.item(i);
                text.append("\n");
                text.append("--------------ReadXml----------------\n");
                text.append(lan.getAttribute("id") + "\n");
                text.append(lan.getElementsByTagName("name").item(0).getTextContent() + "\n");
                text.append(lan.getElementsByTagName("ide").item(0).getTextContent() + "\n");
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
