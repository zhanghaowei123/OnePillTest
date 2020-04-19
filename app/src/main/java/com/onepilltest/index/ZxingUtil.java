package com.onepilltest.index;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.yuyh.library.imgsel.utils.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.hyphenate.chat.EMClient.TAG;

//扫码结果处理类
public class ZxingUtil {

    /**
     * 生成二维码
     */
    public static String getQR(Context context,String str) throws WriterException, IOException {
        String content = str;//二维码内容
        int width = 200; // 图像宽度
        int height = 200; // 图像高度
        Map<EncodeHintType, Object> hints = new HashMap<>();
        //内容编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //设置二维码边的空度，非负数
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        //生成BitMap文件
        Bitmap bitmap = bitMatrixToBitmap(bitMatrix);
        return saveBimap(context,bitmap);

    }
    //生成BitMap文件
    public static Bitmap bitMatrixToBitmap(BitMatrix bitMatrix) {
        final int width = bitMatrix.getWidth();
        final int height = bitMatrix.getHeight();

        final int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[y * width + x] = bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

        return bitmap;
    }

    //保存BitMap文件
    public static String saveBimap(Context context,Bitmap bitmap) {
        String name=context.getExternalCacheDir()+"/face.jpg";
        LogUtils.i(TAG,"name="+name);
        File file = new File(name);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream out = new FileOutputStream(file);

            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
                out.flush();
                out.close();
                return name;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "no";
    }

    /**
     * 识别二维码
     */
//    public static void QRReader(File file) throws IOException, NotFoundException {
//        MultiFormatReader formatReader = new MultiFormatReader();
//        //读取指定的二维码文件
//        BufferedImage bufferedImage =ImageIO.read(file);
//        BinaryBitmap binaryBitmap= new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
//        //定义二维码参数
//        Map  hints= new HashMap<>();
//        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//        com.google.zxing.Result result = formatReader.decode(binaryBitmap, hints);
//        //输出相关的二维码信息
//        System.out.println("解析结果："+result.toString());
//        System.out.println("二维码格式类型："+result.getBarcodeFormat());
//        System.out.println("二维码文本内容："+result.getText());
//        bufferedImage.flush();
//    }

}
