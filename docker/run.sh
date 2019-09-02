#!/usr/bin/env bash

SCENARIO_CASE_HOME=${SCENARIO_CASE_HOME}
OUTPUT_DIR=/skywalking/scenario_case_home
CONFIG_FILE=/skywalking/scenario_case_home/configuration.yml

if [ ! -f "$CONFIG_FILE" ]; then
    echo "The scenario miss configuration.yml"
    exit 0
fi

java -Dconfigure.file=${CONFIG_FILE} \
    -Dscenario.home=${SCENARIO_CASE_HOME} \
    -Doutput.dir=${OUTPUT_DIR} \
    -Dagent.dir=${AGENT_PATH} \
    -jar /usr/local/agent-test-helper.jar