package com.hdr.openqr;

import android.graphics.Bitmap;
import android.view.TextureView;

public final class CodeScanner implements Runnable {

    private final TextureView cameraView;
    private final MainActivity main;

    private static final int[][] ALIGNMENT_PATTERN = new int[][] {
        {0, 0, 0, 0, 0, 0, 0},
        {0, 1, 1, 1, 1, 1, 0},
        {0, 1, 0, 0, 0, 1, 0},
        {0, 1, 0, 0, 0, 1, 0},
        {0, 1, 0, 0, 0, 1, 0},
        {0, 1, 1, 1, 1, 1, 0},
        {0, 0, 0, 0, 0, 0, 0}
    };

    private CodeScanner(TextureView tv, MainActivity main) {
        this.cameraView = tv;
        this.main = main;
    }

    public void run() {
        while (!main.paused) {
            Bitmap bitmap = cameraView.getBitmap();
            scan(bitmap);
        }
    }

    private void scan(Bitmap input) {

    }

    public static synchronized CodeScanner createAndStartInstance(TextureView view, MainActivity main) {
        CodeScanner cs = new CodeScanner(view, main);
        Thread t = new Thread(cs);
        t.start();
        return cs;
    }
}
