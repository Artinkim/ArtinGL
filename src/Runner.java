import java.awt.MouseInfo;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Renderer.Camera;
import Renderer.Renderer;
import math.Matrix4;
import math.Quaternion;
import math.Vector2;
import math.Vector3;
import math.Vector4;
import utilities.ObjReader;

public class Runner extends JPanel implements KeyListener {
	static Vector3 pos;
	static boolean move;
	static boolean m1;
	static float dist = 0.3f;
	static boolean set = false;

	public Runner() {
		this.addKeyListener(this);
	}

	public static void main(String[] args) throws InterruptedException {
		Runner n = new Runner();
		TextField textField = new TextField();

		textField.addKeyListener(n);

		JFrame jframe = new JFrame();

		jframe.add(textField);

		jframe.setSize(400, 350);

		jframe.setVisible(true);
		Renderer render = new Renderer(1000, 1000);
		Vector2 v1 = new Vector2(0.5f, 0.6f);
		Vector2 v2 = new Vector2(0.7f, .8f);
		Vector2 v3 = new Vector2(0.8f, 0.5f);
		ObjReader or = new ObjReader();
		render.clearBackBuffer();
		ArrayList<Vector3> arr = or
				.getVerticesFromObjFile("C:\\Users\\Artin\\eclipse-workspace\\ArtinGL\\src\\artin.obj");
//		System.out.println(arr.size());
//		for(int i = 0;i<arr.size();i++) {
//			System.out.println(arr.get(i));
//		}
//		render.clearBackBuffer();

		pos = new Vector3();
		// cam.viewMatrix.m[2][2] += 1.2;
		// cam.viewMatrix.m[1][2] += 0.1;
		Camera cam = new Camera();
		cam.position = new Vector3(0, 0, 1.2f);
		cam.setPerspective(179.0f, (800f / 500f), 0.001f, 1000.0f);
		int mouseY = MouseInfo.getPointerInfo().getLocation().x;
		int mouseX = MouseInfo.getPointerInfo().getLocation().y;
		int x = mouseX;
		int y = mouseY;
		// cam.updateView(0.1f, 0.1f, 0, pos);
		while (true) {
			// cam.move(new Vector3(0, 0, 1));
			// cam.viewMatrix.m[2][3] += 0.001;

			mouseY = MouseInfo.getPointerInfo().getLocation().x;
			mouseX = MouseInfo.getPointerInfo().getLocation().y;
			int yaw = (mouseX - x);
			int pitch = -(mouseY - y);
			x = mouseX;
			y = mouseY;
			float a = (float) (Math.cos(yaw) * Math.cos(pitch));
			float b = (float) Math.sin(pitch);
			float c = (float) (Math.sin(yaw) * Math.cos(pitch));
			// System.out.println(pitch + " " + yaw);
			// cam.viewMatrix.m[0][2] = a;
			// cam.viewMatrix.m[1][2] = b;
			// cam.viewMatrix.m[2][2] = c;
			// cam.orientation = new Quaternion();
			// System.out.println(pos.x + " " + pos.y + " " + pos.z);
			// System.out.println(move);
			if (set) {
				cam.dist = 0;
				set = false;
			} else {
				cam.dist = dist;
				if (move) {
					cam.moveUpdate(new Vector3(0.001f * pitch, 0.001f * yaw, 0));
				} else if (m1) {
//					cam.moveUpdate(new Vector3(0, 0, dist));
//					render.clearBackBuffer();
//					render.renderVertecies3D(arr, cam);
//					render.swapBuffers();
//					System.out.println("showing move");
//					//Thread.sleep(2000);
//					// move(new Vector3(dist, 0, 0));
////					position.x += forwardVec.x * dist;
////					position.y += forwardVec.y * dist;
////					position.z += forwardVec.z * dist;
//					// Vector3 v = new Vector3(-forwardVec.x * dist, -forwardVec.y * dist,
//					// -forwardVec.z * dist);
//					cam.orientation.rotate(cam.upVec, 0.001f);
//					cam.orientation.rotate(cam.rightVec, 0.001f);
//					cam.viewMatrix = new Matrix4(1);
//					// Still using old camera vectors to move
//					cam.viewMatrix.m[0][3] = cam.position.x;
//					cam.viewMatrix.m[1][3] = cam.position.y;
//					cam.viewMatrix.m[2][3] = cam.position.z;
//					cam.viewMatrix = Matrix4.multiply(cam.viewMatrix, cam.orientation.toMatrix4());
//					cam.rightVec = new Vector3(cam.viewMatrix.m[0][0], cam.viewMatrix.m[0][1], cam.viewMatrix.m[0][2]);
//					cam.upVec = new Vector3(cam.viewMatrix.m[1][0], cam.viewMatrix.m[1][1], cam.viewMatrix.m[1][2]);
//					cam.forwardVec = new Vector3(-cam.viewMatrix.m[2][0], -cam.viewMatrix.m[2][1],
//							-cam.viewMatrix.m[2][2]);
//					render.clearBackBuffer();
//					render.renderVertecies3D(arr, cam);
//					render.swapBuffers();
//					System.out.println("showing rotate");
//					//Thread.sleep(2000);
//					cam.moveUpdate(new Vector3(0, 0, -dist));
//					render.clearBackBuffer();
//					render.renderVertecies3D(arr, cam);
//					render.swapBuffers();
//					System.out.println("showing final");
//					//Thread.sleep(2000);

					// System.out.println(cam.position);
					cam.rotateAround2(0.01f * yaw, 0.01f * pitch);// new Vector3(-1.66f,
					// -0.49f,
					// 0.72f)
				} else {
					cam.updateView(0.001f * yaw, 0.001f * pitch, (pitch + yaw) * 0.001f, pos);
					pos = new Vector3();
				}
			}
			// cam.moveUpdate(pos);
//			System.out.println(cam.forwardVec);
//			System.out.println(cam.upVec);
//			System.out.println(cam.leftVec);

//			l = new Vector3(cam.viewMatrix.m[0][0], cam.viewMatrix.m[1][0], cam.viewMatrix.m[2][0]);
//			r = new Vector3(cam.viewMatrix.m[0][1], cam.viewMatrix.m[1][1], cam.viewMatrix.m[2][1]);
//			k = new Vector3(cam.viewMatrix.m[0][2], cam.viewMatrix.m[1][2], cam.viewMatrix.m[2][2]);
//			cam.orientation.rotate(l, 0.001f * (-yaw));
//			cam.orientation.rotate(r, 0.001f * (pitch));
////			cam.orientation.rotate(new Vector3(cam.viewMatrix.m[0][1], cam.viewMatrix.m[1][1], cam.viewMatrix.m[2][1]),
////					0.001f * pitch);
//			// cam.orientation.normalize();
//			// System.out.println(cam.viewMatrix);
//			cam.viewMatrix = new Matrix4(1);
//
//			// System.out.println(pos.z);
//			// pos.z += l.x / 100;
//
//			cam.viewMatrix = Matrix4.multiply(cam.viewMatrix, cam.orientation.toMatrix4());
//			cam.move(pos);
			// System.out.println(cam.viewMatrix);
			// cam.viewMatrix.m[1][3] += 0.001;
			// System.out.println(cam.position);
			// render.renderTriangle3D(new Vector3(0, 0, 0f), new Vector3(50.0f, 50.0f,
			// 0.0f),
			// new Vector3(50.0f, 100.0f, 1.0f), cam);
			Renderer.color = new Vector4(1f, 0f, 0f, 1f);
			render.renderTriangle3D(new Vector3(0, 0, 0f), new Vector3(30.0f, 0.0f, 0.0f),
					new Vector3(0.2f, 0.2f, 0.2f), cam);
			Renderer.color = new Vector4(0f, 0f, 1f, 1f);
			render.renderTriangle3D(new Vector3(0, 0, 0f), new Vector3(0.0f, 30.0f, 0.0f),
					new Vector3(0.2f, 0.2f, 0.2f), cam);
			Renderer.color = new Vector4(0f, 1f, 0f, 1f);
			render.renderTriangle3D(new Vector3(0, 0, 0f), new Vector3(0.0f, 0.0f, 30.0f),
					new Vector3(0.2f, 0.2f, 0.2f), cam);

			Renderer.color = new Vector4(0.5f, 0.75f, 0.5f, 1f);
			render.renderTriangle3D(new Vector3(0, 0, 0f), Vector3.scale(cam.upVec, 10), new Vector3(0.2f, 0.2f, 0.2f),
					cam);
			Renderer.color = new Vector4(0.75f, 0.5f, 0.5f, 1f);
			render.renderTriangle3D(new Vector3(0, 0, 0f), Vector3.scale(cam.rightVec, 10),
					new Vector3(0.2f, 0.2f, 0.2f), cam);
			Renderer.color = new Vector4(0.5f, 0.5f, 0.75f, 1f);
			render.renderTriangle3D(new Vector3(0, 0, 0f), Vector3.scale(cam.forwardVec, 100),
					new Vector3(0.2f, 0.2f, 0.2f), cam);
			Renderer.color = new Vector4(0.5f, 0.5f, 0.1f, 1f);
//			Renderer.color = new Vector4(0f, 0f, 1f, 1f);
//			render.renderTriangle3D(new Vector3(0, 0, 0f), new Vector3(0.0f, 30.0f, 0.0f),
//					new Vector3(2.0f, 30.0f, 4.0f), cam);
//			Renderer.color = new Vector4(0f, 1f, 0f, 1f);
//			render.renderTriangle3D(new Vector3(0, 0, 0f), new Vector3(0.0f, 0.0f, 30.0f),
//					new Vector3(2.0f, 4.0f, 30.0f), cam);
//			Renderer.color = new Vector4(0.5f, 0f, 0.5f, 1f);
			render.renderVertecies3D(arr, cam);
			render.swapBuffers();
			// render.renderTriangle3D(new Vector3(1,1,-40),new Vector3(0,0,1),new
			// Vector3(2,0,1),cam);

			render.clearBackBuffer();
		}

		// render.renderTriangle2D(v1, v2, v3);

//		float z = 1.3f;
//		float x = 0;
//		for(int i = 0;i<100000;i++) {
//			z+=0.01f;
//			x+=0.00f;
//			System.out.println(z);
//			render.renderTriangle2D(new Vector2((v1.x+x)/z, v1.y/z), new Vector2((v2.x+x)/z, v2.y/z), new Vector2((v3.x+x)/z, v3.y/z));
//			render.swapBuffers();
//			Thread.sleep(10);
//			render.clearBackBuffer();
//		}

//		render.renderFlatTopTriangle(bottom, tl, tr);
//		render.renderFlatBottomTriangle(top, tl, tr);
//		for (int i = (int) tl.x; i < ((int) tl.x) + 5; i++) {
//			render.renderPixel(i, (int) tl.y, new Vector4(255, 0, 155, 1));
//		}
//		for (int i = (int) bottom.x; i < ((int) bottom.x) + 5; i++) {
//			render.renderPixel(i, (int) bottom.y, new Vector4(255, 0, 155, 1));
//		}
//		for (int i = (int) tr.x; i < ((int) tr.x) + 5; i++) {
//			render.renderPixel(i, (int) tr.y, new Vector4(255, 0, 155, 1));
//		}
//		for (int i = (int) top.x; i < ((int) top.x) + 5; i++) {
//			render.renderPixel(i, (int) top.y, new Vector4(255, 0, 155, 1));
//		}

//		render.renderPixel((int) bottom.x, (int) bottom.y, new Vector4(255, 0, 155, 1));
//		render.renderPixel((int) tl.x, (int) tl.y, new Vector4(255, 0, 255, 1));
//		render.renderPixel((int) tr.x, (int) tr.y, new Vector4(255, 0, 255, 1));

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		System.out.println(key);
		// System.out.println(KeyEvent.VK_RIGHT + " " + key);
		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_6) {
			pos.x = 0.01f;
		}
		if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_5) {
			pos.x = -0.01f;
		}
		if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_3) {
			pos.z = -0.01f;
		}
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_4) {
			pos.z = 0.01f;
		}
		if (key == KeyEvent.VK_1) {
			pos.y = 0.01f;
		}
		if (key == KeyEvent.VK_2) {
			pos.y = -0.01f;
		}

		if (key == KeyEvent.VK_SHIFT) {
			move = true;
		}
		if (key == 17) {
			m1 = true;
		}
		if (key == 61) {
			dist -= 0.1f;
			pos.z += 0.1f;
			System.out.println(pos);
		}
		if (key == 45) {
			dist += 0.1f;
			pos.z -= 0.1f;
		}
		if (key == 84) {
			set = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_SHIFT) {
			move = false;
		}
		if (key == 17) {
			m1 = false;
		}
	}
}
