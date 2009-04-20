%~d0
cd %~p0

mvn clean source:jar javadoc:jar deploy -DupdateReleaseInfo=true
