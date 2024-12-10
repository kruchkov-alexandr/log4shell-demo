#!/bin/bash

export CLASSPATH="/app/target/classes:$(find /root/.m2/repository -name '*.jar' | tr '\n' ':')"
java de.predic8.LdapServer &
sleep 5
exec java de.predic8.HttpServer
sleep 5
