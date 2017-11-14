.//====================================================================
.//
.// File:      $RCSfile: validate_gen.arc,v $
.// Version:   $Revision: 1.29.24.1 $
.// Modified:  $Date: 2013/07/19 12:26:27 $
.//
.// Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
.//
.//====================================================================
.//
.invoke arc_env = GET_ENV_VAR( "PTC_MC_ARC_DIR" )
.assign mc_archetypes = arc_env.result
.if ( mc_archetypes == "" )
  .print "\nERROR: Environment variable PTC_MC_ARC_DIR not set."
  .exit 100
.end if
.//
.invoke mc_root_pkg_name = GET_ENV_VAR("PTC_MCC_ROOT")
.assign mc_root_pkg = mc_root_pkg_name.result
.//
.include "arc/get_names.inc"
.//
.//===============================================
.//
.// Generate the java class
.// We need to set the dom.Name to the instance variable name
.// before including the MC-Java files so that
.// the function invocations get the correct variable name.
.//
.select any root_pkg from instances of EP_PKG where (selected.Name == mc_root_pkg)
.assign root_pkg.Name = "Self" 
.//
.//
.include "${mc_archetypes}/do_type.inc"
.include "${mc_archetypes}/arch_utils.inc"
.//
.//
.include "${mc_archetypes}/value.inc"
.include "${mc_archetypes}/statement.inc"
.include "${mc_archetypes}/block.inc"
.include "${mc_archetypes}/translate_oal.inc"
.include "../org.xtuml.bp.core/color/ooaofooa_package_spec.clr"
.//
.// The root package is renamed when generating the parser so that
.// generated code can be run against a containing specialized
.// parser class. Hence, we pass 'Self' rather than 'ooaofooa' here.
.invoke translate_all_oal("Self", "Ooaofooa", true );
.//
.clear

.invoke lang_name = get_lang_name()
.invoke package = get_common_package_name()
.invoke class_name = get_validation_class_name()
.assign java_class = "${class_name.result}"
//========================================================================
//
// File: ${java_class}.java
//
// WARNING:      Do not edit this generated file
// Generated by: ${info.arch_file_name}
// Version:      $$Revision: 1.29.24.1 $$
//
//
//========================================================================
// Copyright 2014 Mentor Graphics Corporation
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
package ${package.result}.${lang_name.result};

import antlr.RecognitionException;
import antlr.Token;

import org.xtuml.bp.core.*;
import org.xtuml.bp.core.common.*;
.select many fncs from instances of S_SYNC where ( ( "${selected.Descrip:ParserValidateFunction}" == "TRUE" ) and ( "${selected.Descrip:Import}" != "" ) )
.for each fnc in fncs
import ${fnc.Descrip:Import};
.end for

public class ${java_class} {
  
  public java.util.UUID m_act_id = IdAssigner.NULL_UUID;
  private ${java_class} Self = null;
  private NonRootModelElement m_nrme = null;
  private boolean contentAssistEnabled;
  
  public ${java_class}(NonRootModelElement nrme) {
    Self = this;
    m_nrme = nrme;
    contentAssistEnabled = false;
  }

  public ${java_class}(NonRootModelElement nrme, boolean contentAssistEnabled) {
    this(nrme);
    this.contentAssistEnabled = contentAssistEnabled;
  }

.select many fncs from instances of S_SYNC
.for each fnc in fncs
  .if ("${fnc.Descrip:ParserValidateFunction}" == "TRUE")
    .select one ret_dt related by fnc->S_DT[R25]
    .invoke type = do_type(ret_dt)
    .select many prms related by fnc->S_SPARM[R24]
    .invoke SortSetAlphabeticallyByNameAttr( prms )
    .assign prm_count = cardinality prms
    .assign prm_number = 0
    .if (prm_count == 0)
  public ${type.body} $cr{fnc.Name}(Ooaofooa modelRoot)
    .else
  public ${type.body} $cr{fnc.Name}(Ooaofooa modelRoot
      .assign first = true
      .while ( prm_number < prm_count )
        .for each prm in prms
          .if ( prm.Order == prm_number )
            .select one prm_dt related by prm->S_DT[R26]
            .invoke type = do_type(prm_dt)
     , final ${type.body} p_$cr{prm.name} 
          .end if
        .end for
        .assign prm_number = prm_number + 1
      .end while
      )
    .end if
    .// emit body of function
	throws RecognitionException
  {
    .if ("${fnc.Descrip:Translate}" == "native")
${fnc.Action_Semantics}
    .else
    Ooaofooa.log.println(ILogger.FUNCTION, "${fnc.Name}", " Function entered: $cr{fnc.Name}") ; 

      .// Generate Action Language Code using Processing Subsystem Instances
      .select one actionblock related by fnc->ACT_FNB[R695]->ACT_ACT[R698]
      .select one block related by actionblock->ACT_BLK[R666]
      .invoke blck = blck_xlate(block)
${blck.body}
    .end if
  }    
  .end if
.end for
}
.//
.//
.invoke package = get_common_package_name()
.invoke src_path = get_common_package_source_path()
.assign outdir = "../${package.result}.${lang_name.result}/src/${src_path.result}/${lang_name.result}"
.assign tstfile = "${outdir}/${java_class}.java"
.emit to file "${tstfile}"
