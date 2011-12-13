package landscape;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;

public class PS_Tilemap3D {
	
    TileMap mapa;
	BufferedImage heightMap;
    
    public static float alturas[][];    
	Vector3d normais[][];
    Vector3d normaisv[][];
    
    Random rand = new Random();
    
    public int MapX = 0;
    public int MapY = 0;
    
    int largura = 0;
    int altura = 0;
    
    public boolean wireframe = false;
    
    public float mapblocksize = 1.0f;
    
    int nblocdraw = 59;
    
    int ntilex = 32;
    int ntiley = 48;
	
	public PS_Tilemap3D(String nomemapa, String heightMapPath) {

	    System.out.println("Carregando mapa");
		mapa = new TileMap(null,200,200);
	    mapa.AbreMapa(nomemapa);
	    try {
	    	BufferedImage tmp = ImageIO.read(getClass().getResource(heightMapPath));
			this.heightMap = new BufferedImage(tmp.getWidth(), tmp.getHeight(), BufferedImage.TYPE_INT_ARGB);
	    	this.heightMap.getGraphics().drawImage(tmp, 0,0,null);
	    	tmp = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	    largura = mapa.Altura;
	    altura = mapa.Largura;
	    
	    System.out.println("largura "+largura+" altura "+altura);
	    
	    alturas = new float[largura+1][altura+1];
	    normais = new Vector3d[largura+1][altura+1];
	    normaisv = new Vector3d[largura+1][altura+1];
	    
	    
	    
	    
	    DataBuffer db =  this.heightMap.getRaster().getDataBuffer();
		
	    for (int i = 0; i < heightMap.getHeight(); i++) {
			for (int j = 0; j < heightMap.getWidth(); j++) {
				alturas[i][j] = (db.getElem(i*heightMap.getWidth()+j)&0x000000ff)*8/255.0f;
			}
		}
	    
	    
	   /*for(int i = 0; i < 1000; i++){
	  	 int mapx = rand.nextInt(largura);
	  	 int mapy = rand.nextInt(altura);
	  	 //if(mapa.colision[mapa.mapa[mapy][mapx]]==0){
	  		 alturas[mapy][mapx] = (rand.nextInt(250)/100.0f);    		 
	  	 //}
	   }*/
	   
	   for(int zi = 0; zi < 5; zi++){
		     for(int i = 1; i < altura-1;i++){
		          for(int j = 1; j < largura-1;j++){
		        	  
		        	  //if(mapa.colision[mapa.mapa[j-1][i-1]]==0){
		        	//	  alturas[j][i] = 0f;  
		        	 // }else{
		        		  //float max = alturas[j+1][i];
		        		  //float min = alturas[j+1][i];
		        		  
		        		  float a[] = new float[8];
		        		  a[0] = alturas[j][i+1];
		        		  a[1] = alturas[j][i-1];
		        		  a[2] = alturas[j+1][i];
		        		  a[3] = alturas[j-1][i];
		        		  a[4] = alturas[j-1][i+1];
		        		  a[5] = alturas[j+1][i+1];
		        		  a[6] = alturas[j-1][i-1];
		        		  a[7] = alturas[j+1][i-1];		        		   
		        		  
		        		  /*if(max<a[0])max=a[0];
		        		  if(max<a[1])max=a[1];
		        		  if(max<a[2])max=a[2];
		        		  if(max<a[3])max=a[3];
		        		  
		        		  if(min>a[0])min=a[0];
		        		  if(min>a[1])min=a[1];
		        		  if(min>a[2])min=a[2];
		        		  if(min>a[3])min=a[3];*/
		        		  
		        		  //if(max>alturas[j][i]){
//			        		float prop = (max-min)*0.07f;  
//		        		  
//			        			for(int w = 0; w < 4; w++){
//			        				if(a[w]<max){
//			        					a[w]+=prop;
//			        				}else{
//			        					a[w]-=prop;
//			        				}
//			        			}
//			        			
//				        		alturas[j][i+1] = a[0];
//				        		alturas[j][i-1] = a[1];
//				        		alturas[j+1][i] = a[2];
//				        		alturas[j-1][i] = a[3];
		        	  	  //}
		        		  
		        		  float media = (a[0]+a[1]+a[2]+a[3]+a[4]+a[5]+a[6]+a[7])/8.0f;
		        		  
		        		  alturas[j][i] +=  ((media - alturas[j][i])*(0.2f*Math.random()));
		        		  
		        	  } 
		        	  
		          }
		      //}
	   }
	    
	    
	    for(int i = 0; i < altura;i++){
	        for(int j = 0; j < largura;j++){
	      	  Vector3d vec1 = new Vector3d(); 
	      	  Vector3d vec2 = new Vector3d(); 
	      	  Vector3d vec3 = new Vector3d();
	      	  
	      	  vec1.x = 0;
	      	  vec1.y = 0;
	      	  vec1.z = alturas[j][i];
	      	  
	      	  vec2.x = 0.2f;
	      	  vec2.y = 0.0f;
	      	  vec2.z = alturas[j+1][i];        	  
	      	  
	      	  vec3.x = 0.00f;
	      	  vec3.y = 0.2f;
	      	  vec3.z = alturas[j][i+1];   
	      	  
	      	  normais[j][i] = CalculateVectorNormal(vec1,vec2,vec3);
	        }
	        //System.out.println(" "+i+" "+Runtime.getRuntime().totalMemory()+" " );
	    }   
	    
	    generate_vertex_normals();     
	}    
	
	
    Vector3d CalculateVectorNormal(Vector3d fVert1, Vector3d fVert2,Vector3d fVert3){
	 	double Qx, Qy, Qz, Px, Py, Pz;
	 	
	 	Vector3d vec = new Vector3d();
	 	
	 	Qx = fVert2.x-fVert1.x;
	 	Qy = fVert2.y-fVert1.y;
	 	Qz = fVert2.z-fVert1.z;
	 	Px = fVert3.x-fVert1.x;
	 	Py = fVert3.y-fVert1.y;
	 	Pz = fVert3.z-fVert1.z;
	 	
	 	vec.x = (float)(Py*Qz - Pz*Qy);
	 	vec.y = (float)(Pz*Qx - Px*Qz);
	 	vec.z = (float)(Px*Qy - Py*Qx);
	 	
	 	//System.out.println(" "+vec.x+" "+vec.y+" "+vec.z);
	 	normalize(vec);
	 	return vec;
    }

    public void normalize(Vector3d v){
	   double d = Math.sqrt(v.x*v.x + v.y*v.y+ v.z*v.z);
	   if (d == 0.0){
	      return;
	   }
	   v.x /= d; v.y /= d; v.z /= d;
	}   

    void generate_vertex_normals(){

	      for(int i = 0; i < altura;i++){
	          for(int j = 0; j < largura;j++){	     
	        	  
	        	  Vector3d vec = new Vector3d();
	        	  
	        	  if(i>0&&j>0){
		        	  vec.x = normais[j][i-1].x+normais[j-1][i].x+normais[j-1][i-1].x+normais[j][i].x;
		        	  vec.y = normais[j][i-1].y+normais[j-1][i].y+normais[j-1][i-1].y+normais[j][i].y;
		        	  vec.z = normais[j][i-1].z+normais[j-1][i].z+normais[j-1][i-1].z+normais[j][i].z;
	        	  }else{
	        		  vec.x = 1;
	        		  vec.y = 1;
	        		  vec.z = 1;
	        	  }
	        	  normalize(vec);
	        	  
	        	  normaisv[j][i] = vec; 
	        	  
	          }
	          //System.out.println(" bbbbb "+i+" "+Runtime.getRuntime().totalMemory()+" " );
	      } 

	}	
    
    class Vector3d{
    	float x = 0;
    	float y = 0;
    	float z = 0;
    }  
    
    
    public void desenhase(GL gl){

    	if(wireframe)gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_LINE);
    	
        for(int i = 0; i < nblocdraw; i++){
            for(int j = 0; j < nblocdraw; j++){
         	   
         	   int t =  mapa.mapa[MapY+j][MapX+i];
         	   
         	   //System.out.println(" "+t);
         	   
         	   int tlx = t%ntilex;
         	   int tly = t/ntiley;
         	   
         	  //System.out.println(" tlx "+tlx+" tly "+tly);
         	   
         	   float propx = 1.0f/(float)ntilex;
         	   float propy = 1.0f/(float)ntiley;
         		   
	           float tx1 = (propx*tlx);
	           float ty1 = (propy*tly);
	           float tx2 = (propx*tlx)+(propx);
	           float ty2 = (propy*tly)+(propy);
                
               tx1 = tx1+0.002f;
               ty1 = ty1+0.002f;
           
               tx2 = tx2-0.002f;
               ty2 = ty2-0.002f;
            

	           /*gl.glBegin (GL.GL_QUADS);
	           
	           float x1 =  +(i*mapblocksize);
		       float x2 =  +(i*mapblocksize+mapblocksize);
	           float z1 =  -(j*mapblocksize);
		       float z2 =  -(j*mapblocksize+mapblocksize);		
		       
		       float y1 = alturas[MapY+j][MapX+i];
		       float y2 = alturas[MapY+j][MapX+i+1];
		       float y3 = alturas[MapY+j+1][MapX+i+1];
		       float y4 = alturas[MapY+j+1][MapX+i];
		       gl.glNormal3d(normaisv[MapY+j][MapX+i].x, normaisv[MapY+j][MapX+i].y, normaisv[MapY+j][MapX+i].z);		        	   
        	   gl.glTexCoord2f (tx1, ty1);
               gl.glVertex3f (x1, y1, z1);
               
        	   gl.glNormal3d(normaisv[MapY+j][MapX+i+1].x, normaisv[MapY+j][MapX+i+1].y, normaisv[MapY+j][MapX+i+1].z);		               
               gl.glTexCoord2f (tx2, ty1);               
               gl.glVertex3f (x2, y2, z1);
               
        	   gl.glNormal3d(normaisv[MapY+j+1][MapX+i+1].x, normaisv[MapY+j+1][MapX+i+1].y, normaisv[MapY+j+1][MapX+i+1].z);		               
               gl.glTexCoord2f (tx2, ty2);
               gl.glVertex3f (x2, y3, z2);
               
        	   gl.glNormal3d(normaisv[MapY+j+1][MapX+i].x, normaisv[MapY+j+1][MapX+i].y, normaisv[MapY+j+1][MapX+i].z);		               
               gl.glTexCoord2f (tx1, ty2);               
               gl.glVertex3f (x1, y4, z2);


               gl.glEnd ();*/
               
               gl.glBegin (GL.GL_TRIANGLE_STRIP);
	           
	           float x1 =  +(i*mapblocksize);
		       float x2 =  +(i*mapblocksize+mapblocksize);
	           float z1 =  -(j*mapblocksize);
		       float z2 =  -(j*mapblocksize+mapblocksize);		
		       
		       float y1 = alturas[MapY+j][MapX+i];
		       float y2 = alturas[MapY+j][MapX+i+1];
		       float y3 = alturas[MapY+j+1][MapX+i+1];
		       float y4 = alturas[MapY+j+1][MapX+i];
		       gl.glNormal3d(normaisv[MapY+j][MapX+i].x, normaisv[MapY+j][MapX+i].y, normaisv[MapY+j][MapX+i].z);		        	   
        	   gl.glTexCoord2f (tx1, ty1);
               gl.glVertex3f (x1, y1, z1);
               
        	   gl.glNormal3d(normaisv[MapY+j][MapX+i+1].x, normaisv[MapY+j][MapX+i+1].y, normaisv[MapY+j][MapX+i+1].z);		               
               gl.glTexCoord2f (tx2, ty1);               
               gl.glVertex3f (x2, y2, z1);
               

               
        	   gl.glNormal3d(normaisv[MapY+j+1][MapX+i].x, normaisv[MapY+j+1][MapX+i].y, normaisv[MapY+j+1][MapX+i].z);		               
               gl.glTexCoord2f (tx1, ty2);               
               gl.glVertex3f (x1, y4, z2);


        	   gl.glNormal3d(normaisv[MapY+j+1][MapX+i+1].x, normaisv[MapY+j+1][MapX+i+1].y, normaisv[MapY+j+1][MapX+i+1].z);		               
               gl.glTexCoord2f (tx2, ty2);
               gl.glVertex3f (x2, y3, z2);               
               
               gl.glEnd ();               
		           

            }
        }
        
        if(wireframe)gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
    }


}
