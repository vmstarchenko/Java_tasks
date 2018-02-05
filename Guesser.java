import java.awt.Color;//Дз 2 encoding bmp->ascii, распознавание цифр P>50% Дз 3 плакат с разделимыми цифрами # (многопотоковое распознавание); не использовать  java.util.concurrent
import java.awt.image.BufferedImage;//0.5 - вес всех домашек, 0.5 - вес проекта
import java.io.File;//Дедлайны: Дз 2 - 5 февраля, Дз 3 -
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.awt.Graphics2D;
import java.lang.System;
import java.lang.Math;

import javax.imageio.ImageIO;

public class Guesser {
    private BufferedImage num;
    private BufferedImage img;
    private double pixval;
    private PrintWriter prntwrt;
    private FileWriter filewrt;



    public static void resize(String inputImagePath,
            String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        inputImage = inputImage.getSubimage(0,0, 200, 200); //subimage
        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);

        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }


    public void guess(String imgname) {
        try {
            img = ImageIO.read(new File(imgname));
        } catch (IOException e) {
        }
        int top=0, bottom=img.getHeight(), left=0, right=img.getWidth();


        boolean f =false;
        for (int i = 0; i < img.getHeight(); i++) {
            boolean b = false;
            for (int j = 0; j < img.getWidth(); j++) {
                Color pixcol = new Color(img.getRGB(j, i));
                pixval = (((pixcol.getRed() * 0.30) + (pixcol.getBlue() * 0.59) + (pixcol
                        .getGreen() * 0.11)));//0.2989 * R + 0.5870 * G + 0.1140 * B
                if (pixval < 100) {
                    b = true;
                    break;
                }
            }
            if (!f && b) {
                top = i;
                f = true;
            }
            if (f && !b) {
                bottom = i;
                break;
            }
        }
        f =false;
        for (int j = 0; j < img.getWidth(); j++) {
            boolean b = false;
            for (int i = 0; i < img.getHeight(); i++) {
                Color pixcol = new Color(img.getRGB(j, i));
                pixval = (((pixcol.getRed() * 0.30) + (pixcol.getBlue() * 0.59) + (pixcol
                        .getGreen() * 0.11)));//0.2989 * R + 0.5870 * G + 0.1140 * B
                if (pixval < 100) {
                    b = true;
                    break;
                }
            }
            if (!f && b) {
                left = j;
                f = true;
            }
            if (f && !b) {
                right = j;
                break;
            }
        }


        BufferedImage extracted = img.getSubimage(left, top, right-left, bottom-top);

        BufferedImage outputImage = new BufferedImage(100,100, img.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(extracted, 0, 0, 100, 100, null);
        g2d.dispose();
        int lowest_error = 99999;
        int guess = 0;

        for (int n = 0; n < 10; ++n) {

            int error = 0;
            try {
                num = ImageIO.read(new File("numbers/"+Integer.toString(n)+".jpg"));
            } catch (IOException e) {
            }
            for(int i = 0; i < 100; ++i) {
                for (int j = 0; j < 100; ++j) {
                  Color pixcol_n = new Color(num.getRGB(j, i));
                  Color pixcol_e = new Color(outputImage.getRGB(j, i));
                  double pixval_n;
                  double pixval_e;
                  int bn = 0, be = 0;

                  pixval_n = (((pixcol_n.getRed() * 0.30) + (pixcol_n.getBlue() * 0.59) + (pixcol_n
                          .getGreen() * 0.11)));//0.2989 * R + 0.5870 * G + 0.1140 *

                  pixval_e = (((pixcol_e.getRed() * 0.30) + (pixcol_e.getBlue() * 0.59) + (pixcol_e
                          .getGreen() * 0.11)));//0.2989 * R + 0.5870 * G + 0.1140 *
                  if (pixval_n < 100) {
                      bn = 1;
                  }
                  if (pixval_e < 100) {
                      be = 1;
                  }
                  error += Math.abs(be - bn);
                }
            }
            if (error < lowest_error) {
                lowest_error = error;
                guess = n;
            }
        }
        System.out.print(guess);
    }



    public static void main(String[] args) {
        Guesser obj = new Guesser();
        /*
        try{
          Img2Ascii.resize(args[0], "qwe.png", 100,100);
        } catch (IOException ex) {
            System.out.println("Error resizing the image.");
            ex.printStackTrace();
        }*/
        obj.guess(args[0]);
    }
}
