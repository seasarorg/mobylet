%~d0
cd %~p0

cmd /k mvn clean source:jar javadoc:jar deploy -DupdateReleaseInfo=true
