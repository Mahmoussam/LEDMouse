/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ledmouse;
import org.opencv.core.Core;
import org.opencv.videoio.VideoCapture;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

public class VideoCap {

    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    VideoCapture cap;
    Mat2Image mat2Img = new Mat2Image();

    VideoCap(){
        
        cap = new VideoCapture();
        cap.open(0);//2 worked for me
        if(cap.isOpened()){
            System.out.println("camera here");
        }
        else{
            System.out.println("Camera not working");
        }
    }
    
    void erode(Mat img, int amount) {
    Mat kernel = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT,
            new Size(2 * amount + 1, 2 * amount + 1),
            new Point(amount, amount));
    Imgproc.erode(img, img, kernel);
    
}
    void dilate(Mat img, int amount) {
    Mat kernel = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT,
            new Size(2 * amount + 1, 2 * amount + 1),
            new Point(amount, amount));
    Imgproc.dilate(img, img, kernel);
}
   void morphOpen(Mat frame){
       //erode then dilation
       //erode(frame, am);
       //dilate(frame, am);
       Mat kernel=Mat.ones(2,2, CvType.CV_8U);
       Imgproc.erode(frame, frame, kernel);
       Imgproc.dilate(frame, frame, kernel);
   }
   void morphClose(Mat frame){
       //dilation then erode
       //dilate(frame, am);
       //erode(frame, am);
       Mat kernel=Mat.ones(25,25, CvType.CV_8U);
       Imgproc.dilate(frame, frame, kernel);
       Imgproc.erode(frame, frame, kernel);
       
   }
    BufferedImage getOneFrame() {
        cap.read(mat2Img.mat);
        Mat frame=mat2Img.mat;
        Mat dest=Mat.zeros(frame.size(), CvType.CV_8UC3);
        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2HSV);
        Core.inRange(frame, new Scalar(161, 155, 84), new Scalar(179,255,255), frame);
        
        Mat kernel2=Mat.ones(18,18, CvType.CV_8U);
        Imgproc.dilate(frame, frame, kernel2);
        List<MatOfPoint> contours=new ArrayList<>();
        Imgproc.findContours(frame, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        System.out.println(contours.size());
        double maxArea=0;
        int maxAreaIDX=0;
        for (int idx=0;idx<contours.size();idx++){
            double area=Imgproc.contourArea(contours.get(idx));
            if(maxArea<area){
                maxArea=area;
                maxAreaIDX=idx;
            }
            //System.out.println("Drawing");
            //System.out.println(maxAreaIDX);
           if(contours.size()>0){
               Imgproc.drawContours(dest, contours, maxAreaIDX, new Scalar(0,255,255),10);
                MatOfPoint cont=contours.get(maxAreaIDX);
                Moments mom=Imgproc.moments(cont);
                double x=mom.get_m10()/mom.get_m00();
                double y=mom.get_m01()/mom.get_m00();
                System.out.println("X : "+x+" || Y : "+y);
                
           }
            
        }
        return mat2Img.getImage(dest);
    }
}