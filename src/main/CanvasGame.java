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
import octtree.TesteObj;
import octtree.Tree;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

import frustum.FrustumV2;

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

	Vector3f v  = new Vector3f(0,0,5);
	Vector3f v1  = new Vector3f(0,5,0);	
	Vector3f v2  = new Vector3f(5,0,0);
	Vector3f posNave  = new Vector3f(0,2,2);
	
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
	
	float X,Y,Z;

    public static float rotAngleX, rotAngleY, rotAngleZ;
	public static final float ANGLESTEP = (float)(Math.PI/90);
	
	
	ObjModel OBJTest;
	
	
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
	       
	       OBJTest = new ObjModel();
	       OBJTest.loadObj("/res/nave.obj");
	       
	       camera.setCamInternals((float)FOVY, (float)(WIDTH * 1.0/ HEIGHT), (float)NEAR, (float)FAR);
	        
	        for (int i = 0; i < 400000; i++) {
	        	Obj8T obj = new TesteObj(rnd.nextFloat()*1000-500, rnd.nextFloat()*1000-500, rnd.nextFloat()*1000-500, 1, 1, 1,rnd.nextFloat()*6-3,rnd.nextFloat()*6-3,rnd.nextFloat()*6-3);
		        treemap.addElement(obj);
		        //objetos.add(obj);
			}
			
	}

    @Override
    public void SimulaSe(long diftime) {
        // TODO Auto-generated method stub  
    	if(UP){
    		rotAngleX = (float)(90.0f*diftime/1000.0f);
			Matrix4x4 m = new Matrix4x4();
			m.setRotate(rotAngleX, v2.x, v2.y, v2.z);
			m.transform(v);
			m.transform(v1);
			m.transform(v2);
			m.transform(posNave);
			v.normalize();
			v1.normalize();
			v2.normalize();
    	}
    	if(DOWN){
    		rotAngleX = -(float)(90.0f*diftime/1000.0f);
			Matrix4x4 m = new Matrix4x4();
			m.setRotate(rotAngleX, v2.x, v2.y, v2.z);
			m.transform(v);
			m.transform(v1);
			m.transform(v2);
			m.transform(posNave);
			v.normalize();
			v1.normalize();
			v2.normalize();    	}
    	
    	
    	
    	if(RIGHT){
    		rotAngleY =(float)(90.0f*diftime/1000.0f);
			Matrix4x4 m = new Matrix4x4();
			m.setRotate(rotAngleY, v1.x, v1.y, v1.z);
			m.transform(v);
			m.transform(v1);
			m.transform(v2);
			m.transform(posNave);
			v.normalize();
			v1.normalize();
			v2.normalize();    	
			}
    	if(LEFT){
    		rotAngleY = -(float)(90.0f*diftime/1000.0f);
			Matrix4x4 m = new Matrix4x4();
			m.setRotate(rotAngleY, v1.x, v1.y, v1.z);
			m.transform(v);
			m.transform(v1);
			m.transform(v2);
			m.transform(posNave);
			v.normalize();
			v1.normalize();
			v2.normalize();    	
		}    	
    	if(Q){
    		rotAngleZ =(float)(90.0f*diftime/1000.0f);
			Matrix4x4 m = new Matrix4x4();
			m.setRotate(rotAngleZ, v.x, v.y, v.z);
			m.transform(v);
			m.transform(v1);
			m.transform(v2);
			m.transform(posNave);
			v.normalize();
			v1.normalize();
			v2.normalize();
    	}
    	if(E){
    		rotAngleZ = -(float)(90.0f*diftime/1000.0f);
			Matrix4x4 m = new Matrix4x4();
			m.setRotate(rotAngleZ, v.x, v.y, v.z);
			m.transform(v);
			m.transform(v1);
			m.transform(v2);
			m.transform(posNave);
			v.normalize();
			v1.normalize();
			v2.normalize();
    	}
    	if(W){
    		X+=v.x*4*diftime/1000.0f;
			Y+=v.y*4*diftime/1000.0f;
			Z+=v.z*4*diftime/1000.0f;
    	}
    	if(S){
    		X-=v.x*4*diftime/1000.0f;
			Y-=v.y*4*diftime/1000.0f;
			Z-=v.z*4*diftime/1000.0f;
    	}
    	if(D){

			Matrix4x4 m = new Matrix4x4();
			m.setRotate(90, v1.x, v1.y, v1.z);
			m.transform(v);
			m.transform(v1);
			m.transform(v2);
			m.transform(posNave);
			v.normalize();
			v1.normalize();
			v2.normalize(); 

    		X+=v.x*4*diftime/1000.0f;
			Y+=v.y*4*diftime/1000.0f;
			Z+=v.z*4*diftime/1000.0f;

			m = new Matrix4x4();
			
			m.setRotate(-90, v1.x, v1.y, v1.z);
			m.transform(v);
			m.transform(v1);
			m.transform(v2);
			m.transform(posNave);
			v.normalize();
			v1.normalize();
			v2.normalize();
    	}
    	if(A){

			Matrix4x4 m = new Matrix4x4();
			m.setRotate(-90, v1.x, v1.y, v1.z);
			m.transform(v);
			m.transform(v1);
			m.transform(v2);
			m.transform(posNave);
			v.normalize();
			v1.normalize();
			v2.normalize(); 

    		X+=v.x*4*diftime/1000.0f;
			Y+=v.y*4*diftime/1000.0f;
			Z+=v.z*4*diftime/1000.0f;

			m = new Matrix4x4();
			
			m.setRotate(90, v1.x, v1.y, v1.z);
			m.transform(v);
			m.transform(v1);
			m.transform(v2);
			m.transform(posNave);
			v.normalize();
			v1.normalize();
			v2.normalize();
    		
    	}/*
    	if(!P){
    		if(quadtree){
    			treemap.simulate(diftime);
    		}else{
    			for (int i = 0; i < objetos.size(); i++) {
    				objetos.get(i).simulate(diftime);
    			}
    		}
    	}*/
    }
    
    float soma = 0;
	@Override
	public void DesenhaSe(GLAutoDrawable drawable) {
	       // Compute total rotations around X, Y, and Z axes.
	       gl = drawable.getGL ();

	       // Clear the drawing surface to the background color, which defaults to
	       // black.
	       
	       //gl.glClearColor(1, 1, 1, 1);

	       gl.glClear (GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

	       // Reset the modelview matrix.
	       
	       gl.glLoadIdentity ();
	       
	       soma+=0.1;
	       
	       float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};    //yellow diffuse : color where light hit directly the object's surface
	       float[] lightAmbient = {1.0f, 1.0f, 1.0f, 1.0f};    //red ambient : color applied everywhere
	       float[] lightPosition = {0f, 10.0f, -8.0f, 1.0f};
	       //Ambient light component
	       
	       
	       gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, lightAmbient,0);
	       //Diffuse light component
	       gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, lightDiffuse,0);
	       //Light position
	       //System.out.println(" "+lightPosition[2]);
	       gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPosition,0);
	       
	       gl.glEnable(GL.GL_LIGHT0);
	       
	       
	       

	       /*float[] lightDiffuse2 = {1.0f, 1.0f, 1.0f, 1.0f};    //yellow diffuse : color where light hit directly the object's surface
	       float[] lightAmbient2 = {1.0f, 1.0f, 1.0f, 1.0f};    //red ambient : color applied everywhere
	       float[] lightPosition2= {0f, 10f, -20.0f, 0.0f};
	       
	       gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, lightAmbient2,0);
	       //Diffuse light component
	       gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, lightDiffuse2,0);
	       //Light position
	       gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, lightPosition2,0);
	       
	       gl.glEnable(GL.GL_LIGHT1);	    */   
	       // The camera is currently positioned at the (0, 0, 0) origin, its lens
	       // is pointing along the negative Z axis (0, 0, -1) into the screen, and
	       // its orientation up-vector is (0, 1, 0). The following call positions
	       // the camera at (0, 0, CAMERA_Z), points the lens towards the origin,
	       // and keeps the same up-vector.



	       
	       
		   
		   //glu.gluLookAt (X, Y, 0, anguloCamera.x+X, anguloCamera.y+Y, anguloCamera.z,anguloTopoCamera.x, anguloTopoCamera.y, anguloTopoCamera.z);

	       glu.gluLookAt (X, Y, Z, X+v.x, Y+v.y, Z+v.z, v1.x, v1.y, v1.z);
	       
	       gl.glPushMatrix();
	       {
	    	   /*
	    	   gl.glRotatef(x?, 1, 0, 0);
	    	   gl.glRotatef(y?, 0, 1, 0);
	    	   gl.glRotatef(z?, 0, 0, 1);
	    	   */
	    	   gl.glTranslatef(X+v.x*5, Y+v.y*5, Z+v.z*5);
	    	   OBJTest.desenhase(gl);
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
	    	   staticVectorDirection.set3P(X+v.x, Y+v.y, Z+v.z);
	    	   staticVectorHelper.set3P(v1.x, v1.y, v1.z);
	       }
	       camera.setCamDef(staticVectorPosition, staticVectorDirection, staticVectorHelper);
	       
	       //if(quadtree){
	    	   treemap.draw(gl, camera);
	       /*}else{
   				for (int i = 0; i < objetos.size(); i++) {
   					objetos.get(i).draw(gl, camera);
   				}
	       }*/
	       

	       
	       /*
           gl.glColor3f(1.0f,1.0f,1.0f); 
	       gl.glEnable(GL.GL_COLOR_MATERIAL);
	       */

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
