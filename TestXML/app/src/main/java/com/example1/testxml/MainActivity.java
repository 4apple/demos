package com.example1.testxml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class MainActivity extends AppCompatActivity {
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView)findViewById(R.id.textView);

        //Read data from xml
/*
        try {

        /*
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            try {
                builder = builderFactory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            Document document = builder.parse(getAssets().open("languages.xml"));
            Element element = document.getDocumentElement();
            NodeList list = element.getElementsByTagName("lan");
            for(int i = 0; i < list.getLength(); i++){
                Element lan = (Element)list.item(i);
                text.append(lan.getAttribute("id")+"\n");
                text.append(lan.getElementsByTagName("name").item(0).getTextContent()+"\n");
                text.append(lan.getElementsByTagName("ide").item(0).getTextContent()+"\n");

            }

        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }*/
            /*
            <?xml version="1.0" encoding="utf-8"?>
            <languages cat = "it">
                <lan id="1">
                    <name>Java</name>
                    <ide>Eclipse</ide>
                </lan>
                <lan id="2">
                    <name>Swift</name>
                    <ide>Xcode</ide>
                </lan>
                <lan id="3">
                    <name>C#</name>
                    <ide>Visual Studio</ide>
                </lan>
            </languages>
            * */
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document newxml = builder.newDocument();
            Element languages = newxml.createElement("languages");
            languages.setAttribute("cat", "it");

            Element lan1 = newxml.createElement("lan");
            lan1.setAttribute("id", "1");
            Element name1 = newxml.createElement("name");
            name1.setTextContent("java");
            Element ide1 = newxml.createElement("ide");
            ide1.setTextContent("Eclipse");
            lan1.appendChild(name1);
            lan1.appendChild(ide1);
            languages.appendChild(lan1);


            Element lan2 = newxml.createElement("lan");
            lan1.setAttribute("id", "2");
            Element name2 = newxml.createElement("name");
            name2.setTextContent("C#");
            Element ide2 = newxml.createElement("ide");
            ide2.setTextContent("Visual Studio");
            lan2.appendChild(name2);
            lan2.appendChild(ide2);
            languages.appendChild(lan2);

            Element lan3 = newxml.createElement("lan");
            lan3.setAttribute("id", "3");
            Element name3 = newxml.createElement("name");
            name3.setTextContent("Swift");
            Element ide3 = newxml.createElement("ide");
            ide3.setTextContent("Xcode");
            lan3.appendChild(name3);
            lan3.appendChild(ide3);
            languages.appendChild(lan3);


            newxml.appendChild(languages);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = null;
            try {
                transformer = transformerFactory.newTransformer();
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            }
            transformer.setOutputProperty("encoding", "UTF-8");
            StringWriter sw = new StringWriter();

            try {
                transformer.transform(new DOMSource(newxml),new StreamResult(sw));
            } catch (TransformerException e) {
                e.printStackTrace();
            }
            text.setText(sw.toString());

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }


}
