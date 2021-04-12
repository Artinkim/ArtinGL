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
import utilities.ObjReader;

public class Runner extends JPanel implements KeyListener {
	static Vector3 pos;

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
		Camera cam = new Camera();
		cam.position = new Vector3(0, 0, 1.2f);
		pos = new Vector3();
		// cam.viewMatrix.m[2][2] += 1.2;
		// cam.viewMatrix.m[1][2] += 0.1;
		cam.setPerspective(179.0f, (800f / 500f), 0.001f, 1000.0f);
		int mouseY = MouseInfo.getPointerInfo().getLocation().x;
		int mouseX = MouseInfo.getPointerInfo().getLocation().y;
		int x = mouseX;
		int y = mouseY;
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
			cam.updateView(0.001f * yaw, 0.001f * pitch, 0, pos);
//			System.out.println(cam.forwardVec);
//			System.out.println(cam.upVec);
//			System.out.println(cam.leftVec);
			pos = new Vector3();
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

			render.renderVertecies3D(arr, cam);
			// render.renderTriangle3D(new Vector3(1,1,-40),new Vector3(0,0,1),new
			// Vector3(2,0,1),cam);
			render.swapBuffers();

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
		// System.out.println(KeyEvent.VK_RIGHT + " " + key);
		if (key == KeyEvent.VK_LEFT) {
			pos.x = -0.01f;
		}
		if (key == KeyEvent.VK_RIGHT) {
			pos.x = 0.01f;
		}
		if (key == KeyEvent.VK_DOWN) {
			pos.z = 0.01f;
		}
		if (key == KeyEvent.VK_UP) {
			pos.z = -0.01f;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
