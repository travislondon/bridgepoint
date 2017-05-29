# There are some dependency issues with maven
# This script will run from a test project builder configuration, building org.xtuml.bp.pkg.feature before a test run
cmd="install"
skipTests=-DskipTests
if [[ $# -eq 3 ]] ; then
  # if not test, then we do not want skipTests
  if [[ $3 = "test" ]]; then
	skipTests=""
  else
    cmd="clean"
  fi
fi
cd ${1}/../src/org.xtuml.bp.pkg-feature && mvn -Dtycho.disableP2Mirrors=true $skipTests -Dmaven.test.failure.ignore=true install && cd ${2} && mvn -Dtycho.disableP2Mirrors=true $skipTests -Dmaven.test.failure.ignore=true $cmd