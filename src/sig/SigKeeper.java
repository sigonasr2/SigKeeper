package sig;

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

    public static Cursor invisibleCursor;

    SigKeeper() {
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
        frame.setVisible(true);
        GraphicsDevice screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        frame.setLocation((screen.getDisplayMode().getWidth()-SCREEN_WIDTH)/2,(screen.getDisplayMode().getHeight()-SCREEN_HEIGHT)/2);
        panel.init();

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
        // TODO Auto-generated method stub
        
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