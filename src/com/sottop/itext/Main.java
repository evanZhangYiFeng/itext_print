package com.sottop.itext;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：Evan
 * 打印测试，ftpc具体写法参考这边的写法
 */
public class Main{

    public static void main(String[] args)  throws Exception {
        String charsetNameFrom = "UTF-8";
        String charsetNameTo = "ISO-8859-1";
        int[] size = {60,60};
        int[] widAndHei = {598,842};
        Document document = PdfCommon.getDocument(270f,230f,8f,8f,10f,10f);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = PdfWriter.getInstance( document,outputStream );

        document.open();
        PdfContentByte directContent = pdfWriter.getDirectContent();
        List list = getList(charsetNameFrom,charsetNameTo);
        SpecialClientPdf.outputPDF(list,document,directContent,size);
        SpecialClientPdf.generateSmallQRCode(document,"Evan666");
        document.close();
        byte[] bytes = outputStream.toByteArray();
        //打印pdf
        for(int i=0;i<2;i++) {
            PdfCommon.printPdf( bytes, "Win32 Printer : PDF", 1, widAndHei );
        }
        outputStream.close();
    }

    public static List getList(String charsetNameFrom,String charsetNameTo ) throws Exception{
        List list = new ArrayList();
        Map<String,Object> map1 = new HashMap<>(16);
        map1.put("ID",1);
        map1.put("TYPE","BARC");
        map1.put("FLAG","128");
        map1.put("VALUE1","evan666 i am iron man");
        list.add(map1);
        Map<String,Object> map2 = new HashMap<>(16);
        map2.put("ID",2);
        map2.put("TYPE","TEXT");
        map2.put("FLAG","");
        map2.put("VALUE1","规格:木之伸曲,水之掩藏");
        list.add(map2);

        Map<String,Object> map4 = new HashMap<>(16);
        map4.put("ID",4);
        map4.put("TYPE","TEXT");
        map4.put("FLAG","");
        map4.put("VALUE1", PdfCommon.getBytes("L201870012",charsetNameFrom,charsetNameTo));
        list.add(map4);

        Map<String,Object> map3 = new HashMap<>(16);
        map3.put("ID",3);
        map3.put("TYPE","BARC");
        map3.put("FLAG","QR");
        map3.put("VALUE1", PdfCommon.getBytes("老铁真的666",charsetNameFrom,charsetNameTo));
        list.add(map3);
        return list;
    }
}
