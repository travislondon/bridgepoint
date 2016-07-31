/**
 * generated by Xtext 2.9.1
 */
package org.xtuml.bp.ui.xtext.myDsl;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>array Bounds</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.xtuml.bp.ui.xtext.myDsl.arrayBounds#getE <em>E</em>}</li>
 * </ul>
 *
 * @see org.xtuml.bp.ui.xtext.myDsl.MyDslPackage#getarrayBounds()
 * @model
 * @generated
 */
public interface arrayBounds extends constrainedArrayTypeRef
{
  /**
   * Returns the value of the '<em><b>E</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>E</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>E</em>' containment reference.
   * @see #setE(expression)
   * @see org.xtuml.bp.ui.xtext.myDsl.MyDslPackage#getarrayBounds_E()
   * @model containment="true"
   * @generated
   */
  expression getE();

  /**
   * Sets the value of the '{@link org.xtuml.bp.ui.xtext.myDsl.arrayBounds#getE <em>E</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>E</em>' containment reference.
   * @see #getE()
   * @generated
   */
  void setE(expression value);

} // arrayBounds
