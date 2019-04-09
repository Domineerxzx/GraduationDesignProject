package com.triplebro.domineer.graduationdesignproject.utils.xml;

import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.beans.TypeConcreteInfo;

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
public class CommodityInfoParser extends DefaultHandler{

    private List<CommodityInfo> commodityInfoList;
    private CommodityInfo commodityInfo;

    private StringBuilder commodity_name;
    private StringBuilder price;
    private StringBuilder commodity_image;
    private StringBuilder type_generalize_id;
    private StringBuilder type_concrete_id;
    private StringBuilder phone_number;

    private String nodeName;

    public List<CommodityInfo> parseXML(InputStream in) throws
            ParserConfigurationException, SAXException, IOException {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        saxParser.parse(in,this);
        return commodityInfoList;
    }


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if(localName.equals("CommodityInfoList")){
            commodityInfoList = new ArrayList<CommodityInfo>();
            commodity_name = new StringBuilder();
            price = new StringBuilder();
            commodity_image = new StringBuilder();
            type_generalize_id = new StringBuilder();
            type_concrete_id = new StringBuilder();
            phone_number = new StringBuilder();
        }else if(localName.equals("commodityInfo")) {
            commodityInfo = new CommodityInfo();
            String value = attributes.getValue(0);
            commodityInfo.setCommodity_id(Integer.parseInt(value));
        }
        nodeName=localName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws
            SAXException {
        super.endElement(uri, localName, qName);
        if(localName.equals("commodityInfo")){
            commodityInfoList.add(commodityInfo);
        }else if(localName.equals("commodity_name")){
            commodityInfo.setCommodity_name(commodity_name.toString().trim());
            commodity_name.setLength(0);
        }else if(localName.equals("price")){
            commodityInfo.setPrice(Integer.parseInt(price.toString().trim()));
            price.setLength(0);
        }else if(localName.equals("commodity_image")){
            commodityInfo.setCommodity_image(commodity_image.toString().trim());
            commodity_image.setLength(0);
        }if(localName.equals("type_generalize_id")){
            commodityInfo.setType_generalize_id(Integer.parseInt(type_generalize_id.toString().trim()));
            type_generalize_id.setLength(0);
        }else if(localName.equals("type_concrete_id")){
            commodityInfo.setType_concrete_id(Integer.parseInt(type_concrete_id.toString().trim()));
            type_concrete_id.setLength(0);
        }else if(localName.equals("phone_number")){
            commodityInfo.setPhone_number(phone_number.toString().trim());
            phone_number.setLength(0);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws
            SAXException {
        super.characters(ch, start, length);
        if(nodeName.equals("commodity_name")){
            commodity_name.append(ch,start,length);
        }else if(nodeName.equals("price")){
            price.append(ch,start,length);
        }else if(nodeName.equals("commodity_image")){
            commodity_image.append(ch,start,length);
        }else if(nodeName.equals("type_generalize_id")){
            type_generalize_id.append(ch,start,length);
        }else if(nodeName.equals("type_concrete_id")){
            type_concrete_id.append(ch,start,length);
        }else if(nodeName.equals("phone_number")){
            phone_number.append(ch,start,length);
        }
    }
}
