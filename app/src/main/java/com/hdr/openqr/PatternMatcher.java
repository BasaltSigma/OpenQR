package com.hdr.openqr;

import android.graphics.Bitmap;
import android.util.Pair;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

// based on the algorithm found at: https://iopscience.iop.org/article/10.1088/1742-6596/1693/1/012163/pdf
// greyscale conversion from https://stackoverflow.com/questions/687261/converting-rgb-to-grayscale-intensity
public final class PatternMatcher {

    private static final double GAMMA = 2.2f;
    private static final float RED_CORRECTION = 0.2126f;
    private static final float GREEN_CORRECTION = 0.7152f;
    private static final float BLUE_CORRECTION = 0.0722f;

    @NonNull
    protected static float[][] convertBitmapToGreyscaleNormalized(@NonNull Bitmap bmp) {
        float[][] greyscale = new float[bmp.getWidth()][bmp.getHeight()];
        for (int x = 0; x < bmp.getWidth(); x++) {
            for (int y = 0; y < bmp.getHeight(); y++) {
                int color = bmp.getPixel(x, y);
                int r = (color >> 16) & 0xff;
                int g = (color >> 8) & 0xff;
                int b = color & 0xff;
                float rLin = (float)Math.pow(r / 255.0, GAMMA);
                float gLin = (float)Math.pow(g / 255.0, GAMMA);
                float bLin = (float)Math.pow(b / 255.0, GAMMA);
                float Y = RED_CORRECTION * rLin + GREEN_CORRECTION * gLin + BLUE_CORRECTION * bLin;
                float l = 116.0f * (float)Math.pow((double)Y, 1.0 / 3.0) - 16.0f;
                greyscale[x][y] = l;
            }
        }
        return greyscale;
    }

    private static int[][] applyThresholdToGreyscale(@NonNull float[][] greyscaleNormalized, float threshold) {
        int[][] result = new int[greyscaleNormalized.length][greyscaleNormalized[0].length];
        for (int i = 0; i < greyscaleNormalized.length; i++) {
            for (int j = 0; j < greyscaleNormalized[i].length; j++) {
                result[i][j] = greyscaleNormalized[i][j] < threshold ? 0 : 1;
            }
        }
        return result;
    }

    public static void findWithAllThresholds(Bitmap bmp, int[][] pattern) {
        float[][] greyscale = convertBitmapToGreyscaleNormalized(bmp);
        for (float threshold = 0.1f; threshold <= 1.0f; threshold += 0.1f) {
            int[][] thresholdMask = applyThresholdToGreyscale(greyscale, threshold);

        }
    }

    private static List<Pair<Integer, Integer>> getListOfMatchingPositions(int[][] mask, int[][] pattern) {
        List<Pair<Integer, Integer>> points = new ArrayList<Pair<Integer, Integer>>();
        
        return points;
    }
}
