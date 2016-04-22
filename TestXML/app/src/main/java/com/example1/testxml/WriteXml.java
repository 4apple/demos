package com.example1.testxml;

import android.app.Activity;
import android.os.Bundle;
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

/**
 * Created by user on 3/25/16.
 */
public class WriteXml extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text = (TextView) findViewById(R.id.textView);

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element languages = document.createElement("languages");
            languages.setAttribute("cat", "it");

            Element lan1 = document.createElement("lan");
            lan1.setAttribute("id", "1");
            Element name1 = document.createElement("name");
            name1.setTextContent("Java");
            Element ide1 = document.createElement("ide");
            ide1.setTextContent("Eclipse");
            lan1.appendChild(name1);
            lan1.appendChild(ide1);
            languages.appendChild(lan1);

            Element lan2 = document.createElement("lan");
            lan2.setAttribute("id", "2");
            Element name2 = document.createElement("name");
            name2.setTextContent("Swift");
            Element ide2 = document.createElement("ide");
            ide2.setTextContent("Xcode");
            lan2.appendChild(name2);
            lan2.appendChild(ide2);
            languages.appendChild(lan2);

            Element lan3 = document.createElement("lan");
            lan3.setAttribute("id", "3");
            Element name3 = document.createElement("name");
            name3.setTextContent("C#");
            Element ide3 = document.createElement("ide");
            ide3.setTextContent("Visual Studio");
            lan3.appendChild(name3);
            lan3.appendChild(ide3);
            languages.appendChild(lan3);

            document.appendChild(languages);


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer;
            try {
                transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty("encoding", "UTF-8");
                StringWriter sw = new StringWriter();

                try {
                    transformer.transform(new DOMSource(document),new StreamResult(sw));
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
                text.setText(sw.toString());
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            }


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
