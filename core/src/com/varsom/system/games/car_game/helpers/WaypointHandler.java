package com.varsom.system.games.car_game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import com.varsom.system.games.car_game.tracks.Track;

/**
 *
 */
public class WaypointHandler {

    //TRACK AND WAYPOINTS
    private Track track;
    private int waypointIndex;
    private final Array<Vector2> path;

    //CURRENT LINE
    private Vector2 currentLineStartPos, currentLineEndPos, currentLineVec;
    private int quadrant;
    private float currentLineAngle;

    //PROJECTION POINT
    private Vector2 projectionPoint, prevProjPoint;

    //FOR CAR
    private float traveledDistance;

    /**
     * This function requires/assumes that the first point within the path in Track t, is the start point of the first line
     * @param t
     */
    public WaypointHandler(Track t, Vector2 spawnPos){//, Vector2 startLineStart, Vector2 startLineEnd,){
        track = t;
        path = t.getPath(); // should not be able to be changed
        waypointIndex = 1; // the index in path of the currentLine's end pos

        //Find the current lines start and end positions
        currentLineStartPos = new Vector2();
        currentLineEndPos = new Vector2();
        assignNewVec(currentLineStartPos,path.get(waypointIndex-1));
        assignNewVec(currentLineEndPos,path.get(waypointIndex));

        //Find the vector vector for the current line
        currentLineVec = new Vector2();
        //Find the values
        calcCurrentLineVec();
        //Find the angle
        currentLineAngle = calcLineAngle(currentLineStartPos,currentLineEndPos);

        //Find in which quadrant of the coordinate system the vector is
        quadrant = calcQuadrant(currentLineVec);

        //update the point on the current line that the leader car is projected on
        projectionPoint = new Vector2();
        updateProjectionPoint(spawnPos);

        //save that position as the previous position
        prevProjPoint = new Vector2();
        assignNewVec(prevProjPoint,projectionPoint);

        //calculate the distance between the starting point of the current line and the projtion point
        traveledDistance = lengthOfVector2(projectionPoint, currentLineStartPos);
    }


    private void calcCurrentLineVec(){
        currentLineVec.x = currentLineEndPos.x - currentLineStartPos.x;
        currentLineVec.y = currentLineEndPos.y - currentLineStartPos.y;
    }

    /** Vector projection following the formula:
     *  ( (a dot b) / (|b|^2) ) * b
     *  where a and b are of type Vector2
     *  a will be projected onto b
     *
     * @param objectPosition
     * @return
     */
    private Vector2 updateProjectionPoint2(Vector2 objectPosition){
        Vector2 lineToObject = objectPosition;
        lineToObject.sub(currentLineStartPos);

        float a_dot_b = dotProd(lineToObject,currentLineVec);
        float abs_b_squared = (currentLineVec.x*currentLineVec.x + currentLineVec.y*currentLineVec.y);
        float scale = a_dot_b/abs_b_squared;
        float projectionEndPointX = scale * currentLineVec.x;
        float projectionEndPointY = scale * currentLineVec.y;
        projectionPoint.x = currentLineStartPos.x + projectionEndPointX;
        projectionPoint.y = currentLineStartPos.y + projectionEndPointY;

        return projectionPoint;
    }

    /**
     * Gives a position on the current line at the distance dist behind the position pos
     * @return
     */
    public Vector2 getOffsetPositionOnCurrentLine(Vector2 pos, float dist){
        //create a vector with length dist with the same angle as the current line
        Vector2 cameraPoint = new Vector2();
        cameraPoint.x = currentLineVec.x / currentLineVec.len2() * dist;
        cameraPoint.y = currentLineVec.y / currentLineVec.len2() * dist;

        cameraPoint.sub(pos);

        return cameraPoint;
    }

    public Vector2 getOffsetPoint(float offset) {
        Vector2 temp = new Vector2();
        temp.x = projectionPoint.x + currentLineVec.x/currentLineVec.len()*offset;
        temp.y = projectionPoint.y + currentLineVec.y/currentLineVec.len()*offset;
        return temp;
    }

    /** Vector projection following the formula:
     *  ( (a dot b) / (b dot b) ) * b
     *  where a and b are of type Vector2
     *  a will be projected onto b
     *
     * @param objectPosition
     * @return
     */
    private void updateProjectionPoint(Vector2 objectPosition){
        //Create a vector to the start position of the current line
        Vector2 lineToObject = new Vector2();
        assignNewVec(lineToObject,objectPosition);
        lineToObject.sub(currentLineStartPos);

        float a_dot_b = dotProd(lineToObject,currentLineVec);
        float b_dot_b = dotProd(currentLineVec,currentLineVec);
        float scale = a_dot_b/b_dot_b;
        float projectionEndPointX = scale * currentLineVec.x;
        float projectionEndPointY = scale * currentLineVec.y;
        projectionPoint.x = currentLineStartPos.x + projectionEndPointX;
        projectionPoint.y = currentLineStartPos.y + projectionEndPointY;
    }

    private float dotProd(Vector2 a, Vector2 b){
        return a.x * b.x + a.y * b.y;
    }

    private void updateWaypointIndex(){
        if(waypointIndex + 1 >= track.getPath().size){ //if we've reached the last point in path
            waypointIndex = 0;
        }
        else {
            waypointIndex++;
        }
    }
    private void updateCurrentLine(){
        if(waypointIndex + 1 >= track.getPath().size){ //if we've reached the last point in path
            waypointIndex = 0;
        }
        else {
            waypointIndex++;
        }
    }

    private boolean waypointIsReached(int quad, Vector2 trackPoint, Vector2 endPoint){
        switch (quad) {
            case 1:
                if(trackPoint.x >= endPoint.x && trackPoint.y >= endPoint.y){
                    return true;
                }
                break;
            case 2:
                if(trackPoint.x <= endPoint.x && trackPoint.y >= endPoint.y){
                    return true;
                }
                break;
            case 3:
                if(trackPoint.x <= endPoint.x && trackPoint.y <= endPoint.y){
                    return true;
                }
                break;
            case 4:
                if(trackPoint.x >= endPoint.x && trackPoint.y <= endPoint.y){
                    return true;
                }
                break;
            default:
        }
        return false;
    }

    /**
     *
     * * PSEUDO CODE - Algorithm..
     *   0.         in Car class, update car (should be done before this function is called)
     *   1.         calculate quadrant of point
     *   2.         project carPos on current line (get the global position of the projected point, pointOnTrack)
     *   3.         check if waypoint reached
     *   3.1            if reached{
     *   3.1.1              calc delta, Delta = distance from pointOnTrack's prev pos, to end of currentLine
     *   3.1.2              add delta to traveledDistance.
     *   3.1.3              update currentLineStartPos,
     *   3.1.4              update pointOnTrack to end of currentLine
     *   3.1.5              update the waypointIndex, make sure it doesn't go out of bounds
     *   3.1.6              update currentLineEndPos,
     *   3.1.7              update currentLine's angle
     *   3.1.8              update currentLineVec
     *      /           }
     *   3.2            else if NOT reached{
     *   3.2.1              if pointOnTrack is further from currentLine's end point than it was in the previous update, (car is going the wrong way) {
     *   3.2.1.2                if pointOnTrack has preceded(?!) the currentLine
     *   3.2.1.2.1                  set pointOnTrack to currentLine's start position
     *   3.2.1.2.2                  calc delta.. delta = distance from previous pointOnTrack to currentLine's start position.
     *          /               }
     *   3.2.1.3                else if pointOnTrack is still on the line {
     *   3.2.1.3.1                  calc delta.. delta = distance from previous pointOnTrack to current pointOnTrack
     *          /               }
     *   3.2.1.4                subtract delta from traveledDistance
     *        /             }
     *   3.2.2              else if pointOnTrack got closer to currentLine's end point or is still
     *   3.2.2.1                calc delta. delta = difference of (the distance from previous pOT to currentLineEnd & the distance from current pOT to currentLineEnd)
     *   3.2.2.2                add delta to traveledDistance
     *         /            }
     *        /         }
     *   4          set prevProjPoint = projectionPoint;
     *
     * @param carPos
     * @return
     */
    public Vector2 getProjectionPoint(Vector2 carPos){
        float delta;

        // updates the projection point
        updateProjectionPoint(carPos);

        // 3.1 if wayPoint is exceeded
        if(waypointIsReached(quadrant,projectionPoint,currentLineEndPos)){
            //find the distance between the point the car was projected to last
            // and the end position of the current line
            delta = lengthOfVector2(prevProjPoint, currentLineEndPos);
            traveledDistance += delta;

            //move the projection points position to the end of the current line
            assignNewVec(projectionPoint,currentLineEndPos);

            //update current line to the next line
            assignNewVec(currentLineStartPos,path.get(waypointIndex));
            updateWaypointIndex();
            assignNewVec(currentLineEndPos,path.get(waypointIndex));
            currentLineAngle = calcLineAngle(currentLineStartPos, currentLineEndPos);
            calcCurrentLineVec();
            quadrant = calcQuadrant(currentLineVec);
        }
        // 3.2 waypoint is NOT yet reached
        else{
            // 3.2.1 if car is reversing
            if(lengthOfVector2(projectionPoint,currentLineEndPos) > lengthOfVector2(prevProjPoint,currentLineEndPos)){
                // 3.2.1.2 preceded currentLineStartPos
                if(waypointIsReached(reverseQuad(quadrant), projectionPoint, currentLineStartPos)) {
                    //update the projection point
                    assignNewVec(projectionPoint,currentLineStartPos);
                    delta = lengthOfVector2(prevProjPoint, currentLineStartPos);
                }
                // 3.2.1.3 still on currentLine
                else {
                    delta = lengthOfVector2(prevProjPoint,projectionPoint);
                }
                traveledDistance -= delta;
            }
            // 3.3 if car is going the right way or standing still
            else {
                //update the projection point
                delta = lengthOfVector2(prevProjPoint, currentLineEndPos) - lengthOfVector2(projectionPoint, currentLineEndPos);
                traveledDistance += delta;
            }
        }

        // make sure the prev is the actual prev next time this function is called
        assignNewVec(prevProjPoint,projectionPoint);
        return projectionPoint;
    }

    private float calcLineAngle(Vector2 startP, Vector2 endP){
        return (float) Math.atan2(endP.y - startP.y,endP.x - startP.x);
    }

    //returns the length between position a and b
    private float lengthOfVector2(Vector2 a, Vector2 b){
       double c = Math.pow(b.x-a.x,2);
       double d = Math.pow(b.y-a.y, 2);
       float e = (float) Math.sqrt(c+d);
       return e;
      // return (float) Math.sqrt(Math.pow(b.x-a.x,2) + Math.pow(b.y-a.y, 2));
    }

    private int calcQuadrant(Vector2 vec){
        if(vec.x >= 0 && vec.y >= 0){ // first quadrant
            return 1;
        }
        else if(vec.x <= 0 && vec.y >= 0){ // second quadrant
            return 2;
        }
        else if(vec.x <= 0 && vec.y <= 0){ // third quadrant
            return 3;
        }
        else if(vec.x >= 0 && vec.y <= 0){ // fourth quadrant
            return 4;
        }

        Gdx.app.log("WAYPOINT", "SOMETHING IS WRONG IN THE calcQuadrant FUNCTION");
        return 0;
    }

    public float getTraveledDistance(){
        return traveledDistance;
    }
    public float getCurrentLineAngle(){
        return currentLineAngle;
    }

    /**
     *  quad should be an integer value between 1-4
     *  quad = 1 => 3
     *  quad = 2 => 4
     *  quad = 3 => 1
     *  quad = 4 => 2
     * @param quad
     * @return
     */
    private int reverseQuad(int quad){
        int reversed = quad-2;
        if (reversed < 1) {
            reversed += 4;
        }
        return reversed;
    }

    private void assignNewVec(Vector2 newVec, Vector2 oldVec) {
        newVec.x = oldVec.x;
        newVec.y = oldVec.y;
    }
}