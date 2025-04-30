package com.wb.amr.robot.flotilla.control.system.tools.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wb.amr.robot.flotilla.control.system.mqtt.model.*;
import com.wb.amr.robot.flotilla.control.system.mqtt.model.Error;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    private static final Logger LOGGER = LogManager.getLogger(Parser.class.getName());

    private @NotNull List<ActionParameter> getActionParameters(Object actionParametersObject) {
        List<ActionParameter> actionParameterList = new ArrayList<>();
        if (actionParametersObject instanceof ArrayList<?>) {
            ArrayList<LinkedHashMap<String, Object>> rawActionParameters = new ObjectMapper()
                    .convertValue(actionParametersObject, new TypeReference<>() {
                    });
            for (LinkedHashMap<String, Object> info : rawActionParameters) {
                for (Map.Entry<String, Object> entry : info.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    ActionParameter actionParameter = new ActionParameter();
                    actionParameter.setValue(key, value);
                    actionParameterList.add(actionParameter);
                }
            }
        }
        return actionParameterList;
    }

    private @NotNull List<Actions> getActions(Object actionsObject) {
        List<Actions> actionsList = new ArrayList<>();
        if (actionsObject instanceof ArrayList<?>) {
            ArrayList<LinkedHashMap<String, Object>> rawActions = new ObjectMapper()
                    .convertValue(actionsObject, new TypeReference<>() {});
            for (LinkedHashMap<String, Object> info : rawActions) {
                List<ActionParameter> actionParameterList = getActionParameters(info.remove("actionParameters"));
                Actions action = new Actions();
                for (Map.Entry<String, Object> entry : info.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    action.setValue(key, value);
                }
                action.setActionParameters(actionParameterList);
                actionsList.add(action);
            }
        }
        return actionsList;
    }

    private @NotNull NodePosition getNodePosition(Object nodePositionObject) {
        NodePosition position = new NodePosition();
        if (nodePositionObject instanceof LinkedHashMap<?, ?>) {
            LinkedHashMap<String, Object> rawPositionObject = new ObjectMapper()
                    .convertValue(nodePositionObject, new TypeReference<>() {
                    });
            for (Map.Entry<String, Object> entry : rawPositionObject.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                position.setValue(key, value);
            }
        }
        return position;
    }

    private @NotNull List<Node> getNode(Object nodeObject) {
        List<Node> nodeList = new ArrayList<>();
        if (nodeObject instanceof ArrayList<?>) {
            ArrayList<LinkedHashMap<String, Object>> nodes = new ObjectMapper()
                    .convertValue(nodeObject, new TypeReference<>() {
                    });
            for (LinkedHashMap<String, Object> rawNodes : nodes) {
                NodePosition nodePosition = getNodePosition(rawNodes.remove("nodePosition"));
                List<Actions> actions = getActions(rawNodes.remove("actions"));
                Node n = new Node();
                for (Map.Entry<String, Object> entry : rawNodes.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    n.setValue(key, value);
                }
                n.setNodePosition(nodePosition);
                n.setActions(actions);
                nodeList.add(n);
            }
        }
        return nodeList;
    }

    private @NotNull List<Edge> getEdge(Object edgeObject) {
        List<Edge> edgeList = new ArrayList<>();
        if (edgeObject instanceof ArrayList<?>) {
            ArrayList<LinkedHashMap<String, Object>> rawEdges = new ObjectMapper()
                    .convertValue(edgeObject, new TypeReference<>() {
                    });
            for (LinkedHashMap<String, Object> edge : rawEdges) {
                List<Actions> actions = getActions(edge.remove("actions"));
                Edge e = new Edge();
                for (Map.Entry<String, Object> entry : edge.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    e.setValue(key, value);
                }
                e.setActions(actions);
                edgeList.add(e);
            }
        }
        return edgeList;
    }

    public Order getOrder(InputStream objectOrder) {
        Order order = new Order();
        try {
            LinkedHashMap<String, Object> rawOrder = new ObjectMapper().readValue(objectOrder, new TypeReference<>() {
            });
            List<Node> nodes = getNode(rawOrder.remove("nodes"));
            List<Edge> edges = getEdge(rawOrder.remove("edges"));
            for (Map.Entry<String, Object> entry : rawOrder.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                order.setValue(key, value);
            }
            order.setNodes(nodes);
            order.setEdges(edges);
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return order;
    }

    private @NotNull AGVPosition getAGVPostion(Object agvPositionObject) {
        AGVPosition agvPosition = new AGVPosition();
        if (agvPositionObject instanceof LinkedHashMap<?, ?>) {
            LinkedHashMap<String, Object> info = new ObjectMapper().convertValue(agvPositionObject, new TypeReference<>() {
            });
            for (Map.Entry<String, Object> entry : info.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                agvPosition.setValue(key, value);
            }
        }
        return agvPosition;
    }

    private @NotNull Velocity getVelocity(Object velocityObject) {
        Velocity velocity = new Velocity();
        if (velocityObject instanceof LinkedHashMap<?, ?>) {
            LinkedHashMap<String, Object> info = new ObjectMapper().convertValue(velocityObject, new TypeReference<>() {
            });
            for (Map.Entry<String, Object> entry : info.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                velocity.setValue(key, value);
            }
        }
        return velocity;
    }

    private @NotNull BatteryState getBatteryState(Object batteryStateObject) {
        BatteryState batteryState = new BatteryState();
        if (batteryStateObject instanceof LinkedHashMap<?, ?>) {
            LinkedHashMap<String, Object> info = new ObjectMapper().convertValue(batteryStateObject, new TypeReference<>() {
            });
            for (Map.Entry<String, Object> entry : info.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                batteryState.setValue(key, value);
            }
        }
        return batteryState;
    }

    private @NotNull SafetyState getSafetyState(Object safetyStateObject) {
        SafetyState safetyState = new SafetyState();
        if (safetyStateObject instanceof LinkedHashMap<?, ?>) {
            LinkedHashMap<String, Object> info = new ObjectMapper().convertValue(safetyStateObject, new TypeReference<>() {
            });
            for (Map.Entry<String, Object> entry : info.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                safetyState.setValue(key, value);
            }
        }
        return safetyState;
    }

    private @NotNull List<ControlPoint> getControlPoints(Object controlPointsObject) {
        List<ControlPoint> controlPoints = new ArrayList<>();
        if (controlPointsObject instanceof ArrayList<?>) {
            ArrayList<LinkedHashMap<String, Object>> rawControlPoints = new ObjectMapper()
                    .convertValue(controlPointsObject, new TypeReference<>() {
                    });
            for (LinkedHashMap<String, Object> info : rawControlPoints) {
                ControlPoint controlPoint = new ControlPoint();
                for (Map.Entry<String, Object> entry : info.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    controlPoint.setValue(key, value);
                }
                controlPoints.add(controlPoint);
            }
        }
        return controlPoints;
    }

    private @NotNull Trajectory getTrajectory(Object trajectoryObject) {
        Trajectory trajectory = new Trajectory();
        if (trajectoryObject instanceof LinkedHashMap<?, ?>) {
            LinkedHashMap<String, Object> info = new ObjectMapper().convertValue(trajectoryObject, new TypeReference<>() {
            });
            List<ControlPoint> controlPoints = getControlPoints(info.remove("controlPoints"));
            for (Map.Entry<String, Object> entry : info.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                trajectory.setValue(key, value);
            }
            trajectory.setControlPoints(controlPoints);
        }
        return trajectory;
    }

    private @NotNull List<EdgeStates> getEdgeStates(Object edgeStateObject) {
        List<EdgeStates> edgeStates = new ArrayList<>();
        if (edgeStateObject instanceof LinkedHashMap<?, ?>) {
            LinkedHashMap<String, Object> info = new ObjectMapper().convertValue(edgeStateObject, new TypeReference<>() {
            });
            EdgeStates edgeState = new EdgeStates();
            Trajectory trajectory = getTrajectory(info.remove("trajectory"));
            for (Map.Entry<String, Object> entry : info.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                edgeState.setValue(key, value);
            }
            edgeState.setTrajectory(trajectory);
            edgeStates.add(edgeState);
        }
        return edgeStates;
    }

    private @NotNull List<NodeState> getNodeSates(Object nodeStatesObject) {
        List<NodeState> nodeStates = new ArrayList<>();
        if (nodeStatesObject instanceof ArrayList<?>) {
            ArrayList<LinkedHashMap<String, Object>> rawNodeStates = new ObjectMapper()
                    .convertValue(nodeStatesObject, new TypeReference<>() {
                    });
            for (LinkedHashMap<String, Object> info : rawNodeStates) {
                NodePosition nodePosition = getNodePosition(info.remove("nodePosition"));
                NodeState nodeState = new NodeState();
                for (Map.Entry<String, Object> entry : info.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    nodeState.setValue(key, value);
                }
                nodeState.setNodePosition(nodePosition);
                nodeStates.add(nodeState);
            }
        }
        return nodeStates;
    }

    private @NotNull List<Load> getLoads(Object loadsObject) {
        List<Load> loads = new ArrayList<>();
        if (loadsObject instanceof LinkedHashMap<?, ?>) {
            LinkedHashMap<String, Object> info = new ObjectMapper().convertValue(loadsObject, new TypeReference<>() {
            });
            Load load = new Load();
            for (Map.Entry<String, Object> entry : info.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                load.setValue(key, value);
            }
            loads.add(load);
        }
        return loads;
    }

    private @NotNull List<ActionState> getActionsStates(Object actionStatesObject) {
        List<ActionState> actionStates = new ArrayList<>();
        if (actionStatesObject instanceof LinkedHashMap<?, ?>) {
            LinkedHashMap<String, Object> info = new ObjectMapper().convertValue(actionStatesObject, new TypeReference<>() {
            });
            ActionState actionState = new ActionState();
            for (Map.Entry<String, Object> entry : info.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                actionState.setValue(key, value);
            }
            actionStates.add(actionState);
        }
        return actionStates;
    }

    private @NotNull List<InfoReference> getInfoReferences(Object infoReferencesObject) {
        List<InfoReference> infoReferences = new ArrayList<>();
        if (infoReferencesObject instanceof LinkedHashMap<?, ?>) {
            LinkedHashMap<String, Object> info = new ObjectMapper().convertValue(infoReferencesObject, new TypeReference<>() {
            });
            InfoReference infoReference = new InfoReference();
            for (Map.Entry<String, Object> entry : info.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                infoReference.setValue(key, value);
            }
            infoReferences.add(infoReference);
        }
        return infoReferences;
    }

    private @NotNull List<Information> getInformation(Object informationObject) {
        List<Information> informations = new ArrayList<>();
        if (informationObject instanceof ArrayList<?>) {
            ArrayList<LinkedHashMap<String, Object>> rawInformation = new ObjectMapper()
                    .convertValue(informationObject, new TypeReference<>() {
                    });
            for (LinkedHashMap<String, Object> info : rawInformation) {
                List<InfoReference> infoReferences = getInfoReferences(info.remove("infoReferences"));
                Information information = new Information();
                for (Map.Entry<String, Object> entry : info.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    information.setValue(key, value);
                }
                information.setInfoReferences(infoReferences);
                informations.add(information);
            }
        }
        return informations;
    }

    private @NotNull List<ErrorReferences> getErrorReferences(Object errorReferencesObject) {
        List<ErrorReferences> errorReferences = new ArrayList<>();
        if (errorReferencesObject instanceof ArrayList<?>) {
            ArrayList<LinkedHashMap<String, Object>> rawErrorReferences = new ObjectMapper()
                    .convertValue(errorReferencesObject, new TypeReference<>() {
                    });
            for (LinkedHashMap<String, Object> info : rawErrorReferences) {
                ErrorReferences references = new ErrorReferences();
                for (Map.Entry<String, Object> entry : info.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    references.setValue(key, value);
                }
                errorReferences.add(references);
            }
        }
        return errorReferences;
    }

    private @NotNull List<Error> getErrors(Object errorsObject) {
        List<Error> errors = new ArrayList<>();
        if (errorsObject instanceof ArrayList<?>) {
            ArrayList<LinkedHashMap<String, Object>> rawErrorReferences = new ObjectMapper()
                    .convertValue(errorsObject, new TypeReference<>() {
                    });
            for (LinkedHashMap<String, Object> info : rawErrorReferences) {
                Error error = new Error();
                List<ErrorReferences> errorReferences = getErrorReferences(info.remove("errorReferences"));
                for (Map.Entry<String, Object> entry : info.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    error.setValue(key, value);
                }
                error.setErrorReferences(errorReferences);
                errors.add(error);
            }
        }
        return errors;
    }

    public Status getStatus(InputStream objectStatus) {
        Status status = new Status();
        try {
            LinkedHashMap<String, Object> rawStatus = new ObjectMapper().readValue(objectStatus, new TypeReference<>() {
            });

            List<NodeState> nodeStates = getNodeSates(rawStatus.remove("nodeStates"));
            List<EdgeStates> edgeStates = getEdgeStates(rawStatus.remove("edgeStates"));
            AGVPosition agvPosition = getAGVPostion(rawStatus.remove("agvPosition"));
            Velocity velocity = getVelocity(rawStatus.remove("velocity"));
            List<Load> loads = getLoads(rawStatus.remove("loads"));
            List<ActionState> actionStates = getActionsStates(rawStatus.remove("actionStates"));
            BatteryState batteryState = getBatteryState(rawStatus.remove("batteryState"));
            List<Error> errors = getErrors(rawStatus.remove("errors"));
            List<Information> information = getInformation(rawStatus.remove("information"));
            SafetyState safetyState = getSafetyState(rawStatus.remove("safetyState"));
            for (Map.Entry<String, Object> entry : rawStatus.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                status.setValue(key, value);
            }
            status.setNodeStates(nodeStates);
            status.setEdgeStates(edgeStates);
            status.setAgvPosition(agvPosition);
            status.setVelocity(velocity);
            status.setLoads(loads);
            status.setActionStates(actionStates);
            status.setBatteryState(batteryState);
            status.setErrors(errors);
            status.setInformation(information);
            status.setSafetyState(safetyState);
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return status;
    }

}