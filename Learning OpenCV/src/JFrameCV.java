import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by ballah on 11/2/2016.
 */

public class JFrameCV extends JFrame {
	private JPanel panel;
	private BufferedImage imageToDraw;
	boolean drawn = true;
	private Mat2Image mat2Image = new Mat2Image();
	private Main main;
	VideoCap videoCap = new VideoCap();

	public JFrameCV() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 490);
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		panel.setLayout(null);

		new MyThread().start();
	}

	public void setMain(Main main) {
		this.main = main;
	}
	public BufferedImage getImage() {

		if (drawn) {
			Mat mat = videoCap.getOneFrameMat();
			Core.flip(mat, mat, 1);
			Imgproc.cvtColor(mat, mat,Imgproc.COLOR_RGB2BGR);

			return (mat2Image.getImage(mat));
		}
		else {
			drawn = true;
			return imageToDraw;
		}
	}
	public void paint(Graphics g){
		g = panel.getGraphics();
		g.drawImage(getImage(), 0, 0, this);

		if (drawn)
			imageToDraw = null;
	}

	public Mat getCameraFrameMat(){
		Mat mat = videoCap.getOneFrameMat();
		return mat;
	}

	public void setImageToDraw(Mat image) {
		drawn = false;
		Mat mat = new Mat();
		Core.flip(image, mat, 1);
		Imgproc.cvtColor(mat, mat,Imgproc.COLOR_RGB2BGR);
		imageToDraw = mat2Image.getImage(mat, false);
	}

	class MyThread extends Thread{
		@Override
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while (this.isAlive()) {
				if (main != null) {
					main.detectFaces();
				}
				repaint();
				try { Thread.sleep(25);
				} catch (InterruptedException e) {    }
			}
		}
	}
}

