package com.sottop.itext;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 生成PDF工具类
 * @author :Evan
 */
public class SpecialClientPdf {

    /**
     * 返回Barcode128条形码
     * @param text:条形码内容
     */
    public static void generateBarcode128(Document document, PdfContentByte pdfContentByte, String text) throws DocumentException{
        Barcode128 barcode128 = new Barcode128();
        barcode128.setCode(text);
        document.add(barcode128.createImageWithBarcode(pdfContentByte,null,null));
    }

    /**
     * 返回BarcodeQR二维码图片
     * @param text: 二维码内容
     * @param document: 文本对象
     */
    public static void generateBarcodeQRCode(Document document,String text,int[] size) throws DocumentException {
       BarcodeQRCode qrCode = new BarcodeQRCode(text,size[0],size[1],null);
       Image image = qrCode.getImage();
       document.add(image);
    }

    /**
     * 在固定位置生成小的二维码,内容为批次号
     * @param document: 文本对象
     * @param text: 二维码内容
     */
    public static void generateSmallQRCode(Document document,String text) throws DocumentException{
        BarcodeQRCode qrCode = new BarcodeQRCode(text,30,30,null);
        Image image = qrCode.getImage();
        image.setAbsolutePosition(230f,10f);
        document.add(image);
    }

    /**
     * 输出PDF
     * @param list: 输出内容集合
     * @param document: 文本集合
     * @param cb: PdfContentByte对象
     */
    public static void outputPDF(List list,Document document,PdfContentByte cb,int[] size) throws DocumentException,IOException {
        Iterator iterator = list.iterator();
        Map<String,String> map;
        while(iterator.hasNext()){
            map = (HashMap<String, String>) iterator.next();
            String type = map.get("TYPE")+map.get("FLAG");
            String text = map.get("VALUE1");
            switch (type){
                case CommonValue.TYPE_TEXT:
                    PdfCommon.generateStringText(document,text);
                    break;
                case CommonValue.TYPE_BARC_128:
                    generateBarcode128(document,cb,text);
                    break;
                case CommonValue.TYPE_BARC_QR:
                    generateBarcodeQRCode(document,text,size);
                    break;
                default:
                    break;
            }
            map.clear();
        }
    }
}
