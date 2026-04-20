FROM tomcat:9.0

RUN rm -rf /usr/local/tomcat/webapps/*

COPY target/controle-financeiro-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

ENV PORT=10000

RUN sed -i 's/port="8080"/port="10000"/' /usr/local/tomcat/conf/server.xml

CMD ["catalina.sh", "run"]