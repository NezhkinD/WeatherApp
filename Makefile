
build:
	bash /opt/maven/bin/mvn compile

package:
	bash /opt/maven/bin/mvn package

clean-install:
	bash /opt/maven/bin/mvn clean install

clean-package:
	bash /opt/maven/bin/mvn clean package

run:
	java -jar target/WeatherApp-1.0.jar