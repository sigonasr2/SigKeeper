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

public class Panel extends JPanel{
    long startTime = System.nanoTime();
    long endTime = System.nanoTime();
    private ColorModel cm;    
    public int width=SigKeeper.SCREEN_WIDTH;
    public int height=SigKeeper.SCREEN_HEIGHT;
    private Image imageBuffer;   
    private MemoryImageSource mImageProducer;   
    public static int[] pixel; 
    public static float[] depthBuffer;
    public static float[] triPoints;

    RenderKernel renderer;

    public Panel() {
        super(true);
    }

    protected static ColorModel getCompatibleColorModel(){        
        GraphicsConfiguration gfx_config = GraphicsEnvironment.
                getLocalGraphicsEnvironment().getDefaultScreenDevice().
                getDefaultConfiguration();        
        return gfx_config.getColorModel();
    }

    public void init(){
        triPoints = new float[22*SigKeeper.MAX_TRIANGLE_COUNT];
        cm = getCompatibleColorModel();
        width = getWidth();
        height = getHeight();
        SigKeeper.SCREEN_WIDTH=getWidth();
        SigKeeper.SCREEN_HEIGHT=getHeight();
        int screenSize = width * height;
        if(pixel == null || pixel.length < screenSize){
            pixel = new int[screenSize];   
            depthBuffer = new float[screenSize];
        }        
        mImageProducer =  new MemoryImageSource(width, height, cm, pixel,0, width);
        mImageProducer.setAnimated(true);
        mImageProducer.setFullBufferUpdates(true);  
        renderer = new RenderKernel(pixel,triPoints,SigKeeper.texData,SigKeeper.SCREEN_WIDTH,SigKeeper.SCREEN_HEIGHT,256,256,depthBuffer);  
        renderer.setExplicit(true);
        imageBuffer = Toolkit.getDefaultToolkit().createImage(mImageProducer);    
        //renderer.setExplicit(true);
        //renderer.put(pixel);
    }
    public /* abstract */ void render(){
        int[] p = pixel; // this avoid crash when resizing
        if(p==null || p.length != width * height) return;      
        for (int x=0;x<SigKeeper.SCREEN_WIDTH;x++) {
            for (int y=0;y<SigKeeper.SCREEN_HEIGHT;y++) {
                pixel[y*SigKeeper.SCREEN_WIDTH+x]=0;
                depthBuffer[y*SigKeeper.SCREEN_WIDTH+x]=0;
            }
        }
        //a=h/w
        //renderer.put(triPoints);
        if (renderer!=null&&SigKeeper.tris.size()>0) {
            renderer.execute(Range.create(SigKeeper.tris.size()));
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
}