import java.awt.Color;//Дз 2 encoding bmp->ascii, распознавание цифр P>50% Дз 3 плакат с разделимыми цифрами # (многопотоковое распознавание); не использовать  java.util.concurrent
import java.awt.image.BufferedImage;//0.5 - вес всех домашек, 0.5 - вес проекта
import java.io.File;//Дедлайны: Дз 2 - 5 февраля, Дз 3 -
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.awt.Graphics2D;


import javax.imageio.ImageIO;

public class Img2Ascii {

    private BufferedImage img;
    private double pixval;
    private PrintWriter prntwrt;
    private FileWriter filewrt;

    public Img2Ascii() {
        try {
            prntwrt = new PrintWriter(filewrt = new FileWriter("asciiart.txt",
                    true));
        } catch (IOException ex) {
        }
    }


    public void convertToAscii(String imgname) {
        try {
            img = ImageIO.read(new File(imgname));
        } catch (IOException e) {
        }

        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color pixcol = new Color(img.getRGB(j, i));
                pixval = (((pixcol.getRed() * 0.30) + (pixcol.getBlue() * 0.59) + (pixcol
                        .getGreen() * 0.11)));//0.2989 * R + 0.5870 * G + 0.1140 * B
                print(strChar(pixval));
            }
            try {
                prntwrt.println("");
                prntwrt.flush();
                filewrt.flush();
            } catch (Exception ex) {
            }
        }
    }

    public String strChar(double g) {
        String str = " ";
        if (g >= 240) {
            str = " ";
        } else if (g >= 210) {
            str = ".";
        } else if (g >= 190) {
            str = "*";
        } else if (g >= 170) {
            str = "+";
        } else if (g >= 120) {
            str = "^";
        } else if (g >= 110) {
            str = "&";
        } else if (g >= 80) {
            str = "8";
        } else if (g >= 60) {
            str = "#";
        } else {
            str = "@";
        }
        return str;
    }

    public void print(String str) {
        try {
            prntwrt.print(str);
            prntwrt.flush();
            filewrt.flush();
        } catch (Exception ex) {
        }
    }

    public static void main(String[] args) {
        Img2Ascii obj = new Img2Ascii();
        obj.convertToAscii(args[0]);
    }
}
