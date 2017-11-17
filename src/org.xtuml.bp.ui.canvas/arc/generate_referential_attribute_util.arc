.invoke arc_env = GET_ENV_VAR( "PTC_MC_ARC_DIR" )
.assign mc_archetypes = arc_env.result
.if ( mc_archetypes == "" )
  .print "\nERROR: Environment variable PTC_MC_ARC_DIR not set."
  .exit 100
.end if
.//
.include "${mc_archetypes}/arch_utils.inc"
.include "${mc_archetypes}/referential_attribute_util.inc"
.//
.//
.invoke ref_attr_util_class = genReferentialAttributeUtilClass( "CanvasReferentialAttributeUtil" )
//======================================================================================
//
// File: org.xtuml.bp.ui.canvas/src/org/xtuml/bp/ui/canvas/util/CanvasReferentialAttributeUtil.java
//
// WARNING:      Do not edit this generated file
// Generated by: ${info.arch_file_name}
//
//======================================================================================

package org.xtuml.bp.ui.canvas.util;

import java.util.HashSet;
import java.util.Set;

import org.xtuml.bp.ui.canvas.*;
import org.xtuml.bp.core.common.IReferentialAttributeManager;
import org.xtuml.bp.core.common.IdAssigner;
import org.xtuml.bp.core.common.NonRootModelElement;

${ref_attr_util_class.body}
.//
.emit to file "src/org/xtuml/bp/ui/canvas/util/CanvasReferentialAttributeUtil.java"
.//
