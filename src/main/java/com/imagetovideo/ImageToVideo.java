package com.imagetovideo;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class ImageToVideo {

  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    System.out
        .println("Enter the directory path of images (for eg /Users/user/Documents/image/pic.jpg)");
    String imgPath = s.nextLine();
    System.out.println(
        "Enter the directory with video file name where resulting video will be saved (for eg /Users/user/Documents/image/test.mp4)");
    String vidPath = s.nextLine();

    File f = new File(imgPath);

    /**
     * Commented code is for if the video is created from a folder of images
     */
//    ArrayList<String> links = new ArrayList<String>();
//    File[] f2 = f.listFiles();
//    for (File f3 : f2) {
//      links.add(f3.getAbsolutePath());
//    }
//    convertJPGtoMovie(links, vidPath);
    System.err.println(imgPath);
    System.err.println(f.getAbsolutePath());
    convertJPGtoMovie(f.getAbsolutePath(), vidPath);
    System.out.println("Video has been created at " + vidPath);
    s.close();
  }

  /**
   * Video created from folder of images
   * @param links
   * @param vidPath
   */
  public static void convertJPGtoMovie(ArrayList<String> links, String vidPath) {
    OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
    FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(vidPath, 640, 720);
    try {
      recorder.setFrameRate(1);
      recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
      recorder.setVideoBitrate(9000);
      recorder.setFormat("mp4");
      recorder.setVideoQuality(0); // maximum quality
      recorder.start();
      System.err.println(links.size());
      for (int j = 0; j < 30; j++) {
        for (int i = 0; i < links.size(); i++) {
          recorder.record(grabberConverter.convert(cvLoadImage(links.get(i))));
        }
      }
      recorder.stop();
    } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Video created from single image path
   * @param link
   * @param vidPath
   */
  public static void convertJPGtoMovie(String link, String vidPath) {
    OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
    FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(vidPath, 640, 720);
    try {
      recorder.setFrameRate(1);
      recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
      recorder.setVideoBitrate(9000);
      recorder.setFormat("mp4");
      recorder.setVideoQuality(0); // maximum quality
      recorder.start();
      System.err.println(link);
      for (int j = 0; j < 30; j++) {
//        for (int i = 0; i < links.size(); i++) {
        recorder.record(grabberConverter.convert(cvLoadImage(link)));
//        }
      }
      recorder.stop();
    } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
      e.printStackTrace();
    }
  }
}
