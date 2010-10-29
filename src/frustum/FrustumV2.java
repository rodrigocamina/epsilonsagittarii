package frustum;

import javax.media.opengl.GL;

import matematcbase.Vector3f;

public class FrustumV2 {

	private final int TOP = 0,BOTTOM = 1,LEFT = 2,RIGHT = 3,NEARP = 4,FARP = 5;
	public static final int OUTSIDE = 6, INTERSECT = 7, INSIDE = 8;
	public final double ANG2RAD = 3.14159265358979323846/180.0;
	
	public Plane[] pl = new Plane[6];
	public Vector3f ntl,ntr,nbl,nbr,ftl,ftr,fbl,fbr,X,Y,Z,camPos;
	public float nearD, farD, ratio, angle;
	public float sphereFactorX, sphereFactorY;
	public double tang;
	public double nw,nh,fw,fh;
	
	public FrustumV2() {
		for (int i = 0; i < 6; i++) {
			pl[i] = new Plane(new Vector3f(), new Vector3f(), new Vector3f());
		}
	}
	
	public void setFrustum(float[][] m) {

		pl[NEARP].setCoefficients(m[2][0] + m[3][0],
								  m[2][1] + m[3][1],
								  m[2][2] + m[3][2],
								  m[2][3] + m[3][3]);
		pl[FARP].setCoefficients( -m[2][0] + m[3][0],
								  -m[2][1] + m[3][1],
								  -m[2][2] + m[3][2],
								  -m[2][3] + m[3][3]);
		pl[BOTTOM].setCoefficients(m[1][0] + m[3][0],
								   m[1][1] + m[3][1],
								   m[1][2] + m[3][2],
								   m[1][3] + m[3][3]);
		pl[TOP].setCoefficients(  -m[1][0] + m[3][0],
								  -m[1][1] + m[3][1],
								  -m[1][2] + m[3][2],
								  -m[1][3] + m[3][3]);
		pl[LEFT].setCoefficients(  m[0][0] + m[3][0],
								   m[0][1] + m[3][1],
								   m[0][2] + m[3][2],
								   m[0][3] + m[3][3]);
		pl[RIGHT].setCoefficients(-m[0][0] + m[3][0],
								  -m[0][1] + m[3][1],
								  -m[0][2] + m[3][2],
								  -m[0][3] + m[3][3]);
	}

	public void setCamInternals(float angle, float ratio, float nearD, float farD) {

		// store the information
		this.ratio = ratio;
		this.angle = (float)(angle * ANG2RAD);
		this.nearD = nearD;
		this.farD = farD;

		// compute width and height of the near and far plane sections
		tang = Math.tan(this.angle);
		sphereFactorY = (float)(1.0/Math.cos(this.angle));//tang * sin(this.angle) + cos(this.angle);

		float anglex = (float)(Math.atan(tang*ratio));
		sphereFactorX = (float)(1.0/Math.cos(anglex)); //tang*ratio * sin(anglex) + cos(anglex);

		nh = nearD * tang;
		nw = nh * ratio; 

		fh = farD * tang;
		fw = fh * ratio;

	}

	public void setCamDef(Vector3f position, Vector3f direcao, Vector3f cima) {

		Vector3f nc,fc;

		camPos = position;

		// compute the Z axis of camera
		Z = position.subtract(direcao);
		Z.normalize();

		// X axis of camera of given "up" vector and Z axis
		X = cima.crossProduct(Z);
		X.normalize();

		// the real "up" vector is the cross product of Z and X
		Y = Z.crossProduct(X);

		// compute the center of the near and far planes
		nc = position.subtract(Z.multiply(nearD));
		fc = position.subtract(Z.multiply(farD));

		// compute the 8 corners of the frustum
		ntl = nc.add(Y.multiply(nh)).subtract(X.multiply(nw));
		ntr = nc.add(Y.multiply(nh)).add(X.multiply(nw));
		nbl = nc.subtract(Y.multiply(nh)).subtract(X.multiply(nw));
		nbr = nc.subtract(Y.multiply(nh)).add(X.multiply(nw));

		ftl = fc.add(Y.multiply(fh)).subtract(X.multiply(fw));
		fbr = fc.subtract(Y.multiply(fh)).add(X.multiply(fw));
		ftr = fc.add(Y.multiply(fh)).add(X.multiply(fw));
		fbl = fc.subtract(Y.multiply(fh)).subtract(X.multiply(fw));

		// compute the six planes
		// the function set3Points asssumes that the points
		// are given in counter clockwise order
		pl[TOP].set3Points(ntr,ntl,ftl);
		pl[BOTTOM].set3Points(nbl,nbr,fbr);
		pl[LEFT].set3Points(ntl,nbl,fbl);
		pl[RIGHT].set3Points(nbr,ntr,fbr);
//		pl[NEARP].set3Points(ntl,ntr,nbr);
//		pl[FARP].set3Points(ftr,ftl,fbl);

		pl[NEARP].setNormalAndPoint(Z.invert(),nc);
		pl[FARP].setNormalAndPoint(Z,fc);

		Vector3f aux,normal;

		aux = (nc.add(Y.multiply(nh))).subtract(position);
		normal = aux.crossProduct(X);
		pl[TOP].setNormalAndPoint(normal,nc.add(Y.multiply(nh)));

		aux = (nc.subtract(Y.multiply(nh))).subtract(position);
		normal = X.crossProduct(aux);
		pl[BOTTOM].setNormalAndPoint(normal,nc.subtract(Y.multiply(nh)));

		aux = (nc.subtract(X.multiply(nw))).subtract(position);
		normal = aux.crossProduct(Y);
		pl[LEFT].setNormalAndPoint(normal,nc.subtract(X.multiply(nw)));

		aux = (nc.add(X.multiply(nw))).subtract(position);
		normal = Y.crossProduct(aux);
		pl[RIGHT].setNormalAndPoint(normal,nc.add(X.multiply(nw)));
	}

	public int pointInFrustum(Vector3f p) {

		float pcz,pcx,pcy,aux;

		// compute vector from camera position to p
		Vector3f v = p.subtract(camPos);

		// compute and test the Z coordinate
		pcz = v.innerProduct(Z.invert());
		if (pcz > farD || pcz < nearD)
			return(OUTSIDE);

		// compute and test the Y coordinate
		pcy = v.innerProduct(Y);
		aux = (float)(pcz * tang);
		if (pcy > aux || pcy < -aux)
			return(OUTSIDE);
			
		// compute and test the X coordinate
		pcx = v.innerProduct(X);
		aux = aux * ratio;
		if (pcx > aux || pcx < -aux)
			return(OUTSIDE);


		return(INSIDE);

		
	}

	public int sphereInFrustum(Vector3f p, float radius) {


		float d1,d2;
		float az,ax,ay,zz1,zz2;
		int result = INSIDE;

		Vector3f v = p.subtract(camPos);

		az = v.innerProduct(Z.invert());
		if (az > farD + radius || az < nearD-radius)
			return(OUTSIDE);

		ax = v.innerProduct(X);
		zz1 = (float)(az * tang * ratio);
		d1 = sphereFactorX * radius;
		if (ax > zz1+d1 || ax < -zz1-d1)
			return(OUTSIDE);

		ay = v.innerProduct(Y);
		zz2 = (float)(az * tang);
		d2 = sphereFactorY * radius;
		if (ay > zz2+d2 || ay < -zz2-d2)
			return(OUTSIDE);



		if (az > farD - radius || az < nearD+radius)
			result = INTERSECT;
		if (ay > zz2-d2 || ay < -zz2+d2)
			result = INTERSECT;
		if (ax > zz1-d1 || ax < -zz1+d1)
			result = INTERSECT;


		return(result);

	}

	public void drawPoints(GL canvas) {


		canvas.glBegin(GL.GL_POINTS);

		canvas.glVertex3f(ntl.x,ntl.y,ntl.z);
		canvas.glVertex3f(ntr.x,ntr.y,ntr.z);
		canvas.glVertex3f(nbl.x,nbl.y,nbl.z);
		canvas.glVertex3f(nbr.x,nbr.y,nbr.z);

		canvas.glVertex3f(ftl.x,ftl.y,ftl.z);
		canvas.glVertex3f(ftr.x,ftr.y,ftr.z);
		canvas.glVertex3f(fbl.x,fbl.y,fbl.z);
		canvas.glVertex3f(fbr.x,fbr.y,fbr.z);

		canvas.glEnd();
	}

	public void drawLines(GL canvas) {

		canvas.glBegin(GL.GL_LINE_LOOP);
		//near plane
		
		canvas.glVertex3f(ntl.x,ntl.y,ntl.z);
		canvas.glVertex3f(ntr.x,ntr.y,ntr.z);
		canvas.glVertex3f(nbr.x,nbr.y,nbr.z);
		canvas.glVertex3f(nbl.x,nbl.y,nbl.z);
		canvas.glEnd();

		canvas.glBegin(GL.GL_LINE_LOOP);
		//far plane
		canvas.glVertex3f(ftr.x,ftr.y,ftr.z);
		canvas.glVertex3f(ftl.x,ftl.y,ftl.z);
		canvas.glVertex3f(fbl.x,fbl.y,fbl.z);
		canvas.glVertex3f(fbr.x,fbr.y,fbr.z);
		canvas.glEnd();

		canvas.glBegin(GL.GL_LINE_LOOP);
		//bottom plane
			canvas.glVertex3f(nbl.x,nbl.y,nbl.z);
			canvas.glVertex3f(nbr.x,nbr.y,nbr.z);
			canvas.glVertex3f(fbr.x,fbr.y,fbr.z);
			canvas.glVertex3f(fbl.x,fbl.y,fbl.z);
		canvas.glEnd();

		canvas.glBegin(GL.GL_LINE_LOOP);
		//top plane
			canvas.glVertex3f(ntr.x,ntr.y,ntr.z);
			canvas.glVertex3f(ntl.x,ntl.y,ntl.z);
			canvas.glVertex3f(ftl.x,ftl.y,ftl.z);
			canvas.glVertex3f(ftr.x,ftr.y,ftr.z);
		canvas.glEnd();

		canvas.glBegin(GL.GL_LINE_LOOP);
		//left plane
			canvas.glVertex3f(ntl.x,ntl.y,ntl.z);
			canvas.glVertex3f(nbl.x,nbl.y,nbl.z);
			canvas.glVertex3f(fbl.x,fbl.y,fbl.z);
			canvas.glVertex3f(ftl.x,ftl.y,ftl.z);
		canvas.glEnd();

		canvas.glBegin(GL.GL_LINE_LOOP);
		// right plane
			canvas.glVertex3f(nbr.x,nbr.y,nbr.z);
			canvas.glVertex3f(ntr.x,ntr.y,ntr.z);
			canvas.glVertex3f(ftr.x,ftr.y,ftr.z);
			canvas.glVertex3f(fbr.x,fbr.y,fbr.z);

		canvas.glEnd();
	}

	public void drawPlanes(GL canvas) {

		canvas.glBegin(GL.GL_QUADS);

		//near plane
		/*
			canvas.glVertex3f(ntl.x,ntl.y,ntl.z);
			canvas.glVertex3f(ntr.x,ntr.y,ntr.z);
			canvas.glVertex3f(nbr.x,nbr.y,nbr.z);
			canvas.glVertex3f(nbl.x,nbl.y,nbl.z);
		

		//far plane
			canvas.glVertex3f(ftr.x,ftr.y,ftr.z);
			canvas.glVertex3f(ftl.x,ftl.y,ftl.z);
			canvas.glVertex3f(fbl.x,fbl.y,fbl.z);
			canvas.glVertex3f(fbr.x,fbr.y,fbr.z);
*/
		//bottom plane
			canvas.glVertex3f(nbl.x,nbl.y,nbl.z);
			canvas.glVertex3f(nbr.x,nbr.y,nbr.z);
			canvas.glVertex3f(fbr.x,fbr.y,fbr.z);
			canvas.glVertex3f(fbl.x,fbl.y,fbl.z);

		//top plane
			canvas.glVertex3f(ntr.x,ntr.y,ntr.z);
			canvas.glVertex3f(ntl.x,ntl.y,ntl.z);
			canvas.glVertex3f(ftl.x,ftl.y,ftl.z);
			canvas.glVertex3f(ftr.x,ftr.y,ftr.z);

		//left plane

			canvas.glVertex3f(ntl.x,ntl.y,ntl.z);
			canvas.glVertex3f(nbl.x,nbl.y,nbl.z);
			canvas.glVertex3f(fbl.x,fbl.y,fbl.z);
			canvas.glVertex3f(ftl.x,ftl.y,ftl.z);

		// right plane
			canvas.glVertex3f(nbr.x,nbr.y,nbr.z);
			canvas.glVertex3f(ntr.x,ntr.y,ntr.z);
			canvas.glVertex3f(ftr.x,ftr.y,ftr.z);
			canvas.glVertex3f(fbr.x,fbr.y,fbr.z);

		canvas.glEnd();

	}

	public void drawNormals(GL canvas) {

		Vector3f a,b;

		canvas.glBegin(GL.GL_LINES);

			// near
			a = (ntr.add(ntl).add(nbr).add(nbl)).multiply(0.25);
			b = a.add(pl[NEARP].normal);
			canvas.glVertex3f(a.x,a.y,a.z);
			canvas.glVertex3f(b.x,b.y,b.z);

			// far
			a = (ftr.add(ftl).add(fbr).add(fbl)).multiply(0.25);
			b = a.add(pl[FARP].normal);
			canvas.glVertex3f(a.x,a.y,a.z);
			canvas.glVertex3f(b.x,b.y,b.z);

			// left
			a = (ftl.add(fbl).add(nbl).add(ntl)).multiply(0.25);
			b = a.add(pl[LEFT].normal);
			canvas.glVertex3f(a.x,a.y,a.z);
			canvas.glVertex3f(b.x,b.y,b.z);
			
			// right
			a = (ftr.add(nbr).add(fbr).add(ntr)).multiply(0.25);
			b = a.add(pl[RIGHT].normal);
			canvas.glVertex3f(a.x,a.y,a.z);
			canvas.glVertex3f(b.x,b.y,b.z);
			
			// top
			a = (ftr.add(ftl).add(ntr).add(ntl)).multiply(0.25);
			b = a.add(pl[TOP].normal);
			canvas.glVertex3f(a.x,a.y,a.z);
			canvas.glVertex3f(b.x,b.y,b.z);
			
			// bottom
			a = (fbr.add(fbl).add(nbr).add(nbl)).multiply(0.25);
			b = a.add(pl[BOTTOM].normal);
			canvas.glVertex3f(a.x,a.y,a.z);
			canvas.glVertex3f(b.x,b.y,b.z);

		canvas.glEnd();


	}

	
	
	
}
