package sig;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Cursor;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener; 
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;

public class SigKeeper implements WindowFocusListener,KeyListener,MouseListener,MouseMotionListener,MouseWheelListener{
    JFrame frame;
    Panel panel;
    public static int SCREEN_WIDTH=1280;
    public static int SCREEN_HEIGHT=720;
    public final static long TIMEPERTICK = 16666667l;
    public static float DRAWTIME=0;
    public static float DRAWLOOPTIME=0;
    public static int MAX_TRIANGLE_COUNT=100;

    public static Cursor invisibleCursor;

    public static List<Triangle> tris = new ArrayList<Triangle>();
    public static int[] texData = new int[256*256];

    SigKeeper() {

        try {
            BufferedImage img = ImageIO.read(new File("textures_256.png"));
            WritableRaster r = img.getRaster();
            for (int x=0;x<256;x++) {
                for (int y=0;y<256;y++) {
                    int[] pixel = r.getPixel(x,y,new int[4]);
                    texData[x+y*256]=pixel[2]+(pixel[1]<<8)+(pixel[0]<<16)+(pixel[3]<<24);
                }
            }
            
        } catch (IOException e1) {
            System.out.println("Could not load game textures! (textures_256.png not found)");
        }

        frame = new JFrame("SigKeeper");
        panel = new Panel();

        frame.getContentPane().addMouseListener(this);
        frame.getContentPane().addMouseMotionListener(this);
        frame.addKeyListener(this);
        frame.getContentPane().addMouseWheelListener(this);
        frame.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        frame.add(panel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        invisibleCursor = frame.getToolkit().createCustomCursor(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB),new Point(),null);

        panel.setCursor(invisibleCursor);
        GraphicsDevice screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        frame.setLocation((screen.getDisplayMode().getWidth()-SCREEN_WIDTH)/2,(screen.getDisplayMode().getHeight()-SCREEN_HEIGHT)/2);
        frame.setVisible(true);
        panel.init();

        for (int i=0;i<Panel.triPoints.length/21;i++) {
            tris.add(new Triangle(new Vector((float)Math.random()*SCREEN_WIDTH,(float)Math.random()*SCREEN_HEIGHT,(float)Math.random()*100),
                new Vector((float)Math.random()*SCREEN_WIDTH,(float)Math.random()*SCREEN_HEIGHT,(float)Math.random()*100),
                new Vector((float)Math.random()*SCREEN_WIDTH,(float)Math.random()*SCREEN_HEIGHT,(float)Math.random()*100)));
        }
        /*tris.add(new Triangle(new Vertex(50,300,100),new Vertex(300,300,100),new Vertex(50,100,100)));
        tris.add(new Triangle(new Vertex(300,300,100),new Vertex(300,100,100),new Vertex(50,100,100)));*/
        for (int i=0;i<SigKeeper.tris.size();i++) {
            Triangle t = SigKeeper.tris.get(i);
            Panel.triPoints[i*22+0]=t.A.x;
            Panel.triPoints[i*22+1]=t.A.y;
            Panel.triPoints[i*22+2]=t.A.z;
            Panel.triPoints[i*22+3]=t.A.w;
            Panel.triPoints[i*22+4]=t.B.x;
            Panel.triPoints[i*22+5]=t.B.y;
            Panel.triPoints[i*22+6]=t.B.z;
            Panel.triPoints[i*22+7]=t.B.w;
            Panel.triPoints[i*22+8]=t.C.x;
            Panel.triPoints[i*22+9]=t.C.y;
            Panel.triPoints[i*22+10]=t.C.z;
            Panel.triPoints[i*22+11]=t.C.w;
            Panel.triPoints[i*22+12]=t.T.u;
            Panel.triPoints[i*22+13]=t.T.v;
            Panel.triPoints[i*22+14]=t.T.w;
            Panel.triPoints[i*22+15]=t.U.u;
            Panel.triPoints[i*22+16]=t.U.v;
            Panel.triPoints[i*22+17]=t.U.w;
            Panel.triPoints[i*22+18]=t.V.u;
            Panel.triPoints[i*22+19]=t.V.v;
            Panel.triPoints[i*22+20]=t.V.w;
            Panel.triPoints[i*22+21]=t.tex;
        }

        new Thread() {
            public void run(){
                while (true) {
                    long startTime = System.nanoTime();
                    runGameLoop();
                    panel.repaint();
                    Toolkit.getDefaultToolkit().sync();
                    long endTime = System.nanoTime();
                    long diff = endTime-startTime;
                    try {
                        long sleepTime = TIMEPERTICK - diff;
                        long millis = (sleepTime)/1000000;
                        int nanos = (int)(sleepTime-(((sleepTime)/1000000)*1000000));
                        //System.out.println("FRAME DRAWING: Sleeping for ("+millis+"ms,"+nanos+"ns) - "+(diff)+"ns");
                        DRAWTIME = (float)diff/1000000;
                        frame.setTitle("Game Loop: "+DRAWTIME+"ms, Draw Loop: "+DRAWLOOPTIME+"ms");
                        if (sleepTime>0) {
                            Thread.sleep(millis,nanos);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    
    protected void runGameLoop() {
    }

    public static void main(String[] args) {
        SigKeeper keeper = new SigKeeper();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (int i=0;i<tris.size();i++) {
            Panel.triPoints[i*22+0]+=1;
            Panel.triPoints[i*22+1]+=1;
            Panel.triPoints[i*22+4]-=1;
            Panel.triPoints[i*22+5]+=1;
            Panel.triPoints[i*22+8]+=1;
            Panel.triPoints[i*22+9]-=1;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowGainedFocus(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowLostFocus(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }
}