package com.eu.skyblue.iaasdocumenter.renderer;

import com.eu.skyblue.iaasdocumenter.utils.Logger;
import de.erichseifert.vectorgraphics2d.EPSGraphics2D;
import org.graphstream.graph.Graph;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: raye
 * Date: 09/08/15
 * Time: 01:40
 * To change this template use File | Settings | File Templates.
 */
public class EPSFormatRenderer extends AbstractGraphicalFormatRenderer implements GraphRenderer {
    private static final String EPS_FILE_EXTENSION = ".eps";
    private Logger logger;

    public EPSFormatRenderer(Logger logger) {
        super(logger);
        this.logger = logger;
    }

    public UMLDeploymentDiagram createDiagram() {
        getLayoutAlgorithm().getDiagramWidth();
        EPSGraphics2D document = new EPSGraphics2D(0.0, 0.0, getLayoutAlgorithm().getDiagramWidth(),
                getLayoutAlgorithm().getDiagramHeght());
        document.setColor(Color.darkGray);
        document.setStroke(new BasicStroke(0.2F));
        return new UMLDeploymentDiagram(document);
    }

    public void render(Graph graph, String filePath) {
        super.render(graph, filePath);
        getLayoutAlgorithm().init(graph);
        getLayoutAlgorithm().compute();

        UMLDeploymentDiagram umlDeploymentDiagram = this.createDiagram();

        placeArtefacts(graph, umlDeploymentDiagram);
        save(filePath, (EPSGraphics2D)umlDeploymentDiagram.getDocument());
    }

    public void save(String filePath, EPSGraphics2D document) {
        filePath = filePath + EPS_FILE_EXTENSION;
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(filePath);
            file.write(document.getBytes());
        } catch (IOException e) {
            logger.err("IOException: %s, while writing file", e.getMessage(), filePath);
        } finally {
            try {
                file.close();
            } catch (IOException e) {
                logger.err("IOException: %s, while closing file: %s", e.getMessage(), filePath);
            }
        }
    }
}
