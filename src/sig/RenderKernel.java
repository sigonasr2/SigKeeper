package sig;

import com.aparapi.Kernel;

public class RenderKernel extends Kernel{

    int[] pixels;
    float[] tris;
    int width;
    int height;

    RenderKernel(int[] pixels,float[] tris,int width,int height) {
        this.pixels=pixels;
        this.tris=tris;
        this.width=width;
        this.height=height;
    }

    @Override
    public void run() {
        int id = getGlobalId();
        int x1=(int)tris[id*12+0];
        int y1=(int)tris[id*12+1];
        int x2=(int)tris[id*12+4];
        int y2=(int)tris[id*12+5];
        int x3=(int)tris[id*12+8];
        int y3=(int)tris[id*12+9];
        if (x1>=0&&x1<width&&y1>=0&&y1<height) {pixels[y1*width+x1]=0xFF;}
        if (x2>=0&&x2<width&&y2>=0&&y2<height) {pixels[y2*width+x2]=0xFF00;}
        if (x3>=0&&x3<width&&y3>=0&&y3<height) {pixels[y3*width+x3]=0xFF0000;}
    }
    
}
