package sig;

import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.image.ColorModel;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import java.awt.Toolkit;

public class Panel extends JPanel implements Runnable {
    long startTime = System.nanoTime();
    long endTime = System.nanoTime();
    private ColorModel cm;    
    private Thread thread;
    public int width=SigKeeper.SCREEN_WIDTH;
    public int height=SigKeeper.SCREEN_HEIGHT;
    private Image imageBuffer;   
    private MemoryImageSource mImageProducer;   
    public int pixel[];

    public Panel() {
        super(true);
        thread = new Thread(this, "Panel Thread");
    }

    protected static ColorModel getCompatibleColorModel(){        
        GraphicsConfiguration gfx_config = GraphicsEnvironment.
                getLocalGraphicsEnvironment().getDefaultScreenDevice().
                getDefaultConfiguration();        
        return gfx_config.getColorModel();
    }

    public void init(){        
        cm = getCompatibleColorModel();
        width = getWidth();
        height = getHeight();
        SigKeeper.SCREEN_WIDTH=getWidth();
        SigKeeper.SCREEN_HEIGHT=getHeight();
        int screenSize = width * height;
        if(pixel == null || pixel.length < screenSize){
            pixel = new int[screenSize];
        }        
        mImageProducer =  new MemoryImageSource(width, height, cm, pixel,0, width);
        mImageProducer.setAnimated(true);
        mImageProducer.setFullBufferUpdates(true);  
        imageBuffer = Toolkit.getDefaultToolkit().createImage(mImageProducer);        
        if(thread.isInterrupted() || !thread.isAlive()){
            thread.start();
        }
    }
    public /* abstract */ void render(){
        int[] p = pixel; // this avoid crash when resizing
        if(p.length != width * height) return;      
        //a=h/w
    }

    public void repaint() {
        super.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        startTime = System.nanoTime();
        super.paintComponent(g);
        // perform draws on pixels
        render();
        // ask ImageProducer to update image
        mImageProducer.newPixels();            
        // draw it on panel          
        g.drawImage(this.imageBuffer, 0, 0, this);  
        endTime=System.nanoTime();      
        SigKeeper.DRAWLOOPTIME=(endTime-startTime)/1000000f;
    }
    
    /**
     * Overrides ImageObserver.imageUpdate.
     * Always return true, assuming that imageBuffer is ready to go when called
     */
    @Override
    public boolean imageUpdate(Image image, int a, int b, int c, int d, int e) {
        return true;
    }
    @Override
    public void run() {
        while (true) {
            // request a JPanel re-drawing
            repaint();                                  
            try {Thread.sleep(5);} catch (InterruptedException e) {}
        }
    }
}