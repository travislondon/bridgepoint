package org.xtuml.bp.io.xmi.translate;

import static java.util.Map.entry;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IgnoreType;
import org.xtuml.bp.io.xmi.translate.processors.XtumlTypeProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.ActorParticipantProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.AssociationProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.AttributeProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.ClassAsSubtypeProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.CommentProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.ComponentProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.DataTypeProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.EnumerationDataTypeProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.EnumeratorProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.HeaderProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.InstanceStateMachineProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.InterfaceOperationProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.InterfaceProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.ModelClassProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.OperationParameterProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.OperationProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.PackageProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.PropertyParameterProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.ProvisionProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.RequirementProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.StateMachineStateProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.TransitionProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.UseCaseAssociationProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.UseCaseParticipantProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.GraphicalElementProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.ModelProcessorSQL;

public class XtumlMapper {

	static enum MapperType {
		SQL,
	}

	static Map<MapperType, Map<String, XtumlTypeProcessor>> mappers = Map.of(MapperType.SQL, Map.ofEntries(
			entry("HEADER", new HeaderProcessorSQL()), entry("S_DT", new DataTypeProcessorSQL()),
			entry("O_OBJ", new ModelClassProcessorSQL()), entry("EP_PKG", new PackageProcessorSQL()),
			entry("O_ATTR", new AttributeProcessorSQL()), entry("C_C", new ComponentProcessorSQL()),
			entry("C_I", new InterfaceProcessorSQL()), entry("C_IO", new InterfaceOperationProcessorSQL()),
			entry("O_TFR", new OperationProcessorSQL()), entry("C_P", new ProvisionProcessorSQL()),
			entry("C_R", new RequirementProcessorSQL()), entry("O_TPARM", new OperationParameterProcessorSQL()),
			entry("C_PP", new PropertyParameterProcessorSQL()), entry("PE_C", new CommentProcessorSQL()),
			entry("S_EDT", new EnumerationDataTypeProcessorSQL()), entry("S_ENUM", new EnumeratorProcessorSQL()),
			entry("IA_UCP", new UseCaseParticipantProcessorSQL()), entry("SQ_AP", new ActorParticipantProcessorSQL()),
			entry("SM_ISM", new InstanceStateMachineProcessorSQL()),
			entry("SM_STATE", new StateMachineStateProcessorSQL()), entry("R_REL", new AssociationProcessorSQL()),
			entry("R_SUB", new ClassAsSubtypeProcessorSQL()), entry("SM_TXN", new TransitionProcessorSQL()),
			entry("UC_UCA", new UseCaseAssociationProcessorSQL())));
	static Map<MapperType, Map<String, XtumlTypeProcessor>> graphicalMappers = Map.of(MapperType.SQL,
			Map.of("GD_MD", new ModelProcessorSQL(), "GD_GE", new GraphicalElementProcessorSQL()));

	public static String process(MapperType type, ModelElement e, String mapping) {
		// mapping can be either a single key_lett value or an array of values
		String actualMapping = getMappingToUse(e, mapping);
		Map<String, XtumlTypeProcessor> processors = mappers.get(type);
		XtumlTypeProcessor xtumlTypeProcessor = processors.get(actualMapping);
		if (xtumlTypeProcessor == null) {
			// check for graphical processor
			processors = graphicalMappers.get(type);
			xtumlTypeProcessor = processors.get(actualMapping);
		}
		if (xtumlTypeProcessor == null) {
			XMITranslator.logNoProcessor("TODO: Implement processor for " + actualMapping);
			return "";
		} else {
			String result = xtumlTypeProcessor.process(e, actualMapping);
			XMITranslator.log(result);
			return result;
		}
	}

	/**
	 * UML2 overloads some elements, like parameter or association.
	 * 
	 * In these cases xtUML uses concrete elements for each parent type.
	 * 
	 * The currently supported mapping types are:
	 * 
	 * # overloaded params mapping="class:O_TPARM, interface:C_PP, function:S_SPARM"
	 * 
	 * # overloaded asssociation
	 * mapping="connection?O_OBJ-O_OBJ:R_REL,connection?IA_UCP-Any:UC_UCA"
	 */
	private static String getMappingToUse(ModelElement e, String mapping) {
		String[] mappings = mapping.split(",");
		if (mappings.length > 1) {
			// break down according to mapping extension type
			Optional<String> expectedMapping = Stream.of(mappings).filter(m -> {
				String[] ancestorChildEntries = m.split(":");
				if (ancestorChildEntries.length > 1) {
					boolean foundAncestor = false;
					if (ancestorChildEntries[0].startsWith("connection")) {
						// reuse association process ignore handling
						AssociationProcessorSQL assocProcessor = new AssociationProcessorSQL();
						assocProcessor.setModelElement(e);
						// if ignored just pass along as R_REL, which will then be ignored
						// this prevents TODO logging for non-handled mappings
						if (assocProcessor.ignoreTranslation() == IgnoreType.HANDLED
								&& m.split(":")[1].equals("R_REL")) {
							return true;
						}
						// dealing with association overload
						String[] connectionDefParts = ancestorChildEntries[0].split("\\?");
						if (connectionDefParts.length == 2) {
							String[] endTypes = connectionDefParts[1].split("-");
							Collection<?> ends = e.getSetAttribute("ownedends");
							int matches = 0;
							for (Iterator<?> iter = ends.iterator(); iter.hasNext();) {
								ModelElement end = (ModelElement) iter.next();
								ModelElement owningElement = end.getRefAttribute("propertytype");
								if (owningElement != null) {
									String endMapping = owningElement.getType().getMapping();
									for (int i = 0; i < endTypes.length; i++) {
										if (endTypes[i].equals(endMapping)) {
											matches++;
											break;
										}
									}
								}
							}
							if (Stream.of(endTypes).filter(s -> s.equals("Any")).findAny().isPresent()) {
								// just need one match
								foundAncestor = matches == 1;
							} else {
								// must match two
								foundAncestor = matches == 2;
							}
						}
					} else {
						// dealing with parent/child overload
						// if any ancestor type matches elements owner type
						ModelElement owner = e.getOwner();
						while (owner != null) {
							if (owner.getType().getName().equals(ancestorChildEntries[0].trim())) {
								foundAncestor = true;
								break;
							}
							owner = owner.getOwner();
						}
					}
					return foundAncestor;
				}
				return false;
			}).findFirst();
			if (expectedMapping.isPresent()) {
				return expectedMapping.get().split(":")[1];
			}
		}
		return mapping;
	}

}