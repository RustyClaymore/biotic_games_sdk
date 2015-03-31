package edu.stanford.riedel_kruse.euglenasoccer;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

import java.util.ArrayList;

import edu.stanford.riedel_kruse.bioticgamessdk.Circle;
import edu.stanford.riedel_kruse.bioticgamessdk.GameObject;
import edu.stanford.riedel_kruse.bioticgamessdk.MathUtil;
import edu.stanford.riedel_kruse.bioticgamessdk.Shape;

/**
 * Created by dchiu on 3/28/15.
 */
public class SoccerBall extends Circle {
    public static final int RADIUS = 30;
    private static final Scalar COLOR = new Scalar(255, 255, 255);
    private static final Scalar OUTLINE_COLOR = new Scalar(0, 0, 0);
    private static final int OUTLINE_THICKNESS = 5;
    private static final Scalar PENTAGONS_COLOR = new Scalar(150, 150, 150);

    private static final int TRACKING_CIRCLE_RADIUS = RADIUS * 2;
    private static final Scalar TRACKING_CIRCLE_COLOR = new Scalar(255, 0, 0);
    private static final int TRACKING_CIRCLE_THICKNESS = 3;

    private Point mDirection;

    public SoccerBall(Point position) {
        super(position, TRACKING_CIRCLE_RADIUS, TRACKING_CIRCLE_COLOR, TRACKING_CIRCLE_THICKNESS,
                true);

        mDirection = new Point(0, 0);
    }

    @Override
    public void draw(Mat frame, Point offset) {
        Point drawPosition = MathUtil.addPoints(offset, mPosition);
        Core.circle(frame, drawPosition, TRACKING_CIRCLE_RADIUS, TRACKING_CIRCLE_COLOR,
                TRACKING_CIRCLE_THICKNESS);

        Point ballPosition = mDirection.clone();
        ballPosition.x *= TRACKING_CIRCLE_RADIUS;
        ballPosition.y *= TRACKING_CIRCLE_RADIUS;
        ballPosition = MathUtil.addPoints(ballPosition, drawPosition);

        // Draws the black outline of the ball.
        Core.circle(frame, ballPosition, RADIUS, OUTLINE_COLOR,
                OUTLINE_THICKNESS);

        // Draws a filled white circle, which is the background of the ball.
        Core.circle(frame, ballPosition, RADIUS, COLOR, -1);


        //Draw pentagons
        ArrayList<MatOfPoint> pentagons = new ArrayList<MatOfPoint>();
        pentagons.add(0, new MatOfPoint(new Point(ballPosition.x, ballPosition.y - 12),
                new Point(ballPosition.x - 11, ballPosition.y - 4),
                new Point(ballPosition.x - 6, ballPosition.y + 9),
                new Point(ballPosition.x + 6, ballPosition.y + 9),
                new Point(ballPosition.x + 11, ballPosition.y - 4)));
        pentagons.add(1, new MatOfPoint(new Point(ballPosition.x - 10, ballPosition.y - 20),
                new Point(ballPosition.x - 20, ballPosition.y - 12),
                new Point(ballPosition.x - 25, ballPosition.y - 12),
                new Point(ballPosition.x - 18, ballPosition.y - 19),
                new Point(ballPosition.x - 10, ballPosition.y - 25)));
        pentagons.add(2, new MatOfPoint(new Point(ballPosition.x + 10, ballPosition.y - 20),
                new Point(ballPosition.x + 20, ballPosition.y - 12),
                new Point(ballPosition.x + 25, ballPosition.y - 12),
                new Point(ballPosition.x + 18, ballPosition.y - 19),
                new Point(ballPosition.x + 10, ballPosition.y - 25)));
        pentagons.add(3, new MatOfPoint(new Point(ballPosition.x - 23, ballPosition.y + 3),
                new Point(ballPosition.x - 16, ballPosition.y + 16),
                new Point(ballPosition.x - 19, ballPosition.y + 18),
                new Point(ballPosition.x - 26, ballPosition.y + 11),
                new Point(ballPosition.x - 27, ballPosition.y + 3)));
        pentagons.add(4, new MatOfPoint(new Point(ballPosition.x + 23, ballPosition.y + 3),
                new Point(ballPosition.x + 16, ballPosition.y + 16),
                new Point(ballPosition.x + 19, ballPosition.y + 18),
                new Point(ballPosition.x + 26, ballPosition.y + 11),
                new Point(ballPosition.x + 27, ballPosition.y + 3)));
        pentagons.add(5, new MatOfPoint(new Point(ballPosition.x - 6, ballPosition.y + 22),
                new Point(ballPosition.x + 6, ballPosition.y + 22),
                new Point(ballPosition.x + 8, ballPosition.y + 27),
                new Point(ballPosition.x - 8, ballPosition.y + 27)));
        Core.fillPoly(frame, pentagons, PENTAGONS_COLOR);
    }

    public Point direction() {
        return mDirection.clone();
    }

    public void setDirection(Point direction) {
        mDirection = direction;
    }
}