package imgProcess;

import javafx.scene.image.Image;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;


public class Processing {


    public ArrayList<double[]> pixels = new ArrayList<>();

    private static Mat toGrayScale(File image) {


        String path = image.getAbsolutePath();
        Mat source = Imgcodecs.imread(path);

        Mat destination = new Mat();

        // Converting the image to gray scale and
        // saving it in the dst matrix
        Imgproc.cvtColor(source, destination, Imgproc.COLOR_RGB2GRAY);

        return destination;

    }

    public static Image processImage(File image) {

        Mat source = toGrayScale(image);

        //binarizing the image
        int adapt = Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C;
        int thresh = Imgproc.THRESH_BINARY_INV;
        Imgproc.adaptiveThreshold(source, source, 225, adapt, thresh, 15, 2);


        source = getNeighbors(source);

        MatOfByte byteMat = new MatOfByte();
        Imgcodecs.imencode(".bmp", source, byteMat);


        return new Image(new ByteArrayInputStream(byteMat.toArray()));


    }



    public static Mat getNeighbors(Mat image) {

        //loopin
        double white = 225.0;
        Mat finished = new Mat();
        image.copyTo(finished);
        double part_width = finished.width()/5;
        double part_height = finished.height()/5;
        System.out.println(image.cols());
        for (double i = 0; i < image.rows(); i += part_height) {
            for (double j = 0; j < image.cols(); j += part_width) {
                Rect roi = new Rect((int) j, (int)i, (int)part_width, (int)part_height);
                Mat predImg = image.submat(roi).clone();
                Imgproc.rectangle(finished, new Point(j, i), new Point(j + part_width, i + part_height), new Scalar(255.0, 255.0, 255.0));

            }
        }

        return finished;
    }
}
