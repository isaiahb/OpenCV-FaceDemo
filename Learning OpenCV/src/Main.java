import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by ballah on 11/1/2016.
 */

//if (this.capture.isOpened())

public class Main {

	public static JFrameCV frameCV;
	public float absoluteFaceSize = 0;
	public Main() {



		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.out.println("hello world");

		frameCV = new JFrameCV();
		frameCV.setVisible(true);
		frameCV.setMain(this);
	}
	public static void main(String[] args) throws InterruptedException {
		new Main();
	}

	public void detectFaces() {
		String haar = "C:/Users/ballah/Desktop/opencv/sources/data/haarcascades/haarcascade_frontalface_default.xml";
		String lpb = "C:/Users/ballah/Desktop/opencv/sources/data/lbpcascades/lbpcascade_frontalface.xml";
		CascadeClassifier faceDetector = new CascadeClassifier(lpb);

		Mat image = frameCV.getCameraFrameMat();
		Mat grayFrame = new Mat();

		Imgproc.cvtColor(image, grayFrame, Imgproc.COLOR_BGR2GRAY);
		Imgproc.equalizeHist(grayFrame, grayFrame);
		if (absoluteFaceSize == 0) {
			int height = grayFrame.rows();
			if (Math.round(height * 0.1f) > 0) {
				this.absoluteFaceSize = Math.round(height * 0.2f);
			}
		}

		MatOfRect faces = new MatOfRect();
		faceDetector.detectMultiScale(grayFrame, faces, 1.1, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE, new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size());
		Rect[] facesArray = faces.toArray();
		for (int i = 0; i < facesArray.length; i++)
			Core.rectangle(image, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0, 255), 3);


		frameCV.setImageToDraw(image);
	}

}





//System.out.println("Image details \n Channels: " + image.channels()+ " width: " + image.width()+ " height: " + image.height());
//old code
		/*
		// Detect faces in the image.
		// MatOfRect is a special container class for Rect.
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections); //detectMultiScale will perform the detection

		//System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));

		// Draw a bounding box around each face.
		for (Rect rect : faceDetections.toArray()) {
			Core.rectangle(image, new Point(rect.x, rect.y),
					new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
		}

		// Save the visualized detection.
		String filename = "harr_faceDetected_man.png";
		//System.out.println(String.format("Writing %s", filename));
		//Highgui.imwrite(filename, image);

		//*/

//