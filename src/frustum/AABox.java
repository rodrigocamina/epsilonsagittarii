package frustum;

import matematcbase.Vector3f;

public class AABox 
{


		public Vector3f corner;
		public float x,y,z;

		public AABox( Vector3f corner,  float x, float y, float z) {

			setBox(corner,x,y,z);
		}



		public AABox() {
			corner.x = 0; corner.y = 0; corner.z = 0;
			x = 1.0f;
			y = 1.0f;
			z = 1.0f;
			
		}


		public void setBox( Vector3f corner,  float x, float y, float z) {


			this.corner.copy(corner);

			if(x < 0.0){
				x = -x;
				this.corner.x -= x;
			}
			if (y < 0.0) {
				y = -y;
				this.corner.y -= y;
			}
			if (z < 0.0) {
				z = -z;
				this.corner.z -= z;
			}
			this.x = x;
			this.y = y;
			this.z = z;


		}



		public Vector3f getVertexP(Vector3f normal) {

			Vector3f res = corner;

			if (normal.x > 0)
				res.x += x;

			if (normal.y > 0)
				res.y += y;

			if (normal.z > 0)
				res.z += z;

			return(res);
		}



		public Vector3f getVertexN(Vector3f normal) {

			Vector3f res = corner;

			if (normal.x < 0)
				res.x += x;

			if (normal.y < 0)
				res.y += y;

			if (normal.z < 0)
				res.z += z;

			return(res);
		}

	};
