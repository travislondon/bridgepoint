//========================================================================
//
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

package org.xtuml.bp.ui.text.editor.oal;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.texteditor.ContentAssistAction;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.xtuml.bp.ui.preference.IPreferenceModel;
import org.xtuml.bp.ui.text.AbstractModelElementTextEditor;
import org.xtuml.bp.ui.text.OALEditorPlugin;
import org.xtuml.bp.ui.text.editor.ActionLanguageDocumentProvider;
import org.xtuml.bp.ui.text.editor.SyntaxHighlightingPreferences;

public class OALEditor extends AbstractModelElementTextEditor {

	private SyntaxHighlightingPreferences preferences;
	private IPreferenceModel.IChangeListener prefChangeListener;

	public OALEditor() {
		this(OALEditorPlugin.getDefaultOALPlugin().getSyntaxHighlightingPreferences());
	}

	public OALEditor(SyntaxHighlightingPreferences aPreferences) {
		super();

		preferences = aPreferences;

		setSourceViewerConfiguration(new OALEditorConfiguration(preferences, this));
		setDocumentProvider(new ActionLanguageDocumentProvider());
		setEventListeners();
	}
	
	public void createPartControl(Composite parent){
		super.createPartControl(parent);
		getTextWidget().setBackground(preferences.getBackgroundColor());
	}

	public void dispose() {
		preferences.removeModelChangeListener(prefChangeListener);
		super.dispose();
	}

	protected void setEventListeners() {
		prefChangeListener = new IPreferenceModel.IChangeListener() {
			public void modelChanged(IPreferenceModel model, int changeHints) {
				switch (changeHints) {
					case SyntaxHighlightingPreferences.MODEL_BACKGROUND_CHANGED :
						getTextWidget().setBackground(preferences.getBackgroundColor());
						break;
					case SyntaxHighlightingPreferences.MODEL_TOKEN_ATTRIBUTE_CHANGED :
						getSourceViewer().invalidateTextPresentation();
						//getTextWidget().redrawRange(0, doc.getLength(), true);
						break;
				}
			}
		};

		preferences.addModelChangeListener(prefChangeListener);
	}

	public StyledText getTextWidget() {
		return getSourceViewer().getTextWidget();
	}
    
    /* (non-Javadoc)
     * @see org.eclipse.ui.texteditor.AbstractDecoratedTextEditor#isPrefQuickDiffAlwaysOn()
     */
    protected boolean isPrefQuickDiffAlwaysOn()
    {
        // we don't currently want quick-diff info to be displayed in the
        // line column bar
        return false;
    }
    
    @Override
    protected void createActions() {
        super.createActions();
        Action action = new ContentAssistAction(OALEditorPlugin.getDefaultOALPlugin().getResourceBundle(), "ContentAssistProposal.", this); 
        String id = ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS;
        action.setActionDefinitionId(id);
        setAction("ContentAssistProposal", action); 
        markAsStateDependentAction("ContentAssistProposal", true);
    }
    
	@Override
	protected boolean isTabsToSpacesConversionEnabled()
	{ 
	    IPreferenceStore store = EditorsUI.getPreferenceStore(); 
	    boolean spacesForTabs = store.getBoolean(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_SPACES_FOR_TABS);
		return spacesForTabs;
	}
}