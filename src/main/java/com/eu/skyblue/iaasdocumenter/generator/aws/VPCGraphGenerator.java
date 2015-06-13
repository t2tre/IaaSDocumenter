package com.eu.skyblue.iaasdocumenter.generator.aws;

import com.amazonaws.services.ec2.model.*;
import com.amazonaws.services.elasticloadbalancing.model.LoadBalancerDescription;
import com.eu.skyblue.iaasdocumenter.documenter.aws.InfrastructureClient;
import org.graphstream.algorithm.generator.BaseGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: raye
 * Date: 13/06/15
 * Time: 09:47
 * To change this template use File | Settings | File Templates.
 */
public class VPCGraphGenerator extends BaseGenerator implements Generator {
    private InfrastructureClient infrastructureClient;
    private Vpc vpc;
    private Graph vpcGraph;

    private List<InternetGateway> internetGateways;
    private List<NetworkAcl> networkAcls;
    private List<SecurityGroup> securityGroups;
    private List<Instance> instances;
    private List<RouteTable> routeTables;
    private List<Subnet> subnets;
    private List<NetworkInterface> networkInterfaces;
    private List<LoadBalancerDescription> loadBalancerDescriptions;

    protected VPCGraphGenerator(InfrastructureClient infrastructureClient, Vpc vpc, Graph vpcGraph) {
        this.infrastructureClient = infrastructureClient;
        this.vpc = vpc;
        this.vpcGraph = vpcGraph;

        this.internetGateways = new ArrayList<InternetGateway>();
        this.networkAcls = new ArrayList<NetworkAcl>();
        this.securityGroups = new ArrayList<SecurityGroup>();
        this.instances = new ArrayList<Instance>();
        this.routeTables = new ArrayList<RouteTable>();
        this.subnets = new ArrayList<Subnet>();
        this.networkInterfaces = new ArrayList<NetworkInterface>();
        this.loadBalancerDescriptions = new ArrayList<LoadBalancerDescription>();
    }

    public void begin() {
        addNote();
        addVpc();
        addRouter();
    }

    private void addRouter() {
        // Add implicit router to graph
        addAWSNode(AttributeName.VPC_ROUTER, Stereotype.ROUTER);

        sendNodeAttributeAdded(sourceId, AttributeName.VPC_ROUTER, AttributeName.CIDR_BLOCK, vpc.getCidrBlock());
    }

    public boolean nextEvents() {
        // Populate graphs with artefacts of the UML base class Node
        this.addNodes();

        // Populate graph with artefacts of the UML base class Artefact
        this.addArtefacts();

        // Link the objects together
        this.addLinks();

        return false;
    }

    public void end() {
        this.clearSinks();
    }

    private void addNodes() {
        addInternetGateways();
        addNetworkAcls();
        addSecurityGroups();
        addEc2Instances();
        addLoadBalancers();
    }

    private void addAWSNode(String nodeId, String stereotype) {
        addGraphNode(nodeId, stereotype);
    }

    private void addGraphNode(String nodeId, String stereotype) {
        sendNodeAdded(sourceId, nodeId);
        sendNodeAttributeAdded(sourceId, nodeId, AttributeName.UI_LABEL, nodeId);
        sendNodeAttributeAdded(sourceId, nodeId, AttributeName.STEREOTYPE, stereotype);
    }

    private void addNote() {
        String nodeId = vpc.getVpcId() + AttributeName.TOKEN_SEPARATOR + Stereotype.NOTE;
        addGraphNode(nodeId, Stereotype.NOTE);
        sendNodeAttributeAdded(sourceId, nodeId, AttributeName.UI_LABEL, nodeId);
        sendNodeAttributeAdded(sourceId, nodeId, AttributeName.VPC, vpc.getVpcId());
        sendNodeAttributeAdded(sourceId, nodeId, AttributeName.CIDR_BLOCK, vpc.getCidrBlock());
    }

    private void addInternetGateways() {
        // Populate graph with internet gateways
        for (InternetGateway internetGateway : infrastructureClient.getInternetGateways(vpc)) {
            this.internetGateways.add(internetGateway);

            addAWSNode(internetGateway.getInternetGatewayId(), Stereotype.INTERNET_GATEWAY);

            for (Tag tag : internetGateway.getTags()) {
                if (tag.getKey().equalsIgnoreCase(AttributeName.NAME)) {
                    sendNodeAttributeAdded(sourceId, internetGateway.getInternetGatewayId(), AttributeName.NAME, tag.getValue());
                }
            }
        }
    }

    private void addNetworkAcls() {
        // Populate graph with Network ACLs
        for (NetworkAcl networkAcl : infrastructureClient.getNetworkAcls(vpc)) {
            this.networkAcls.add(networkAcl);
            addAWSNode(networkAcl.getNetworkAclId(), Stereotype.NETWORK_ACL);
        }
    }

    private void addSecurityGroups() {
        // Populate graph with Security Groups
        for (SecurityGroup securityGroup : infrastructureClient.getSecurityGroups(vpc)) {
            this.securityGroups.add(securityGroup);
            addAWSNode(securityGroup.getGroupId(), Stereotype.SECURITY_GROUP);
        }
    }

    private void addEc2Instances() {
        // Populate graph with EC2 instances
        for (Reservation reservation : infrastructureClient.getInstances(vpc)) {
            for (Instance instance : reservation.getInstances()) {
                this.instances.add(instance);

                addAWSNode(instance.getInstanceId(), Stereotype.EC2_INSTANCE);

                sendNodeAttributeAdded(sourceId, instance.getInstanceId(), AttributeName.NETWORK_INTERFACES, instance.getNetworkInterfaces());
                sendNodeAttributeAdded(sourceId, instance.getInstanceId(), AttributeName.SECURITY_GROUPS, instance.getSecurityGroups());
                sendNodeAttributeAdded(sourceId, instance.getInstanceId(), AttributeName.SUBNETS, instance.getSubnetId());
            }
        }
    }

    private void addLoadBalancers() {
        // Populate graph with load balancers (filtering on VPC needed here)
        for (LoadBalancerDescription loadBalancerDescription : infrastructureClient.getElasticLoadBalancers(vpc)) {
            this.loadBalancerDescriptions.add(loadBalancerDescription);

            addAWSNode(loadBalancerDescription.getLoadBalancerName(), Stereotype.ELASTIC_LB);
            sendNodeAttributeAdded(sourceId, loadBalancerDescription.getLoadBalancerName(), AttributeName.DNS_NAME, loadBalancerDescription.getDNSName());
        }
    }

    private void addArtefacts() {
        addRouteTables();
        addSubnets();
        addNetworkInterfaces();
    }

    private void addAWSArtefact(String nodeId, String stereotype) {
        addGraphNode(nodeId, stereotype);
    }

    private void addNetworkInterfaces() {
        // Populate graph with network interfaces
        for (NetworkInterface networkInterface : infrastructureClient.getNeworkInterfaces(vpc)) {
            this.networkInterfaces.add(networkInterface);

            addAWSArtefact(networkInterface.getNetworkInterfaceId(), Stereotype.ENI);
        }
    }

    private void addVpc() {
        addAWSArtefact(vpc.getVpcId(), Stereotype.VPC);
        sendNodeAttributeAdded(sourceId, vpc.getVpcId(), AttributeName.IS_DEFAULT, vpc.getIsDefault());
        sendNodeAttributeAdded(sourceId, vpc.getVpcId(), AttributeName.CIDR_BLOCK, vpc.getCidrBlock());
    }

    private void addSubnets() {
        // Populate graph with Subnets
        for (Subnet subnet : infrastructureClient.getSubnets(vpc)) {
            this.subnets.add(subnet);

            addAWSArtefact(subnet.getSubnetId(), Stereotype.SUBNET);
            sendNodeAttributeAdded(sourceId, subnet.getSubnetId(), AttributeName.IS_DEFAULT, subnet.isDefaultForAz());
            sendNodeAttributeAdded(sourceId, subnet.getSubnetId(), AttributeName.CIDR_BLOCK, subnet.getCidrBlock());
            sendNodeAttributeAdded(sourceId, subnet.getSubnetId(), AttributeName.AVAILABILITY_ZONE, subnet.getAvailabilityZone());
        }
    }

    private void addRouteTables() {
        // Populate graph with route tables
        for (RouteTable routeTable : infrastructureClient.getRouteTables(vpc)) {
            this.routeTables.add(routeTable);

            addAWSArtefact(routeTable.getRouteTableId(), Stereotype.ROUTE_TABLE);
        }
    }

    // Return whether a subnet is linked to any of the route tables configured for the VPC
    private boolean isLinkedSubnetRouteTable(List<RouteTable> routeTables, String subnet) {
        for (RouteTable routeTable : routeTables) {
            for(RouteTableAssociation routeTableAssociation : routeTable.getAssociations()) {
                if ((routeTableAssociation.getSubnetId() != null) &&
                        routeTableAssociation.getSubnetId().equalsIgnoreCase(subnet)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isLinkedSubnetToRouter(String subnet) {
        return vpcGraph.getNode(subnet).hasEdgeBetween(AttributeName.VPC_ROUTER);
    }

    private boolean vpcContainsSubnet(String subnetId) {
        return vpcGraph.getNode(subnetId) != null;
    }

    private boolean vpcContainsSecurityGroup(String securityGroupId) {
        return vpcGraph.getNode(securityGroupId) != null;
    }

    private void addLinks() {
        createLinkInternetGatewayToImplictRouter();

        createLinksRoutingTableToImplicitRouter();

        createLinksRouterToNetworkAcl();

        createLinksEC2InstanceToSubnetsAndSecurityGroups();

        createLinksElasticLoadBalancersToSubnetsAndSecurityGroups();

        createLinkImplicitRouterOrInternetGatewayToVpc();
    }

    private void addAWSAssociation(String edgeId, String fromNodeId, String toNodeId, boolean directed,
                                   String stereotype) {
        addGraphEdge(edgeId, fromNodeId, toNodeId, directed, stereotype);
    }

    private void addGraphEdge(String edgeId, String fromNodeId, String toNodeId, boolean directed, String stereotype) {
        sendEdgeAdded(sourceId, edgeId,fromNodeId, toNodeId, directed);
        sendEdgeAttributeAdded(sourceId, edgeId, AttributeName.UI_LABEL, "<<" + stereotype + ">>");
        sendEdgeAttributeAdded(sourceId, edgeId, AttributeName.STEREOTYPE, stereotype);
    }

    private void createLinksElasticLoadBalancersToSubnetsAndSecurityGroups() {
        // Link load balancers to subnets and security groups
        for (LoadBalancerDescription loadBalancerDescription : this.loadBalancerDescriptions) {
            if (loadBalancerDescription.getSubnets() != null) {
                for (String subnetId : loadBalancerDescription.getSubnets()) {
                    if (vpcContainsSubnet(subnetId)) {
                        addAWSAssociation(loadBalancerDescription.getLoadBalancerName() + AttributeName.TOKEN_SEPARATOR + subnetId,
                                loadBalancerDescription.getLoadBalancerName(), subnetId, false, Stereotype.OSI_LAYER2_LINK);
                    }
                }
            }

            if (loadBalancerDescription.getSecurityGroups() != null) {
                for (String securityGroup : loadBalancerDescription.getSecurityGroups()) {
                    if (vpcContainsSecurityGroup(securityGroup)) {
                        addAWSAssociation(loadBalancerDescription.getLoadBalancerName() + AttributeName.TOKEN_SEPARATOR
                                + securityGroup,
                                loadBalancerDescription.getLoadBalancerName(), securityGroup, false,
                                Stereotype.PACKET_FILTERING);
                    }
                }
            }
        }
    }

    private void createLinksEC2InstanceToSubnetsAndSecurityGroups() {
        // Link NICs to EC2 instances and security groups
        for (NetworkInterface networkInterface : this.networkInterfaces) {
            if (networkInterface.getAttachment().getInstanceId() != null) {
                String edgeId = networkInterface.getNetworkInterfaceId() +
                        AttributeName.TOKEN_SEPARATOR + networkInterface.getAttachment().getInstanceId();

                addAWSAssociation(edgeId, networkInterface.getAttachment().getInstanceId(),
                        networkInterface.getSubnetId(), false, Stereotype.OSI_LAYER2_LINK);

                for (GroupIdentifier groupIdentifier : networkInterface.getGroups()) {
                    edgeId = networkInterface.getAttachment().getInstanceId() + AttributeName.TOKEN_SEPARATOR + groupIdentifier.getGroupId();

                    addAWSAssociation(edgeId, groupIdentifier.getGroupId(),
                            networkInterface.getAttachment().getInstanceId(), false, Stereotype.PACKET_FILTERING);
                }
            }
            // delete NIC (connection between EC2 instance and subnet has been established).
            sendNodeRemoved(sourceId, networkInterface.getNetworkInterfaceId());
        }
    }

    private void createLinksRouterToNetworkAcl() {
        // Link router to network acl, and network acl to subnet
        for (NetworkAcl networkAcl : this.networkAcls) {
            String edgeId = networkAcl.getNetworkAclId() + AttributeName.TOKEN_SEPARATOR + AttributeName.VPC_ROUTER;

            addAWSAssociation(edgeId, networkAcl.getNetworkAclId(), AttributeName.VPC_ROUTER, false, Stereotype.OSI_LAYER2_LINK);

            for (NetworkAclAssociation networkAclAssociation : networkAcl.getAssociations()) {
                if (isLinkedSubnetToRouter(networkAclAssociation.getSubnetId())) {
                    sendEdgeRemoved(sourceId, AttributeName.VPC_ROUTER + AttributeName.TOKEN_SEPARATOR + networkAclAssociation.getSubnetId());

                    edgeId = networkAcl.getNetworkAclId() + AttributeName.TOKEN_SEPARATOR + networkAclAssociation.getSubnetId();

                    addAWSAssociation(edgeId, networkAcl.getNetworkAclId(), networkAclAssociation.getSubnetId(),
                            false, Stereotype.OSI_LAYER2_LINK);
                }
            }
        }
    }

    private void createLinksRoutingTableToImplicitRouter() {
        for (RouteTable routeTable : this.routeTables) {
            String edgeId = routeTable.getRouteTableId() + AttributeName.TOKEN_SEPARATOR + AttributeName.VPC_ROUTER;
            addAWSAssociation(edgeId,routeTable.getRouteTableId(), AttributeName.VPC_ROUTER, true, Stereotype.DEPENDENCY);

            // Create routing table associations that were configured explicitly
            for(RouteTableAssociation routeTableAssociation : routeTable.getAssociations()) {
                if (routeTableAssociation.getSubnetId() != null) {
                    // Link router to subnet
                    edgeId = AttributeName.VPC_ROUTER + AttributeName.TOKEN_SEPARATOR + routeTableAssociation.getSubnetId();
                    addAWSAssociation(edgeId, AttributeName.VPC_ROUTER, routeTableAssociation.getSubnetId(), false,
                            Stereotype.OSI_LAYER2_LINK);
                }
            }

            // Create implicit routing table associations (by virtue of being the main routing table)
            for(RouteTableAssociation routeTableAssociation : routeTable.getAssociations()) {
                for (Subnet subnet : this.subnets) {
                    if (routeTableAssociation.isMain() && !isLinkedSubnetRouteTable(routeTables, subnet.getSubnetId())) {
                        // Link router to subnet
                        edgeId = AttributeName.VPC_ROUTER + AttributeName.TOKEN_SEPARATOR + subnet.getSubnetId();
                        addAWSAssociation(edgeId, AttributeName.VPC_ROUTER, subnet.getSubnetId(), false,
                                Stereotype.OSI_LAYER2_LINK);
                    }
                }
            }
        }
    }

    private void createLinkInternetGatewayToImplictRouter() {
        for (InternetGateway internetGateway : this.internetGateways) {
            String edgeId = internetGateway.getInternetGatewayId() + AttributeName.TOKEN_SEPARATOR + AttributeName.VPC_ROUTER;
            addAWSAssociation(edgeId, internetGateway.getInternetGatewayId(), AttributeName.VPC_ROUTER, false, Stereotype.OSI_LAYER2_LINK);
        }
    }

    private void createLinkImplicitRouterOrInternetGatewayToVpc() {
        if (this.internetGateways.size() > 0) {
            for (InternetGateway internetGateway : this.internetGateways) {
                String edgeId = internetGateway.getInternetGatewayId() + AttributeName.TOKEN_SEPARATOR + vpc.getVpcId();
                addAWSAssociation(edgeId, internetGateway.getInternetGatewayId(), vpc.getVpcId(), false, Stereotype.OSI_LAYER2_LINK);
            }
        } else {
            String edgeId = AttributeName.VPC_ROUTER + AttributeName.TOKEN_SEPARATOR + vpc.getVpcId();
            addAWSAssociation(edgeId, AttributeName.VPC_ROUTER, vpc.getVpcId(), false, Stereotype.OSI_LAYER2_LINK);
        }
    }
}