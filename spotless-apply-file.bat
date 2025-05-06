set FILE=%1
call mvn spotless:apply -DspotlessFiles=%FILE:\=\\%
