package cn.edu.zstu.sunshine.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.orhanobut.logger.Logger;

/**
 * 旋转式拖动条（单指针和双指针模式）
 * Created by CooLoongWu on 2017-11-13 14:28.
 */

public class RotarySeekBar extends View {

    private float centerX;  //圆心X
    private float centerY;  //圆心Y

    //day：星期
    //ordinal：课程节数
    //week：课程周数

    private int backCircleColor = Color.parseColor("#EDEDED");
    private int mainCircleColor = Color.parseColor("#FFFFFF");
    private int indicatorColor = Color.parseColor("#FFA036");//指示器的颜色
    private int progressDefaultColor = Color.parseColor("#111111");//进度条默认颜色
    private int progressAccentColor = Color.parseColor("#FFA036");//进度条选中颜色

    private Paint backCirclePaint;
    private Paint mainCirclePaint;
    private Paint indicatorPaint;
    private Paint progressDefaultPaint;
    private Paint progressAccentPaint;

    RectF rectF = new RectF();

    private float progressRadius = 160;

    public RotarySeekBar(Context context) {
        super(context);
        init();
    }

    public RotarySeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RotarySeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        backCirclePaint = new Paint();
        backCirclePaint.setAntiAlias(true);
        backCirclePaint.setColor(backCircleColor);
        backCirclePaint.setStyle(Paint.Style.FILL);

        mainCirclePaint = new Paint();
        mainCirclePaint.setAntiAlias(true);
        mainCirclePaint.setColor(mainCircleColor);
        mainCirclePaint.setStyle(Paint.Style.FILL);

        indicatorPaint = new Paint();
        indicatorPaint.setAntiAlias(true);
        indicatorPaint.setStrokeWidth(1);
        indicatorPaint.setStyle(Paint.Style.STROKE);
        indicatorPaint.setColor(indicatorColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        centerX = canvas.getWidth() / 2;
        centerY = canvas.getHeight() / 2;

        Logger.e("当前圆心X：" + centerX + "；Y：" + centerY);

        canvas.drawCircle(centerX, centerY, progressRadius - 110, backCirclePaint);
        canvas.drawCircle(centerX, centerY, progressRadius - 120, mainCirclePaint);

        drawDay(180, 7, canvas);
        drawOrdinal(190, 12, canvas);
        drawWeek(200, 20, canvas);

    }


    /**
     * 画星期的刻度盘
     *
     * @param sweepAngle 总角度
     * @param dialCount  刻度数量
     * @param canvas     画布
     */
    private void drawDay(int sweepAngle, int dialCount, Canvas canvas) {
        /*
         * 假设一个圆的圆心坐标是(a,b)，半径为r，则圆上每个点的
         * X坐标=a + Math.sin(2*Math.PI / 360) * r ；
         * Y坐标=b + Math.cos(2*Math.PI / 360) * r ；
         */

        //每个刻度的圆心X
        float x;
        //每个刻度的圆心Y
        float y;
        //刻度间的角度偏移量（注意-1，因为10个刻度间有9段距离）
        float angleOffset = sweepAngle / (dialCount - 1);

        //星期刻度盘的半径
        float radius = progressRadius - 40;

        //角度是参考竖直方向最下端，逆时针方向
        for (int i = 0; i < dialCount; i++) {
            float angle = angleOffset * i + (360 - sweepAngle) / 2;
            x = centerX + (float) (radius * Math.sin(2 * Math.PI / 360 * angle));
            y = centerY + (float) (radius * Math.cos(2 * Math.PI / 360 * angle));

            canvas.drawCircle(x, y, 6, indicatorPaint);
//            canvas.drawPoint(x, y, indicatorPaint);
        }

    }

    /**
     * 画一天课程节数的刻度盘
     *
     * @param sweepAngle 总角度
     * @param dialCount  刻度数量
     * @param canvas     画布
     */
    private void drawOrdinal(int sweepAngle, int dialCount, Canvas canvas) {

        //课程节数刻度盘的半径
        float radius = progressRadius - 20;
        rectF.left = centerX - radius;
        rectF.top = centerY - radius;
        rectF.right = centerX + radius;
        rectF.bottom = centerY + radius;

        canvas.drawArc(rectF, 180, sweepAngle, false, indicatorPaint);
    }

    /**
     * 画周数的刻度盘
     *
     * @param sweepAngle 总角度
     * @param dialCount  刻度数量
     * @param canvas     画布
     */
    private void drawWeek(int sweepAngle, int dialCount, Canvas canvas) {
        //课程节数刻度盘的半径
        float radius = progressRadius;
        rectF.left = centerX - radius;
        rectF.top = centerY - radius;
        rectF.right = centerX + radius;
        rectF.bottom = centerY + radius;

        canvas.drawArc(rectF, 180, sweepAngle, false, indicatorPaint);


    }
}
