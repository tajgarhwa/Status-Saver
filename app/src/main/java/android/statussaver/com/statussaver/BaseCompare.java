package android.statussaver.com.statussaver;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.util.Comparator;

public class BaseCompare {
    public static class compare implements Comparator<File> {
        public compare() {
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public int compare(File f1, File f2) {
            return Long.compare(f2.lastModified(), f1.lastModified());

        }
    }
}
