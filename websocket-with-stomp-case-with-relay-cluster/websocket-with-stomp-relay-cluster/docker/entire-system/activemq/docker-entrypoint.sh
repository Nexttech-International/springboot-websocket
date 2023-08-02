#!/usr/bin/env bash

# start wait command first
echo "Wait for ${WAIT_HOSTS} before running the migration scripts"
/wait

cd "$ACTIVEMQ_HOME"
bin/activemq console