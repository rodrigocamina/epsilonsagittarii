package main;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import matematcbase.Matrix4x4;
import matematcbase.Vector3f;
import obj.ObjModel;
import octtree.Obj8T;
import octtree.Tree;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

import effects.CameraAnimator;
import effects.CameraFrame;
import frustum.FrustumV2;
import gameobjects.GameObj;
import gameobjects.Nave;

/*
 * Created on 21/03/2010
 * Atualizado Para Verção 1.0
 * Desenvolvido Por Dennis Kerr Coelho
 * PalmSoft Tecnologia
 */

public class CanvasGame extends PS_3DCanvas{


    private final static double FOVY = 45.0; // field-of-view angle around Y

    private final static double NEAR = 0.1; // Z values < NEAR are clipped
    private final static double FAR = 50.0;  // Z values > FAR are clipped

    private final static int WIDTH = 800;
    private final static int HEIGHT = 600;
    
    private GLU glu = new GLU ();
    

    // Total and increment rotation variables.

    //public static float rotAngleX, rotAngleY, rotAngleZ;

    public static Texture [] textures;
    Random rnd = new Random();
	FrustumV2 camera = new FrustumV2(); 
	GL gl = null;

	long timer = 50;
	
	Vector3f staticVectorPosition = new Vector3f(0, 0, 0.35f);
	Vector3f staticVectorDirection = new Vector3f(0, 0, -1);
	Vector3f staticVectorHelper = new Vector3f(0, 1, 0);
	
	boolean UP,DOWN,LEFT,RIGHT,A,D,W,S,Q,E,P;
	private boolean staticFrustum = false;
	public static boolean frustumCulling = false;
	//public static boolean quadtree = false;
	boolean MatrixOpengl = true;
	
	private Tree treemap = new Tree(6, -500, -500, -500, 500, 500,500);
	
	//public static ArrayList<Obj8T> objetos = new ArrayList<Obj8T>();
	
	
	public static float X,Y,Z;
    public static float rotAngleX, rotAngleY, rotAngleZ;
	public static final float ANGLESTEP = (float)(Math.PI/90);
	
	
	private Nave nave;
	
	
	public CanvasGame() {
	}
	
	@Override
	public void init(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
	       GL gl = drawable.getGL ();

	       gl.glEnable (GL.GL_DEPTH_TEST);
	       
	       gl.glEnable (GL.GL_LIGHTING);
	       
	       // Initialize rotation accumulator and increment variables.

	       
	       
	       //Random rand = new Random ();
	       // Load six 2D textures to decal the cube. If the image file on which the
	       // texture is based does not exist, load() returns null.

	       textures = new Texture [3];
	       textures [0] = load ("Logo  - UNIVALI.png",gl);
	       textures [1] = load ("textura-tanque.png",gl);
	       textures [2] = load ("TileMap.png",gl);

	       X = Y = 0;
	       
	       ObjModel model = new ObjModel();
	       model.loadObj("/res/nave.obj");
	       nave = new Nave(0, -1, 5, 1, 1, 1, 0, 0, 0, model);
	       CameraAnimator.startFrame(new Vector3f(nave.getFrontV()), new Vector3f(nave.getUpV()), new Vector3f(nave.getRightV()));
	       
	       
	       camera.setCamInternals((float)FOVY, (float)(WIDTH * 1.0/ HEIGHT), (float)NEAR, (float)FAR);
	        
	       //Adiciona elementos de cenario, estaticos
	        for (int i = 0; i < 400000; i++) {
	        	Obj8T obj = new GameObj(rnd.nextFloat()*1000-500, rnd.nextFloat()*1000-500, rnd.nextFloat()*1000-500, 1, 1, 1,rnd.nextFloat()*6-3,rnd.nextFloat()*6-3,rnd.nextFloat()*6-3,null);
		        treemap.addElement(obj);
		        //objetos.add(obj);
			}
			
	}

    @Override
    public void SimulaSe(long diffTime) {
        // TODO Auto-generated method stub 
    	simulacaoDeTeclas(diffTime);
    	
    	timer -= diffTime;
    	if(timer<0){
    		timer+=100;
    		CameraAnimator.addFrame(new Vector3f(nave.getFrontV()), new Vector3f(nave.getUpV()), new Vector3f(nave.getRightV()));
    	}
    	
    	/*if(!P){
    		treemap.simulate(diffTime);
    	}*/
    }
    
	@Override
	public void DesenhaSe(GLAutoDrawable drawable) {
	       // Compute total rotations around X, Y, and Z axes.
	       gl = drawable.getGL ();
	       // Clear the drawing surface to the background color, which defaults to
	       // black.
	       gl.glClear (GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
	       // Reset the modelview matrix.
	       gl.glLoadIdentity ();
	       //Ambient light component
	       float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};    //yellow diffuse : color where light hit directly the object's surface
	       float[] lightAmbient = {1.0f, 1.0f, 1.0f, 1.0f};    //red ambient : color applied everywhere
	       float[] lightPosition = {0f, 10.0f, -8.0f, 1.0f};
	       gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, lightAmbient,0);
	       //Diffuse light component
	       gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, lightDiffuse,0);
	       //Light position
	       //System.out.println(" "+lightPosition[2]);
	       gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPosition,0);
	       gl.glEnable(GL.GL_LIGHT0);
	       CameraFrame frame = CameraAnimator.getActualFrame();
	       //System.out.println("Frame "+frame);
	       glu.gluLookAt (X, Y, Z, X+frame.frontV.x, Y+frame.frontV.y, Z+frame.frontV.z, frame.upV.x, frame.upV.y, frame.upV.z);
	       gl.glPushMatrix();
	       {
//	    	   Matrix4x4 m = new Matrix4x4().setRotate(rotAngleX, 1,0,0);
//	    	   Matrix4x4 m2 = new Matrix4x4().setRotate(rotAngleX, 0,1,0);
//	    	   Matrix4x4 m3 = new Matrix4x4().setRotate(rotAngleX, 0,0,1);
//	    	   gl.glMultMatrixf(m.combine(m2).combine(m3).toFloatArray(), 0);
	    	   nave.draw(gl, camera);
	       }
	       gl.glPopMatrix();
	       
	       if(staticFrustum){
	    	   gl.glColor4f(1,1,1,0);
	    	   camera.drawLines(gl);
	    	   camera.drawNormals(gl);
	    	   camera.drawPlanes(gl);
	    	   camera.drawPoints(gl);
	       }else{
	    	   staticVectorPosition.set3P(X, Y, Z);
	    	   staticVectorDirection.set3P(X+frame.frontV.x, Y+frame.frontV.y, Z+frame.frontV.z);
	    	   staticVectorHelper.set3P(frame.upV.x, frame.upV.y, frame.upV.z);
	       }
	       camera.setCamDef(staticVectorPosition, staticVectorDirection, staticVectorHelper);
	       treemap.draw(gl, camera);

	}
	
	    
	    public void reshape (GLAutoDrawable drawable, int x, int y, int width,
                int height)
		{	
			GL gl = drawable.getGL ();
			
			// We don't need to invoke gl.glViewport(x, y, width, height) because
			// this is done automatically by JOGL prior to invoking reshape().
			
			// Because the modelview matrix is currently in effect, we need to
			// switch to the projection matrix before we can establish a perspective
			// view.
			
			gl.glMatrixMode (GL.GL_PROJECTION);
			
			// Reset the projection matrix.
			
			gl.glLoadIdentity ();
			
			// Establish a perspective view with an FOVY-degree viewing angle based
			// on the drawable's width and height. Furthermore, set the near and far
			// clipping planes to NEAR and FAR, respectively. All drawing must take
			// place between these planes. The view volume is assigned the same
			// aspect ratio as the viewport, to prevent distortion.
			
			float aspect = (float) width/(float) height;
			glu.gluPerspective (FOVY, aspect, NEAR, FAR);
			
			// From now on, we'll work with the modelview matrix.
			
			gl.glMatrixMode (GL.GL_MODELVIEW);
			
		}	    

	    
	    
	    
	private void simulacaoDeTeclas(long diffTime){
		Vector3f rightV = nave.getRightV();
		Vector3f frontV = nave.getFrontV();
		Vector3f upV = nave.getUpV();
		
		if(UP){
    		rotAngleX = (float)(30.0f*diffTime/1000.0f);
			Matrix4x4 m = new Matrix4x4();
			m.setRotate(rotAngleX, rightV.x, rightV.y, rightV.z);
			m.transform(frontV);
			m.transform(upV);
			m.transform(rightV);
			m.transform(nave.getPosition());
			frontV.normalize();
			upV.normalize();
			rightV.normalize();
    	}
    	if(DOWN){
    		rotAngleX = -(float)(30.0f*diffTime/1000.0f);
			Matrix4x4 m = new Matrix4x4();
			m.setRotate(rotAngleX, rightV.x, rightV.y, rightV.z);
			m.transform(frontV);
			m.transform(upV);
			m.transform(rightV);
			m.transform(nave.getPosition());
			frontV.normalize();
			upV.normalize();
			rightV.normalize();    	}
    	
    	
    	
    	if(RIGHT){
    		rotAngleY =(float)(30.0f*diffTime/1000.0f);
			Matrix4x4 m = new Matrix4x4();
			m.setRotate(rotAngleY, upV.x, upV.y, upV.z);
			m.transform(frontV);
			m.transform(upV);
			m.transform(rightV);
			m.transform(nave.getPosition());
			frontV.normalize();
			upV.normalize();
			rightV.normalize();    	
			}
    	if(LEFT){
    		rotAngleY = -(float)(30.0f*diffTime/1000.0f);
			Matrix4x4 m = new Matrix4x4();
			m.setRotate(rotAngleY, upV.x, upV.y, upV.z);
			m.transform(frontV);
			m.transform(upV);
			m.transform(rightV);
			m.transform(nave.getPosition());
			frontV.normalize();
			upV.normalize();
			rightV.normalize();    	
		}    	
    	if(Q){
    		rotAngleZ =(float)(30.0f*diffTime/1000.0f);
			Matrix4x4 m = new Matrix4x4();
			m.setRotate(rotAngleZ, frontV.x, frontV.y, frontV.z);
			m.transform(frontV);
			m.transform(upV);
			m.transform(rightV);
			m.transform(nave.getPosition());
			frontV.normalize();
			upV.normalize();
			rightV.normalize();
    	}
    	if(E){
    		rotAngleZ = -(float)(30.0f*diffTime/1000.0f);
			Matrix4x4 m = new Matrix4x4();
			m.setRotate(rotAngleZ, frontV.x, frontV.y, frontV.z);
			m.transform(frontV);
			m.transform(upV);
			m.transform(rightV);
			m.transform(nave.getPosition());
			frontV.normalize();
			upV.normalize();
			rightV.normalize();
    	}
    	if(W){
    		X+=frontV.x*4*diffTime/1000.0f;
			Y+=frontV.y*4*diffTime/1000.0f;
			Z+=frontV.z*4*diffTime/1000.0f;
    	}
    	if(S){
    		X-=frontV.x*4*diffTime/1000.0f;
			Y-=frontV.y*4*diffTime/1000.0f;
			Z-=frontV.z*4*diffTime/1000.0f;
    	}
    	if(D){

			Matrix4x4 m = new Matrix4x4();
			m.setRotate(90, upV.x, upV.y, upV.z);
			m.transform(frontV);
			m.transform(upV);
			m.transform(rightV);
			m.transform(nave.getPosition());
			frontV.normalize();
			upV.normalize();
			rightV.normalize(); 

    		X+=frontV.x*4*diffTime/1000.0f;
			Y+=frontV.y*4*diffTime/1000.0f;
			Z+=frontV.z*4*diffTime/1000.0f;

			m = new Matrix4x4();
			
			m.setRotate(-90, upV.x, upV.y, upV.z);
			m.transform(frontV);
			m.transform(upV);
			m.transform(rightV);
			m.transform(nave.getPosition());
			frontV.normalize();
			upV.normalize();
			rightV.normalize();
    	}
    	if(A){

			Matrix4x4 m = new Matrix4x4();
			m.setRotate(-90, upV.x, upV.y, upV.z);
			m.transform(frontV);
			m.transform(upV);
			m.transform(rightV);
			m.transform(nave.getPosition());
			frontV.normalize();
			upV.normalize();
			rightV.normalize(); 

    		X+=frontV.x*4*diffTime/1000.0f;
			Y+=frontV.y*4*diffTime/1000.0f;
			Z+=frontV.z*4*diffTime/1000.0f;

			m = new Matrix4x4();
			
			m.setRotate(90, upV.x, upV.y, upV.z);
			m.transform(frontV);
			m.transform(upV);
			m.transform(rightV);
			m.transform(nave.getPosition());
			frontV.normalize();
			upV.normalize();
			rightV.normalize();
    		
    	}
	}
	    
	    
    @Override
    public void TratadorDoMouseClic(int x, int y, int c) {
    	System.out.println("CLICK");
    }
    
    @Override
    public void TratadorDoMouseReleased(int x, int y, int c) {
    	System.out.println("RELEASED");
    }

    @Override
    public void TratadorDeTecladoPressed(KeyEvent e) {
    	int key = e.getKeyCode();
    	if(key == KeyEvent.VK_UP){
    		UP = true;
    	}
    	if(key == KeyEvent.VK_DOWN){
    		DOWN = true;
    	}
    	if(key == KeyEvent.VK_LEFT){
    		LEFT = true;
    	}
    	if(key == KeyEvent.VK_RIGHT){
    		RIGHT = true;
    	}
    	if(key == KeyEvent.VK_A){
    		A = true;
    	}
    	if(key == KeyEvent.VK_D){
    		D = true;
    	}
    	if(key == KeyEvent.VK_W){
    		W = true;
    	} 
    	if(key == KeyEvent.VK_S){
    		S = true;
    	}
    	if(key == KeyEvent.VK_Q){
    		Q = true;
    	} 
    	if(key == KeyEvent.VK_E){
    		E = true;
    	} 
    	if(key == KeyEvent.VK_P){
    		P = !P;
    	}
    	if(key == KeyEvent.VK_O){
    		staticFrustum = !staticFrustum;
    	}
    	if(key == KeyEvent.VK_I){
    		frustumCulling = !frustumCulling;
    	}
    	
    	if(key == KeyEvent.VK_1){
    		MatrixOpengl=!MatrixOpengl;
    	}
    }
    
    @Override
    public void TratadorDeTecladoReleased(KeyEvent e) {
    	int key = e.getKeyCode();
    	if(key == KeyEvent.VK_UP){
    		UP = false;
    	}
    	if(key == KeyEvent.VK_DOWN){
    		DOWN = false;
    	}
    	if(key == KeyEvent.VK_LEFT){
    		LEFT = false;
    	}
    	if(key == KeyEvent.VK_RIGHT){
    		RIGHT = false;
    	}
    	if(key == KeyEvent.VK_A){
    		A = false;
    	}
    	if(key == KeyEvent.VK_D){
    		D = false;
    	}
    	if(key == KeyEvent.VK_W){
    		W = false;
    	}
    	if(key == KeyEvent.VK_S){
    		S = false;
    	}
    	if(key == KeyEvent.VK_Q){
    		Q = false;
    	}
    	if(key == KeyEvent.VK_E){
    		E = false;
    	}
    	
    }
    
    @Override
    public void TratadorDoMouseMove(int x, int y, int c) {

    }
    
    Texture load (String filename,GL gl)
    {
       Texture texture = null;

       try
       {

     	  
     	  
           InputStream stream = getClass().getResourceAsStream("/res/"+filename);
           TextureData data = TextureIO.newTextureData(stream, false, "png");
           
           
           data.setMipmap(true);
           
           texture = TextureIO.newTexture (data);


           texture.setTexParameteri (GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);


           texture.setTexParameteri (GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR_MIPMAP_NEAREST);

       }
       catch (Exception e)
       {
    	   e.printStackTrace();
           System.out.println ("error loading texture from "+filename);
       }

       return texture;
    }    

    public static Texture loadTexture (String filename){
        Texture texture = null;

        try
        {

      	  
            InputStream stream = CanvasGame.class.getResourceAsStream("/res/"+filename);
            TextureData data = TextureIO.newTextureData(stream, false, "png");
            
            
            data.setMipmap(true);
            
            texture = TextureIO.newTexture (data);


            texture.setTexParameteri (GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);


            texture.setTexParameteri (GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR_MIPMAP_NEAREST);

        }
        catch (Exception e)
        {
            System.out.println ("error loading texture from "+filename);
        }

        return texture;
     }    

    
    

}
