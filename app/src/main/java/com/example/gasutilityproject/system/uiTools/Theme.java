package com.example.gasutilityproject.system.uiTools;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.StateSet;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.gasutilityproject.AppLoader;
import com.example.gasutilityproject.StaticFields.Colors;

//todo : done
public class Theme {

    public static boolean isShowingKeyboard = false;
    private static boolean isStatusIconLight = false;
    private static boolean firstRequestForStatus = true;
    private static boolean lockKeyboardOperation = false;
    private static int statusBarColor = Colors.whiteLow;

    public static void getScreenPX() {
        float widthPixels = Resources.getSystem().getDisplayMetrics().widthPixels;
        float heightPixels = Resources.getSystem().getDisplayMetrics().heightPixels;

        AppLoader.diameter = (int) (Math.sqrt(Math.pow(widthPixels, 2) + Math.pow(heightPixels, 2)) / 2.3f);
        AppLoader.widthScreen = (int) widthPixels;
        AppLoader.heightScreen = (int) (heightPixels);
    }

    public static int convertToResponsivePx(float percent) {
        return (int) (percent * AppLoader.diameter);
    }

    public static void setThemeNavigationBar(Context context, Window window) {
        if (null == window && 23 > AppLoader.mySDK)
            return;
        GradientDrawable drawable = new GradientDrawable();
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        Resources resources = context.getResources();
        switch (resources.getConfiguration().uiMode & 0x30) {
            case 0x20:
                gradientDrawable.setColor(0xFF000000);
                break;
            case 0x10:
                gradientDrawable.setColor(0xFFFFFFFF);
                break;
        }
        Drawable[] layers = {drawable, gradientDrawable};
        LayerDrawable windowBackground = new LayerDrawable(layers);
        windowBackground.setLayerInsetTop(1, AppLoader.heightScreen);
        if (window != null) {
            window.setBackgroundDrawable(windowBackground);
        }
    }

    public static void setUpStatusBar(Activity activity, int statusBarColor, boolean isStatusIconLight) {
        if (Theme.isStatusIconLight != isStatusIconLight || Theme.firstRequestForStatus) {
            Theme.isStatusIconLight = isStatusIconLight;
            Theme.firstRequestForStatus = false;
            View decorView = activity.getWindow().getDecorView();
            if (isStatusIconLight)
                decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            else
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        if (Theme.statusBarColor != statusBarColor && statusBarColor != Colors.transparent && AppLoader.mySDK >= 21) {
            Theme.statusBarColor = statusBarColor;
            activity.getWindow().setStatusBarColor(statusBarColor);
        }
    }

    public static void showKeyboard(Context context) {
        if (lockKeyboardOperation)
            return;
        lockKeyboardOperation = true;
        AppLoader.handler.postDelayed((Runnable) () -> lockKeyboardOperation = false, 100);
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (!isShowingKeyboard || !inputMethodManager.isAcceptingText()) {
            isShowingKeyboard = true;
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    public static void hideKeyboard(Activity activity) {
        hideKeyboard(activity, false);
    }

    public static void hideKeyboard(Activity activity, boolean force) {
        if (lockKeyboardOperation)
            return;
        lockKeyboardOperation = true;
        AppLoader.handler.postDelayed((Runnable) () -> lockKeyboardOperation = false, 100);
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isShowingKeyboard || inputMethodManager.isAcceptingText() || force) {
            isShowingKeyboard = false;
            inputMethodManager.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public static void hideKeyboard(Activity activity, View view) {
        if (lockKeyboardOperation)
            return;
        lockKeyboardOperation = true;
        AppLoader.handler.postDelayed((Runnable) () -> lockKeyboardOperation = false, 100);
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isShowingKeyboard || inputMethodManager.isAcceptingText()) {
            isShowingKeyboard = false;
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static Drawable createRoundDrawable(int topRadius, int bottomRadius, int backgroundColor) {
        ShapeDrawable defaultDrawable = new ShapeDrawable();
        if (bottomRadius != 0 || topRadius != 0)
            defaultDrawable.setShape(new RoundRectShape(new float[]{topRadius, topRadius, topRadius, topRadius
                    , bottomRadius, bottomRadius, bottomRadius, bottomRadius}, null, null));
        defaultDrawable.getPaint().setColor(backgroundColor);
        return defaultDrawable;
    }

    public static Drawable createRoundDrawable(int radius, int backgroundColor) {
        ShapeDrawable defaultDrawable = new ShapeDrawable();
        if (radius != 0)
            defaultDrawable.setShape(new RoundRectShape(new float[]{radius, radius, radius, radius
                    , radius, radius, radius, radius}, null, null));
        defaultDrawable.getPaint().setColor(backgroundColor);
        return defaultDrawable;
    }

    public static Drawable createRoundDrawable(int radius, int backgroundColor, int paddingLeftRight, int paddingTopBottom) {
        ShapeDrawable defaultDrawable = new ShapeDrawable();
        if (radius != 0)
            defaultDrawable.setShape(new RoundRectShape(new float[]{radius, radius, radius, radius, radius, radius, radius, radius}, null, null));
        defaultDrawable.getPaint().setColor(backgroundColor);
        /// defaultDrawable.setPadding(paddingLeftRight, paddingTopBottom, paddingLeftRight, paddingTopBottom);
        defaultDrawable.setIntrinsicWidth(paddingLeftRight);
        defaultDrawable.setIntrinsicHeight(paddingTopBottom);
        return defaultDrawable;
    }

    public static Drawable createRoundDrawable(int tLR, int tRR, int bLR, int bRR, int backgroundColor) {
        ShapeDrawable defaultDrawable = new ShapeDrawable();
        defaultDrawable.setShape(new RoundRectShape(new float[]{tLR, tLR, tRR, tRR, bRR, bRR, bLR, bLR}, null, null));
        defaultDrawable.getPaint().setColor(backgroundColor);
        return defaultDrawable;
    }
//    public static GradientDrawable createRoundedRectangleDrawable(int color, float cornerRadius) {
//        GradientDrawable drawable = new GradientDrawable();
//        drawable.setShape(GradientDrawable.RECTANGLE);
//        drawable.setColor(color);
//        drawable.setCornerRadius(cornerRadius);
//        return drawable;
//    }


    public static void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables())
            if (drawable != null)
                drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
    }

    public static Drawable createBorderRoundDrawable(int borderColor, int backgroundColor, int radius) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(backgroundColor);
        gradientDrawable.setStroke(Theme.getAf(3), borderColor);
        gradientDrawable.setCornerRadius(radius);
        return gradientDrawable;
    }

    public static Drawable createBorderRoundDrawable(int borderColor, int backgroundColor, int tR, int bR) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(backgroundColor);
        gradientDrawable.setStroke(Theme.getAf(3), borderColor);
        gradientDrawable.setCornerRadii(new float[]{tR, tR, tR, tR, bR, bR, bR, bR});
        return gradientDrawable;
    }

    public static Drawable createBorderRoundDrawable(int borderColor, int backgroundColor, int radius, int dashWidth, int dashGap) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(backgroundColor);
        gradientDrawable.setStroke(Theme.getAf(3), borderColor, dashWidth, dashGap);
        gradientDrawable.setCornerRadius(radius);
        return gradientDrawable;
    }

    public static Drawable createGradient(int colorGradientOne, int colorGradientTwo, int radius) {
        return createGradient(colorGradientOne, colorGradientTwo, radius, GradientDrawable.Orientation.LEFT_RIGHT);
    }

    public static Drawable createGradient(int colorGradientOne, int colorGradientTwo, int radius, GradientDrawable.Orientation orientation) {
        GradientDrawable gradientDrawable = new GradientDrawable(orientation, new int[]{colorGradientOne
                , colorGradientTwo});
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(radius);
        return gradientDrawable;
    }

    public static StateListDrawable createRoundFocusableDrawable(int backgroundColor, int enableBorder, int disableBorder, int radius) {
        StateListDrawable out = new StateListDrawable();
        out.addState(new int[]{-android.R.attr.state_focused}, createBorderRoundDrawable(disableBorder, backgroundColor, radius));
        out.addState(new int[]{android.R.attr.state_focused}, createBorderRoundDrawable(enableBorder, backgroundColor, radius));
        return out;
    }

    public static StateListDrawable createRoundFocusableDrawable(int enableBackgroundColor, int disabledBackgroundColor, int enableBorder, int disableBorder, int radius) {
        StateListDrawable out = new StateListDrawable();
        out.addState(new int[]{-android.R.attr.state_focused}, createBorderRoundDrawable(disableBorder, disabledBackgroundColor, radius));
        out.addState(new int[]{android.R.attr.state_focused}, createBorderRoundDrawable(enableBorder, enableBackgroundColor, radius));
        return out;
    }

    public static StateListDrawable createRoundOnClickedStateDrawable(int enableBackgroundColor, int disabledBackgroundColor, int enableBorder, int disableBorder, int radius) {
        StateListDrawable out = new StateListDrawable();
        out.addState(new int[]{-android.R.attr.state_pressed}, createBorderRoundDrawable(disableBorder, disabledBackgroundColor, radius));
        out.addState(new int[]{android.R.attr.state_pressed}, createBorderRoundDrawable(enableBorder, enableBackgroundColor, radius));
        return out;
    }

    public static Drawable createRoundSelectorDrawable(int rippleColor, int backgroundColor, int topRadius, int bottomRadius) {
        Drawable contentDrawable = null;
        if (bottomRadius != 0 || topRadius != 0)
            contentDrawable = createRoundDrawable(topRadius, bottomRadius, backgroundColor);

        if (rippleColor == Colors.transparent)
            return contentDrawable;

        Drawable maskDrawable = createRoundDrawable(topRadius, bottomRadius, 0xffffffff);
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{StateSet.WILD_CARD},
                new int[]{rippleColor}
        );
        return new RippleDrawable(colorStateList, contentDrawable, maskDrawable);
    }

    public static Drawable createRoundSelectorDrawable(int rippleColor, int backgroundColor, int tLR, int tRR, int bLR, int bRR) {
        Drawable contentDrawable = createRoundDrawable(tLR, tRR, bLR, bRR, backgroundColor);

        if (rippleColor == Colors.transparent)
            return contentDrawable;

        Drawable maskDrawable = createRoundDrawable(tLR, tRR, bLR, bRR, 0xffffffff);
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{StateSet.WILD_CARD},
                new int[]{rippleColor}
        );
        return new RippleDrawable(colorStateList, contentDrawable, maskDrawable);
    }

    public static Drawable createRoundSelectorDrawable(int rippleColor, int backgroundColor, int borderColor, int topRadius, int bottomRadius) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(backgroundColor);
        gradientDrawable.setStroke(Theme.getAf(5), borderColor);
        gradientDrawable.setCornerRadius(topRadius);

        if (rippleColor == Colors.transparent)
            return gradientDrawable;

        Drawable maskDrawable = createRoundDrawable(topRadius, bottomRadius, 0xffffffff);
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{StateSet.WILD_CARD},
                new int[]{rippleColor}
        );
        return new RippleDrawable(colorStateList, gradientDrawable, maskDrawable);
    }

    public static class ImageRoundDrawable extends Drawable {
        private final RectF mBounds = new RectF();
        private final RectF mBitmapRect = new RectF();
        private final Path mPath = new Path();
        private Paint mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        private boolean mBoundsConfigured = false;
        private int mBitmapWidth = -1;
        private int mBitmapHeight = -1;
        private final float[] mRadii;

        private BitmapShader mBitmapShader;
        private final ImageView.ScaleType mScaleType;
        private final Bitmap mBitmap;

        public ImageRoundDrawable(Drawable drawable, Resources resources, ImageView.ScaleType scaleType, int radii) {
            mScaleType = scaleType;
            mRadii = new float[]{radii, radii, radii, radii, radii, radii, radii, radii};
            mBitmap = drawableToBitmap(drawable);
            if (mBitmap == null)
                return;
            mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mBitmapWidth = mBitmap.getScaledWidth(resources.getDisplayMetrics());
            mBitmapHeight = mBitmap.getScaledHeight(resources.getDisplayMetrics());
            mBitmapRect.set(0, 0, mBitmapWidth, mBitmapHeight);
            mBitmapPaint.setShader(mBitmapShader);
        }

        public ImageRoundDrawable(Drawable bitmap, Resources r, ImageView.ScaleType scaleType, int topDrawable, int bottomDrawable) {
            mScaleType = scaleType;
            mRadii = new float[]{topDrawable, topDrawable, topDrawable, topDrawable, bottomDrawable, bottomDrawable, bottomDrawable, bottomDrawable};
            mBitmap = drawableToBitmap(bitmap);
            if (mBitmap == null)
                return;
            mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mBitmapWidth = mBitmap.getScaledWidth(r.getDisplayMetrics());
            mBitmapHeight = mBitmap.getScaledHeight(r.getDisplayMetrics());
            mBitmapRect.set(0, 0, mBitmapWidth, mBitmapHeight);
            mBitmapPaint.setShader(mBitmapShader);
        }

        public static Bitmap drawableToBitmap(Drawable drawable) {
            if (drawable == null)
                return null;
            if (drawable instanceof BitmapDrawable)
                return ((BitmapDrawable) drawable).getBitmap();
            try {
                Bitmap bitmap = Bitmap.createBitmap(Math.max(drawable.getIntrinsicWidth(), 2), Math.max(drawable.getIntrinsicHeight(), 2), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                return bitmap;
            } catch (IllegalArgumentException e) {
                return null;
            }
        }

        private void configureBounds(Canvas canvas) {
            if (mBitmapShader == null)
                return;
            mBoundsConfigured = true;
            Rect clipBounds = canvas.getClipBounds();
            if (ImageView.ScaleType.FIT_XY == mScaleType) {
                Matrix m = new Matrix();
                m.setRectToRect(mBitmapRect, new RectF(clipBounds), Matrix.ScaleToFit.FILL);
                mBitmapShader.setLocalMatrix(m);
                mBounds.set(clipBounds);
            } else if (ImageView.ScaleType.FIT_CENTER == mScaleType)
                mBounds.set(mBitmapRect);
            else if (ImageView.ScaleType.CENTER_CROP == mScaleType)
                mBounds.set(clipBounds);
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.save();
            if (!mBoundsConfigured)
                configureBounds(canvas);
            mPath.addRoundRect(mBounds, mRadii, Path.Direction.CW);
            canvas.drawPath(mPath, mBitmapPaint);
            canvas.restore();
        }

        @Override
        public int getOpacity() {
            return (mBitmap == null || mBitmap.hasAlpha() || mBitmapPaint.getAlpha() < 255) ? PixelFormat.TRANSLUCENT : PixelFormat.OPAQUE;
        }

        @Override
        public void setAlpha(int alpha) {
            mBitmapPaint.setAlpha(alpha);
            invalidateSelf();
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            mBitmapPaint.setColorFilter(cf);
            invalidateSelf();
        }

        @Override
        public int getIntrinsicHeight() {
            return mBitmapHeight;
        }

        @Override
        public int getIntrinsicWidth() {
            return mBitmapWidth;
        }
    }

    public static int getAf(int dimen) {
        return (int) (dimen * 0.001f * AppLoader.diameter);
    }

    public static int getAf(int dimen, int desiredHeightInPixels) {

        float widthPixels = Resources.getSystem().getDisplayMetrics().widthPixels;

        int diameter = (int) (Math.sqrt(Math.pow(widthPixels, 2) + Math.pow((float) desiredHeightInPixels, 2)) / 2.3f);
        return (int) (dimen * 0.001f * diameter);
    }

    public static Drawable resizeDrawable(Context context, int drawableResourceId, int width, int height) {
        Drawable originalDrawable = ContextCompat.getDrawable(context, drawableResourceId);
        if (originalDrawable != null) {
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            originalDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            originalDrawable.draw(canvas);
            return new BitmapDrawable(context.getResources(), bitmap);
        }
        return null;
    }
}