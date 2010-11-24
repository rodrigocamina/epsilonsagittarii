package main;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import matematcbase.Matrix4x4;
import matematcbase.Vector3f;
import obj.ObjModel;
import octtree.Obj8T;
import octtree.Tree;
import util.ConfigTeclado;
import EfeitosParticulas.Particula;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;
import effects.CameraAnimator;
import effects.CameraFrame;
import frustum.FrustumV2;
import gameobjects.EnemyGroup;
import gameobjects.EnemyShip;
import gameobjects.GameObj;
import gameobjects.PlayerShip;
import gameobjects.Station;
import gameobjects.Weapon;
import java.io.*;

/*
 * Created on 21/03/2010
 * Atualizado Para Verção 1.0
 * Desenvolvido Por Dennis Kerr Coelho
 * PalmSoft Tecnologia
 */

public class CanvasGame extends PS_3DCanvas{


    private final static double FOVY = 45.0; // field-of-view angle around Y

    private final static double NEAR = 0.1; // Z values < NEAR are clipped
    private final static double FAR = 300.0;  // Z values > FAR are clipped

    private final static int WIDTH = 800;
    private final static int HEIGHT = 600;
    
    private GLU glu = new GLU ();
    

    // Total and increment rotation variables.

    //public static float rotAngleX, rotAngleY, rotAngleZ;

    public static Texture [] textures;
    //Ordem de textura para ser usada.
    private final int numeroDetexturas = 9;
    public static final int TEX_NAVE_PLAYER = 0;
    public static final int TEX_TIRO_BLUE = 1;   
    public static final int TEX_TIRO_GREEN = 2;
    public static final int TEX_TIRO_RED = 3;
    public static final int TEX_TIRO_ORANGE = 4;
    public static final int TEX_TIRO_WHITE = 5;
    public static final int TEX_FOGO = 6;
    public static final int TEX_FOGO_AZUL = 7;
    
    Random rnd = new Random();
	FrustumV2 camera = new FrustumV2(); 
	GL gl = null;

	long timer = 50;
	
	Vector3f staticVectorPosition = new Vector3f(0, 0, 0.35f);
	Vector3f staticVectorDirection = new Vector3f(0, 0, -1);
	Vector3f staticVectorHelper = new Vector3f(0, 1, 0);
	
	
	public static boolean UP,DOWN,LEFT,RIGHT,A,D,W,S,SPACE,SHIFT,ESC;
	private boolean staticFrustum = false;
	public static boolean frustumCulling = true;
	//public static boolean quadtree = false;
	boolean MatrixOpengl = true;
	
	private Tree treemap = new Tree(6, -500, -500, -500, 500, 500,500);
	
	public static float X,Y,Z;
    public static float rotAngleX, rotAngleY, rotAngleZ;
	public static final float ANGLESTEP = (float)(Math.PI/90);
	

	//public static EnemyShip enemySheep;

	public static List<EnemyShip> enemySheeps = new ArrayList<EnemyShip>();

	public static PlayerShip nave;
	private Station estacao;
	public static List<Weapon> shots = new ArrayList<Weapon>();
	
	
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
	       textures = new Texture [numeroDetexturas];
	       try {
	    	   textures [TEX_NAVE_PLAYER] = load("texturaNave.png",gl);//("NovaUVMap.png",gl);
	    	   System.out.println("erro carregamento de textura nave");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("erro carregamento de textura nave");
			}
			
			 try {
				 textures [TEX_TIRO_BLUE] = load("StarBlue.png",gl);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("erro carregamento de textura tiro azul");
				}
				
			try {
				textures [TEX_TIRO_GREEN] = load("StarGreen.png",gl);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("erro carregamento de textura tiro verde");
			}
			try {
				textures [TEX_TIRO_ORANGE] = load ("StarOrange.png",gl);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("erro carregamento de textura tiro laranja");
			}
			try {
				 textures [TEX_TIRO_RED] = load("StarRed.png",gl);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("erro carregamento de textura tiro red");
			}
			try {
				textures [TEX_TIRO_WHITE] = load("Star.png",gl);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("erro carregamento de textura tiro star");
			}
			try {
				 textures [TEX_FOGO] = load("fogo1.png",gl);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("erro carregamento de textura fogo");
			}
			
	       X = Y = 0;

	       ObjModel modelP = new ObjModel();
	       modelP.loadObj("/res/NaveManeira.obj");
	       ObjModel model = new ObjModel();
	       model.loadObj("/res/NaveR.obj");
	       ObjModel modelStation = new ObjModel();
	       modelStation.loadObj("/res/Station.obj");
	       nave = new PlayerShip(0, -0.15f, 1f, 10, 10, 10, 5, 5, 5, modelP);
	       nave.setIndiceTextura(TEX_NAVE_PLAYER);
	       estacao = new Station(0, 0, 0, 10, 10, 10, 0, 0, 0, modelStation);
	       EnemyGroup engroup0 = new EnemyGroup();
	       EnemyGroup engroup1 = new EnemyGroup();
	       EnemyGroup engroup2 = new EnemyGroup();
	       enemySheeps.add(new EnemyShip(20, 0, 0, 0, 0, 0, 5, 5, 5, model, estacao,engroup0));
	       enemySheeps.add(new EnemyShip(-20, 0, 0, 0, 0, 0, 5, 5, 5, model, estacao,engroup0));
	       enemySheeps.add(new EnemyShip(10, 10, 0, 0, 0, 0, 5, 5, 5, model, estacao,engroup0));
	       enemySheeps.add(new EnemyShip(20, 10, 0, 0, 0, 0, 5, 5, 5, model, estacao,engroup0));
//	       enemySheeps.add(new EnemyShip(-20, 0, 20, 0, 0, 0, 5, 5, 5, model, estacao,engroup0));
//	  	   enemySheeps.add(new EnemyShip(0, 0, 0, 0, 0, 0, 5, 5, 5, model, estacao,engroup1));
//	       enemySheeps.add(new EnemyShip(10, 0, 0, 0, 0, 0, 5, 5, 5, model, estacao,engroup1));
//	       enemySheeps.add(new EnemyShip(-10, 0, 0, 0, 0, 0, 5, 5, 5, model, estacao,engroup1));
//	       enemySheeps.add(new EnemyShip(10, 10, 10, 0, 0, 0, 5, 5, 5, model, estacao,engroup2));
//	       enemySheeps.add(new EnemyShip(-10, 10, 0, 0, 0, 0, 5, 5, 5, model, estacao,engroup2));
//	       enemySheeps.add(new EnemyShip(10, -10, 0, 0, 0, 0, 5, 5, 5, model, estacao,engroup2));
//	       enemySheeps.add(new EnemyShip(-10, -10, 0, 0, 0, 0, 5, 5, 5, model, estacao,engroup2));
//	       enemySheeps.add(new EnemyShip(20, 20, 0, 0, 0, 0, 5, 5, 5, model, estacao,engroup2));
//	       enemySheeps.add(new EnemyShip(-20, -20, 0, 0, 0, 0, 5, 5, 5, model, estacao,engroup2));

	       //enemySheep.setIndiceTextura(TEX_NAVE_PLAYER);
	       CameraAnimator.startFrame(new Vector3f(nave.getFrontV()), new Vector3f(nave.getUpV()), new Vector3f(nave.getRightV()));
	       
	       
	       camera.setCamInternals((float)FOVY, (float)(WIDTH * 1.0/ HEIGHT), (float)NEAR, (float)FAR);
	        
	       //Adiciona elementos de cenario, estaticos
	        for (int i = 0; i < 40; i++) {
	        	Obj8T obj = new GameObj(rnd.nextFloat()*1000-500, rnd.nextFloat()*1000-500, rnd.nextFloat()*1000-500, 1, 1, 1,rnd.nextFloat()*6-3,rnd.nextFloat()*6-3,rnd.nextFloat()*6-3,null);
		        treemap.addElement(obj);
		        //objetos.add(obj);
			}
	        
			
	}

    @Override
    public void SimulaSe(long diffTime) {
    	simulacaoDeTeclas(diffTime);
    	
    	
    	
    	timer -= diffTime;
    	if(timer<0){
    		timer+=45;
    		CameraAnimator.addFrame(new Vector3f(nave.getFrontV()), new Vector3f(nave.getUpV()), new Vector3f(nave.getRightV()));
    	}
    	nave.simulate(diffTime);
    	estacao.simulate(diffTime);
    	for (int i = 0; i < enemySheeps.size(); i++) {
    		enemySheeps.get(i).simulate(diffTime);
    		
    		if(enemySheeps.get(i).life<0){
    			enemySheeps.remove(enemySheeps.get(i));
    		}
		}
    	int sz = shots.size();
    	for (int i = 0; i < sz; i++) {
    		try{
			Weapon w = shots.get(i);

			
    		w.simulate(diffTime);
			if(w.isDead()){


				CanvasGame.shots.remove(i);
				i--;
			}
    		}catch (Exception e) {
				// TODO: handle exception
			}
		}
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
	       gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPosition,0);
	       gl.glEnable(GL.GL_LIGHT0);
	       CameraFrame frame = CameraAnimator.getActualFrame();
	       glu.gluLookAt (X, Y, Z, X+frame.frontV.x, Y+frame.frontV.y, Z+frame.frontV.z, frame.upV.x, frame.upV.y, frame.upV.z);
	       gl.glPushMatrix();
	       {
//	    	   Matrix4x4 m = new Matrix4x4().setRotate(rotAngleX, 1,0,0);
//	    	   Matrix4x4 m2 = new Matrix4x4().setRotate(rotAngleX, 0,1,0);
//	    	   Matrix4x4 m3 = new Matrix4x4().setRotate(rotAngleX, 0,0,1);
//	    	   gl.glMultMatrixf(m.combine(m2).combine(m3).toFloatArray(), 0);
	    	   nave.draw(gl, camera);
	    	   estacao.draw(gl, camera);
	    	   for (int i = 0; i < enemySheeps.size(); i++) {
	    	   		enemySheeps.get(i).draw(gl, camera);
	    	   }
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
	    	int sz = shots.size();
	    	for (int i = 0; i < sz; i++) {
				shots.get(i).draw(gl, camera);
			}

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
    		rotAngleX = (float)(60.0f*diffTime/1000.0f);
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
    		rotAngleX = -(float)(60.0f*diffTime/1000.0f);
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
    	
    	
    	
    	if(A){
    		rotAngleY =(float)(40.0f*diffTime/1000.0f);
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
    	
    	if(D){
    		rotAngleY = -(float)(40.0f*diffTime/1000.0f);
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
    		rotAngleZ =(float)(150.0f*diffTime/1000.0f);
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
    	
    	if(RIGHT){
    		rotAngleZ = -(float)(150.0f*diffTime/1000.0f);
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
    		//SPEED UP
    		/*
    		X+=frontV.x*4*diffTime/1000.0f;
			Y+=frontV.y*4*diffTime/1000.0f;
			Z+=frontV.z*4*diffTime/1000.0f;*/
    		nave.addSpeed(diffTime/100.0f);
    	}
    	if(S){
    		//SPEED DOWN
    		/*
    		X-=frontV.x*4*diffTime/1000.0f;
			Y-=frontV.y*4*diffTime/1000.0f;
			Z-=frontV.z*4*diffTime/1000.0f;*/
    		nave.addSpeed(-diffTime/100.0f);
    	}
    	/*
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
    		
    	}*/
    	
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
    	if(key == ConfigTeclado.teclas[1]){
    		UP = true;
    	}
    	if(key == ConfigTeclado.teclas[0]){
    		DOWN = true;
    	}
    	if(key == ConfigTeclado.teclas[3]){
    		LEFT = true;
    	}
    	if(key == ConfigTeclado.teclas[2]){
    		RIGHT = true;
    	}
    	if(key == ConfigTeclado.teclas[7]){
    		A = true;
    	}
    	if(key == ConfigTeclado.teclas[6]){
    		D = true;
    	}
    	if(key == ConfigTeclado.teclas[4]){
    		W = true;
    	} 
    	if(key == ConfigTeclado.teclas[5]){
    		S = true;
    	}
    	if(key == ConfigTeclado.teclas[8]){
    		nave.setShooting(true);
    	} 
    	if(key == ConfigTeclado.teclas[9]){
    		SHIFT = true;
    	} 
    	if(key == ConfigTeclado.teclas[10]){
    		ESC = !ESC;
    	} 
    	if(key == ConfigTeclado.teclas[11]){
    		ESC = !ESC;
    	} 
    	
    	/*
    	if(key == KeyEvent.VK_O){
    		staticFrustum = !staticFrustum;
    	}
    	if(key == KeyEvent.VK_I){
    		frustumCulling = !frustumCulling;
    	}*/
    	
    	if(key == KeyEvent.VK_1){
    		MatrixOpengl=!MatrixOpengl;
    	}
    }
    
    @Override
    public void TratadorDeTecladoReleased(KeyEvent e) {
    	int key = e.getKeyCode();
    	if(key == ConfigTeclado.teclas[1]){
    		UP = false;
    	}
    	if(key == ConfigTeclado.teclas[0]){
    		DOWN = false;
    	}
    	if(key == ConfigTeclado.teclas[3]){
    		LEFT = false;
    	}
    	if(key == ConfigTeclado.teclas[2]){
    		RIGHT = false;
    	}
    	if(key == ConfigTeclado.teclas[7]){
    		A = false;
    	}
    	if(key == ConfigTeclado.teclas[6]){
    		D = false;
    	}
    	if(key == ConfigTeclado.teclas[4]){
    		W = false;
    	}
    	if(key == ConfigTeclado.teclas[5]){
    		S = false;
    	}
    	if(key == ConfigTeclado.teclas[8]){
    		nave.setShooting(false);
    	} 
    	if(key == ConfigTeclado.teclas[9]){
    		SHIFT = false;
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
