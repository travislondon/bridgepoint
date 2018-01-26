//========================================================================
//
//File:      $RCSfile: ShapeGraphicalNodeEditPolicy.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/01/10 23:05:51 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//======================================================================== 
//
package org.xtuml.bp.ui.graphics.policies;

import java.lang.reflect.Method;
import java.util.UUID;

import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ReconnectRequest;
import org.xtuml.bp.core.End_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.ui.canvas.Cl_c;
import org.xtuml.bp.ui.canvas.ConnectorSpecification_c;
import org.xtuml.bp.ui.canvas.Connector_c;
import org.xtuml.bp.ui.canvas.ContainingShape_c;
import org.xtuml.bp.ui.canvas.ElementSpecification_c;
import org.xtuml.bp.ui.canvas.GraphicalElement_c;
import org.xtuml.bp.ui.canvas.ModelTool_c;
import org.xtuml.bp.ui.canvas.ShapeTerminal_c;
import org.xtuml.bp.ui.canvas.Shape_c;
import org.xtuml.bp.ui.canvas.TerminalSpecification_c;
import org.xtuml.bp.ui.canvas.WhitespaceTerminal_c;
import org.xtuml.bp.ui.graphics.commands.UpdateEndPointLocationCommand;
import org.xtuml.bp.ui.graphics.parts.ConnectorEditPart;
import org.xtuml.bp.ui.graphics.parts.DiagramEditPart;

public class ShapeGraphicalNodeEditPolicy extends ConnectionPolicy {

	@Override
	public NonRootModelElement getEndTerm(ElementSpecification_c newSpec) {
		Shape_c shape = (Shape_c) getHost().getModel();
		final ElementSpecification_c sourceSpec = ElementSpecification_c
				.getOneGD_ESOnR10(GraphicalElement_c.getOneGD_GEOnR2(shape));
		NonRootModelElement endTerm = ShapeTerminal_c.getOneTS_SHTOnR201(
				TerminalSpecification_c
						.getManyTS_TSPsOnR203(ConnectorSpecification_c
								.getManyTS_CSPsOnR200(newSpec)),
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((ShapeTerminal_c) candidate).getOoa_type() == sourceSpec
								.getOoa_type();
					}
				});
		if (endTerm == null) {
			// if this is a container, then also check for whitespace
			ContainingShape_c cs = ContainingShape_c.getOneGD_CTROnR28(shape);
			if (cs != null) {
				endTerm = WhitespaceTerminal_c
						.getOneTS_WSTOnR201(TerminalSpecification_c
								.getManyTS_TSPsOnR203(ConnectorSpecification_c
										.getManyTS_CSPsOnR200(newSpec)));
			}
		}
		return endTerm;
	}

	@Override
	public NonRootModelElement getStartTerm(ElementSpecification_c newSpec) {
		Shape_c shape = (Shape_c) getHost().getModel();
		final ElementSpecification_c sourceSpec = ElementSpecification_c
				.getOneGD_ESOnR10(GraphicalElement_c.getOneGD_GEOnR2(shape));
		NonRootModelElement startTerm = ShapeTerminal_c.getOneTS_SHTOnR201(
				TerminalSpecification_c
						.getManyTS_TSPsOnR202(ConnectorSpecification_c
								.getManyTS_CSPsOnR200(newSpec)),
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((ShapeTerminal_c) candidate).getOoa_type() == sourceSpec
								.getOoa_type();
					}
				});
		if (startTerm == null) {
			// if this is a container, then also check for whitespace
			ContainingShape_c cs = ContainingShape_c.getOneGD_CTROnR28(shape);
			if (cs != null) {
				startTerm = WhitespaceTerminal_c
						.getOneTS_WSTOnR201(TerminalSpecification_c
								.getManyTS_TSPsOnR202(ConnectorSpecification_c
										.getManyTS_CSPsOnR200(newSpec)));
			}
		}
		return startTerm;
	}

	@Override
	protected boolean isReconnectAllowed(ReconnectRequest request,
			boolean source) {
		ConnectionEditPart connectionEditPart = request.getConnectionEditPart();
		Connector_c connector = (Connector_c) connectionEditPart.getModel();
		ElementSpecification_c spec = ElementSpecification_c
				.getOneGD_ESOnR10(GraphicalElement_c.getOneGD_GEOnR2(connector));
		ConnectorSpecification_c cs = ConnectorSpecification_c
				.getOneTS_CSPOnR200(spec);
		TerminalSpecification_c[] termSpecs = null;
		if (source)
			termSpecs = TerminalSpecification_c.getManyTS_TSPsOnR202(cs);
		else
			termSpecs = TerminalSpecification_c.getManyTS_TSPsOnR203(cs);
		Shape_c shape = (Shape_c) getHost().getModel();
		ContainingShape_c container = ContainingShape_c
				.getOneGD_CTROnR28(shape);
		if (container != null)
			return true;
		ElementSpecification_c shapeSpec = ElementSpecification_c
				.getOneGD_ESOnR10(GraphicalElement_c.getOneGD_GEOnR2(shape));
		for (int i = 0; i < termSpecs.length; i++) {
			ShapeTerminal_c st = ShapeTerminal_c
					.getOneTS_SHTOnR201(termSpecs[i]);
			if (st != null) {
				if (st.getOoa_type() == shapeSpec.getOoa_type())
					return true;
			}
		}
		return false;
	}

    @Override
    protected Command getSpecializedReconnectTargetCommand(final ReconnectRequest request) {
        // if the host is a container shape then return an
        // update end point command
        Shape_c shape = (Shape_c) getHost().getModel();
        ContainingShape_c container = ContainingShape_c.getOneGD_CTROnR28(shape);
        if (container != null) {
            request.setTargetEditPart(getHost().getParent());
            return new UpdateEndPointLocationCommand(request);
        }
        if (request.getConnectionEditPart().getTarget() != getHost()) {
            // if the host has the "moveAssociation" operation reconnect
            final Method moveMethod = Cl_c.findMethod(getConnectionRepresents(request), "Moveassociation", new Class[] { UUID.class, int.class, UUID.class });
            if ( moveMethod != null ) {
                return new Command("Retarget Connection") {
                    @Override
                    public void execute() {
                        Object result = Cl_c.doMethod( moveMethod, getConnectionRepresents(request),
                            new Object[] { Cl_c.Getooa_idfrominstance(getConnectionTargetRepresents(request)), End_c.End, Cl_c.Getooa_idfrominstance(getHostRepresents()) });
                        if (result instanceof Boolean) {
                            if (!((Boolean) result).booleanValue()) return;
                        }
                        // finalize the connector, this call will create the necessary graphical elements
                        ModelTool_c tool = ModelTool_c.getOneCT_MTLOnR100(getHostModel());
                        associateTerminalSpecs(GraphicalElement_c.getOneGD_GEOnR2((Connector_c) request.getConnectionEditPart().getModel()));
                        tool.Moveconnector(((Connector_c) request.getConnectionEditPart().getModel()).getElementid(),
                            ((Shape_c)request.getConnectionEditPart().getTarget().getModel()).getElementid(),
                            End_c.End,
                            ((Shape_c)getHost().getModel()).getElementid() );
                        ((ConnectorEditPart) request.getConnectionEditPart()).transferLocation();
                        DiagramEditPart diagramPart = (DiagramEditPart) request.getConnectionEditPart().getViewer().getContents();
                        diagramPart.resizeContainer();
                    }
                };
            }
        }
        return null;
    }

    @Override
    protected Command getSpecializedReconnectSourceCommand(final ReconnectRequest request) {
        if (request.getConnectionEditPart().getSource() != getHost()) {
            // if the host has the "moveAssociation" operation reconnect
            final Method moveMethod = Cl_c.findMethod(getConnectionRepresents(request), "Moveassociation", new Class[] { UUID.class, int.class, UUID.class });
            if ( moveMethod != null ) {
                return new Command("Retarget Connection") {
                    @Override
                    public void execute() {
                        Object result = Cl_c.doMethod( moveMethod, getConnectionRepresents(request),
                            new Object[] { Cl_c.Getooa_idfrominstance(getConnectionSourceRepresents(request)), End_c.Start, Cl_c.Getooa_idfrominstance(getHostRepresents()) });
                        if (result instanceof Boolean) {
                            if (!((Boolean) result).booleanValue()) return;
                        }
                        // finalize the connector, this call will create the necessary graphical elements
                        ModelTool_c tool = ModelTool_c.getOneCT_MTLOnR100(getHostModel());
                        associateTerminalSpecs(GraphicalElement_c.getOneGD_GEOnR2((Connector_c) request.getConnectionEditPart().getModel()));
                        tool.Moveconnector(((Connector_c) request.getConnectionEditPart().getModel()).getElementid(),
                            ((Shape_c)request.getConnectionEditPart().getSource().getModel()).getElementid(),
                            End_c.Start,
                            ((Shape_c)getHost().getModel()).getElementid() );
                        ((ConnectorEditPart) request.getConnectionEditPart()).transferLocation();
                        DiagramEditPart diagramPart = (DiagramEditPart) request.getConnectionEditPart().getViewer().getContents();
                        diagramPart.resizeContainer();
                    }
                };
            }
        }
        return null;
    }

    protected Object getConnectionTargetRepresents( ReconnectRequest request ) {
		Object model = request.getConnectionEditPart().getTarget().getModel();
		if (model instanceof Connector_c) {
			return GraphicalElement_c.getOneGD_GEOnR2((Connector_c) model)
					.getRepresents();
		}
		else if (model instanceof Shape_c) {
			return GraphicalElement_c.getOneGD_GEOnR2((Shape_c) model)
					.getRepresents();
		}
		return null;
	}

    protected Object getConnectionSourceRepresents( ReconnectRequest request ) {
		Object model = request.getConnectionEditPart().getSource().getModel();
		if (model instanceof Connector_c) {
			return GraphicalElement_c.getOneGD_GEOnR2((Connector_c) model)
					.getRepresents();
		}
		else if (model instanceof Shape_c) {
			return GraphicalElement_c.getOneGD_GEOnR2((Shape_c) model)
					.getRepresents();
		}
		return null;
	}

}
