package matematcbase;

import javax.media.opengl.GL;

public class Util {

	public static void rotacionaGLViaVetores(GL gl,Vector3f frontV,Vector3f rightV,Vector3f upV){
		float[] matriz = new float[16];
 	    matriz[0] = upV.x;
 	    matriz[1] = upV.y;
 	    matriz[2] = upV.z;
 	    //------------------
 	    matriz[4] = rightV.x;
 	    matriz[5] = rightV.y;
 	    matriz[6] = rightV.z;
 	    //------------------
 	    matriz[8] = frontV.x;
 	    matriz[9] = frontV.y;
 	    matriz[10] = frontV.z;
 	    //------------------
 	    matriz[3] = matriz[7] = matriz[11] = matriz[12] = matriz[13] = matriz[14] = 0.0f;
 	    matriz[15] = 1.0f;
 	    // float[] -> matrix de rotacao
 	    // int -> offset(primeiro representante da matrix)
 	    gl.glMultMatrixf(matriz, 0);
	}
	public static void rotacionaGLViaVetores(GL gl,Vector3f frontV,Vector3f rightV,Vector3f upV,Vector3f position){
		float[] matriz = new float[16];
 	    matriz[0] = upV.x-position.x;
 	    matriz[1] = upV.y-position.y;
 	    matriz[2] = upV.z-position.z;
 	    //------------------
 	    matriz[4] = rightV.x-position.x;
 	    matriz[5] = rightV.y-position.y;
 	    matriz[6] = rightV.z-position.z;
 	    //------------------
 	    matriz[8] = frontV.x-position.x;
 	    matriz[9] = frontV.y-position.y;
 	    matriz[10] = frontV.z-position.z;
 	    //------------------
 	    matriz[3] = matriz[7] = matriz[11] = matriz[12] = matriz[13] = matriz[14] = 0.0f;
 	    matriz[15] = 1.0f;
 	    // float[] -> matrix de rotacao
 	    // int -> offset(primeiro representante da matrix)
 	    gl.glMultMatrixf(matriz, 0);
	}
}
