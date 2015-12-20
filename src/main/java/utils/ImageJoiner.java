import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * This code try to join two BufferedImage
 * @author wangdq
 * 2013-12-29
 */
public class ImageJoiner {
    public static void main(String args[])
    {
        String filename = System.getProperty("user.home")+File.separator;
        System.out.println("filename : " + filename);
        try {
            BufferedImage img1 = ImageIO.read(new File(filename+"1.png"));
            BufferedImage img2=ImageIO.read(new File(filename+"2.png"));
            BufferedImage joinedImg = joinBufferedImage(img1,img2);
            boolean success = ImageIO.write(joinedImg, "png", new File(filename+"joined.png"));
            System.out.println("saved success? "+success);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * join two BufferedImage
     * you can add a orientation parameter to control direction
     * you can use a array to join more BufferedImage
     */

    public static BufferedImage joinBufferedImage(BufferedImage img1,BufferedImage img2) {
        //do some calculate first
        int offset  = 10;
        int height = img1.getHeight() + img2.getHeight() + offset;
        int width = Math.max(img1.getWidth(),img2.getWidth())+offset;
        System.out.println("height " + height + " width " + width + " h1 " + img1.getHeight() + " w1 " + img1.getWidth() + " h2 " + img2.getHeight() + " w2 " + img2.getWidth());
        //create a new buffer and draw two image into the new image
        BufferedImage newImage = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
        //fill background
        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, width, height);
        //draw image
        g2.setColor(oldColor);
        g2.drawImage(img1, null, 0, 0);
        g2.drawImage(img2, null, 0, img1.getHeight() + offset);
        g2.dispose();
        return newImage;
    }
}