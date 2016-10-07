package org.xtuml.bp.xtext.masl

import com.google.inject.Inject
import java.util.Set
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.resource.IResourceDescriptions
import org.eclipse.xtext.resource.ISelectable
import org.xtuml.bp.xtext.masl.masl.behavior.Expression
import org.xtuml.bp.xtext.masl.masl.behavior.OperationCall
import org.xtuml.bp.xtext.masl.masl.structure.AbstractNamed
import org.xtuml.bp.xtext.masl.masl.structure.DomainDefinition
import org.xtuml.bp.xtext.masl.masl.structure.DomainFunctionDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.DomainFunctionDefinition
import org.xtuml.bp.xtext.masl.masl.structure.DomainServiceDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.DomainServiceDefinition
import org.xtuml.bp.xtext.masl.masl.structure.ObjectDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.ObjectDefinition
import org.xtuml.bp.xtext.masl.masl.structure.ObjectFunctionDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.ObjectFunctionDefinition
import org.xtuml.bp.xtext.masl.masl.structure.ObjectServiceDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.ObjectServiceDefinition
import org.xtuml.bp.xtext.masl.masl.structure.RelationshipEnd
import org.xtuml.bp.xtext.masl.masl.structure.RelationshipNavigation
import org.xtuml.bp.xtext.masl.masl.structure.StateDefinition
import org.xtuml.bp.xtext.masl.masl.structure.StructurePackage
import org.xtuml.bp.xtext.masl.masl.structure.SubtypeRelationshipDefinition
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorFunctionDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorFunctionDefinition
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorServiceDeclaration
import org.xtuml.bp.xtext.masl.masl.structure.TerminatorServiceDefinition
import org.xtuml.bp.xtext.masl.masl.types.TypeDeclaration

/**
 * Utility methods for MASL model elements.
 */
class MASLExtensions {

	@Inject extension IQualifiedNameProvider
	@Inject extension StructurePackage maslPackage

	/**
	 * @return the name of the domain the argument is contained in without resolving cross-references. 
	 */
	def String getDomainName(EObject it) {
		switch it {
			DomainDefinition:
				name
			ObjectServiceDefinition:
				getReferredElementName(objectServiceDefinition_Domain)
			ObjectFunctionDefinition:
				getReferredElementName(objectFunctionDefinition_Domain)
			StateDefinition:
				getReferredElementName(stateDefinition_Domain)
			DomainServiceDefinition:
				getReferredElementName(domainServiceDefinition_Domain)
			DomainFunctionDefinition:
				getReferredElementName(domainFunctionDefinition_Domain)
			TerminatorServiceDefinition:
				getReferredElementName(terminatorServiceDefinition_Domain)
			TerminatorFunctionDefinition:
				getReferredElementName(terminatorFunctionDefinition_Domain)
			default:
				eContainer?.domainName
		}
	}

	/**
	 * @return the name of the object the argument is contained in without resolving cross-references. 
	 */
	def String getObjectName(EObject it) {
		switch it {
			ObjectServiceDefinition:
				getReferredElementName(objectServiceDefinition_Object)
			ObjectFunctionDefinition:
				getReferredElementName(objectFunctionDefinition_Object)
			StateDefinition:
				getReferredElementName(stateDefinition_Object)
			default:
				eContainer?.objectName
		}
	}

	/**
	 * @return the name of the terminator the argument is contained in without resolving cross-references. 
	 */
	def String getTerminatorName(EObject it) {
		switch it {
			TerminatorServiceDefinition:
				getReferredElementName(terminatorServiceDefinition_Terminator)
			TerminatorFunctionDefinition:
				getReferredElementName(terminatorFunctionDefinition_Terminator)
			default:
				eContainer?.terminatorName
		}
	}

	private def String getReferredElementName(EObject it, EReference ref) {
		val value = eGet(ref, false)
		if (value instanceof AbstractNamed) {
			if (value.eIsProxy) {
				NodeModelUtils.findNodesForFeature(it, ref).map[leafNodes].flatten.filter[!hidden].map[text].join
			} else {
				value.eGet(abstractNamed_Name) as String
			}
		}
	}

	/**
	 * @return all supertypes including the object itself.
	 */
	def Iterable<ObjectDefinition> getAllSupertypes(ObjectDefinition object, IResourceDescriptions index) {
		val result = newHashSet
		internalGetAllSupertypes(object, index, result)
		result
	}

	private def void internalGetAllSupertypes(ObjectDefinition object, IResourceDescriptions index,
		Set<ObjectDefinition> result) {
		if (object == null || !result.add(object))
			return // definition already captured
		val domain = object.eContainer as DomainDefinition
		domain.relationships.filter(SubtypeRelationshipDefinition).filter[subtypes.contains(object)].map [
			supertype?.getObjectDefinition(index)
		].forEach [
			internalGetAllSupertypes(index, result)
		]
	}

	/**
	 * @return the object definition associated with an object declaration, by means of index lookup. 
	 */
	def ObjectDefinition getObjectDefinition(ObjectDeclaration it, IResourceDescriptions index) {
		val defs = getDefinitions(objectDefinition, index) 
		if(defs.size != 1)
			return null
		else 
			return defs.head as ObjectDefinition
	}
	
	def EClass getDefinitionClass(EObject declaration) {
		if (declaration != null) {
			val declarationClassName = declaration.eClass.name
			val index = declarationClassName.lastIndexOf('Declaration')
			if(index != -1) {
				val definitionClass = maslPackage.getEClassifier(declarationClassName.substring(0, index) + 'Definition')
				if(definitionClass instanceof EClass)
					return definitionClass
			}
		}
		return null
	}
	
	def EClass getDeclarationClass(EObject definition) {
		if (definition != null) {
			val definitionClassName = definition.eClass.name
			val index = definitionClassName.lastIndexOf('Definition')
			if(index != -1) {
				val declarationClass = maslPackage.getEClassifier(definitionClassName.substring(0, index) + 'Declaration')
				if(declarationClass instanceof EClass)
					return declarationClass
			}				
		}
		return null
	}
	
	def Iterable<EObject> getDefinitions(EObject declaration, EClass definitionClass, ISelectable index) {
		if (declaration != null && definitionClass != null) {
			val entries = index.getExportedObjects(definitionClass, declaration.fullyQualifiedName, false)
			return entries.map[EcoreUtil.resolve(EObjectOrProxy, declaration)]
		}
		return null
	}

	def Iterable<EObject> getDeclarations(EObject definition, EClass declarationClass, ISelectable index) {
		if (definition != null && declarationClass != null) {
			val entries = index.getExportedObjects(declarationClass, definition.fullyQualifiedName, false)
			return entries.map[EcoreUtil.resolve(EObjectOrProxy, definition)] 
		}
		return emptyList
	}

	/**
	 * @return the object declaration this relationship navigation points to.
	 */
	def ObjectDeclaration getReferredObject(RelationshipNavigation it) {
		if (it != null) {
			if (relationship instanceof SubtypeRelationshipDefinition)
				return (relationship as SubtypeRelationshipDefinition).supertype
			else if (object != null)
				return object
			else {
				switch objectOrRole {
					ObjectDeclaration:
						return (objectOrRole as ObjectDeclaration)
					RelationshipEnd:
						return (objectOrRole as RelationshipEnd).to
				}
			}
		}
		return null
	}

	def boolean isOperation(EObject it) {
		switch it {
			DomainFunctionDeclaration,
			DomainServiceDeclaration,
			ObjectFunctionDeclaration,
			ObjectServiceDeclaration,
			TerminatorFunctionDeclaration,
			TerminatorServiceDeclaration: 
				true
			default:
				false		
		}
	}
	
	def boolean isCastExpression(OperationCall it) {
		receiver == null && feature instanceof TypeDeclaration
	}
	
	
	def ObjectDeclaration getContainerObject(Expression expr) {
		var parent = expr.eContainer
		while (parent != null) {
			switch parent {
				ObjectServiceDefinition:
					return parent.object
				ObjectFunctionDefinition:
					return parent.object
				StateDefinition:
					return parent.object
			}
			parent = parent.eContainer
		}
		return null
	}
	
	def DomainDefinition getDomain(EObject expr) {
		var current = expr
		while (current != null) {
			switch current {
				DomainDefinition:
					return current
				ObjectServiceDefinition:
					return current.domain
				ObjectFunctionDefinition:
					return current.domain
				TerminatorServiceDefinition:
					return current.domain
				TerminatorFunctionDefinition:
					return current.domain
				StateDefinition:
					return current.domain
			}
			current = current.eContainer
		}
		return null
	}
	
}
