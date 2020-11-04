package org.devgru.authorityforce.entity;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class AFTrigger extends Rectangle {

    private boolean isMapEnd;

    public AFTrigger(float pX, float pY, float pWidth, float pHeight,
	    VertexBufferObjectManager pVertexBufferObjectManager) {
	super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
    }

    public boolean isMapEnd() {
	return isMapEnd;
    }

    public void setMapEnd(boolean isMapEnd) {
	this.isMapEnd = isMapEnd;
    }
}
