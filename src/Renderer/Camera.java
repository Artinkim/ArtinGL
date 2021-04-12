package Renderer;

import math.Matrix4;
import math.Quaternion;
import math.Vector3;

public class Camera {
	public Matrix4 viewMatrix;
	public Matrix4 projectionMatrix;
	public Quaternion orientation;

	public Camera() {
		viewMatrix = new Matrix4(1);
		projectionMatrix = new Matrix4(1);
		orientation = new Quaternion();
	}

	public void setPerspective(float fov, float aspect, float far, float near) {
		float scale = (float) (1 / (aspect * Math.tan(fov / 2)));
		projectionMatrix.m[0][0] = scale;
		projectionMatrix.m[1][1] = scale;
		projectionMatrix.m[2][2] = -far / (far - near);
		projectionMatrix.m[3][2] = -(far * near) / (far - near);
		projectionMatrix.m[2][3] = -1;
	}
	
	public void move(Vector3 vec) {
		viewMatrix.m[0][3] += vec.x;
		viewMatrix.m[1][3] += vec.y;
		viewMatrix.m[2][3] -= vec.z;
	}
}
