package com.sottop.itext;


import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 纸筒标签打印测试
 * @author :Evan
 *
 */
public class MainZhiTong {
    public static void main(String[] args)  throws Exception {
        String charsetNameFrom = "UTF-8";
        String charsetNameTo = "ISO-8859-1";
        int[] size = {60,60};
        int[] widAndHei = {598,843};
        Document document = PdfCommon.getDocument(300f,90f,8f,8f,10f,10f);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = PdfWriter.getInstance( document,outputStream );
        document.open();
        PdfContentByte directContent = pdfWriter.getDirectContent();
        List list = getZhiTongList( charsetNameFrom, charsetNameTo );
        ZhiTongPdf.outputZhiTongLabel(list,document,directContent,size);
        document.close();
        byte[] bytes = outputStream.toByteArray();
        //打印多次pdf
        for(int i=0;i<2;i++) {
            PdfCommon.printPdf( bytes,  "Win32 Printer : PDF", 1, widAndHei );
        }
        outputStream.close();
        //https://github.com/evanZhangYiFeng/itext_print.git
    }

    public static List getZhiTongList(String charsetNameFrom,String charsetNameTo) throws UnsupportedEncodingException {
        List list  = new ArrayList(  );
        Map<String,Object> map2 = new HashMap<>(16);
        map2.put("TYPE","TEXT");
        map2.put("VALUE1","供应商Supplier:ShengYi");
        list.add( map2 );

        Map<String,Object> map3 = new HashMap<>(16);
        map3.put("TYPE","TEXT");
        map3.put("VALUE1","卷号LOT NO:2018112382");
        list.add( map3 );

        Map<String,Object> map4 = new HashMap<>(16);
        map4.put("TYPE","TEXT");
        map4.put("VALUE1","生产日期Production Date:2020.01.10");
        list.add( map4 );

        Map<String,Object> map5 = new HashMap<>(16);
        map5.put("TYPE","TEXT");
        map5.put("VALUE1","信息Material:S1150B 106 RC=75%");
        list.add( map5 );

        Map<String,Object> map6 = new HashMap<>(16);
        map6.put("TYPE","BARCQR");
        map6.put("VALUE1",PdfCommon.getBytes("张益峰真牛逼",charsetNameFrom,charsetNameTo));
        list.add( map6 );
        return list;
    }
}
