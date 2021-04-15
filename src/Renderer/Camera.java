package Renderer;

import java.awt.event.KeyEvent;

import math.Matrix4;
import math.Quaternion;
import math.Vector3;
import math.Vector4;

public class Camera {
	public Matrix4 viewMatrix;
	public Matrix4 projectionMatrix;
	public Quaternion orientation;
	public Vector3 position;
	public Vector3 rightVec;
	public Vector3 upVec;
	public Vector3 forwardVec;
	public float dist = 0.3f;

	public Camera() {
		viewMatrix = new Matrix4(1);
		projectionMatrix = new Matrix4(1);
		orientation = new Quaternion();
		position = new Vector3(0, 0, 0);
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

	public void setPerspectiveProjection(float fov, float aspect, float near, float far) {
		projectionMatrix = new Matrix4();
		float tanHFov = (float) (Math.tan(fov / 2.0f));
		projectionMatrix.m[0][0] = 1.0f / (aspect * tanHFov);
		projectionMatrix.m[1][1] = 1.0f / tanHFov;
		projectionMatrix.m[2][2] = -(far + near) / (far - near);
		projectionMatrix.m[2][3] = -1.0f;
		projectionMatrix.m[3][2] = -(2.0f * far * near) / (far - near);
	}

	public void move(Vector3 moveVec) {
		position.x += rightVec.x * moveVec.x;
		position.y += rightVec.y * moveVec.x;
		position.z += rightVec.z * moveVec.x;

		position.x += upVec.x * moveVec.y;
		position.y += upVec.y * moveVec.y;
		position.z += upVec.z * moveVec.y;

		position.x += forwardVec.x * moveVec.z;
		position.y += forwardVec.y * moveVec.z;
		position.z += forwardVec.z * moveVec.z;

	}

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
	public void moveUpdate(Vector3 moveVec) {
		move(moveVec);
		viewMatrix.m[0][3] = 0.0f;
		viewMatrix.m[1][3] = 0.0f;
		viewMatrix.m[2][3] = 0.0f;
		Vector4 positionVec4 = new Vector4(position);
		Vector4 positionTransformedVec4 = Matrix4.multiply(viewMatrix, positionVec4);
		viewMatrix.m[0][3] = positionTransformedVec4.x;
		viewMatrix.m[1][3] = positionTransformedVec4.y;
		viewMatrix.m[2][3] = positionTransformedVec4.z;
	}

	public void rotateAround2(float pitch, float yaw) {
		move(new Vector3(0, 0, dist));

		// move(new Vector3(dist, 0, 0));
//		position.x += forwardVec.x * dist;
//		position.y += forwardVec.y * dist;
//		position.z += forwardVec.z * dist;
		// Vector3 v = new Vector3(-forwardVec.x * dist, -forwardVec.y * dist,
		// -forwardVec.z * dist);
		orientation.rotate(upVec, yaw);
		orientation.rotate(rightVec, pitch);
		viewMatrix = new Matrix4(1);
		// Still using old camera vectors to move
		viewMatrix.m[0][3] = position.x;
		viewMatrix.m[1][3] = position.y;
		viewMatrix.m[2][3] = position.z;
		viewMatrix = Matrix4.multiply(viewMatrix, orientation.toMatrix4());
		rightVec = new Vector3(viewMatrix.m[0][0], viewMatrix.m[0][1], viewMatrix.m[0][2]);
		upVec = new Vector3(viewMatrix.m[1][0], viewMatrix.m[1][1], viewMatrix.m[1][2]);
		forwardVec = new Vector3(-viewMatrix.m[2][0], -viewMatrix.m[2][1], -viewMatrix.m[2][2]);
		moveUpdate(new Vector3(0, 0, -dist));
	}

	public void rotateAround(float pitch, float yaw, Vector3 moveVec) {
		// float dist = 0.3f;
		Vector3 relativeVector = new Vector3(position.x - moveVec.x, position.x - moveVec.y, position.x - moveVec.z);
		// System.out.println(position);
		moveUpdate(Vector3.scale(relativeVector, -1));
		// System.out.println(position);
		orientation.rotate(upVec, yaw);
		orientation.rotate(rightVec, pitch);
		viewMatrix = orientation.toMatrix4();

		rightVec = new Vector3(viewMatrix.m[0][0], viewMatrix.m[0][1], viewMatrix.m[0][2]);
		upVec = new Vector3(viewMatrix.m[1][0], viewMatrix.m[1][1], viewMatrix.m[1][2]);
		forwardVec = new Vector3(-viewMatrix.m[2][0], -viewMatrix.m[2][1], -viewMatrix.m[2][2]);
		moveUpdate(relativeVector);
		// moveUpdate(relativeVector);
		// System.out.println(position);
//		viewMatrix = Matrix4.multiply(viewMatrix, orientation.toMatrix4());
//		rightVec = new Vector3(viewMatrix.m[0][0], viewMatrix.m[1][0], viewMatrix.m[2][0]);
//		upVec = new Vector3(viewMatrix.m[0][1], viewMatrix.m[1][1], viewMatrix.m[2][1]);
//		forwardVec = new Vector3(-viewMatrix.m[0][2], -viewMatrix.m[1][2], -viewMatrix.m[2][2]);
//		move(new Vector3(dist, 0, 0));
//		viewMatrix = new Matrix4(1);
//		updateView(pitch, yaw, 0.0f, new Vector3(0, 0, 0));
//		System.out.println(forwardVec);
//		Quaternion rotation = new Quaternion();
//		rotation.rotate(upVec, yaw);
//		rotation.rotate(rightVec, pitch);
//		Vector4 forwardVec4 = new Vector4(Vector3.mul(new Vector3(dist, dist, dist), forwardVec));
//		forwardVec4 = Matrix4.multiply(rotation.toMatrix4(), forwardVec4);
//		forwardVec = new Vector3(forwardVec4);
//		System.out.println(forwardVec);
	}

	public void updateView(float pitch, float yaw, float roll, Vector3 moveVec) {
		// System.out.println("11111111111111");
		orientation.rotate(forwardVec, roll);// Does not update camera vectors
		orientation.rotate(upVec, yaw);
		orientation.rotate(rightVec, pitch);

		viewMatrix = new Matrix4(1);
		move(moveVec); // Still using old camera vectors to move
		viewMatrix.m[0][3] = position.x;
		viewMatrix.m[1][3] = position.y;
		viewMatrix.m[2][3] = position.z;
		viewMatrix = Matrix4.multiply(viewMatrix, orientation.toMatrix4());
//		rightVec = new Vector3(viewMatrix.m[0][0], viewMatrix.m[1][0], viewMatrix.m[2][0]);
//		upVec = new Vector3(viewMatrix.m[0][1], viewMatrix.m[1][1], viewMatrix.m[2][1]);
//		forwardVec = new Vector3(-viewMatrix.m[0][2], -viewMatrix.m[1][2], -viewMatrix.m[2][2]);
//		
		rightVec = new Vector3(viewMatrix.m[0][0], viewMatrix.m[0][1], viewMatrix.m[0][2]);
		upVec = new Vector3(viewMatrix.m[1][0], viewMatrix.m[1][1], viewMatrix.m[1][2]);
		forwardVec = new Vector3(-viewMatrix.m[2][0], -viewMatrix.m[2][1], -viewMatrix.m[2][2]);

//		rightVec = new Vector3(viewMatrix.m[2][0], viewMatrix.m[1][0], viewMatrix.m[0][0]);
//		upVec = new Vector3(viewMatrix.m[2][1], viewMatrix.m[1][1], viewMatrix.m[0][1]);
//		forwardVec = new Vector3(-viewMatrix.m[2][2], -viewMatrix.m[1][2], -viewMatrix.m[0][2]);

		// System.out.println(viewMatrix.m[0][3] + " " + viewMatrix.m[1][3] + " " +
		// viewMatrix.m[2][3]);

//		System.out.println("dddd");
//		System.out.println(Vector3.mul(rightVec, rightVec));
//		Vector3 v1 = Vector3.mul(rightVec, upVec);
//		Vector3 v2 = Vector3.mul(forwardVec, upVec);
//		Vector3 v3 = Vector3.mul(rightVec, forwardVec);
//		System.out.println(v1.x + v1.y + v1.z);
//		System.out.println(v2.x + v2.y + v2.z);
//		System.out.println(v3.x + v3.y + v3.z);

		// System.out.println(rightVec.length());
		// System.out.println(upVec.length());
		// System.out.println(forwardVec.length());

//		rightVec.normalize();
//		upVec.normalize();
//		forwardVec.normalize();
	}

	public void updateView2(float pitch, float yaw, float roll, Vector3 moveVec) {

		// System.out.println("22222222222222");
		orientation.rotate(forwardVec, roll); // Does not update camera vectors
		orientation.rotate(upVec, yaw);
		orientation.rotate(rightVec, pitch);

		move(moveVec); // Still using old camera vectors to move
		Vector4 positionVec4 = new Vector4(position);

		viewMatrix = orientation.toMatrix4();
		Vector4 positionTransformedVec4 = Matrix4.multiply(viewMatrix, positionVec4);
		viewMatrix.m[0][3] = positionTransformedVec4.x;
		viewMatrix.m[1][3] = positionTransformedVec4.y;
		viewMatrix.m[2][3] = positionTransformedVec4.z;

		rightVec = new Vector3(viewMatrix.m[0][0], viewMatrix.m[0][1], viewMatrix.m[0][2]);
		upVec = new Vector3(viewMatrix.m[1][0], viewMatrix.m[1][1], viewMatrix.m[1][2]);
		forwardVec = new Vector3(-viewMatrix.m[2][0], -viewMatrix.m[2][1], -viewMatrix.m[2][2]);
//		rightVec = new Vector3(viewMatrix.m[0][0], viewMatrix.m[1][0], viewMatrix.m[2][0]);
//		upVec = new Vector3(viewMatrix.m[0][1], viewMatrix.m[1][1], viewMatrix.m[2][1]);
//		forwardVec = new Vector3(-viewMatrix.m[0][2], -viewMatrix.m[1][2], -viewMatrix.m[2][2]);
		// System.out.println(viewMatrix.m[0][3] + " " + viewMatrix.m[1][3] + " " +
		// viewMatrix.m[2][3]);
//		rightVec.normalize();
//		upVec.normalize();
//		forwardVec.normalize();
	}

}
