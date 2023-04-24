package com.flywin.utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class WatermarkUtil {

    /**
     * 给图片加水印
     *
     * @param inputFile
     * @param waterMarkName
     * @return
     */
    public static byte[] addTxtWaterMaker(byte[] inputFile, String waterMarkName) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            PdfReader reader = new PdfReader(inputFile);
            PdfStamper stamper = new PdfStamper(reader, baos);
            // 使用系统字体
            BaseFont base = BaseFont.createFont();
//            BaseFont base = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Rectangle pageRect;
            PdfGState gs = new PdfGState();
            //设置文字透明度
            gs.setFillOpacity(0.6f);
            gs.setStrokeOpacity(0.6f);
            //获取pdf总页数
            int total = reader.getNumberOfPages() + 1;
            JLabel label = new JLabel();
            FontMetrics metrics;
            int textH;
            int textW;
            label.setText(waterMarkName);
            metrics = label.getFontMetrics(label.getFont());
            //得到文字的宽高
            textH = metrics.getHeight();
            textW = metrics.stringWidth(label.getText());
            PdfContentByte under;
            for (int i = 1; i < total; i++) {
                pageRect = reader.getPageSizeWithRotation(i);
                //得到一个覆盖在上层的水印文字
                under = stamper.getOverContent(i);
                under.saveState();
                under.setGState(gs);
                under.beginText();
                //设置水印文字颜色
                under.setColorFill(BaseColor.LIGHT_GRAY);
                //设置水印文字和大小
                under.setFontAndSize(base, 12);
                //这个position主要是为了在换行加水印时能往右偏移起始坐标
                int position = 0;
                int interval = -3;
                for (int height = interval + textH; height < pageRect.getHeight(); height = height + textH * 3) {
                    for (int width = interval + textW - position * 150; width < pageRect.getWidth() + textW; width = width + textW) {
                        //添加水印文字，水印文字成25度角倾斜
                        under.showTextAligned(Element.ALIGN_LEFT, waterMarkName, width - textW, height - textH, 25);
                    }
                    position++;
                }
                // 添加水印文字
                under.endText();
            }
            //关闭流
            stamper.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

//    public static void main(String[] args) throws Exception {
//        byte[] bytes = addTxtWaterMaker("F:\\test.pdf", "水印测试 2021-12-32");
//        File somefile = new File("F:\\测试水印2.pdf");
//        FileOutputStream fileOutputStream = new FileOutputStream(somefile);
//        fileOutputStream.write(bytes);
//        fileOutputStream.flush();
//        fileOutputStream.close();
//    }

    /**
     * @param data          文件
     * @param waterMarkName 页脚添加水印
     * @throws DocumentException
     * @throws IOException
     */
    public static byte[] setWatermark(InputStream data, String waterMarkName)
            throws DocumentException, IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfReader reader = null;
        PdfStamper stamper = null;
        try {
            reader = new PdfReader(data);
            stamper = new PdfStamper(reader, baos);
            // 获取总页数 +1, 下面从1开始遍历
            int total = reader.getNumberOfPages() + 1;
            //设置字体
            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            //默认水印
            if (StringUtils.isEmpty(waterMarkName)) {
                waterMarkName = "中国航空";
            }
            // 间隔
            int interval = -5;
            // 获取水印文字的高度和宽度
            int textH = 0, textW = 0;
            JLabel label = new JLabel();
            label.setText(waterMarkName);
            FontMetrics metrics = label.getFontMetrics(label.getFont());
            textH = metrics.getHeight();
            textW = metrics.stringWidth(label.getText());
            System.out.println("textH: " + textH);
            System.out.println("textW: " + textW);

            // 设置水印透明度
            PdfGState gs = new PdfGState();
            gs.setFillOpacity(0.4f);
            gs.setStrokeOpacity(0.4f);

            Rectangle pageSizeWithRotation = null;
            PdfContentByte content = null;
            for (int i = 1; i < total; i++) {
                // 在内容上方加水印
                content = stamper.getOverContent(i);
                // 在内容下方加水印
                // content = stamper.getUnderContent(i);
                content.saveState();
                content.setGState(gs);

                // 设置字体和字体大小
                content.beginText();
                content.setFontAndSize(baseFont, 20);

                // 获取每一页的高度、宽度
                pageSizeWithRotation = reader.getPageSizeWithRotation(i);
                float pageHeight = pageSizeWithRotation.getHeight();
                float pageWidth = pageSizeWithRotation.getWidth();

                // 根据纸张大小多次添加， 水印文字成30度角倾斜
                for (int height = interval + textH; height < pageHeight; height = height + textH * 3) {
                    for (int width = interval + textW; width < pageWidth + textW; width = width + textW * 2) {
                        content.showTextAligned(Element.ALIGN_LEFT, waterMarkName, width - textW, height - textH, 30);
                    }
                }
                content.endText();
            }
        } finally {
            // 关流
            if (stamper != null) {
                stamper.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
        return baos.toByteArray();
    }
}
