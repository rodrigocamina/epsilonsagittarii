package main;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


import javax.media.opengl.*;
import javax.media.opengl.glu.*;

import com.sun.opengl.util.*;
import com.sun.opengl.util.texture.*;
import com.sun.org.apache.xerces.internal.util.URI;

/*
 * Created on 21/03/2010
 * Atualizado Para Verção 1.0
 * Desenvolvido Por Dennis Kerr Coelho
 * PalmSoft Tecnologia
 */

public class GameMain implements Runnable, GLEventListener{

    public static GameMain instance;

    public static int PWIDTH;
    public static int PHEIGHT;


    int FPS,SFPS;
    long SegundoAtual = 0;
    long NovoSegundo = 0;

    PS_3DCanvas TelaAtiva = null;

	public GLCanvas canvas;	
	
    public Thread animator; // for the animation
    public boolean running = false; // stops the animation
	

	public GameMain() {
		// TODO Auto-generated constructor stub
		
		instance = this;
		
	   	GLCapabilities glCapabilities = new GLCapabilities();
		
		glCapabilities.setDoubleBuffered(true);
		glCapabilities.setHardwareAccelerated(true);		
		
		canvas = new GLCanvas(glCapabilities);
		
		canvas.setSize(950,512);
			
	     
	     
	     canvas.addKeyListener(new KeyListener(){

	         public void keyPressed(KeyEvent e) {
	        	 instance.TratadorDeTecladoPressed(e);
	         }

	         public void keyReleased(KeyEvent e) {
	        	 instance.TratadorDeTecladoReleased(e);
	         }

	         public void keyTyped(KeyEvent e) {
	         }          
	      });
	     
	     canvas.addMouseListener( new MouseListener(){

	        public void mouseClicked(MouseEvent e) {
	        }

	        public void mouseEntered(MouseEvent e) {
	        }

	        public void mouseExited(MouseEvent e) {
	        }

	        public void mousePressed(MouseEvent e) {
	            instance.TratadorDoMouseClic(e.getX(), e.getY(),e.getButton());
	        }

	        public void mouseReleased(MouseEvent e) {
	        	instance.TratadorDoMouseReleased(e.getX(), e.getY(),e.getButton());
	        }
	          
	      });
	      
	     canvas.addMouseMotionListener(new MouseMotionListener(){

	        public void mouseDragged(MouseEvent e) {
	        }

	        public void mouseMoved(MouseEvent e) {
	        	instance.TratadorDoMouseMove(e.getX(), e.getY(),e.getButton());
	        }
	      });
	      
	     canvas.addMouseWheelListener(new MouseWheelListener(){
	        public void mouseWheelMoved(MouseWheelEvent e) {
	        	instance.TratadorMouseWheel(e);
	        }
	          
	      });     
	     
		  canvas.addGLEventListener(instance);
			
	}

	public void start(){
        if (animator == null || !running) {
            animator = new Thread(this);
            
            animator.setPriority(Thread.MAX_PRIORITY);
            
            animator.start();
            
        }
	}
	
    public void init (GLAutoDrawable drawable)
    {

    	
        CanvasGame spprop = new CanvasGame();
        spprop.init(drawable);
        TelaAtiva = spprop;    	
    }    
    
    long segundoatual = 0;
    long diftime = 0;
    int ultimosegundo = 0;
    int somaframes = 0;
    
	@Override
	public void run() {
		// TODO Auto-generated method stub
		running = true;
		
        while(running) {
            if(TelaAtiva!=null){
         	   TelaAtiva.SimulaSe(diftime);
         	   canvas.display();
         	   canvas.swapBuffers();
            }
         	
            if(segundoatual>0){
         	   diftime = System.currentTimeMillis() - segundoatual;
            }
            segundoatual = System.currentTimeMillis();
            
            if(((int)(segundoatual/1000))!=ultimosegundo){
          	  System.out.println(" _FPS "+somaframes);
          	  somaframes = 0;
            }
            somaframes++;
            ultimosegundo = (int)(segundoatual/1000);
            try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       }

	}  
    
    
    public void display (GLAutoDrawable drawable)
    {
    	   TelaAtiva.DesenhaSe(drawable);
    }




    public void reshape (GLAutoDrawable drawable, int x, int y, int width,
                         int height)
    {
    	if(TelaAtiva!=null&&(PWIDTH != width||PHEIGHT != height)){
    		
    		TelaAtiva.reshape(drawable, x, y, width, height);
    		PWIDTH = width;
    		PHEIGHT = height; 
    	}

    }

    public void displayChanged (GLAutoDrawable drawable, boolean modeChanged,
                                boolean deviceChanged)
    {
    }
    
    
    
    
    public void TratadorDoMousePressed(int x, int y,int c)
    {
    	if(TelaAtiva!=null){
            TelaAtiva.TratadorDoMousePressed(x, y, c);
        }
    }

    public void TratadorDoMouseReleased(int x, int y,int c)
    {
    	if(TelaAtiva!=null){
            TelaAtiva.TratadorDoMouseReleased(x, y, c);
        }
    }

    public void TratadorDoMouseMove(int x, int y,int c)
    {
    	if(TelaAtiva!=null){
            TelaAtiva.TratadorDoMouseMove(x, y, c);
        }
    }
    public void TratadorDoMouseClic(int x, int y,int c)
    {
    	if(TelaAtiva!=null){
            TelaAtiva.TratadorDoMouseClic(x, y, c);
        }
    }

    public void TratadorDeTecladoPressed(KeyEvent e){
    	if(TelaAtiva!=null){
            TelaAtiva.TratadorDeTecladoPressed(e);
        }
    }
    public void TratadorDeTecladoReleased(KeyEvent e){
    	if(TelaAtiva!=null){
            TelaAtiva.TratadorDeTecladoReleased(e);
        }
    }

    public void TratadorMouseWheel(MouseWheelEvent e){
    	if(TelaAtiva!=null){
            TelaAtiva.TratadorMouseWheel(e);
        }
    }
    
 }

