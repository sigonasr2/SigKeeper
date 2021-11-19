package sig;

import java.awt.Graphics;
import javax.swing.JPanel;

import com.aparapi.Range;

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
    public int[] pixel;
    public float[] triPoints;

    RenderKernel renderer;

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
        triPoints = new float[120000000];
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
        renderer = new RenderKernel(pixel,triPoints,SigKeeper.SCREEN_WIDTH,SigKeeper.SCREEN_HEIGHT);  
        renderer.setExplicit(true);
        imageBuffer = Toolkit.getDefaultToolkit().createImage(mImageProducer);    
        if(thread.isInterrupted() || !thread.isAlive()){
            thread.start();
        }
        //renderer.setExplicit(true);
        //renderer.put(pixel);
    }
    public /* abstract */ void render(){
        int[] p = pixel; // this avoid crash when resizing
        if(p!=null && p.length != width * height) return;      
        for (int x=0;x<SigKeeper.SCREEN_WIDTH;x++) {
            for (int y=0;y<SigKeeper.SCREEN_HEIGHT;y++) {
                pixel[y*SigKeeper.SCREEN_WIDTH+x]=0;
            }
        }
        //a=h/w
        for (int i=0;i<SigKeeper.tris.size();i++) {
            Triangle t = SigKeeper.tris.get(i);
            triPoints[i*12+0]=t.A.x;
            triPoints[i*12+1]=t.A.y;
            triPoints[i*12+2]=t.A.z;
            triPoints[i*12+3]=t.A.w;
            triPoints[i*12+4]=t.B.x;
            triPoints[i*12+5]=t.B.y;
            triPoints[i*12+6]=t.B.z;
            triPoints[i*12+7]=t.B.w;
            triPoints[i*12+8]=t.C.x;
            triPoints[i*12+9]=t.C.y;
            triPoints[i*12+10]=t.C.z;
            triPoints[i*12+11]=t.C.w;
        }
        //renderer.put(triPoints);
        if (renderer!=null) {
            renderer.execute(Range.create(1000000));
        }
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