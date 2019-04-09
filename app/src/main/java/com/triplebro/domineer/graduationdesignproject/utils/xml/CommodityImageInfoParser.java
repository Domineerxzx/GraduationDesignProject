package com.triplebro.domineer.graduationdesignproject.utils.xml;

import com.triplebro.domineer.graduationdesignproject.beans.CommodityImageInfo;
import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author Domineer
 * @data 2019/3/24,0:45
 * ----------为梦想启航---------
 * --Set Sell For Your Dream--
 */
public class CommodityImageInfoParser extends DefaultHandler{

    private List<CommodityImageInfo> commodityImageInfoList;
    private CommodityImageInfo commodityImageInfo;

    private StringBuilder commodity_id;
    private StringBuilder commodity_image;
    private StringBuilder phone_number;

    private String nodeName;

    public List<CommodityImageInfo> parseXML(InputStream in) throws
            ParserConfigurationException, SAXException, IOException {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        saxParser.parse(in,this);
        return commodityImageInfoList;
    }


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if(localName.equals("CommodityImageInfoList")){
            commodityImageInfoList = new ArrayList<CommodityImageInfo>();
            commodity_id = new StringBuilder();
            commodity_image = new StringBuilder();
            phone_number = new StringBuilder();
        }else if(localName.equals("commodityImageInfo")) {
            commodityImageInfo = new CommodityImageInfo();
            String value = attributes.getValue(0);
            commodityImageInfo.set_id(Integer.parseInt(value));
        }
        nodeName=localName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws
            SAXException {
        super.endElement(uri, localName, qName);
        if(localName.equals("commodityImageInfo")){
            commodityImageInfoList.add(commodityImageInfo);
        }else if(localName.equals("commodity_id")){
            commodityImageInfo.setCommodity_id(Integer.parseInt(commodity_id.toString().trim()));
            commodity_id.setLength(0);
        }else if(localName.equals("commodity_image")){
            commodityImageInfo.setCommodity_image(commodity_image.toString().trim());
            commodity_image.setLength(0);
        }else if(localName.equals("phone_number")){
            commodityImageInfo.setPhone_number(phone_number.toString().trim());
            phone_number.setLength(0);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws
            SAXException {
        super.characters(ch, start, length);
        if(nodeName.equals("commodity_id")){
            commodity_id.append(ch,start,length);
        }else if(nodeName.equals("commodity_image")){
            commodity_image.append(ch,start,length);
        }else if(nodeName.equals("phone_number")){
            phone_number.append(ch,start,length);
        }
    }
}
