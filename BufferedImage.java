import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.Hashtable;  // <-- import this

// This is a stub class to make the code compile
// In a real implementation, you would use java.awt.image.BufferedImage
public class BufferedImage extends java.awt.image.BufferedImage {
    public static final int TYPE_INT_ARGB = java.awt.image.BufferedImage.TYPE_INT_ARGB;

    public BufferedImage(int width, int height, int imageType) {
        super(width, height, imageType);
    }

    public BufferedImage(ColorModel cm, WritableRaster raster, boolean isRasterPremultiplied, Hashtable<?, ?> properties) {
        super(cm, raster, isRasterPremultiplied, properties);
    }
}
