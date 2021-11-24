package sig;

import com.aparapi.Kernel;

public class RenderKernel extends Kernel{

    int[] pixels;
    float[] tris;
    float[] transformedTris;
    float[] depthBuffer;
    int[] tex;
    int width,tex_width;
    int height,tex_height;

    RenderKernel(int[] pixels,float[] tris,int[] tex,int width,int height,int tex_width,int tex_height,float[] depthBuffer) {
        this.pixels=pixels;
        this.tris=tris;
        this.width=width;
        this.height=height;
        this.tex=tex;
        this.tex_width=tex_width;
        this.tex_height=tex_height;
        this.depthBuffer=depthBuffer;
        this.transformedTris = new float[this.tris.length];
    }

    @Override
    public void run() {
        for (int i=0;i<tris.length;i++) {
            transformedTris[i]=tris[i];
        }
        int id = getGlobalId();
        float x1=transformedTris[id*22+0];
        float y1=transformedTris[id*22+1];
        float z1=transformedTris[id*22+2];
        float w1=transformedTris[id*22+3];
        float x2=transformedTris[id*22+4];
        float y2=transformedTris[id*22+5];
        float z2=transformedTris[id*22+6];
        float w2=transformedTris[id*22+7];
        float x3=transformedTris[id*22+8];
        float y3=transformedTris[id*22+9];
        float z3=transformedTris[id*22+10];
        float w3=transformedTris[id*22+11];
        float Tu=transformedTris[id*22+12];
        float Tv=transformedTris[id*22+13];
        float Tw=transformedTris[id*22+14];
        float Uu=transformedTris[id*22+15];
        float Uv=transformedTris[id*22+16];
        float Uw=transformedTris[id*22+17];
        float Vu=transformedTris[id*22+18];
        float Vv=transformedTris[id*22+19];
        float Vw=transformedTris[id*22+20];
        int tex=(int)transformedTris[id*22+21];
        TexturedTriangle(
            (int)x1,(int)y1,Tu,Tv,Tw,
            (int)x2,(int)y2,Uu,Uv,Uw,
            (int)x3,(int)y3,Vu,Vv,Vw,
            tex,1f);
    }

    int getTextureColor(int tex_id,float u,float v,float colorMult) {
        int x = (int)(u*16+(16*(tex_id%16)));
        int y = (int)(v*16+(16*(tex_id/16)));
        if (x<0||x>=256||y<0||y>=256) {
            return 0;
        } else {
            int col = tex[x+y*tex_width];
            if (colorMult==1f) {
                return col;
            } else
            if (colorMult==0f) {
                return 0;
            } else {
                return (int)(((col&0xFF)*colorMult)+((int)(((col&0xFF00)>>>8)*colorMult)<<8)+((int)(((col&0xFF0000)>>>16)*colorMult)<<16) + ((int)(((col&0xFF000000)>>>24))<<24));
            }
        }
    }

    void TexturedTriangle(int x1, int y1, float u1,float v1,float w1,
            int x2, int y2, float u2,float v2,float w2,
            int x3, int y3, float u3,float v3,float w3,
            int tex_id, float colorMult
    ) {
		if (y2<y1) {int t=y1;y1=y2;y2=t;t=x1;x1=x2;x2=t;float u=u1;u1=u2;u2=u;float v=v1;v1=v2;v2=v;float w=w1;w1=w2;w2=w;}
		if (y3<y1) {int t=y1;y1=y3;y3=t;t=x1;x1=x3;x3=t;float u=u1;u1=u3;u3=u;float v=v1;v1=v3;v3=v;float w=w1;w1=w3;w3=w;}
		if (y3<y2) {int t=y2;y2=y3;y3=t;t=x2;x2=x3;x3=t;float u=u2;u2=u3;u3=u;float v=v2;v2=v3;v3=v;float w=w2;w2=w3;w3=w;}

        int dy1=y2-y1;
        int dx1=x2-x1;
        float dv1=v2-v1;
        float du1=u2-u1;
        float dw1=w2-w1;
        int dy2=y3-y1;
        int dx2=x3-x1;
        float dv2=v3-v1;
        float du2=u3-u1;
        float dw2=w3-w1;
        float tex_u=0f,tex_v=0f,tex_w=0f;

        float dax_step=0,dbx_step=0,
            du1_step=0,dv1_step=0,dw1_step=0,
            du2_step=0,dv2_step=0,dw2_step=0;

        if (dy1!=0) {dax_step=dx1/((float)Math.abs(dy1));}
        if (dy2!=0) {dbx_step=dx2/((float)Math.abs(dy2));}

        if (dy1!=0) {du1_step=du1/((float)Math.abs(dy1));}
        if (dy1!=0) {dv1_step=dv1/((float)Math.abs(dy1));}
        if (dy1!=0) {dw1_step=dw1/((float)Math.abs(dy1));}
        if (dy2!=0) {du2_step=du2/((float)Math.abs(dy2));}
        if (dy2!=0) {dv2_step=dv2/((float)Math.abs(dy2));}
        if (dy2!=0) {dw2_step=dw2/((float)Math.abs(dy2));}

        if (dy1!=0) {
            for (int i=y1;i<=y2;i++) {
                int ax=(int)(x1+((float)(i-y1))*dax_step);
                int bx=(int)(x1+((float)(i-y1))*dbx_step);

                float tex_su=u1+((float)(i-y1))*du1_step;
                float tex_sv=v1+((float)(i-y1))*dv1_step;
                float tex_sw=w1+((float)(i-y1))*dw1_step;
                float tex_eu=u1+((float)(i-y1))*du2_step;
                float tex_ev=v1+((float)(i-y1))*dv2_step;
                float tex_ew=w1+((float)(i-y1))*dw2_step;

                if (ax>bx) {
                    int t=ax;ax=bx;bx=t;
                    float u=tex_su;tex_su=tex_eu;tex_eu=u;
                    float v=tex_sv;tex_sv=tex_ev;tex_ev=v;
                    float w=tex_sw;tex_sw=tex_ew;tex_ew=w;
                }

                tex_u=tex_su;
                tex_v=tex_sv;
                tex_w=tex_sw;

                float tstep = 1.0f/(float)(bx-ax);
                float t=0.0f;

                for (int j=ax;j<=bx;j++) {
                    tex_u=(1.0f-t)*tex_su+t*tex_eu;
                    tex_v=(1.0f-t)*tex_sv+t*tex_ev;
                    tex_w=(1.0f-t)*tex_sw+t*tex_ew;
                    int col = getTextureColor(tex_id,tex_u/tex_w,tex_v/tex_w,colorMult);
                    Draw(j,i,col);
                    t+=tstep;
                }
            }
        }

        dy1=y3-y2;
        dx1=x3-x2;
        dv1=v3-v2;
        du1=u3-u2;
        dw1=w3-w2;
        if (dy1!=0) {dax_step=dx1/((float)Math.abs(dy1));}
        if (dy2!=0) {dbx_step=dx2/((float)Math.abs(dy2));}
        du1_step=0f;
        dv1_step=0f;
        if (dy1!=0) {du1_step=du1/((float)Math.abs(dy1));}
        if (dy1!=0) {dv1_step=dv1/((float)Math.abs(dy1));}
        if (dy1!=0) {dw1_step=dw1/((float)Math.abs(dy1));}

        if (dy1!=0) {
            for (int i=y2;i<=y3;i++) {
                int ax=(int)(x2+((float)(i-y2))*dax_step);
                int bx=(int)(x1+((float)(i-y1))*dbx_step);

                float tex_su=u2+((float)(i-y2))*du1_step;
                float tex_sv=v2+((float)(i-y2))*dv1_step;
                float tex_sw=w2+((float)(i-y2))*dw1_step;
                float tex_eu=u1+((float)(i-y1))*du2_step;
                float tex_ev=v1+((float)(i-y1))*dv2_step;
                float tex_ew=w1+((float)(i-y1))*dw2_step;

                if (ax>bx) {
                    int t=ax;ax=bx;bx=t;
                    float u=tex_su;tex_su=tex_eu;tex_eu=u;
                    float v=tex_sv;tex_sv=tex_ev;tex_ev=v;
                    float w=tex_sw;tex_sw=tex_ew;tex_ew=w;
                }

                tex_u=tex_su;
                tex_v=tex_sv;
                tex_w=tex_sw;

                float tstep = 1.0f/(float)(bx-ax);
                float t=0.0f;

                for (int j=ax;j<=bx;j++) {
                    tex_u=(1.0f-t)*tex_su+t*tex_eu;
                    tex_v=(1.0f-t)*tex_sv+t*tex_ev;
                    tex_w=(1.0f-t)*tex_sw+t*tex_ew;
                    int col = getTextureColor(tex_id,tex_u/tex_w,tex_v/tex_w,colorMult);
                    Draw(j,i,col);
                    t+=tstep;
                }
            }
        }
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
