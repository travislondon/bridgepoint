//=====================================================================
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
//=====================================================================


import org.eclipse.core.runtime.CoreException;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.test.IPRSetupTests;
import org.xtuml.bp.core.test.SetupCreationTests;
import org.xtuml.bp.core.test.TigerNatureWorkspaceSetupTestGenerics;
import org.xtuml.bp.core.util.WorkspaceUtil;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
* Test the system level areas of core.
*/
public class WorkspaceSetupTestSuite extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 * @throws CoreException
	 */
	public static Test suite() throws CoreException {
		return new WorkspaceSetupTestSuite();
	}

	/**
	 * Construct the test suite.
	 */
	public WorkspaceSetupTestSuite() throws CoreException {

		// turn off autobuild to stop MC-3020 builders from running
		WorkspaceUtil.setAutobuilding(false);;   // throws CoreException

		CorePlugin.disableParseAllOnResourceChange();

        addTest(new TestSuite(IPRSetupTests.class));
        addTest(new TestSuite(SetupCreationTests.class));
        addTest(new TestSuite(TigerNatureWorkspaceSetupTestGenerics.class));
	}
}