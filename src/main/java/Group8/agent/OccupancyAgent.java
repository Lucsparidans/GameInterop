package Group8.agent;

import Group8.Game;
import Interop.Action.DropPheromone;
import Interop.Action.GuardAction;
import Interop.Action.Move;
import Interop.Action.Rotate;
import Interop.Agent.Guard;
import Interop.Geometry.Angle;
import Interop.Geometry.Distance;
import Interop.Percept.GuardPercepts;
import Interop.Percept.Scenario.SlowDownModifiers;
import Interop.Percept.Smell.SmellPerceptType;

/**
 * @author Thomas Sijpkens
 * class OccupancyAgent uses and Occupancy grid representation of the environment.  This allows us to use ML approaches for the agent.
 */
public class OccupancyAgent implements Guard {
    private double xsize; //X size of map
    private double ysize; //Y size of map
    private double grid_size; //how thicc the wall assumption be.

    //For optimality sake OccupancyGrid().update() would determine whether log likelihood is 0.5 is (<,>).
    //I am leaving it incase we want to do some fancy data analytics.
    //private ArrayList<ArrayList<Double>> log_prob_map; //initially set to zero

    //Agent has pose defined by (x,y,theta)
    private final int DEGREE_OF_FREEDOM = 3;
    //index 1 is x coordinate, 2 is y coordinate, 3 is robot's heading(theta).
    private double[] pose = new double[3];

    //some modifiable
    private double alpha = 1.0; //How thick can obstacles be
    private double beta = 5.0 * Math.PI/180; //width of FOV.
    private double z_max = 150.0; //how much info could be stored from the laser

    //Log-probabilities to add or remove from the map
    private double log_occ = Math.log(0.65/0.35);
    private double log_free = Math.log(0.35/0.65);

    private GuardPercepts percepts;

    public OccupancyAgent() {
        OccupancyGrid occupancyGrid = new OccupancyGrid();
    }

    //as defined in https://www.youtube.com/watch?v=Ko7SWZQIawM

    //TODO: use Normal number genrator instead of uniform distribution.

    /**
     * @return an optimal estimate of the state of a acell given by a MAP decision rule
     * a cell C is Occupied if P[Mx,y = 1] > P(Mx,y = 0)
     * else if P[Mx,y = 1] > P(Mx,y = 0) then C is empty
     * otw, P[Mx,y = 1] = P(Mx,y = 0) then C is unknown.
     */
    public void maximumAPosteriori() {
        return;
    }


    /**
     * This action is called by another agent, agent B, upon accessing a communication point made by agent A.
     * Agent B verifies false positive rate that of agent A's occupancyGrid.
     *
     * THIS SERVES AS DATA ANALYSIS FOR PHASE 3, TO SEE IF THERE ARE ANY POINTS OF IMPROVEMENTS!
     * !DO NOT CALL METHOD IN FINAL SUBMISSION, OR ELSE AGENT IS EVEN SLOWER!
     */
    public void verify() {

    }

    private double getSpeedModifier(GuardPercepts guardPercepts)
    {
        SlowDownModifiers slowDownModifiers =  guardPercepts.getScenarioGuardPercepts().getScenarioPercepts().getSlowDownModifiers();
        if(guardPercepts.getAreaPercepts().isInWindow())
        {
            return slowDownModifiers.getInWindow();
        }
        else if(guardPercepts.getAreaPercepts().isInSentryTower())
        {
            return slowDownModifiers.getInSentryTower();
        }
        else if(guardPercepts.getAreaPercepts().isInDoor())
        {
            return slowDownModifiers.getInDoor();
        }

        return 1;
    }

    @Override
    public GuardAction getAction(GuardPercepts percepts) {
        if(!percepts.wasLastActionExecuted())
        {
            if(Math.random() < 0.1)
            {
                return new DropPheromone(SmellPerceptType.values()[(int) (Math.random() * SmellPerceptType.values().length)]);
            }
            return new Rotate(Angle.fromRadians(percepts.getScenarioGuardPercepts().getScenarioPercepts().getMaxRotationAngle().getRadians() * Game._RANDOM.nextDouble()));
        }
        else
        {
            return new Move(new Distance(percepts.getScenarioGuardPercepts().getMaxMoveDistanceGuard().getValue() * getSpeedModifier(percepts)));
        }
    }

    /**
     * There are only 4 conditional probabilities.
     * P(z = 1|Mx,y = 1) : True occupied measurement
     * P(z = 0|Mx,y = 1) : False free measurement
     * P(z = 1|Mx,y = 0) : False occupied measurement
     * P(z = 0|Mx,y = 0) : True free measurement
     */
    public void conditionalProbabilities() {
        //recall: P(A^c|B) = 1 - P(A | B)
    }

    /**
     * Converts our Prior Map to Posterior map using Bayes formula:
     * P(Mx,y | z) = (P(z | Mx,y) * P(Mx,y)) / P(z)
     */
    public void posteriorMap(GuardPercepts percepts){
        Distance distanceToNearestObject = percepts.getVision().getFieldOfView().getRange();
        Angle directionFacing = percepts.getVision().getFieldOfView().getViewAngle();
    }

    /**
     * a binary random variable (0,1) with Mx,y:{free, occupied} -> {0,1}https://www.youtube.com/watch?v=Ko7SWZQIawM
     * Given some probability sapce (theta, P) a R.V. X: theta -> R is a function that maps the sample space to the reals.
     */
    public void Occupancy() {

    }

    /**
     * fine-grained grid map where an occupancy variable associated with each cell.
     * i.e. it is just an array of probability using Occupancy() Mx,y
     * Requires Bayesian filtering to maintain a occupancy grid map.
     *  Recursively update p(Mx,My) for each cell
     */
}
