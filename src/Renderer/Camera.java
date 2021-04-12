package Renderer;

import java.awt.event.KeyEvent;

import math.Matrix4;
import math.Quaternion;
import math.Vector3;


public class Camera {
	public Matrix4 viewMatrix;
	public Matrix4 projectionMatrix;
	public Quaternion orientation;
	public Vector3 position;
	public Vector3 rightVec;
	public Vector3 upVec;
	public Vector3 forwardVec;

	public Camera() {
		viewMatrix = new Matrix4(1);
		projectionMatrix = new Matrix4(1);
		orientation = new Quaternion();
		position = new Vector3();
		rightVec = new Vector3(1, 0, 0);
		upVec = new Vector3(0, 1, 0);
		forwardVec = new Vector3(0, 0, 1);
	}

	public void setPerspective(float fov, float aspect, float far, float near) {
		float scale = (float) (1 / (aspect * Math.tan(fov / 2)));
		projectionMatrix.m[0][0] = scale;
		projectionMatrix.m[1][1] = scale;
		projectionMatrix.m[2][2] = -far / (far - near);
		projectionMatrix.m[3][2] = -(far * near) / (far - near);
		projectionMatrix.m[2][3] = -1;
	}

	public void move(Vector3 moveVec) {
		position.x += forwardVec.z * moveVec.x;
		position.y += forwardVec.y * moveVec.x;
		position.z += forwardVec.x * moveVec.x;

		position.x += upVec.z * moveVec.y;
		position.y += upVec.y * moveVec.y;
		position.z += upVec.x * moveVec.y;

		position.x += rightVec.z * moveVec.z;
		position.y += rightVec.y * moveVec.z;
		position.z += rightVec.x * moveVec.z;
	}
//
//	public void rotate(float pitch, float yaw, float roll) {
//		orientation.rotate(forwardVec, yaw);
//		orientation.rotate(upVec, pitch);
//		orientation.rotate(leftVec, roll);
//		viewMatrix = new Matrix4(1);
//		viewMatrix.m[0][3] = position.x;
//		viewMatrix.m[1][3] = position.y;
//		viewMatrix.m[2][3] = position.z;
//		viewMatrix = Matrix4.multiply(viewMatrix, orientation.toMatrix4());
//		forwardVec = new Vector3(-viewMatrix.m[0][0], -viewMatrix.m[1][0], -viewMatrix.m[2][0]);
//		upVec = new Vector3(viewMatrix.m[0][1], viewMatrix.m[1][1], viewMatrix.m[2][1]);
//		leftVec = new Vector3(viewMatrix.m[0][2], viewMatrix.m[1][2], viewMatrix.m[2][2]);
//	}

	public void updateView(float pitch, float yaw, float roll, Vector3 moveVec) {

		orientation.rotate(forwardVec, roll);
		orientation.rotate(upVec, yaw);
		orientation.rotate(rightVec, pitch);
		viewMatrix = new Matrix4(1);
		move(moveVec);
		viewMatrix.m[0][3] = position.x;
		viewMatrix.m[1][3] = position.y;
		viewMatrix.m[2][3] = position.z;
		viewMatrix = Matrix4.multiply(viewMatrix, orientation.toMatrix4());
		rightVec = new Vector3(viewMatrix.m[0][0], viewMatrix.m[1][0], viewMatrix.m[2][0]);
		upVec = new Vector3(viewMatrix.m[0][1], viewMatrix.m[1][1], viewMatrix.m[2][1]);
		forwardVec = new Vector3(-viewMatrix.m[0][2], -viewMatrix.m[1][2], -viewMatrix.m[2][2]);
	}

}
