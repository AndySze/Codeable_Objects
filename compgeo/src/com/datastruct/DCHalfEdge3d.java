package com.datastruct;

import com.math.CompPoint3d;

public class DCHalfEdge3d extends DCHalfEdge {
	public CompPoint3d start;
	public CompPoint3d end;
	
	
	public DCHalfEdge3d(CompPoint3d start,CompPoint3d end ){//constructor for 3d points (for retrieval only)
		super(start,end);
		this.start = start;
		this.end = end;
		
	
		
	}
}
