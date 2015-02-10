package com.keertech.myandroid.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.keertech.myandroid.R;
import com.keertech.myandroid.activity.base.AbstractBarActivity;

import java.util.Hashtable;

public class QRCodeGenerateActivity extends AbstractBarActivity {

    Button btn1 = null;
    Button btn2 = null;
    ImageView ivImageView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_generate);
        btn1 = (Button) findViewById(R.id.button1);// 条形码
        btn2 = (Button) findViewById(R.id.button2);// 二维码
        ivImageView = (ImageView) findViewById(R.id.imageView1);
        final String content = "c2b0f58a6f09cafd1503c06ef08ac7aeb7ddb91a602dac145551c102143e6159e385cdc2bfadeb94";

        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bitmap mBitmap = null;
                mBitmap = createBarcode(mContext, content, 300, 300, true);
                if (mBitmap != null) {
                    ivImageView.setImageBitmap(mBitmap);
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bitmap mBitmap = null;
                try {
                    if (!content.equals("")) {
                        mBitmap = create2DCode(content);
                        // Bitmap bm =
                        // BitmapFactory.decodeResource(getResources(),
                        // R.drawable.diagnose1);
//                        if (mBitmap != null) {
//                            ivImageView.setImageBitmap(mBitmap);
//                        }
                        ivImageView.setImageBitmap(createBitmap(mBitmap, zoomBitmapBorder(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher), 100, 100)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Bitmap create2DCode(String str) throws WriterException {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        // hints.put(EncodeHintType.CHARACTER_SET, "GBK");
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 500, 500, hints);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        // 二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0xffffffff;
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 图片两端所保留的空白的宽度
     */
    private int marginW = 20;
    /**
     * 条形码的编码类型
     */
    private BarcodeFormat barcodeFormat = BarcodeFormat.CODE_128;

    /**
     * 生成条形码
     *
     * @param context
     * @param contents      需要生成的内容
     * @param desiredWidth  生成条形码的宽带
     * @param desiredHeight 生成条形码的高度
     * @param displayCode   是否在条形码下方显示内容
     * @return
     */
    public Bitmap createBarcode(Context context, String contents,
                                int desiredWidth, int desiredHeight, boolean displayCode) {
        Bitmap resultBitmap = null;
        if (displayCode) {
            Bitmap barcodeBitmap = encodeAsBitmap(contents, barcodeFormat,
                    desiredWidth, desiredHeight);
            Bitmap codeBitmap = createCodeBitmap(contents, desiredWidth + 2
                    * marginW, desiredHeight, context);
            resultBitmap = mixtureBitmap(barcodeBitmap, codeBitmap, new PointF(
                    0, desiredHeight));
        } else {
            resultBitmap = encodeAsBitmap(contents, barcodeFormat,
                    desiredWidth, desiredHeight);
        }

        return resultBitmap;
    }

    /**
     * 生成显示编码的Bitmap
     *
     * @param contents
     * @param width
     * @param height
     * @param context
     * @return
     */
    protected Bitmap createCodeBitmap(String contents, int width, int height, Context context) {
        TextView tv = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(layoutParams);
        tv.setText(contents);
        tv.setHeight(height);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setWidth(width);
        tv.setDrawingCacheEnabled(true);
        tv.setTextColor(Color.BLACK);
        tv.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());

        tv.buildDrawingCache();
        Bitmap bitmapCode = tv.getDrawingCache();
        return bitmapCode;
    }

    /**
     * 生成条形码的Bitmap
     *
     * @param contents      需要生成的内容
     * @param format        编码格式
     * @param desiredWidth
     * @param desiredHeight
     * @return
     * @throws WriterException
     */
    protected Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int desiredWidth, int desiredHeight) {
        final int WHITE = 0xFFFFFFFF;
        final int BLACK = 0xFF000000;

        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result = null;
        try {
            result = writer.encode(contents, format, desiredWidth,desiredHeight, null);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        // All are 0, or black, by default
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 将两个Bitmap合并成一个
     *
     * @param first
     * @param second
     * @param fromPoint 第二个Bitmap开始绘制的起始位置（相对于第一个Bitmap）
     * @return
     */
    protected Bitmap mixtureBitmap(Bitmap first, Bitmap second, PointF fromPoint) {
        if (first == null || second == null || fromPoint == null) {
            return null;
        }
        Bitmap newBitmap = Bitmap.createBitmap(
                first.getWidth() + second.getWidth() + marginW,
                first.getHeight() + second.getHeight(), Config.ARGB_4444);
        Canvas cv = new Canvas(newBitmap);
        cv.drawBitmap(first, marginW, 0, null);
        cv.drawBitmap(second, fromPoint.x, fromPoint.y, null);
        cv.save(Canvas.ALL_SAVE_FLAG);
        cv.restore();

        return newBitmap;
    }

    /**
     * 仿微信二维码开始 **
     */
    // 图片剪切
    public Bitmap cutBitmap(Bitmap mBitmap, Rect r, Bitmap.Config config) {
        int width = r.width();
        int height = r.height();
        Bitmap croppedImage = Bitmap.createBitmap(width, height, config);
        Canvas cvs = new Canvas(croppedImage);
        Rect dr = new Rect(0, 0, width, height);
        cvs.drawBitmap(mBitmap, r, dr, null);
        return croppedImage;
    }

    /**
     * 合并图片
     *
     * @param src
     * @param watermark
     * @return
     */
    private Bitmap createBitmap(Bitmap src, Bitmap watermark) {
        String tag = "createBitmap";
        Log.d(tag, "create a new bitmap");
        if (src == null) {
            return null;
        }
        int w = src.getWidth();
        int h = src.getHeight();
        int ww = watermark.getWidth();
        int wh = watermark.getHeight();
        // create the new blank bitmap
        Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);

        // draw src into
        cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src

        // 在src的中间画watermark
        cv.drawBitmap(watermark, w / 2 - ww / 2, h / 2 - wh / 2, null);// 设置ic_launcher的位置

        // save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        // store
        cv.restore();// 存储
        return newb;
    }

    /**
     * 缩放图片
     *
     * @param src
     * @param destWidth
     * @param destHeigth
     * @return
     */
    private Bitmap zoomBitmap(Bitmap src, int destWidth, int destHeigth) {
        String tag = "lessenBitmap";
        if (src == null) {
            return null;
        }
        int w = src.getWidth();// 源文件的大小
        int h = src.getHeight();
        // calculate the scale - in this case = 0.4f
        float scaleWidth = ((float) destWidth) / w;// 宽度缩小比例
        float scaleHeight = ((float) destHeigth) / h;// 高度缩小比例
        Log.d(tag, "bitmap width is :" + w);
        Log.d(tag, "bitmap height is :" + h);
        Log.d(tag, "new width is :" + destWidth);
        Log.d(tag, "new height is :" + destHeigth);
        Log.d(tag, "scale width is :" + scaleWidth);
        Log.d(tag, "scale height is :" + scaleHeight);
        Matrix m = new Matrix();// 矩阵
        m.postScale(scaleWidth, scaleHeight);// 设置矩阵比例
        Bitmap resizedBitmap = Bitmap.createBitmap(src, 0, 0, w, h, m, true);// 直接按照矩阵的比例把源文件画入进行
        return resizedBitmap;
    }

    /**
     * 缩放图片并加描边
     *
     * @param src
     * @param destWidth
     * @param destHeigth
     * @return
     */
    private Bitmap zoomBitmapBorder(Bitmap src, int destWidth, int destHeigth) {
        String tag = "lessenBitmap";
        if (src == null) {
            return null;
        }
        int w = src.getWidth();// 源文件的大小
        int h = src.getHeight();
        // calculate the scale - in this case = 0.4f
        float scaleWidth = ((float) destWidth - 4) / w;// 宽度缩小比例
        float scaleHeight = ((float) destHeigth - 4) / h;// 高度缩小比例
        Log.d(tag, "bitmap width is :" + w);
        Log.d(tag, "bitmap height is :" + h);
        Log.d(tag, "new width is :" + destWidth);
        Log.d(tag, "new height is :" + destHeigth);
        Log.d(tag, "scale width is :" + scaleWidth);
        Log.d(tag, "scale height is :" + scaleHeight);
        Matrix m = new Matrix();// 矩阵
        m.postScale(scaleWidth, scaleHeight);// 设置矩阵比例
        Bitmap resizedBitmap = Bitmap.createBitmap(src, 0, 0, w, h, m, true);// 直接按照矩阵的比例把源文件画入进行

        Bitmap newb = Bitmap.createBitmap(destWidth, destHeigth,
                Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        //cv.drawColor(R.color.white);
        cv.drawRGB(0, 128, 128);
        cv.drawBitmap(resizedBitmap, 2, 2, null);// 设置ic_launcher的位置

        // save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        // store
        cv.restore();// 存储

        return getRoundedCornerBitmap(newb);
    }

    /**
     * 图片圆角
     *
     * @param bitmap
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 12;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;

    }

}