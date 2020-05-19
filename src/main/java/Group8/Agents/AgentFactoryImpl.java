package Group8.Agents;

import Group8.Agents.Intruder.SimplePathfindingIntruder;
import Group9.agent.factories.IAgentFactory;
import Interop.Agent.Guard;
import Interop.Agent.Intruder;

import java.util.ArrayList;
import java.util.List;

public class AgentFactoryImpl implements IAgentFactory{
    public static final AlgoG GUARD_ALGORITHM= AlgoG.AI1;
    public static final AlgoI INTRUDER_ALGORITHM = AlgoI.AI1;

    public enum AlgoI {
        AI1,SIMPLE_PATH
    }
    public enum AlgoG {
        AI1,OCCUPANCY_AGENT

    }

    @Override
     public List<Intruder> createIntruders(int number) {
        List<Intruder> intruders = new ArrayList<>();

        for(int i=0; i<number; i++){
            switch(INTRUDER_ALGORITHM) {
                case AI1:
                    intruders.add(null);
                    break;
                case SIMPLE_PATH:
                    intruders.add(new SimplePathfindingIntruder());
                    break;
            }
        }

        return intruders;
    }

    @Override
     public List<Guard> createGuards(int number) {
        List<Guard> guards = new ArrayList<>();

        for(int i=0; i<number; i++){
            switch(GUARD_ALGORITHM) {
                case AI1:
                    guards.add(null);
                    break;
                case OCCUPANCY_AGENT:
                    guards.add(new OccupancyAgent());
                    break;
            }
        }

        return guards;
    }
}