package matematcbase;

public class Vector3f {
	
	public float x,y,z;
	
	public Vector3f(){
		x = 0;
		y = 0;
		z = 0;
	}
			
	public Vector3f(float x,float y,float z) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f(Vector3f v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}
	
	public Vector3f add(Vector3f v){
		return new Vector3f(x + v.x, y + v.y, z + v.z);
	}
	
	public Vector3f subtract(Vector3f v){
		return new Vector3f(x - v.x, y - v.y, z - v.z);
	}
	
	public Vector3f multiply(Vector3f V2)
	{
		return new Vector3f (x * V2.x,  y * V2.y,  z * V2.z);
	}

	public Vector3f multiply(float f)
	{
		return new Vector3f (x * f,  y * f,  z * f);
	}
	
	public Vector3f multiply(double f)
	{
		return multiply((float)f);
	}
	
	public Vector3f invert(){
		return new Vector3f(-x, -y, -z);
	}
	
	// Functions
	public float dot(Vector3f v1)
	{
		return v1.x*x + v1.y*y + v1.z*z;
	}

	
	public Vector3f crossProduct( Vector3f V2 )
		{
		return new Vector3f(
			y * V2.z  -  z * V2.y,
			z * V2.x  -  x * V2.z,
			x * V2.y  -  y * V2.x 	);
		}

	// Return vector rotated by the 3x3 portion of matrix m
	// (provided because it's used by bbox.cpp in article 21)
	public Vector3f RotByMatrix( float m[] )
	{
	return new Vector3f( 
		x*m[0] + y*m[4] + z*m[8],
		x*m[1] + y*m[5] + z*m[9],
		x*m[2] + y*m[6] + z*m[10] );
 	}
	
	public Vector3f RotByMatrix( double m[] )
	{
	return new Vector3f( 
		x*(float)m[0] + y*(float)m[4] + z*(float)m[8],
		x*(float)m[1] + y*(float)m[5] + z*(float)m[9],
		x*(float)m[2] + y*(float)m[6] + z*(float)m[10] );
 	}

	// These require math.h for the sqrtf function
	public float magnitude( )
	{
		return (float)Math.sqrt( x*x + y*y + z*z );
	}

	public float distance(Vector3f V1)
	{
		return multiply(V1).magnitude();	
	}

	
	public void normalize()
		{
		float fMag = ( x*x + y*y + z*z );
		if (fMag == 0) {return;}

		float fMult = (float)(1.0f/Math.sqrt(fMag));            
		x *= fMult;
		y *= fMult;
		z *= fMult;
		return;
	}
	
	public float innerProduct(Vector3f v) {

		return (x * v.x + y * v.y + z * v.z);
	}
	
	public Vector3f set3P(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	public Vector3f copy(Vector3f v){
		return set3P(v.x, v.y, v.z);
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+","+z+")";
	}
}
