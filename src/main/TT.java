package main;

import matematcbase.Matrix4x4;
import matematcbase.Vector3f;

public class TT {

	
	
	public static void main(String[] args) {

		Vector3f v1 = new Vector3f(0,0,1);
		Vector3f v2 = new Vector3f(0,0,1);
		
		Matrix4x4 m1 = new Matrix4x4().setIdentity();
		m1.combine(new Matrix4x4().setRotateX(5));
		m1.combine(new Matrix4x4().setRotateX(5));
		m1.combine(new Matrix4x4().setRotateX(5));
		m1.combine(new Matrix4x4().setRotateX(-5));
		m1.combine(new Matrix4x4().setRotateX(-5));
		m1.combine(new Matrix4x4().setRotateX(-5));
		
		Matrix4x4 m2 = new Matrix4x4();
		m2.setRotateX(15);
		m2.combine(new Matrix4x4().setRotateX(-15));

		//System.out.println(v1.RotByMatrix(m1.toArray()));
		//System.out.println(v2.RotByMatrix(m2.toArray()));
	}
}
