package com.example.hm.convertlab.custom_view;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.example.hm.convertlab.R;
import com.example.hm.convertlab.api.Modell.Banks;

/**
 * Created by hm on 24.02.2016.
 */
public class CustomView extends View {
    private float firstCur;
    private Banks banks;
    private int paintForBankName = 1;
    private int paintForAllRows = 2;
    private int paintForCurName =3;
    private float mCurrenciesSize;
    private float mBankNameSize;
    private float mAllRowsSize;
    private int mCurrenciesColor;
    private float MARGIN_LEFT;  //50dp
    private float MARGIN_LEFT_CUR; // 100
    private float MARGIN_TOP; // 100
    private float MARGIN_LEFT_CUR_NUM;  //450
    private float DISTANCE_BETWEEN_ROWS;  //70
    private float DISTANCE_BETWEEN_CUR;  //150
    private float FIRS_LINE_CUR; // 320;
    private float DEFAULT_COMPONENT_FOR_CANVAS_SIZE; // 350
    private float DEFAULT_CUR_SIZE = 60f;
    private float DEFAULT_BANK_NAME_SIZE = 40f;
    private float DEFAULT_ALL_ROWS_SIZE = 34f;

    public CustomView(Context context) {
        this(context, null);
        setFocusable(true);

        MARGIN_LEFT = convertDpToPixel(50f, context);
        MARGIN_LEFT_CUR =convertDpToPixel(100f, context);
        MARGIN_TOP = convertDpToPixel(100f, context);
        MARGIN_LEFT_CUR_NUM = convertDpToPixel(450f, context);
        DISTANCE_BETWEEN_ROWS = convertDpToPixel(70f, context);
        DISTANCE_BETWEEN_CUR = convertDpToPixel(150f, context);
        FIRS_LINE_CUR = convertDpToPixel(320f, context);
        DEFAULT_COMPONENT_FOR_CANVAS_SIZE = convertDpToPixel(350f, context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSizeAndCurForXML(context, attrs);

        MARGIN_LEFT = convertDpToPixel(20f, context);
        MARGIN_LEFT_CUR =convertDpToPixel(40f, context);
        MARGIN_TOP = convertDpToPixel(45f, context);
        MARGIN_LEFT_CUR_NUM = convertDpToPixel(170f, context);
        DISTANCE_BETWEEN_ROWS = convertDpToPixel(28f, context);
        DISTANCE_BETWEEN_CUR = convertDpToPixel(60f, context);
        FIRS_LINE_CUR = convertDpToPixel(145f, context);
        DEFAULT_COMPONENT_FOR_CANVAS_SIZE = convertDpToPixel(155f, context);

    }

    public void setDialogBanks(Banks _banks){
        banks = _banks;
    }

    public Paint initPaint(int paintNum) {
        Paint paint = new Paint();

        switch (paintNum) {
            case 1:
                paint.setAntiAlias(true);
                paint.setColor(Color.BLACK);
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                paint.setTextSize(mBankNameSize);
                break;

            case 2:
                paint.setAntiAlias(true);
                paint.setColor(Color.BLACK);
                paint.setTextSize(mAllRowsSize);
                break;

            case 3:
                paint.setAntiAlias(true);
                paint.setColor(mCurrenciesColor);
                paint.setTextSize(mCurrenciesSize);
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                break;
        }
        return paint;
    }

    public void initSizeAndCurForXML (Context context, AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Options, 0, 0);
        mCurrenciesSize = a.getDimension(R.styleable.Options_currencies_size, DEFAULT_CUR_SIZE);
        mBankNameSize = a.getDimension(R.styleable.Options_bank_name_size, DEFAULT_BANK_NAME_SIZE);
        mAllRowsSize = a.getDimension(R.styleable.Options_all_rows_size, DEFAULT_ALL_ROWS_SIZE);
        mCurrenciesColor = a.getColor(R.styleable.Options_currencies_color,
                ContextCompat.getColor(context, R.color.currencies_color));
        a.recycle();
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources r = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final  float height = DEFAULT_COMPONENT_FOR_CANVAS_SIZE + banks.mCurrencies.size() * DISTANCE_BETWEEN_CUR;
        final  int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width,(int)height );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawText(banks.mBankName, MARGIN_LEFT, MARGIN_TOP, initPaint(paintForBankName));
        canvas.drawText(banks.mRegion, MARGIN_LEFT, MARGIN_TOP + DISTANCE_BETWEEN_ROWS, initPaint(paintForAllRows));
        canvas.drawText(banks.mCity, MARGIN_LEFT, MARGIN_TOP + DISTANCE_BETWEEN_ROWS *2, initPaint(paintForAllRows));

        for(int i = 0; i < banks.mCurrencies.size();i++){
            firstCur = FIRS_LINE_CUR+((i+1)* DISTANCE_BETWEEN_CUR);

            canvas.drawText(banks.mCurrencies.get(i).CurrenciesName,MARGIN_LEFT_CUR, firstCur,initPaint(paintForCurName));
            canvas.drawText(banks.mCurrencies.get(i).getCanvasAsk()
                    + " / " + banks.mCurrencies.get(i).getCanvasBid(), MARGIN_LEFT_CUR_NUM, firstCur, initPaint(paintForAllRows));
        }
    }

}
