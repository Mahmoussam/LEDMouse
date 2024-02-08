/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ledmouse;

import java.awt.EventQueue;
/*import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
*/
/**
 *
 * @author el safer
 */
public class LEDMouse {
    public LEDMouse(){
       
       
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Viewr frame = new Viewr();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    /*System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
        System.out.println(mat.dump());
        System.out.println("sad");*/
    }
}
