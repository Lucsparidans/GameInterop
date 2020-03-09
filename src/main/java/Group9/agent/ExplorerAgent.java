package Group9.agent;

import Interop.Action.GuardAction;
import Interop.Agent.Guard;
import Interop.Geometry.Vector;
import Interop.Percept.GuardPercepts;

public class ExplorerAgent extends AgentContainer<Guard> implements Guard {

    public ExplorerAgent(Vector position, Vector direction) {
        super(position, direction);
    }

    @Override
    public GuardAction getAction(GuardPercepts percepts) {
        return null;
    }

}
