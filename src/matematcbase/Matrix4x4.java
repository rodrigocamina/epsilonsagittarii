package matematcbase;


public class Matrix4x4 {
   public double m00;
   /** element 0,1 of matrix */
   public double m01;
   /** element 0,2 of matrix */
   public double m02;
   /** element 0,3 of matrix */
   public double m03;
   /** element 1,0 of matrix */
   public double m10;
   /**element 1,1 of matrix */
   public double m11;
   /** element 1,2 of matrix */
   public double m12;
   /** element 1,3 of matrix */
   public double m13;
   /** element 2,0 of matrix */
   public double m20;
   /** element 2,1 of matrix */
   public double m21;
   /** element 2,2 of matrix */
   public double m22;
   /** element 2,3 of matrix */
   public double m23;
   /** element 3,0 of matrix */
   public double m30;
   /** element 3,1 of matrix */
   public double m31;
   /** element 3,2 of matrix */
   public double m32;
   /** element 3,3 of matrix */
   public double m33;
   
   /** constructor for initialy zero matrix */
   public Matrix4x4() {
   m00 = 0.0;
   m01 = 0.0;
   m02 = 0.0;
   m03 = 0.0;
   m10 = 0.0;
   m11 = 0.0;
   m12 = 0.0;
   m13 = 0.0;
   m20 = 0.0;
   m21 = 0.0;
   m22 = 0.0;
   m23 = 0.0;
   m30 = 0.0;
   m31 = 0.0;
   m32 = 0.0;
   m33 = 0.0;
   }
   
   /** copy constructor
   * @param a class to copy
   * */
   public Matrix4x4(Matrix4x4 a) {
   m00 = a.m00;
   m01 = a.m01;
   m02 = a.m02;
   m03 = a.m03;
   m10 = a.m10;
   m11 = a.m11;
   m12 = a.m12;
   m13 = a.m13;
   m20 = a.m20;
   m21 = a.m21;
   m22 = a.m22;
   m23 = a.m23;
   m30 = a.m30;
   m31 = a.m31;
   m32 = a.m32;
   m33 = a.m33;
   }
   
   /** constructor set to value of a times b
   * @param a first matrix
   * @param b second matrix
   */
   public Matrix4x4(Matrix4x4 a,Matrix4x4 b) {
	   combine(a,b);
   }
   
   /** a static class to return the VRML name of this property
   * @return the VRML name of this property
   */
   public static String vrmlType_s(){
   return "SFTransform";
   }
   
   /** sets this transform to the identity matrix, multiplying by the
   * identity matrix does not alter the other matrix.
   * for theory see:
   * http://www.euclideanspace.com/maths/algebra/matrix/arithmetic/index.htm
   * */
   public Matrix4x4 setIdentity(){
   m00 = 1.0;
   m01 = 0.0;
   m02 = 0.0;
   m03 = 0.0;
   m10 = 0.0;
   m11 = 1.0;
   m12 = 0.0;
   m13 = 0.0;
   m20 = 0.0;
   m21 = 0.0;
   m22 = 1.0;
   m23 = 0.0;
   m30 = 0.0;
   m31 = 0.0;
   m32 = 0.0;
   m33 = 1.0;
   return this;
   }/** overrides the clone method for this class
   * @return new instance with same values as this
   */
   public Object clone() {
   return new Matrix4x4(this);
   }
   
   /** sets the value of this transform to a copy of the transform supplied to    it
   * @param input instance to copy
   * */
   public void copy(Matrix4x4 input){
   m00 = input.m00;
   m01 = input.m01;
   m02 = input.m02;
   m03 = input.m03;
   m10 = input.m10;
   m11 = input.m11;
   m12 = input.m12;
   m13 = input.m13;
   m20 = input.m20;
   m21 = input.m21;
   m22 = input.m22;
   m23 = input.m23;
   m30 = input.m30;
   m31 = input.m31;
   m32 = input.m32;
   m33 = input.m33;
   }
   
   /** create an array of doubles with the values of this matrix
   *
   */
   public double[] toArray(){
   double[] matrix = new double[16];
   matrix[0] = m00;
   matrix[1] = m01;
   matrix[2] = m02;
   matrix[3] = m03;
   matrix[4] = m10;
   matrix[5] = m11;
   matrix[6] = m12;
   matrix[7] = m13;
   matrix[8] = m20;
   matrix[9] = m21;
   matrix[10] = m22;
   matrix[11] = m23;
   matrix[12] = m30;
   matrix[13] = m31;
   matrix[14] = m32;
   matrix[15] = m33;
   return matrix;
   }
   
   /** multiply this matrix with the matrix supplied (m1)
   ** for theory see:
   * http://www.euclideanspace.com/maths/algebra/matrix/arithmetic/index.htm
   * @param m1 matrix to multiply by
   * */
   public Matrix4x4 combine(Matrix4x4 m1) {
	   Matrix4x4 tmp = new Matrix4x4(this);
	   return combine(tmp,m1);
   }
   
   /** multiply the matrix supplied (m1) with this matrix. This is different
   * from the combine because the order of multipication is significant
   * for theory see:
   * http://www.euclideanspace.com/maths/algebra/matrix/arithmetic/index.htm
   * @param m1 matrix which we multiply this by
   * */
   public void combineInverse(Matrix4x4 m1) {
   Matrix4x4 tmp = new Matrix4x4(this);
   combine(m1,tmp);
   }
   
   /** sets the value of this to m1 * m2
   * for theory see:
   * http://www.euclideanspace.com/maths/algebra/matrix/arithmetic/index.htm
   * @param m1 martix 1
   * @param m2 matrix 2
   * */
   public Matrix4x4 combine(Matrix4x4 m1,Matrix4x4 m2) {
   m00 = m1.m00*m2.m00 + m1.m01*m2.m10 + m1.m02*m2.m20 + m1.m03*m2.m30;
   m01 = m1.m00*m2.m01 + m1.m01*m2.m11 + m1.m02*m2.m21 + m1.m03*m2.m31;
   m02 = m1.m00*m2.m02 + m1.m01*m2.m12 + m1.m02*m2.m22 + m1.m03*m2.m32;
   m03 = m1.m00*m2.m03 + m1.m01*m2.m13 + m1.m02*m2.m23 + m1.m03*m2.m33;
   m10 = m1.m10*m2.m00 + m1.m11*m2.m10 + m1.m12*m2.m20 + m1.m13*m2.m30;
   m11 = m1.m10*m2.m01 + m1.m11*m2.m11 + m1.m12*m2.m21 + m1.m13*m2.m31;
   m12 = m1.m10*m2.m02 + m1.m11*m2.m12 + m1.m12*m2.m22 + m1.m13*m2.m32;
   m13 = m1.m10*m2.m03 + m1.m11*m2.m13 + m1.m12*m2.m23 + m1.m13*m2.m33;
   m20 = m1.m20*m2.m00 + m1.m21*m2.m10 + m1.m22*m2.m20 + m1.m23*m2.m30;
   m21 = m1.m20*m2.m01 + m1.m21*m2.m11 + m1.m22*m2.m21 + m1.m23*m2.m31;
   m22 = m1.m20*m2.m02 + m1.m21*m2.m12 + m1.m22*m2.m22 + m1.m23*m2.m32;
   m23 = m1.m20*m2.m03 + m1.m21*m2.m13 + m1.m22*m2.m23 + m1.m23*m2.m33;
   m30 = m1.m30*m2.m00 + m1.m31*m2.m10 + m1.m32*m2.m20 + m1.m33*m2.m30;
   m31 = m1.m30*m2.m01 + m1.m31*m2.m11 + m1.m32*m2.m21 + m1.m33*m2.m31;
   m32 = m1.m30*m2.m02 + m1.m31*m2.m12 + m1.m32*m2.m22 + m1.m33*m2.m32;
   m33 = m1.m30*m2.m03 + m1.m31*m2.m13 + m1.m32*m2.m23 + m1.m33*m2.m33;
   return this;
   }
   
   /** transform the vector supplied using this transform
   * for theory see:
   * http://www.euclideanspace.com/maths/algebra/matrix/transforms/index.htm
   * @param v vector to be transformed
   * */
   public void transform(Vector3f v){
	   if (v==null) {
	   System.out.println("Matrix4x4.transform v==null");
	   return;
	   }
	   Vector3f temp = new Vector3f(v);
	   v.x = (float)(m00 * temp.x + m01 * temp.y + m02 * temp.z + m03);
	   v.y = (float)(m10 * temp.x + m11 * temp.y + m12 * temp.z + m13);
	   v.z = (float)(m20 * temp.x + m21 * temp.y + m22 * temp.z + m23);
   }
   
   /** linear translation, ie add the vector supplied to the translation part    of this matrix
   * for theory see:
   * http://www.euclideanspace.com/maths/algebra/matrix/transforms/index.htm
   * @param v ammount of translation
   * */
   public void translate(Vector3f v){
	   if (v==null) {
	   System.out.println("Matrix4x4.translate v==null");
	   return;
	   }
	   m03 += v.x;
	   m13 += v.y;
	   m23 += v.z;
	   m33 = 1.0;
   }
   /** scale this transform,
   * for theory see:
   * http://www.euclideanspace.com/maths/geometry/rotations/rotationAndTranslation/nonMatrix/index.htm
   * @param v scale factor in x,y and z dimensions
   */
   public void scale(Vector3f v){
	   m00 *= v.x;
	   m01 *= v.x;
	   m02 *= v.x;
	   m10 *= v.y;
	   m11 *= v.y;
	   m12 *= v.y;
	   m20 *= v.z;
	   m21 *= v.z;
	   m22 *= v.z;
	   m33 = 1.0;
   }
   /** scale equally in all dimensions
   * http://www.euclideanspace.com/maths/geometry/rotations/rotationAndTranslation/nonMatrix/index.htm
   * @param scale the scale factor for the matrix
   * */
   public void scale(double scale) {
	   m00 *= scale;
	   m01 *= scale;
	   m02 *= scale;
	   m03 *= scale;
	   m10 *= scale;
	   m11 *= scale;
	   m12 *= scale;
	   m13 *= scale;
	   m20 *= scale;
	   m21 *= scale;
	   m22 *= scale;
	   m23 *= scale;
	   m30 *= scale;
	   m31 *= scale;
	   m32 *= scale;
	   m33 *= scale;
   }
   
   /** rotate about a point, rotation given by euler angles
   * for theory see:
   * http://www.euclideanspace.com/maths/algebra/matrix/orthogonal/rotation/index.htm
   * @param centre">point to rotate around
   * @param theta">angle in radians
   * @param phi">angle in radians
   * @param alpha">angle in radians
   */
   public Matrix4x4 setRotate(Vector3f centre, double theta,double phi,double alpha)    {
	   double cosAlpha, sinAlpha, cosPhi, sinPhi,
	   cosTheta, sinTheta, cosPhi2, sinPhi2,
	   cosTheta2, sinTheta2, c, a1,a2,a3;
	   if (centre==null) {
	   a1=a2=a3=0;
	   } else {
	   a1 = centre.x;
	   a2 = centre.y;
	   a3 = centre.z;
	   }
	   cosPhi = Math.cos(phi); sinPhi = Math.sin(phi);
	   cosPhi2 = cosPhi * cosPhi; sinPhi2 = sinPhi * sinPhi;
	   cosTheta = Math.cos(theta);
	   sinTheta = Math.sin(theta);
	   cosTheta2 = cosTheta * cosTheta;
	   sinTheta2 = sinTheta * sinTheta;
	   cosAlpha = Math.cos(alpha);
	   sinAlpha = Math.sin(alpha);
	   c = 1.0 - cosAlpha;
	   m00 = cosTheta2 * (cosAlpha * cosPhi2 + sinPhi2)
	   + cosAlpha * sinTheta2;
	   m10 = sinAlpha * cosPhi + c * sinPhi2 * cosTheta * sinTheta;
	   m20 = sinPhi * (cosPhi * cosTheta * c - sinAlpha * sinTheta);
	   m30 = 0.0; m01 = sinPhi2 * cosTheta * sinTheta * c - sinAlpha * cosPhi;
	   m11 = sinTheta2 * (cosAlpha * cosPhi2 + sinPhi2)
	   + cosAlpha * cosTheta2;
	   m21 = sinPhi * (cosPhi * sinTheta * c + sinAlpha * cosTheta);
	   m31 = 0.0; m02 = sinPhi * (cosPhi * cosTheta * c + sinAlpha * sinTheta);
	   m12 = sinPhi * (cosPhi * sinTheta * c - sinAlpha * cosTheta);
	   m22 = cosAlpha * sinPhi2 + cosPhi2;
	   m32 = 0.0; m03 = a1 - a1 * m00 - a2 * m01 - a3 * m02;
	   m13 = a2 - a1 * m10 - a2 * m11 - a3 * m12;
	   m23 = a3 - a1 * m20 - a2 * m21 - a3 * m22;
	   m33 = 1.0;
	   return this;
   }
   
   public Matrix4x4 setRotateX(double ang)    {
	   double cosAng, sinAng;

	   cosAng = Math.cos(ang*0.0174532);
	   sinAng = Math.sin(ang*0.0174532);

	   m00 = 1;
	   m01 = 0;
	   m02 = 0;
	   m03 = 0;
	   m10 = 0;
	   m11 = cosAng;
	   m12 = sinAng;
	   m13 = 0;
	   m20 = 0;
	   m21 = -sinAng;
	   m22 = cosAng;
	   m23 = 0;
	   m30 = 0;
	   m31 = 0;
	   m32 = 0;
	   m33 = 1;
	   return this;
   }
   
   public Matrix4x4 setRotateY(double ang)    {
	   double cosAng, sinAng;

	   cosAng = Math.cos(Math.toRadians(ang));
	   sinAng = Math.sin(Math.toRadians(ang));

	   m00 = cosAng;
	   m01 = 0;
	   m02 = -sinAng;
	   m03 = 0;
	   m10 = 0;
	   m11 = 1;
	   m12 = 0;
	   m13 = 0;
	   m20 = sinAng;
	   m21 = 0;
	   m22 = cosAng;
	   m23 = 0;
	   m30 = 0;
	   m31 = 0;
	   m32 = 0;
	   m33 = 1;
	   return this;
   }  
   
   public Matrix4x4 setRotateZ(double ang)    {
	   double cosAng, sinAng;

	   cosAng = Math.cos(ang*0.0174532);
	   sinAng = Math.sin(ang*0.0174532);

	   m00 = cosAng;
	   m01 = sinAng;
	   m02 = 0;
	   m03 = 0;
	   m10 = -sinAng;
	   m11 = cosAng;
	   m12 = 0;
	   m13 = 0;
	   m20 = 0;
	   m21 = 0;
	   m22 = 1;
	   m23 = 0;
	   m30 = 0;
	   m31 = 0;
	   m32 = 0;
	   m33 = 1;
	   return this;
   } 
   
   public Matrix4x4 setRotate(double ang,double x,double y,double z){

	   double c = Math.cos(ang*0.0174532);
	   double s = Math.sin(ang*0.0174532);
	   double t = 1.00-c; 

		double fMag = ( x*x + y*y + z*z );
		if (fMag == 0) {return this;}
		
		double fMult = 1.0f/Math.sqrt(fMag);            
		x *= fMult;
		y *= fMult;
		z *= fMult;

	   System.out.println("x "+x+" y "+y+" z "+z+" c "+c+" s "+s+" t "+t);
	
	   //m( coluna)( linha)
	   m00 = (t*x*x)+c;
	   m01 = (t*x*y)+(s*z);
	   m02 = (t*x*z)-(s*y);
	   m03 = 0;
	   m10 = (t*y*x)-(s*z);
	   m11 = (t*y*y)+c;
	   m12 = (t*y*z)+(s*x);
	   m13 = 0;
	   m20 = (t*z*x)+(s*y);
	   m21 = (t*z*y)-(s*x);
	   m22 = (t*z*z)+c;
	   m23 = 0;
	   m30 = 0;
	   m31 = 0;
	   m32 = 0;
	   m33 = 1;
	   return this;
	   
   } 
   
   /** format values into a string with square brackets
   * @return a string representation of this class
   * */
   public String toString(){
   String s1="["+m00+","+m01+","+m02+","+m03+"]";
   String s2="["+m10+","+m11+","+m12+","+m13+"]";
   String s3="["+m20+","+m21+","+m22+","+m23+"]";
   String s4="["+m30+","+m31+","+m32+","+m33+"]";
   return ""+s1+"\n"+s2+"\n"+s3+"\n"+s4;
   }
   
   /** output as a string
   * @param mode possible values:
   * 0 - output modified values
   * 1 - output original values
   * 2 - output attribute
   * 3 - output attribute in brackets
   * 4 - output with f prefix
   * @return a string representation of this class
   */
   public String outstring(int i) {
   return "\n"+m00+","+m01+","+m02+","+m03+"\n"+
   m10+","+m11+","+m12+","+m13+"\n"+
   m20+","+m21+","+m22+","+m23+"\n"+
   m30+","+m31+","+m32+","+m33;
   }
   
   /** find inverse matrix
   * for theory see:
   * http://www.euclideanspace.com/maths/algebra/matrix/arithmetic/index.htm
   * */
   public void invert() {
   double det = determinant();
   double t00 = m12*m23*m31 - m13*m22*m31 + m13*m21*m32 - m11*m23*m32 - m12*m21*m33    + m11*m22*m33;
   double t01 = m03*m22*m31 - m02*m23*m31 - m03*m21*m32 + m01*m23*m32 + m02*m21*m33    - m01*m22*m33;
   double t02 = m02*m13*m31 - m03*m12*m31 + m03*m11*m32 - m01*m13*m32 - m02*m11*m33    + m01*m12*m33;
   double t03 = m03*m12*m21 - m02*m13*m21 - m03*m11*m22 + m01*m13*m22 + m02*m11*m23    - m01*m12*m23;
   double t10 = m13*m22*m30 - m12*m23*m30 - m13*m20*m32 + m10*m23*m32 + m12*m20*m33    - m10*m22*m33;
   double t11 = m02*m23*m30 - m03*m22*m30 + m03*m20*m32 - m00*m23*m32 - m02*m20*m33    + m00*m22*m33;
   double t12 = m03*m12*m30 - m02*m13*m30 - m03*m10*m32 + m00*m13*m32 + m02*m10*m33    - m00*m12*m33;
   double t13 = m02*m13*m20 - m03*m12*m20 + m03*m10*m22 - m00*m13*m22 - m02*m10*m23    + m00*m12*m23;
   double t20 = m11*m23*m30 - m13*m21*m30 + m13*m20*m31 - m10*m23*m31 - m11*m20*m33    + m10*m21*m33;
   double t21 = m03*m21*m30 - m01*m23*m30 - m03*m20*m31 + m00*m23*m31 + m01*m20*m33    - m00*m21*m33;
   double t22 = m01*m13*m30 - m03*m11*m30 + m03*m10*m31 - m00*m13*m31 - m01*m10*m33    + m00*m11*m33;
   double t23 = m03*m11*m20 - m01*m13*m20 - m03*m10*m21 + m00*m13*m21 + m01*m10*m23    - m00*m11*m23;
   double t30 = m12*m21*m30 - m11*m22*m30 - m12*m20*m31 + m10*m22*m31 + m11*m20*m32    - m10*m21*m32;
   double t31 = m01*m22*m30 - m02*m21*m30 + m02*m20*m31 - m00*m22*m31 - m01*m20*m32    + m00*m21*m32;
   double t32 = m02*m11*m30 - m01*m12*m30 - m02*m10*m31 + m00*m12*m31 + m01*m10*m32    - m00*m11*m32;
   double t33 = m01*m12*m20 - m02*m11*m20 + m02*m10*m21 - m00*m12*m21 - m01*m10*m22    + m00*m11*m22;
   m00 = t00;
   m01 = t01;
   m02 = t02;
   m03 = t03;
   m10 = t10;
   m11 = t11;
   m12 = t12;
   m13 = t13;
   m20 = t20;
   m21 = t21;
   m22 = t22;
   m23 = t23;
   m30 = t30;
   m31 = t31;
   m32 = t32;
   m33 = t33;
   scale(1/det);
   }
   
   /** for theory see:
   * http://www.euclideanspace.com/maths/algebra/matrix/arithmetic/index.htm
   */
   public void invertAffine() {
   double d = determinantAffine();
   if (d != 0.0) {
   double t00 = (m11*m22 - m12*m21) / d;
   double t01 = (m02*m21 - m01*m22) / d;
   double t02 = (m01*m12 - m02*m11) / d;
   double t10 = (m12*m20 - m10*m22) / d;
   double t11 = (m00*m22 - m02*m20) / d;
   double t12 = (m02*m10 - m00*m12) / d;
   double t20 = (m10*m21 - m11*m20) / d;
   double t21 = (m01*m20 - m00*m21) / d;
   double t22 = (m00*m11 - m01*m10) / d;
   m00 = t00;
   m01 = t01;
   m02 = t02;
   m10 = t10;
   m11 = t11;
   m12 = t12;
   m20 = t20;
   m21 = t21;
   m22 = t22;
   }
   m03 *= -1.0;
   m13 *= -1.0;
   m23 *= -1.0;
   }
   
   /** Calculates the determinant of this matrix
   * for theory see:
   * http://www.euclideanspace.com/maths/algebra/matrix/arithmetic/index.htm
   * @return the determinant of the matrix
   * */
   public double determinant() {
	   double value;
	   value =
	   m03 * m12 * m21 * m30-m02 * m13 * m21 * m30-m03 * m11 * m22 * m30+m01 * m13    * m22 * m30+
	   m02 * m11 * m23 * m30-m01 * m12 * m23 * m30-m03 * m12 * m20 * m31+m02 * m13    * m20 * m31+
	   m03 * m10 * m22 * m31-m00 * m13 * m22 * m31-m02 * m10 * m23 * m31+m00 * m12    * m23 * m31+
	   m03 * m11 * m20 * m32-m01 * m13 * m20 * m32-m03 * m10 * m21 * m32+m00 * m13    * m21 * m32+
	   m01 * m10 * m23 * m32-m00 * m11 * m23 * m32-m02 * m11 * m20 * m33+m01 * m12    * m20 * m33+
	   m02 * m10 * m21 * m33-m00 * m12 * m21 * m33-m01 * m10 * m22 * m33+m00 * m11    * m22 * m33;
	   return value;
   }/** calculates the transpose
   * for theory see:
   * http://www.euclideanspace.com/maths/algebra/matrix/arithmetic/index.htm
   * */
   public void transpose() {
	   double tmp = m01;
	   m01 = m10;
	   m10 = tmp; tmp = m02;
	   m02 = m20;
	   m20 = tmp; tmp = m03;
	   m03 = m30;
	   m30 = tmp; tmp = m12;
	   m12 = m21;
	   m21 = tmp; tmp = m13;
	   m13 = m31;
	   m31 = tmp; tmp = m23;
	   m23 = m32;
	   m32 = tmp;
   }
   
   /** calculates the affine determinant of this matrix.
   * for theory see:
   * http://www.euclideanspace.com/maths/algebra/matrix/arithmetic/index.htm
   * @return the determinant of the matrix
   * */
   public double determinantAffine() {
	   double value;
	   value = m00 * ( m11 * m22 - m21 * m12 );
	   value -= m01 * ( m10 * m22 - m20 * m12 );
	   value += m02 * ( m10 * m21 - m20 * m11 );
	   return value;
   }
  
   public Matrix4x4 testSetRotateXYZ(double angX,double angY,double angZ){
	   double c1 = Math.cos(angX);
	   double c2 = Math.cos(angY);
	   double c3 = Math.cos(angZ);
	   double s1 = Math.sin(angX);
	   double s2 = Math.sin(angY);
	   double s3 = Math.sin(angZ);
	   m00 = c2*c3;
	   m01 = -c2*s3;
	   m02 = s2;
	   m03 = 0;
	   m10 = c1*s3+c3*s1*s2;
	   m11 = c1*c3 - s1*s2*s3;
	   m12 = -c2*s1;
	   m13 = 0;
	   m20 = s1*s3-c1*c3*s2;
	   m21 = c1*s2*s3+c3*s1;
	   m22 = c1*c2;
	   m23 = 0;
	   m30 = 0;
	   m31 = 0;
	   m32 = 0;
	   m33 = 1;
	   return this;
   }
}