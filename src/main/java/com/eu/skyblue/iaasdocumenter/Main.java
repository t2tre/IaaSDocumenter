package com.eu.skyblue.iaasdocumenter;

import com.amazonaws.regions.Regions;
import com.eu.skyblue.iaasdocumenter.documenter.IaasDocumenter;
import com.eu.skyblue.iaasdocumenter.documenter.aws.DocumenterFactory;
import com.eu.skyblue.iaasdocumenter.renderer.*;
import com.eu.skyblue.iaasdocumenter.renderer.algo.OrthogonalLayoutAlgorithm;
import com.eu.skyblue.iaasdocumenter.ui.CommandLineInterface;
import com.eu.skyblue.iaasdocumenter.uml.IaaSProfile;
import com.eu.skyblue.iaasdocumenter.utils.Logger;
import org.graphstream.graph.Graph;
import org.graphstream.ui.layout.HierarchicalLayout;
import org.graphstream.ui.view.Viewer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: raye
 * Date: 13/06/15
 * Time: 09:59
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    private static IaasDocumenter iaasDocumenter;
    public static void main(String[] args) {
        System.out.println("*** IaaSDocumenter! ***");
        CommandLineInterface cli = new CommandLineInterface();
        cli.parseArgs(args);

        /*
        Logger logger = new Logger(Boolean.TRUE);
        IaaSProfile iaaSProfile = new IaaSProfile(logger);

        // AWS polling
        try {
            iaasDocumenter = DocumenterFactory.createIaasDocumenter(Regions.US_WEST_2);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        iaasDocumenter.createInventory();

        // Debug stuff - should be moved into a renderer
        // vpc-8762c3e2 (large), vpc-9371f0f6 (small), vpc-190cb27c (medium)
        String testGraph = "vpc-190cb27c";
        //String testGraph = "vpc-8762c3e2";
        List<Graph> g = iaasDocumenter.getGraphs();
        for (Graph graph : iaasDocumenter.getGraphs()) {
            if (graph.getId().equalsIgnoreCase(testGraph)) {
                System.out.println(">>>>>>>> " + graph.getId());
                //Viewer v = graph.display(false);
                //HierarchicalLayout hl = new HierarchicalLayout();
                //v.enableAutoLayout(hl);

                GraphRenderer xmiRenderer = new XMIRenderer(iaaSProfile, logger);
                xmiRenderer.render(graph, "/Users/raye/tmp/" + graph.getId());

                //GraphicalRenderer
                //GraphRenderer graphicalRenderer = new GraphicalRenderer(logger);
                //graphicalRenderer.render(graph, "/Users/raye/tmp/" + graph.getId() + ".svg");

                //OrthogonalLayoutAlgorithm orthogonalLayoutAlgorithm1 = new OrthogonalLayoutAlgorithm(logger);
                //orthogonalLayoutAlgorithm1.init(graph);
                //orthogonalLayoutAlgorithm1.compute();

                //GraphRenderer svgRenderer = new SVGFormatRenderer(logger);
                //svgRenderer.render(graph, "/Users/raye/tmp/" + graph.getId());

                //GraphRenderer pdfRenderer = new PDFFormatRenderer(logger);
                //pdfRenderer.render(graph, "/Users/raye/tmp/" + graph.getId());



                //Iterator<Node> nodeIterator = graph.iterator();
                //while (nodeIterator.hasNext()) {
                //    System.out.println(nodeIterator.next().getId());
                //}
            }
            System.out.println("--------------------------------------------");
        }
        */




        //XMIRenderer xmiRenderer = new XMIRenderer(iaaSProfile);
    }
}
