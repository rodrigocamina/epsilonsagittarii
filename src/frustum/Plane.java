package frustum;

import matematcbase.Vector3f;

public class Plane {

	Vector3f normal = new Vector3f(),point = new Vector3f();
	float d;

	public Plane( Vector3f v1,  Vector3f v2,  Vector3f v3) {

		set3Points(v1,v2,v3);
	}

	public void set3Points( Vector3f v1,  Vector3f v2,  Vector3f v3) {


		Vector3f aux1, aux2;

		aux1 = v1.subtract(v2);
		aux2 = v3.subtract(v2);

		normal = aux2.crossProduct(aux1);

		normal.normalize();
		point.copy(v2);
		d = -(normal.innerProduct(point));
	}

	public void setNormalAndPoint(Vector3f normal, Vector3f point) {

		this.normal.copy(normal);
		this.normal.normalize();
		d = -(this.normal.innerProduct(point));
	}

	public void setCoefficients(float a, float b, float c, float d) {

		// set the normal vector
		normal.set3P(a,b,c);
		//compute the lenght of the vector
		float l = normal.magnitude();
		// normalize the vector
		normal.set3P(a/l,b/l,c/l);
		// and divide d by th length as well
		this.d = d/l;
	}

	public float distance(Vector3f p) {

		return (d + normal.innerProduct(p));
	}
}
