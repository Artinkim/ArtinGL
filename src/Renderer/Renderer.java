package Renderer;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import math.Matrix4;
import math.Vector2;
import math.Vector3;
import math.Vector4;

public class Renderer {
	public static final int BYTES_PER_PIXEL = 4;

	BufferedImage frontBuffer;
	byte[] backBuffer;
	float[] clearColor = { 0, 0, 0, 1 };

	int bytesPerRow;
	int totalImageSize;

	int width;
	int height;

	JFrame frame;
	JLabel label;

	public Renderer(int width, int height) {
		this.width = width;
		this.height = height;

		frontBuffer = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		frame = new JFrame("ArtinGraphics");
		label = new JLabel();

		frame.add(label);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		bytesPerRow = width * BYTES_PER_PIXEL;
		totalImageSize = width * height * BYTES_PER_PIXEL;
		backBuffer = new byte[totalImageSize];
	}

	public void renderVertecies2D(ArrayList<Vector3> arr) {
		for (int i = 0; i < arr.size(); i += 3) {
			renderTriangle2D(new Vector2(arr.get(i).x, arr.get(i).y), new Vector2(arr.get(i + 1).x, arr.get(i + 1).y),
					new Vector2(arr.get(i + 2).x, arr.get(i + 2).y));
		}
	}

	public void renderVertecies3D(ArrayList<Vector3> arr, Camera cam) {
		
		for (int i = 0; i < arr.size(); i += 3) {
			renderTriangle3D(arr.get(i), arr.get(i + 1), arr.get(i + 2), cam);
		}
	}

	public void renderFlatTopTriangle(Vector2 bottom, Vector2 topLeft, Vector2 topRight) {
		int rise = (int) (bottom.y - topLeft.y);
		int runLeft = (int) (topLeft.x - bottom.x);
		int runRight = (int) (topRight.x - bottom.x);
		float leftSlope = rise == 0 ? 0 : (float) runLeft / (float) rise;
		float rightSlope = rise == 0 ? 0 : (float) runRight / (float) rise;
		for (int i = (int) topLeft.y; i < bottom.y; i++) {
			int start = (int) (topLeft.x - leftSlope * (i - topLeft.y));
			int end = (int) (topRight.x - rightSlope * (i - topLeft.y));
			for (int j = start; j < end; j++) {
				renderPixel(j, i);
			}
		}
	}

	public void renderFlatBottomTriangle(Vector2 top, Vector2 bottomLeft, Vector2 bottomRight) {
		int rise = (int) (bottomLeft.y - top.y);
		int runLeft = (int) (top.x - bottomLeft.x);
		int runRight = (int) (top.x - bottomRight.x);
		float leftSlope = rise == 0 ? 0 : (float) runLeft / (float) rise;
		float rightSlope = rise == 0 ? 0 : (float) runRight / (float) rise;

		for (int i = (int) top.y; i < bottomLeft.y; i++) {
			int start = (int) (top.x - leftSlope * (i - top.y));
			int end = (int) (top.x - rightSlope * (i - top.y));

			for (int j = start; j < end; j++) {
				renderPixel(j, i);
			}
		}
	}

	public void renderTriangle2D(Vector2 vec1, Vector2 vec2, Vector2 vec3) {
		Vector2 v1 = new Vector2((int) ((vec1.x + 1.0f) * 0.5f * width), (int) ((vec1.y + 1.0f) * 0.5f * height));
		Vector2 v2 = new Vector2((int) ((vec2.x + 1.0f) * 0.5f * width), (int) ((vec2.y + 1.0f) * 0.5f * height));
		Vector2 v3 = new Vector2((int) ((vec3.x + 1.0f) * 0.5f * width), (int) ((vec3.y + 1.0f) * 0.5f * height));
//		Vector2 v1 = new Vector2((int) (vec1.x * width), (int) (vec1.y * height));
//		Vector2 v2 = new Vector2((int) (vec2.x * width), (int) (vec2.y * height));
//		Vector2 v3 = new Vector2((int) (vec3.x * width), (int) (vec3.y * height));
//		Vector2 v1 = new Vector2((int) vec1.x, (int) vec1.y);
//		Vector2 v2 = new Vector2((int) vec2.x, (int) vec2.y);
//		Vector2 v3 = new Vector2((int) vec3.x, (int) vec3.y);
		if (v1.y == v2.y) {
			if (v3.y > v1.y) {
				if (v1.x < v2.x) {
					renderFlatTopTriangle(v3, v1, v2);
				} else {
					renderFlatTopTriangle(v3, v2, v1);
				}

			} else {
				if (v1.x < v2.x) {
					renderFlatBottomTriangle(v3, v1, v2);
				} else {
					renderFlatBottomTriangle(v3, v2, v1);
				}
			}
		} else if (v2.y == v3.y) {
			if (v1.y > v2.y) {
				if (v2.x < v3.x) {
					renderFlatTopTriangle(v1, v2, v3);
				} else {
					renderFlatTopTriangle(v1, v3, v2);
				}
			} else {

				if (v2.x < v3.x) {
					renderFlatBottomTriangle(v1, v2, v3);
				} else {
					renderFlatBottomTriangle(v1, v3, v2);
				}
			}
		} else if (v1.y == v3.y) {
			if (v2.y > v1.y) {
				if (v1.x < v3.x) {
					renderFlatTopTriangle(v2, v1, v3);
				} else {
					renderFlatTopTriangle(v2, v3, v1);
				}
			} else {
				if (v1.x < v3.x) {
					renderFlatBottomTriangle(v2, v1, v3);
				} else {
					renderFlatBottomTriangle(v2, v3, v1);
				}
			}
		} else {
			Vector2 top;
			Vector2 bot;
			Vector2 mid;
			Vector2 midIntersect;
			if (v1.y < v2.y && v1.y < v3.y) {
				top = v1;
				if (v2.y < v3.y) {
					mid = v2;
					bot = v3;
				} else {
					mid = v3;
					bot = v2;
				}
			} else if (v2.y < v3.y && v2.y < v1.y) {
				top = v2;
				if (v1.y < v3.y) {
					mid = v1;
					bot = v3;
				} else {
					mid = v3;
					bot = v1;
				}
			} else {
				top = v3;
				if (v1.y < v2.y) {
					mid = v1;
					bot = v2;
				} else {
					mid = v2;
					bot = v1;
				}
			}
			int rise = (int) (bot.y - top.y);
			int run = (int) (top.x - bot.x);
			float slope = rise == 0 ? 0 : (float) run / (float) rise;
			int x = (int) ((slope * (bot.y - mid.y)) + bot.x);
			midIntersect = new Vector2(x, mid.y);

			if (mid.x < midIntersect.x) {
				renderFlatBottomTriangle(top, mid, midIntersect);
				renderFlatTopTriangle(bot, mid, midIntersect);
			} else {
				renderFlatBottomTriangle(top, midIntersect, mid);
				renderFlatTopTriangle(bot, midIntersect, mid);
			}
		}
	}

	public void renderTriangle3D(Vector3 vec1, Vector3 vec2, Vector3 vec3, Camera cam) {
		Vector4 vec1V4 = new Vector4(vec1.x, vec1.y, vec1.z, 1.0f);
		Vector4 vec2V4 = new Vector4(vec2.x, vec2.y, vec2.z, 1.0f);
		Vector4 vec3V4 = new Vector4(vec3.x, vec3.y, vec3.z, 1.0f);
		Matrix4 projView = Matrix4.multiply(cam.projectionMatrix, cam.viewMatrix);

//		System.out.println(vec1V4);
//		System.out.println(cam.viewMatrix.m[2][3]);
//		for (int i = 0; i < 4; i++) {
//			for (int j = 0; j < 4; j++) {
//				System.out.print(cam.viewMatrix.m[i][j] + " ");
//			}
//			System.out.println();
//		}
//		System.out.println();
//		for (int i = 0; i < 4; i++) {
//			for (int j = 0; j < 4; j++) {
//				System.out.print(cam.projectionMatrix.m[i][j] + " ");
//			}
//			System.out.println();
//		}
//		System.out.println();
//		
//		for (int i = 0; i < 4; i++) {
//			for (int j = 0; j < 4; j++) {
//				System.out.print(projView.m[i][j] + " ");
//			}
//			System.out.println();
//		}
		Vector4 vec1Final = Matrix4.multiply(projView, vec1V4);
		Vector4 vec2Final = Matrix4.multiply(projView, vec2V4);
		Vector4 vec3Final = Matrix4.multiply(projView, vec3V4);
//		System.out.println(vec1Final);
		if (vec1Final.z > 0.0001f && vec1Final.z < 1000.0f && vec2Final.z > 0.0001f && vec2Final.z < 1000.0f
				&& vec3Final.z > 0.0001f && vec3Final.z < 1000.0f) {
			renderTriangle2D(new Vector2(vec1Final.x / vec1Final.z, vec1Final.y / vec1Final.z),
					new Vector2(vec2Final.x / vec2Final.z, vec2Final.y / vec2Final.z),
					new Vector2(vec3Final.x / vec3Final.z, vec3Final.y / vec3Final.z));
//			renderTriangle2D(new Vector2(vec1Final.x, vec1Final.y), new Vector2(vec2Final.x, vec2Final.y),
//					new Vector2(vec3Final.x, vec3Final.y));
		}
	}

	public void clearBackBuffer() {
		for (int i = 0; i < totalImageSize; i += 4) {
			backBuffer[i] = (byte) (clearColor[3] * 255.0);
			backBuffer[i + 1] = (byte) (clearColor[2] * 255.0);
			backBuffer[i + 2] = (byte) (clearColor[1] * 255.0);
			backBuffer[i + 3] = (byte) (clearColor[0] * 255.0);
		}
	}

	public void swapBuffers() {
		byte[] imgData = ((DataBufferByte) frontBuffer.getRaster().getDataBuffer()).getData();
		System.arraycopy(backBuffer, 0, imgData, 0, backBuffer.length);
		label.setIcon(new ImageIcon(frontBuffer));
		frame.pack();
	}

	public void setClearColor(float r, float g, float b, float a) {
		clearColor[0] = r;
		clearColor[1] = g;
		clearColor[2] = b;
		clearColor[3] = a;
	}

	public void renderPixel(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return;
		Vector4 color = new Vector4(0.5f, 0f, 0.5f, 1f);
		// Vector4 color = new Vector4((float) Math.random(), (float) Math.random(),
		// (float) Math.random(), 1f);
		int index = (y * bytesPerRow) + (x * BYTES_PER_PIXEL);
		backBuffer[index] = (byte) (color.w * 255);
		backBuffer[index + 1] = (byte) (color.z * 255);
		backBuffer[index + 2] = (byte) (color.y * 255);
		backBuffer[index + 3] = (byte) (color.x * 255);
	}

	public void renderPixel(int x, int y, Vector4 color) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return;
		int index = (y * bytesPerRow) + (x * BYTES_PER_PIXEL);
		backBuffer[index] = (byte) (color.w * 255);
		backBuffer[index + 1] = (byte) (color.z * 255);
		backBuffer[index + 2] = (byte) (color.y * 255);
		backBuffer[index + 3] = (byte) (color.x * 255);
	}
}
