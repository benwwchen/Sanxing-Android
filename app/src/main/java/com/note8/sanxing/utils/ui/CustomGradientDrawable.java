package com.note8.sanxing.utils.ui;


import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;

/**
 * Created by BenWwChen on 2017/3/25.
 */

public class CustomGradientDrawable extends PaintDrawable {
    public CustomGradientDrawable(final int[] colors, final float[] percentage) {
        ShapeDrawable.ShaderFactory shaderFactory = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                LinearGradient linearGradient = new LinearGradient(0, 0, width, height,
                        colors,
                        percentage,
                        Shader.TileMode.REPEAT);
                return linearGradient;
            }
        };
        this.setShape(new RectShape());
        this.setShaderFactory(shaderFactory);
    }
}
