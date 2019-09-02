FROM openjdk:8-jdk
MAINTAINER Zhang Xin(zhangxin@apache.org)
ENV AGENT_PATH /skywalking/agent
ENV SCENARIO_CASE_HOME /skywalking/scenario_case_home

COPY agent-test-helper.jar /usr/local
COPY run.sh /usr/local

VOLUME /skywalking/scenario_case_home
CMD ["/usr/local/run.sh"]