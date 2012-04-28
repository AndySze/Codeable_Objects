package com.datastruct;

import com.math.CompPoint;
import com.math.Geom;

public class DCHalfEdge implements Comparable<DCHalfEdge> {	
	
	public CompPoint start; //pointer to start point
	public CompPoint end=null;  //pointer to end point
	public CompPoint direction; //directional vector, from "start", points to "end", normal of |left, right|
	public  CompPoint left = null;  //pointer to Voronoi site on the left side of edge
	public  CompPoint right = null; //pointer to Voronoi site on the left side of edge
	public DCHalfEdge intersectedEdge=null; //edge that this edge will be added beneath if a merge takes place
	public double length; //euclidean length of edge;
    public int infiniteEdge=0;
	
	public boolean inner = false; //determines if edge needs to be cut first because it is an inner edge
	public double		m; //directional coefficients satisfying equation y = m*x + b (edge lies on this line) 
	public double		b;
	
	public DCHalfEdge neighbor = null; //twin to edge
	

	
	
	public DCHalfEdge(CompPoint start,CompPoint end ){//constructor for end that is predetermined
		this.start = start;
		this.end = end;
		
		m = (start.getY() - end.getY()) / (start.getX() - end.getX()) ; //calculate the slope of the line by the inverse of the slope of the line through left and right
		b = start.getY() -(m * start.getX()); //calculate the y intercept with y=mx+b


        this.length = Math.sqrt(Math.pow(start.getX()-end.getX(),2)+Math.pow(start.getY()-end.getY(),2));

    }
	
	public DCHalfEdge(CompPoint start, CompPoint left, CompPoint right ){//constructor
		this.start = start;
		this.left = left;
		this.right = right;
		
		m = (right.getX() - left.getX()) / (left.getY() - right.getY()) ; //calculate the slope of the line by the inverse of the slope of the line through left and right
		b = start.getY() -m * start.getX(); //calculate the y intercept with y=mx+b
		
		direction = new CompPoint((right.getY()-left.getY()),-(right.getX()-left.getX()));
        this.length = Math.sqrt(Math.pow(Math.abs(start.getX()-end.getX()),2)+Math.pow(Math.abs(start.getY()-end.getY()),2));

    }
	
	public void setleft(CompPoint left){
		this.left = left;
	}
	
	public void setright(CompPoint right){
		this.right = right;
	}
	
	
	
	public int compareTo(DCHalfEdge e){//will need to implement
		return 0;
	}
	
	
	public void translate(double x, double y, CompPoint focus){
			double dx = x-focus.getX();
			double dy = y-focus.getY();
			
			start.setX(start.getX()+dx);
			start.setY(start.getY()+dy);
			end.setX(end.getX()+dx);
			end.setY(end.getY()+dy);
			
			m = (start.getY() - end.getY()) / (start.getX() - end.getX()) ; //calculate the slope of the line by the inverse of the slope of the line through left and right
			b = start.getY() -(m * start.getX()); //calculate the y intercept with y=mx+b
		
	}
	
	public void rotate(double startR, double newStartTheta, double endR, double newEndTheta,CompPoint _focus){
		CompPoint newStart= Geom.polarToCart(startR, newStartTheta);
		CompPoint newEnd= Geom.polarToCart(endR, newEndTheta);
		start.setX(newStart.getX()+_focus.getX());
		start.setY(newStart.getY()+_focus.getY());
		end.setX(newEnd.getX()+_focus.getX());
		end.setY(newEnd.getY()+_focus.getY());
		
		m = (start.getY() - end.getY()) / (start.getX() - end.getX()) ; //calculate the slope of the line by the inverse of the slope of the line through left and right
		b = start.getY() -(m * start.getX()); //calculate the y intercept with y=mx+b
	
}

}
