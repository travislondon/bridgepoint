/*
 * generated by Xtext 2.9.2
 */
package org.xtuml.bp.xtext.masl.scoping

import com.google.inject.Inject
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.scoping.IScope
import org.xtuml.bp.xtext.masl.MASLExtensions
import org.xtuml.bp.xtext.masl.masl.behavior.AttributeReferential
import org.xtuml.bp.xtext.masl.masl.behavior.BehaviorPackage
import org.xtuml.bp.xtext.masl.masl.behavior.CodeBlock
import org.xtuml.bp.xtext.masl.masl.behavior.CreateExpression
import org.xtuml.bp.xtext.masl.masl.behavior.FindExpression
import org.xtuml.bp.xtext.masl.masl.behavior.ForStatement
import org.xtuml.bp.xtext.masl.masl.behavior.GenerateStatement
import org.xtuml.bp.xtext.masl.masl.behavior.NavigateExpression
import org.xtuml.bp.xtext.masl.masl.behavior.OperationCall
import org.xtuml.bp.xtext.masl.masl.behavior.SimpleFeatureCall
import org.xtuml.bp.xtext.masl.masl.behavior.SortOrderFeature
import org.xtuml.bp.xtext.masl.masl.behavior.TerminatorOperationCall
import org.xtuml.bp.xtext.masl.masl.structure.AssocRelationshipDefinition
import org.xtuml.bp.xtext.masl.masl.structure.DomainFunctionDefinition
import org.xtuml.bp.xtext.masl.masl.structure.DomainServiceDefinition
import org.xtuml.bp.xtext.masl.masl.structure.ObjectDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.ObjectDefinition
import org.xtuml.bp.xtext.masl.masl.structure.ObjectFunctionDefinition
import org.xtuml.bp.xtext.masl.masl.structure.ObjectServiceDefinition
import org.xtuml.bp.xtext.masl.masl.structure.Parameter
import org.xtuml.bp.xtext.masl.masl.structure.RegularRelationshipDefinition
import org.xtuml.bp.xtext.masl.masl.structure.RelationshipNavigation
import org.xtuml.bp.xtext.masl.masl.structure.StateDefinition
import org.xtuml.bp.xtext.masl.masl.structure.StructurePackage
import org.xtuml.bp.xtext.masl.masl.structure.SubtypeRelationshipDefinition
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorDefinition
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorFunctionDefinition
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorServiceDefinition
import org.xtuml.bp.xtext.masl.masl.structure.TransitionOption
import org.xtuml.bp.xtext.masl.masl.structure.TransitionRow
import org.xtuml.bp.xtext.masl.typesystem.CollectionType
import org.xtuml.bp.xtext.masl.typesystem.InstanceType
import org.xtuml.bp.xtext.masl.typesystem.MaslType
import org.xtuml.bp.xtext.masl.typesystem.MaslTypeProvider
import org.xtuml.bp.xtext.masl.typesystem.NamedType
import org.xtuml.bp.xtext.masl.typesystem.StructureType

import static org.eclipse.xtext.scoping.Scopes.*

import static extension org.eclipse.xtext.EcoreUtil2.*

/**
 * This class contains custom scoping description.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
class MASLScopeProvider extends AbstractMASLScopeProvider {

	@Inject extension StructurePackage structurePackage
	@Inject extension BehaviorPackage 
	
	@Inject extension MASLExtensions
	
	@Inject extension MaslTypeProvider
	
	@Inject ResourceDescriptionsProvider resourceDescriptionsProvider

	override getScope(EObject context, EReference reference) {
		switch reference {
			case featureCall_Feature:
				return context.featureScope
			case generateStatement_Event: {
				if(context instanceof GenerateStatement) 
					return createObjectScope(context.object, [events])
			}
			case createArgument_CurrentState: {
				val contextObject = context.getContainerOfType(CreateExpression)?.object
				if(contextObject != null) 
					return createObjectScope(contextObject, [states])				
			}
			case createArgument_Attribute: {
				val contextObject = context.getContainerOfType(CreateExpression)?.object
				if(contextObject != null) 
					return createObjectScope(contextObject, [attributes])				
			}
			case attributeReferential_Attribute: {
				if(context instanceof AttributeReferential) 
					return createObjectScope(context.referredObject, [attributes])
			}
			case generateStatement_Event: {
				if(context instanceof GenerateStatement) 
					return createObjectScope(context.object, [events])
			}
			case transitionRow_Start: {
				if(context instanceof TransitionRow) 
					return createObjectScope(context.getContainerOfType(ObjectDefinition), [states])
			}
			case transitionOption_EndState: {
				if(context instanceof TransitionOption) 
					return createObjectScope(context.getContainerOfType(ObjectDefinition), [states])		
			}
			case terminatorOperationCall_TerminalOperation: {
				if(context instanceof TerminatorOperationCall) {
					val receiver = context?.receiver
					if(receiver instanceof SimpleFeatureCall) {
						val feature = receiver.feature
						if(feature instanceof TerminatorDefinition) {
							return scopeFor(feature.services + feature.functions)		
						}						
					}
				} 
			}
			case structurePackage.relationshipNavigation_ObjectOrRole: {
				if(context instanceof RelationshipNavigation) {
					val relationShip = context.relationship
					switch relationShip {
						RegularRelationshipDefinition:
							return scopeFor(#{relationShip.forwards, relationShip.backwards,
								relationShip.forwards.from, relationShip.forwards.to, 
								relationShip.backwards.from, relationShip.backwards.to
							})
						AssocRelationshipDefinition:
							return scopeFor(#{relationShip.forwards, relationShip.backwards,
								relationShip.forwards.from, relationShip.forwards.to, 
								relationShip.backwards.from, relationShip.backwards.to,
								relationShip.object
							})
						SubtypeRelationshipDefinition:
							return scopeFor(relationShip.subtypes)
							
					}
 				}
			}
			case structurePackage.relationshipNavigation_Object: {
				if(context instanceof RelationshipNavigation) {
					val relationShip = context.relationship
					switch relationShip {
						RegularRelationshipDefinition:
							return scopeFor(#{
								relationShip.forwards.from, relationShip.forwards.to, 
								relationShip.backwards.from, relationShip.backwards.to
							})
						AssocRelationshipDefinition:
							return scopeFor(#{
								relationShip.forwards.from, relationShip.forwards.to, 
								relationShip.backwards.from, relationShip.backwards.to
							})
						SubtypeRelationshipDefinition:
							return scopeFor(relationShip.subtypes)
					}
 				}
			}
		}
		super.getScope(context, reference)
	}
	
	private def <T extends EObject> createObjectScope(ObjectDefinition object, (ObjectDefinition)=>Iterable<? extends T> reference, IScope parentScope) {
		if(object == null || object.eIsProxy)
			return IScope.NULLSCOPE
		val index = resourceDescriptionsProvider.getResourceDescriptions(object.eResource)
		scopeFor(object
			.getAllSupertypes(index)
			.map[reference.apply(it)]
			.flatten, parentScope)
	}
	
	private def <T extends EObject> createObjectScope(ObjectDefinition object, (ObjectDefinition)=>Iterable<? extends T> reference) {
		createObjectScope(object, reference, IScope.NULLSCOPE)
	}

	private def <T extends EObject> createObjectScope(ObjectDeclaration object, (ObjectDefinition)=>Iterable<? extends T> reference, IScope parentScope) {
		if(object == null || object.eIsProxy)
			return IScope.NULLSCOPE
		val index = resourceDescriptionsProvider.getResourceDescriptions(object.eResource)
		val definition = object.getObjectDefinition(index)
		scopeFor(definition
			.getAllSupertypes(index)
			.map[reference.apply(it)]
			.flatten, parentScope)
	}

	private def <T extends EObject> createObjectScope(ObjectDeclaration object, (ObjectDefinition)=>Iterable<? extends T> reference) {
		createObjectScope(object, reference, IScope.NULLSCOPE)
	}
	
	private def dispatch IScope getFeatureScope(SortOrderFeature call) {
		call.getContainerOfType(NavigateExpression).maslType.componentType.typeFeatureScope
	}
	
	private def dispatch IScope getFeatureScope(SimpleFeatureCall call) {
		if(call.receiver == null) {
			return call.getLocalSimpleFeatureScope(delegate.getScope(call, featureCall_Feature), false)
		} else {
			val type = call.receiver.maslType
			return getTypeFeatureScope(type)
		}
	}
	
	private def IScope getTypeFeatureScope(MaslType type) {
		switch type {
			InstanceType:
				return type.instance.createObjectScope[attributes]
			NamedType: {
				val innerType = type.type
				if (innerType instanceof StructureType)
					return scopeFor(innerType.structureType.components)
			}
			default:
				return IScope.NULLSCOPE
		}
	}
	
	private def IScope getLocalSimpleFeatureScope(EObject expr, IScope parentScope, boolean isFindExpression_Expression) {
		if(expr == null)
			return IScope.NULLSCOPE
		val parent = expr.eContainer
		switch expr {
			FindExpression: {
				if(!isFindExpression_Expression) {
					val maslType = expr.expression.maslType
					val instance = 
						switch maslType {
							InstanceType: 
								 maslType.instance
							CollectionType case maslType.elementType instanceof InstanceType:
								(maslType.elementType as InstanceType).instance
							default:
								null							
						}
					if (instance != null)
						return instance.createObjectScope([attributes], parent.getLocalSimpleFeatureScope(parentScope, false))
				}
			}
			CodeBlock:
				return scopeFor(expr.variables, parent.getLocalSimpleFeatureScope(parentScope, false))
			ForStatement:
				return scopeFor(#[expr.variable], parent.getLocalSimpleFeatureScope(parentScope, false))
			DomainFunctionDefinition:
				return scopeFor(expr.parameters, parentScope)
			DomainServiceDefinition:
				return scopeFor(expr.parameters, parentScope)
			ObjectFunctionDefinition:
				return getSimpleFeatureScopeForObjectAction(expr.parameters, expr.object, parentScope)
			ObjectServiceDefinition:
				return getSimpleFeatureScopeForObjectAction(expr.parameters, expr.object, parentScope)
			StateDefinition:
				return getSimpleFeatureScopeForObjectAction(expr.parameters, expr.object, parentScope)
			TerminatorFunctionDefinition:
				return scopeFor(expr.parameters, parentScope) 
			TerminatorServiceDefinition:
				return scopeFor(expr.parameters, parentScope) 
		}
		return parent.getLocalSimpleFeatureScope(parentScope, expr.eContainmentFeature == findExpression_Expression)
	}
	
	private def getSimpleFeatureScopeForObjectAction(List<Parameter> parameters, ObjectDeclaration context, IScope parentScope) {
		scopeFor(parameters, context.createObjectScope([attributes], parentScope))
	}
	
	private def dispatch IScope getFeatureScope(OperationCall call) {
		if(call.receiver == null) {
			return call.getLocalOperationScope(delegate.getScope(call, featureCall_Feature))
		} else {
			val type = call.receiver.maslType
			switch type {
				InstanceType:
					return type.instance.createObjectScope[services + functions]
			}
		}
		return IScope.NULLSCOPE
	}
	
	private def IScope getLocalOperationScope(EObject expr, IScope parentScope) {
		if(expr == null)
			return IScope.NULLSCOPE
		val parent = expr.eContainer
		switch expr {
			DomainFunctionDefinition:
				return scopeFor(expr.domain.services + expr.domain.functions, parentScope)
			DomainServiceDefinition:
				return scopeFor(expr.domain.services + expr.domain.functions, parentScope)
			ObjectFunctionDefinition:
				return expr.object.createObjectScope([functions + services], parentScope)
			ObjectServiceDefinition:
				return expr.object.createObjectScope([functions + services], parentScope)
			StateDefinition:
				return expr.object.createObjectScope([functions + services], parentScope)
			TerminatorFunctionDefinition:
				return scopeFor(expr.terminator.functions + expr.terminator.services, parentScope)
			TerminatorServiceDefinition:
				return scopeFor(expr.terminator.functions + expr.terminator.services, parentScope)
			default: 
				return parent.getLocalOperationScope(parentScope)
		}
	}
	
	private def dispatch IScope getFeatureScope(EObject call) {
		IScope.NULLSCOPE
	}
}
