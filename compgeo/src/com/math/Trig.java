package com.math;

import com.datastruct.DCHalfEdge;
import com.datastruct.DoublyConnectedEdgeList;

import java.util.Vector;

public class Trig {

	public static CompPoint polarToCart(double r, double theta){
		double x = Math.cos(theta * Math.PI/ 180.0)*r;
		double y = Math.sin(theta * Math.PI/ 180.0)*r;

		/*double[] xY = new double[2];
        xY[0]=x;
        xY[1] = y;
		return xY;*/
		return new CompPoint(x,y);

	}

	public static double[] cartToPolar(double x,double y){

		double r = 0.0; 
		double theta = 0.0;

		r = Math.sqrt( (x*x)+(y*y) );

		int type = 0;
		if(x>0 && y>=0) type=1;
		if(x>0 && y<0) type=2;
		if(x<0) type=3;
		if(x==0 && y>0) type=4;
		if(x==0 && y<0) type=5;
		if(x==0 && y==0) type=6;

		//Find theta
		switch(type){
		case(1):
			theta = Math.atan(y/x) * (180.0/Math.PI);
		break;
		case(2):
			theta = (Math.atan( y/x ) + 2*Math.PI)* (180.0/Math.PI);
		break;
		case(3):
			theta = (Math.atan( y/x ) + Math.PI)* (180.0/Math.PI);
		break;
		case(4):
			theta = (Math.PI/2.0) * (180.0/Math.PI);
		break;
		case(5):
			theta = ( (3*Math.PI)/2.0 ) * (180.0/Math.PI);
		break;
		case(6):
			theta = 0.0;
		break;
		default:
			theta =0.0;
			break;
		}

		double[] rTheta = new double[2];
		rTheta[0]=r;
		rTheta[1] = theta;

		return rTheta;
	}

	
	public static DCHalfEdge getIntersectedEdge(CompPoint focus, CompPoint direction, DCHalfEdge parentEdge, DoublyConnectedEdgeList border){
		

		DCHalfEdge edge = new DCHalfEdge(focus,direction);

		double dx=direction.getX()-focus.getX(); //direction x
		double dy=direction.getY()-focus.getY(); //direction y

		DCHalfEdge borderEdge = null; //selected border edge for intersection;

		double[] thetas = border.getBorderPoints(focus); //degrees of all points along the border

		double edgeTheta = Trig.cartToPolar(dx, dy)[1];//degree of edge
		double selectedTheta=-1;
		//myParent.println("\n\n\n edgeTheta="+edgeTheta+"\n\n\n");

		for(int i=0;i<thetas.length;i++){ //iterate through all edges and look for correct point of intersection 
			//myParent.println("edge "+i+" theta="+thetas[i]+" x="+border.edges.get(i).start.getX()+" y="+border.edges.get(i).start.getY());
			int after= i+1;
			if(i==thetas.length-1){ 
				after=0;
			}

			if(thetas[after]>thetas[i]){//special case where angle of previous edge is greater than angle of current edge
				//myParent.println("call special case");
				if (edgeTheta>=thetas[after] || edgeTheta<thetas[i]) {//detects quadrant of intersection for special case


					if(edgeTheta>thetas[i]){
						borderEdge =  border.edges.get(i-1);

						selectedTheta=thetas[i];
					}
					else{
						borderEdge =  border.edges.get(i);
						

						selectedTheta=thetas[after];
					}

					/*myParent.println("\n\n\n edgeTheta="+edgeTheta);
					myParent.println("theta i="+thetas[i]);
					myParent.println("theta after="+thetas[after]);
					myParent.println("selected theta="+selectedTheta);
					myParent.println("start x,y,="+focus.getX()+","+focus.getY());
					myParent.println("end x,y,="+direction.getX()+","+direction.getY());
					myParent.println("intersection x,y,="+intersection.getX()+","+intersection.getY()+"\n\n\n");*/

					parentEdge.infiniteEdge=1;
					break;


				}
			}
			else{//otherwise all thetas should be greater than the preceeding theta

				if (edgeTheta<=thetas[i] && edgeTheta>thetas[after]) {//detects quadrant of intersection
					
					borderEdge =  border.edges.get(i);
					selectedTheta=thetas[i];
					break;


				}
			}
		}
		
	
		return borderEdge;
		
	
	}

	//finds the correct edge of intersection for a line segment and a polygon ---eventually will need to be merged with get intersection point and 
	public static CompPoint getIntersectedEdgePoint(CompPoint focus, CompPoint direction, DCHalfEdge parentEdge, DoublyConnectedEdgeList border){



		CompPoint intersection = null;

		DCHalfEdge edge = new DCHalfEdge(focus,direction);

		double dx=direction.getX()-focus.getX(); //direction x
		double dy=direction.getY()-focus.getY(); //direction y

		DCHalfEdge borderEdge = null; //selected border edge for intersection;

		double[] thetas = border.getBorderPoints(focus); //degrees of all points along the border

		double edgeTheta = Trig.cartToPolar(dx, dy)[1];//degree of edge
		double selectedTheta=-1;
		//myParent.println("\n\n\n edgeTheta="+edgeTheta+"\n\n\n");

		for(int i=0;i<thetas.length;i++){ //iterate through all edges and look for correct point of intersection 
			//myParent.println("edge "+i+" theta="+thetas[i]+" x="+border.edges.get(i).start.getX()+" y="+border.edges.get(i).start.getY());
			int after= i+1;
			if(i==thetas.length-1){ 
				after=0;
			}

			if(thetas[after]>thetas[i]){//special case where angle of previous edge is greater than angle of current edge
				//myParent.println("call special case");
				if (edgeTheta>=thetas[after] || edgeTheta<thetas[i]) {//detects quadrant of intersection for special case


					if(edgeTheta>thetas[i]){
						borderEdge =  border.edges.get(i-1);

						selectedTheta=thetas[i];
					}
					else{
						borderEdge =  border.edges.get(i);
						

						selectedTheta=thetas[after];
					}

					/*myParent.println("\n\n\n edgeTheta="+edgeTheta);
					myParent.println("theta i="+thetas[i]);
					myParent.println("theta after="+thetas[after]);
					myParent.println("selected theta="+selectedTheta);
					myParent.println("start x,y,="+focus.getX()+","+focus.getY());
					myParent.println("end x,y,="+direction.getX()+","+direction.getY());
					myParent.println("intersection x,y,="+intersection.getX()+","+intersection.getY()+"\n\n\n");*/

					parentEdge.infiniteEdge=1;
					break;


				}
			}
			else{//otherwise all thetas should be greater than the preceeding theta

				if (edgeTheta<=thetas[i] && edgeTheta>thetas[after]) {//detects quadrant of intersection
					
					borderEdge =  border.edges.get(i);
					selectedTheta=thetas[i];
					break;


				}
			}
		}
		
		edge.intersectedEdge = borderEdge;
		//System.out.println("intersected edge="+border.edges.indexOf(borderEdge));
		intersection = findIntersectionPoint(edge, borderEdge);
		//myParent.println("\n\n\n selectedTheta="+selectedTheta+"\n\n\n");
		
		return intersection;
	}




	public static boolean pointInComPolygon(CompPoint testPoint, DoublyConnectedEdgeList border){
		int angleCount =0;
		
		for (int i=border.edges.size()-1;i>=0;i--) { 
			DCHalfEdge edge = border.edges.get(i);
			if(edge.start.getY()>=edge.end.getY()){
				if (leftOn(edge.start, edge.end, testPoint)){
					angleCount++;
				}
			}
			else{
				if(leftOn(edge.end,edge.start, testPoint)){
					angleCount --;
				}
			}
		}
		
		//System.out.println("winding edge count ="+angleCount);
		if(angleCount!=0){
			return true;
		}
		else{
			return false;
		}
		
	}

	//determines if a given point is in a polygon defined by a doubly connected edge list
	public static boolean pointInPolygon(CompPoint testPoint, DoublyConnectedEdgeList border) {

		for (int i=border.edges.size()-1;i>=0;i--) { 
			DCHalfEdge edge = border.edges.get(i);
			if (leftOn(edge.start, edge.end, testPoint)) {
				return false;
			}
		}
		return true;
	}


	//determines if a segment intersects a polygon defined by a doubly connected edge list and returns edges of intersection if they exist
	public static Vector<DCHalfEdge> lineIntersectsPolygon(DCHalfEdge edge, DoublyConnectedEdgeList border) {
		Vector<DCHalfEdge> intersectedEdges = new Vector<DCHalfEdge>();
		for (int i=border.edges.size()-1;i>=0;i--) { 
			DCHalfEdge borderEdge = border.edges.get(i);
			if (lineIntersect(borderEdge.start, borderEdge.end, edge.start,edge.end)) {
				intersectedEdges.addElement(borderEdge);
			}
		}
		return intersectedEdges;
	}

	
	//detects if a line intersects an edge but does not return the point(s) of intersection
	public static boolean lineIntersect(CompPoint edgeStart, CompPoint edgeEnd, CompPoint testPointStart, CompPoint testPointEnd){


		if(intersectProp(edgeStart,edgeEnd,testPointStart,testPointEnd))
			return true;
		else if(between(edgeStart,edgeEnd,testPointStart)||
				between(edgeStart,edgeEnd,testPointEnd)||
				between(testPointStart,testPointEnd,edgeStart)||
				between(testPointStart,testPointEnd,edgeEnd))
			return true;
		else{
			return false;

		}

	}


	public static boolean between(CompPoint edgeStart, CompPoint edgeEnd, CompPoint testPoint){
		if(!collinear(edgeStart,edgeEnd,testPoint)){
			return false;
		}
		if(edgeStart.getX()!=edgeEnd.getX()){
			return(edgeStart.getX()<=testPoint.getX())&&(testPoint.getX()<=edgeEnd.getX())||
					(edgeStart.getX()>=testPoint.getX())&&(testPoint.getX()>=edgeEnd.getX());
		}
		else{
			return(edgeStart.getY()<=testPoint.getY())&&(testPoint.getY()<=edgeEnd.getY())||
					(edgeStart.getY()>=testPoint.getY())&&(testPoint.getY()>=edgeEnd.getY());
		}

	}


	public static boolean intersectProp(CompPoint edgeStart, CompPoint edgeEnd, CompPoint testPointStart, CompPoint testPointEnd){
		if(collinear(edgeStart,edgeEnd,testPointStart)||
				collinear(edgeStart,edgeEnd,testPointEnd)||
				collinear(testPointStart,testPointEnd,edgeStart)||
				collinear(testPointStart,testPointEnd,edgeEnd)){
			return false;
		}

		return Xor(left(edgeStart,edgeEnd,testPointStart),left(edgeStart,edgeEnd,testPointEnd)) 
				&& Xor(left(testPointStart,testPointEnd,edgeStart),left(testPointStart,testPointEnd,edgeEnd));

	}

	public static boolean Xor(boolean x, boolean y){

		return !x^!y;
	}

	public static boolean leftOn(CompPoint edgeStart, CompPoint edgeEnd, CompPoint testPoint){
		return area2(edgeStart,edgeEnd,testPoint)>=0;

	}
	
	

	public static boolean left(CompPoint edgeStart, CompPoint edgeEnd, CompPoint testPoint){
		return area2(edgeStart,edgeEnd,testPoint)>0;

	}

	public static boolean collinear(CompPoint edgeStart, CompPoint edgeEnd, CompPoint testPoint){
		return area2(edgeStart,edgeEnd,testPoint)==0;

	}

	public static double area2(CompPoint a, CompPoint b, CompPoint c){
		double area = ((b.getX()-a.getX())*(c.getY()-a.getY())) - ((c.getX()-a.getX())*(b.getY()-a.getY()));
		return area;

	}



	//finds the point of intersection between two edges that are known to intersect
	public static CompPoint findIntersectionPoint(DCHalfEdge edge, DCHalfEdge borderEdge){
		double mx=0;
		double my=0;

		if(Double.isInfinite(borderEdge.m)){//check to see if slope is undefined (line is vertical)
			mx=borderEdge.start.getX();
			my = (mx*edge.m)+edge.b;
		}
		else if(Double.isNaN(borderEdge.m)){//check to see if slope is NaN (line is horizontal)
			my=borderEdge.start.getY();
			mx=(my-edge.b)/edge.m;

		}
		else{	
			mx = (edge.b-borderEdge.b)/(borderEdge.m-edge.m);//line has a slope
			my = (mx*edge.m)+edge.b;
		}

		CompPoint intersection = new CompPoint(mx, my); 

		return intersection;
	}
}
