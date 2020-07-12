package com.sottop.itext;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;

import javax.print.PrintService;
import java.awt.print.*;
import java.io.*;

/**
 * 生成pdf要素的公共方法
 * @author Administrator
 */
public class PdfCommon {
    /**
     *初始化文本
     * @param width: 文本页面长度
     * @param height: 文本页面宽度
     * @param marginLeft: 距离左边框距离
     * @param marginRight:距离右边框距离
     * @param marginTop: 距离上边框距离
     * @param marginBottom: 距离下边框距离
     * @return: 返回文本对象
     */
    public static Document getDocument(float width, float height, float marginLeft, float marginRight, float marginTop, float marginBottom){
        Rectangle rectangle = new Rectangle(width,height);
        Document document = new Document(rectangle,marginLeft,marginRight,marginTop,marginBottom);
        return document;
    }

    /**
     * @param text: 段落的文本
     * @return: 返回段落对象
     */
    public static Paragraph getParagraph(String text) throws IOException, DocumentException {
        BaseFont baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        Font font =  new Font(baseFont, 10, Font.NORMAL);
        Paragraph paragraph = new Paragraph( text, font );
        return paragraph;
    }

    /**
     * @param document: 文本对象
     * @return :PdfWriter实例对象
     */
    /*public static PdfWriter getPdfWriter(Document document) throws DocumentException{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance( document,outputStream );
        return writer;
    }*/

    /**
     * 返回文本TEXT
     * @param text 文本内容
     */
    public static Document generateStringText(Document document,String text)
            throws DocumentException, IOException {
        Paragraph paragraph = PdfCommon.getParagraph(text);
        paragraph.setSpacingBefore(CommonValue.SPACE_BEFORE);
        document.add(paragraph);
        return document;
    }

    /**
     * @param text: 二维码内容
     * @param charsetNameFrom: 需要显示string的字符集
     * @param charsetNameTo: 接口接收的string字符集
     * @return: 字符集转换后的字符串
     */
    public static String getBytes(String text,String charsetNameFrom,String charsetNameTo) throws UnsupportedEncodingException {
        String str = new String( text.getBytes( charsetNameFrom), charsetNameTo);
        return str;
    }

    /**
     * 将生成的PDF文件打印
     * @param bytes
     * @param printName : 打印机名称
     * @param pages : 打印张数
     * @param size : 纸张大小数组,宽度+高度
     */
    public static void printPdf(byte[] bytes, String printName, int pages, int[] size) throws PrinterException, IOException {
        // 读取pdf文件
        PDDocument document = PDDocument.load( bytes );
        //创建打印任务
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        //查找可用的打印机
        PrintService[] printServices = PrinterJob.lookupPrintServices();
        PrintService service = null;
        for (PrintService printService : printServices) {
            if(printName.equals( printService.toString())){
                service = printService;
                break;
            }
        }
        printerJob.setPrintService( service );
        PDFPrintable pdfPrintable = new PDFPrintable( document,Scaling.STRETCH_TO_FIT);
        Book book = new Book();
        Paper paper = new Paper();
        // 设置打印纸张大小
        paper.setSize( size[0],size[1] );
        paper.setImageableArea( 0,0,paper.getWidth(),paper.getHeight() );
        PageFormat format = new PageFormat();
        format.setPaper( paper );
        format.setOrientation( PageFormat.PORTRAIT );
        book.append( pdfPrintable, format,pages);
        printerJob.setPageable( book );
        printerJob.print();
        document.close();
    }
}
