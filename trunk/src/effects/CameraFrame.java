package effects;
import matematcbase.Vector3f;


public class CameraFrame {

	long time;
	public Vector3f frontV  = new Vector3f(0,0,5);
	public Vector3f upV  = new Vector3f(0,5,0);	
	public Vector3f rightV  = new Vector3f(5,0,0);
	
	public CameraFrame(Vector3f frontV,Vector3f upV,Vector3f rightV) {
		//+2000 == momento em que a camera deve ajustar o seu angulo
		time = System.currentTimeMillis();
		this.frontV = frontV;
		this.upV = upV;
		this.rightV = rightV;
	}
	
	@Override
	public String toString() {
		return frontV.x+","+frontV.y+","+frontV.z+" "+upV.x+","+upV.y+","+upV.z+" "+rightV.x+","+rightV.y+","+rightV.z;
	}
}
