/**
 */
package org.xtuml.bp.xtext.masl.maslBase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>State Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.xtuml.bp.xtext.masl.maslBase.MaslBasePackage#getStateType()
 * @model
 * @generated
 */
public enum StateType implements Enumerator
{
	/**
	 * The '<em><b>Assigner</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ASSIGNER_VALUE
	 * @generated
	 * @ordered
	 */
	ASSIGNER(0, "assigner", "assigner"),

	/**
	 * The '<em><b>Assigner Start</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ASSIGNER_START_VALUE
	 * @generated
	 * @ordered
	 */
	ASSIGNER_START(1, "assignerStart", "assignerStart"),

	/**
	 * The '<em><b>Creation</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CREATION_VALUE
	 * @generated
	 * @ordered
	 */
	CREATION(2, "creation", "creation"),

	/**
	 * The '<em><b>Terminal</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TERMINAL_VALUE
	 * @generated
	 * @ordered
	 */
	TERMINAL(3, "terminal", "terminal");

	/**
	 * The '<em><b>Assigner</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Assigner</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ASSIGNER
	 * @model name="assigner"
	 * @generated
	 * @ordered
	 */
	public static final int ASSIGNER_VALUE = 0;

	/**
	 * The '<em><b>Assigner Start</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Assigner Start</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ASSIGNER_START
	 * @model name="assignerStart"
	 * @generated
	 * @ordered
	 */
	public static final int ASSIGNER_START_VALUE = 1;

	/**
	 * The '<em><b>Creation</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Creation</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CREATION
	 * @model name="creation"
	 * @generated
	 * @ordered
	 */
	public static final int CREATION_VALUE = 2;

	/**
	 * The '<em><b>Terminal</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Terminal</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TERMINAL
	 * @model name="terminal"
	 * @generated
	 * @ordered
	 */
	public static final int TERMINAL_VALUE = 3;

	/**
	 * An array of all the '<em><b>State Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final StateType[] VALUES_ARRAY =
		new StateType[]
		{
			ASSIGNER,
			ASSIGNER_START,
			CREATION,
			TERMINAL,
		};

	/**
	 * A public read-only list of all the '<em><b>State Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<StateType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>State Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static StateType get(String literal)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			StateType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>State Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static StateType getByName(String name)
	{
		for (int i = 0; i < VALUES_ARRAY.length; ++i)
		{
			StateType result = VALUES_ARRAY[i];
			if (result.getName().equals(name))
			{
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>State Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static StateType get(int value)
	{
		switch (value)
		{
			case ASSIGNER_VALUE: return ASSIGNER;
			case ASSIGNER_START_VALUE: return ASSIGNER_START;
			case CREATION_VALUE: return CREATION;
			case TERMINAL_VALUE: return TERMINAL;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private StateType(int value, String name, String literal)
	{
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue()
	{
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName()
	{
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral()
	{
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString()
	{
		return literal;
	}
	
} //StateType
