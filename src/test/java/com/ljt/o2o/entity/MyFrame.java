package com.ljt.o2o.entity;

import java.awt.Graphics;
import java.awt.HeadlessException;

import javax.swing.JFrame;

public class MyFrame extends JFrame{

	public MyFrame(String title) throws HeadlessException {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 300);
		this.setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		String msg = "this is my rule";
		g.drawString(msg, 100, 100);
	}

	public static void main(String[] args) {
		MyFrame myFrame= new MyFrame("demo");
	}
}
