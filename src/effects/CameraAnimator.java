package effects;

import java.util.LinkedList;
import java.util.List;

import matematcbase.Vector3f;

public class CameraAnimator {

	private static LinkedList<CameraFrame> frames = new LinkedList<CameraFrame>();
	
	public static void startFrame(Vector3f front, Vector3f up, Vector3f right){
		CameraFrame cf = new CameraFrame(front, up, right);
		cf.time = System.currentTimeMillis();
		frames.addLast(cf);
	}
	
	public static void addFrame(Vector3f front, Vector3f up, Vector3f right){
		frames.addLast(new CameraFrame(front, up, right));
	}
	
	public static CameraFrame getActualFrame(){
		CameraFrame start = frames.getFirst();
		if(frames.size()>1){
			CameraFrame end = frames.get(1);
			long actTime = System.currentTimeMillis()-100;
			while(end.time<actTime){
				frames.remove();
				start = end;
				if(frames.size()>1){
					end = frames.get(1);
				}else{
					return start;
				}
			}
			float peso = (actTime-start.time)/(float)(end.time-start.time);
			float peso2 = 1-peso;
			//System.out.println((actTime-start.time)+"/"+(end.time-start.time));
			//System.out.println(start);
			//System.out.println(end);
			//System.out.println("getAct "+peso);
			return new CameraFrame(vetorMedia(start.frontV,peso,end.frontV,peso2), vetorMedia(start.upV,peso,end.upV,peso2), vetorMedia(start.rightV,peso,end.rightV,peso2));
		}else{
			return start;
		}
	}
	
	public static Vector3f vetorMedia(Vector3f v1, float peso, Vector3f v2, float peso2){
		Vector3f result = new Vector3f();
		result.x = v1.x*peso2+v2.x*peso;
		result.y = v1.y*peso2+v2.y*peso;
		result.z = v1.z*peso2+v2.z*peso;
		return result;
	}
	/*
	3 start.time
	3.4 actTime
	4 end.time
	*/
	
}
