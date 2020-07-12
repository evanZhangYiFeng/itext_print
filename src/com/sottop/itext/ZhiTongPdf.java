package com.sottop.itext;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfContentByte;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 纸筒标签模板
 * @author : Evan
 * @date : 2020-06-30
 */
public class ZhiTongPdf {
    /**
     * 画固定位置二维码
     */
    public static void setFixedBarcodeQRCode(Document document,String text,int[] size) throws DocumentException {
        BarcodeQRCode qrCode = new BarcodeQRCode(text,size[0],size[1],null);
        Image image = qrCode.getImage();
        image.setAbsolutePosition(230f,25f);
        document.add( image );
    }

    /**
     * 画固定位置条形码
     */
    public static void setFixedBarcode128(Document document, PdfContentByte pdfContentByte, String text) throws DocumentException{
        Barcode128 barcode128 = new Barcode128();
        barcode128.setCode(text);
        Image image = barcode128.createImageWithBarcode( pdfContentByte, null, null );
        image.setAbsolutePosition(200f,40f);
        document.add(image);
    }

    /**
     * 输出PDF
     * @param list: 输出内容集合
     * @param document: 文本集合
     * @param cb: PdfContentByte对象
     */
    public static void outputZhiTongLabel(List list, Document document, PdfContentByte cb,int[] size) throws DocumentException,IOException {
        Iterator iterator = list.iterator();
        Map<String,String> map;
        while (iterator.hasNext()){
            map = (HashMap<String, String>) iterator.next();
            String type = map.get("TYPE");
            String text = map.get("VALUE1");
            switch (type){
                case CommonValue.TYPE_TEXT:
                    PdfCommon.generateStringText( document,text );
                break;
                case CommonValue.TYPE_BARC_128:
                    setFixedBarcode128(document,cb,text);
                    break;
                case CommonValue.TYPE_BARC_QR:
                    setFixedBarcodeQRCode(document,text ,size);
                    break;
                default:
                    break;
            }
        }
    }
}
