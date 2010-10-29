package frustum;

import javax.media.opengl.GL;

import matematcbase.Vector3f;

public class FrustumV1 {

	private final int TOP = 0,BOTTOM = 1,LEFT = 2,RIGHT = 3,NEARP = 4,FARP = 5;
	public final int OUTSIDE = 6, INTERSECT = 7, INSIDE = 8;
	public final double ANG2RAD = 3.14159265358979323846/180.0;
	
	public Plane[] pl = new Plane[6];
	public Vector3f ntl,ntr,nbl,nbr,ftl,ftr,fbl,fbr;
	public float nearD, farD, ratio, angle,tang;
	public float nw,nh,fw,fh;
	
	public FrustumV1() {
		for (int i = 0; i < 6; i++) {
			pl[i] = new Plane(new Vector3f(), new Vector3f(), new Vector3f());
		}
	}
	
	public void setCamInternals(float angle, float ratio, float nearD, float farD) {

		this.ratio = ratio;
		this.angle = angle;
		this.nearD = nearD;
		this.farD = farD;

		tang = (float)Math.tan(angle* ANG2RAD * 0.5) ;
		nh = nearD * tang;
		nw = nh * ratio; 
		fh = farD  * tang;
		fw = fh * ratio;


	}

	public void setCamDef(Vector3f p, Vector3f l, Vector3f u) {

		Vector3f nc,fc,X,Y,Z;

		Z = p.subtract(l);
		Z.normalize();

		X = u.crossProduct(Z);
		X.normalize();

		Y = Z.crossProduct(X);

		nc = p.subtract(Z.multiply(nearD));
		fc = p.subtract(Z.multiply(farD));

		ntl = nc.add((Y.multiply(nh))).subtract((X.multiply(nw)));
		ntr = nc.add((Y.multiply(nh))).add((X.multiply(nw)));
		nbl = nc.subtract((Y.multiply(nh))).subtract((X.multiply(nw)));
		nbr = nc.subtract((Y.multiply(nh))).add((X.multiply(nw)));

		ftl = fc.add((Y.multiply(fh))).subtract((X.multiply(fw)));
		ftr = fc.add((Y.multiply(fh))).add((X.multiply(fw)));
		fbl = fc.subtract((Y.multiply(fh))).subtract((X.multiply(fw)));
		fbr = fc.subtract((Y.multiply(fh))).add((X.multiply(fw)));

		pl[TOP].set3Points(ntr,ntl,ftl);
		pl[BOTTOM].set3Points(nbl,nbr,fbr);
		pl[LEFT].set3Points(ntl,nbl,fbl);
		pl[RIGHT].set3Points(nbr,ntr,fbr);
		pl[NEARP].set3Points(ntl,ntr,nbr);
		pl[FARP].set3Points(ftr,ftl,fbl);
	}

	public int pointInFrustum(Vector3f p) {

		int result = INSIDE;
		for(int i=0; i < 6; i++) {

			if (pl[i].distance(p) < 0)
				return OUTSIDE;
		}
		return(result);

	}

	public int sphereInFrustum(Vector3f p, float raio) {

		int result = INSIDE;
		float distance;

		for(int i=0; i < 6; i++) {
			distance = pl[i].distance(p);
			if (distance < -raio)
				return OUTSIDE;
			else if (distance < raio)
				result =  INTERSECT;
		}
		return(result);

	}

	public int boxInFrustum(AABox b) {

		int result = INSIDE;
		for(int i=0; i < 6; i++) {

			if (pl[i].distance(b.getVertexP(pl[i].normal)) < 0)
				return OUTSIDE;
			else if (pl[i].distance(b.getVertexN(pl[i].normal)) < 0)
				result =  INTERSECT;
		}
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
		canvas.glVertex3f(ntl.x,ntl.y,ntl.z);
		canvas.glVertex3f(ntr.x,ntr.y,ntr.z);
		canvas.glVertex3f(nbr.x,nbr.y,nbr.z);
		canvas.glVertex3f(nbl.x,nbl.y,nbl.z);

		//far plane
		canvas.glVertex3f(ftr.x,ftr.y,ftr.z);
		canvas.glVertex3f(ftl.x,ftl.y,ftl.z);
		canvas.glVertex3f(fbl.x,fbl.y,fbl.z);
		canvas.glVertex3f(fbr.x,fbr.y,fbr.z);

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
			a = (ntr.add(ntl).add(nbr).add(nbl)).multiply(0.25f);
			b = a.add(pl[NEARP].normal);
			canvas.glVertex3f(a.x,a.y,a.z);
			canvas.glVertex3f(b.x,b.y,b.z);

			// far
			a = (ftr.add(ftl).add(fbr).add(fbl)).multiply(0.25f);
			b = a.add(pl[FARP].normal);
			canvas.glVertex3f(a.x,a.y,a.z);
			canvas.glVertex3f(b.x,b.y,b.z);

			// left
			a = (ftl.add(fbl).add(nbl).add(ntl)).multiply(0.25f);
			b = a.add(pl[LEFT].normal);
			canvas.glVertex3f(a.x,a.y,a.z);
			canvas.glVertex3f(b.x,b.y,b.z);
			
			// right
			a = (ftr.add(nbr).add(fbr).add(ntr)).multiply(0.25f);
			b = a.add(pl[RIGHT].normal);
			canvas.glVertex3f(a.x,a.y,a.z);
			canvas.glVertex3f(b.x,b.y,b.z);
			
			// top
			a = (ftr.add(ftl).add(ntr).add(ntl)).multiply(0.25f);
			b = a.add(pl[TOP].normal);
			canvas.glVertex3f(a.x,a.y,a.z);
			canvas.glVertex3f(b.x,b.y,b.z);
			
			// bottom
			a = (fbr.add(fbl).add(nbr).add(nbl)).multiply(0.25f);
			b = a.add(pl[BOTTOM].normal);
			canvas.glVertex3f(a.x,a.y,a.z);
			canvas.glVertex3f(b.x,b.y,b.z);

			canvas.glEnd();
	}
}
