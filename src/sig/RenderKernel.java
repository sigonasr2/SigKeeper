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
        FillTriangle(x1,y1,x2,y2,x3,y3,0xFF0000);
    }

    void DrawHorizontalLine(int sx,int ex,int ny,int col) {
        for (int i=sx;i<=ex;i++) {
            Draw(i,ny,col);
        }
    }

    void FillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, int col)
	{
		int t1x=0, t2x=0, y=0, minx=0, maxx=0, t1xp=0, t2xp=0;
		boolean changed1 = false;
		boolean changed2 = false;
		int signx1=0, signx2=0, dx1=0, dy1=0, dx2=0, dy2=0;
		int e1=0, e2=0;
		// Sort vertices
		if (y1>y2) {int t=y1;y1=y2;y2=t;t=x1;x1=x2;x2=t;}
		if (y1>y3) {int t=y1;y1=y3;y3=t;t=x1;x1=x3;x3=t;}
		if (y2>y3) {int t=y2;y2=y3;y3=t;t=x2;x2=x3;x3=t;}

		t1x = t2x = x1; y = y1;   // Starting points
		dx1 = (int)(x2 - x1); if (dx1<0) { dx1 = -dx1; signx1 = -1; }
		else signx1 = 1;
		dy1 = (int)(y2 - y1);

		dx2 = (int)(x3 - x1); if (dx2<0) { dx2 = -dx2; signx2 = -1; }
		else signx2 = 1;
		dy2 = (int)(y3 - y1);

		if (dy1 > dx1) {   // swap values
			int t=dx1;dx1=dy1;dy1=t;
			changed1 = true;
		}
		if (dy2 > dx2) {   // swap values
            int t=dy2;dy2=dx2;dx2=t;
			changed2 = true;
		}

		e2 = (int)(dx2 >> 1);
		// Flat top, just process the second half
        boolean goNext=false;
		if (y1 == y2) goNext=true;
        if (!goNext) {
            e1 = (int)(dx1 >> 1);
            boolean lloopDone=false;
            for (int i = 0; i < dx1&&!lloopDone;) {
                t1xp = 0; t2xp = 0;
                if (t1x<t2x) { minx = t1x; maxx = t2x; }
                else { minx = t2x; maxx = t1x; }
                // process first line until y value is about to change
                boolean loopDone=false;
                while (i<dx1&&!loopDone) {
                    i++;
                    e1 += dy1;
                    while (e1 >= dx1) {
                        e1 -= dx1;
                        if (changed1) t1xp = signx1;//t1x += signx1;
                        else loopDone=true;
                    }
                    if (changed1) loopDone=true;
                    else t1x += signx1;
                }
                // Move line
                // process second line until y value is about to change

                loopDone=false;
                while (!loopDone) {
                    e2 += dy2;
                    while (e2 >= dx2) {
                        e2 -= dx2;
                        if (changed2) t2xp = signx2;//t2x += signx2;
                        else loopDone=true;
                    }
                    if (changed2)     loopDone=true;
                    else              t2x += signx2;
                }
                if (minx>t1x) minx = t1x; if (minx>t2x) minx = t2x;
                if (maxx<t1x) maxx = t1x; if (maxx<t2x) maxx = t2x;
                DrawHorizontalLine(minx, maxx, y,col);    // Draw line from min to max points found on the y
                                            // Now increase y
                if (!changed1) t1x += signx1;
                t1x += t1xp;
                if (!changed2) t2x += signx2;
                t2x += t2xp;
                y += 1;
                if (y == y2) lloopDone=true;

            }
        }
		// Second half
		dx1 = (int)(x3 - x2); if (dx1<0) { dx1 = -dx1; signx1 = -1; }
		else signx1 = 1;
		dy1 = (int)(y3 - y2);
		t1x = x2;

		if (dy1 > dx1) {   // swap values
            int t=dy1;dy1=dx1;dx1=t;
			changed1 = true;
		}
		else changed1 = false;

		e1 = (int)(dx1 >> 1);

        boolean lloopDone=false;
		for (int i = 0; i <= dx1&&!lloopDone; i++) {
			t1xp = 0; t2xp = 0;
			if (t1x<t2x) { minx = t1x; maxx = t2x; }
			else { minx = t2x; maxx = t1x; }
			// process first line until y value is about to change
            boolean loopDone=false;
			while (i<dx1&&!loopDone) {
				e1 += dy1;
				while (e1 >= dx1) {
					e1 -= dx1;
					if (changed1) { t1xp = signx1; loopDone=true;}//t1x += signx1;
					else loopDone=true;
				}
				if (changed1) loopDone=true;
				else   	   	  t1x += signx1;
				if (i<dx1) i++;
			}
            // process second line until y value is about to change
            loopDone=false;
            while (t2x != x3&&!loopDone) {
                e2 += dy2;
                while (e2 >= dx2) {
                    e2 -= dx2;
                    if (changed2) t2xp = signx2;
                    else loopDone=true;
                }
                if (changed2)     loopDone=true;
                else              t2x += signx2;
            }
			if (minx>t1x) minx = t1x; if (minx>t2x) minx = t2x;
			if (maxx<t1x) maxx = t1x; if (maxx<t2x) maxx = t2x;
			DrawHorizontalLine(minx, maxx, y,col);   										
			if (!changed1) t1x += signx1;
			t1x += t1xp;
			if (!changed2) t2x += signx2;
			t2x += t2xp;
			y += 1;
			if (y>y3) lloopDone=true;
		}
	}

    void DrawTriangle(int x1,int y1,int x2,int y2,int x3,int y3,int col) {
        DrawLine(x1,y1,x2,y2,col);
        DrawLine(x2,y2,x3,y3,col);
        DrawLine(x3,y3,x1,y1,col);
    }
    
    void DrawLine(int x1,int y1,int x2,int y2,int col) {
        int x=0,y=0,dx=0,dy=0,dx1=0,dy1=0,px=0,py=0,xe=0,ye=0;
        dx=x2-x1;dy=y2-y1;
        dx1=Math.abs(dx);dy1=Math.abs(dy);
        px=2*dy1-dx1;py=2*dx1-dy1;
        if (dy1<=dx1) {
            if (dx>=0) {
                x=x1;y=y1;xe=x2;
            } else {
                x=x2;y=y2;xe=x1;
            }
            Draw(x,y,col);
            while (x<xe) {
                x=x+1;
                if (px<0) {
                    px=px+2*dy1;
                } else {
                    if ((dx<0&&dy<0)||(dx>0&&dy>0)) {
                        y=y+1;
                    } else {
                        y=y-1;
                    }
                    px=px+2*(dy1-dx1);
                }
                Draw(x,y,col);
            }
        } else {
            if (dy>=0) {
                x=x1;y=y1;ye=y2;
            } else {
                x=x2;y=y2;ye=y1;
            }
            Draw(x,y,col);
            while (y<ye) {
                y=y+1;
                if (py<=0) {
                    py=py+2*dx1;
                } else {
                    if ((dx<0&&dy<0)||(dx>0&&dy>0)) {
                        x=x+1;
                    } else {
                        x=x-1;
                    }
                    py=py+2*(dx1-dy1);
                }
                Draw(x,y,col);
            }
        }
    }
    
    void Draw(int x,int y,int col) {
        if (x>=0&&y>=0&&x<width&&y<height) {
            int alpha = col>>>24;
            if (alpha>0&&alpha<255) {
                float ratio = alpha/255f;
                int prev_col = pixels[x+y*width];
                int prev_r=prev_col&0xFF;
                int prev_g=(prev_col&0xFF00)>>>8;
                int prev_b=(prev_col&0xFF0000)>>>16;
                int r=col&0xFF;
                int g=(col&0xFF00)>>>8;
                int b=(col&0xFF0000)>>>16;
                int new_r=(int)(ratio*r+(1-ratio)*prev_r);
                int new_g=(int)(ratio*g+(1-ratio)*prev_g);
                int new_b=(int)(ratio*b+(1-ratio)*prev_b);
                pixels[x+y*width]=new_r+(new_g<<8)+(new_b<<16)+(col&0xFF000000);
            } else {
                pixels[x+y*width]=col;
            }
        }
    }
}
