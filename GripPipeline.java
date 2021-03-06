
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.vision.VisionPipeline;


/**
* GripPipeline class.
*
* <p>An OpenCV pipeline generated by GRIP.
*
* @author GRIP
*/
public class GripPipeline implements VisionPipeline {

	//Outputs
	private Mat hsvThresholdOutput = new Mat();
	private Point newPoint0Output = new Point();
	private Point newPoint1Output = new Point();
	private Mat cvRectangleOutput = new Mat();
	private ArrayList<MatOfPoint> findContoursOutput = new ArrayList<MatOfPoint>();
	private ArrayList<MatOfPoint> filterContoursOutput = new ArrayList<MatOfPoint>();

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	/**
	 * This is the primary method that runs the entire pipeline and updates the outputs.
	 */
	public void process(Mat source0) {
		// Step HSV_Threshold0:
		Mat hsvThresholdInput = source0;
		double[] hsvThresholdHue = {0.0, 180.0};
		double[] hsvThresholdSaturation = {0.0, 144.60101867572158};
		double[] hsvThresholdValue = {0.0, 60.17826825127334};
		hsvThreshold(hsvThresholdInput, hsvThresholdHue, hsvThresholdSaturation, hsvThresholdValue, hsvThresholdOutput);

		// Step New_Point0:
		double newPoint0X = 1.0;
		double newPoint0Y = 0.0;
		newPoint(newPoint0X, newPoint0Y, newPoint0Output);

		// Step New_Point1:
		double newPoint1X = -1.0;
		double newPoint1Y = -1.0;
		newPoint(newPoint1X, newPoint1Y, newPoint1Output);

		// Step CV_rectangle0:
		Mat cvRectangleSrc = hsvThresholdOutput;
		Point cvRectanglePt1 = newPoint0Output;
		Point cvRectanglePt2 = newPoint1Output;
		Scalar cvRectangleColor = new Scalar(0.0, 0.0, 0.0, 0.0);
		double cvRectangleThickness = 0.0;
		int cvRectangleLinetype = Core.LINE_8;
		double cvRectangleShift = 0.0;
		cvRectangle(cvRectangleSrc, cvRectanglePt1, cvRectanglePt2, cvRectangleColor, cvRectangleThickness, cvRectangleLinetype, cvRectangleShift, cvRectangleOutput);

		// Step Find_Contours0:
		Mat findContoursInput = cvRectangleOutput;
		boolean findContoursExternalOnly = true;
		findContours(findContoursInput, findContoursExternalOnly, findContoursOutput);

		// Step Filter_Contours0:
		ArrayList<MatOfPoint> filterContoursContours = findContoursOutput;
		double filterContoursMinArea = 0.0;
		double filterContoursMinPerimeter = 0.0;
		double filterContoursMinWidth = 0.0;
		double filterContoursMaxWidth = 1000.0;
		double filterContoursMinHeight = 0.0;
		double filterContoursMaxHeight = 1100.0;
		double[] filterContoursSolidity = {87.23021582733811, 100.0};
		double filterContoursMaxVertices = 1000.0;
		double filterContoursMinVertices = 4.0;
		double filterContoursMinRatio = 0.2;
		double filterContoursMaxRatio = 1000.0;
		filterContours(filterContoursContours, filterContoursMinArea, filterContoursMinPerimeter, filterContoursMinWidth, filterContoursMaxWidth, filterContoursMinHeight, filterContoursMaxHeight, filterContoursSolidity, filterContoursMaxVertices, filterContoursMinVertices, filterContoursMinRatio, filterContoursMaxRatio, filterContoursOutput);

	}

	/**
	 * This method is a generated getter for the output of a HSV_Threshold.
	 * @return Mat output from HSV_Threshold.
	 */
	public Mat hsvThresholdOutput() {
		return hsvThresholdOutput;
	}

	/**
	 * This method is a generated getter for the output of a New_Point.
	 * @return Point output from New_Point.
	 */
	public Point newPoint0Output() {
		return newPoint0Output;
	}

	/**
	 * This method is a generated getter for the output of a New_Point.
	 * @return Point output from New_Point.
	 */
	public Point newPoint1Output() {
		return newPoint1Output;
	}

	/**
	 * This method is a generated getter for the output of a CV_rectangle.
	 * @return Mat output from CV_rectangle.
	 */
	public Mat cvRectangleOutput() {
		return cvRectangleOutput;
	}

	/**
	 * This method is a generated getter for the output of a Find_Contours.
	 * @return ArrayList<MatOfPoint> output from Find_Contours.
	 */
	public ArrayList<MatOfPoint> findContoursOutput() {
		return findContoursOutput;
	}

	/**
	 * This method is a generated getter for the output of a Filter_Contours.
	 * @return ArrayList<MatOfPoint> output from Filter_Contours.
	 */
	public ArrayList<MatOfPoint> filterContoursOutput() {
		return filterContoursOutput;
	}


	/**
	 * Segment an image based on hue, saturation, and value ranges.
	 *
	 * @param input The image on which to perform the HSL threshold.
	 * @param hue The min and max hue
	 * @param sat The min and max saturation
	 * @param val The min and max value
	 * @param output The image in which to store the output.
	 */
	private void hsvThreshold(Mat input, double[] hue, double[] sat, double[] val,
	    Mat out) {
		Imgproc.cvtColor(input, out, Imgproc.COLOR_BGR2HSV);
		Core.inRange(out, new Scalar(hue[0], sat[0], val[0]),
			new Scalar(hue[1], sat[1], val[1]), out);
	}

	/**
	 * Fills a point with given x and y values.
	 * @param x the x value to put in the point
	 * @param y the y value to put in the point
	 * @param point the point to fill
	 */
	private void newPoint(double x, double y, Point point) {
		point.x = x;
		point.y = y;
	}

	/**
	 * Draws a rectangle on an image.
	 * @param src Image to draw rectangle on.
	 * @param pt1 one corner of the rectangle.
	 * @param pt2 opposite corner of the rectangle.
	 * @param color Scalar indicating color to make the rectangle.
	 * @param thickness Thickness of the lines of the rectangle.
	 * @param lineType Type of line for the rectangle.
	 * @param shift Number of decimal places in the points.
	 * @param dst output image.
	 */
	private void cvRectangle(Mat src, Point pt1, Point pt2, Scalar color,
		double thickness, int lineType, double shift, Mat dst) {
		src.copyTo(dst);
		if (color == null) {
			color = Scalar.all(1.0);
		}
		Imgproc.rectangle(dst, pt1, pt2, color, (int)thickness, lineType, (int)shift);
	}

	/**
	 * Sets the values of pixels in a binary image to their distance to the nearest black pixel.
	 * @param input The image on which to perform the Distance Transform.
	 * @param type The Transform.
	 * @param maskSize the size of the mask.
	 * @param output The image in which to store the output.
	 */
	private void findContours(Mat input, boolean externalOnly,
		List<MatOfPoint> contours) {
		Mat hierarchy = new Mat();
		contours.clear();
		int mode;
		if (externalOnly) {
			mode = Imgproc.RETR_EXTERNAL;
		}
		else {
			mode = Imgproc.RETR_LIST;
		}
		int method = Imgproc.CHAIN_APPROX_SIMPLE;
		Imgproc.findContours(input, contours, hierarchy, mode, method);
	}


	/**
	 * Filters out contours that do not meet certain criteria.
	 * @param inputContours is the input list of contours
	 * @param output is the the output list of contours
	 * @param minArea is the minimum area of a contour that will be kept
	 * @param minPerimeter is the minimum perimeter of a contour that will be kept
	 * @param minWidth minimum width of a contour
	 * @param maxWidth maximum width
	 * @param minHeight minimum height
	 * @param maxHeight maximimum height
	 * @param Solidity the minimum and maximum solidity of a contour
	 * @param minVertexCount minimum vertex Count of the contours
	 * @param maxVertexCount maximum vertex Count
	 * @param minRatio minimum ratio of width to height
	 * @param maxRatio maximum ratio of width to height
	 */
	private void filterContours(List<MatOfPoint> inputContours, double minArea,
		double minPerimeter, double minWidth, double maxWidth, double minHeight, double
		maxHeight, double[] solidity, double maxVertexCount, double minVertexCount, double
		minRatio, double maxRatio, List<MatOfPoint> output) {
		final MatOfInt hull = new MatOfInt();
		output.clear();
		//operation
		for (int i = 0; i < inputContours.size(); i++) {
			final MatOfPoint contour = inputContours.get(i);
			final Rect bb = Imgproc.boundingRect(contour);
			if (bb.width < minWidth || bb.width > maxWidth) continue;
			if (bb.height < minHeight || bb.height > maxHeight) continue;
			final double area = Imgproc.contourArea(contour);
			if (area < minArea) continue;
			if (Imgproc.arcLength(new MatOfPoint2f(contour.toArray()), true) < minPerimeter) continue;
			Imgproc.convexHull(contour, hull);
			MatOfPoint mopHull = new MatOfPoint();
			mopHull.create((int) hull.size().height, 1, CvType.CV_32SC2);
			for (int j = 0; j < hull.size().height; j++) {
				int index = (int)hull.get(j, 0)[0];
				double[] point = new double[] { contour.get(index, 0)[0], contour.get(index, 0)[1]};
				mopHull.put(j, 0, point);
			}
			final double solid = 100 * area / Imgproc.contourArea(mopHull);
			if (solid < solidity[0] || solid > solidity[1]) continue;
			if (contour.rows() < minVertexCount || contour.rows() > maxVertexCount)	continue;
			final double ratio = bb.width / (double)bb.height;
			if (ratio < minRatio || ratio > maxRatio) continue;
			output.add(contour);
		}
	}




}

