package com.cdjysdkj.diary.anomotion;

/**
 */
public abstract class Dynamics {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int MAX_TIMESTEP = 50;
	
	// ===========================================================
	// Fields
	// ===========================================================

	protected float mPosition;
    protected float mVelocity;
    protected float mMaxPosition = Float.MAX_VALUE;
    protected float mMinPosition = -Float.MAX_VALUE;
    protected long mLastTime = 0;

    
	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Setter
	// ===========================================================
    
    public void setState(final float position, final float velocity, final long now) {
        mVelocity = velocity;
        mPosition = position;
        mLastTime = now;
    }

    /**
     *
     * @param maxPosition
     */
    public void setMaxPosition(final float maxPosition) {
        mMaxPosition = maxPosition;
    }

    
    /**
     *
     * @param minPosition
     */
    public void setMinPosition(final float minPosition) {
        mMinPosition = minPosition;
    }
    
	// ===========================================================
	// Getter
	// ===========================================================
    
    /**
     *
     * @return
     */
    public float getPosition() {
        return mPosition;
    }

    /**
     *
     * @return
     */
    public float getVelocity() {
        return mVelocity;
    }

    
    /**
     *
     * @param velocityTolerance
     * @param positionTolerance
     * @return
     */
    public boolean isAtRest(final float velocityTolerance, final float positionTolerance) {
        final boolean standingStill = Math.abs(mVelocity) < velocityTolerance;
        final boolean withinLimits = mPosition - positionTolerance < mMaxPosition
                && mPosition + positionTolerance > mMinPosition;
        return standingStill && withinLimits;
    }


    /**
     *
     * @param now
     */
    public void update(final long now) {
        int dt = (int)(now - mLastTime);
        if (dt > MAX_TIMESTEP) {
            dt = MAX_TIMESTEP;
        }

        onUpdate(dt);

        mLastTime = now;
    }

    
    /**
     *
     * @return
     */
    protected float getDistanceToLimit() {
        float distanceToLimit = 0;

        if (mPosition > mMaxPosition) {
            distanceToLimit = mMaxPosition - mPosition;
        } else if (mPosition < mMinPosition) {
            distanceToLimit = mMinPosition - mPosition;
        }

        return distanceToLimit;
    }
    
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

    /**
     * @param dt
     */
    abstract protected void onUpdate(int dt);
    
	// ===========================================================
	// Private Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

 
    

}
